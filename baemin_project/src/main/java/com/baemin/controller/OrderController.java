package com.baemin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.baemin.config.auth.CustomUserDetails;
import com.baemin.dto.CartDto;
import com.baemin.dto.CartListDto;
import com.baemin.dto.OrderListDto;
import com.baemin.service.OrderService;
import com.baemin.utils.CreateOrderNum;
import com.baemin.utils.FoodInfoFromJson;
import com.baemin.utils.Page;

@Controller
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@GetMapping("/order/{storeId}")
	public String order(@PathVariable long storeId, HttpSession session, Model model) {
		CartListDto cartListDto = (CartListDto) session.getAttribute("cartList");
		model.addAttribute("cartList", cartListDto);
		String orderNum = CreateOrderNum.createOrderNum();
		model.addAttribute("orderNum", orderNum);
		return "order/order";
	}
	
	// 주문목록
	@GetMapping({"/orderList", "/orderList/{page}"})
	public String orderList(@AuthenticationPrincipal CustomUserDetails principal, Model model,
			@PathVariable(required = false) Integer page) {
		if (principal == null) {
			System.out.println("비로그인");
		} else {
			System.out.println("로그인");
			long userId = principal.getId();
			
			Page p = new Page(page);
			
			List<OrderListDto> orderList = orderService.orderList(userId, p);
			
			if (orderList.size() == 0) {
				return "order/orderList";
			}
			
			List<List<CartDto>> cartDtoList = new ArrayList<>();
			
			for (int i = 0; i < orderList.size(); i++) {
				cartDtoList.add(FoodInfoFromJson.foodInfoFromJson(orderList.get(i).getFoodInfo()));
				
			}
			
			p.totalPage(orderList.get(0).getListCount());
			
			model.addAttribute("cartList", cartDtoList);
			model.addAttribute("orderList", orderList);
			
			// 추가
			model.addAttribute("page", p);
		}
		
		return "order/orderList";
	}
	
	@GetMapping("/orderDetail/{orderNum}")
	public String orderDetail(@PathVariable String orderNum, Model model,
			@AuthenticationPrincipal CustomUserDetails principal) {
		
		OrderListDto orderListDto = orderService.orderListDetail(orderNum);
		
		if (principal != null && (principal.getId() != orderListDto.getUserId())) {
			System.out.println("다른 사용자");
			return "redirect:/";
		} else if (principal == null) {
			System.out.println("비로그인");
			return "redirect:/";
		}
		
		List<CartDto> CartDtolist = FoodInfoFromJson.foodInfoFromJson(orderListDto.getFoodInfo());
		
		model.addAttribute("orderDetail", orderListDto);
		model.addAttribute("cart", CartDtolist);
		
		return "order/orderDetail";
		
	}
	
}
