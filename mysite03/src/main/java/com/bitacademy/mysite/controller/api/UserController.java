package com.bitacademy.mysite.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitacademy.mysite.dto.JsonResult;
import com.bitacademy.mysite.service.UserService;

@Controller("userApiController")
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@ResponseBody
	@RequestMapping("/existemail")
	public JsonResult checkEmail(@RequestParam(value="email", required=false, defaultValue="") String email) {
		
		boolean result = userService.existsEmail(email);
		return JsonResult.success(result);
	}
}
