package com.cattsoft.baseplatform.func.sm.entity.query;

import com.cattsoft.baseplatform.core.entity.QueryBean;

public class SysDeptQuery extends QueryBean {

	private static final long serialVersionUID = 4319218308423638181L;
	// 部门名称
	private String deptName;
	private String deptCode;

	private String sts;

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}
