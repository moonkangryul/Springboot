<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/layout/link.jsp"%>

<link rel="stylesheet" href="/css/layout/nav.css">
<link rel="stylesheet" href="/css/user/myInfo.css">

<%@ include file="/WEB-INF/views/layout/header.jsp"%>

<div class="wrap">
		<section class="title">
				<h1>내 정보 수정</h1>
		</section>

		<main>
				<div class="info_box">
						<h2>닉네임</h2>
						<div class="user_nickname">${principal.nickname }</div>

						<h2>비밀번호</h2>
						<div>
								<div class="input_box">
										<div>
												<span>현재 비밀번호</span>
												<input class="present_password" type="password" placeholder="현재 비밀번호">
										</div>

										<div>
												<span>신규 비밀번호</span>
												<input class="new_password" type="password" placeholder="신규 비밀번호" maxlength="20">
										</div>

										<div>
												<span>신규 비밀번호 확인</span>
												<input class="new_password_check" type="password" placeholder="신규 비밀번호 확인" maxlength="20">
										</div>
								</div>

								<div class="btn_box">
										<button type="button" class="pwd_modify">변경</button>
								</div>
						</div>

						<h2>닉네임 변경</h2>
						<div>
								<div class="input_box">
										<input type="text" class="nickname" name="nickname" required placeholder="변경하실 닉네임을 입력해 주세요">
								</div>

								<div class="btn_box">
										<button type="button" class="nickname_modify">수정</button>
								</div>
						</div>

						<h2>휴대폰 번호 변경</h2>
						<div>
								<div class="input_box">
										<div>
												<input type="number" class="phone" name="phone" required placeholder="전화번호를 입력해 주세요" onkeypress="return lengthCheck(this, 11);">
										</div>

										<div class="auth_num_box">
												<input type="text" class="phone_auth_num" name="authNum" required placeholder="인증번호 입력">
												<span class="timer_box">
													<span>남은시간</span>
													<span class="timer"></span>
												</span>
										</div>
								</div>

								<div class="btn_box">
										<div>
												<button type="button" class="auth_num_send">인증번호 전송</button>
										</div>

										<div class="auth_num_box">
												<button type="button" class="phone_auth_btn">인증</button>
										</div>
								</div>
						</div>
				</div>
		</main>
</div>

<%@ include file="/WEB-INF/views/layout/nav.jsp"%>
<%@ include file="/WEB-INF/views/layout/footer.jsp"%>

<script type="text/javascript" src="/js/user/myInfo.js"></script>
<script type="text/javascript" src="/js/utils/util.js"></script>

</body>
</html>