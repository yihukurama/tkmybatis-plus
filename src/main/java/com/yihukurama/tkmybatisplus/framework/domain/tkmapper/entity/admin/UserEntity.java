package com.yihukurama.tkmybatisplus.framework.domain.tkmapper.entity.admin;

import com.yihukurama.tkmybatisplus.framework.domain.tkmapper.entity.BaseEntity;
import lombok.Data;

/**
 * 说明： 基础用户类
 * @author dengshuai
 * @date Created in 19:27 2019/2/27
 * @modified by autor in 19:27 2019/2/27
 */
@Data
public class UserEntity extends BaseEntity
{
	private String id;
	/**
	*账号
	**/
	private String username;
	/**
	*密码
	**/
	private String password;
	/**
	 *密码
	 **/
	private String realName;

}
