package com.cattsoft.baseplatform.func.sm.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cattsoft.baseplatform.func.sm.entity.CustomizeArea;
import com.cattsoft.baseplatform.func.sm.service.CustomizeAreaService;
import com.cattsoft.baseplatform.web.action.Action;

/**
 * 自定义区域管理
 * 
 * @author wanghuacun
 * 
 */
public class CustomizeAreaManagerAction extends Action {

	private static final long serialVersionUID = -6123918277607424900L;

	private CustomizeAreaService customizeAreaService;

	private String areaId;

	private List<CustomizeArea> customizeAreas = new ArrayList<CustomizeArea>();

	private CustomizeArea customizeAreaObj;

	private boolean flag;

	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 跳转到自定义区域管理页面
	 * 
	 * @return
	 */
	public String jumpCustomizeAreaManager() {
		return "jumpCustomizeAreaManager";
	}

	/**
	 * 获取自定义区域数据
	 * 
	 * @return
	 */
	public String getCustomizeArea() {
		if (StringUtils.isNotBlank(areaId)) {
			customizeAreas = customizeAreaService.getSubAreas(Long.valueOf(areaId));
		} else {
			CustomizeArea customizeArea = customizeAreaService.getRootArea();
			customizeAreas.add(customizeArea);
		}
		return "getCustomizeArea";
	}

	/**
	 * 修改CustomizeArea
	 * 
	 * @return
	 */
	public String editCustomizeArea() {
		flag = false;
		if (customizeAreaObj != null) {
			try {
				CustomizeArea customizeArea = customizeAreaService.getAreaByCode(customizeAreaObj
						.getAreaCode());
				if (customizeArea != null
						&& !customizeArea.getAreaId().equals(customizeAreaObj.getAreaId())) {
					msg = "更新失败：区域代码存在，请重新输入";
				} else {
					customizeAreaService.modifyArea(customizeAreaObj);
					flag = true;
				}
			} catch (Exception e) {
				flag = false;
			}
		}
		return "editCustomizeArea";
	}

	/**
	 * 删除CustomizeArea
	 * 
	 * @return
	 */
	public String deleteCustomizeArea() {
		try {
			if (StringUtils.isNotBlank(areaId)) {
				customizeAreaService.removeArea(Long.valueOf(areaId));
				flag = true;
			} else {
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
		}
		return "deleteCustomizeArea";
	}

	/**
	 * 添加CustomizeArea
	 * 
	 * @return
	 */
	public String addCustomizeArea() {
		flag = false;
		if (customizeAreaObj != null) {
			try {
				if (customizeAreaService.getAreaByCode(customizeAreaObj.getAreaCode()) != null) {
					msg = "添加失败：区域代码存在，请重新输入";
				} else {
					customizeAreaService.createArea(customizeAreaObj);
					flag = true;
				}
			} catch (Exception e) {
				flag = false;
			}
		}
		return "addCustomizeArea";
	}

	public CustomizeAreaService getCustomizeAreaService() {
		return customizeAreaService;
	}

	public void setCustomizeAreaService(CustomizeAreaService customizeAreaService) {
		this.customizeAreaService = customizeAreaService;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public List<CustomizeArea> getCustomizeAreas() {
		return customizeAreas;
	}

	public void setCustomizeAreas(List<CustomizeArea> customizeAreas) {
		this.customizeAreas = customizeAreas;
	}

	public CustomizeArea getCustomizeAreaObj() {
		return customizeAreaObj;
	}

	public void setCustomizeAreaObj(CustomizeArea customizeAreaObj) {
		this.customizeAreaObj = customizeAreaObj;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}