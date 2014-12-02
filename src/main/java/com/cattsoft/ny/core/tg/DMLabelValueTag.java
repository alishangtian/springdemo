package com.cattsoft.ny.core.tg;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cattsoft.baseplatform.cache.DomainCacheManager;
import com.cattsoft.baseplatform.ui.pub.WebUtils;
import com.cattsoft.baseplatform.ui.tag.BaseTag;

/**
 * domainCache缓存label-value转换控件 Description: <br>
 * Date: 2011-7-8 <br>
 * Copyright (c) 2011 CATTSoft <br>
 * 
 * @author wankai
 */
public class DMLabelValueTag extends BaseTag {

	private static final long serialVersionUID = 1L;

	private String name;
	private String domainCode;
	private String valueCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDomainCode() {
		return domainCode;
	}

	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}

	public String getValueCode() {
		return valueCode;
	}

	public void setValueCode(String valueCode) {
		this.valueCode = valueCode;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			if (StringUtils.isBlank(valueCode) && StringUtils.isBlank(this.name)) {
				throw new JspTagException("请至少指定valueCode和name任何一个");
			}
			// 如果valueCode和name都不为空，优先使用valueCode
			if (StringUtils.isBlank(valueCode)) {
				HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
				Object objVal = WebUtils.parseTagName(this.name, request);
				if (objVal != null && !"".equals(objVal)) {
					valueCode = objVal.toString();
				}
			}
			ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
			DomainCacheManager domainCacheManager = (DomainCacheManager) ctx.getBean("domainCacheManager");
			String valueName = domainCacheManager.getValueName(domainCode, valueCode);
			if (StringUtils.isNotBlank(valueName)) {
				write("<label id='" + id + "'>" + valueName + "</label>");
			}
			//清空valueCode，避免下一个标签使用上一个标签的值
			valueCode = null;
		}
		catch (Exception ex) {
			throw new JspTagException(ex.getMessage());
		}
		return EVAL_BODY_INCLUDE;
	}

}
