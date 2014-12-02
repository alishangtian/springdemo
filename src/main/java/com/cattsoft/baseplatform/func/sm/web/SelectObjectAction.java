package com.cattsoft.baseplatform.func.sm.web;

import com.cattsoft.baseplatform.web.action.Action;

/**
 * 选择对象
 */
public class SelectObjectAction extends Action {
	private static final long serialVersionUID = -6776851689195480187L;

	/**
	 * 选择单用户
	 * 
	 * @return
	 */
	public String selectOneUser() {
		return "selectOneUser";
	}

	/**
	 * 选择多用户
	 * 
	 * @return
	 */
	public String selectManyUser() {
		return "selectManyUser";
	}

	/**
	 * 选择单部门
	 * 
	 * @return
	 */
	public String selectOneDept() {
		return "selectOneDept";
	}

	/**
	 * 选择多部门
	 * 
	 * @return
	 */
	public String selectManyDept() {
		return "selectManyDept";
	}
}