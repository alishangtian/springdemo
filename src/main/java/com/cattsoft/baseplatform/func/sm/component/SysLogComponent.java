package com.cattsoft.baseplatform.func.sm.component;

import java.util.ArrayList;
import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.core.util.IdConverter;
import com.cattsoft.baseplatform.core.util.IdConvertionBean;
import com.cattsoft.baseplatform.core.util.IdDomainBean;
import com.cattsoft.baseplatform.func.sm.entity.SysLog;
import com.cattsoft.baseplatform.func.sm.entity.query.SysLogQuery;
import com.cattsoft.baseplatform.func.sm.persistence.SysLogMapper;
import com.cattsoft.baseplatform.func.sm.util.SysConstants;

public class SysLogComponent {
	private SysLogMapper sysLogMapper;
	private IdConverter<SysLog> idConverter;

	public void setSysLogMapper(SysLogMapper sysLogMapper) {
		this.sysLogMapper = sysLogMapper;
	}

	public void setIdConverter(IdConverter<SysLog> idConverter) {
		this.idConverter = idConverter;
	}

	/**
	 * 分页查询系统日志
	 * 
	 * @param qb
	 * @return
	 */
	public PagingResultBean<List<IdConvertionBean<SysLog>>> getSysLogsPaging(
			PagingQueryBean<SysLogQuery> qb) {

		List<IdDomainBean> converFields = new ArrayList<IdDomainBean>();
		IdDomainBean domain1 = new IdDomainBean();
		domain1.setDomainCode(SysConstants.DomainCode.SYS_LOG_LOG_CATEGORY);
		domain1.setIdCode("logCategory");
		converFields.add(domain1);
		IdDomainBean domain2 = new IdDomainBean();
		domain2.setDomainCode(SysConstants.DomainCode.SYS_LOG_LOG_LEVEL);
		domain2.setIdCode("logLevel");
		converFields.add(domain2);

		// 分页查询列表
		PagingResultBean<List<IdConvertionBean<SysLog>>> result = new PagingResultBean<List<IdConvertionBean<SysLog>>>();
		result.setResultList(this.idConverter.convert(this.sysLogMapper.selectPageSysLog(qb),
				converFields));
		// 查询记录数
		qb.getPagingInfo().setTotalRows(this.sysLogMapper.selectCountSysLog(qb));
		result.setPagingInfo(qb.getPagingInfo());
		return result;
	}
}
