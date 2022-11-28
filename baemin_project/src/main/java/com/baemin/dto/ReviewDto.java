package com.baemin.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ReviewDto {
	
	private String orderNum;
	private long	 storeId;
	private String storeName;
	private String reviewContent;
	private String bossComment;
	private Date	 regiDate;
	private float	 score;
	private String reviewImg;
	
	private MultipartFile file;
	
	private long	 userId;
	private String username;
	private String nickname;
	
}
