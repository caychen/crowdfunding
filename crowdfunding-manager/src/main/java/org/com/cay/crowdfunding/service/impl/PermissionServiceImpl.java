package org.com.cay.crowdfunding.service.impl;

import org.com.cay.crowdfunding.mapper.IPermissionMapper;
import org.com.cay.crowdfunding.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author:           Caychen
 * Interface:        org.com.cay.crowdfunding.service.impl.PermissionServiceService
 * Date:             2018/12/11
 * Version:          v1.0
 * Desc:
 */
@Service
public class PermissionServiceImpl implements IPermissionService {

	@Autowired
	private IPermissionMapper permissionMapper;
}
