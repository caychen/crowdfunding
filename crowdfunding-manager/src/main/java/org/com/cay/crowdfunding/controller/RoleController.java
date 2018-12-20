package org.com.cay.crowdfunding.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.com.cay.crowdfunding.common.JsonResult;
import org.com.cay.crowdfunding.entity.Permission;
import org.com.cay.crowdfunding.entity.Role;
import org.com.cay.crowdfunding.service.IPermissionService;
import org.com.cay.crowdfunding.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author:       Caychen
 * Class:        org.com.cay.crowdfunding.controller.RoleController
 * Date:         2018/12/11
 * Version:      v1.0
 * Desc:
 */
@Controller
@RequestMapping("/role")
@Slf4j
public class RoleController {

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IPermissionService permissionService;

	@ResponseBody
	@PostMapping({"/query"})
	public JsonResult roleQuery(
			@RequestParam(required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize,
			String queryText) {
		log.info("分页查询角色列表，当前页:[{}]", pageNum);
		try {
			//分页查询
			List<Role> roleList = roleService.queryPageInfo(queryText, pageNum, pageSize);
			PageInfo<Role> rolePageInfo = new PageInfo<>(roleList);
			log.info("查询成功,本页[{}]条", rolePageInfo.getList().size());
			return JsonResult.ok(rolePageInfo);
		} catch (Exception e) {
			log.error("角色列表查询失败，原因：", e);
			return JsonResult.error(e.getMessage());
		}
	}

	@GetMapping({"/", "/index"})
	public String index() {
		return "role/index";
	}

	@GetMapping("/add")
	public String toAddRole() {
		return "role/add";
	}

	@PostMapping("/save")
	@ResponseBody
	public JsonResult save(Role role) {
		log.info("当前角色对象: [{}]", role);
		try {
			if (role.getId() == null) {
				roleService.insert(role);
				log.info("角色保存成功");
			} else {
				roleService.update(role);
				log.info("角色更新成功");
			}
			return JsonResult.ok();
		} catch (Exception e) {
			log.error("角色保存失败，原因：", e);
			return JsonResult.error("角色保存失败: " + e.getMessage());
		}

	}

	@GetMapping("/edit")
	public String edit(Integer id, Model model) {
		Role role = roleService.queryById(id);
		model.addAttribute("role", role);
		log.info("当前角色信息:[{}]", role);
		return "role/edit";
	}

	@ResponseBody
	@DeleteMapping("/delete/{id}")
	public JsonResult delete(@PathVariable Integer id) {
		log.info("角色删除id: [{}]", id);
		try {
			roleService.delete(id);
			log.info("角色删除成功");
			return JsonResult.ok();
		} catch (Exception e) {
			log.error("角色删除失败，原因：", e);
			return JsonResult.error("角色删除失败: " + e.getMessage());
		}

	}

	@ResponseBody
	@DeleteMapping("/batchdelete")
	public JsonResult delete(String ids) {
		log.info("角色删除ids: [{}]", ids);
		try {
			List<String> idList = Lists.newArrayList(ids.split(","));
			roleService.batchDelete(idList);
			log.info("角色批量删除成功");
			return JsonResult.ok();
		} catch (Exception e) {
			log.error("角色批量删除失败，原因：", e);
			return JsonResult.error("角色批量删除失败: " + e.getMessage());
		}

	}

	@GetMapping("/assign")
	public String assign(Integer id) {
		return "role/assign";
	}

	@ResponseBody
	@PostMapping("/doassign")
	public JsonResult doAssign(Integer roleId, String permissionIds) {
		try {
			log.info("许可分配,roleId:[{}], permissionIds:[{}]", roleId, permissionIds);
			Map<String, Object> map = Maps.newHashMap();
			map.put("roleId", roleId);
			List<String> strList = Lists.newArrayList(permissionIds.split(","));
			map.put("permissionIds", strList.stream().map(s -> Integer.valueOf(s)).collect(Collectors.toList()));
			roleService.doAssign(map);
			log.info("许可分配成功!");
			return JsonResult.ok();
		} catch (Exception e) {
			log.error("许可分配有误：", e);
			return JsonResult.error("许可分配有误：" + e.getMessage());
		}
	}

	@PostMapping("/loadpermission")
	@ResponseBody
	public Object loadPermission(Integer roleId) {
		List<Integer> rolePermissionIds = permissionService.queryPermissionIdsByRoleId(roleId);

		//先查询所有节点，然后通过pid来将当前节点添加到父节点的下级节点集合中
		List<Permission> permissionList = Lists.newArrayList();
		List<Permission> permissions = permissionService.queryAll();
		Map<Integer, Permission> permissionMap = Maps.newHashMap();

		//拼装map
		permissions.stream().forEach(permission -> {
			if (rolePermissionIds.contains(permission.getId())) {
				permission.setChecked(true);
			}
			permissionMap.put(permission.getId(), permission);

		});

		permissions.stream().forEach(permission -> {
			if (permission.getPid() == 0) {
				//顶级节点
				permissionList.add(permission);
			} else {
				//将当前节点添加到父节点上
				Permission parent = permissionMap.get(permission.getPid());
				parent.getChildren().add(permission);
			}
		});
		return permissionList;
	}


}
