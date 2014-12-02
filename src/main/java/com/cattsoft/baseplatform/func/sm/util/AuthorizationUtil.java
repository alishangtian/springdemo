package com.cattsoft.baseplatform.func.sm.util;

import com.cattsoft.baseplatform.func.sm.entity.FuncItem;
import com.cattsoft.baseplatform.func.sm.entity.FuncNode;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;

/**
 * 授权涉及到的公共方法
 * 
 * @author longtao
 * 
 */
public class AuthorizationUtil {

	/**
	 * 判断用户是否具备指定的功能操作权限
	 * 
	 * @param funcItemId
	 * @param sysUser
	 * @return boolean (<b>true</b> or <b>false</b>)
	 */
	public static boolean funcItemAuthorize(Long funcItemId, SysUser sysUser) {
		if (null == funcItemId || null == sysUser || null == sysUser.getFuncList()) {
			return false;
		}
		for (FuncNode funcNode : sysUser.getFuncList()) {
			for (FuncItem funcItem : funcNode.getFuncItemList()) {
				if (funcItem.getFuncItemId().equals(funcItemId)) {
					return true;
				}
			}
		}
		return false;
	}
}
