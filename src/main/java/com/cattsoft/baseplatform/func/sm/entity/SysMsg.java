package com.cattsoft.baseplatform.func.sm.entity;

import java.sql.Timestamp;

import com.cattsoft.baseplatform.core.entity.Entity;

/**
 * 系统消息实体
 */
public class SysMsg extends Entity {
	private static final long serialVersionUID = -8798813852055617268L;
	private Long sysMsgId;
	private String msgName;
	private String msgCountUrl;
	private String dialogUrl;
	private String dialogTitle;
	private Long dialogWidth;
	private Long dialogHeight;
	private String sts;
	private Timestamp stsTime;

	public Long getSysMsgId() {
		return sysMsgId;
	}

	public void setSysMsgId(Long sysMsgId) {
		this.sysMsgId = sysMsgId;
	}

	public String getMsgName() {
		return msgName;
	}

	public void setMsgName(String msgName) {
		this.msgName = msgName;
	}

	public String getMsgCountUrl() {
		return msgCountUrl;
	}

	public void setMsgCountUrl(String msgCountUrl) {
		this.msgCountUrl = msgCountUrl;
	}

	public String getDialogTitle() {
		return dialogTitle;
	}

	public void setDialogTitle(String dialogTitle) {
		this.dialogTitle = dialogTitle;
	}

	public Long getDialogWidth() {
		return dialogWidth;
	}

	public void setDialogWidth(Long dialogWidth) {
		this.dialogWidth = dialogWidth;
	}

	public Long getDialogHeight() {
		return dialogHeight;
	}

	public void setDialogHeight(Long dialogHeight) {
		this.dialogHeight = dialogHeight;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public Timestamp getStsTime() {
		return stsTime;
	}

	public void setStsTime(Timestamp stsTime) {
		this.stsTime = stsTime;
	}

	public String getDialogUrl() {
		return dialogUrl;
	}

	public void setDialogUrl(String dialogUrl) {
		this.dialogUrl = dialogUrl;
	}

}
