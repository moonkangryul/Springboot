package com.baemin.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baemin.config.auth.CustomUserDetails;
import com.baemin.dto.FoodOptionDto;
import com.baemin.dto.ReviewDto;
import com.baemin.dto.StoreDto;
import com.baemin.service.StoreService;

@RestController
public class StoreApiController {
	
	@Autowired
	StoreService storeService;
	
	// 메뉴 클릭시 음식 추가옵션 가져오기
	@GetMapping("/api/food/{foodId}/option")
	public List<FoodOptionDto> menuDetail(@PathVariable long foodId) {
		
		List<FoodOptionDto> foodOption = storeService.foodOption(foodId);
		return foodOption;
		
	}
	
	// 리뷰 작성
	@PostMapping("/api/review")
	public ResponseEntity<String> reviewWrite(ReviewDto reviewDto, @AuthenticationPrincipal CustomUserDetails principal) {
		
		reviewDto.setUserId(principal.getId());
		
		if (storeService.reviewWrite(reviewDto)) {
			return ResponseEntity.ok().body("리뷰 작성 완료");
		}
		
		return ResponseEntity.badRequest().body("파일 저장 실패");
		
	}
	
	// 리뷰 수정
	@PutMapping("/api/review")
	public ResponseEntity<String> reviewModify(ReviewDto reviewDto,
			@AuthenticationPrincipal CustomUserDetails principal) {
		
		reviewDto.setUserId(principal.getId());
		
		if (storeService.reviewModify(reviewDto)) {
			return ResponseEntity.ok().body("리뷰 수정 완료");
		}
		
		return ResponseEntity.badRequest().body("파일 저장 실패");
		
	}
	
	// 매장목록 페이징
	@GetMapping("/api/store/storeList")
	public ResponseEntity<List<StoreDto>> sortStore(int category, int address1, String sort, int page) {
		
		List<StoreDto> storeList = storeService.storeList(category, address1 / 100, sort, page);
		return ResponseEntity.ok().body(storeList);
		
	}
	
	// 찜하기
	@PostMapping("/api/store/likes")
	public ResponseEntity<String> likes(long id, String likes, @AuthenticationPrincipal CustomUserDetails principal) {
		
		if (principal == null) {
			return ResponseEntity.badRequest().body("회원만 가능합니다.");
		}
		
		long userId = principal.getId();
		storeService.likes(id, likes, userId);
		
		return ResponseEntity.ok().body("완료");
		
	}
	
	// 찜목록 페이징
	@GetMapping("/api/store/likesList")
	public ResponseEntity<List<StoreDto>> likesStore(@AuthenticationPrincipal CustomUserDetails principal, int page) {
		
		List<StoreDto> storeList = storeService.likesList(principal.getId(), page);
		return ResponseEntity.ok().body(storeList);
		
	}
	
	// 매장 검색 페이징
	@GetMapping("/api/store/storeSearch")
	public ResponseEntity<List<StoreDto>> storeSearch(String keyword, int address1, int page) {
		
		List<StoreDto> storeList = storeService.storeSearch(keyword, address1 / 100, page);
		return ResponseEntity.ok().body(storeList);
		
	}
	
}
