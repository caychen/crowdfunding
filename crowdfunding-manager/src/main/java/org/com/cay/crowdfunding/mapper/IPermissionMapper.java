package org.com.cay.crowdfunding.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.com.cay.crowdfunding.entity.Permission;
import org.com.cay.crowdfunding.entity.User;

import java.util.List;

/**
 * Author:           Caychen
 * Interface:        org.com.cay.crowdfunding.mapper.IPermissionMapper
 * Date:             2018/12/11
 * Version:          v1.0
 * Desc:
 */
public interface IPermissionMapper {

	@Select("select * from t_permission where pid is null")
	Permission queryRootPermission();

	@Select("select * from t_permission where pid = #{pid}")
	List<Permission> queryChildPermissionsByPid(Integer pid);

	@Select("select * from t_permission")
	List<Permission> queryAll();

	void insert(Permission permission);

	@Select("select * from t_permission where id = #{id}")
	Permission queryById(Integer id);

	void update(Permission permission);

	@Delete("delete from t_permission where id = #{id}")
	void delete(Integer id);

	@Select("select permissionId from t_role_permission where roleid = #{roleId}")
	List<Integer> queryPermissionIdsByRoleId(Integer roleId);

	List<Permission> queryUserPermission(User user);

	@Delete("delete from t_role_permission where permissionId = #{id}")
	void deleteRolesByPermissionId(Integer id);
}
