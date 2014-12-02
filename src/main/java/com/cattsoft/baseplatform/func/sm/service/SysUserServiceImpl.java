package com.cattsoft.baseplatform.func.sm.service;

import java.sql.Timestamp;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.core.exception.AuthenticationException;
import com.cattsoft.baseplatform.core.exception.ServiceException;
import com.cattsoft.baseplatform.core.util.DateUtil;
import com.cattsoft.baseplatform.func.sm.component.FunctionComponent;
import com.cattsoft.baseplatform.func.sm.component.SysUserComponent;
import com.cattsoft.baseplatform.func.sm.entity.FuncNode;
import com.cattsoft.baseplatform.func.sm.entity.FuncTree;
import com.cattsoft.baseplatform.func.sm.entity.Staff;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.entity.query.SysUserQuery;
import com.cattsoft.baseplatform.func.sm.util.SysConstants;

@Transactional
public class SysUserServiceImpl implements SysUserService {
	private SysUserComponent sysUserComponent;
	private FunctionComponent functionComponent;

	public void setSysUserComponent(SysUserComponent sysUserComponent) {
		this.sysUserComponent = sysUserComponent;
	}

	public void setFunctionComponent(FunctionComponent functionComponent) {
		this.functionComponent = functionComponent;
	}

	@Transactional(readOnly = true)
	@Override
	public List<FuncTree> loadFuncTree() {
		return this.functionComponent.initFuncTree();
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<SysUser>> getSysUserPaging(PagingQueryBean<SysUserQuery> qb) {
		SysUserQuery sysUserQuery = qb.getQueryBean();
		if (StringUtils.isBlank(sysUserQuery.getOwnerType())) {
			throw new ServiceException("未指定用户使用者类型");
		}
		if (!SysConstants.SysUser.OwnerType.DEPT.equals(sysUserQuery.getOwnerType())
				&& !SysConstants.SysUser.OwnerType.STAFF.equals(sysUserQuery.getOwnerType())) {
			throw new ServiceException("指定的用户使用者类型异常");
		}
		// 将空串、空白串转为null，不影响mapper的判断
		if (StringUtils.isBlank(sysUserQuery.getLoginName())) {
			sysUserQuery.setLoginName(null);
		}
		if (StringUtils.isBlank(sysUserQuery.getOwnerName())) {
			sysUserQuery.setOwnerName(null);
		}
		// 用户资料维护的隐式查询条件
		sysUserQuery.setSts(SysConstants.Status.STS_A);
		sysUserQuery.setUserExpTime(DateUtil.getCurrentTimestamp());
		return this.sysUserComponent.getSysUserPaging(qb);
	}

	@Override
	public Long createSysUser(SysUser sysUser, String ownerType, String ownerId, Staff staff) {
		if (!SysConstants.SysUser.OwnerType.DEPT.equals(ownerType)
				&& !SysConstants.SysUser.OwnerType.STAFF.equals(ownerType)) {
			throw new ServiceException("用户使用者类型异常");
		}
		if (!StringUtils.isBlank(ownerId) && staff != null) {
			throw new ServiceException("用户使用者为新员工，入参ownerId只能为空");
		}
		Timestamp time = DateUtil.getCurrentTimestamp();
		if (staff != null) {
			staff.setSts(SysConstants.Status.STS_A);
			staff.setStsTime(time);
			staff.setCreateTime(time);
		}
		// 只能创建普通用户
		sysUser.setUserType(SysConstants.SysUser.UserType.COMMON);
		if (StringUtils.isBlank(sysUser.getPassword())) {
			sysUser.setPassword(SysConstants.INIT_PASSWORD);
		}
		if (null == sysUser.getPwdExpTime()) {
			sysUser.setPwdExpTime(DateUtil.addMonth(time, 3));
		}
		sysUser.setSts(SysConstants.Status.STS_A);
		sysUser.setStsTime(time);
		sysUser.setCreateTime(time);
		return this.sysUserComponent.createSysUser(sysUser, ownerType, ownerId, staff);
	}

	@Transactional(readOnly = true)
	@Override
	public SysUser getSysUser(Long userId) {
		return this.sysUserComponent.getSysUser(userId);
	}

	@Transactional(readOnly = true)
	@Override
	public SysUser getSysUserByName(String loginName) {
		if (StringUtils.isBlank(loginName)) {
			throw new ServiceException("未指定登录名称，无法查询用户信息");
		}
		return this.sysUserComponent.getSysUserByName(loginName);
	}

	@Transactional(readOnly = true)
	@Override
	public boolean loginNameBeenUsed(String loginName) {
		if (StringUtils.isBlank(loginName)) {
			throw new ServiceException("未指定登录名称，无需判定登录名称是否被使用");
		}
		return this.sysUserComponent.loginNameBeenUsed(loginName);
	}

	@Override
	public void modifyUserExpTime(Long userId, Timestamp userExpTime) {
		this.sysUserComponent.modifyUserExpTime(userId, userExpTime);
	}

	@Override
	public void removeSysUser(Long userId) {
		this.sysUserComponent.removeSysUser(userId);
	}

	@Override
	public void modifyPassword(Long userId, String password, String newPassword) {
		if (StringUtils.isBlank(password)) {
			throw new ServiceException("旧密码为空，无法执行密码修改操作");
		}
		if (StringUtils.isBlank(newPassword)) {
			throw new ServiceException("新密码为空，无法执行密码修改操作");
		}
		this.sysUserComponent.modifyPassword(userId, password, newPassword);
	}

	@Override
	public void resetPassword(Long userId,String password) {
		this.sysUserComponent.resetPassword(userId,password);
	}

	@Transactional(readOnly = true)
	@Override
	public JSONArray loadRoleAuth(Long operUserId, Long userId) {
		return this.sysUserComponent.loadRoleAuth(operUserId, userId);
	}

	@Override
	public void saveUserRoleAuth(Long userId, JSONArray roleAuthInfos) {
		if (null == roleAuthInfos) {
			throw new ServiceException("未提交角色授权信息，无法保存");
		}
		this.sysUserComponent.saveUserRoleAuth(userId, roleAuthInfos);
	}

	@Transactional(readOnly = true)
	@Override
	public JSONArray loadFuncAuth(Long operUserId, Long userId, Long nodeTreeId) {
		return this.sysUserComponent.loadFuncAuth(operUserId, userId, nodeTreeId);
	}

	@Override
	public void saveUserFuncAuth(Long userId, JSONArray funcAuthInfos) {
		if (null == funcAuthInfos) {
			throw new ServiceException("未提交功能及功能操作授权信息，无法保存");
		}
		this.sysUserComponent.saveUserFuncAuth(userId, funcAuthInfos);
	}

	@Transactional(readOnly = true)
	@Override
	public SysUser authenticate(String loginName, String password) throws AuthenticationException {
		if (StringUtils.isBlank(loginName) || StringUtils.isBlank(password)) {
			throw new ServiceException("用户登录名称或登录密码为空，无法进行认证");
		}
		return this.sysUserComponent.authenticate(loginName, password);
	}

	@Transactional(readOnly = true)
	@Override
	public List<FuncNode> getUserFuncInfos(Long userId) {
		return this.sysUserComponent.getUserFuncInfos(userId);
	}

	@Override
	public Long createUserShortcut(Long userId, Long funcNodeId) {
		if (null == userId) {
			throw new ServiceException("用户标识为空");
		}
		if (null == funcNodeId) {
			throw new ServiceException("功能标识为空");
		}
		return this.sysUserComponent.createUserShortcut(userId, funcNodeId);
	}

	@Override
	public void removeUserShortcut(Long userId, Long funcNodeId) {
		if (null == userId) {
			throw new ServiceException("用户标识为空");
		}
		if (null == funcNodeId) {
			throw new ServiceException("功能标识为空");
		}
		this.sysUserComponent.removeUserShortcut(userId, funcNodeId);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<SysUser>> getSysUserPaging4unfrozen(
			PagingQueryBean<SysUserQuery> qb) {
		SysUserQuery sysUserQuery = qb.getQueryBean();
		if (StringUtils.isBlank(sysUserQuery.getOwnerType())) {
			throw new ServiceException("未指定用户使用者类型");
		}
		if (!SysConstants.SysUser.OwnerType.DEPT.equals(sysUserQuery.getOwnerType())
				&& !SysConstants.SysUser.OwnerType.STAFF.equals(sysUserQuery.getOwnerType())) {
			throw new ServiceException("指定的用户使用者类型异常");
		}
		// 将空串、空白串转为null，不影响mapper的判断
		if (StringUtils.isBlank(sysUserQuery.getLoginName())) {
			sysUserQuery.setLoginName(null);
		}
		if (StringUtils.isBlank(sysUserQuery.getOwnerName())) {
			sysUserQuery.setOwnerName(null);
		}
		// 用户资料维护的隐式查询条件
		sysUserQuery.setSts(SysConstants.Status.STS_A);
		/* 必须传空 */
		sysUserQuery.setUserExpTime(null);
		return this.sysUserComponent.getSysUserPaging(qb);
	}

	@Override
	public void modifyUserExpTime(List<Long> userIds, Timestamp userExpTime) {
		for (Long userId : userIds) {
			this.sysUserComponent.modifyUserExpTime(userId, userExpTime);
		}
	}

}
