package org.com.cay.crowdfunding.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.com.cay.crowdfunding.common.JsonResult;
import org.com.cay.crowdfunding.constant.Constants;
import org.com.cay.crowdfunding.entity.Permission;
import org.com.cay.crowdfunding.entity.User;
import org.com.cay.crowdfunding.service.IPermissionService;
import org.com.cay.crowdfunding.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

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

	@Autowired
	private IPermissionService permissionService;

	@ResponseBody
	@PostMapping("/doLogin")
	public JsonResult doLogin(User user, HttpSession session) {

		User dbUser = userService.queryForLogin(user);
		if (dbUser != null) {
			session.setAttribute(Constants.LOGIN_USERNAME, dbUser);

			String path = session.getServletContext().getContextPath();
			List<Permission> permissionList = permissionService.queryUserPermission(dbUser);

			List<String> permissionPathList = Lists.newArrayList();
			Map<Integer, Permission> permissionMap = Maps.newHashMap();
			Permission root = null;
			permissionList.stream().forEach(permission -> {
				permissionMap.put(permission.getId(), permission);

				if (StringUtils.isNoneBlank(permission.getUrl())) {
					permissionPathList.add(path + permission.getUrl());
				}
			});
			session.setAttribute(Constants.PERMISSION_PATH_NAME, permissionPathList);

			for (Permission permission : permissionList) {
				if (permission.getPid() == 0) {
					root = permission;
				} else {
					//将当前节点添加到父节点上
					Permission parent = permissionMap.get(permission.getPid());
					parent.getChildren().add(permission);
				}
			}

			session.setAttribute(Constants.ROOT_PERMISSION, root);
			return JsonResult.ok();
		} else {
			return JsonResult.error("账户或者密码不正确，请重新输入！");
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(Constants.LOGIN_USERNAME);
		//session失效，把session所有的数据删除掉
		session.invalidate();
		return "redirect:/login";
	}
}
