package org.com.cay.crowdfunding.service.impl;

import com.github.pagehelper.PageHelper;
import org.com.cay.crowdfunding.entity.Role;
import org.com.cay.crowdfunding.mapper.IRoleMapper;
import org.com.cay.crowdfunding.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author:       Caychen
 * Class:        org.com.cay.crowdfunding.service.impl.RoleServiceImpl
 * Date:         2018/12/11
 * Version:      v1.0
 * Desc:
 */
@Service
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private IRoleMapper roleMapper;

	@Override
	public List<Role> queryPageInfo(String queryText, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return this.queryBy(queryText);
	}

	@Override
	public List<Role> queryBy(String queryText) {
		return roleMapper.queryBy(queryText);
	}

	@Override
	public List<Role> queryAll() {
		return this.queryBy(null);
	}

	@Override
	@Transactional
	public void insert(Role role) {
		role.setCreateDate(new Date()).setUpdateDate(new Date());
		roleMapper.insert(role);
	}

	@Override
	@Transactional
	public void update(Role role) {
		role.setUpdateDate(new Date());
		roleMapper.update(role);
	}

	@Override
	public Role queryById(Integer id) {
		return roleMapper.queryById(id);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		roleMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void batchDelete(List<String> idList) {
		List<Integer> ids = idList.stream().map(id -> Integer.parseInt(id)).collect(Collectors.toList());
		roleMapper.batchDelete(ids);
	}
}
