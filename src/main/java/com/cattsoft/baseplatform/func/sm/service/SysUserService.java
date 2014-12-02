package com.cattsoft.baseplatform.func.sm.service;

import java.sql.Timestamp;
import java.util.List;

import net.sf.json.JSONArray;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.core.exception.AuthenticationException;
import com.cattsoft.baseplatform.func.sm.entity.FuncNode;
import com.cattsoft.baseplatform.func.sm.entity.FuncTree;
import com.cattsoft.baseplatform.func.sm.entity.Staff;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.entity.query.SysUserQuery;

public interface SysUserService {
	/**
	 * 初始化功能目录结构
	 * 
	 * @return
	 */
	public List<FuncTree> loadFuncTree();

	/**
	 * 用户资料维护：依据用户所有者类型、用户所有者名称、用户名称查询满足条件的用户列表
	 * 
	 * @param qb
	 * @return
	 */
	public PagingResultBean<List<SysUser>> getSysUserPaging(PagingQueryBean<SysUserQuery> qb);

	/**
	 * 创建系统用户
	 * 
	 * @param sysUser
	 * @param ownerType
	 * @param ownerId
	 * @param staff
	 *            :只有新建员工才传此参数，否则传null
	 * @return
	 */
	public Long createSysUser(SysUser sysUser, String ownerType, String ownerId, Staff staff);

	/**
	 * 加载系统用户及用户对应的使用者信息
	 * 
	 * @param userId
	 * @return
	 */
	public SysUser getSysUser(Long userId);

	/**
	 * 依据用户登录名称获取唯一的用户信息，如能匹配到多个用户，则说明用户数据有误，返回null
	 * 
	 * @param loginName
	 * @return
	 */
	public SysUser getSysUserByName(String loginName);

	/**
	 * 判断登录是否被未注销的用户使用
	 * 
	 * @param loginName
	 * @return
	 */
	public boolean loginNameBeenUsed(String loginName);

	/**
	 * 更改用户的失效时间
	 * 
	 * @param userId
	 * @param userExpTime
	 */
	public void modifyUserExpTime(Long userId, Timestamp userExpTime);

	/**
	 * 注销系统用户
	 * 
	 * @param userId
	 */
	public void removeSysUser(Long userId);

	/**
	 * 用户密码修改
	 * 
	 * @param userId
	 *            ：用户标识
	 * @param password
	 *            ：加密后的原密码
	 * @param newPassword
	 *            ：加密后的新密码
	 */
	public void modifyPassword(Long userId, String password, String newPassword);

	/**
	 * 用户密码重置
	 * 
	 * @param userId
	 * @param password
	 *            :密文密码
	 */
	public void resetPassword(Long userId, String password);

	/**
	 * 加载系统用户的角色权限信息
	 * 
	 * @param operUserId
	 *            ：执行授权操作的用户（登录用户）
	 * @param userId
	 *            ：被授权的用户
	 * @return
	 */
	public JSONArray loadRoleAuth(Long operUserId, Long userId);

	/**
	 * 保存用户的角色授权信息
	 * 
	 * @param userId
	 * @param roleAuthInfos
	 */
	public void saveUserRoleAuth(Long userId, JSONArray roleAuthInfos);

	/**
	 * 加载用户的功能及功能操作权限信息
	 * 
	 * @param operUserId
	 *            ：执行授权操作的用户（登录用户）
	 * @param userId
	 *            ：被授权的用户
	 * @param nodeTreeId
	 * @return
	 */
	public JSONArray loadFuncAuth(Long operUserId, Long userId, Long nodeTreeId);

	/**
	 * 保存用户的功能授权信息
	 * 
	 * @param userId
	 *            :用户标识
	 * @param funcAuthInfos
	 *            :前台提交的授权结果
	 */
	public void saveUserFuncAuth(Long userId, JSONArray funcAuthInfos);

	/**
	 * 用户认证
	 * 
	 * @param loginName
	 * @param password
	 * @return
	 * @throws AuthenticationException
	 */
	public SysUser authenticate(String loginName, String password) throws AuthenticationException;

	/**
	 * 获取用户的授权的功能及功能操作集合
	 * 
	 * @param userId
	 * @return
	 */
	public List<FuncNode> getUserFuncInfos(Long userId);

	/**
	 * 新增用户的功能快捷方式
	 * 
	 * @param userId
	 * @param funcNodeId
	 * @return
	 */
	public Long createUserShortcut(Long userId, Long funcNodeId);

	/**
	 * 删除用户的功能快捷方式
	 * 
	 * @param userId
	 * @param funcNodeId
	 * @return
	 */
	public void removeUserShortcut(Long userId, Long funcNodeId);

	/**
	 * 用户冻结/解冻：依据用户所有者类型、用户所有者名称、用户名称查询满足条件的用户列表
	 * 
	 * @param qb
	 * @return
	 */
	public PagingResultBean<List<SysUser>> getSysUserPaging4unfrozen(
			PagingQueryBean<SysUserQuery> qb);

	/**
	 * 用户冻结/解冻：更改用户的失效时间
	 * 
	 * @param userIds
	 * @param userExpTime
	 */
	public void modifyUserExpTime(List<Long> userIds, Timestamp userExpTime);

}
