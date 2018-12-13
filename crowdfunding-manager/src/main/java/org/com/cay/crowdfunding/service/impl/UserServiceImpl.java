package org.com.cay.crowdfunding.service.impl;

import com.github.pagehelper.PageHelper;
import org.com.cay.crowdfunding.constant.Constants;
import org.com.cay.crowdfunding.entity.User;
import org.com.cay.crowdfunding.mapper.IUserMapper;
import org.com.cay.crowdfunding.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Author:       Caychen
 * Class:        org.com.cay.crowdfunding.service.impl.UserServiceImpl
 * Date:         2018/11/29
 * Version:      v1.0
 * Desc:
 */
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserMapper userMapper;

	@Override
	public List<User> queryAll() {
		return userMapper.queryBy(null);
	}

	@Override
	public List<User> queryBy(String queryText) {
		return userMapper.queryBy(queryText);
	}

	@Override
	public User queryForLogin(User user) {
		return userMapper.queryForLogin(user);
	}

	@Override
	public List<User> queryPageInfo(String queryText, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return this.queryBy(queryText);
	}

	@Override
	@Transactional
	public void insert(User user) {
		user.setCreateDate(new Date()).setUpdateDate(new Date());
		user.setPassword(Constants.DEFAULT_PASSWORD);
		userMapper.insert(user);
	}

	@Override
	public User queryById(Integer id) {
		return userMapper.queryById(id);
	}

	@Transactional
	@Override
	public void update(User user) {
		user.setUpdateDate(new Date());
		userMapper.update(user);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		userMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void batchDelete(List<String> idList) {
		List<Integer> ids = idList.stream().map(id -> Integer.parseInt(id)).collect(Collectors.toList());
		userMapper.batchDelete(ids);
	}

	@Override
	public void insertUserRoles(Map<String, Object> map) {
		userMapper.insertUserRoles(map);
	}

	@Override
	public void deleteUserRoles(Map<String, Object> map) {
		userMapper.deleteUserRoles(map);
	}

	@Override
	public List<Integer> queryRoleIdsByUserId(Integer id) {
		return userMapper.queryRoleIdsByUserId(id);
	}
}
