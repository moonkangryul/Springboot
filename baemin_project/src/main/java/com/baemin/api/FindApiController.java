package com.baemin.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baemin.service.AuthService;
import com.baemin.service.FindService;

@RestController
public class FindApiController {
	
	@Autowired
	FindService findService;
	
	@Autowired
	BCryptPasswordEncoder encodePwd;
	
	@Autowired
	AuthService authService;
	
	// 이메일로 아이디 보내기
	@PostMapping("/api/find/sendUsernames")
	public ResponseEntity<Object> sendEmail(String email) {
		
		List<String> usernames = findService.findId(email);
		
		if (usernames.size() != 0) {
			findService.sendUsernames(email, usernames);
			return new ResponseEntity<Object>(HttpStatus.OK);
		}
		
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		
	}
	
	// 아이디가 존재하는지 확인
	@GetMapping("/api/find/overlapCheck")
	public ResponseEntity<String> overlapCheck(String username, HttpSession session) {
		
		if (authService.usernameChk(username) != 0) {
			
			Map<String, Object> authStatus = new HashMap<>();
			authStatus.put("username", username);
			authStatus.put("status", false);
			
			session.setMaxInactiveInterval(300);
			session.setAttribute("authStatus", authStatus);
			
			return ResponseEntity.ok().body(username);
			
		}
		
		return ResponseEntity.badRequest().body("아이디가 존재하지 않습니다.");
		
	}
	
	// username의 이메일이 맞는지 확인
	@GetMapping("/api/find/password/emailCheck")
	public ResponseEntity<Boolean> emailCheck(String username, String email) {
		
		boolean emailCheck = findService.emailCheck(username, email);
		return new ResponseEntity<Boolean>(emailCheck, HttpStatus.OK);
		
	}
	
	// username의 전화번호가 맞는지 확인
	@GetMapping("/api/find/password/phoneCheck")
	public ResponseEntity<Boolean> phoneCheck(String username, String phone) {
		
		boolean phoneCheck = findService.phoneCheck(username, phone);
		return new ResponseEntity<Boolean>(phoneCheck, HttpStatus.OK);
		
	}
	
	// 인증완료 후
	@PostMapping("/api/find/auth/completion")
	public ResponseEntity<String> authCompletion(HttpSession session) {
		
		Map<String, Object> authStatus = (Map<String, Object>) session.getAttribute("authStatus");
		
		if (authStatus == null) {
			return new ResponseEntity<String>("인증시간이 만료되었습니다.", HttpStatus.BAD_REQUEST);
		}
		
		authStatus.put("status", true);
		return new ResponseEntity<String>(HttpStatus.OK);
		
	}
	
}
