package com.cattsoft.baseplatform.func.sm.web;

import java.util.Map;

import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.util.SysConstants;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * 用户认证拦截器，用于校验用户是否是登录的合法用户，并对实现了特定接口的Action注入相应的依赖对象。
 * 
 * @author wangcl
 */
public class AuthenticationInterceptor implements Interceptor {

	private static final long serialVersionUID = -6602526366645509733L;

	public static final String LOGIN_PAGE = "login";

	@Override
	public void destroy() {
		// Nothing to do at this time.
	}

	@Override
	public void init() {
		// Nothing to do at this time.
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> session = invocation.getInvocationContext().getSession();

		SysUser user = (SysUser) session.get(SysConstants.LOGIN_USER);

		if (user == null) {
			// 如果未登录，则退回登录页面
			return LOGIN_PAGE;
		} else {
			Object action = invocation.getAction();

			if (action instanceof StaffAware) {
				// 为实现了StaffAware接口的Action注入Staff对象
				((StaffAware) action).setStaff(user.getStaff());
			}

			if (action instanceof SysUserAware) {
				// 为实现了SysUserAware接口的Action注入SysUser对象
				((SysUserAware) action).setSysUser(user);
			}

			return invocation.invoke();
		}
	}
}
