package com.cattsoft.baseplatform.func.sm.persistence;

import com.cattsoft.baseplatform.func.sm.entity.UserPageSetting;

/**
 * 用户主页设置。
 * 
 * @author wangcl
 */
public interface UserPageSettingMapper {
	void insert(UserPageSetting userPageSetting);

	void update(UserPageSetting userPageSetting);

	void delete(Long userId);

	UserPageSetting select(Long userId);
}
