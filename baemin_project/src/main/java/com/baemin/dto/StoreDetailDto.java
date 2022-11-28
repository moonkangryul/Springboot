package com.baemin.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StoreDetailDto {
	
	private StoreDto				storeInfo;
	private List<FoodDto>		foodList;	 // 메뉴 목록
	private List<ReviewDto>	reviewList;
	
}
