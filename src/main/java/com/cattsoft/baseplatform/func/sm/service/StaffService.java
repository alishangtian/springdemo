package com.cattsoft.baseplatform.func.sm.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.func.sm.entity.Staff;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.entity.query.StaffQuery;

public interface StaffService {
	/**
	 * 依据员工名称、所属部门，模糊查询满足条件的员工列表
	 * 
	 * @param qb
	 * @return
	 */
	public PagingResultBean<List<Staff>> getStaffPaging(PagingQueryBean<StaffQuery> qb);
	
	/**
	 * 依据员工账号、所属部门，员工姓名模糊查询满足条件的员工列表
	 * 
	 * @param qb
	 * @return
	 */
	public PagingResultBean<List<Staff>> getSelectiveStaffPaging(PagingQueryBean<StaffQuery> qb);

	/**
	 * 验证员工编码是否被其他员工[在职、离职、停薪留职]使用
	 * 
	 * @param staffCode
	 * @return
	 */
	public boolean staffCodeBeenUsed(String staffCode);

	/**
	 * 新增员工信息
	 * 
	 * @param staff
	 * @return
	 */
	public Long createStaff(Staff staff);

	/**
	 * 获取员工信息
	 * 
	 * @param staffId
	 * @return
	 */
	public Staff getStaff(Long staffId);

	/**
	 * 停薪留职
	 * 
	 * @param staffId
	 */
	public void retainStaff(Long staffId);

	/**
	 * 离职
	 * 
	 * @param staffId
	 */
	public void removeStaff(Long staffId);

	/**
	 * 更新员工资料
	 * 
	 * @param staff
	 */
	public void modifyStaff(Staff staff);

	/**
	 * 加载员工的所有在用用户信息
	 * 
	 * @param staffId
	 * @return
	 */
	public List<SysUser> loadSysUsers(Long staffId);

	/**
	 * 注销员工的用户信息
	 * 
	 * @param userIds
	 */
	public void removeSysUsers(List<Long> userIds);

}
