package com.cattsoft.baseplatform.func.sm.persistence;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.func.sm.entity.LoginLog;
import com.cattsoft.baseplatform.func.sm.entity.query.LoginLogQuery;

public interface LoginLogMapper {
	/**
	 * 分页查询登录日志
	 * 
	 * @param qb
	 * @return
	 */
	List<LoginLog> selectPageLoginLog(PagingQueryBean<LoginLogQuery> qb);

	/**
	 * 查询登录日志总数
	 * 
	 * @param qb
	 * @return
	 */
	Integer selectCountLoginLog(PagingQueryBean<LoginLogQuery> qb);
}
