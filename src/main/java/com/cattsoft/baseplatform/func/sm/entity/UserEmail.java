package com.cattsoft.baseplatform.func.sm.entity;

import java.sql.Timestamp;
import java.util.Date;

import com.cattsoft.baseplatform.core.entity.Entity;

/**
 * 用户获取密码时用到的邮箱实体类
 * 
 * @author wanghuacun
 * 
 */
public class UserEmail extends Entity {

	private static final long serialVersionUID = 1L;
	private Long userEmailId;
	private String emailAddress;
	private String emailContent;
	private String emailStatus;
	private Timestamp createTime;
	private Timestamp processTime;
	private Long userId;

	public UserEmail() {
		super();
	}

	public UserEmail(Long userEmailId, String emailAddress, String emailContent,
			String emailStatus, Timestamp createTime, Timestamp processTime, Long userId) {
		super();
		this.userEmailId = userEmailId;
		this.emailAddress = emailAddress;
		this.emailContent = emailContent;
		this.emailStatus = emailStatus;
		this.createTime = createTime;
		this.processTime = processTime;
		this.userId = userId;
	}

	public Long getUserEmailId() {
		return userEmailId;
	}

	public void setUserEmailId(Long userEmailId) {
		this.userEmailId = userEmailId;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}

	public String getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(String emailStatus) {
		this.emailStatus = emailStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Date getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Timestamp processTime) {
		this.processTime = processTime;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
