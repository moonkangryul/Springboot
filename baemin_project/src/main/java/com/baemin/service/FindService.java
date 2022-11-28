package com.baemin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.baemin.dao.FindMapper;

@Service
public class FindService {
	
	@Autowired
	FindMapper findMapper;
	
	@Autowired
	JavaMailSender mailSender;
	
	public List<String> findId(String email) {
		return findMapper.findId(email);
	}
	
	public void sendUsernames(String email, List<String> usernames) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setTo(email);
		simpleMailMessage.setSubject("아이디 찾기");
		
		StringBuffer sb = new StringBuffer();
		sb.append("가입하신 아이디는");
		sb.append(System.lineSeparator());
		
		for (int i = 0; i < usernames.size() - 1; i++) {
			sb.append(usernames.get(i));
			sb.append(System.lineSeparator());
		}
		sb.append(usernames.get(usernames.size() - 1)).append("입니다");
		
		simpleMailMessage.setText(sb.toString());
		
		new Thread(new Runnable() {
			public void run() {
				mailSender.send(simpleMailMessage);
			}
		}).start();
	}
	
	public boolean emailCheck(String username, String email) {
		Map<String, Object> map = new HashMap<>();
		map.put("username", username);
		map.put("email", email);
		String result = findMapper.emailCheck(map);
		if ("1".equals(result)) {
			return true;
		}
		return false;
	}
	
	public boolean phoneCheck(String username, String phone) {
		Map<String, Object> map = new HashMap<>();
		map.put("username", username);
		map.put("phone", phone);
		System.out.println(map);
		String result = findMapper.phoneCheck(map);
		if ("1".equals(result)) {
			return true;
		}
		return false;
	}
	
	public void sendAuthNum(String email, String authNum) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setTo(email);
		simpleMailMessage.setSubject("비밀번호 찾기 인증번호");
		
		String text = "인증번호는 " + authNum + "입니다";
		
		simpleMailMessage.setText(text);
		new Thread(new Runnable() {
			public void run() {
				mailSender.send(simpleMailMessage);
			}
		}).start();
		
	}
	
}
