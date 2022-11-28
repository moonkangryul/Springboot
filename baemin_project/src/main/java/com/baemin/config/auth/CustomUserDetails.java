package com.baemin.config.auth;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Data;

@Data
public class CustomUserDetails implements UserDetails, OAuth2User {
	
	private static final long serialVersionUID = 1L;
	
	private int		 id;
	private String username;
	private String password;
	private String nickname;
	private String phone;
	private int		 point;
	private String role;
	
	// 계정이 가지고 있는 권한을 리턴
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority(this.role));
	}
	
	@Override
	public String getUsername() {
		return this.username;
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}
	
	// 계정이 만료되었는지를 리턴 (true : 만료되지 않음)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	// 계정이 잠겨있는지를 리턴
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	// 패스워드가 만료되었는지를 리턴
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	// 계정이 사용가능한지를 리턴
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public Map<String, Object> getAttributes() {
		return null;
	}
	
	@Override
	public String getName() {
		return null;
	}
	
}
