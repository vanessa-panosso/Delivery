package com.panosso.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetRouteDTO {

	private String originName;
	
	private String destinationName;
	
	private BigDecimal kmPerLiter;
	
	private BigDecimal gasLiterPrice;
}
