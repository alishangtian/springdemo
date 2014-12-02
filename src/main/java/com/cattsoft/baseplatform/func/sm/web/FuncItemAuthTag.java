package com.cattsoft.baseplatform.func.sm.web;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.util.AuthorizationUtil;
import com.cattsoft.baseplatform.func.sm.util.SysConstants;

/**
 * 从用户登录信息中判断是否具备指定的功能操作权限
 * 
 * @author wanghuacun
 * 
 */
public class FuncItemAuthTag extends TagSupport {

	private static final long serialVersionUID = -1979465578351228175L;

	private String funcItemId;

	@Override
	public int doStartTag() throws JspException {
		HttpSession session = pageContext.getSession();
		SysUser sysUser = (SysUser) session.getAttribute(SysConstants.LOGIN_USER);
		boolean flag = false;
		if (StringUtils.isNotBlank(funcItemId)) {
			flag = AuthorizationUtil.funcItemAuthorize(Long.valueOf(funcItemId), sysUser);
		}
		int returnValue = SKIP_BODY;
		if (flag) {
			returnValue = EVAL_BODY_INCLUDE;
		}
		return returnValue;
	}

	@Override
	public int doEndTag() throws JspException {
		funcItemId = "";
		return EVAL_PAGE;
	}

	public String getFuncItemId() {
		return funcItemId;
	}

	public void setFuncItemId(String funcItemId) {
		this.funcItemId = funcItemId;
	}

}
