package org.com.cay.crowdfunding.controller;

import org.com.cay.crowdfunding.Constants;
import org.com.cay.crowdfunding.common.JsonResult;
import org.com.cay.crowdfunding.entity.User;
import org.com.cay.crowdfunding.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Author:       Caychen
 * Class:        org.com.cay.crowdfunding.controller.LoginController
 * Date:         2018/11/30
 * Version:      v1.0
 * Desc:
 */
@Controller
public class LoginController {

	@Autowired
	private IUserService userService;

	@ResponseBody
	@PostMapping("/doLogin")
	public JsonResult doLogin(User user, HttpSession session){

		User dbUser = userService.queryForLogin(user);
		if(dbUser != null){
			session.setAttribute(Constants.LOGIN_USERNAME, dbUser);
			return JsonResult.ok();
		}else{
			return JsonResult.error("账户或者密码不正确，请重新输入！");
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session){
		session.removeAttribute(Constants.LOGIN_USERNAME);
		//session失效，把session所有的数据删除掉
		session.invalidate();
		return "redirect:/login";
	}
}
