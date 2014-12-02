package com.cattsoft.baseplatform.func.sm.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.func.sm.entity.Staff;
import com.cattsoft.baseplatform.func.sm.entity.SysDept;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.entity.query.StaffQuery;
import com.cattsoft.baseplatform.func.sm.entity.query.SysDeptQuery;
import com.cattsoft.baseplatform.func.sm.entity.query.SysUserQuery;

/**
 * 历史资料查询：用户历史资料、员工历史资料、部门历史资料
 * 
 * @author longtao
 * 
 */
public interface HistoryDataQueryService {
	/**
	 * 用户资料历史查询：依据用户所有者类型、用户所有者名称、用户名称查询满足条件的用户列表
	 * 
	 * @param qb
	 * @return
	 */
	public PagingResultBean<List<SysUser>> getSysUserPaging(PagingQueryBean<SysUserQuery> qb);

	/**
	 * 加载系统用户及用户对应的使用者信息
	 * 
	 * @param userId
	 * @return
	 */
	public SysUser getSysUser(Long userId);

	/**
	 * 员工历史资料查询：依据员工名称、所属部门，模糊查询满足条件的员工列表
	 * 
	 * @param qb
	 * @return
	 */
	public PagingResultBean<List<Staff>> getStaffPaging(PagingQueryBean<StaffQuery> qb);

	/**
	 * 获取员工信息
	 * 
	 * @param staffId
	 * @return
	 */
	public Staff getStaff(Long staffId);

	/**
	 * 员工历史资料查询：查询员工的曾用用户信息
	 * 
	 * @param staffId
	 * @return
	 */
	public List<SysUser> loadStaffUsers(Long staffId);

	/**
	 * 部门历史资料查询：依据部门名称，模糊查询满足条件的部门列表
	 * 
	 * @param qb
	 * @return
	 */
	public PagingResultBean<List<SysDept>> getSysDeptPaging(PagingQueryBean<SysDeptQuery> qb);

	/**
	 * 部门历史资料查询：查询部门的曾用用户信息
	 * 
	 * @param deptId
	 * @return
	 */
	public List<SysUser> loadDeptUsers(Long deptId);
}
