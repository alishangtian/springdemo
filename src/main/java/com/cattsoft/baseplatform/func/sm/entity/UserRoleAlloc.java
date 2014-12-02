package com.cattsoft.baseplatform.func.sm.entity;

import java.sql.Timestamp;

import org.apache.struts2.json.annotations.JSON;

import com.cattsoft.baseplatform.core.entity.Entity;

public class UserRoleAlloc extends Entity {

	private static final long serialVersionUID = 8850703513808277667L;
	private Long userRoleAllocId;
	private Long userId;
	private Long roleId;
	private String designate;
	private String sts;
	private Timestamp stsTime;
	private Timestamp createTime;

	public Long getUserRoleAllocId() {
		return userRoleAllocId;
	}

	public void setUserRoleAllocId(Long userRoleAllocId) {
		this.userRoleAllocId = userRoleAllocId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
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
