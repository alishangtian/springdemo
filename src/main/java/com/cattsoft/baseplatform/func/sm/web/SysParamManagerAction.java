package com.cattsoft.baseplatform.func.sm.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.ServletActionContext;

import com.cattsoft.baseplatform.cache.SysParamCacheManager;
import com.cattsoft.baseplatform.core.entity.PagingInfo;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.core.entity.SysParam;
import com.cattsoft.baseplatform.core.service.SysParamService;
import com.cattsoft.baseplatform.core.util.IdConverter;
import com.cattsoft.baseplatform.core.util.IdConvertionBean;
import com.cattsoft.baseplatform.core.util.IdDomainBean;
import com.cattsoft.baseplatform.func.sm.util.SysConstants;
import com.cattsoft.baseplatform.web.action.Action;

/**
 * 系统 参数管理
 * 
 * @author wanghuacun
 * 
 */
public class SysParamManagerAction extends Action {

	private static final long serialVersionUID = -3253866890867313548L;

	private SysParamCacheManager sysParamCacheManager;

	private SysParamService sysParamService;

	private SysParam sysParamObj;

	private List<SysParam> sysParams;

	private PagingResultBean<List<IdConvertionBean<SysParam>>> sysParamsResult;
	private IdConverter<SysParam> idConverter;
	private String paramCodeStr;

	private String enabledFlag;

	private boolean flag;

	/**
	 * 跳转到系统参数管理页面
	 * 
	 * @return
	 */
	public String jumpSysParamManager() {
		return "jumpSysParamManager";
	}

	/**
	 * 获取SysParam
	 * 
	 * @return
	 */
	public String getSysParam() {
		sysParamObj = null == sysParamObj ? new SysParam() : sysParamObj;
		HttpServletRequest request = ServletActionContext.getRequest();
		PagingInfo pagingInfo = new PagingInfo();
		pagingInfo.setPageRows(NumberUtils.toInt(request.getParameter("pqb.pagingInfo.pageRows"),
				10));
		pagingInfo.setPageNum(NumberUtils.toInt(request.getParameter("pqb.pagingInfo.pageNum"), 1));
		/* 查询所有满足条件的列表 */
		sysParams = sysParamCacheManager.getSysParams(sysParamObj.getEnabledFlag(),
				sysParamObj.getParamCode(), sysParamObj.getParamName());
		/* 设置记录总数 */
		pagingInfo.setTotalRows(sysParams.size());
		/* 内存中进行分页 */
		List<SysParam> pagingSysParam = new ArrayList<SysParam>();
		int startIdx = pagingInfo.getStartIdx();
		int endIndex = Math.min(pagingInfo.getEndIdx(), sysParams.size());
		/* 分页标签中的索引号是从1开始的 */
		for (int index = (startIdx - 1); index < endIndex; index++) {
			pagingSysParam.add(sysParams.get(index));
		}
		sysParamsResult = new PagingResultBean<List<IdConvertionBean<SysParam>>>();
		sysParamsResult.setPagingInfo(pagingInfo);
		/* 进行键值对的转换 */
		List<IdDomainBean> converFields = new ArrayList<IdDomainBean>();
		IdDomainBean idDomain = new IdDomainBean();
		idDomain.setDomainCode(SysConstants.DomainCode.COMMON_ENABLED_FLAG);
		idDomain.setIdCode("enabledFlag");
		converFields.add(idDomain);
		sysParamsResult.setResultList(this.idConverter.convert(pagingSysParam, converFields));
		/* 释放对象 */
		sysParams = null;
		return "getSysParam";
	}

	/**
	 * 启用/停用系统参数
	 * 
	 * @return
	 */
	public String editSysParamSts() {
		this.flag = false;
		if (StringUtils.isNotBlank(paramCodeStr) && StringUtils.isNotBlank(enabledFlag)) {
			String[] paramCodes = paramCodeStr.split(",");
			List<SysParam> sysParams = new ArrayList<SysParam>();
			Map<String, String> sysParamEnableMap = new HashMap<String, String>();
			for (String paramCode : paramCodes) {
				/* 获得缓存中的业务参数对象 */
				SysParam sysParam = sysParamCacheManager.getSysParam(paramCode);
				sysParamEnableMap.put(paramCode, sysParam.getEnabledFlag());
				if (SysConstants.BooleanFlag.TRUE.equals(enabledFlag)) {
					/* 直接修改该业务参数对象 */
					sysParam.setEnabledFlag(SysConstants.BooleanFlag.TRUE);
				} else {
					sysParam.setEnabledFlag(SysConstants.BooleanFlag.FALSE);
				}
				sysParams.add(sysParam);
			}
			try {
				/* 批量更新数据库 */
				sysParamService.batchModifySysParam(sysParams);
				this.flag = true;
			} catch (Exception e) {
				this.flag = false;
				// 还原缓存内的信息
				for (SysParam sysParam : sysParams) {
					sysParam.setEnabledFlag(sysParamEnableMap.get(sysParam.getParamCode()));
				}
			}
		}

		return "editSysParamSts";
	}

	/**
	 * 获取系统参数详细信息
	 * 
	 * @return
	 */
	public String getSysParamDetail() {
		if (StringUtils.isNotBlank(paramCodeStr)) {
			sysParamObj = sysParamCacheManager.getSysParam(paramCodeStr);
		}
		return "getSysParamDetail";
	}

	/**
	 * 修改系统参数
	 * 
	 * @return
	 */
	public String editSysParam() {
		this.flag = false;
		if (sysParamObj != null) {
			try {
				sysParamService.modifySysParam(sysParamObj);
				this.flag = true;
			} catch (Exception e) {
				this.flag = false;
			}
			// 数据库更新成功才更新缓存信息
			if (this.flag) {
				sysParamCacheManager.modifyParamValue(sysParamObj.getParamCode(),
						sysParamObj.getParamValue());
			}
		}

		return "editSysParam";
	}

	public SysParamCacheManager getSysParamCacheManager() {
		return sysParamCacheManager;
	}

	public void setSysParamCacheManager(SysParamCacheManager sysParamCacheManager) {
		this.sysParamCacheManager = sysParamCacheManager;
	}

	public SysParamService getSysParamService() {
		return sysParamService;
	}

	public void setSysParamService(SysParamService sysParamService) {
		this.sysParamService = sysParamService;
	}

	public SysParam getSysParamObj() {
		return sysParamObj;
	}

	public void setSysParamObj(SysParam sysParamObj) {
		this.sysParamObj = sysParamObj;
	}

	public List<SysParam> getSysParams() {
		return sysParams;
	}

	public void setSysParams(List<SysParam> sysParams) {
		this.sysParams = sysParams;
	}

	public PagingResultBean<List<IdConvertionBean<SysParam>>> getSysParamsResult() {
		return sysParamsResult;
	}

	public void setSysParamsResult(
			PagingResultBean<List<IdConvertionBean<SysParam>>> sysParamsResult) {
		this.sysParamsResult = sysParamsResult;
	}

	public void setIdConverter(IdConverter<SysParam> idConverter) {
		this.idConverter = idConverter;
	}

	public String getParamCodeStr() {
		return paramCodeStr;
	}

	public void setParamCodeStr(String paramCodeStr) {
		this.paramCodeStr = paramCodeStr;
	}

	public String getEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}