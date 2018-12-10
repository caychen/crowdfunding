package org.com.cay.crowdfunding.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.com.cay.crowdfunding.common.JsonResult;
import org.com.cay.crowdfunding.entity.User;
import org.com.cay.crowdfunding.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author:       Caychen
 * Class:        org.com.cay.crowdfunding.controller.UserController
 * Date:         2018/12/3
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
	@PostMapping({"/query"})
	public JsonResult userQuery(
			@RequestParam(required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize,
			String queryText) {

		//分页查询
		List<User> userList = userService.queryPageInfo(queryText, pageNum, pageSize);
		PageInfo<User> userPageInfo = new PageInfo<>(userList);

		return JsonResult.ok(userPageInfo);
	}

	@GetMapping({"/", "/index"})
	public String index(){
		return "user/index";
	}

	@GetMapping("/add")
	public String toAddUser(){
		return "user/add";
	}

	@PostMapping("/save")
	@ResponseBody
	public JsonResult save(User user){
		try{
			if(StringUtils.isEmpty(user.getId())){
				userService.insert(user);
			}else{
				userService.update(user);
			}
			return JsonResult.ok();
		}catch (Exception e){
			return JsonResult.error("用户保存失败: " + e.getMessage());
		}

	}

	@GetMapping("/edit")
	public String edit(Integer id, Model model){
		User user = userService.queryById(id);
		model.addAttribute("user", user);
		return "user/edit";
	}

	@ResponseBody
	@DeleteMapping("/delete/{id}")
	public JsonResult delete(@PathVariable Integer id){
		try {
			log.info("删除id: {}", id);
			userService.delete(id);
			return JsonResult.ok();
		}catch (Exception e){
			return JsonResult.error("删除失败: " + e.getMessage());
		}

	}

	@ResponseBody
	@DeleteMapping("/batchdelete")
	public JsonResult delete(String ids){
		try {
			log.info("删除ids: {}", ids);
//			userService.delete(id);
			List<String> idList = Lists.newArrayList(ids.split(","));
			userService.batchDelete(idList);

			return JsonResult.ok();
		}catch (Exception e){
			return JsonResult.error("删除失败: " + e.getMessage());
		}

	}
}
