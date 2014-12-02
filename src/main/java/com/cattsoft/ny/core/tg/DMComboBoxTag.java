package com.cattsoft.ny.core.tg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cattsoft.baseplatform.cache.DomainCacheManager;
import com.cattsoft.baseplatform.core.entity.DomainValue;
import com.cattsoft.baseplatform.ui.pub.WebUtils;
import com.cattsoft.baseplatform.ui.tag.BaseCompTag;

/**
 * Description: 从DomainCache取值的下拉框控件<br>
 * Copyright:DATANG SOFTWARE CO.LTD<br>
 * 
 * @author tangliling
 * 
 */
public class DMComboBoxTag extends BaseCompTag {

	private static final long serialVersionUID = 1L;
	private String name;
	private String url;
	private Object datasource;
	private String keyField;
	private String valueField;
	private String selectIndex;
	private String selectValue;
	private boolean hasAll;
	private String allKey;
	private String allValue;
	private boolean hasNull;
	private String nullKey;
	private String nullValue;
	private String onChange;
	private String relaId;
	private String refreshUrl;
	private String sourceTargetId;
	private String paramkey;
	private boolean readonly;
	private boolean required = false;
	private String prompt;

	/*-------------在基础平台combobox上新添字段----------*/
	private String domainCode;

	public DMComboBoxTag() {
		this.setPropertyDefaultValue();
	}

	public int doStartTag() throws JspException {
		try {
			String elemId = getId();
			if (StringUtils.isBlank(this.name)) {
				this.name = elemId;
			}
			if (StringUtils.isBlank(this.selectValue)) {
				HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
				Object objVal = WebUtils.parseTagName(this.name, request);
				if ((objVal != null) && (!("".equals(objVal)))) {
					this.selectValue = String.valueOf(objVal);
				}
			}
			ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
			DomainCacheManager domainCacheManager = (DomainCacheManager) ctx.getBean("domainCacheManager");
			// Map<String, String> map = new LinkedHashMap<String, String>();
			ArrayList<DomainValue> domainValues = (ArrayList<DomainValue>) domainCacheManager.getDomainValues(domainCode);
			if (CollectionUtils.isNotEmpty(domainValues)) {
				keyField = "valueCode";
				valueField = "valueName";
				this.datasource = domainValues;
			}
			// for (int i = 0; i < domainValues.size(); i++) {
			// DomainValue tmp = domainValues.get(i);
			// map.put(tmp.getValueCode(), tmp.getValueName());
			// }
			// if (map.size() > 0) {
			// keyField = "key";
			// valueField = "value";
			// this.datasource = map;
			// }
			String datasourceJson = null;
			if (this.datasource != null) {
				if (this.datasource instanceof Map)
					datasourceJson = JSONObject.fromObject(this.datasource).toString();
				else if (this.datasource instanceof Collection)
					datasourceJson = JSONArray.fromObject(this.datasource).toString();
				else {
					datasourceJson = this.datasource.toString();
				}
			}
			this.datasource = datasourceJson;
			writeDivStart(getId());
		}
		catch (Exception ex) {
			throw new JspTagException("错误");
		}
		return EVAL_BODY_INCLUDE;
	}

	private void writeDivStart(String Id) throws JspException {
		if (StringUtils.isBlank(this.id))
			write("<div>");
		else
			write("<div id='" + getId() + "'>");
	}

	public int doEndTag() throws JspException {
		write("</div>");
		try {
			if (StringUtils.isBlank(getId())) {
				throw new JspException("标签的id不能为空！");
			}

			writeTemplate("dtcombobox.js.ftl");
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		setPropertyDefaultValue();
		return EVAL_PAGE;
	}

	private void setPropertyDefaultValue() {
		this.name = null;
		this.url = null;
		this.datasource = null;
		this.keyField = null;
		this.valueField = null;
		this.selectIndex = "0";
		this.selectValue = null;
		this.hasAll = false;
		this.allKey = "all";
		this.allValue = "--全部--";
		this.hasNull = false;
		this.nullKey = null;
		this.nullValue = "--请选择--";
		this.onChange = null;
		this.relaId = null;
		this.refreshUrl = null;
		this.sourceTargetId = null;
		this.paramkey = "";
		this.readonly = false;
		this.required = false;
		this.domainCode = null;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Object getDatasource() {
		return this.datasource;
	}

	public void setDatasource(Object datasource) {
		this.datasource = datasource;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKeyField() {
		return this.keyField;
	}

	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}

	public String getValueField() {
		return this.valueField;
	}

	public void setValueField(String valueField) {
		this.valueField = valueField;
	}

	public String getSelectIndex() {
		return this.selectIndex;
	}

	public void setSelectIndex(String selectIndex) {
		this.selectIndex = selectIndex;
	}

	public String getSelectValue() {
		return this.selectValue;
	}

	public void setSelectValue(String selectValue) {
		this.selectValue = selectValue;
	}

	public boolean isHasAll() {
		return this.hasAll;
	}

	public void setHasAll(boolean hasAll) {
		this.hasAll = hasAll;
	}

	public String getAllKey() {
		return this.allKey;
	}

	public void setAllKey(String allKey) {
		this.allKey = allKey;
	}

	public String getAllValue() {
		return this.allValue;
	}

	public void setAllValue(String allValue) {
		this.allValue = allValue;
	}

	public boolean isHasNull() {
		return this.hasNull;
	}

	public void setHasNull(boolean hasNull) {
		this.hasNull = hasNull;
	}

	public String getNullKey() {
		return this.nullKey;
	}

	public void setNullKey(String nullKey) {
		this.nullKey = nullKey;
	}

	public String getNullValue() {
		return this.nullValue;
	}

	public void setNullValue(String nullValue) {
		this.nullValue = nullValue;
	}

	public String getOnChange() {
		return this.onChange;
	}

	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}

	public String getRelaId() {
		return this.relaId;
	}

	public void setRelaId(String relaId) {
		this.relaId = relaId;
	}

	public String getRefreshUrl() {
		return this.refreshUrl;
	}

	public void setRefreshUrl(String refreshUrl) {
		this.refreshUrl = refreshUrl;
	}

	public String getSourceTargetId() {
		return this.sourceTargetId;
	}

	public void setSourceTargetId(String sourceTargetId) {
		this.sourceTargetId = sourceTargetId;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public String getParamkey() {
		return this.paramkey;
	}

	public void setParamkey(String paramkey) {
		this.paramkey = paramkey;
	}

	public String getDomainCode() {
		return domainCode;
	}

	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
}
