package com.baemin.aop.exception;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {
	// try catch문을 통해 커스텀 예외처리
	@ExceptionHandler(CustomApiException.class)
	public ResponseEntity<?> apiException(CustomApiException e) {
		System.out.println(e.getMessage());
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	
	// try catch문 없이 전역 예외처리
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globalException(Exception e) {
		System.out.println(e.getMessage());
		return ResponseEntity.badRequest().body("예상치 못한 오류가 발생하였습니다! 관리자에게 문의하세요.");
	}
	
	@ExceptionHandler(SQLException.class)
	public ResponseEntity<?> sqlException(SQLException e) {
		System.out.println(e.getMessage());
		return ResponseEntity.badRequest().body("데이터베이스와 통신에 실패하였습니다.");
	}
	
	@ExceptionHandler(UnexpectedRollbackException.class)
	public ResponseEntity<?> rollbackException(UnexpectedRollbackException e) {
		System.out.println(e.getMessage());
		return ResponseEntity.badRequest().body("데이터 처리를 정상적으로 완료하지 못했습니다.");
	}
	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<?> sqlintException(SQLIntegrityConstraintViolationException e) {
		System.out.println(e.getMessage());
		return ResponseEntity.badRequest().body("무결성 제약 위반이 발생하였습니다.");
	}
	
}
