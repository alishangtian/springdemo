package com.cattsoft.baseplatform.func.sm.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.cattsoft.baseplatform.core.util.DateUtil;
import com.cattsoft.baseplatform.func.sm.component.AdminDivisionComponent;
import com.cattsoft.baseplatform.func.sm.entity.AdminDivision;
import com.cattsoft.baseplatform.func.sm.util.SysConstants;

@Transactional
public class AdminDivisionServiceImpl implements AdminDivisionService {
	private AdminDivisionComponent adminDivisionComponent;

	public void setAdminDivisionComponent(AdminDivisionComponent adminDivisionComponent) {
		this.adminDivisionComponent = adminDivisionComponent;
	}

	@Transactional(readOnly = true)
	@Override
	public AdminDivision getRootDivision() {
		return this.adminDivisionComponent.getRootDivision();
	}

	@Transactional(readOnly = true)
	@Override
	public AdminDivision getDivision(Long divisionId) {
		return this.adminDivisionComponent.getDivision(divisionId);
	}

	@Transactional(readOnly = true)
	@Override
	public List<AdminDivision> getSubDivisions(Long divisionId) {
		return this.adminDivisionComponent.getSubDivisions(divisionId);
	}

	@Override
	public void createDivision(AdminDivision adminDivision) {
		Timestamp time = DateUtil.getCurrentTimestamp();
		adminDivision.setSts(SysConstants.Status.STS_A);
		adminDivision.setStsTime(time);
		adminDivision.setCreateTime(time);
		adminDivisionComponent.createDivision(adminDivision);
	}

	@Override
	public void modifyDivision(AdminDivision adminDivision) {
		this.adminDivisionComponent.updateDivision(adminDivision);
	}

	@Override
	public void removeDivision(Long divisionId) {
		List<AdminDivision> divisions = this.adminDivisionComponent.getDivisionCascade(divisionId);
		for (AdminDivision division : divisions) {
			division.setSts(SysConstants.Status.STS_P);
			this.adminDivisionComponent.updateDivision(division);
		}
	}

	@Override
	public AdminDivision getDivisionByCode(String divisionCode) {
		return this.adminDivisionComponent.getDivisionByCode(divisionCode);
	}

}
