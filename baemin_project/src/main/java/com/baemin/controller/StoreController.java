package com.baemin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.baemin.config.auth.CustomUserDetails;
import com.baemin.dto.StoreDetailDto;
import com.baemin.dto.StoreDto;
import com.baemin.service.StoreService;

@Controller
public class StoreController {
	
	@Autowired
	StoreService storeService;
	
	@GetMapping("/store/{category}/{address1}")
	public String store(@PathVariable int category, @PathVariable int address1, Model model) {
		List<StoreDto> storeList = storeService.storeList(category, address1 / 100);
		model.addAttribute("storeList", storeList);
		return "store/store";
	}
	
	@GetMapping({"/store/{id}/detail", "/{id}/detail"})
	public String storeDetail(@PathVariable long id, Model model, @AuthenticationPrincipal CustomUserDetails principal) {
		
		long userId = 0;
		if (principal != null) {
			userId = principal.getId();
		}
		
		StoreDetailDto storeDetailDto = storeService.storeDetail(id, userId);
		model.addAttribute("store", storeDetailDto);
		
		return "store/detail";
	}
	
	// 찜한 가게 목록
	@GetMapping("/likes/store")
	public String likes(Model model, @AuthenticationPrincipal CustomUserDetails principal) {
		
		if (principal == null) {
			System.out.println("비로그인");
			
		} else {
			System.out.println("로그인");
			List<StoreDto> likesList = storeService.likesList(principal.getId());
			model.addAttribute("likesList", likesList);
		}
		
		return "/store/likes";
	}
	
	// 매장검색
	@GetMapping("/store/search")
	public String search(String keyword, Integer address1, Model model) {
		
		if (keyword != null) {
			
			List<StoreDto> storeList = storeService.storeSearch(keyword, address1 / 100);
			model.addAttribute("storeList", storeList);
			model.addAttribute("keyword", keyword);
			
		}
		
		return "store/search";
	}
	
}
