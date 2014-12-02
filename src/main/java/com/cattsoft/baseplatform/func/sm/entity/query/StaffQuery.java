package com.cattsoft.baseplatform.func.sm.entity.query;

import com.cattsoft.baseplatform.core.entity.QueryBean;

public class StaffQuery extends QueryBean {

	private static final long serialVersionUID = -4028091085285093847L;
	private String staffName;
	private String staffCode;
	private Long deptId;
	private boolean history;
	private String loginName; //账号名称
	private String deptName;  //部门名称

	public boolean isHistory() {
		return history;
	}

	public void setHistory(boolean history) {
		this.history = history;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

}
