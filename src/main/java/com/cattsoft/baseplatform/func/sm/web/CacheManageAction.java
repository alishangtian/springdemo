package com.cattsoft.baseplatform.func.sm.web;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cattsoft.baseplatform.cache.BusiParamCacheManager;
import com.cattsoft.baseplatform.cache.DomainCacheManager;
import com.cattsoft.baseplatform.cache.SysParamCacheManager;
import com.cattsoft.baseplatform.cache.TableCacheConfig;
import com.cattsoft.baseplatform.cache.TableCacheManager;
import com.cattsoft.baseplatform.core.annotation.Authorization;
import com.cattsoft.baseplatform.core.annotation.Todo;
import com.cattsoft.baseplatform.core.entity.BusiParam;
import com.cattsoft.baseplatform.func.sm.entity.RunSysConfig;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.entity.query.CacheQuery;
import com.cattsoft.baseplatform.web.action.Action;

/**
 * 缓存管理Action，实现缓存的查询、刷新功能。
 * 
 * @author wangcl
 */
public class CacheManageAction extends Action implements SysUserAware {
	private static final long serialVersionUID = -8634643632257807558L;

	private SysUser sysUser;

	// Action Result
	private static final String REFRESH_RESULT = "refreshResult";
	private static final String QUERY_TABLE = "tableCache";
	private static final String QUERY_DOMAIN = "domainCache";
	private static final String QUERY_RUN_SYS_CONFIG = "runSysConfigCache";
	private static final String QUERY_BUSI_SYS_CONFIG = "busiSysConfigCache";
	private static final String PARAM_CODE_COMBOBOX = "params";

	// 缓存类型取值
	private static final String CACHE_TYPE_TABLE = "table";
	private static final String CACHE_TYPE_DOMAIN = "domain";
	private static final String CACHE_TYPE_RUN = "runsysconfig";
	private static final String CACHE_TYPE_BUSI = "busisysconfig";

	// 下拉框“全部”取值
	private static final String OPTION_ALL = "all";

	// 页面查询对象
	private CacheQuery qry = new CacheQuery();

	// DomainCache缓存
	private DomainCacheManager domainCacheManager;

	// TableCache缓存
	private TableCacheManager tableCacheManager;

	// run_sys_config 缓存
	private SysParamCacheManager sysParamCacheManager;

	private List<RunSysConfig> runSysConfigList = null;

	// busi_sys_config 缓存
	private BusiParamCacheManager busiParamCacheManager = null;

	private List<BusiParam> busiSysConfigList = null;

	// "缓存类型"下拉框
	private Map<String, String> cacheTypeMap;

	// TableCache缓存"集合名称"下拉框
	private Map<String, String> tableMap;

	// DomainCache缓存"类型编码"(TYPE_CODE)下拉框
	@SuppressWarnings("unused")
	private Map<String, String> typeMap;

	// DomainCache缓存"参数编码"(PARAM_CODE)下拉框
	private Map<String, String> paramMap;

	// 刷新操作的结果，用于JSON通知
	private String resultCode;

	// 查询结果
	private Map<String, Map<String, String>> cacheMap;

	@Override
	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	@Override
	public SysUser getSysUser() {
		return sysUser;
	}

	public CacheQuery getQry() {
		return qry;
	}

	public void setQry(CacheQuery qry) {
		this.qry = qry;
	}

	public DomainCacheManager getDomainCacheManager() {
		return domainCacheManager;
	}

	public void setDomainCacheManager(DomainCacheManager domainCacheManager) {
		this.domainCacheManager = domainCacheManager;
	}

	public TableCacheManager getTableCacheManager() {
		return tableCacheManager;
	}

	public void setTableCacheManager(TableCacheManager tableCacheManager) {
		this.tableCacheManager = tableCacheManager;
	}

	public void setSysParamCacheManager(SysParamCacheManager sysParamCacheManager) {
		this.sysParamCacheManager = sysParamCacheManager;
	}

	public void setBusiParamCacheManager(BusiParamCacheManager busiParamCacheManager) {
		this.busiParamCacheManager = busiParamCacheManager;
	}

	public List<BusiParam> getBusiSysConfigList() {
		return busiSysConfigList;
	}

	public List<RunSysConfig> getRunSysConfigList() {
		return runSysConfigList;
	}

	/**
	 * “缓存类型”的下拉列表。
	 * 
	 * @return 下拉列表的数据集合
	 */
	@Todo("可以配置在DOMAIN表中，然后从DomainCache中获取")
	public Map<String, String> getCacheTypeMap() {
		// TODO get from cache.
		this.cacheTypeMap = new LinkedHashMap<String, String>(4);
		cacheTypeMap.put(CACHE_TYPE_DOMAIN, "值域缓存");
		cacheTypeMap.put(CACHE_TYPE_TABLE, "配置表缓存");
		cacheTypeMap.put(CACHE_TYPE_RUN, "运行参数缓存");
		cacheTypeMap.put(CACHE_TYPE_BUSI, "业务参数缓存");
		return cacheTypeMap;
	}

	/**
	 * 获取TableCache的“集合名称”查询条件的下拉框。
	 * 
	 * @return 集合名称的数据集合
	 */
	public Map<String, String> getTableMap() {
		List<TableCacheConfig> tableConfigs = tableCacheManager.getTableConfigs();
		this.tableMap = new LinkedHashMap<String, String>();
		for (TableCacheConfig tableConfig : tableConfigs) {
			tableMap.put(tableConfig.getMapName(), tableConfig.getMapName());
		}
		return tableMap;
	}

	/**
	 * 获取DomainCache的“类型编码”查询条件的下拉框。
	 * 
	 * @return 类型编码的数据集合
	 */
	public Map<String, String> getTypeMap() {
		// return domainCacheManager.getTypes();
		return new HashMap<String, String>();
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public String getResultCode() {
		return resultCode;
	}

	public Map<String, Map<String, String>> getCacheMap() {
		return cacheMap;
	}

	public void setCacheMap(Map<String, Map<String, String>> cacheMap) {
		this.cacheMap = cacheMap;
	}

	/**
	 * 根据TYPE_CODE下拉框取值获取PARAM_CODE下拉框，用于前台下拉框联动。
	 * 
	 * @return JSON对象
	 */
	public String getParams() {
		// paramMap = domainCacheManager.getParams(qry.getTypeCode());
		paramMap = new HashMap<String, String>();
		return PARAM_CODE_COMBOBOX;
	}

	/**
	 * 显示缓存管理主界面。
	 */
	@Override
	@Authorization
	public String execute() {
		return SUCCESS;
	}

	/**
	 * 查询缓存。
	 */
	@Authorization
	public String query() {
		String result = null;
		if (CACHE_TYPE_TABLE.equals(qry.getCacheType())) {
			if (StringUtils.isBlank(qry.getMapName()) || OPTION_ALL.equals(qry.getMapName())) {
				// cacheMap = tableCacheManager.getAll();
			} else {
				cacheMap = new LinkedHashMap<String, Map<String, String>>(1);
				cacheMap.put(qry.getMapName(), tableCacheManager.getMap(qry.getMapName()));
			}
			result = QUERY_TABLE;
		} else if (CACHE_TYPE_DOMAIN.equals(qry.getCacheType())) {
			if (StringUtils.isBlank(qry.getTypeCode()) || OPTION_ALL.equals(qry.getTypeCode())) {
				// cacheMap = domainCacheManager.getAll();
			} else {
				cacheMap = new LinkedHashMap<String, Map<String, String>>(1);
				// cacheMap.put(qry.getTypeCode() + "." + qry.getParamCode(),
				// domainCacheManager.getMap(qry.getTypeCode(),
				// qry.getParamCode()));
			}
			result = QUERY_DOMAIN;
		} else if (CACHE_TYPE_RUN.equals(qry.getCacheType())) {
			// runSysConfigList =
			// sysParamCacheManager.getRunSysConfigAllBycache();
			result = QUERY_RUN_SYS_CONFIG;
		} else if (CACHE_TYPE_BUSI.equals(qry.getCacheType())) {
			// busiSysConfigList =
			// busiParamCacheManager.getBusiSysConfigAllBycache();
			result = QUERY_BUSI_SYS_CONFIG;
		}
		return result;
	}

	/**
	 * 刷新缓存。
	 */
	@Authorization
	public String refresh() {
		try {
			if (CACHE_TYPE_TABLE.equals(qry.getCacheType())) {
				if (StringUtils.isBlank(qry.getMapName()) || OPTION_ALL.equals(qry.getMapName())) {
					tableCacheManager.reload();
				} else {
					// tableCacheManager.reload(qry.getMapName());
				}
			} else if (CACHE_TYPE_DOMAIN.equals(qry.getCacheType())) {
				if (StringUtils.isBlank(qry.getTypeCode()) || OPTION_ALL.equals(qry.getTypeCode())) {
					domainCacheManager.reload();
				} else {
					// domainCacheManager.reload(qry.getTypeCode(),
					// qry.getParamCode());
				}
			} else if (CACHE_TYPE_RUN.equals(qry.getCacheType())) {
				sysParamCacheManager.reload();
			} else if (CACHE_TYPE_BUSI.equals(qry.getCacheType())) {
				busiParamCacheManager.reload();
			}
			this.resultCode = "true";
		} catch (RuntimeException e) {
			this.resultCode = "false";
			throw e;
		}

		return REFRESH_RESULT;
	}

}
