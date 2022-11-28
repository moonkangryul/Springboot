package com.baemin.dto;

import lombok.Data;

@Data
public class StoreDto {
	
	private long	 id;
	private int		 category;
	private String storeName;
	private int		 storeAddress1;
	private String storeAddress2;
	private String storeAddress3;
	private String storePhone;
	private String storeImg;
	private String storeThumb;
	private int		 openingTime;
	private int		 closingTime;
	private int		 minDelivery;
	private int		 deliveryTime;
	private int		 deliveryTip;
	private String storeDes;
	
	private float	score;
	private int		orderCount;
	private int		reviewCount;
	private int		bossCommentCount;
	private int		likesCount;
	
	private int	score1;	// 리뷰 1점
	private int	score2;	// 리뷰 2점
	private int	score3;	// 리뷰 3점
	private int	score4;	// 리뷰 4점
	private int	score5;	// 리뷰 5점
	
	private String isOpen; // 영업중
	
	private int isLikes;// 찜 상태
	
}
