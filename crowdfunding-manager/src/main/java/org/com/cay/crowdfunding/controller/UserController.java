package org.com.cay.crowdfunding.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.com.cay.crowdfunding.common.JsonResult;
import org.com.cay.crowdfunding.entity.Role;
import org.com.cay.crowdfunding.entity.User;
import org.com.cay.crowdfunding.service.IRoleService;
import org.com.cay.crowdfunding.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

	@Autowired
	private IRoleService roleService;

	@ResponseBody
	@PostMapping({"/query"})
	public JsonResult userQuery(
			@RequestParam(required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize,
			String queryText) {

		log.info("分页查询用户列表，当前页:[{}]", pageNum);
		try {
			//分页查询
			List<User> userList = userService.queryPageInfo(queryText, pageNum, pageSize);
			PageInfo<User> userPageInfo = new PageInfo<>(userList);

			log.info("查询成功,本页[{}]条", userPageInfo.getList().size());
			return JsonResult.ok(userPageInfo);
		}catch (Exception e){
			log.error("用户列表查询失败，原因：", e);
			return JsonResult.error(e.getMessage());
		}
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
		log.info("当前用户对象: [{}]", user);
		try{
			if(user.getId() == null){
				userService.insert(user);
				log.info("用户保存成功");
			}else{
				userService.update(user);
				log.info("用户更新成功");
			}

			return JsonResult.ok();
		}catch (Exception e){
			log.error("用户保存失败，原因：", e);
			return JsonResult.error("用户保存失败: " + e.getMessage());
		}

	}

	@GetMapping("/edit")
	public String edit(Integer id, Model model){
		User user = userService.queryById(id);
		model.addAttribute("user", user);
		log.info("当前用户信息:[{}]", user);
		return "user/edit";
	}

	@ResponseBody
	@DeleteMapping("/delete/{id}")
	public JsonResult delete(@PathVariable Integer id){
		log.info("删除id: [{}]", id);
		try {
			userService.delete(id);
			log.info("用户删除成功");
			return JsonResult.ok();
		}catch (Exception e){
			log.error("用户删除失败，原因：", e);
			return JsonResult.error("用户删除失败: " + e.getMessage());
		}

	}

	@ResponseBody
	@DeleteMapping("/batchdelete")
	public JsonResult delete(String ids){
		log.info("删除ids: [{}]", ids);
		try {
//			userService.delete(id);
			List<String> idList = Lists.newArrayList(ids.split(","));
			userService.batchDelete(idList);
			log.info("用户批量删除成功");
			return JsonResult.ok();
		}catch (Exception e){
			log.error("用户批量删除失败，原因：", e);
			return JsonResult.error("用户批量删除失败: " + e.getMessage());
		}

	}

	@GetMapping("/assign")
	public String assign(Integer id, Model model){
		//用户信息
		User user = userService.queryById(id);
		model.addAttribute("user", user);

		//所有角色
		List<Role> roleList = roleService.queryAll();

		//根据用户id获取当前拥有的角色id
		List<Integer> hasRoleIdList = userService.queryRoleIdsByUserId(id);

		List<Role> hasRoleList = Lists.newArrayList();
		List<Role> noRoleList = Lists.newArrayList();
		for (Role role : roleList){
			if(hasRoleIdList.contains(role.getId())){
				hasRoleList.add(role);
			}else{
				noRoleList.add(role);
			}
		}

		log.info("查询到用户信息:[{}], 角色信息:[{}]", user, hasRoleList.stream().map(role -> role.getName()).collect(Collectors.toList()));

		model.addAttribute("hasRoleList", hasRoleList);
		model.addAttribute("noRoleList", noRoleList);
		return "user/assign";
	}

	@ResponseBody
	@PostMapping("/submit")
	public JsonResult submit(Integer userId, Integer[] unassignroleids){
		log.info("给用户id为[{}]分配角色[{}]", userId, Arrays.asList(unassignroleids));
		try{
			Map<String, Object> map = Maps.newHashMap();
			map.put("userId", userId);
			map.put("roleIds", unassignroleids);
			userService.insertUserRoles(map);

			log.info("分配角色成功");
			return JsonResult.ok();
		}catch (Exception e){
			log.info("分配角色失败，原因：", e);
			return JsonResult.error();
		}

	}

	@ResponseBody
	@PostMapping("/cancel")
	public JsonResult cancel(Integer userId, Integer[] assignroleids){
		log.info("给用户id为[{}]取消角色[{}]", userId, Arrays.asList(assignroleids));
		try{
			Map<String, Object> map = Maps.newHashMap();
			map.put("userId", userId);
			map.put("roleIds", Lists.newArrayList(assignroleids));
			userService.deleteUserRoles(map);

			log.info("取消角色成功");
			return JsonResult.ok();
		}catch (Exception e){
			log.info("取消角色失败，原因：", e);
			return JsonResult.error();
		}

	}
}
