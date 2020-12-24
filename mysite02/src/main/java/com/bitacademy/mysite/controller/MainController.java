package com.bitacademy.mysite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitacademy.mysite.mvc.main.MainActionFactory;
import com.bitacademy.web.mvc.Action;
import com.bitacademy.web.mvc.ActionFactory;

public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		String actionName = request.getParameter("a");

		ActionFactory actionFactory = new MainActionFactory();
		Action action = actionFactory.getAction(actionName);
		action.execute(request, response);
	}

	@Override
	public void destroy() {
		System.out.println("destroy() called");
		super.destroy();
	}

	@Override
	public void init() throws ServletException {
		String configPath = this.getServletConfig().getInitParameter("config");
		System.out.println("init() called:" + configPath);
		super.init();
	}

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		super.service(req, res);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
