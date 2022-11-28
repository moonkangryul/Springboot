package com.baemin.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baemin.config.auth.CustomUserDetails;
import com.baemin.service.AuthService;
import com.baemin.service.FindService;
import com.baemin.service.UserService;
import com.baemin.utils.UserInfoSessionUpdate;

@RestController
public class UserApiController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	BCryptPasswordEncoder encodePwd;
	
	@Autowired
	AuthService authService;
	
	@Autowired
	FindService findService;
	
	// 리뷰 삭제
	@DeleteMapping("/api/user/review")
	public ResponseEntity<String> deleteReview(@AuthenticationPrincipal CustomUserDetails principal, String orderNum) {
		
		long id = principal.getId();
		userService.deleteReview(id, orderNum);
		return ResponseEntity.ok().body("리뷰 삭제 완료");
		
	}
	
	// 나의 비밀번호와 닉네임 수정하기
	@PatchMapping("/api/user/info")
	public ResponseEntity<String> modifyInfo(String value, String valueType, String prevPassword,
			@AuthenticationPrincipal CustomUserDetails principal, HttpSession session) {
		
		// value = 변경할 값, valueType = password, nickname, phone
		String username	= principal.getUsername();
		String msg			= "";
		
		switch (valueType) {
			case "password":
				if (!encodePwd.matches(prevPassword, principal.getPassword())) {
					return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
				}
				value = encodePwd.encode(value);
				msg = "비밀번호가 변경되었습니다.";
				break;
			case "nickname":
				msg = "닉네임이 변경되었습니다.";
				break;
			case "phone":
				msg = "전화번호가 변경되었습니다.";
				session.setMaxInactiveInterval(0);
				session.setAttribute("authNum", null);
				break;
		}
		
		userService.modifyInfo(username, valueType, value);
		UserInfoSessionUpdate.sessionUpdate(value, valueType, principal, session);
		
		return ResponseEntity.ok().body(msg);
		
	}
	
	// 인증번호 보내기
	@PostMapping("/api/user/sendAuthNum")
	private ResponseEntity<String> authNum(String phone, String email, HttpSession session) {
		
		String authNum = "";
		
		for (int i = 0; i < 6; i++) {
			authNum += (int) (Math.random() * 10);
		}
		
		System.out.println("인증번호 : " + authNum);
		
		if (phone != null) {// 전화번호로 인증번호 보내기 추가
			System.out.println("전화번호로 인증번호 보내기");
		} else if (email != null) {// 이메일로 인증번호 보내기
			System.out.println("이메일로 인증번호 보내기");
			findService.sendAuthNum(email, authNum);
		}
		
		Map<String, Object> authNumMap = new HashMap<>();
		
		long createTime	= System.currentTimeMillis();	// 인증번호 생성시간
		long endTime		= createTime + (300 * 1000);	// 인증번호 만료시간
		
		authNumMap.put("createTime", createTime);
		authNumMap.put("endTime", endTime);
		authNumMap.put("authNum", authNum);
		
		session.setMaxInactiveInterval(300);
		session.setAttribute("authNum", authNumMap);
		
		return new ResponseEntity<String>("인증번호가 전송되었습니다.", HttpStatus.OK);
		
	}
	
	// 인증번호가 맞는지 확인
	@PostMapping("/api/user/authNumCheck")
	private ResponseEntity<String> authNumCheck(String authNum, HttpSession session) {
		
		Map<String, Object>	sessionAuthNumMap	= (Map<String, Object>) session.getAttribute("authNum");
		String							msg								= "";
		
		if (sessionAuthNumMap == null) {
			msg = "인증번호를 전송해주세요.";
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}
		
		// 인증번호 만료시간
		long endTime = (long) sessionAuthNumMap.get("endTime");
		
		// 인증번호 만료시간이 지났다면
		if (System.currentTimeMillis() > endTime) {
			msg = "인증시간이 만료되었습니다.";
			session.setAttribute(authNum, null);
			session.setMaxInactiveInterval(0);
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}
		
		// 인증번호
		String sessionAuthNum = (String) sessionAuthNumMap.get("authNum");
		
		if (!authNum.equals(sessionAuthNum)) { // 인증번호가 일치하지 않는다면
			msg = "인증번호가 일치하지 않습니다.";
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		} else { // 인증번호가 일치하면
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		
	}
	
	// 비밀번호 변경
	@PatchMapping("/api/user/password")
	public ResponseEntity<String> modifyPassword(String password, String username, HttpSession session) {
		
		password = encodePwd.encode(password);
		userService.modifyInfo(username, "password", password);
		
		session.setMaxInactiveInterval(0);
		session.setAttribute("authStatus", null);
		return new ResponseEntity<String>("비밀번호를 변경했습니다.", HttpStatus.OK);
		
	}
	
}
