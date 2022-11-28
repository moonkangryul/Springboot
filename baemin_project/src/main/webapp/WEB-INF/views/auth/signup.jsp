<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/layout/link.jsp"%>

<link rel="stylesheet" href="/css/auth/signin.css">

</head>
<body>
	<main>
		<div class="login_box">
			<a href="/">
				<img src="/img/bamin2.png" alt="/img/none.gif" class="bm_img">
			</a>
			
			<form action="/auth/signup" method="post">
				<div class="input_aera">
					<input type="text" name="username" class="username" maxlength="20" placeholder="아이디를 입력해 주세요" value="${signupDto.username}">
					<span class="msg_box">${valid_username}</span>					
				</div>
				<span>아이디는 특수문자를 제외한 5자 이상이어야 합니다.</span>
				
				<div class="input_aera">
					<input type="password" class="password1" name="password" maxlength="20" placeholder="비밀번호를 입력해 주세요" value="${signupDto.password}">					
				</div>
				<span>최소 하나의 문자 및 숫자를 포함한 8~16자 이어야 합니다.</span>

				<div class="input_aera">
					<input type="password" class="password2" maxlength="20" placeholder="비밀번호를 한 번 더 입력해 주세요" value="${signupDto.password}">
					<span class="msg_box">${valid_password}</span>
				</div>

				<div class="input_aera">
					<input type="text" name="email" class="email" placeholder="이메일을 입력해 주세요" value="${signupDto.email}">
					<span class="msg_box">${valid_email}</span>
				</div>

				<div class="input_aera">
					<input type="text" class="nickname" name="nickname" maxlength="20" placeholder="사용하실 닉네임을 입력해 주세요" value="${signupDto.nickname}">
					<span class="msg_box">${valid_nickname}</span>					
				</div>
				<span>숫자 또는 특수문자를 제외한 2자 이상 입력해주세요.</span>

				<div class="input_aera">
					<input type=number name="phone" class="phone" placeholder="휴대전화번호를 '-' 없이 입력해 주세요" maxlength="11" value="${signupDto.phone}">
					<span class="msg_box">${valid_phone}</span>
				</div>

				<input type="submit" value="회원가입" class="login_btn">				
			</form>
		</div>
	</main>
</body>
</html>