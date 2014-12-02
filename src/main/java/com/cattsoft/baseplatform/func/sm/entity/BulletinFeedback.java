package com.cattsoft.baseplatform.func.sm.entity;

import java.sql.Timestamp;

import org.apache.struts2.json.annotations.JSON;

import com.cattsoft.baseplatform.core.entity.Entity;

/**
 * 公告发布结果反馈实体
 */
public class BulletinFeedback extends Entity {
	private static final long serialVersionUID = -4927384796766930635L;
	
	private Long bulletinFeedbackId;
	private Long bulletinId;
	private Long userId;
	private String sts;
	private Timestamp stsTime;
	private Timestamp createTime;
	
	public Long getBulletinFeedbackId() {
		return bulletinFeedbackId;
	}
	public void setBulletinFeedbackId(Long bulletinFeedbackId) {
		this.bulletinFeedbackId = bulletinFeedbackId;
	}
	public Long getBulletinId() {
		return bulletinId;
	}
	public void setBulletinId(Long bulletinId) {
		this.bulletinId = bulletinId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
