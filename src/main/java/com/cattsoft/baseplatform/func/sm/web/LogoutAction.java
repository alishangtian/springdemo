package com.cattsoft.baseplatform.func.sm.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.cattsoft.baseplatform.cache.SysParamCacheManager;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.util.HttpRequestUtil;
import com.cattsoft.baseplatform.func.sm.util.SysConstants;
import com.cattsoft.baseplatform.log.util.BPDBLogger;
import com.cattsoft.baseplatform.log.util.BPFileLogger;
import com.cattsoft.baseplatform.web.action.Action;

/**
 * 用户登出Action。
 * 
 * @author wangcl
 */
public class LogoutAction extends Action implements ServletRequestAware, SessionAware {
	private static final long serialVersionUID = 318625920112991030L;
	private SysParamCacheManager sysParamCacheManager;
	private HttpServletRequest request;
	private Map<String, Object> session;

	public void setSysParamCacheManager(SysParamCacheManager sysParamCacheManager) {
		this.sysParamCacheManager = sysParamCacheManager;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	/**
	 * 用户登出处理: 清除HttpSession
	 * 
	 * @return 系统主页
	 */
	@Override
	public String execute() {
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

		return "login";
	}
}
