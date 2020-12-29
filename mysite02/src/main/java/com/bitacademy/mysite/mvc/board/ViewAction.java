package com.bitacademy.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitacademy.mysite.repository.BoardRepository;
import com.bitacademy.mysite.vo.BoardVo;
import com.bitacademy.web.mvc.Action;
import com.bitacademy.web.util.WebUtil;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		new BoardRepository().updateHitbyNo(Long.parseLong(request.getParameter("no")));
		BoardVo boardVo = new BoardRepository().findByNo(Long.parseLong(request.getParameter("no")));
		request.setAttribute("boardVo", boardVo);
		request.setAttribute("newLine", "\n");
		WebUtil.forward(request, response, "/WEB-INF/views/board/view.jsp");
	}
}
