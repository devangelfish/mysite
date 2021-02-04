package com.bitacademy.mysite.exception;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bitacademy.mysite.dto.JsonResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Log LOGGER = LogFactory.getLog(GlobalExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public void handleException(
			HttpServletRequest request,
			HttpServletResponse response,
			Exception e) throws Exception {
		// 1. 로깅(Logging)
		StringWriter errors = new StringWriter(); // 버퍼
		e.printStackTrace(new PrintWriter(errors));
		LOGGER.error(errors.toString());
		
		// 2. 요청구분
		// 만약, JSON 요청인 경우 request header의 Accept application/json
		// 만약, HTML 요청인 경우 request header의 Accept text/html
		// 만약, jpg 요청인 경우 request header의 Accept image/jpeg
		String accept = request.getHeader("accept");
		if(accept.matches(".*application/json.*")) {
			/* 3. JSON 응답*/
			response.setStatus(HttpServletResponse.SC_OK);
			
			JsonResult jsonResult = JsonResult.fail(errors.toString());
			
			String jsonString = new ObjectMapper().writeValueAsString(jsonResult);
			
			OutputStream out = response.getOutputStream();
			out.write(jsonString.getBytes("UTF-8"));
			out.close();
		} else {
			// 3. 사과(안내페이지 포워딩, 정상종료)
			request.setAttribute("errors", errors);
			request
				.getRequestDispatcher("/WEB-INF/views/error/exception.jsp")
				.forward(request, response);
		}
	}
}
