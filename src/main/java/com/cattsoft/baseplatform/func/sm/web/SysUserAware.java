package com.cattsoft.baseplatform.func.sm.web;

import com.cattsoft.baseplatform.func.sm.entity.SysUser;

/**
 * 声明需要获取系统用户信息。
 * 
 * @author wangcl
 */
public interface SysUserAware {
	/**
	 * 用于注入系统用户（IDEA_USER）信息。
	 * 
	 * @param user
	 *            系统用户信息。
	 */
	public void setSysUser(SysUser user);

	/**
	 * 获取系统用户信息（IDEA_USER）。
	 */
	public SysUser getSysUser();
}
