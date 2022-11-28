package com.baemin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baemin.dao.UserMapper;
import com.baemin.dto.PointDto;
import com.baemin.dto.ReviewDto;

@Service
public class UserService {
	
	@Autowired
	UserMapper userMapper;
	
	public List<PointDto> myPoint(long id) {
		return userMapper.myPoint(id);
	}
	
	public List<ReviewDto> myReviewList(long id) {
		return userMapper.myReviewList(id);
	}
	
	public void deleteReview(long id, String orderNum) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", id);
		map.put("orderNum", orderNum);
		userMapper.deleteReview(map);
	}
	
	public void modifyInfo(String username, String valueType, String value) {
		Map<String, Object> map = new HashMap<>();
		map.put("username", username);
		map.put("valueType", valueType);
		map.put("value", value);
		userMapper.modifyInfo(map);
	}
	
}
