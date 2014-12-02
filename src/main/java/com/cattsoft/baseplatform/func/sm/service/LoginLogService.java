package com.cattsoft.baseplatform.func.sm.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.func.sm.entity.LoginLog;
import com.cattsoft.baseplatform.func.sm.entity.query.LoginLogQuery;

public interface LoginLogService {
	/**
	 * 分页查询登录日志信息
	 * 
	 * @param qb
	 * @return
	 */
	PagingResultBean<List<LoginLog>> getLoginLogsPaging(PagingQueryBean<LoginLogQuery> qb);
}
