package org.com.cay.crowdfunding.controller;

import lombok.extern.slf4j.Slf4j;
import org.com.cay.crowdfunding.common.JsonResult;
import org.com.cay.crowdfunding.entity.User;
import org.com.cay.crowdfunding.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Author:       Caychen
 * Class:        org.com.cay.crowdfunding.controller.UserController
 * Date:         2018/11/29
 * Version:      v1.0
 * Desc:
 */
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

	@Autowired
	private IUserService userService;

	@ResponseBody
	@GetMapping("/all")
	public List<User> queryAll(){
		return userService.queryAll();
	}

	@ResponseBody
	@PostMapping("/save")
	public JsonResult save(User user){
		try {
			log.info("保存用户信息：{}", user.getUsername());
			userService.save(user);
			return JsonResult.ok(user);
		}catch (Exception e){
			log.error("发生异常", e);
			return JsonResult.error(e.getMessage());
		}
	}

	@ResponseBody
	@PostMapping("/save2")
	public JsonResult save2(User user){
		try {
			log.info("保存用户信息：{}", user.getUsername());
			userService.saveWithException(user);
			return JsonResult.ok(user);
		}catch (Exception e){
			log.error("发生异常", e);
			return JsonResult.error(e.getMessage());
		}
	}
}
