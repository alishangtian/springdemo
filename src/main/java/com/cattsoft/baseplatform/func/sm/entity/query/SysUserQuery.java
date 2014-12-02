package com.cattsoft.baseplatform.func.sm.entity.query;

import java.sql.Timestamp;

import com.cattsoft.baseplatform.core.entity.QueryBean;

public class SysUserQuery extends QueryBean {
	private static final long serialVersionUID = -6734205807386553512L;
	// 所有者类型：取值类型参看SysConstants.SysUser.OwnerType中的定义
	private String ownerType;
	// 所有者名称
	private String ownerName;
	// 登录名称
	private String loginName;
	// 角色管理>>用户授权：只查询已授权的用户
	private boolean auth;
	// 角色管理>>用户授权：角色标识
	private Long roleId;

	// 用户失效时间不得晚于此时间
	private Timestamp userExpTime;
	// 用户状态
	private String sts;

	private Long staffId;

	private Long deptId;

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public Timestamp getUserExpTime() {
		return userExpTime;
	}

	public void setUserExpTime(Timestamp userExpTime) {
		this.userExpTime = userExpTime;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public boolean isAuth() {
		return auth;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
