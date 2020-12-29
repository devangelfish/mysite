package com.bitacademy.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitacademy.mysite.repository.BoardRepository;
import com.bitacademy.mysite.vo.BoardVo;
import com.bitacademy.web.mvc.Action;
import com.bitacademy.web.util.WebUtil;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardVo boardVo = new BoardVo();
		boardVo.setNo(Long.valueOf(request.getParameter("no")));
		boardVo.setTitle(request.getParameter("title"));
		boardVo.setContents(request.getParameter("content"));
		
		new BoardRepository().updateNo(boardVo);
		
		WebUtil.redirect(request, response, request.getContextPath() + "/board?a=view&no=" + request.getParameter("no") + "&name=" + request.getParameter("name"));
	}
} 
