package com.baemin.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.baemin.dto.CartDto;

public class SalesSort {
	
	public SalesSort(List<CartDto> menuList, String sort) {
		sort(menuList, sort);
	}
	
	public void sort(List<CartDto> menuList, String sort) {
		
		Collections.sort(menuList, new Comparator<CartDto>() {
			@Override
			public int compare(CartDto o1, CartDto o2) {
				
				// 가격 오름차순
				if ("price".equals(sort)) {
					return o1.getTotalPrice() - o2.getTotalPrice();
				}
				
				// 가격 내림차순
				else if ("priceR".equals(sort)) {
					return o2.getTotalPrice() - o1.getTotalPrice();
				}
				
				// 기본 정렬 이름 오름차순
				else {
					String name1 = o1.getFoodName();
					String name2 = o2.getFoodName();
					
					if (name1.compareTo(name2) == 0) {
						String[] option1 = o1.getOptionName();
						String[] option2 = o2.getOptionName();
						if (option1 == null) {
							return 1;
						}
						if (option2 == null) {
							return -1;
						}
						
						return option1[0].compareTo(option2[0]);
					}
					
					if ("nameR".equals(sort)) {
						return o2.getFoodName().compareTo(o1.getFoodName());
					} else {
						return o1.getFoodName().compareTo(o2.getFoodName());
					}
				}
			}
		});
	}
}
