package com.cattsoft.baseplatform.func.sm.entity.query;

import com.cattsoft.baseplatform.core.entity.QueryBean;

public class SysRoleQuery extends QueryBean {

	private static final long serialVersionUID = 1781583490184650379L;
	private String roleName;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
