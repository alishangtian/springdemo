package com.cattsoft.baseplatform.func.sm.entity;

import java.sql.Timestamp;

import org.apache.struts2.json.annotations.JSON;

import com.cattsoft.baseplatform.core.entity.Entity;

/**
 * @author longtao
 * 
 */
public class SysDept extends Entity {

	private static final long serialVersionUID = 439709530365924823L;
	// 部门标识
	private Long deptId;
	// 上级部门标识
	private Long superDeptId;
	// 部门编码
	private String deptCode;
	// 部门名称
	private String deptName;
	// 部门地址
	private String address;
	// 邮编
	private String postCode;
	// 电话号码
	private String telNbr;
	// 传真号码
	private String faxNbr;
	// 联系人
	private String linkMan;
	// 邮箱地址
	private String emailAddr;
	// 部门网址
	private String webSite;
	// 备注
	private String remarks;
	// 部门状态A|P
	private String sts;
	private Timestamp stsTime;
	private Timestamp createTime;

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getSuperDeptId() {
		return superDeptId;
	}

	public void setSuperDeptId(Long superDeptId) {
		this.superDeptId = superDeptId;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getTelNbr() {
		return telNbr;
	}

	public void setTelNbr(String telNbr) {
		this.telNbr = telNbr;
	}

	public String getFaxNbr() {
		return faxNbr;
	}

	public void setFaxNbr(String faxNbr) {
		this.faxNbr = faxNbr;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
