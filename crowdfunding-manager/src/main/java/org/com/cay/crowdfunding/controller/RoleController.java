package org.com.cay.crowdfunding.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.com.cay.crowdfunding.common.JsonResult;
import org.com.cay.crowdfunding.entity.Role;
import org.com.cay.crowdfunding.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
		}catch (Exception e){
			log.error("角色列表查询失败，原因：", e);
			return JsonResult.error(e.getMessage());
		}
	}

	@GetMapping({"/", "/index"})
	public String index(){
		return "role/index";
	}

	@GetMapping("/add")
	public String toAddRole(){
		return "role/add";
	}

	@PostMapping("/save")
	@ResponseBody
	public JsonResult save(Role role){
		log.info("当前角色对象: [{}]", role);
		try{
			if(StringUtils.isEmpty(role.getId())){
				roleService.insert(role);
			}else{
				roleService.update(role);
			}
			log.info("角色保存成功");
			return JsonResult.ok();
		}catch (Exception e){
			log.error("角色保存失败，原因：", e);
			return JsonResult.error("角色保存失败: " + e.getMessage());
		}

	}

	@GetMapping("/edit")
	public String edit(Integer id, Model model){
		Role role = roleService.queryById(id);
		model.addAttribute("role", role);
		log.info("当前角色信息:[{}]", role);
		return "role/edit";
	}

	@ResponseBody
	@DeleteMapping("/delete/{id}")
	public JsonResult delete(@PathVariable Integer id){
		log.info("角色删除id: [{}]", id);
		try {
			roleService.delete(id);
			log.info("角色删除成功");
			return JsonResult.ok();
		}catch (Exception e){
			log.error("角色删除失败，原因：", e);
			return JsonResult.error("角色删除失败: " + e.getMessage());
		}

	}

	@ResponseBody
	@DeleteMapping("/batchdelete")
	public JsonResult delete(String ids){
		log.info("角色删除ids: [{}]", ids);
		try {
			List<String> idList = Lists.newArrayList(ids.split(","));
			roleService.batchDelete(idList);
			log.info("角色批量删除成功");
			return JsonResult.ok();
		}catch (Exception e){
			log.error("角色批量删除失败，原因：", e);
			return JsonResult.error("角色批量删除失败: " + e.getMessage());
		}

	}
}
