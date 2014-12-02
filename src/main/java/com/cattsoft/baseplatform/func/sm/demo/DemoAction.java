package com.cattsoft.baseplatform.func.sm.demo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

/**
 * demo演示类
 * 
 * @author zhangweiqiang
 * 
 */
public class DemoAction extends AjaxSupportAction {

	private Map<String, String> nameList;
	private Map<String, String> nickList;
	private List<Map<String, Object>> hisCallList;
	private Map<String, Object> listResult;

	public Map<String, Object> getListResult() {
		return listResult;
	}

	public void setListResult(Map<String, Object> listResult) {
		this.listResult = listResult;
	}

	public List<Map<String, Object>> getHisCallList() {
		return hisCallList;
	}

	public void setHisCallList(List<Map<String, Object>> hisCallList) {
		this.hisCallList = hisCallList;
	}

	public Map<String, String> getNameList() {
		return nameList;
	}

	public void setNameList(Map<String, String> nameList) {
		this.nameList = nameList;
	}

	public Map<String, String> getNickList() {
		return nickList;
	}

	public void setNickList(Map<String, String> nickList) {
		this.nickList = nickList;
	}

	/**
	 * 获取名称
	 * 
	 * @return
	 */
	public String doGetNameList() {
		nameList = new HashMap<String, String>();
		nameList.put("zhangsan", "张三");
		nameList.put("lisi", "李四");
		return "doGetNameList";
	}

	/**
	 * 获取昵称列表
	 * 
	 * @return
	 */
	public String doGetNickList() {
		nickList = new HashMap<String, String>();
		nickList.put("shuaige", "帅哥");
		nickList.put("meinv", "美女");
		return "doGetNickList";
	}

	/**
	 * 获取代拨电话历史
	 * 
	 * @return
	 */
	public String doHisList() {
		HttpServletRequest request = ServletActionContext.getRequest();
		hisCallList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("phone", "13812345678");
		map1.put("name", "王五");
		map1.put("linkman", "默认联系人");
		map1.put("callphone", "10086");
		map1.put("operdate", new Timestamp((new Date()).getTime()));
		map1.put("operstaff", "me");
		hisCallList.add(map1);
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("phone", "13887654321");
		map2.put("name", "赵六");
		map2.put("linkman", "联系人");
		map2.put("callphone", "100001");
		map2.put("operdate", new Timestamp((new Date()).getTime()));
		map2.put("operstaff", "her");
		hisCallList.add(map2);
		listResult = new HashMap<String, Object>();
		listResult.put("result", hisCallList);
		return "doHisList";
	}

	/**
	 * 查询电话簿
	 * 
	 * @return
	 */
	public String doBookList() {
		HttpServletRequest request = ServletActionContext.getRequest();
		hisCallList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("phone", "13812345678");
		map1.put("name", "王五");
		map1.put("nick", "帅哥");
		hisCallList.add(map1);
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("phone", "13887654321");
		map2.put("name", "赵六");
		map2.put("nick", "美女");
		hisCallList.add(map2);
		listResult = new HashMap<String, Object>();
		listResult.put("result", hisCallList);
		return "doBookList";
	}

	/**
	 * 初始化短信页面
	 * 
	 * @return
	 */
	public String initDuanxin() {
		return "getDuanxin";
	}

}
