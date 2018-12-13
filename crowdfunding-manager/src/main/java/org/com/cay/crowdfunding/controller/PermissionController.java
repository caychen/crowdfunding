package org.com.cay.crowdfunding.controller;

import lombok.extern.slf4j.Slf4j;
import org.com.cay.crowdfunding.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author:       Caychen
 * Class:        org.com.cay.crowdfunding.controller.PermissionController
 * Date:         2018/12/11
 * Version:      v1.0
 * Desc:
 */
@Controller
@RequestMapping("/permission")
@Slf4j
public class PermissionController {

	@Autowired
	private IPermissionService permissionService;

}
