package com.cattsoft.baseplatform.func.sm.web;

import com.cattsoft.baseplatform.func.sm.entity.Staff;
import com.cattsoft.baseplatform.web.action.CrudAction;

/**
 * 功能模块通用的增删改查基类。
 * 
 * @author wangcl
 */
public abstract class CrudFuncAction<T> extends CrudAction<T> implements StaffAware {
	private static final long serialVersionUID = 795768637875887217L;

	private Staff staff;

	public Staff getStaff() {
		return staff;
	}

	@Override
	public void setStaff(Staff staff) {
		this.staff = staff;
	}

}
