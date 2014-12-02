package com.cattsoft.baseplatform.func.sm.component;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.func.sm.entity.Staff;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.entity.query.StaffQuery;
import com.cattsoft.baseplatform.func.sm.entity.query.SysUserQuery;
import com.cattsoft.baseplatform.func.sm.persistence.StaffMapper;
import com.cattsoft.baseplatform.func.sm.persistence.SysDeptMapper;

public class StaffComponent {

	private StaffMapper staffMapper;

	private SysDeptMapper sysDeptMapper;

	public void setStaffMapper(StaffMapper staffMapper) {
		this.staffMapper = staffMapper;
	}

	public void setSysDeptMapper(SysDeptMapper sysDeptMapper) {
		this.sysDeptMapper = sysDeptMapper;
	}

	/**
	 * 依据员工名称、所属部门，模糊查询满足条件的员工列表
	 * 
	 * @param qb
	 * @return
	 */
	public PagingResultBean<List<Staff>> getStaffPaging(PagingQueryBean<StaffQuery> qb) {
		// 分页查询列表
		PagingResultBean<List<Staff>> result = new PagingResultBean<List<Staff>>();
		result.setResultList(this.staffMapper.selectPageStaff(qb));
		// 查询记录数
		qb.getPagingInfo().setTotalRows(this.staffMapper.selectCountStaff(qb));
		result.setPagingInfo(qb.getPagingInfo());
		return result;
	}
	
	/**
	 * 依据账号名称、所属部门，员工姓名模糊查询满足条件的员工列表
	 * 
	 * @param qb
	 * @return
	 */
	public PagingResultBean<List<Staff>> getSelectiveStaffPaging(PagingQueryBean<StaffQuery> qb) {
		// 分页查询列表
		PagingResultBean<List<Staff>> result = new PagingResultBean<List<Staff>>();
		result.setResultList(this.staffMapper.selectPageSelectiveStaff(qb));
		// 查询记录数
		qb.getPagingInfo().setTotalRows(this.staffMapper.selectCountSelectiveStaff(qb));
		result.setPagingInfo(qb.getPagingInfo());
		return result;
	}

	/**
	 * 验证员工编码是否被其他员工[在职、离职、停薪留职]使用
	 * 
	 * @param staffCode
	 * @return
	 */
	public boolean staffCodeBeenUsed(String staffCode) {
		return this.staffMapper.countStaffCode(staffCode).intValue() > 0;
	}

	/**
	 * 新增员工信息
	 * 
	 * @param staff
	 * @return
	 */
	public Long createStaff(Staff staff) {
		this.staffMapper.insert(staff);
		return staff.getStaffId();
	}

	/**
	 * 获取员工信息
	 * 
	 * @param staffId
	 * @return
	 */
	public Staff getStaff(Long staffId) {
		Staff staff = this.staffMapper.selectOne(staffId);
		staff.setSysDept(this.sysDeptMapper.selectOne(staff.getDeptId()));
		return staff;
	}

	/**
	 * 更新指定实例，staffId属性必须有值
	 * 
	 * @param staff
	 */
	public void updateStaff(Staff staff) {
		this.staffMapper.update(staff);
	}

	/**
	 * 加载员工的用户信息
	 * 
	 * @param staffId
	 * @return
	 */
	public List<SysUser> loadSysUsers(SysUserQuery sysUserQuery) {
		return this.staffMapper.selectSysUsers(sysUserQuery);
	}

}
