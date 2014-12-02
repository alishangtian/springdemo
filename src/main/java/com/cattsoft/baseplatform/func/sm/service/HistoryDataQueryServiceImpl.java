package com.cattsoft.baseplatform.func.sm.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.core.exception.ServiceException;
import com.cattsoft.baseplatform.func.sm.component.StaffComponent;
import com.cattsoft.baseplatform.func.sm.component.SysDeptComponent;
import com.cattsoft.baseplatform.func.sm.component.SysUserComponent;
import com.cattsoft.baseplatform.func.sm.entity.Staff;
import com.cattsoft.baseplatform.func.sm.entity.SysDept;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.entity.query.StaffQuery;
import com.cattsoft.baseplatform.func.sm.entity.query.SysDeptQuery;
import com.cattsoft.baseplatform.func.sm.entity.query.SysUserQuery;
import com.cattsoft.baseplatform.func.sm.util.SysConstants;

@Transactional
public class HistoryDataQueryServiceImpl implements HistoryDataQueryService {
	private SysUserComponent sysUserComponent;
	private StaffComponent staffComponent;
	private SysDeptComponent sysDeptComponent;

	public void setSysUserComponent(SysUserComponent sysUserComponent) {
		this.sysUserComponent = sysUserComponent;
	}

	public void setStaffComponent(StaffComponent staffComponent) {
		this.staffComponent = staffComponent;
	}

	public void setSysDeptComponent(SysDeptComponent sysDeptComponent) {
		this.sysDeptComponent = sysDeptComponent;
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
		// 用户资料历史查询的隐式查询条件
		sysUserQuery.setSts(SysConstants.Status.STS_P);
		sysUserQuery.setUserExpTime(null);
		return this.sysUserComponent.getSysUserPaging(qb);
	}

	@Transactional(readOnly = true)
	@Override
	public SysUser getSysUser(Long userId) {
		return this.sysUserComponent.getSysUser(userId);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<Staff>> getStaffPaging(PagingQueryBean<StaffQuery> qb) {
		if (StringUtils.isBlank(qb.getQueryBean().getStaffName())) {
			qb.getQueryBean().setStaffName(null);
		}
		// 员工历史资料功能隐含查询条件：查询历史的资料信息
		qb.getQueryBean().setHistory(true);
		return this.staffComponent.getStaffPaging(qb);
	}

	@Transactional(readOnly = true)
	@Override
	public Staff getStaff(Long staffId) {
		return this.staffComponent.getStaff(staffId);
	}

	@Transactional(readOnly = true)
	@Override
	public List<SysUser> loadStaffUsers(Long staffId) {
		SysUserQuery sysUserQuery = new SysUserQuery();
		sysUserQuery.setStaffId(staffId);
		/* 隐含条件：注销用户[员工已注销的前提下，员工的用户一定是注销状态] */
		sysUserQuery.setSts(SysConstants.Status.STS_P);
		return this.staffComponent.loadSysUsers(sysUserQuery);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<SysDept>> getSysDeptPaging(PagingQueryBean<SysDeptQuery> qb) {
		SysDeptQuery sysDeptQuery = qb.getQueryBean();
		// 将空串、空白串转为null，不影响mapper的判断
		if (StringUtils.isBlank(sysDeptQuery.getDeptName())) {
			sysDeptQuery.setDeptName(null);
		}
		if (StringUtils.isBlank(sysDeptQuery.getDeptCode())) {
			sysDeptQuery.setDeptCode(null);
		}
		/* 部门管理隐含查询条件 */
		sysDeptQuery.setSts(SysConstants.Status.STS_P);
		return this.sysDeptComponent.getSysDeptPaging(qb);
	}

	@Transactional(readOnly = true)
	@Override
	public List<SysUser> loadDeptUsers(Long deptId) {
		SysUserQuery sysUserQuery = new SysUserQuery();
		sysUserQuery.setDeptId(deptId);
		/* 隐含查询条件 */
		sysUserQuery.setSts(SysConstants.Status.STS_P);
		return this.sysDeptComponent.loadSysUsers(sysUserQuery);
	}

}
