package com.cattsoft.baseplatform.func.sm.entity;

import java.sql.Timestamp;

import org.apache.struts2.json.annotations.JSON;

import com.cattsoft.baseplatform.core.entity.Entity;

/**
 * 公告信息发布范围实体
 */
public class BulletinPublishRangeInst extends Entity {
	private static final long serialVersionUID = -4927384796766930635L;

	/** 发布范围:部门 */
	public static final Long RANGE_DEPT = 1000l;
	/** 发布范围:角色 */
	public static final Long RANGE_ROLE = 2000l;
	/** 发布范围:用户 */
	public static final Long RANGE_USER = 3000l;

	private Long bullPubRangeInstId;
	private Long bulletinId;
	private Long bullPubRangeId;
	private Long instObjId;
	private String instObjIdStr;
	private String sts;
	private Timestamp stsTime;
	private Timestamp createTime;

	public String getInstObjIdStr() {
		return instObjIdStr;
	}

	public void setInstObjIdStr(String instObjIdStr) {
		this.instObjIdStr = instObjIdStr;
	}

	public Long getBullPubRangeInstId() {
		return bullPubRangeInstId;
	}

	public void setBullPubRangeInstId(Long bullPubRangeInstId) {
		this.bullPubRangeInstId = bullPubRangeInstId;
	}

	public Long getBulletinId() {
		return bulletinId;
	}

	public void setBulletinId(Long bulletinId) {
		this.bulletinId = bulletinId;
	}

	public Long getBullPubRangeId() {
		return bullPubRangeId;
	}

	public void setBullPubRangeId(Long bullPubRangeId) {
		this.bullPubRangeId = bullPubRangeId;
	}

	public Long getInstObjId() {
		return instObjId;
	}

	public void setInstObjId(Long instObjId) {
		this.instObjId = instObjId;
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
