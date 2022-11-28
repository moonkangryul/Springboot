package com.baemin.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baemin.config.auth.CustomUserDetails;
import com.baemin.dto.SignupDto;

@Mapper
public interface AuthMapper {
	
	public int usernameChk(String username);
	
	public void signup(SignupDto signupDto);
	
	public CustomUserDetails getUser(String username);
	
}
