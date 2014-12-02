package com.cattsoft.baseplatform.func.sm.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.core.util.DateUtil;
import com.cattsoft.baseplatform.func.sm.component.FunctionComponent;
import com.cattsoft.baseplatform.func.sm.component.SysRoleComponent;
import com.cattsoft.baseplatform.func.sm.component.SysUserComponent;
import com.cattsoft.baseplatform.func.sm.entity.FuncTree;
import com.cattsoft.baseplatform.func.sm.entity.SysRole;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.entity.ctrl.RoleAllocUserCtrl;
import com.cattsoft.baseplatform.func.sm.entity.query.SysRoleQuery;
import com.cattsoft.baseplatform.func.sm.entity.query.SysUserQuery;
import com.cattsoft.baseplatform.func.sm.util.SysConstants;

@Transactional
public class SysRoleServiceImpl implements SysRoleService {
	private SysRoleComponent sysRoleComponent;
	private FunctionComponent functionComponent;
	private SysUserComponent sysUserComponent;

	public void setSysRoleComponent(SysRoleComponent sysRoleComponent) {
		this.sysRoleComponent = sysRoleComponent;
	}

	public void setFunctionComponent(FunctionComponent functionComponent) {
		this.functionComponent = functionComponent;
	}

	public void setSysUserComponent(SysUserComponent sysUserComponent) {
		this.sysUserComponent = sysUserComponent;
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<SysRole>> getSysRolePaging(PagingQueryBean<SysRoleQuery> qb) {
		// 将空串、空白串转为null，不影响mapper的判断
		if (StringUtils.isBlank(qb.getQueryBean().getRoleName())) {
			qb.getQueryBean().setRoleName(null);
		}
		return this.sysRoleComponent.getSysRolePaging(qb);
	}

	@Override
	public Long createSysRole(String roleName, String roleDesc) {
		Timestamp time = DateUtil.getCurrentTimestamp();
		SysRole sysRole = new SysRole();
		sysRole.setRoleName(roleName);
		sysRole.setRoleDesc(roleDesc);
		sysRole.setSts(SysConstants.Status.STS_A);
		sysRole.setStsTime(time);
		sysRole.setCreateTime(time);
		return this.sysRoleComponent.createSysRole(sysRole);
	}

	@Transactional(readOnly = true)
	@Override
	public SysRole getSysRole(Long roleId) {
		return this.sysRoleComponent.getSysRole(roleId);
	}

	@Override
	public void modifySysRole(Long roleId, String roleName, String roleDesc) {
		SysRole sysRole = new SysRole();
		sysRole.setRoleId(roleId);
		sysRole.setRoleName(roleName);
		sysRole.setRoleDesc(roleDesc);
		this.sysRoleComponent.updateSysRole(sysRole);
	}

	@Transactional(readOnly = true)
	@Override
	public List<FuncTree> loadFuncTree() {
		return this.functionComponent.initFuncTree();
	}

	@Override
	public void removeSysRole(Long roleId) {
		this.sysRoleComponent.removeSysRole(roleId);
	}

	@Transactional(readOnly = true)
	@Override
	public JSONArray loadFuncAuth(Long operUserId, Long roleId, Long nodeTreeId) {
		return this.sysRoleComponent.loadFuncAuth(operUserId, roleId, nodeTreeId);
	}

	@Override
	public void saveRoleFuncAuth(Long roleId, JSONArray funcAuthInfos) {
		this.sysRoleComponent.saveRoleFuncAuth(roleId, funcAuthInfos);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<RoleAllocUserCtrl>> getSysUserPaging(
			PagingQueryBean<SysUserQuery> qb) {
		if (false == qb.getQueryBean().isAuth()) {
			// 查询所有用户信息
			Long roleId = qb.getQueryBean().getRoleId();
			PagingResultBean<List<SysUser>> tempResult = this.sysUserComponent.getSysUserPaging(qb);
			PagingResultBean<List<RoleAllocUserCtrl>> result = new PagingResultBean<List<RoleAllocUserCtrl>>();
			List<RoleAllocUserCtrl> resultList = new ArrayList<RoleAllocUserCtrl>();
			for (SysUser sysUser : tempResult.getResultList()) {
				resultList.add(new RoleAllocUserCtrl(sysUser, this.sysRoleComponent.roleAuthUser(
						roleId, sysUser.getUserId())));
			}
			result.setResultList(resultList);
			result.setPagingInfo(tempResult.getPagingInfo());
			return result;
		} else {
			// 查询已关联用户
			PagingResultBean<List<SysUser>> tempResult = this.sysRoleComponent
					.getAuthSysUserPaging(qb);
			PagingResultBean<List<RoleAllocUserCtrl>> result = new PagingResultBean<List<RoleAllocUserCtrl>>();
			List<RoleAllocUserCtrl> resultList = new ArrayList<RoleAllocUserCtrl>();
			for (SysUser sysUser : tempResult.getResultList()) {
				// check属性一定是true
				resultList.add(new RoleAllocUserCtrl(sysUser, true));
			}
			result.setResultList(resultList);
			result.setPagingInfo(tempResult.getPagingInfo());
			return result;
		}
	}

	@Override
	public void saveRoleUserAuth(Long roleId, List<Long> retakeUsers, List<Long> grantUsers) {
		this.sysRoleComponent.saveRoleUserAuth(roleId, retakeUsers, grantUsers);
	}

	@Transactional(readOnly = true)
	@Override
	public JSONArray viewRoleFuncAuth(Long roleId, Long nodeTreeId) {
		return this.sysRoleComponent.viewRoleFuncAuth(roleId, nodeTreeId);
	}

}
