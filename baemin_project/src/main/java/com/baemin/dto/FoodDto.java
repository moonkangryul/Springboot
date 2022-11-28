package com.baemin.dto;

import lombok.Data;

@Data
public class FoodDto {
	
	private long	 id;
	private long	 storeId;
	private String foodName;
	private String foodPrice;
	private String foodDec;
	private String foodImg;
	private String foodThumb;
	
}
