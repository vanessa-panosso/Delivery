package com.panosso.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.panosso.entity.Route;

public interface RouteRepository extends JpaRepository<Route, Integer> {

	Optional<Route> findByName(String name);

	List<Route> findAllByOrderByNameAsc();

}
