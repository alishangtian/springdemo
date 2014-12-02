package com.cattsoft.baseplatform.func.sm.component;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.func.sm.entity.LoginLog;
import com.cattsoft.baseplatform.func.sm.entity.query.LoginLogQuery;
import com.cattsoft.baseplatform.func.sm.persistence.LoginLogMapper;

public class LoginLogComponent {

	private LoginLogMapper loginLogMapper;

	public void setLoginLogMapper(LoginLogMapper loginLogMapper) {
		this.loginLogMapper = loginLogMapper;
	}

	/**
	 * 分页查询登录日志
	 * 
	 * @param qb
	 * @return
	 */
	public PagingResultBean<List<LoginLog>> getLoginLogsPaging(PagingQueryBean<LoginLogQuery> qb) {
		// 分页查询列表
		PagingResultBean<List<LoginLog>> result = new PagingResultBean<List<LoginLog>>();
		result.setResultList(this.loginLogMapper.selectPageLoginLog(qb));
		// 查询记录数
		qb.getPagingInfo().setTotalRows(this.loginLogMapper.selectCountLoginLog(qb));
		result.setPagingInfo(qb.getPagingInfo());
		return result;
	}

}
