package com.baemin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.baemin.dto.PointDto;
import com.baemin.dto.ReviewDto;

@Mapper
public interface UserMapper {
	
	// 포인트 내역
	public List<PointDto> myPoint(long id);
	
	// 리뷰내역
	public List<ReviewDto> myReviewList(long id);
	
	// 리뷰삭제
	public void deleteReview(Map<String, Object> map);
	
	// 유저정보 수정
	public void modifyInfo(Map<String, Object> map);
	
}
