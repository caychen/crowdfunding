package org.com.cay.crowdfunding.service;

import org.com.cay.crowdfunding.entity.Permission;

import java.util.List;

/**
 * Author:           Caychen
 * Interface:        org.com.cay.crowdfunding.service.IPermissionService
 * Date:             2018/12/11
 * Version:          v1.0
 * Desc:
 */
public interface IPermissionService {

	Permission queryRootPermission();

	List<Permission> queryChildPermissionsByPid(Integer pid);

	List<Permission> queryAll();

	void insert(Permission permission);

	Permission queryById(Integer id);

	void update(Permission permission);

	void delete(Integer id);
}
