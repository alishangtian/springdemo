package com.cattsoft.baseplatform.func.sm.web;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.cattsoft.baseplatform.core.annotation.Authorization;
import com.cattsoft.baseplatform.core.security.AuthorizationException;
import com.cattsoft.baseplatform.func.sm.entity.FuncItem;
import com.cattsoft.baseplatform.func.sm.entity.FuncNode;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;

/**
 * 用户功能权限校验的切面，提供对标注了@Authorization注解的Action方法的横切。
 * <p>
 * 与<code>AuthenticationInterceptor</code>和<code>SysUserAware</code>结合使用。
 * 因为用户功能权限校验需要登录用户（及其功能权限）信息，而登录用户是保留在<code>ServletHttpSession</code>
 * 对象中，因此需要action对象实现 <code>SysUserAware</code>接口，由
 * <code>AuthenticationInterceptor</code>拦截器为action注入<code>SysUser</code>
 * 对象，从而获取登录用户信息。
 * </p>
 * 
 * @author wangcl
 */
@Aspect
public class AuthorizationAspect {
	private static Log log = LogFactory.getLog(AuthorizationAspect.class);

	/**
	 * 切入Action所有用@Authorization注解标注的方法。
	 * 
	 * @param jp
	 *            连接点对象。
	 * @param authorization
	 *            Authorization注解。
	 */
	@Before("com.cattsoft.baseplatform.core.aop.AppArchitectureAspect.inWebLayer() && @annotation(authorization)")
	public void authorize(JoinPoint jp, Authorization authorization) {
		Object target = jp.getTarget();
		if (target instanceof SysUserAware) {
			String method = jp.getSignature().getName();
			String action = jp.getTarget().getClass().getName();
			SysUser user = ((SysUserAware) target).getSysUser();
			log.debug("[action:" + action + "][method:" + method + "][sysUserId:"
					+ user.getUserId() + "]");

			/*
			 * 校验方法：<br>
			 * 1.根据Action名称和方法名称在用户FuncNode授权结果中进行匹配，如果匹配成功，说明有权限；否则继续下一步骤；<br>
			 * 2.根据Action名称和方法名称在用户FuncItem授权结果中进行匹配，如果匹配成功，说明有权限，否则没有权限。<br>
			 * 注：此处实现在遍历FuncNode的同时遍历其下的FuncItem列表。
			 */
			for (FuncNode node : user.getFuncList()) {
				if (action.equals(node.getActionName()) && (method.equals(node.getMethodName()))) {
					return;
				} else {
					List<FuncItem> items = node.getFuncItemList();
					for (FuncItem item : items) {
						if (action.equals(item.getActionName())
								&& method.equals(item.getMethodName())) {
							return;
						}
					}
				}
			}
			throw new AuthorizationException("对不起，你没有相应权限");
		} else {
			throw new RuntimeException("需要权限验证的Action必须实现SysUserAware接口");
		}
	}

}
