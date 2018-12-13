package org.com.cay.crowdfunding.service;

import org.com.cay.crowdfunding.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Author:           Caychen
 * Interface:        org.com.cay.crowdfunding.service.IUserService
 * Date:             2018/11/29
 * Version:          v1.0
 * Desc:
 */
public interface IUserService {

	List<User> queryAll();

	List<User> queryBy(String queryText);

	User queryForLogin(User user);

	List<User> queryPageInfo(String queryText, Integer pageNum, Integer pageSize);

	void insert(User user);

	User queryById(Integer id);

	void update(User user);

	void delete(Integer id);

	void batchDelete(List<String> idList);

	void insertUserRoles(Map<String, Object> map);

	void deleteUserRoles(Map<String, Object> map);

	List<Integer> queryRoleIdsByUserId(Integer id);
}
