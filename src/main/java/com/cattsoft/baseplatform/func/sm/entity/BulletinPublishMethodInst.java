package com.cattsoft.baseplatform.func.sm.entity;

import java.sql.Timestamp;

import org.apache.struts2.json.annotations.JSON;

import com.cattsoft.baseplatform.core.entity.Entity;

/**
 * 公告信息发布方式实体
 */
public class BulletinPublishMethodInst extends Entity {
	private static final long serialVersionUID = -4927384796766930635L;

	private Long bullPubMethodInstId;
	private Long bulletinId;
	private Long bullPubMethodId;
	private String sts;
	private Timestamp stsTime;
	private Timestamp createTime;

	public Long getBullPubMethodInstId() {
		return bullPubMethodInstId;
	}

	public void setBullPubMethodInstId(Long bullPubMethodInstId) {
		this.bullPubMethodInstId = bullPubMethodInstId;
	}

	public Long getBulletinId() {
		return bulletinId;
	}

	public void setBulletinId(Long bulletinId) {
		this.bulletinId = bulletinId;
	}

	public Long getBullPubMethodId() {
		return bullPubMethodId;
	}

	public void setBullPubMethodId(Long bullPubMethodId) {
		this.bullPubMethodId = bullPubMethodId;
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
