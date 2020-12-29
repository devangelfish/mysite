package com.bitacademy.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bitacademy.mysite.vo.UserVo;
import com.bitacademy.web.mvc.Action;
import com.bitacademy.web.util.WebUtil;

public class WriteFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		UserVo userVo = null;

		userVo = (UserVo) session.getAttribute("authUser");

		if (userVo == null) {
			WebUtil.forward(request, response, "WEB-INF/views/user/loginform.jsp");
			return;
		} else {
			WebUtil.forward(request, response, "/WEB-INF/views/board/write.jsp");
		}
	}
}
