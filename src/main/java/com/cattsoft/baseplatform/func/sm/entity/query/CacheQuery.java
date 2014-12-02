package com.cattsoft.baseplatform.func.sm.entity.query;

import com.cattsoft.baseplatform.core.entity.QueryBean;

public class CacheQuery extends QueryBean {
	private static final long serialVersionUID = 2980841973169969636L;

	private String clusterInst; // 集群实例
	private String cacheType; // 缓存类型
	private String mapName; // TableCache缓存中的Map名称
	private String typeCode; // DomainCache缓存中的TYPE_CODE
	private String paramCode; // DomainCache缓存中的PARAM_CODE

	public String getClusterInst() {
		return clusterInst;
	}

	public void setClusterInst(String clusterInst) {
		this.clusterInst = clusterInst;
	}

	public String getCacheType() {
		return cacheType;
	}

	public void setCacheType(String cacheType) {
		this.cacheType = cacheType;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getParamCode() {
		return paramCode;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}
}
