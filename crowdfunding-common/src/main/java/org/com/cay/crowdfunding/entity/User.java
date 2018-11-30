package org.com.cay.crowdfunding.entity;

import lombok.Data;

/**
 * Author:       Caychen
 * Class:        org.com.cay.crowdfunding.entity.User
 * Date:         2018/11/29
 * Version:      v1.0
 * Desc:
 */
@Data
public class User {

	private Integer id;

	private String username;

	private String password;
}