package com.cattsoft.baseplatform.func.sm.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.ServletActionContext;

import com.cattsoft.baseplatform.cache.BusiParamCacheManager;
import com.cattsoft.baseplatform.core.entity.BusiParam;
import com.cattsoft.baseplatform.core.entity.PagingInfo;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.core.service.BusiParamService;
import com.cattsoft.baseplatform.core.util.IdConverter;
import com.cattsoft.baseplatform.core.util.IdConvertionBean;
import com.cattsoft.baseplatform.core.util.IdDomainBean;
import com.cattsoft.baseplatform.func.sm.util.SysConstants;
import com.cattsoft.baseplatform.web.action.Action;

/**
 * 业务参数管理
 * 
 * @author wanghuacun
 * 
 */
public class BusiParamManagerAction extends Action {
	private static final long serialVersionUID = -3253866890867313548L;

	private BusiParamCacheManager busiParamCacheManager;

	private BusiParamService busiParamService;

	private BusiParam busiParamObj;

	private List<BusiParam> busiParams;

	private PagingResultBean<List<IdConvertionBean<BusiParam>>> busiParamsResult;

	private String paramCodeStr;

	private String enabledFlag;

	private boolean flag;

	private IdConverter<BusiParam> idConverter;

	/**
	 * 跳转到系统参数管理页面
	 * 
	 * @return
	 */
	public String jumpBusiParamManager() {
		return "jumpBusiParamManager";
	}

	/**
	 * 获取BusiParam
	 * 
	 * @return
	 */
	public String getBusiParam() {
		busiParamObj = null == busiParamObj ? new BusiParam() : busiParamObj;
		HttpServletRequest request = ServletActionContext.getRequest();
		PagingInfo pagingInfo = new PagingInfo();
		pagingInfo.setPageRows(NumberUtils.toInt(request.getParameter("pqb.pagingInfo.pageRows"),
				10));
		pagingInfo.setPageNum(NumberUtils.toInt(request.getParameter("pqb.pagingInfo.pageNum"), 1));
		/* 查询所有满足条件的列表 */
		busiParams = busiParamCacheManager.getBusiParams(busiParamObj.getEnabledFlag(),
				busiParamObj.getParamCode(), busiParamObj.getParamName());
		/* 设置记录总数 */
		pagingInfo.setTotalRows(busiParams.size());
		/* 内存中进行分页 */
		List<BusiParam> pagingBusiParam = new ArrayList<BusiParam>();
		int startIdx = pagingInfo.getStartIdx();
		int endIndex = Math.min(pagingInfo.getEndIdx(), busiParams.size());
		/* 分页标签中的索引号是从1开始的 */
		for (int index = (startIdx - 1); index < endIndex; index++) {
			pagingBusiParam.add(busiParams.get(index));
		}
		busiParamsResult = new PagingResultBean<List<IdConvertionBean<BusiParam>>>();
		busiParamsResult.setPagingInfo(pagingInfo);
		/* 进行键值对的转换 */
		List<IdDomainBean> converFields = new ArrayList<IdDomainBean>();
		IdDomainBean idDomain = new IdDomainBean();
		idDomain.setDomainCode(SysConstants.DomainCode.COMMON_ENABLED_FLAG);
		idDomain.setIdCode("enabledFlag");
		converFields.add(idDomain);
		busiParamsResult.setResultList(this.idConverter.convert(pagingBusiParam, converFields));
		/* 释放对象 */
		busiParams = null;
		return "getBusiParam";
	}

	/**
	 * 启用/停用业务参数
	 * 
	 * @return
	 */
	public String editBusiParamSts() {
		this.flag = false;
		if (StringUtils.isNotBlank(paramCodeStr) && StringUtils.isNotBlank(enabledFlag)) {
			String[] paramCodes = paramCodeStr.split(",");
			List<BusiParam> busiParams = new ArrayList<BusiParam>();
			Map<String, String> busiParamEnableMap = new HashMap<String, String>();
			for (String paramCode : paramCodes) {
				/* 获得缓存中的业务参数对象 */
				BusiParam busiParam = busiParamCacheManager.getBusiParam(paramCode);
				busiParamEnableMap.put(paramCode, busiParam.getEnabledFlag());
				if (SysConstants.BooleanFlag.TRUE.equals(enabledFlag)) {
					/* 直接修改该业务参数对象 */
					busiParam.setEnabledFlag(SysConstants.BooleanFlag.TRUE);
				} else {
					busiParam.setEnabledFlag(SysConstants.BooleanFlag.FALSE);
				}
				busiParams.add(busiParam);
			}
			try {
				/* 批量更新数据库 */
				busiParamService.batchModifyBusiParam(busiParams);
				this.flag = true;
			} catch (Exception e) {
				this.flag = false;
				// 还原缓存内的信息
				for (BusiParam busiParam : busiParams) {
					busiParam.setEnabledFlag(busiParamEnableMap.get(busiParam.getParamCode()));
				}
			}
		}

		return "editBusiParamSts";
	}

	/**
	 * 获取业务参数详细信息
	 * 
	 * @return
	 */
	public String getBusiParamDetail() {
		if (StringUtils.isNotBlank(paramCodeStr)) {
			busiParamObj = busiParamCacheManager.getBusiParam(paramCodeStr);
		}
		return "getBusiParamDetail";
	}

	/**
	 * 修改业务参数
	 * 
	 * @return
	 */
	public String editBusiParam() {
		this.flag = false;
		if (busiParamObj != null) {
			try {
				busiParamService.modifyBusiParam(busiParamObj);
				this.flag = true;
			} catch (Exception e) {
				e.printStackTrace();
				this.flag = false;
			}
			// 数据库更新成功才更新缓存信息
			if (this.flag) {
				busiParamCacheManager.modifyParamValue(busiParamObj.getParamCode(),
						busiParamObj.getParamValue());
			}
		}
		return "editBusiParam";
	}

	public BusiParamCacheManager getBusiParamCacheManager() {
		return busiParamCacheManager;
	}

	public void setBusiParamCacheManager(BusiParamCacheManager busiParamCacheManager) {
		this.busiParamCacheManager = busiParamCacheManager;
	}

	public BusiParamService getBusiParamService() {
		return busiParamService;
	}

	public void setBusiParamService(BusiParamService busiParamService) {
		this.busiParamService = busiParamService;
	}

	public BusiParam getBusiParamObj() {
		return busiParamObj;
	}

	public void setBusiParamObj(BusiParam busiParamObj) {
		this.busiParamObj = busiParamObj;
	}

	public List<BusiParam> getBusiParams() {
		return busiParams;
	}

	public void setBusiParams(List<BusiParam> busiParams) {
		this.busiParams = busiParams;
	}

	public PagingResultBean<List<IdConvertionBean<BusiParam>>> getBusiParamsResult() {
		return busiParamsResult;
	}

	public void setBusiParamsResult(
			PagingResultBean<List<IdConvertionBean<BusiParam>>> busiParamsResult) {
		this.busiParamsResult = busiParamsResult;
	}

	public void setIdConverter(IdConverter<BusiParam> idConverter) {
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