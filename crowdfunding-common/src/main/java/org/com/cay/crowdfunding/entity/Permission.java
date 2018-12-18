package org.com.cay.crowdfunding.entity;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Author:       Caychen
 * Class:        org.com.cay.crowdfunding.entity.Permission
 * Date:         2018/12/11
 * Version:      v1.0
 * Desc:
 */
@Data
@Accessors(chain = true)
public class Permission {

	private Integer id;

	private String name;

	private String url;

	private Integer pid;

	private boolean open = true;

	private boolean checked = false;

	private String icon;

	private List<Permission> children = Lists.newArrayList();
}
