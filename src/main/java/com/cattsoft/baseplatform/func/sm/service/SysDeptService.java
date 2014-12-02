package com.cattsoft.baseplatform.func.sm.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.func.sm.entity.Staff;
import com.cattsoft.baseplatform.func.sm.entity.SysDept;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.entity.query.SysDeptQuery;

public interface SysDeptService {
	/**
	 * 依据部门名称，模糊查询满足条件的部门列表
	 * 
	 * @param qb
	 * @return
	 */
	public PagingResultBean<List<SysDept>> getSysDeptPaging(
			PagingQueryBean<SysDeptQuery> qb);

	/**
	 * 获取最顶层的组织部门根节点[0]
	 * 
	 * @return
	 */
	public SysDept getRootSysDept();

	/**
	 * 获取部门的直属子部门列表
	 * 
	 * @param deptId
	 * @return
	 */
	public List<SysDept> getChildrenSysDept(Long deptId);

	/**
	 * 验证部门编码是否被其他部门[在用+注销]使用
	 * 
	 * @param deptCode
	 * @return
	 */
	public boolean deptCodeBeenUsed(String deptCode);

	/**
	 * 创建部门
	 * 
	 * @param sysDept
	 * @return
	 */
	public Long createSysDept(SysDept sysDept);

	/**
	 * 获取部门的详细信息
	 * 
	 * @param deptId
	 * @return
	 */
	public SysDept getSysDept(Long deptId);

	/**
	 * 部门信息更新
	 * 
	 * @param sysDept
	 */
	public void modifySysDept(SysDept sysDept);

	/**
	 * 部门或其全部下级部门下存在非离职的员工
	 * 
	 * @param deptId
	 * @return
	 */
	public boolean hasStaff(Long deptId);

	/**
	 * 查询直接隶属该部门下的非离职的员工
	 * 
	 * @param deptId
	 * @return
	 */
	public List<Staff> loadStaffs(Long deptId);

	/**
	 * 查询部门的所有在用、未失效的系统用户
	 * 
	 * @param deptId
	 * @return
	 */
	public List<SysUser> loadSysUsers(Long deptId);

	/**
	 * 注销部门下的用户
	 * 
	 * @param userIds
	 */
	public void removeSysUsers(List<Long> userIds);

	/**
	 * 注销部门：部门自身及其下级部门，以及这些部门下的用户
	 * 
	 * @param deptId
	 */
	public void removeSysDept(Long deptId);

	/**
	 * 获取所有部门信息
	 * 
	 * @return
	 */
	public List<SysDept> getAllSysDeptData();
}
