package com.cattsoft.baseplatform.func.sm.demo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.cattsoft.baseplatform.core.entity.PagingInfo;
import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.QueryBean;
import com.cattsoft.baseplatform.web.action.Action;

public class PagingSupportAction extends Action implements SessionAware {

	protected Map<String, Object> session;

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	protected <T extends QueryBean> PagingQueryBean<T> buildPagingQueryBean(
			HttpServletRequest request, T queryBean) {
		PagingInfo pagingInfo = new PagingInfo();
		int pageRows = NumberUtils.toInt(request.getParameter("pageRows"), 10);
		int pageNum = NumberUtils.toInt(request.getParameter("pageNum"), 1);
		pagingInfo.setPageRows(pageRows);
		pagingInfo.setPageNum(pageNum);
		PagingQueryBean<T> qb = new PagingQueryBean<T>();
		qb.setPagingInfo(pagingInfo);
		qb.setQueryBean(queryBean);
		return qb;
	}

}
