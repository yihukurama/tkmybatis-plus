package com.yihukurama.tkmybatisplus.framework.domain.tkmapper.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;

/**
 * 说明： 基础用户类
 * @author dengshuai
 * @date Created in 19:27 2019/2/27
 *  modified by autor in 19:27 2019/2/27
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserEntity extends BaseEntity
{

	/**
	*账号
	**/
	private String username;
	/**
	*密码
	**/
	private String password;
	/**
	 *真实姓名
	 **/
	@Transient
	private String realName;

}
