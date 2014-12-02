package com.cattsoft.ny.base.entity.querybean;

import java.util.Date;

public class NewsQB {
	private Long pickId;
	private String time;
	private String BaseName;
	private String HouseName;
	private String item;
	private Double figure;
	private Long userId;

	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getBaseName() {
		return BaseName;
	}
	public void setBaseName(String baseName) {
		BaseName = baseName;
	}
	public String getHouseName() {
		return HouseName;
	}
	public void setHouseName(String houseName) {
		HouseName = houseName;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public Double getFigure() {
		return figure;
	}
	public void setFigure(Double figure) {
		this.figure = figure;
	}
	public Long getPickId() {
		return pickId;
	}
	public void setPickId(Long pickId) {
		this.pickId = pickId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
