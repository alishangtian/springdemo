/*
 * Powered By Generator Util
 */
package com.cattsoft.baseplatform.func.sm.entity.query;

import com.cattsoft.baseplatform.core.entity.QueryBean;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public class BulletinQB extends QueryBean {
	private static final long serialVersionUID = 1L;
	
	private java.lang.Long bulletinId;
	private java.lang.Long userId;
	private java.lang.String bulletinTitle;
	private java.lang.String bulletinContent;
	private java.util.Date publishTime;
	private java.util.Date revokeTime;
	private java.lang.String priority;
	private java.lang.String keyword;
	private java.lang.String sts;
	private java.util.Date stsTime;
	private java.util.Date createTime;
	
	public void setBulletinId(java.lang.Long bulletinId) {
		this.bulletinId = bulletinId;
	}
	
	public java.lang.Long getBulletinId() {
		return this.bulletinId;
	}
	public void setUserId(java.lang.Long userId) {
		this.userId = userId;
	}
	
	public java.lang.Long getUserId() {
		return this.userId;
	}
	public void setBulletinTitle(java.lang.String bulletinTitle) {
		this.bulletinTitle = bulletinTitle;
	}
	
	public java.lang.String getBulletinTitle() {
		return this.bulletinTitle;
	}

	public java.lang.String getBulletinContent() {
		return bulletinContent;
	}

	public void setBulletinContent(java.lang.String bulletinContent) {
		this.bulletinContent = bulletinContent;
	}

	public void setPublishTime(java.util.Date publishTime) {
		this.publishTime = publishTime;
	}
	
	public java.util.Date getPublishTime() {
		return this.publishTime;
	}
	public void setRevokeTime(java.util.Date revokeTime) {
		this.revokeTime = revokeTime;
	}
	
	public java.util.Date getRevokeTime() {
		return this.revokeTime;
	}
	public void setPriority(java.lang.String priority) {
		this.priority = priority;
	}
	
	public java.lang.String getPriority() {
		return this.priority;
	}
	public void setKeyword(java.lang.String keyword) {
		this.keyword = keyword;
	}
	
	public java.lang.String getKeyword() {
		return this.keyword;
	}
	public void setSts(java.lang.String sts) {
		this.sts = sts;
	}
	
	public java.lang.String getSts() {
		return this.sts;
	}
	public void setStsTime(java.util.Date stsTime) {
		this.stsTime = stsTime;
	}
	
	public java.util.Date getStsTime() {
		return this.stsTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
}