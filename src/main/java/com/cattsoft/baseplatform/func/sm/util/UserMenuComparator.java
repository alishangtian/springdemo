package com.cattsoft.baseplatform.func.sm.util;

import java.util.Comparator;

import net.sf.json.JSONObject;

/**
 * 用户菜单排序:displayOrder+menuName
 * 
 * @author longtao
 * 
 */
public class UserMenuComparator implements Comparator<JSONObject> {

	@Override
	public int compare(JSONObject json1, JSONObject json2) {
		int displayOrderCompare = Integer.valueOf(json1.getInt("displayOrder")).compareTo(
				Integer.valueOf(json2.getInt("displayOrder")));

		return (displayOrderCompare == 0) ? json1.getString("menuName").compareTo(
				json2.getString("menuName")) : displayOrderCompare;

	}

}
