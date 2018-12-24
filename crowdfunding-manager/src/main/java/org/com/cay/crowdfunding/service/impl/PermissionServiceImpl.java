package org.com.cay.crowdfunding.service.impl;

import org.com.cay.crowdfunding.entity.Permission;
import org.com.cay.crowdfunding.entity.User;
import org.com.cay.crowdfunding.mapper.IPermissionMapper;
import org.com.cay.crowdfunding.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author:           Caychen
 * Interface:        org.com.cay.crowdfunding.service.impl.PermissionServiceService
 * Date:             2018/12/11
 * Version:          v1.0
 * Desc:
 */
@Service
public class PermissionServiceImpl implements IPermissionService {

	@Autowired
	private IPermissionMapper permissionMapper;

	@Override
	public Permission queryRootPermission() {
		return permissionMapper.queryRootPermission();
	}

	@Override
	public List<Permission> queryChildPermissionsByPid(Integer pid) {
		return permissionMapper.queryChildPermissionsByPid(pid);
	}

	@Override
	public List<Permission> queryAll() {
		return permissionMapper.queryAll();
	}

	@Override
	@Transactional
	public void insert(Permission permission) {
		permissionMapper.insert(permission);
	}

	@Override
	public Permission queryById(Integer id) {
		return permissionMapper.queryById(id);
	}

	@Override
	@Transactional
	public void update(Permission permission) {
		permissionMapper.update(permission);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		permissionMapper.delete(id);

		//关联数据的删除
		permissionMapper.deleteRolesByPermissionId(id);
	}

	@Override
	public List<Integer> queryPermissionIdsByRoleId(Integer roleId) {
		return permissionMapper.queryPermissionIdsByRoleId(roleId);
	}

	@Override
	public List<Permission> queryUserPermission(User dbUser) {
		return permissionMapper.queryUserPermission(dbUser);
	}
}
