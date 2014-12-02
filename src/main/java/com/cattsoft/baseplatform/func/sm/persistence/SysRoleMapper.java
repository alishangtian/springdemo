package com.cattsoft.baseplatform.func.sm.persistence;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.func.sm.entity.SysRole;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.entity.query.SysRoleQuery;
import com.cattsoft.baseplatform.func.sm.entity.query.SysUserQuery;

public interface SysRoleMapper {
	void insert(SysRole sysRole);

	/**
	 * 更新角色名称、角色描述、角色状态
	 * 
	 * @param sysRole
	 */
	void update(SysRole sysRole);

	SysRole selectOne(Long roleId);

	/**
	 * 查询所有有效的角色信息
	 * 
	 * @return
	 */
	List<SysRole> selectAll();

	/**
	 * 依据角色名称，模糊查询满足条件的角色列表
	 * 
	 * @param qb
	 * @return
	 */
	List<SysRole> selectPageSysRole(PagingQueryBean<SysRoleQuery> qb);

	/**
	 * 依据角色名称，模糊查询满足条件的角色总数
	 * 
	 * @param qb
	 * @return
	 */
	Integer selectCountSysRole(PagingQueryBean<SysRoleQuery> qb);

	/**
	 * 依据用户登录名称、部门名称查询所有者为部门，且已与指定角色建立授权关系的用户列表
	 * 
	 * @param qb
	 * @return
	 */
	List<SysUser> selectPageDeptUser(PagingQueryBean<SysUserQuery> qb);

	/**
	 * 依据用户登录名称、部门名称查询所有者为部门，且已与指定角色建立授权关系的用户总数
	 * 
	 * @param qb
	 * @return
	 */
	Integer selectCountDeptUser(PagingQueryBean<SysUserQuery> qb);

	/**
	 * 依据用户登录名称、员工名称查询所有者为员工，且已与指定角色建立授权关系的用户列表
	 * 
	 * @param qb
	 * @return
	 */
	List<SysUser> selectPageStaffUser(PagingQueryBean<SysUserQuery> qb);

	/**
	 * 依据用户登录名称、员工名称查询所有者为员工，且已与指定角色建立授权关系的用户总数
	 * 
	 * @param qb
	 * @return
	 */
	Integer selectCountStaffUser(PagingQueryBean<SysUserQuery> qb);

}
