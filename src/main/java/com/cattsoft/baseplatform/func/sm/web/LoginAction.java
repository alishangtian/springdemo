package com.cattsoft.baseplatform.func.sm.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.cattsoft.baseplatform.cache.SysParamCacheManager;
import com.cattsoft.baseplatform.core.exception.AuthenticationException;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.service.SysUserService;
import com.cattsoft.baseplatform.func.sm.util.HttpRequestUtil;
import com.cattsoft.baseplatform.func.sm.util.SysConstants;
import com.cattsoft.baseplatform.log.util.BPDBLogger;
import com.cattsoft.baseplatform.log.util.BPFileLogger;
import com.cattsoft.baseplatform.ui.pub.Constants;
import com.cattsoft.baseplatform.web.action.Action;

/**
 * 用户登录Action。
 * 
 * @author wangcl
 */
public class LoginAction extends Action implements SessionAware, ServletRequestAware {
	private static final long serialVersionUID = -7907500628105377533L;
	
	private static final Log log = LogFactory.getLog(LoginAction.class);
	
	private SysParamCacheManager sysParamCacheManager;
	// 用户认证服务
	private SysUserService ideaUserService;

	private Map<String, Object> session;
	private HttpServletRequest request;

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setIdeaUserService(SysUserService ideaUserService) {
		this.ideaUserService = ideaUserService;
	}

	public void setSysParamCacheManager(SysParamCacheManager sysParamCacheManager) {
		this.sysParamCacheManager = sysParamCacheManager;
	}

	// ---------------------------------------------------------------------------------------------
	// 登录用户名
	private String userName;
	// 登录密码
	private String password;
	// 验证码
	private String verifyCode;
	//结果数据
	private String resultMsg = null;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	/**
	 * 用户登录处理:<br>
	 * 1. 校验验证码<br>
	 * 2. 校验用户名、密码<br>
	 * 3. 将登录用户对应的员工信息存储在session中
	 * 
	 * @return 系统主页
	 */
	@Override
	public String execute() {
		if (StringUtils.isBlank(userName))
			resultMsg = "用户名不能为空";
		if (StringUtils.isBlank(password))
			resultMsg = "密码不能为空";
		String code = (String) request.getSession().getAttribute(Constants.DT_VERIFYCODE);
		if (!verifyCode.equals(code))
			resultMsg = "验证码错误";
		
		if(StringUtils.isNotEmpty(resultMsg))
			return SUCCESS;
		try {
			SysUser user = ideaUserService.authenticate(userName, password);
			/* 写登录日志：是否入库 */
			if (SysConstants.BooleanFlag.TRUE.equals(sysParamCacheManager
					.getEnableParamValue(SysConstants.SysParamCode.LOGIN_LOG_DB))) {
				BPDBLogger.infoLoginLog(request.getSession().getId(), user.getUserOwnerName(),
						user.getLoginName(), HttpRequestUtil.getIpAddr(request));
			}
			/* 写登录日志：是否写文件 */
			if (SysConstants.BooleanFlag.TRUE.equals(sysParamCacheManager
					.getEnableParamValue(SysConstants.SysParamCode.LOGIN_LOG_FILE))) {
				BPFileLogger.infoLoginLog(request.getSession().getId(), user.getUserOwnerName(),
						user.getLoginName(), HttpRequestUtil.getIpAddr(request));
			}
			/* 将登录用户信息存储在session中 */
			session.put(SysConstants.LOGIN_USER, user);
		} catch (AuthenticationException e) {
			resultMsg = "账号密码错误，请重试！";
			log.error(LoginAction.class, e);
		}
		return SUCCESS;
	}

	public String loginOut() {
		try {
			/* 写登出日志：是否入库 */
			if (SysConstants.BooleanFlag.TRUE.equals(sysParamCacheManager
					.getEnableParamValue(SysConstants.SysParamCode.LOGIN_LOG_DB))) {

				SysUser user = (SysUser) session.get(SysConstants.LOGIN_USER);
				if(user!=null){
					BPDBLogger.infoLogoutLog(request.getSession().getId(), user.getUserOwnerName(),
							user.getLoginName(), HttpRequestUtil.getIpAddr(request));
				}
			}
			/* 写登出日志：是否写文件 */
			if (SysConstants.BooleanFlag.TRUE.equals(sysParamCacheManager
					.getEnableParamValue(SysConstants.SysParamCode.LOGIN_LOG_FILE))) {

				SysUser user = (SysUser) session.get(SysConstants.LOGIN_USER);
				if(user!=null){
					BPFileLogger.infoLogoutLog(request.getSession().getId(), user.getUserOwnerName(),
							user.getLoginName(), HttpRequestUtil.getIpAddr(request));
				}
			}
			/* 清除HttpSession信息 */
			request.getSession().invalidate();
		} catch (Exception e) {
			log.equals(e.getMessage());
			resultMsg = e.getMessage();
		}

		return SUCCESS;
	}
	public String getResultMsg() {
		return resultMsg;
	}
}
