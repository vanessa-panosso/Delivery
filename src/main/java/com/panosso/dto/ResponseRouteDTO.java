package com.panosso.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Vanessa Panosso
 *
 */
@Getter
@Setter
public class ResponseRouteDTO {
	
	private String routes;
	
	private BigDecimal cost;
}
