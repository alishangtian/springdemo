package com.cattsoft.baseplatform.func.sm.entity;

import java.sql.Timestamp;

import org.apache.struts2.json.annotations.JSON;

import com.cattsoft.baseplatform.core.entity.Entity;

public class RoleFuncItemAlloc extends Entity {

	private static final long serialVersionUID = 3754592821397204244L;
	private Long roleFuncItemAllocId;
	private Long roleFuncAllocId;
	private Long funcItemId;
	private String designate;
	private String sts;
	private Timestamp stsTime;
	private Timestamp createTime;

	public Long getRoleFuncItemAllocId() {
		return roleFuncItemAllocId;
	}

	public void setRoleFuncItemAllocId(Long roleFuncItemAllocId) {
		this.roleFuncItemAllocId = roleFuncItemAllocId;
	}

	public Long getRoleFuncAllocId() {
		return roleFuncAllocId;
	}

	public void setRoleFuncAllocId(Long roleFuncAllocId) {
		this.roleFuncAllocId = roleFuncAllocId;
	}

	public Long getFuncItemId() {
		return funcItemId;
	}

	public void setFuncItemId(Long funcItemId) {
		this.funcItemId = funcItemId;
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
