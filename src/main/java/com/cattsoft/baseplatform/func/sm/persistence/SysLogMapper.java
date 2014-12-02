package com.cattsoft.baseplatform.func.sm.persistence;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.func.sm.entity.SysLog;
import com.cattsoft.baseplatform.func.sm.entity.query.SysLogQuery;

public interface SysLogMapper {
	/**
	 * 分页查询系统日志
	 * 
	 * @param qb
	 * @return
	 */
	List<SysLog> selectPageSysLog(PagingQueryBean<SysLogQuery> qb);

	/**
	 * 查询系统日志总数
	 * 
	 * @param qb
	 * @return
	 */
	Integer selectCountSysLog(PagingQueryBean<SysLogQuery> qb);
}
