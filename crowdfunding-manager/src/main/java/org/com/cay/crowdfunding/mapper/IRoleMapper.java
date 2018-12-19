package org.com.cay.crowdfunding.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.com.cay.crowdfunding.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * Author:           Caychen
 * Interface:        org.com.cay.crowdfunding.mapper.IRoleMapper
 * Date:             2018/12/11
 * Version:          v1.0
 * Desc:
 */
public interface IRoleMapper {

	void insert(Role Role);

	List<Role> queryBy(@Param("queryText") String queryText);

	@Select("select * from t_role where id = #{id}")
	Role queryById(Integer id);

	@Delete("delete from t_role where id = #{id}")
	void deleteById(Integer id);

	void update(Role Role);

	void batchDelete(@Param("ids") List<Integer> ids);

	void doAssign(Map<String, Object> map);
}
