package com.bitacademy.mysite.mvc.main;

import com.bitacademy.web.mvc.Action;
import com.bitacademy.web.mvc.ActionFactory;

public class MainActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		return new MainAction();
	}
	
}
