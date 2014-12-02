package com.cattsoft.baseplatform.func.sm.entity;

import com.cattsoft.baseplatform.core.entity.Entity;

/**
 * 主页定制：功能组件。
 * 
 * @author wangcl
 */
public class PageComponent extends Entity {
	private static final long serialVersionUID = -3801712499855049894L;

	private Integer pageComponentId; // 组件标识

	private String title; // 组件标题

	private String description; // 组件描述

	private String thumbnailLocation; // 组件缩略图位置

	private String url; // 组件URL地址

	public Integer getPageComponentId() {
		return pageComponentId;
	}

	public void setPageComponentId(Integer pageComponentId) {
		this.pageComponentId = pageComponentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getThumbnailLocation() {
		return thumbnailLocation;
	}

	public void setThumbnailLocation(String thumbnailLocation) {
		this.thumbnailLocation = thumbnailLocation;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
