package com.baemin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDetailDto {
	
	private String orderNum;
	private String foodInfoJSON;
	
}
