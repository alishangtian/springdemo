package com.cattsoft.baseplatform.func.sm.component;

import com.cattsoft.baseplatform.func.sm.entity.UserEmail;
import com.cattsoft.baseplatform.func.sm.persistence.UserEmailMapper;

public class UserEmailComponent {

	private UserEmailMapper userEmailMapper;

	public void setUserEmailMapper(UserEmailMapper userEmailMapper) {
		this.userEmailMapper = userEmailMapper;
	}

	/**
	 * 添加用户邮箱信息
	 * 
	 * @param userEmail
	 */
	public void addUserEmail(UserEmail userEmail) {
		this.userEmailMapper.insert(userEmail);
	}

}
