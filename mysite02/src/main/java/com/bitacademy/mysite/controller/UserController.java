package com.bitacademy.mysite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitacademy.mysite.repository.UserRepository;
import com.bitacademy.mysite.vo.UserVo;
import com.bitacademy.web.mvc.WebUtil;

public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String actionName = request.getParameter("a");
		
		if("joinform".equals(actionName)) {
			WebUtil.forward(request, response, "WEB-INF/views/user/joinform.jsp");
		} else if("joinsuccess".equals(actionName)) {
			WebUtil.forward(request, response, "WEB-INF/views/user/joinsuccess.jsp");
		} else if("join".equals(actionName)) {
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
			
			UserVo userVo = new UserVo();
			userVo.setName(name);
			userVo.setEmail(email);
			userVo.setPassword(password);
			userVo.setGender(gender);
			
			new UserRepository().insert(userVo);
			WebUtil.redirect(request, response, request.getContextPath() + "/user?a=joinsuccess");
		} else {
			WebUtil.redirect(request, response, request.getContextPath());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
