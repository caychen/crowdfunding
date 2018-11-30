package org.com.cay.crowdfunding.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.com.cay.crowdfunding.entity.User;

import java.util.List;

/**
 * Author:           Caychen
 * Interface:        org.com.cay.crowdfunding.dao.IUserDao
 * Date:             2018/11/29
 * Version:          v1.0
 * Desc:
 */
public interface IUserDao {

	@Select("select * from t_user")
	List<User> queryAll();

	@Insert("insert into t_user values(null, #{username})")
	@Options(useGeneratedKeys = true, keyColumn = "id")
	void insert(User user);

	@Select("select * from t_user where username=#{username} and password=#{password}")
	User queryForLogin(User user);
}
