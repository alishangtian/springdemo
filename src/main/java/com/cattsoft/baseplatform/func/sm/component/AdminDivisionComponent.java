package com.cattsoft.baseplatform.func.sm.component;

import java.util.List;

import com.cattsoft.baseplatform.func.sm.entity.AdminDivision;
import com.cattsoft.baseplatform.func.sm.persistence.AdminDivisionMapper;

public class AdminDivisionComponent {
	private AdminDivisionMapper adminDivisionMapper;

	public void setAdminDivisionMapper(AdminDivisionMapper adminDivisionMapper) {
		this.adminDivisionMapper = adminDivisionMapper;
	}

	/**
	 * 获取行政区划根节点
	 * 
	 * @return
	 */
	public AdminDivision getRootDivision() {
		return this.adminDivisionMapper.getRootDivision();
	}

	/**
	 * 获取行政区划
	 * 
	 * @param divisionId
	 * @return
	 */
	public AdminDivision getDivision(Long divisionId) {
		return this.adminDivisionMapper.getDivision(divisionId);
	}

	/**
	 * 获取行政区划的直属下级区划
	 * 
	 * @param divisionId
	 * @return
	 */
	public List<AdminDivision> getSubDivisions(Long divisionId) {
		return this.adminDivisionMapper.getSubDivisions(divisionId);
	}

	/**
	 * 新增行政区划
	 * 
	 * @param adminDivision
	 */
	public void createDivision(AdminDivision adminDivision) {
		this.adminDivisionMapper.insert(adminDivision);
	}

	/**
	 * 更新行政区划
	 * 
	 * @param adminDivision
	 */
	public void updateDivision(AdminDivision adminDivision) {
		this.adminDivisionMapper.update(adminDivision);
	}

	/**
	 * 获取行政区划及其所有下级区划
	 * 
	 * @param divisionId
	 * @return
	 */
	public List<AdminDivision> getDivisionCascade(Long divisionId) {
		return this.adminDivisionMapper.getDivisionCascade(divisionId);
	}

	/**
	 * 依据区划代码获取行政区划信息
	 * 
	 * @param divisionCode
	 * @return
	 */
	public AdminDivision getDivisionByCode(String divisionCode) {
		return this.adminDivisionMapper.getDivisionByCode(divisionCode);
	}
}
