package org.com.cay.crowdfunding.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.com.cay.crowdfunding.entity.User;

import java.util.List;

/**
 * Author:           Caychen
 * Interface:        org.com.cay.crowdfunding.mapper.IUserMapper
 * Date:             2018/11/29
 * Version:          v1.0
 * Desc:
 */
public interface IUserMapper {

	void insert(User user);

	@Select("select * from t_user where username=#{username} and password=#{password}")
	User queryForLogin(User user);

	List<User> queryBy(@Param("queryText") String queryText);
}
