package com.cattsoft.baseplatform.func.sm.entity;

import java.sql.Timestamp;

import org.apache.struts2.json.annotations.JSON;

import com.cattsoft.baseplatform.core.entity.Entity;

public class RoleFuncAlloc extends Entity {

	private static final long serialVersionUID = -868134575566064593L;
	private Long roleFuncAllocId;
	private Long roleId;
	private Long funcNodeId;
	private String designate;
	private String sts;
	private Timestamp stsTime;
	private Timestamp createTime;

	public Long getRoleFuncAllocId() {
		return roleFuncAllocId;
	}

	public void setRoleFuncAllocId(Long roleFuncAllocId) {
		this.roleFuncAllocId = roleFuncAllocId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getFuncNodeId() {
		return funcNodeId;
	}

	public void setFuncNodeId(Long funcNodeId) {
		this.funcNodeId = funcNodeId;
	}

	public String getDesignate() {
		return designate;
	}

	public void setDesignate(String designate) {
		this.designate = designate;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Timestamp getStsTime() {
		return stsTime;
	}

	public void setStsTime(Timestamp stsTime) {
		this.stsTime = stsTime;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
