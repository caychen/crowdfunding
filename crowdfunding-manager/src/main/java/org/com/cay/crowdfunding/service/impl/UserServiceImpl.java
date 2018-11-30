package org.com.cay.crowdfunding.service.impl;

import org.com.cay.crowdfunding.dao.IUserDao;
import org.com.cay.crowdfunding.entity.User;
import org.com.cay.crowdfunding.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
	private IUserDao userDao;

	@Override
	public List<User> queryAll() {
		return userDao.queryAll();
	}

	@Transactional
	@Override
	public void save(User user) {
		userDao.insert(user);
	}

	@Override
	@Transactional
	public void saveWithException(User user) {
		userDao.insert(user);
		throw new RuntimeException("抛出异常...");
	}

	@Override
	public User queryForLogin(User user) {
		return userDao.queryForLogin(user);
	}
}
