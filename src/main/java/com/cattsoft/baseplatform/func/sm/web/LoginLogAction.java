package com.cattsoft.baseplatform.func.sm.web;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingInfo;
import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.func.sm.entity.LoginLog;
import com.cattsoft.baseplatform.func.sm.entity.query.LoginLogQuery;
import com.cattsoft.baseplatform.func.sm.service.LoginLogService;
import com.cattsoft.baseplatform.web.action.PagingSupportAction;

/**
 * 登录日志查询
 */
public class LoginLogAction extends PagingSupportAction {

	private static final long serialVersionUID = 1L;
	/* 服务对象 */
	private LoginLogService loginLogService;
	/* 分页查询对象 */
	private final PagingQueryBean<LoginLogQuery> pqb;
	/* 分页查询结果集合 */
	private PagingResultBean<List<LoginLog>> prb;

	public void setLoginLogService(LoginLogService loginLogService) {
		this.loginLogService = loginLogService;
	}

	/**
	 * 构造函数
	 */
	public LoginLogAction() {
		pqb = new PagingQueryBean<LoginLogQuery>();
		pqb.setQueryBean(new LoginLogQuery());
		pqb.setPagingInfo(new PagingInfo());
	}

	/**
	 * 执行查询操作
	 */
	public String doQuery() throws Exception {
		prb = this.loginLogService.getLoginLogsPaging(pqb);
		return "doQuery";
	}

	public PagingQueryBean<LoginLogQuery> getPqb() {
		return pqb;
	}

	public PagingResultBean<List<LoginLog>> getPrb() {
		return prb;
	}

	public void setPrb(PagingResultBean<List<LoginLog>> prb) {
		this.prb = prb;
	}

}
