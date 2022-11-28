package com.baemin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.baemin.aop.IsMyStore;
import com.baemin.config.auth.CustomUserDetails;
import com.baemin.dto.StoreDetailDto;
import com.baemin.dto.StoreDto;
import com.baemin.service.AdminService;
import com.baemin.service.StoreService;

@Controller
public class AdminController {

	@Autowired
	AdminService adminService;

	@Autowired
	StoreService storeService;

	@GetMapping("/admin/myStore")
	public String myStore(@AuthenticationPrincipal CustomUserDetails principal, Model model) {
		long userId = principal.getId();
		List<StoreDto> storeList = adminService.myStore(userId);

		model.addAttribute("storeList", storeList);
		return "admin/myStore";
	}

	@IsMyStore
	@GetMapping("/admin/management/{id}/detail")
	public String detail(@PathVariable long id, @AuthenticationPrincipal CustomUserDetails principal, Model model) {
		long userId = principal.getId();
		StoreDetailDto storeDetailDto = storeService.storeDetail(id, userId);
		model.addAttribute("store", storeDetailDto);
		model.addAttribute("adminPage", true);

		return "admin/detail";
	}

	// 주문접수 페이지
	@IsMyStore
	@GetMapping("/admin/management/order/{id}")
	public String order(@PathVariable long id) {
		return "admin/order";
	}

	@IsMyStore
	@GetMapping("/admin/management/sales/{id}")
	public String sales(@PathVariable long id) {
		return "admin/sales";
	}

}
