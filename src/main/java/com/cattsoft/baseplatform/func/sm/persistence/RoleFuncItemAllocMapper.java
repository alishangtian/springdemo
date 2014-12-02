package com.cattsoft.baseplatform.func.sm.persistence;

import java.util.List;

import com.cattsoft.baseplatform.func.sm.entity.RoleFuncItemAlloc;
import com.cattsoft.baseplatform.func.sm.entity.query.RoleFuncAuthQuery;

public interface RoleFuncItemAllocMapper {

	void insert(RoleFuncItemAlloc roleFuncItemAlloc);

	/**
	 * 收回用户的功能下的某个功能操作或所有功能操作的授权信息
	 * 
	 * @param roleFuncAuthQuer
	 *            :roleId+funcNodeId+funcItemId[可空]
	 */
	void retake(RoleFuncAuthQuery roleFuncAuthQuery);

	/**
	 * 角色注销时，注销角色对应的功能操作授权信息
	 * 
	 * @param roleId
	 */
	void destroyRole(Long roleId);

	/**
	 * 获取角色在指定功能下的所有功能操作授权信息
	 * 
	 * @param roleFuncAuthQuery
	 *            ：roleId+funcNodeId
	 * @return
	 */
	List<RoleFuncItemAlloc> selectAllocFuncItems(RoleFuncAuthQuery roleFuncAuthQuery);
}
