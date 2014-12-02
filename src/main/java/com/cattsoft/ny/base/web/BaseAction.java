package com.cattsoft.ny.base.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.util.SysConstants;
import com.cattsoft.baseplatform.web.action.PagingSupportAction;

/**
 * action类公共父类
 * 
 * @author maoxb
 * 
 */
public class BaseAction extends PagingSupportAction {
	private static final Logger log = Logger.getLogger(BaseAction.class);

	private static final long serialVersionUID = 1L;

	/**
	 * 获取字符串类型的登录用户id（customerId）
	 * 
	 * @return
	 */
	public String getStringLoginUserId() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			SysUser sysUser = (SysUser) request.getSession().getAttribute(
					SysConstants.LOGIN_USER);
			return sysUser.getLoginUserId();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 获取长整型的用户id
	 * 
	 * @return
	 */
	public Long getLongLoginUserId() {
		try {
			return Long.parseLong(getStringLoginUserId());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

}
