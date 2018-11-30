package org.com.cay.crowdfunding.service;

import org.com.cay.crowdfunding.entity.User;

import java.util.List;

/**
 * Author:           Caychen
 * Interface:        org.com.cay.crowdfunding.service.IUserService
 * Date:             2018/11/29
 * Version:          v1.0
 * Desc:
 */
public interface IUserService {

	List<User> queryAll();

	void save(User user);

	void saveWithException(User user);

	User queryForLogin(User user);
}
