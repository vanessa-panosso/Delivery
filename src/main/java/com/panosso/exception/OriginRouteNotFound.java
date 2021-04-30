package com.panosso.exception;

public class OriginRouteNotFound extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OriginRouteNotFound() {
		super("Origin route not found");
	}
}
