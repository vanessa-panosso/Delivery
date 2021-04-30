package com.panosso.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyJoinColumn;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Route {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(unique = true)
	private String name;

	@ElementCollection
	@CollectionTable(name = "adjacent_route", joinColumns = {
			@JoinColumn(name = "origin_id", referencedColumnName = "id") })
	@MapKeyJoinColumn(name = "destination_id")
    @Column(name = "distance")
	private Map<Route, Integer> adjacentRoutes = new HashMap<>();

	public void addDestination(Route destination, int distance) {
		adjacentRoutes.put(destination, distance);
	}

	public Route(String name) {
		this.name = name;
	}
}
