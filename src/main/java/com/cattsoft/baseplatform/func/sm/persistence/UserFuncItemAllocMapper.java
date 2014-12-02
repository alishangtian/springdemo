package com.cattsoft.baseplatform.func.sm.persistence;

import java.util.List;

import com.cattsoft.baseplatform.func.sm.entity.UserFuncItemAlloc;
import com.cattsoft.baseplatform.func.sm.entity.query.UserFuncAuthQuery;

public interface UserFuncItemAllocMapper {

	void insert(UserFuncItemAlloc userFuncItemAlloc);

	/**
	 * 获取用户的功能下的已授权功能操作列表
	 * 
	 * @param userFuncAuthQuery
	 * @return
	 */
	List<UserFuncItemAlloc> selectAllocFuncItems(UserFuncAuthQuery userFuncAuthQuery);

	/**
	 * 收回用户的功能下的某个功能操作或所有功能操作授权信息
	 * 
	 * @param userFuncAuthQuery
	 *            :userId+funcNodeId+funcItemId[可空]
	 */
	void retake(UserFuncAuthQuery userFuncAuthQuery);

	/**
	 * 用户注销时，注销用户对应的功能操作授权信息
	 * 
	 * @param userId
	 */
	void destroyUser(Long userId);
}
