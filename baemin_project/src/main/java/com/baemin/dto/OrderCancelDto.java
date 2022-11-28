package com.baemin.dto;

import lombok.Data;

@Data
public class OrderCancelDto {
	
	private String OrderNum;
	private long	 userId;
	private int		 totaloPrice;
	private int		 usedPoint;
	private int		 deleveryTip;
	
	private String impUid; // 아임포트 결제번호
	
}
