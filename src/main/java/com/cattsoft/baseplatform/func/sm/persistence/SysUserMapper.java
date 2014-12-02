package com.cattsoft.baseplatform.func.sm.persistence;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.func.sm.entity.FuncItem;
import com.cattsoft.baseplatform.func.sm.entity.FuncNode;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.entity.UserShortcut;
import com.cattsoft.baseplatform.func.sm.entity.query.SysUserQuery;
import com.cattsoft.baseplatform.func.sm.entity.query.UserFuncAuthQuery;

public interface SysUserMapper {
	void insert(SysUser sysUser);

	/**
	 * 更新用户失效时间、用户状态及密码相关信息
	 * 
	 * @param sysUser
	 */
	void update(SysUser sysUser);

	/**
	 * 获取用户的基本信息
	 * 
	 * @param userId
	 * @return
	 */
	SysUser selectOne(Long userId);

	/**
	 * 依据唯一的登录名称查询未注销的用户信息
	 * 
	 * @param loginName
	 * @return
	 */
	List<SysUser> selectListByName(String loginName);

	/**
	 * 注销员工下的所有系统用户
	 * 
	 * @param staffId
	 */
	void destroyByStaff(Long staffId);

	/**
	 * 依据用户登录名称、部门名称查询所有者为部门的用户列表
	 * 
	 * @param qb
	 * @return
	 */
	List<SysUser> selectPageDeptUser(PagingQueryBean<SysUserQuery> qb);

	/**
	 * 依据用户登录名称、部门名称查询所有者为部门的用户总数
	 * 
	 * @param qb
	 * @return
	 */
	Integer selectCountDeptUser(PagingQueryBean<SysUserQuery> qb);

	/**
	 * 依据用户登录名称、员工名称查询所有者为员工的用户列表
	 * 
	 * @param qb
	 * @return
	 */
	List<SysUser> selectPageStaffUser(PagingQueryBean<SysUserQuery> qb);

	/**
	 * 依据用户登录名称、员工名称查询所有者为员工的用户总数
	 * 
	 * @param qb
	 * @return
	 */
	Integer selectCountStaffUser(PagingQueryBean<SysUserQuery> qb);

	/**
	 * 获取用户的所有功能授权信息功能:通过角色授权和功能授权获得的权限的集合
	 * 
	 * @param userFuncAuthQuery
	 *            :userId+nodeTreeId+designate
	 * @return
	 */
	List<FuncNode> selectUserAuthFuncs(UserFuncAuthQuery userFuncAuthQuery);

	/**
	 * 获取用户指定功能下的功能操作授权信息：通过角色授权和功能授权获得权限的集合
	 * 
	 * @param userFuncAuthQuery
	 *            :userId+funcNodeId+designate
	 * @return
	 */
	List<FuncItem> selectUserAuthFuncItems(UserFuncAuthQuery userFuncAuthQuery);

	/**
	 * 获取用户的所有功能菜单快捷方式
	 * 
	 * @param userId
	 * @return
	 */
	List<FuncNode> selectUserShortcut(Long userId);

	/**
	 * 新增用户的功能快捷方式
	 * 
	 * @param userShortcut
	 */
	void insertShortcut(UserShortcut userShortcut);

	/**
	 * 删除用户的功能快捷方式
	 * 
	 * @param userShortcut
	 */
	void deleteShortcut(UserShortcut userShortcut);

}
