package org.com.cay.crowdfunding.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.com.cay.crowdfunding.common.JsonResult;
import org.com.cay.crowdfunding.entity.Permission;
import org.com.cay.crowdfunding.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Author:       Caychen
 * Class:        org.com.cay.crowdfunding.controller.PermissionController
 * Date:         2018/12/11
 * Version:      v1.0
 * Desc:
 */
@Controller
@RequestMapping("/permission")
@Slf4j
public class PermissionController {

	@Autowired
	private IPermissionService permissionService;

	@GetMapping({"/", "/index"})
	public String index(){
		return "permission/index";
	}

	@ResponseBody
	@PostMapping("/load")
	public Object load(){
//		//顶级节点
//		Permission root = new Permission();
//		root.setName("顶级节点");
//
//		Permission child = new Permission();
//		child.setName("子节点");
//
//		root.getChildren().add(child);
//		permissionList.add(root);

		//读取树形结构数据
//		Permission root = permissionService.queryRootPermission();
//		permissionList.add(root);
//
//		List<Permission> children = permissionService.queryChildPermissionsByPid(root.getId());
//		root.setChildren(children);

		//递归查询
		/*Permission root = new Permission();
		root.setId(0);
		queryChildPermissions(root);

		return root.getChildren();*/

		//先查询所有节点，然后通过pid来将当前节点添加到父节点的下级节点集合中
		List<Permission> permissionList = Lists.newArrayList();
		List<Permission> permissions = permissionService.queryAll();
		Map<Integer, Permission> permissionMap = Maps.newHashMap();

		//拼装map
		permissions.stream().forEach(permission -> permissionMap.put(permission.getId(), permission));

		permissions.stream().forEach(permission -> {
			if(permission.getPid() == 0){
				//顶级节点
				permissionList.add(permission);
			}else{
				//将当前节点添加到父节点上
				Permission parent = permissionMap.get(permission.getPid());
				parent.getChildren().add(permission);
			}
		});

		return  permissionList;
	}

	//递归算法
	private void queryChildPermissions(Permission parent){
		List<Permission> childrenPermissions = permissionService.queryChildPermissionsByPid(parent.getId());
		parent.setChildren(childrenPermissions);

		childrenPermissions.stream().forEach(childrenPermission -> queryChildPermissions(childrenPermission));
	}

	@GetMapping("/add")
	public String add(){
		return "permission/add";
	}

	@GetMapping("/edit")
	public String edit(Integer id, Model model){
		Permission permission = permissionService.queryById(id);
		model.addAttribute("permission", permission);
		log.info("当前用户信息:[{}]", permission);
		return "permission/edit";
	}

	@ResponseBody
	@PostMapping("/save")
	public JsonResult save(Permission permission){
		try{
			log.info("许可信息保存/更新：[{}]", permission);
			if(permission.getId() == null) {
				permissionService.insert(permission);
				log.info("许可信息保存成功！");
			}else{
				permissionService.update(permission);
				log.info("许可信息更新成功！");
			}
			return JsonResult.ok();
		}catch (Exception e){
			e.printStackTrace();
			log.error("许可信息保存失败：", e);
			return JsonResult.error(e.getMessage());
		}
	}

	@ResponseBody
	@DeleteMapping("/delete/{id}")
	public JsonResult delete(@PathVariable Integer id){
		log.info("删除id: [{}]", id);
		try {
			permissionService.delete(id);
			log.info("许可信息删除成功");
			return JsonResult.ok();
		}catch (Exception e){
			log.error("许可信息删除失败，原因：", e);
			return JsonResult.error("许可信息删除失败: " + e.getMessage());
		}

	}
}
