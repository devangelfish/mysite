package com.bitacademy.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitacademy.mysite.repository.BoardRepository;
import com.bitacademy.mysite.repository.UserRepository;
import com.bitacademy.mysite.vo.BoardVo;
import com.bitacademy.web.mvc.Action;
import com.bitacademy.web.util.WebUtil;

public class ReplyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardVo parentBoardVo = new BoardVo();
		BoardVo boardVo = new BoardVo();
		
		boardVo.setUserNo(Long.valueOf(request.getParameter("userNo")));
		boardVo.setUserName(new UserRepository().findByNo(Long.valueOf(request.getParameter("userNo"))).getName());
		boardVo.setTitle(request.getParameter("title"));
		boardVo.setContents(request.getParameter("content"));
		
		parentBoardVo.setGroupNo(Long.valueOf(request.getParameter("groupNo")));
		parentBoardVo.setOrderNo(Integer.valueOf(request.getParameter("orderNo")));
		parentBoardVo.setDepth(Integer.valueOf(request.getParameter("depth")));
		
		new BoardRepository().reply(boardVo, parentBoardVo);
		
		WebUtil.redirect(request, response, request.getContextPath() + "/board?page=1");
	}

}
