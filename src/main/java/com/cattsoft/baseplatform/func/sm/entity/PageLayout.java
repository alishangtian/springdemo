package com.cattsoft.baseplatform.func.sm.entity;

import com.cattsoft.baseplatform.core.entity.Entity;

/**
 * 主页定制：页面布局。
 * 
 * @author wangcl
 */
public class PageLayout extends Entity {
	private static final long serialVersionUID = 3205785684808846136L;

	private Integer pageLayoutId;

	private String thumbnailLocation;

	private String setting;

	public Integer getPageLayoutId() {
		return pageLayoutId;
	}

	public void setPageLayoutId(Integer pageLayoutId) {
		this.pageLayoutId = pageLayoutId;
	}

	public String getThumbnailLocation() {
		return thumbnailLocation;
	}

	public void setThumbnailLocation(String thumbnailLocation) {
		this.thumbnailLocation = thumbnailLocation;
	}

	public String getSetting() {
		return setting;
	}

	public void setSetting(String setting) {
		this.setting = setting;
	}

}
