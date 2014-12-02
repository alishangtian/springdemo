package com.cattsoft.baseplatform.func.sm.web;

import java.util.Map;
import java.util.StringTokenizer;

import com.cattsoft.baseplatform.core.security.AuthorizationException;
import com.cattsoft.baseplatform.func.sm.entity.FuncNode;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.util.SysConstants;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * 用户鉴权拦截器，用于校验用户是否拥有合法权限。
 * 
 * @author wangcl
 */
public class AuthorizationInterceptor implements Interceptor {

	private static final long serialVersionUID = 8817152694913802510L;

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
		Action action = (Action) invocation.getAction();
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		SysUser user = (SysUser) session.get(SysConstants.LOGIN_USER);

		// struts action对象可能被CGLib增强，导致实际使用的是action对象的一个子类，
		// ClassName是一个CGLib形式的名称
		StringTokenizer st = new StringTokenizer(action.getClass().getName(), "$$");
		String actionName = st.nextToken();

		// 从用户拥有权限列表中匹配Action名称，如能匹配则证明有相应权限
		for (FuncNode node : user.getFuncList()) {
			if (actionName.equals(node.getActionName())) {
				return invocation.invoke();
			}
		}

		throw new AuthorizationException("对不起，你没有相应权限");
	}
}
