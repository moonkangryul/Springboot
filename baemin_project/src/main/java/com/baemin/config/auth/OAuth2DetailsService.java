package com.baemin.config.auth;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.baemin.dao.AuthMapper;
import com.baemin.dto.SignupDto;

@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService {

	@Autowired
	AuthMapper authMapper;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		OAuth2User oauth2User = super.loadUser(userRequest);
		String provider = userRequest.getClientRegistration().getRegistrationId();
		Map<String, Object> userInfo = oauth2User.getAttributes();

		String username = "";
		String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
		String email = "";

		switch (provider) {

		case "google":
			username = "google_" + (String) userInfo.get("sub");
			email = (String) userInfo.get("email");
			break;

		case "naver":
			Map<String, Object> response = oauth2User.getAttribute("response");
			username = "naver_" + (String) response.get("id");
			email = (String) response.get("email");
			break;

		case "kakao":
			Map<String, Object> kakaoAccount = oauth2User.getAttribute("kakao_account");
			username = "kakao_" + userInfo.get("id");
			email = (String) kakaoAccount.get("email");
			break;
		}

		if (authMapper.usernameChk(username) == 0) { // 회원이 아닐시
			SignupDto signupDto = new SignupDto();
			signupDto.setUsername(username);
			signupDto.setNickname(username);
			signupDto.setEmail(email);
			signupDto.setPassword(password);
			signupDto.setPhone("");
			authMapper.signup(signupDto);
		}

		CustomUserDetails principal = authMapper.getUser(username);

		return principal;

	}

}
