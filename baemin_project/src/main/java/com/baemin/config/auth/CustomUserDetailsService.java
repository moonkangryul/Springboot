package com.baemin.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.baemin.dao.AuthMapper;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	AuthMapper authMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CustomUserDetails principal = authMapper.getUser(username);
		if (principal == null)
			throw new UsernameNotFoundException("회원이 존재하지 않습니다.");
		return principal;
	}
	
}
