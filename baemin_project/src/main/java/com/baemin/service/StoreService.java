package com.baemin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baemin.dao.StoreMapper;
import com.baemin.dto.FoodDto;
import com.baemin.dto.FoodOptionDto;
import com.baemin.dto.ReviewDto;
import com.baemin.dto.StoreDetailDto;
import com.baemin.dto.StoreDto;
import com.baemin.utils.FileUpload;
import com.baemin.utils.Page;

@Service
public class StoreService {
	
	@Autowired
	StoreMapper storeMapper;
	
	@Autowired
	FileUpload fileUpload;
	
	@Transactional
	public List<StoreDto> storeList(int category, int address) {
		return storeList(category, address, "기본 순", 1);
	}
	
	// 추가
	@Transactional
	public List<StoreDto> storeList(int category, int address1, String sort, int page) {
		Page								p		= new Page(page, 10);
		Map<String, Object>	map	= new HashMap<>();
		map.put("category", category);
		map.put("address", address1);
		map.put("firstList", p.getFirstList());
		map.put("lastList", p.getLastList());
		map.put("sort", sort);
//		System.out.println("페이지 시작 = " + p.getFirstList() + " 페이지 끝 = " + p.getLastList());
		return storeMapper.storeList(map);
	}
	
	// @Transactional
	// public StoreDetailDto storeDetail(long storeId) {
	// StoreDto storeDto = storeMapper.storeDetail(storeId);
	// List<FoodDto> foodList = storeMapper.foodList(storeId);
	// List<ReviewDto> reviewList = storeMapper.reviewList(storeId);
	// return new StoreDetailDto(storeDto, foodList, reviewList);
	// }
	
	@Transactional
	public StoreDetailDto storeDetail(long storeId, long userId) {
		
		Map<String, Long> map = new HashMap<>();
		map.put("storeId", storeId);
		map.put("userId", userId);
		
		StoreDto				storeDto	 = storeMapper.storeDetail(map);
		List<FoodDto>		foodList	 = storeMapper.foodList(storeId);
		List<ReviewDto>	reviewList = storeMapper.reviewList(storeId);
		
		return new StoreDetailDto(storeDto, foodList, reviewList);
	}
	
	@Transactional
	public List<FoodOptionDto> foodOption(long foodId) {
		return storeMapper.foodOption(foodId);
	}
	
	// 리뷰작성
	@Transactional
	public boolean reviewWrite(ReviewDto reviewDto) {
		if (reviewDto.getFile() == null) {
			reviewDto.setReviewImg("");
		}
		
		else {
			if (!fileUpload.uploadReviewImg(reviewDto))
				return false;
		}
		storeMapper.reviewWrite(reviewDto);
		return true;
	}
	
	// 리뷰수정
	@Transactional
	public boolean reviewModify(ReviewDto reviewDto) {
		if (reviewDto.getFile() == null) {
			if (reviewDto.getReviewImg() == null || reviewDto.getReviewImg() == "")
				reviewDto.setReviewImg("");
		} else {
			if (!fileUpload.uploadReviewImg(reviewDto))
				return false;
		}
		storeMapper.reviewModify(reviewDto);
		return true;
	}
	
	@Transactional
	public void likes(long storeId, String likes, long userId) {
		Map<String, Long> map = new HashMap<>();
		map.put("storeId", storeId);
		map.put("userId", userId);
		
		if (likes.equals("on")) {
			storeMapper.addLikes(map);
		} else {
			storeMapper.deleteLikes(map);
		}
	}
	
	// 찜한 가게 목록 첫 접근시
	public List<StoreDto> likesList(long userId) {
		return likesList(userId, 1);
	}
	
	// 찜한 가게 목록 추가 데이터 비동기
	@Transactional
	public List<StoreDto> likesList(long userId, int page) {
		Page								p		= new Page(page, 10);
		Map<String, Object>	map	= new HashMap<>();
		map.put("firstList", p.getFirstList());
		map.put("lastList", p.getLastList());
		map.put("userId", userId);
//		System.out.println("페이지 시작 = " + p.getFirstList() + " 페이지 끝 = " + p.getLastList());
		return storeMapper.likesList(map);
	}
	
	// 매장검색
	@Transactional
	public List<StoreDto> storeSearch(String keyword, int address) {
		
		return storeSearch(keyword, address, 1);
	}
	
	// 매장검색 무한스크롤 페이징
	@Transactional
	public List<StoreDto> storeSearch(String keyword, int address1, int page) {
		Page								p		= new Page(page, 10);
		Map<String, Object>	map	= new HashMap<>();
		map.put("keyword", keyword);
		map.put("address", address1);
		map.put("firstList", p.getFirstList());
		map.put("lastList", p.getLastList());
//		System.out.println("페이지 시작 = " + p.getFirstList() + " 페이지 끝 = " + p.getLastList());
		return storeMapper.storeSearch(map);
	}
	
}
