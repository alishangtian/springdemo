package com.cattsoft.baseplatform.func.sm.persistence;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.func.sm.entity.Staff;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.entity.query.StaffQuery;
import com.cattsoft.baseplatform.func.sm.entity.query.SysUserQuery;

public interface StaffMapper {
	void insert(Staff staff);

	void update(Staff staff);

	Staff selectOne(Long staffId);

	/**
	 * 依据员工名称，模糊查询满足条件的员工列表
	 * 
	 * @param qb
	 * @return
	 */
	List<Staff> selectPageStaff(PagingQueryBean<StaffQuery> qb);

	/**
	 * 依据员工名称，模糊查询满足条件的员工总数
	 * 
	 * @param qb
	 * @return
	 */
	Integer selectCountStaff(PagingQueryBean<StaffQuery> qb);
	
	/**
	 * 依据员工账号、所属部门，员工姓名模糊查询满足条件的员工列表
	 * @param qb
	 * @return
	 */
	List<Staff> selectPageSelectiveStaff(PagingQueryBean<StaffQuery> qb);
	
	Integer selectCountSelectiveStaff(PagingQueryBean<StaffQuery> qb);

	/**
	 * 获取员工编码的员工[在职、离职、停薪留职]总数
	 * 
	 * @param staffCode
	 * @return
	 */
	Integer countStaffCode(String staffCode);

	/**
	 * 获取员工的用户信息
	 * 
	 * @param staffId
	 * @return
	 */
	List<SysUser> selectSysUsers(SysUserQuery sysUserQuery);

}
