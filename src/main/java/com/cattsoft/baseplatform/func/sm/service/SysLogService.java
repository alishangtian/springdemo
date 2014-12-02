package com.cattsoft.baseplatform.func.sm.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.core.util.IdConvertionBean;
import com.cattsoft.baseplatform.func.sm.entity.SysLog;
import com.cattsoft.baseplatform.func.sm.entity.query.SysLogQuery;

public interface SysLogService {
	/**
	 * 分页查询系统日志信息
	 * 
	 * @param qb
	 * @return
	 */
	PagingResultBean<List<IdConvertionBean<SysLog>>> getSysLogsPaging(
			PagingQueryBean<SysLogQuery> qb);
}
