package com.cattsoft.baseplatform.func.sm.service;

import java.util.List;

import com.cattsoft.baseplatform.func.sm.entity.AdminDivision;

public interface AdminDivisionService {
	/**
	 * 获取行政区划根节点
	 * 
	 * @return
	 */
	AdminDivision getRootDivision();

	/**
	 * 获取行政区划
	 * 
	 * @param divisionId
	 * @return
	 */
	AdminDivision getDivision(Long divisionId);

	/**
	 * 获取行政区划的直属下级区划
	 * 
	 * @param divisionId
	 * @return
	 */
	List<AdminDivision> getSubDivisions(Long divisionId);

	/**
	 * 新增行政区划
	 * 
	 * @param adminDivision
	 */
	void createDivision(AdminDivision adminDivision);

	/**
	 * 更新行政区划
	 * 
	 * @param adminDivision
	 */
	void modifyDivision(AdminDivision adminDivision);

	/**
	 * 删除新增区划：级联删除所有下级区划
	 * 
	 * @param divisionId
	 */
	void removeDivision(Long divisionId);

	/**
	 * 依据区划代码获取行政区划信息
	 * 
	 * @param divisionCode
	 * @return
	 */
	AdminDivision getDivisionByCode(String divisionCode);
}
