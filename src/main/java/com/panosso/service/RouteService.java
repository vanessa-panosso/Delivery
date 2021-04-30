package com.panosso.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panosso.calculate.CalculateRoute;
import com.panosso.calculate.Graph;
import com.panosso.calculate.Node;
import com.panosso.dto.GetRouteDTO;
import com.panosso.dto.ResponseRouteDTO;
import com.panosso.dto.RouteDTO;
import com.panosso.entity.Route;
import com.panosso.exception.DestinationRouteNotFound;
import com.panosso.exception.OriginRouteNotFound;
import com.panosso.repository.RouteRepository;

@Service
public class RouteService {

	@Autowired
	private RouteRepository repository;

	public void save(RouteDTO routeDTO) {
		final Route route = repository.findByName(routeDTO.getOriginName()).orElse(new Route(routeDTO.getOriginName()));
		Boolean foundDestination = false;
		for (Entry<Route, Integer> adjacentRoute : route.getAdjacentRoutes().entrySet()) {
			if (adjacentRoute.getKey().getName().equals(routeDTO.getDestination())) {
				adjacentRoute.setValue(routeDTO.getDistance());
				foundDestination = true;
			}
		}
		if (!foundDestination) {
			Route adjacentRoute = repository.findByName(routeDTO.getDestination())
					.orElse(new Route(routeDTO.getDestination()));
			if (adjacentRoute.getId() == null)
				adjacentRoute = repository.save(adjacentRoute);
			route.getAdjacentRoutes().put(adjacentRoute, routeDTO.getDistance());
		}
		repository.save(route);
	}

	public ResponseRouteDTO getRoute(GetRouteDTO dto) {
		final Route routeOrigin = repository.findByName(dto.getOriginName()).orElseThrow(OriginRouteNotFound::new);
		final List<Route> routes = repository.findAllByOrderByNameAsc();
		Graph graph = new Graph();
		Node nodeOrigin = new Node(routeOrigin.getName());
		graph.addNode(nodeOrigin);
		for (Route route : routes) {
			if (route.getName().equals(dto.getOriginName()))
				continue;
			graph.addNode(new Node(route.getName()));
		}
		
		createAdjacentNode(graph, routes);
		graph = CalculateRoute.calculateShortestPathFromSource(graph, nodeOrigin);

		final Node nodeDestination = graph.getNodes().stream().filter(n -> dto.getDestinationName().equals(n.getName()))
				.findFirst().orElse(null);

		if (nodeDestination == null)
			throw new DestinationRouteNotFound();
		
		final ResponseRouteDTO response = new ResponseRouteDTO();
		response.setRoutes(String.join(", ",
				nodeDestination.getShortestPath().stream().map(Node::getName).collect(Collectors.toList())));
		
		response.setCost(BigDecimal.valueOf(nodeDestination.getDistance())
				.divide(dto.getKmPerLiter(), 2, RoundingMode.HALF_EVEN).multiply(dto.getGasLiterPrice()).setScale(2));
		return response;
	}

	private void createAdjacentNode(Graph graph, List<Route> routes) {
		for (Route route : routes) {
			
			for (Entry<Route, Integer> adjacentRoute : route.getAdjacentRoutes().entrySet()) {
				final Node node = graph.getNodes().stream().filter(n -> n.getName().equals(route.getName())).findFirst().orElse(null);
				final Node adjacentNode = graph.getNodes().stream().filter(n -> n.getName().equals(adjacentRoute.getKey().getName())).findFirst().orElse(null);
				node.addDestination(adjacentNode, adjacentRoute.getValue());
			}
		}
		
	}
}
