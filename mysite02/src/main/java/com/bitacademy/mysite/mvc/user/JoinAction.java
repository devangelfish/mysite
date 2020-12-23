package com.bitacademy.mysite.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitacademy.mysite.repository.UserRepository;
import com.bitacademy.mysite.vo.UserVo;
import com.bitacademy.web.mvc.Action;
import com.bitacademy.web.util.WebUtil;

public class JoinAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	}
}
