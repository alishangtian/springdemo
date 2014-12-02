package com.cattsoft.baseplatform.func.sm.persistence;

import com.cattsoft.baseplatform.func.sm.entity.UserOwnerRela;

public interface UserOwnerRelaMapper {

	void insert(UserOwnerRela userOwnerRela);

	/**
	 * 依据用户标识作废关系数据
	 * 
	 * @param userId
	 */
	void destroyUser(Long userId);

	/**
	 * 获取用户的唯一的所有者信息
	 * 
	 * @param userId
	 * @return
	 */
	UserOwnerRela getUserOwnerType(Long userId);

}
