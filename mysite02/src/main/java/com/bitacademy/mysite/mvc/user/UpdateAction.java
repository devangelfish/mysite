package com.bitacademy.mysite.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bitacademy.mysite.repository.UserRepository;
import com.bitacademy.mysite.vo.UserVo;
import com.bitacademy.web.mvc.Action;
import com.bitacademy.web.util.WebUtil;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		 1. 세션에서 authUser 가져오기
//		 2. authUser 에서 no 가져오기
//		 3. no를 가지고 Repository를 통해 UserVo 가져오기
//		 4. jsp로 UserVo 전달하면서 Forwarding하기

		HttpSession session = request.getSession(false);
		UserVo userVo = null;

		if (session != null) {
			userVo = (UserVo) session.getAttribute("authUser");
		}

		if (userVo == null) {
			WebUtil.forward(request, response, "WEB-INF/views/main/index.jsp");
			return;
		} else {
			
			userVo.setName(request.getParameter("name"));
			userVo.setPassword(request.getParameter("password"));

			new UserRepository().update(userVo);

			WebUtil.redirect(request, response, request.getContextPath());
		}
	}
}
