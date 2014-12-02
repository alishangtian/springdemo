package com.cattsoft.baseplatform.func.sm.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.core.exception.ServiceException;
import com.cattsoft.baseplatform.func.sm.component.LoginLogComponent;
import com.cattsoft.baseplatform.func.sm.entity.LoginLog;
import com.cattsoft.baseplatform.func.sm.entity.query.LoginLogQuery;

public class LoginLogServiceImpl implements LoginLogService {
	private LoginLogComponent loginLogComponent;

	public void setLoginLogComponent(LoginLogComponent loginLogComponent) {
		this.loginLogComponent = loginLogComponent;
	}

	@Override
	public PagingResultBean<List<LoginLog>> getLoginLogsPaging(PagingQueryBean<LoginLogQuery> qb) {
		if (null == qb || null == qb.getQueryBean() || null == qb.getPagingInfo()) {
			throw new ServiceException("查询条件对象缺失");
		}
		if (null == qb.getQueryBean().getStartTime()) {
			throw new ServiceException("查询条件[开始时间]禁止为空");
		}
		if (null == qb.getQueryBean().getEndTime()) {
			throw new ServiceException("查询条件[结束时间]禁止为空");
		}
		return this.loginLogComponent.getLoginLogsPaging(qb);
	}

}
