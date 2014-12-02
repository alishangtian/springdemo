package com.cattsoft.baseplatform.func.sm.persistence;

import java.util.List;

import com.cattsoft.baseplatform.func.sm.entity.RoleFuncAlloc;
import com.cattsoft.baseplatform.func.sm.entity.query.RoleFuncAuthQuery;

public interface RoleFuncAllocMapper {

	void insert(RoleFuncAlloc roleFuncAlloc);

	/**
	 * 收回角色的功能授权
	 * 
	 * @param roleFuncAuthQuery
	 *            :roleId+funcNodeId
	 */
	void retake(RoleFuncAuthQuery roleFuncAuthQuery);

	/**
	 * 角色注销时，注销角色对应的功能授权信息
	 * 
	 * @param roleId
	 */
	void destroyRole(Long roleId);

	/**
	 * 获取角色在某功能目录下的所有功能授权信息
	 * 
	 * @param roleFuncAuthQuery
	 *            :roleId+nodeTreeId
	 * @return
	 */
	List<RoleFuncAlloc> selectAllocFuncs(RoleFuncAuthQuery roleFuncAuthQuery);

	/**
	 * 获取角色的指定功能的授权信息
	 * 
	 * @param roleFuncAlloc
	 *            ：roleId+funcNodeId
	 * @return
	 */
	RoleFuncAlloc selectAllocFunc(RoleFuncAlloc roleFuncAlloc);

	/**
	 * 更新角色功能授权的委派标识
	 * 
	 * @param roleFuncAlloc
	 *            ：roleFuncAllocId+designate
	 */
	void modifyDesignate(RoleFuncAlloc roleFuncAlloc);
}
