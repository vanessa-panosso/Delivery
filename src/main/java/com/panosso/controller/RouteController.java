package com.panosso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.panosso.dto.GetRouteDTO;
import com.panosso.dto.RouteDTO;
import com.panosso.exception.DestinationRouteNotFound;
import com.panosso.exception.OriginRouteNotFound;
import com.panosso.service.RouteService;

@RestController
@RequestMapping
public class RouteController {

	@Autowired
	private RouteService service;
	
	@PostMapping("/route")
	public ResponseEntity<Void> createRoute(@RequestBody final RouteDTO route) {
		service.save(route);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PostMapping("/find-route")
	public ResponseEntity<Object> createRoute(@RequestBody final GetRouteDTO dto) {
		try {
			return new ResponseEntity<Object>(service.getRoute(dto), HttpStatus.OK);
		} catch (OriginRouteNotFound | DestinationRouteNotFound e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
