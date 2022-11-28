package com.baemin.dto;

import java.util.Date;

import lombok.Data;

@Data
public class OrderListDto {
	
	private String orderNum;
	private long	 storeId;
	private long	 userId;
	private Date	 orderDate;
	private String deliveryStatus;
	private int		 deliveryAddress1;
	private String deliveryAddress2;
	private String deliveryAddress3;
	private String payMethod;
	private int		 totalPrice;
	private int		 usedPoint;
	private String phone;
	private String request;
	private String foodInfo;
	private String storeName;
	private String storeImg;
	private String storeThumb;
	private String deliveryTip;
	private int		 listCount;				// 목록 총 갯수
	
	private String reviewContent;
	private int		 score;
	private String reviewImg;
	
	private String impUid; // 아임포트 결제번호 추가
	
}
