package com.baemin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baemin.dao.AdminMapper;
import com.baemin.dto.CartDto;
import com.baemin.dto.FoodDto;
import com.baemin.dto.OrderCancelDto;
import com.baemin.dto.OrderListDto;
import com.baemin.dto.SalesDetailDto;
import com.baemin.dto.SalesDto;
import com.baemin.dto.StoreDto;
import com.baemin.utils.FoodInfoFromJson;
import com.baemin.utils.Page;
import com.baemin.utils.SalesSort;

@Service
public class AdminService {
	
	@Autowired
	AdminMapper adminMapper;
	
	public List<StoreDto> myStore(long userId) {
		return adminMapper.myStore(userId);
	}
	
	// 메뉴추가
	@Transactional
	public void addMenu(FoodDto foodDto, String[] foodOption, Integer[] foodOptionPrice) {
		
		adminMapper.addMenu(foodDto);
		
		if (foodOption != null) {
			List<Map<String, Object>> optionList = new ArrayList<>();
			
			for (int i = 0; i < foodOption.length; i++) {
				Map<String, Object> optionMap = new HashMap<>();
				optionMap.put("optionName", foodOption[i]);
				optionMap.put("optionPrice", foodOptionPrice[i]);
				optionMap.put("foodId", foodDto.getId());
				optionList.add(optionMap);
			}
			
			adminMapper.addMenuOption(optionList);
		}
	}
	
	// 메뉴수정
	@Transactional
	public void updateMenu(FoodDto foodDto, String[] foodOption, Integer[] foodOptionPrice, Integer[] optionId) {
		Map<String, Object> map = new HashMap<>();
		if (foodOption == null) {
			adminMapper.deleteMenuOption(foodDto.getId());
		} else {
			List<Map<String, Object>> optionList = new ArrayList<>();
			
			for (int i = 0; i < foodOption.length; i++) {
				Map<String, Object>	optionMap	= new HashMap<>();
				long								oid				= -1;
				if (optionId.length != 0 && optionId[i] != null) {
					oid = optionId[i];
				}
				
				optionMap.put("optionId", oid);
				optionMap.put("optionName", foodOption[i]);
				optionMap.put("optionPrice", foodOptionPrice[i]);
				
				optionList.add(optionMap);
			}
			
			map.put("optionList", optionList);
		}  
		map.put("food", foodDto);
		adminMapper.updateMenu(map);
	}
	
	// 메뉴삭제
	public void deleteMenu(long storeId, long[] deleteNumber) {
		adminMapper.deleteMenu(storeId, deleteNumber);
	}
	
	// 매장 정보 수정
	public void storeInfoUpdate(StoreDto storeDto) {
		adminMapper.storeInfoUpdate(storeDto);
	}
	
	// 리뷰 답글
	public String bossComment(long storeId, String orderNum, String bossComment) {
		bossComment = bossComment.replace("\n", "<br>").replaceAll(" ", "&nbsp");
		
		Map<String, Object> map = new HashMap<>();
		map.put("storeId", storeId);
		map.put("bossComment", bossComment);
		map.put("orderNum", orderNum);
		
		adminMapper.bossComment(map);
		return bossComment;
	}
	
	// 주문 목록
	public List<OrderListDto> orderList(long storeId, String list, int page) {
		Page p = new Page(page, 10);
		
		Map<String, Object> map = new HashMap<>();
		map.put("storeId", storeId);
		map.put("list", list);
		map.put("firstList", p.getFirstList());
		map.put("lastList", p.getLastList());
		return adminMapper.orderList(map);
	}
	
	// 주문 수락
	public void orderAccept(String orderNum, long userId) {
		Map<String, Object> map = new HashMap<>();
		map.put("orderNum", orderNum);
		map.put("userId", userId);
		adminMapper.orderAccept(map);
	}
	
	// 주문 취소
	public void orderCancel(OrderCancelDto orderCancelDto) {
		adminMapper.orderCancle(orderCancelDto);
	}
	
	// 주문 완료
	public void orderComplete(String orderNum, long userId) {
		Map<String, Object> map = new HashMap<>();
		map.put("orderNum", orderNum);
		map.put("userId", userId);
		adminMapper.orderComplete(map);
	}
	
	// 특정일 판매 목록
	public Map<String, Object> salesDetail(long storeId, String date, String sort) {
		Map<String, Object> map = new HashMap<>();
		map.put("storeId", storeId);
		map.put("date", date);
		List<SalesDetailDto> salesToday	= adminMapper.salesDetail(map);
		long								 total			= 0;
		
		List<CartDto> menuList = new ArrayList<>();
		
		for (int i = 0; i < salesToday.size(); i++) {
			List<CartDto> cartList = FoodInfoFromJson.foodInfoFromJson(salesToday.get(i).getFoodInfo());
			
			for (int j = 0; j < cartList.size(); j++) {
				CartDto cart = cartList.get(j);
				if (menuList.contains(cart)) {
					
					int	index	 = menuList.indexOf(cart);
					int	amount = cart.getAmount();
					int	price	 = cart.getTotalPrice();
					
					menuList.get(index).setAmount(amount + menuList.get(index).getAmount());
					menuList.get(index).setTotalPrice(price + menuList.get(index).getTotalPrice());
					
				} else {
					menuList.add(cartList.get(j));
				}
			}
			
			total += salesToday.get(i).getTotalPrice();
		}
		
		map.remove("storeId'");
		map.remove("date");
		
		new SalesSort(menuList, sort);
		map.put("menuList", menuList);
		map.put("total", total);
		
		return map;
	}
	
	// 특정 기간 매출 데이터
	public List<SalesDto> sales(long storeId, String date, String term) {
		date = date + "-01";
		
		Map<String, Object> map = new HashMap<>();
		map.put("storeId", storeId);
		map.put("date", date);
		map.put("term", term);
		
		return adminMapper.sales(map);
	}
	
	// 관리중인 매장 리스트
	public List<Long> getMyStoreId(long userId) {
		return adminMapper.getMyStoreId(userId);
	}
	
}
