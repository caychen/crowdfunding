package org.com.cay.crowdfunding.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.com.cay.crowdfunding.entity.User;

import java.util.List;
import java.util.Map;

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

	@Select("select * from t_user where id = #{id}")
	User queryById(Integer id);

	@Delete("delete from t_user where id = #{id}")
	void deleteById(Integer id);

	void update(User user);

	void batchDelete(@Param("ids") List<Integer> ids);

	void insertUserRoles(Map<String, Object> map);

	void deleteUserRoles(Map<String, Object> map);

	@Select("select roleid from t_user_role where userid = #{id}")
	List<Integer> queryRoleIdsByUserId(Integer id);

	void batchDeleteRolesByUserIds(Map<String, Object> map);
}
