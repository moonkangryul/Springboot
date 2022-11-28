package com.baemin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.baemin.dto.SignupDto;
import com.baemin.service.AuthService;
import com.baemin.utils.Script;

@Controller
public class AuthController {
	
	@Autowired
	AuthService authService;
	
	@GetMapping("/auth/signin")
	public String signin(HttpServletRequest request, HttpSession session) {
		String referer = (String) request.getHeader("referer");
		session.setAttribute("referer", referer);
		return "auth/signin";
	}
	
	@GetMapping("/auth/signup")
	public String signup() {
		return "auth/signup";
	}
	
	// 로그인 실패시 에러메시지를 보여주기 위함
	@GetMapping("/auth/failed")
	public String failedSignin(Model model) {
		return Script.locationMsg("/auth/signin", "아이디 또는 비밀번호를 잘못 입력하셨습니다.", model);
	}
	
	// Errors는 반드시 Request 객체 바로 뒤에 위치해야 함
	@PostMapping("/auth/signup")
	public String signup(@Valid SignupDto signupDto, Errors errors, Model model) {
		// 유효성 검사 실패
		if (errors.hasErrors()) {
			model.addAttribute("signupDto", signupDto); // 사용자로부터 입력받은 데이터를 유지하기 위함
			Map<String, String> validResult = authService.validHandling(errors); // 유효성검사에 실패한 필드와 메시지를 저장
			for (String key : validResult.keySet()) { // 필드를 key값으로 에러메시지 저장
				model.addAttribute(key, validResult.get(key));
			}
			return "auth/signup"; // 사용자로부터 입력받은 데이터와 에러메시지를 가지고 회원가입 페이지로 다시 이동
		}
		
		// username이 이미 존재할시 키 값에 오류메시지 저장
		if (authService.usernameChk(signupDto.getUsername()) != 0) {
			model.addAttribute("valid_username", "이미 등록된 아이디입니다.");
			return "auth/signup";
		}
		authService.signup(signupDto); // 유효성 검사 성공시 회원가입 서비스 로직 실행
		return Script.locationMsg("/auth/signin", "회원가입에 성공하였습니다.", model); // 이때 회원가입이 성공하였다는 메시지 출력 후 로그인페이지 이동
	}
	
}
