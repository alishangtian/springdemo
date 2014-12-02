package com.cattsoft.baseplatform.func.sm.service;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.core.exception.ServiceException;
import com.cattsoft.baseplatform.core.util.DateUtil;
import com.cattsoft.baseplatform.func.sm.component.StaffComponent;
import com.cattsoft.baseplatform.func.sm.component.SysUserComponent;
import com.cattsoft.baseplatform.func.sm.entity.Staff;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.entity.query.StaffQuery;
import com.cattsoft.baseplatform.func.sm.entity.query.SysUserQuery;
import com.cattsoft.baseplatform.func.sm.util.SysConstants;

@Transactional
public class StaffServiceImpl implements StaffService {
	private StaffComponent staffComponent;
	private SysUserComponent sysUserComponent;

	public void setStaffComponent(StaffComponent staffComponent) {
		this.staffComponent = staffComponent;
	}

	public void setSysUserComponent(SysUserComponent sysUserComponent) {
		this.sysUserComponent = sysUserComponent;
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<Staff>> getStaffPaging(PagingQueryBean<StaffQuery> qb) {
		if (StringUtils.isBlank(qb.getQueryBean().getStaffName())) {
			qb.getQueryBean().setStaffName(null);
		}
		// 员工资料维护功能隐含查询条件：查询非历史的资料信息
		qb.getQueryBean().setHistory(false);
		return this.staffComponent.getStaffPaging(qb);
	}

	@Transactional(readOnly = true)
	@Override
	public boolean staffCodeBeenUsed(String staffCode) {
		if (StringUtils.isBlank(staffCode)) {
			throw new ServiceException("未指定员工编码，无需判定员工编码是否被使用");
		}
		return this.staffComponent.staffCodeBeenUsed(staffCode);
	}

	@Override
	public Long createStaff(Staff staff) {
		Timestamp time = DateUtil.getCurrentTimestamp();
		staff.setSts(SysConstants.Staff.Status.STS_A);
		staff.setStsTime(time);
		staff.setCreateTime(time);
		return this.staffComponent.createStaff(staff);
	}

	@Transactional(readOnly = true)
	@Override
	public Staff getStaff(Long staffId) {
		return this.staffComponent.getStaff(staffId);
	}

	@Override
	public void retainStaff(Long staffId) {
		Staff staff = new Staff();
		staff.setStaffId(staffId);
		staff.setSts(SysConstants.Staff.Status.STS_R);
		this.staffComponent.updateStaff(staff);
	}

	@Override
	public void removeStaff(Long staffId) {
		Staff staff = new Staff();
		staff.setStaffId(staffId);
		staff.setSts(SysConstants.Staff.Status.STS_P);
		this.staffComponent.updateStaff(staff);
		// 员工的用户列表
		SysUserQuery sysUserQuery = new SysUserQuery();
		sysUserQuery.setStaffId(staffId);
		/* 隐含条件：在用用户 */
		sysUserQuery.setSts(SysConstants.Status.STS_A);
		List<SysUser> sysUsers = this.staffComponent.loadSysUsers(sysUserQuery);
		for (SysUser sysUser : sysUsers) {
			this.sysUserComponent.removeSysUser(sysUser.getUserId());
		}
	}

	@Override
	public void modifyStaff(Staff staff) {
		this.staffComponent.updateStaff(staff);
	}

	@Transactional(readOnly = true)
	@Override
	public List<SysUser> loadSysUsers(Long staffId) {
		SysUserQuery sysUserQuery = new SysUserQuery();
		sysUserQuery.setStaffId(staffId);
		/* 隐含条件：在用用户 */
		sysUserQuery.setSts(SysConstants.Status.STS_A);
		return this.staffComponent.loadSysUsers(sysUserQuery);
	}

	@Override
	public void removeSysUsers(List<Long> userIds) {
		for (Long userId : userIds) {
			this.sysUserComponent.removeSysUser(userId);
		}
	}

	@Override
	public PagingResultBean<List<Staff>> getSelectiveStaffPaging(
			PagingQueryBean<StaffQuery> qb) {
		return this.staffComponent.getSelectiveStaffPaging(qb);
	}
}
