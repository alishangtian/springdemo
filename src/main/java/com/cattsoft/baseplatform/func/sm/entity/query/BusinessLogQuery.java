package com.cattsoft.baseplatform.func.sm.entity.query;

import com.cattsoft.baseplatform.core.entity.QueryBean;

public class BusinessLogQuery extends QueryBean {

	private static final long serialVersionUID = -8274371178576114949L;
	
	private String sessionNo = null;

	public String getSessionNo() {
		return sessionNo;
	}

	public void setSessionNo(String sessionNo) {
		this.sessionNo = sessionNo;
	} 

}
