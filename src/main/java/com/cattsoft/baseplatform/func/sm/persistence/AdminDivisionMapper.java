package com.cattsoft.baseplatform.func.sm.persistence;

import java.util.List;

import com.cattsoft.baseplatform.func.sm.entity.AdminDivision;

public interface AdminDivisionMapper {
	/**
	 * 获取行政区划根节点
	 * 
	 * @return
	 */
	AdminDivision getRootDivision();

	/**
	 * 获取行政区划
	 * 
	 * @return
	 */
	AdminDivision getDivision(Long divisionId);

	/**
	 * 依据代码获取行政区划
	 * 
	 * @return
	 */
	AdminDivision getDivisionByCode(String divisionCode);

	/**
	 * 获取行政区划的直属下级区划
	 * 
	 * @param divisionPid
	 * @return
	 */
	List<AdminDivision> getSubDivisions(Long divisionId);

	/**
	 * 新增行政区划
	 * 
	 * @param adminDivison
	 */
	void insert(AdminDivision adminDivision);

	/**
	 * 更新行政区划
	 * 
	 * @param adminDivision
	 */
	void update(AdminDivision adminDivision);

	/**
	 * 获取行政区划及其所有下级区划
	 * 
	 * @param divisionId
	 * @return
	 */
	List<AdminDivision> getDivisionCascade(Long divisionId);

}
