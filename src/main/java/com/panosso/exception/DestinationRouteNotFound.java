package com.panosso.exception;

public class DestinationRouteNotFound extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DestinationRouteNotFound() {
		super("Destination route not found");
	}
}
