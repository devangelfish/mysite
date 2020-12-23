package com.bitacademy.mysite.mvc.guestbook;

import com.bitacademy.mysite.mvc.main.MainAction;
import com.bitacademy.mysite.mvc.user.JoinFormAction;
import com.bitacademy.web.mvc.Action;
import com.bitacademy.web.mvc.ActionFactory;

public class GuestActionFactory extends ActionFactory {
	
	@Override
	public Action getAction(String actionName) {
		Action action = null;

		if("deleteform".equals(actionName)) {
			action = new DeleteFormAction();
		} else if("delete".equals(actionName)) {
			action = new DeleteAction();
		} else if("insert".equals(actionName)) {
			action = new InsertAction();
		} else {
			action = new ListAction();
		}

		return action;
	}
}
