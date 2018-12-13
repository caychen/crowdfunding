package org.com.cay.crowdfunding.service;

import org.com.cay.crowdfunding.entity.Role;

import java.util.List;

/**
 * Author:           Caychen
 * Interface:        org.com.cay.crowdfunding.service.IRoleService
 * Date:             2018/12/11
 * Version:          v1.0
 * Desc:
 */
public interface IRoleService {

	List<Role> queryPageInfo(String queryText, Integer pageNum, Integer pageSize);

	void insert(Role role);

	void update(Role role);

	Role queryById(Integer id);

	void delete(Integer id);

	void batchDelete(List<String> idList);

	List<Role> queryBy(String queryText);

	List<Role> queryAll();
}
