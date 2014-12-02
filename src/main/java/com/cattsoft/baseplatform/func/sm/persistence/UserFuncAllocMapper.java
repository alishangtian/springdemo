package com.cattsoft.baseplatform.func.sm.persistence;

import java.util.List;

import com.cattsoft.baseplatform.func.sm.entity.UserFuncAlloc;
import com.cattsoft.baseplatform.func.sm.entity.query.UserFuncAuthQuery;

public interface UserFuncAllocMapper {

	void insert(UserFuncAlloc userFuncAlloc);

	/**
	 * 获取用户在某功能目录下已授权的功能列表
	 * 
	 * @param userFuncAuthQuery
	 *            :userId+nodeTreeId
	 * @return
	 */
	List<UserFuncAlloc> selectAllocFuncs(UserFuncAuthQuery userFuncAuthQuery);

	/**
	 * 获取用户的指定功能授权信息
	 * 
	 * @param userFuncAlloc
	 *            :userId+funcNodeId
	 * @return
	 */
	UserFuncAlloc selectAllocFunc(UserFuncAlloc userFuncAlloc);

	/**
	 * 收回用户的功能授权信息
	 * 
	 * @param userFuncAuthQuery
	 *            :userId+funcNodeId
	 */
	void retake(UserFuncAuthQuery userFuncAuthQuery);

	/**
	 * 用户注销时，注销用户对应的功能授权信息
	 * 
	 * @param userId
	 */
	void destroyUser(Long userId);

	/**
	 * 更新用户功能授权的委派标识
	 * 
	 * @param userFuncAlloc
	 *            ：userFuncAllocId+designate
	 */
	void modifyDesignate(UserFuncAlloc userFuncAlloc);
}
