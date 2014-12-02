package com.cattsoft.baseplatform.func.sm.service;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.core.exception.ServiceException;
import com.cattsoft.baseplatform.core.util.DateUtil;
import com.cattsoft.baseplatform.func.sm.component.SysDeptComponent;
import com.cattsoft.baseplatform.func.sm.component.SysUserComponent;
import com.cattsoft.baseplatform.func.sm.entity.Staff;
import com.cattsoft.baseplatform.func.sm.entity.SysDept;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.entity.query.SysDeptQuery;
import com.cattsoft.baseplatform.func.sm.entity.query.SysUserQuery;
import com.cattsoft.baseplatform.func.sm.util.SysConstants;

@Transactional
public class SysDeptServiceImpl implements SysDeptService {
	private SysDeptComponent sysDeptComponent;
	private SysUserComponent sysUserComponent;

	public void setSysDeptComponent(SysDeptComponent sysDeptComponent) {
		this.sysDeptComponent = sysDeptComponent;
	}

	public void setSysUserComponent(SysUserComponent sysUserComponent) {
		this.sysUserComponent = sysUserComponent;
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<SysDept>> getSysDeptPaging(
			PagingQueryBean<SysDeptQuery> qb) {
		SysDeptQuery sysDeptQuery = qb.getQueryBean();
		// 将空串、空白串转为null，不影响mapper的判断
		if (StringUtils.isBlank(sysDeptQuery.getDeptName())) {
			sysDeptQuery.setDeptName(null);
		}
		/* 部门管理隐含查询条件 */
		sysDeptQuery.setSts(SysConstants.Status.STS_A);
		return this.sysDeptComponent.getSysDeptPaging(qb);
	}

	@Transactional(readOnly = true)
	@Override
	public SysDept getRootSysDept() {
		return this.sysDeptComponent.getSysDept(0L);
	}

	@Transactional(readOnly = true)
	@Override
	public List<SysDept> getChildrenSysDept(Long deptId) {
		SysDept sysDept = new SysDept();
		sysDept.setSuperDeptId(deptId);
		return this.sysDeptComponent.getSysDeptList(sysDept);
	}

	@Transactional(readOnly = true)
	@Override
	public boolean deptCodeBeenUsed(String deptCode) {
		if (StringUtils.isBlank(deptCode)) {
			throw new ServiceException("部门编码为空，无法验证编码是否被占用");
		}
		return this.sysDeptComponent.deptCodeBeenUsed(deptCode);
	}

	@Override
	public Long createSysDept(SysDept sysDept) {
		Timestamp time = DateUtil.getCurrentTimestamp();
		sysDept.setSts(SysConstants.Status.STS_A);
		sysDept.setStsTime(time);
		sysDept.setCreateTime(time);
		return this.sysDeptComponent.createSysDept(sysDept);
	}

	@Transactional(readOnly = true)
	@Override
	public SysDept getSysDept(Long deptId) {
		return this.sysDeptComponent.getSysDept(deptId);
	}

	@Override
	public void modifySysDept(SysDept sysDept) {
		this.sysDeptComponent.updateSysDept(sysDept);
	}

	@Transactional(readOnly = true)
	@Override
	public boolean hasStaff(Long deptId) {
		return this.sysDeptComponent.staffCount(deptId).compareTo(0L) > 0;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Staff> loadStaffs(Long deptId) {
		return this.sysDeptComponent.loadStaffs(deptId);
	}

	@Transactional(readOnly = true)
	@Override
	public List<SysUser> loadSysUsers(Long deptId) {
		SysUserQuery sysUserQuery = new SysUserQuery();
		sysUserQuery.setDeptId(deptId);
		/* 隐含查询条件 */
		sysUserQuery.setSts(SysConstants.Status.STS_A);
		return this.sysDeptComponent.loadSysUsers(sysUserQuery);
	}

	@Override
	public void removeSysUsers(List<Long> userIds) {
		for (Long userId : userIds) {
			this.sysUserComponent.removeSysUser(userId);
		}
	}

	@Override
	public void removeSysDept(Long deptId) {
		// 部门及其所有下级部门列表
		List<SysDept> sysDepts = this.sysDeptComponent
				.getCascadeSysDepts(deptId);
		for (SysDept sysDept : sysDepts) {
			// 注销部门信息
			sysDept.setSts(SysConstants.Status.STS_P);
			this.sysDeptComponent.updateSysDept(sysDept);
			// 注销部门下的用户信息
			SysUserQuery sysUserQuery = new SysUserQuery();
			sysUserQuery.setDeptId(sysDept.getDeptId());
			sysUserQuery.setSts(SysConstants.Status.STS_A);
			List<SysUser> sysUsers = this.sysDeptComponent
					.loadSysUsers(sysUserQuery);
			for (SysUser sysUser : sysUsers) {
				this.sysUserComponent.removeSysUser(sysUser.getUserId());
			}
		}
	}

	/**
	 * 获取所有部门信息
	 * 
	 * @return
	 */
	public List<SysDept> getAllSysDeptData() {
		return this.sysDeptComponent.getAllSysDeptData();
	}
}
