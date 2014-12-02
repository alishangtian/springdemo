package com.cattsoft.baseplatform.func.sm.entity;

import com.cattsoft.baseplatform.core.entity.Entity;

/**
 * 主页定制：用户定制信息。
 * 
 * @author wangcl
 */
public class UserPageSetting extends Entity {
	private static final long serialVersionUID = 6919101342747898912L;

	private Long userId; // 系统用户标识

	private String setting; // 用户设定，JSON

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getSetting() {
		return setting;
	}

	public void setSetting(String setting) {
		this.setting = setting;
	}

}
