package com.cattsoft.baseplatform.func.sm.component;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.func.sm.entity.Staff;
import com.cattsoft.baseplatform.func.sm.entity.SysDept;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.entity.query.SysDeptQuery;
import com.cattsoft.baseplatform.func.sm.entity.query.SysUserQuery;
import com.cattsoft.baseplatform.func.sm.persistence.SysDeptMapper;

public class SysDeptComponent {
	private SysDeptMapper sysDeptMapper;

	public void setSysDeptMapper(SysDeptMapper sysDeptMapper) {
		this.sysDeptMapper = sysDeptMapper;
	}

	/**
	 * 依据部门名称，模糊查询满足条件的部门列表
	 * 
	 * @param qb
	 * @return
	 */
	public PagingResultBean<List<SysDept>> getSysDeptPaging(
			PagingQueryBean<SysDeptQuery> qb) {
		// 分页查询列表
		PagingResultBean<List<SysDept>> result = new PagingResultBean<List<SysDept>>();
		result.setResultList(this.sysDeptMapper.selectPageSysDept(qb));
		// 查询记录数
		qb.getPagingInfo().setTotalRows(
				this.sysDeptMapper.selectCountSysDept(qb));
		result.setPagingInfo(qb.getPagingInfo());
		return result;
	}

	/**
	 * 验证部门编码是否被其他部门[在用+注销]使用
	 * 
	 * @param deptCode
	 * @return
	 */
	public boolean deptCodeBeenUsed(String deptCode) {
		return this.sysDeptMapper.countDeptCode(deptCode).intValue() > 0;
	}

	/**
	 * 创建部门
	 * 
	 * @param sysDept
	 * @return
	 */
	public Long createSysDept(SysDept sysDept) {
		this.sysDeptMapper.insert(sysDept);
		return sysDept.getDeptId();
	}

	/**
	 * 部门信息更新
	 * 
	 * @param sysDept
	 */
	public void updateSysDept(SysDept sysDept) {
		this.sysDeptMapper.update(sysDept);
	}

	/**
	 * 部门详细信息
	 * 
	 * @param deptId
	 * @return
	 */
	public SysDept getSysDept(Long deptId) {
		return this.sysDeptMapper.selectOne(deptId);
	}

	/**
	 * 查询部门列表：superDeptId+deptCode+deptName
	 * 
	 * @param sysDept
	 * @return
	 */
	public List<SysDept> getSysDeptList(SysDept sysDept) {
		return this.sysDeptMapper.selectList(sysDept);
	}

	/**
	 * 部门及其子部门下的非离职员工总数
	 * 
	 * @param deptId
	 * @return
	 */
	public Long staffCount(Long deptId) {
		return this.sysDeptMapper.countStaff(deptId);
	}

	/**
	 * 查询直接隶属该部门下的非离职的员工
	 * 
	 * @param deptId
	 * @return
	 */
	public List<Staff> loadStaffs(Long deptId) {
		return this.sysDeptMapper.selectStaffList(deptId);
	}

	/**
	 * 查询部门的所有系统用户信息
	 * 
	 * @param sysUserQuery
	 * @return
	 */
	public List<SysUser> loadSysUsers(SysUserQuery sysUserQuery) {
		return this.sysDeptMapper.selectSysUserList(sysUserQuery);
	}

	/**
	 * 查询指定部门及其所有下级部门的信息
	 * 
	 * @param deptId
	 * @return
	 */
	public List<SysDept> getCascadeSysDepts(Long deptId) {
		return this.sysDeptMapper.selectCascadeSysDepts(deptId);
	}

	/**
	 * 获取所有部门信息
	 * 
	 * @return
	 */
	public List<SysDept> getAllSysDeptData() {
		return this.sysDeptMapper.selectAllSysDeptData();
	}
}
