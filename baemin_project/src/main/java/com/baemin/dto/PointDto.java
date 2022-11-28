package com.baemin.dto;

import java.util.Date;

import lombok.Data;

@Data
public class PointDto {
	
	private long	 userId;
	private Date	 usedDate;
	private String info;
	private int		 point;
	
}
