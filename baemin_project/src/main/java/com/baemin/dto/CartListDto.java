package com.baemin.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartListDto {
	
	private long	 storeId;
	private String storeName;
	int						 cartTotal;
	private int		 deliveryTip;
	
	List<CartDto> cartDto;
	
}
