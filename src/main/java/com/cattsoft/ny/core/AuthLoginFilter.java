package com.cattsoft.ny.core;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cattsoft.baseplatform.func.sm.util.SysConstants;

/**
 * 用户认证拦截器，用于校验用户是否是登录的合法用户，并对实现了特定接口的Action注入相应的依赖对象。
 * 
 * @author wangcl
 */
public class AuthLoginFilter implements Filter {

	private static final long serialVersionUID = -6602526366645509733L;
	
	private static final Log log = LogFactory.getLog(AuthLoginFilter.class);
	
	private String contextPath = null;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		contextPath = filterConfig.getServletContext().getContextPath()+"/";
	}

	@Override
	public void doFilter(ServletRequest inRequest, ServletResponse inResponse,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request =  (HttpServletRequest)inRequest;
		HttpServletResponse response = (HttpServletResponse) inResponse;
		String requestURI = request.getRequestURI();
		try {
			if(requestURI==null||requestURI.equals("")||requestURI.equals(contextPath)){
				HttpSession session = request.getSession();
				Object user =  session.getAttribute(SysConstants.LOGIN_USER);
				RequestDispatcher rd = null;
				if(user == null)
					rd = request.getRequestDispatcher("/pages/login.jsp");
				else
					rd = request.getRequestDispatcher("/pages/main.jsp");
				rd.forward(request, response);
			}else{
				chain.doFilter(request, response);
			}
		} catch (Exception e) {
			log.error(AuthLoginFilter.class,e);
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
