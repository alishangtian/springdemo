package com.cattsoft.ny.core.tg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cattsoft.baseplatform.cache.DomainCacheManager;
import com.cattsoft.baseplatform.core.entity.DomainValue;
import com.cattsoft.baseplatform.ui.pub.WebUtils;
import com.cattsoft.baseplatform.ui.tag.BaseCompTag;

/**
 * Description: 从domain中取值的RadioGroup<br>
 * Copyright:DATANG SOFTWARE CO.LTD<br>
 * 
 * @author yuchunfang
 * 
 */
public class DMRadioGroupTag extends BaseCompTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = -345754289864809663L;
	private boolean disabled = false;
	private boolean required = false;
	private String name = null;
	private Object items = null;
	private String col = null;
	private String value = null;
	private String title = null;
	private String keyField = null;
	private String valueField = null;
	private String onChange = null;
	// 以下是扩展平台标签的属性
	private String domainCode = null;
	private String valueCode = null;

	@Override
	public int doStartTag() throws JspException {
		try {
			writeDivStart(getId());
		}
		catch (Exception ex) {
			throw new JspTagException("错误");
		}
		return EVAL_BODY_INCLUDE;
	}

	private void writeDivStart(String Id) throws JspException {
		if (StringUtils.isBlank(id)) {
			write("<div>");
		}
		else {
			write("<div id='" + getId() + "'>");
		}
	}

	@Override
	public int doEndTag() throws JspException {
		write("</div>");
		try {

			if (StringUtils.isBlank(getId())) {
				throw new JspException("标签的id不能为空！");
			}

			String elemId = getId();
			if (StringUtils.isBlank(name)) {
				name = elemId;
			}

			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			Object objVal = WebUtils.parseTagName(name, request);
			if (StringUtils.isEmpty(value) && objVal != null && !"".equals(objVal)) {
				value = String.valueOf(objVal);
			}
			ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
			DomainCacheManager domainCacheManager = (DomainCacheManager) ctx.getBean("domainCacheManager");
			Map<String, String> map = new LinkedHashMap<String, String>();
			ArrayList<DomainValue> domainValues = (ArrayList<DomainValue>) domainCacheManager.getDomainValues(domainCode);
			for (int i = 0; i < domainValues.size(); i++) {
				DomainValue tmp = domainValues.get(i);
				map.put(tmp.getValueCode(), tmp.getValueName());
			}
			if (StringUtils.isNotBlank(valueCode)) {
				value = valueCode;
			}
			if (map.size() > 0) {
				this.items = map;
			}
			String itemsJson = null;
			if (items != null) {
				if (items instanceof Map) {
					itemsJson = JSONObject.fromObject(items).toString();
				}
				else if (items instanceof Collection) {
					itemsJson = JSONArray.fromObject(items).toString();
				}
				else {
					itemsJson = items.toString();
				}
			}

			items = itemsJson;

			writeTemplate("dtradiogroup.js.ftl");
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		setPropertyDefaultValue();
		return EVAL_PAGE;
	}

	private void setPropertyDefaultValue() {
		disabled = false;
		name = null;
		items = null;
		col = null;
		value = null;
		title = null;
		keyField = null;
		valueField = null;
		onChange = null;
		domainCode = null;
		valueCode = null;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getItems() {
		return items;
	}

	public void setItems(Object items) {
		this.items = items;
	}

	public String getCol() {
		return col;
	}

	public void setCol(String col) {
		this.col = col;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOnChange() {
		return onChange;
	}

	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}

	public String getKeyField() {
		return keyField;
	}

	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}

	public String getValueField() {
		return valueField;
	}

	public void setValueField(String valueField) {
		this.valueField = valueField;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
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

}
