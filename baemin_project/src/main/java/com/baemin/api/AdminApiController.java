package com.baemin.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baemin.dto.CartDto;
import com.baemin.dto.FoodDto;
import com.baemin.dto.OrderCancelDto;
import com.baemin.dto.OrderListDto;
import com.baemin.dto.SalesDto;
import com.baemin.dto.StoreDto;
import com.baemin.service.AdminService;
import com.baemin.service.PaymentService;
import com.baemin.utils.FileUpload;
import com.baemin.utils.FoodInfoFromJson;

@RestController
public class AdminApiController {
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	FileUpload fileUpload;
	
	@Autowired
	PaymentService paymentService;
	
	// 메뉴 추가
	@PostMapping("/api/admin/management/menu")
	public ResponseEntity<FoodDto> addMenu(FoodDto foodDto, String[] foodOption, Integer[] foodOptionPrice,
			MultipartFile file) throws IOException {
		
		if (file.isEmpty()) {
			String img = File.separator + "img" + File.separator + "none.gif";
			foodDto.setFoodImg(img);
			foodDto.setFoodThumb(img);
		} else {
			String img = fileUpload.uploadImg(file, "foodImg");
			foodDto.setFoodImg(img);
			foodDto.setFoodThumb(img);
		}
		adminService.addMenu(foodDto, foodOption, foodOptionPrice);
		return ResponseEntity.ok().body(foodDto);
	}
	
	// 메뉴 수정
	@PatchMapping("/api/admin/management/menu")
	public ResponseEntity<FoodDto> updateMenu(FoodDto foodDto, String[] foodOption, Integer[] foodOptionPrice,
			Integer[] optionId, MultipartFile file) throws IOException {
		
		System.out.println("foodDto = " + foodDto);
		
		if (!file.isEmpty()) {
			String img = fileUpload.uploadImg(file, "foodImg");
			foodDto.setFoodImg(img);
			foodDto.setFoodThumb(img);
		}
		adminService.updateMenu(foodDto, foodOption, foodOptionPrice, optionId);
		return ResponseEntity.ok().body(foodDto);
	}
	
	// 메뉴 삭제
	@DeleteMapping("/api/admin/management/menu")
	public ResponseEntity<String> deleteMenu(long storeId, long[] deleteNumber) {
		adminService.deleteMenu(storeId, deleteNumber);
		return ResponseEntity.ok().body("삭제가 완료되었습니다.");
	}
	
	// 매장정보 수정
	@PatchMapping("/api/admin/management/storeInfo")
	public ResponseEntity<StoreDto> storeInfoUpdate(StoreDto storeDto, MultipartFile file) throws IOException {
		if (!file.isEmpty()) {
			String img = fileUpload.uploadImg(file, "storeImg");
			storeDto.setStoreImg(img);
			storeDto.setStoreThumb(img);
		}
		System.out.println("storeDto = " + storeDto.toString());
		adminService.storeInfoUpdate(storeDto);
		return ResponseEntity.ok().body(storeDto);
	}
	
	// 리뷰 답글 작성
	@PatchMapping("/api/admin/management/bossComment")
	public ResponseEntity<String> bossComment(long storeId, String orderNum, String bossComment) throws IOException {
		String reviewContent = adminService.bossComment(storeId, orderNum, bossComment);
		return ResponseEntity.ok().body(reviewContent);
	}
	
	// 주문접수 리스트
	@GetMapping("/api/admin/management/orderList")
	public ResponseEntity<Map<String, Object>> orderList(long storeId, String list, int page) {
		
		System.out.println("storeId = " + storeId);
		System.out.println("list = " + list);
		System.out.println("page = " + page);
		
		List<OrderListDto>	orderListDto = adminService.orderList(storeId, list, page);
		Map<String, Object>	map				 = new HashMap<>();
		List<List<CartDto>>	menuList		 = new ArrayList<>();
		
		System.out.println("orderListDto = " + orderListDto);		
		
		if (orderListDto.size() != 0 && orderListDto.get(0).getFoodInfo() != null) {
			for (int i = 0; i < orderListDto.size(); i++) {
				menuList.add(FoodInfoFromJson.foodInfoFromJson(orderListDto.get(i).getFoodInfo()));
			}
		}
		
		System.out.println("menuList = " + menuList);
		map.put("orderList", orderListDto);
		map.put("cartList", menuList);
		return ResponseEntity.ok().body(map);
	}
	
	// 주문 수락
	@PatchMapping("/api/admin/management/orderAccept")
	public ResponseEntity<String> orderAccept(String orderNum, long userId) {
		// userId == 0 비회원
		adminService.orderAccept(orderNum, userId);
		return ResponseEntity.ok().body("주문접수완료");
	}
	
	// 주문 취소
	@PatchMapping("/api/admin/management/orderCancle")
	public ResponseEntity<String> orderCancle(OrderCancelDto orderCancelDto) throws IOException {
		System.out.println("주문 취소 = " + orderCancelDto.toString());
		if (!"".equals(orderCancelDto.getImpUid())) {
			String token	= paymentService.getToken();
			int		 amount	= paymentService.paymentInfo(orderCancelDto.getImpUid(), token);
			paymentService.payMentCancle(token, orderCancelDto.getImpUid(), amount, "관리자 취소");
		}
		adminService.orderCancel(orderCancelDto);
		return ResponseEntity.ok().body("주문취소완료");
	}
	
	// 주문 완료
	@PatchMapping("/api/admin/management/orderComplete")
	public ResponseEntity<String> orderComplete(String orderNum, long userId) {
		// userId == 0 비회원
		adminService.orderComplete(orderNum, userId);
		return ResponseEntity.ok().body("주문완료");
	}
	
	// 특정일 판매 데이터
	@GetMapping("/api/admin/management/salesDetail")
	public ResponseEntity<Map<String, Object>> salesDetail(long storeId, String date, String sort) {
		System.out.printf("가게 번호 : %d, 날짜 : %s ", storeId, date);
		Map<String, Object> salseToday = adminService.salesDetail(storeId, date, sort);
		return ResponseEntity.ok().body(salseToday);
	}
	
	// 특정기간 매출 데이터
	@GetMapping("/api/admin/management/sales")
	public ResponseEntity<List<SalesDto>> sales(long storeId, String date, String term) {
		System.out.printf("가게 번호 : %d, 날짜 : %s ", storeId, date);
		List<SalesDto> sales = adminService.sales(storeId, date, term);
		return ResponseEntity.ok().body(sales);
	}
	
}
