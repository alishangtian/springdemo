package com.cattsoft.baseplatform.func.sm.persistence;

import java.util.List;

import com.cattsoft.baseplatform.func.sm.entity.SysRole;
import com.cattsoft.baseplatform.func.sm.entity.UserRoleAlloc;
import com.cattsoft.baseplatform.func.sm.entity.query.UserRoleAuthQuery;

public interface UserRoleAllocMapper {
	void insert(UserRoleAlloc userRoleAlloc);

	/**
	 * 收回用户角色授权信息
	 * 
	 * @param UserRoleAuthQuery
	 *            :roleId+userId
	 */
	void retake(UserRoleAuthQuery userRoleAuthQuery);

	/**
	 * 角色注销时，将角色相关的用户授权信息注销
	 * 
	 * @param roleId
	 */
	void destroyRole(Long roleId);

	/**
	 * 用户注销时，将用户相关的角色授权信息注销
	 * 
	 * @param userId
	 */
	void destroyUser(Long userId);

	/**
	 * 获取用户已授权的角色列表
	 * 
	 * @param userId
	 * @return
	 */
	List<UserRoleAlloc> selectAllocRoles(Long userId);

	/**
	 * 查询用户的所有可委派的角色
	 * 
	 * @param userId
	 * @return
	 */
	List<SysRole> selectDesignateRoles(Long userId);

	/**
	 * 指定用户的指定角色的授权信息
	 * 
	 * @param userRoleAlloc
	 * @return
	 */
	UserRoleAlloc selectUserRoleAlloc(UserRoleAlloc userRoleAlloc);

}
