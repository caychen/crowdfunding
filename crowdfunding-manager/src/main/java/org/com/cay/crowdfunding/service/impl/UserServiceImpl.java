package org.com.cay.crowdfunding.service.impl;

import com.github.pagehelper.PageHelper;
import org.com.cay.crowdfunding.mapper.IUserMapper;
import org.com.cay.crowdfunding.entity.User;
import org.com.cay.crowdfunding.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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

	@Transactional
	@Override
	public void save(User user) {
		userMapper.insert(user);
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
}
