package com.bitacademy.mysite.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public String handleException(Exception e) throws Exception {
		// 1. 로깅(Logging)
		e.printStackTrace();
		
		// 2. 사과(안내페이지 포워딩)
		return "error/exception";
	}
}
