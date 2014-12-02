package com.cattsoft.baseplatform.func.sm.demo;

/**
 * 支持AJAX请求转发类
 * 
 * @author zhangweiqiang
 * 
 */
public class AjaxSupportAction extends PagingSupportAction {
	private static final long serialVersionUID = 3153883654749205870L;

	protected static String AJAXSUCCESS = "ajaxsuccess";
	protected boolean result;
	protected String msg;

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
