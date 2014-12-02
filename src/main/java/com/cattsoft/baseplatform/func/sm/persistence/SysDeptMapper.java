package com.cattsoft.baseplatform.func.sm.persistence;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.func.sm.entity.Staff;
import com.cattsoft.baseplatform.func.sm.entity.SysDept;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.entity.query.SysDeptQuery;
import com.cattsoft.baseplatform.func.sm.entity.query.SysUserQuery;

public interface SysDeptMapper {
	void insert(SysDept sysDept);

	void update(SysDept sysDept);

	SysDept selectOne(Long deptId);

	/**
	 * 依据部门编码、部门名称、上级部门查询所有在用部门
	 * 
	 * @param sysDept
	 * @return
	 */
	List<SysDept> selectList(SysDept sysDept);

	/**
	 * 查询指定部门及其子部门下的非离职员工总数
	 * 
	 * @param deptId
	 * @return
	 */
	Long countStaff(Long deptId);

	/**
	 * 依据部门名称，模糊查询满足条件的部门列表
	 * 
	 * @param qb
	 * @return
	 */
	List<SysDept> selectPageSysDept(PagingQueryBean<SysDeptQuery> qb);

	/**
	 * 依据部门名称，模糊查询满足条件的部门总数
	 * 
	 * @param qb
	 * @return
	 */
	Integer selectCountSysDept(PagingQueryBean<SysDeptQuery> qb);

	/**
	 * 获取部门编码为指定值的部门[在用+注销]总数
	 * 
	 * @param deptCode
	 * @return
	 */
	Integer countDeptCode(String deptCode);

	/**
	 * 查询直接隶属该部门下的非离职的员工
	 * 
	 * @param deptId
	 * @return
	 */
	List<Staff> selectStaffList(Long deptId);

	/**
	 * 查询部门的系统用户信息
	 * 
	 * @param sysUserQuery
	 * @return
	 */
	List<SysUser> selectSysUserList(SysUserQuery sysUserQuery);

	/**
	 * 查询指定部门及其所有下级部门的信息
	 * 
	 * @param deptId
	 * @return
	 */
	List<SysDept> selectCascadeSysDepts(Long deptId);

	/**
	 * 获取所有部门信息
	 * 
	 * @return
	 */
	List<SysDept> selectAllSysDeptData();

}
