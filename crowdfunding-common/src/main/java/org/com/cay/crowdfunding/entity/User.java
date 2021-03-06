package org.com.cay.crowdfunding.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Author:       Caychen
 * Class:        org.com.cay.crowdfunding.entity.User
 * Date:         2018/11/29
 * Version:      v1.0
 * Desc:
 */
@Data
@Accessors(chain = true)
public class User {

	private Integer id;

	private String username;

	private String password;

	private String nickname;

	private String email;

	private Date createDate;

	private Date updateDate;
}
