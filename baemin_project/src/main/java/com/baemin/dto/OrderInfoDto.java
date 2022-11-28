package com.baemin.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class OrderInfoDto {
	
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
	
	private String impUid; // 아임포트 결제번호 추가
	
}
