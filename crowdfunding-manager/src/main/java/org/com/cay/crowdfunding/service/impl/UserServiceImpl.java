package org.com.cay.crowdfunding.service.impl;

import com.github.pagehelper.PageHelper;
import org.com.cay.crowdfunding.entity.User;
import org.com.cay.crowdfunding.mapper.IUserMapper;
import org.com.cay.crowdfunding.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
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
		user.setCreateDate(new Date());
		if(StringUtils.isEmpty(user.getNickname())) {
			user.setNickname(user.getUsername());
		}
		userMapper.insert(user);
	}
}
