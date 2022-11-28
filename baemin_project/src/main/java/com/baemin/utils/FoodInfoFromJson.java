package com.baemin.utils;

import java.util.ArrayList;
import java.util.List;

import com.baemin.dto.CartDto;
import com.google.gson.Gson;

public class FoodInfoFromJson {
	
	public static List<CartDto> foodInfoFromJson(String foodInfoJSON) {
		String[] arr	= foodInfoJSON.split("/");
		Gson		 gson	= new Gson();
		
		List<CartDto> cartDtoList = new ArrayList<>();
		for (int i = 0; i < arr.length; i++) {
			cartDtoList.add(gson.fromJson(arr[i], CartDto.class));
		}
		return cartDtoList;
	}
	
}
