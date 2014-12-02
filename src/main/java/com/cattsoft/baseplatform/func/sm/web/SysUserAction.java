package com.cattsoft.baseplatform.func.sm.web;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.core.util.DateUtil;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.entity.UserEmail;
import com.cattsoft.baseplatform.func.sm.entity.query.SysUserQuery;
import com.cattsoft.baseplatform.func.sm.service.HistoryDataQueryService;
import com.cattsoft.baseplatform.func.sm.service.SysUserService;
import com.cattsoft.baseplatform.func.sm.service.UserEmailService;
import com.cattsoft.baseplatform.func.sm.util.DateUtils;
import com.cattsoft.baseplatform.func.sm.util.PasswordGenerateUtils;
import com.cattsoft.baseplatform.func.sm.util.SysConstants;
import com.cattsoft.baseplatform.web.action.PagingSupportAction;

public class SysUserAction extends PagingSupportAction implements SessionAware {

	private static final long serialVersionUID = 2816647881033666601L;
	private SysUserService ideaUserService;
	private HistoryDataQueryService historyDataQueryService;
	private UserEmailService userEmailService;
	private Map<String, Object> session;
	private SysUser sysUser;
	private SysUser sysUserHistory;
	private String queryLoginName;
	private Long sysUserId;
	private Long sysUserIdHistory;
	private JSONObject retMsg;
	private String password;
	private String newPassword;
	private PagingResultBean<List<SysUser>> sysUserQueryResult;
	private PagingResultBean<List<SysUser>> sysUserQueryResultHistory;
	private boolean result;
	private String msg;
	private static String AJAXSUCCESS = "ajaxsuccess";

	private UserEmail userEmail;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public JSONObject getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(JSONObject retMsg) {
		this.retMsg = retMsg;
	}

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public SysUser getSysUserHistory() {
		return sysUserHistory;
	}

	public void setSysUserHistory(SysUser sysUserHistory) {
		this.sysUserHistory = sysUserHistory;
	}

	public String getQueryLoginName() {
		return queryLoginName;
	}

	public void setQueryLoginName(String queryLoginName) {
		this.queryLoginName = queryLoginName;
	}

	public Long getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Long sysUserId) {
		this.sysUserId = sysUserId;
	}

	public Long getSysUserIdHistory() {
		return sysUserIdHistory;
	}

	public void setSysUserIdHistory(Long sysUserIdHistory) {
		this.sysUserIdHistory = sysUserIdHistory;
	}

	public void setIdeaUserService(SysUserService ideaUserService) {
		this.ideaUserService = ideaUserService;
	}

	public HistoryDataQueryService getHistoryDataQueryService() {
		return historyDataQueryService;
	}

	public void setHistoryDataQueryService(HistoryDataQueryService historyDataQueryService) {
		this.historyDataQueryService = historyDataQueryService;
	}

	public UserEmailService getUserEmailService() {
		return userEmailService;
	}

	public void setUserEmailService(UserEmailService userEmailService) {
		this.userEmailService = userEmailService;
	}

	public PagingResultBean<List<SysUser>> getSysUserQueryResult() {
		return sysUserQueryResult;
	}

	public void setSysUserQueryResult(PagingResultBean<List<SysUser>> sysUserQueryResult) {
		this.sysUserQueryResult = sysUserQueryResult;
	}

	public PagingResultBean<List<SysUser>> getSysUserQueryResultHistory() {
		return sysUserQueryResultHistory;
	}

	public void setSysUserQueryResultHistory(
			PagingResultBean<List<SysUser>> sysUserQueryResultHistory) {
		this.sysUserQueryResultHistory = sysUserQueryResultHistory;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public UserEmail getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(UserEmail userEmail) {
		this.userEmail = userEmail;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	/**
	 * 依据登录名称精确查找唯一的用户
	 * 
	 * @return
	 */
	public String getSysUserByName() {
		try {
			this.sysUser = this.ideaUserService.getSysUserByName(this.queryLoginName);
		} catch (Exception e) {
			this.sysUser = null;
		}
		return "sysUserInfo";
	}

	/**
	 * 依据登录名称精确查找唯一的用户
	 * 
	 * @return
	 */
	public String getSysUserByNameResetPassword() {
		try {
			this.sysUser = this.ideaUserService.getSysUserByName(this.queryLoginName);
		} catch (Exception e) {
			this.sysUser = null;
		}
		return "sysUserInfoResetPassword";
	}

	/**
	 * 密码重置:转入密码重置主界面
	 * 
	 * @return
	 */
	public String resetPasswordInit() {
		return "resetPassword";
	}

	/**
	 * 密码重置：ajax操作执行密码重置功能
	 * 
	 * @return
	 */
	public String resetPassword() {
		this.retMsg = new JSONObject();
		this.retMsg.put("Code", "Success");
		this.retMsg.put("Msg", "密码重置成功");
		/*
		 * if (this.sysUserId != null) { try {
		 * this.ideaUserService.resetPassword(this.sysUserId); } catch
		 * (Exception e) { this.retMsg.put("Code", "Failure");
		 * this.retMsg.put("Msg", "密码重置失败"); } } else { this.retMsg.put("Code",
		 * "Failure"); this.retMsg.put("Msg", "操作异常，未指定待密码重置的用户"); }
		 */
		if (userEmail != null && userEmail.getUserId() != null) {
			try {
				String password = PasswordGenerateUtils.generatePassword();
				String encryptedPassword = PasswordGenerateUtils.MD5password(password);

				// 重置用户密码
				this.ideaUserService.resetPassword(userEmail.getUserId(), encryptedPassword);

				userEmail.setEmailContent(password);
				userEmail.setEmailStatus("I");
				userEmailService.addUserEmail(userEmail);

			} catch (Exception e) {
				e.printStackTrace();
				this.retMsg.put("Code", "Failure");
				this.retMsg.put("Msg", "密码重置失败");
			}
		} else {
			this.retMsg.put("Code", "Failure");
			this.retMsg.put("Msg", "操作异常，未指定待密码重置的用户");
		}
		return "resetPasswordResult";

	}

	/**
	 * 用户注销：转入用户注销主界面
	 * 
	 * @return
	 */
	public String removeSysUserInit() {
		return "removeSysUser";
	}

	/**
	 * 用户注销：ajax操作执行用户注销功能
	 * 
	 * @return
	 */
	public String removeSysUser() {
		this.retMsg = new JSONObject();
		this.retMsg.put("Code", "Success");
		this.retMsg.put("Msg", "用户注销成功");
		if (this.sysUserId != null) {
			try {
				this.ideaUserService.removeSysUser(this.sysUserId);
			} catch (Exception e) {
				this.retMsg.put("Code", "Failure");
				this.retMsg.put("Msg", "用户注销失败");
			}
		} else {
			this.retMsg.put("Code", "Failure");
			this.retMsg.put("Msg", "操作异常，未指定待注销的用户");
		}
		return "removeSysUserResult";
	}

	/**
	 * 密码修改：转入密码修改主界面
	 * 
	 * @return
	 */
	public String modifyPasswordInit() {
		return "modifyPassword";
	}

	/**
	 * 密码修改：ajax操作执行密码修改功能
	 * 
	 * @return
	 */
	public String modifyPassword() {
		SysUser sysUser = (SysUser) session.get(SysConstants.LOGIN_USER);
		this.retMsg = new JSONObject();
		this.retMsg.put("Code", "Success");
		this.retMsg.put("Msg", "密码修改成功");
		try {
			this.ideaUserService.modifyPassword(sysUser.getUserId(), this.password,
					this.newPassword);
			// 更新session内SysUser对象
			sysUser.setPassword(this.newPassword);
			sysUser.setOldPwd(this.password);
			Timestamp time = DateUtil.getCurrentTimestamp();
			sysUser.setPwdChgTime(time);
			sysUser.setPwdExpTime(DateUtil.addMonth(time, 3));
		} catch (Exception e) {
			this.retMsg.put("Code", "Failure");
			this.retMsg.put("Msg", "密码修改失败：" + e.getMessage());
		}

		return "modifyPasswordResult";
	}

	/**
	 * 跳转到用户冻结解冻页面
	 * 
	 * @return
	 */
	public String changeExptime() {
		return "changeExptime";
	}

	/**
	 * 跳转到用户资料
	 * 
	 * @return
	 */
	public String historyResource() {
		return "historyResource";
	}

	/**
	 * 查询系统用户列表
	 * 
	 * @return
	 */
	public String getSysUserPaging() {
		HttpServletRequest request = ServletActionContext.getRequest();
		SysUserQuery sysUserQuery = new SysUserQuery();
		sysUserQuery.setLoginName(request.getParameter("loginName"));
		sysUserQuery.setOwnerType(request.getParameter("ownerType"));
		sysUserQuery.setOwnerName(request.getParameter("ownerName"));
		PagingQueryBean<SysUserQuery> sysUserQueryPage = buildPagingQueryBean(request, sysUserQuery);
		sysUserQueryResult = ideaUserService.getSysUserPaging4unfrozen(sysUserQueryPage);// mockData();
		return "getSysUserPaging";
	}

	/**
	 * 查询系统用户列表
	 * 
	 * @return
	 */
	public String getSysUserPagingHistory() {
		HttpServletRequest request = ServletActionContext.getRequest();
		SysUserQuery sysUserQuery = new SysUserQuery();
		sysUserQuery.setLoginName(request.getParameter("loginName"));
		sysUserQuery.setOwnerType(request.getParameter("ownerType"));
		sysUserQuery.setOwnerName(request.getParameter("ownerName"));
		PagingQueryBean<SysUserQuery> sysUserQueryPage = buildPagingQueryBean(request, sysUserQuery);
		sysUserQueryResultHistory = historyDataQueryService.getSysUserPaging(sysUserQueryPage);// mockData();
		return "getSysUserPagingHistory";
	}

	/**
	 * 完成用户冻结解冻
	 * 
	 * @return
	 */
	public String changeExptimeFin() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String userIds = request.getParameter("userIds");
		String userExpTime = request.getParameter("userExpTime");
		List<Long> userIdList = new ArrayList<Long>();
		JSONArray array = JSONArray.fromObject(userIds);
		for (Object object : array) {
			userIdList.add(NumberUtils.toLong(String.valueOf(object), -1));
		}
		ideaUserService.modifyUserExpTime(userIdList, DateUtils.parseDataDb(userExpTime));
		result = true;
		return AJAXSUCCESS;
	}

	/**
	 * 依据userId精确查找唯一的用户
	 * 
	 * @param userId
	 * @return
	 */
	public String getSysUserById() {
		try {
			sysUserHistory = historyDataQueryService.getSysUser(sysUserIdHistory);
		} catch (Exception e) {
			sysUserHistory = null;
		}
		return "getSysUserByIdHistory";
	}

}
