package com.cattsoft.baseplatform.func.sm.entity.query;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

import com.cattsoft.baseplatform.core.entity.QueryBean;

public class LoginLogQuery extends QueryBean {

	private static final long serialVersionUID = 1L;
	private String userName;
	private String loginName;
	private String loginIp;
	private Timestamp startTime;
	private Timestamp endTime;

	public String getUserName() {
		return StringUtils.isBlank(userName) ? null : userName.trim();
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginName() {
		return StringUtils.isBlank(loginName) ? null : loginName.trim();
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginIp() {
		return StringUtils.isBlank(loginIp) ? null : loginIp.trim();
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

}
