package com.cattsoft.baseplatform.func.sm.web;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.jsp.JspException;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cattsoft.baseplatform.cache.DomainCacheManager;
import com.cattsoft.baseplatform.ui.tag.BaseTag;

/**
 * 根据DOMAIN表数据生成缓存。从DomainCache中获取数据。
 * 
 * @author wangcl
 */
public class DomainOptionsTag extends BaseTag {
	private static final long serialVersionUID = 7169815955211239079L;

	private String domainCode;
	private String optionalValue;

	public String getDomainCode() {
		return domainCode;
	}

	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}

	public String getOptionalValue() {
		return optionalValue;
	}

	public void setOptionalValue(String optionalValue) {
		this.optionalValue = optionalValue;
	}

	@Override
	public int doStartTag() throws JspException {
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(pageContext
				.getServletContext());
		if (optionalValue != null) {
			/* [全部]、[请选择]选项都是不参与条件匹配的选项，不设置选项值 */
			if ("all".equals(optionalValue.trim().toLowerCase())) {
				writeln("<option value=''>--全部--</option>");
			} else if ("none".equals(optionalValue.trim().toLowerCase())) {
				writeln("<option value=''>--请选择--</option>");
			}
		}
		DomainCacheManager domainCacheManager = (DomainCacheManager) ctx
				.getBean("domainCacheManager");
		Map<String, String> domainMap = domainCacheManager.getEnableDomainValueMap(domainCode);
		for (Entry<String, String> entry : domainMap.entrySet()) {
			writeln("<option value='" + entry.getKey() + "'>" + entry.getValue() + "</option>");
		}

		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

}
