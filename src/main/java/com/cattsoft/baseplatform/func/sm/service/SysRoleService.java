package com.cattsoft.baseplatform.func.sm.service;

import java.util.List;

import net.sf.json.JSONArray;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.func.sm.entity.FuncTree;
import com.cattsoft.baseplatform.func.sm.entity.SysRole;
import com.cattsoft.baseplatform.func.sm.entity.ctrl.RoleAllocUserCtrl;
import com.cattsoft.baseplatform.func.sm.entity.query.SysRoleQuery;
import com.cattsoft.baseplatform.func.sm.entity.query.SysUserQuery;

public interface SysRoleService {

	/**
	 * 依据角色名称，模糊查询满足条件的角色列表
	 * 
	 * @param qb
	 * @return
	 */
	public PagingResultBean<List<SysRole>> getSysRolePaging(PagingQueryBean<SysRoleQuery> qb);

	/**
	 * 创建角色
	 * 
	 * @param roleName
	 * @param roleDesc
	 * @return
	 */
	public Long createSysRole(String roleName, String roleDesc);

	/**
	 * 获取角色详细信息
	 * 
	 * @param roleId
	 * @return
	 */
	public SysRole getSysRole(Long roleId);

	/**
	 * 角色信息更新
	 * 
	 * @param roleId
	 * @param roleName
	 * @param roleDesc
	 */
	public void modifySysRole(Long roleId, String roleName, String roleDesc);

	/**
	 * 初始化功能目录结构
	 * 
	 * @return
	 */
	public List<FuncTree> loadFuncTree();

	/**
	 * 角色注销：注销角色信息、角色授权信息
	 * 
	 * @param roleId
	 */
	public void removeSysRole(Long roleId);

	/**
	 * 角色管理>>功能授权：加载角色的授权功能信息
	 * 
	 * @param operUserId
	 * @param roleId
	 * @param nodeTreeId
	 * @return
	 */
	public JSONArray loadFuncAuth(Long operUserId, Long roleId, Long nodeTreeId);

	/**
	 * 保存角色的功能授权信息
	 * 
	 * @param roleId
	 *            :角色标识
	 * @param funcAuthInfos
	 *            :前台提交的授权结果
	 */
	public void saveRoleFuncAuth(Long roleId, JSONArray funcAuthInfos);

	/**
	 * 依据条件模糊查询满足条件的用户列表
	 * 
	 * @param qb
	 * @return
	 */
	public PagingResultBean<List<RoleAllocUserCtrl>> getSysUserPaging(
			PagingQueryBean<SysUserQuery> qb);

	/**
	 * 保存角色的用户授权信息
	 * 
	 * @param roleId
	 *            ：角色标识
	 * @param retakeUsers
	 *            ：取消授权的用户
	 * @param grantUsers
	 *            ：授权的用户
	 */
	public void saveRoleUserAuth(Long roleId, List<Long> retakeUsers, List<Long> grantUsers);

	/**
	 * 浏览角色的功能权限
	 * 
	 * @param roleId
	 * @param nodeTreeId
	 * @return
	 */
	public JSONArray viewRoleFuncAuth(Long roleId, Long nodeTreeId);
}
