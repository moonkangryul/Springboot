package com.baemin.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.baemin.service.UserService;

@Controller
public class FindController {
	
	@Autowired
	UserService userService;
	
	// 아이디 찾기 페이지
	@GetMapping("/find/id")
	public String findId() {
		return "user/find/findId";
	}
	
	// 비밀번호 찾기 페이지
	@GetMapping("/find/password")
	public String findPassword() {
		return "user/find/findPassword";
	}
	
	// 인증번호 보내기 페이지
	@GetMapping("/find/password/auth")
	public String auth(String username, HttpSession session) {
		Map<String, Object> authStatus = (Map<String, Object>) session.getAttribute("authStatus");
		if (authStatus == null || !username.equals(authStatus.get("username"))) {
			return "redirect:/find/password";
		}
		return "user/find/auth";
	}
	
	// 비밀번호 변경 페이지
	@GetMapping("/find/modify/password")
	public String moldifyPassword(String username, HttpSession session) {
		Map<String, Object> authStatus = (Map<String, Object>) session.getAttribute("authStatus");
		
		if (authStatus == null || !username.equals(authStatus.get("username"))) {
			return "redirect:/find/password";
		}
		
		// 페이지에 왔을때 인증이 안되어 있다면
		if (!(boolean) authStatus.get("status")) {
			return "redirect:/find/password";
		}
		return "user/find/modify";
	}
	
}
