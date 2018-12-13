package org.com.cay.crowdfunding.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Author:       Caychen
 * Class:        org.com.cay.crowdfunding.entity.Role
 * Date:         2018/12/11
 * Version:      v1.0
 * Desc:
 */
@Data
@Accessors(chain = true)
public class Role {

	private Integer id;

	private String name;

	private Date createDate;

	private Date updateDate;
}
