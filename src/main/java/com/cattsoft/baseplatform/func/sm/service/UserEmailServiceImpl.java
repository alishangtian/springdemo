package com.cattsoft.baseplatform.func.sm.service;

import java.sql.Timestamp;

import com.cattsoft.baseplatform.core.util.DateUtil;
import com.cattsoft.baseplatform.func.sm.component.UserEmailComponent;
import com.cattsoft.baseplatform.func.sm.entity.UserEmail;

public class UserEmailServiceImpl implements UserEmailService {

	private UserEmailComponent userEmailComponent;

	public UserEmailComponent getUserEmailComponent() {
		return userEmailComponent;
	}

	public void setUserEmailComponent(UserEmailComponent userEmailComponent) {
		this.userEmailComponent = userEmailComponent;
	}

	@Override
	public void addUserEmail(UserEmail userEmail) {
		Timestamp time = DateUtil.getCurrentTimestamp();
		userEmail.setCreateTime(time);
		userEmail.setProcessTime(time);
		userEmailComponent.addUserEmail(userEmail);
	}

}
