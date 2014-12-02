package com.cattsoft.ny.base.entity;

public class CropsResult {
	private Long id;
	private String baseName;
	private String houseName;
	private String crops;
	private Double amounts;
	public String getBaseName() {
		return baseName;
	}
	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}
	public String getCrops() {
		return crops;
	}
	public void setCrops(String crops) {
		this.crops = crops;
	}
	public Double getAmounts() {
		return amounts;
	}
	public void setAmounts(Double amounts) {
		this.amounts = amounts;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getHouseName() {
		return houseName;
	}
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	
}
