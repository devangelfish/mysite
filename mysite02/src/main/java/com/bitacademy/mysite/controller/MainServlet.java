package com.bitacademy.mysite.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitacademy.web.mvc.WebUtil;

public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MainServlet() {
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		WebUtil.forward(request, response, "/WEB-INF/views/main/index.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
