package com.cattsoft.baseplatform.func.sm.web;

import java.util.List;

import com.cattsoft.baseplatform.cache.BusiParamCacheManager;
import com.cattsoft.baseplatform.cache.DomainCacheManager;
import com.cattsoft.baseplatform.cache.SysParamCacheManager;
import com.cattsoft.baseplatform.cache.TableCacheConfig;
import com.cattsoft.baseplatform.cache.TableCacheManager;
import com.cattsoft.baseplatform.web.action.Action;

public class CacheManagerAction extends Action {
	private static final long serialVersionUID = 7444952227588045321L;

	private TableCacheManager tableCacheManager;

	private DomainCacheManager domainCacheManager;

	private BusiParamCacheManager busiParamCacheManager;

	private SysParamCacheManager sysParamCacheManager;

	private List<TableCacheConfig> tableConfigs;

	private String mapName;

	private boolean flag;

	/**
	 * 跳转到缓存管理界面
	 * 
	 * @return
	 */
	public String jumpCacheManager() {
		return "jumpCacheManager";
	}

	/**
	 * 根据mapName刷新TableCache
	 * 
	 * @return
	 */
	public String reloadTableCache() {
		try {
			tableCacheManager.reloadTableCache(mapName);
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return "reloadTableCache";
	}

	/**
	 * 刷新TableCache
	 * 
	 * @return
	 */
	public String reloadAllTableCache() {
		try {
			tableCacheManager.reload();
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return "reloadAllTableCache";
	}

	/**
	 * 刷新DomainCache
	 * 
	 * @return
	 */
	public String reloadDomainCache() {
		try {
			domainCacheManager.reload();
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return "reloadDomainCache";
	}

	/**
	 * 刷新BusiParamCache
	 * 
	 * @return
	 */
	public String reloadBusiParamCache() {
		try {
			busiParamCacheManager.reload();
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return "reloadBusiParamCache";
	}

	/**
	 * 刷新SysParamCache
	 * 
	 * @return
	 */
	public String reloadSysParamCache() {
		try {
			sysParamCacheManager.reload();
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return "reloadSysParamCache";
	}

	public String getTableCache() {
		tableConfigs = tableCacheManager.getTableConfigs();
		return "getTableCache";
	}

	public TableCacheManager getTableCacheManager() {
		return tableCacheManager;
	}

	public void setTableCacheManager(TableCacheManager tableCacheManager) {
		this.tableCacheManager = tableCacheManager;
	}

	public DomainCacheManager getDomainCacheManager() {
		return domainCacheManager;
	}

	public void setDomainCacheManager(DomainCacheManager domainCacheManager) {
		this.domainCacheManager = domainCacheManager;
	}

	public BusiParamCacheManager getBusiParamCacheManager() {
		return busiParamCacheManager;
	}

	public void setBusiParamCacheManager(BusiParamCacheManager busiParamCacheManager) {
		this.busiParamCacheManager = busiParamCacheManager;
	}

	public SysParamCacheManager getSysParamCacheManager() {
		return sysParamCacheManager;
	}

	public void setSysParamCacheManager(SysParamCacheManager sysParamCacheManager) {
		this.sysParamCacheManager = sysParamCacheManager;
	}

	public List<TableCacheConfig> getTableConfigs() {
		return tableConfigs;
	}

	public void setTableConfigs(List<TableCacheConfig> tableConfigs) {
		this.tableConfigs = tableConfigs;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}
