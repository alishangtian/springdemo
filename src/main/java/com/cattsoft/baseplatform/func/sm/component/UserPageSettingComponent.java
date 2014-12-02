package com.cattsoft.baseplatform.func.sm.component;

import com.cattsoft.baseplatform.func.sm.entity.UserPageSetting;
import com.cattsoft.baseplatform.func.sm.persistence.UserPageSettingMapper;

/**
 * 用户主页设置组件。
 * 
 * @author wangcl
 */
public class UserPageSettingComponent {
	private UserPageSettingMapper userPageSettingMapper;

	public void setUserPageSettingMapper(UserPageSettingMapper userPageSettingMapper) {
		this.userPageSettingMapper = userPageSettingMapper;
	}

	/**
	 * 新增。
	 * 
	 * @param userPageSetting
	 *            用户设置信息。
	 * @return 主键ID
	 */
	public Long createUserPageSetting(UserPageSetting userPageSetting) {
		userPageSettingMapper.insert(userPageSetting);
		return userPageSetting.getUserId();
	}

	public void updateUserPageSetting(UserPageSetting userPageSetting) {
		userPageSettingMapper.update(userPageSetting);
	}

	public void removeUserPageSetting(Long userId) {
		userPageSettingMapper.delete(userId);
	}

	public UserPageSetting getUserPageSetting(Long userId) {
		return userPageSettingMapper.select(userId);
	}

}
