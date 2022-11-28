package com.baemin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.baemin.dto.FoodDto;
import com.baemin.dto.OrderCancelDto;
import com.baemin.dto.OrderListDto;
import com.baemin.dto.SalesDetailDto;
import com.baemin.dto.SalesDto;
import com.baemin.dto.StoreDto;

@Mapper
public interface AdminMapper {
	
	// 포인트 테이블 내역 insert
	public int pointUpdate(Map<String, Object> ponitUpdateMap);
	
	// 유저 테이블 point update
	public int pointUpdateUser(Map<String, Object> ponitUpdateMap);
	
	// 운영중인 매장 목록
	public List<StoreDto> myStore(long userId);
	
	// 메뉴 추가
	public void addMenu(FoodDto foodDto);
	
	// 매장 정보 수정
	public void storeInfoUpdate(StoreDto storeDto);
	
	// 메뉴 옵션 추가
	public void addMenuOption(List<Map<String, Object>> optionList);
	
	// 메뉴 옵션 삭제
	public void deleteMenuOption(long id);
	
	// 메뉴 수정
	public void updateMenu(Map<String, Object> map);
	
	// 메뉴 삭제
	public void deleteMenu(long storeId, long[] deleteNumber);
	
	// 사장님 답글
	public void bossComment(Map<String, Object> map);
	
	// 주문목록
	public List<OrderListDto> orderList(Map<String, Object> map);
	
	// 주문처리
	public void orderAccept(Map<String, Object> map);
	
	// 주문취소
	public void orderCancle(OrderCancelDto orderCancelDto);
	
	// 주문완료
	public void orderComplete(Map<String, Object> map);
	
	// 특정일 판매 데이터
	public List<SalesDetailDto> salesDetail(Map<String, Object> map);
	
	// 특정기간 매출 데이터
	public List<SalesDto> sales(Map<String, Object> map);
	
	// 관리중인 매장 리스트
	public List<Long> getMyStoreId(long userId);
	
}
