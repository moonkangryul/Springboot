package com.baemin.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.baemin.config.auth.CustomUserDetails;
import com.baemin.dto.PointDto;
import com.baemin.dto.ReviewDto;
import com.baemin.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/myPage")
	public String myPage() {
		return "user/myPage";
	}
	
	@GetMapping("/user/point")
	public String point(@AuthenticationPrincipal CustomUserDetails principal, Model model) {
		long					 id			 = principal.getId();
		List<PointDto> myPoint = userService.myPoint(id);
		model.addAttribute("myPoint", myPoint);
		model.addAttribute("point", principal.getPoint());
		
		return "user/point";
	}
	
	@GetMapping("/user/myReview")
	public String myreView(@AuthenticationPrincipal CustomUserDetails principal, Model model) {
		long						id					 = principal.getId();
		List<ReviewDto>	myReviewList = userService.myReviewList(id);
		model.addAttribute("myReviewList", myReviewList);
		
		return "user/myReview";
	}
	
	// 회원 정보 수정 페이지
	@GetMapping("/user/myInfo")
	public String myInfo() {
		return "user/myInfo";
	}
	
}
