package com.cattsoft.baseplatform.func.sm.web;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingInfo;
import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.core.util.IdConvertionBean;
import com.cattsoft.baseplatform.func.sm.entity.SysLog;
import com.cattsoft.baseplatform.func.sm.entity.query.SysLogQuery;
import com.cattsoft.baseplatform.func.sm.service.SysLogService;
import com.cattsoft.baseplatform.web.action.PagingSupportAction;

/**
 * 日志查询
 */
public class SysLogAction extends PagingSupportAction {

	private static final long serialVersionUID = 1L;
	/* 服务对象 */
	private SysLogService sysLogService;
	/* 分页查询对象 */
	private final PagingQueryBean<SysLogQuery> pqb;
	/* 分页查询结果集合 */
	private PagingResultBean<List<IdConvertionBean<SysLog>>> prb;

	public void setSysLogService(SysLogService sysLogService) {
		this.sysLogService = sysLogService;
	}

	/**
	 * 构造函数
	 */
	public SysLogAction() {
		pqb = new PagingQueryBean<SysLogQuery>();
		pqb.setQueryBean(new SysLogQuery());
		pqb.setPagingInfo(new PagingInfo());
	}

	/**
	 * 执行查询操作
	 */
	public String doQuery() throws Exception {
		prb = this.sysLogService.getSysLogsPaging(pqb);
		return "doQuery";
	}

	public PagingQueryBean<SysLogQuery> getPqb() {
		return pqb;
	}

	public PagingResultBean<List<IdConvertionBean<SysLog>>> getPrb() {
		return prb;
	}

	public void setPrb(PagingResultBean<List<IdConvertionBean<SysLog>>> prb) {
		this.prb = prb;
	}

}
