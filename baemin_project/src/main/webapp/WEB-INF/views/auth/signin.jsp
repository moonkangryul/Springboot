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

			<form action="/auth/signin" method="post">
				<div class="input_aera">
					<input type="text" name="username" value="" required placeholder="아이디를 입력해주세요" maxlength="30">
				</div>				
				<div class="input_aera">
					<input type="password" name="password" value="" required placeholder="비밀번호를 입력해 주세요" maxlength="30">
				</div>

				<input type="submit" value="로그인" class="login_btn">
				
				<div class="box">
					<div class="continue_login">
						<label for="continue_login">
							<span>로그인 유지하기</span> <input type="checkbox" id="continue_login" name="remember-me"> <i class="fas fa-check-square"></i>
						</label>
					</div>

					<div>
						<span class="id_search"><a href="/find/id">아이디</a></span> <span> ㅣ </span> <span><a href="/find/password">비밀번호 찾기</a></span>
					</div>
				</div>
			</form>

			<div id="oauth_login">
				<div>
					<a href="/oauth2/authorization/kakao"></a>
				</div>

				<div>
					<a href="/oauth2/authorization/naver"></a>
				</div>

				<div>
					<a href="/oauth2/authorization/google"></a>
				</div>
			</div>

			<div class="join">
				<a href="/auth/signup">회원가입 하러가기</a>
			</div>
		</div>
	</main>
</body>
</html>