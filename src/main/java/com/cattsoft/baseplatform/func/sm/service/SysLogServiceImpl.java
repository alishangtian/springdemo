package com.cattsoft.baseplatform.func.sm.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.core.exception.ServiceException;
import com.cattsoft.baseplatform.core.util.IdConvertionBean;
import com.cattsoft.baseplatform.func.sm.component.SysLogComponent;
import com.cattsoft.baseplatform.func.sm.entity.SysLog;
import com.cattsoft.baseplatform.func.sm.entity.query.SysLogQuery;

public class SysLogServiceImpl implements SysLogService {
	private SysLogComponent sysLogComponent;

	public void setSysLogComponent(SysLogComponent sysLogComponent) {
		this.sysLogComponent = sysLogComponent;
	}

	@Override
	public PagingResultBean<List<IdConvertionBean<SysLog>>> getSysLogsPaging(
			PagingQueryBean<SysLogQuery> qb) {
		if (null == qb || null == qb.getQueryBean() || null == qb.getPagingInfo()) {
			throw new ServiceException("查询条件对象缺失");
		}
		if (null == qb.getQueryBean().getStartTime()) {
			throw new ServiceException("查询条件[开始时间]禁止为空");
		}
		if (null == qb.getQueryBean().getEndTime()) {
			throw new ServiceException("查询条件[结束时间]禁止为空");
		}
		return this.sysLogComponent.getSysLogsPaging(qb);
	}

}
