package com.bitacademy.mysite.mvc.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitacademy.mysite.repository.GuestbookRepository;
import com.bitacademy.mysite.vo.GuestbookVo;
import com.bitacademy.web.mvc.Action;
import com.bitacademy.web.util.WebUtil;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long no = Long.valueOf(request.getParameter("no"));
		String password = request.getParameter("password");
		
		GuestbookVo vo = new GuestbookVo();
		
		vo.setNo(no);
		vo.setPassword(password);
		
		new GuestbookRepository().delete(vo);
		
		WebUtil.redirect(request, response, request.getContextPath() + "/guestbook");
	}
}
