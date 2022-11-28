package com.baemin.api;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baemin.config.auth.CustomUserDetails;
import com.baemin.dto.CartListDto;
import com.baemin.dto.OrderInfoDto;
import com.baemin.service.OrderService;
import com.baemin.service.PaymentService;

@RestController
public class OrderApiController {
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	PaymentService paymentService;
	
	@PostMapping("/api/order/payment-cash")
	public ResponseEntity<?> payment(HttpSession session, OrderInfoDto orderInfoDto, long totalPrice,
			@AuthenticationPrincipal CustomUserDetails principal) throws IOException {
		
		CartListDto cartListDto = (CartListDto) session.getAttribute("cartList");
		
		long orderPriceCheck = orderService.orderPriceCheck(cartListDto);
		
		// System.out.println("계산금액 = " + totalPrice + " 실제 계산해야할 금액 = " + orderPriceCheck);
		
		if (orderPriceCheck == totalPrice) {
			orderService.order(cartListDto, orderInfoDto, principal, session);
			session.removeAttribute("cartList");
		}
		
		return ResponseEntity.ok().body("주문금액 일치");
		
	}
	
	// 카드 결제 성공 후
	@PostMapping("/api/order/payment/complete")
	public ResponseEntity<String> paymentComplete(HttpSession session, OrderInfoDto orderInfo, long totalPrice,
			@AuthenticationPrincipal CustomUserDetails user) throws IOException {
		
		String token = paymentService.getToken();
		System.out.println("token = " + token);
		
		// 결제 완료된 금액
		int amount = paymentService.paymentInfo(orderInfo.getImpUid(), token);
		
		try {
			// 주문 시 사용한 포인트
			int usedPoint = orderInfo.getUsedPoint();
			
			if (user != null) {
				int point = user.getPoint();
				
				// 사용된 포인트가 유저의 포인트보다 많을 때
				if (point < usedPoint) {
					paymentService.payMentCancle(token, orderInfo.getImpUid(), amount, "유저 포인트 오류");
					return new ResponseEntity<String>("유저 포인트 오류", HttpStatus.BAD_REQUEST);
				}
				
			} else {
				// 로그인 하지않았는데 포인트사용 되었을 때
				if (usedPoint != 0) {
					paymentService.payMentCancle(token, orderInfo.getImpUid(), amount, "비회원 포인트 사용 오류");
					return new ResponseEntity<String>("비회원 포인트 사용 오류", HttpStatus.BAD_REQUEST);
				}
			}
			
			CartListDto cartList = (CartListDto) session.getAttribute("cartList");
			// 실제 계산 금액 가져오기
			long orderPriceCheck = orderService.orderPriceCheck(cartList) - usedPoint;
			
			// 계산 된 금액과 실제 금액이 다를 때
			if (orderPriceCheck != amount) {
				paymentService.payMentCancle(token, orderInfo.getImpUid(), amount, "결제 금액 오류");
				return new ResponseEntity<String>("결제 금액 오류, 결제 취소", HttpStatus.BAD_REQUEST);
			}
			
			orderService.order(cartList, orderInfo, user, session);
			session.removeAttribute("cartList");
			
			return new ResponseEntity<>("주문이 완료되었습니다", HttpStatus.OK);
			
		} catch (Exception e) {
			paymentService.payMentCancle(token, orderInfo.getImpUid(), amount, "결제 에러");
			return new ResponseEntity<String>("결제 에러", HttpStatus.BAD_REQUEST);
		}
		
	}
	
}
