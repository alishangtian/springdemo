package com.cattsoft.baseplatform.func.sm.web;

import com.cattsoft.baseplatform.func.sm.entity.Staff;

/**
 * 声明需要获取员工信息。
 * 
 * @author wangcl
 */
public interface StaffAware {

	/**
	 * 用于注入员工（STAFF）信息。
	 * 
	 * @param staff
	 *            员工信息。
	 */
	void setStaff(Staff staff);
}
