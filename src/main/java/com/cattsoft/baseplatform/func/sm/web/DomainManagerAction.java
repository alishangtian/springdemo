package com.cattsoft.baseplatform.func.sm.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.ServletActionContext;

import com.cattsoft.baseplatform.cache.DomainCacheManager;
import com.cattsoft.baseplatform.core.entity.Domain;
import com.cattsoft.baseplatform.core.entity.DomainValue;
import com.cattsoft.baseplatform.core.entity.PagingInfo;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.core.service.DomainService;
import com.cattsoft.baseplatform.core.util.IdConverter;
import com.cattsoft.baseplatform.core.util.IdConvertionBean;
import com.cattsoft.baseplatform.core.util.IdDomainBean;
import com.cattsoft.baseplatform.func.sm.util.SysConstants;
import com.cattsoft.baseplatform.web.action.Action;

/**
 * 值域管理
 * 
 * @author wanghuacun
 * 
 */
public class DomainManagerAction extends Action {

	private static final long serialVersionUID = 4454195886648743386L;

	private DomainCacheManager domainCacheManager;

	private DomainService domainService;

	private Domain domainObj;

	private List<Domain> domains;

	@SuppressWarnings("rawtypes")
	private IdConverter idConverter;

	private PagingResultBean<List<IdConvertionBean<Domain>>> domainsResult;

	private List<IdConvertionBean<DomainValue>> domainValues;
	
	private List<String> domainDetailValues;

	private boolean flag;

	private String msg;

	private String domainCodeStr;

	private String domainCode;
	private String valueCodeStr;
	private String enabledFlag;
	private String domainIdStr;

	private DomainValue domainValueObj;

	/**
	 * 跳转到Domain管理界面
	 * 
	 * @return
	 */
	public String jumpDomainManager() {
		return "jumpDomainManager";
	}

	/**
	 * 获取Domain
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDomain() {
		this.domainObj = null == this.domainObj ? new Domain() : this.domainObj;
		HttpServletRequest request = ServletActionContext.getRequest();
		PagingInfo pagingInfo = new PagingInfo();
		pagingInfo.setPageRows(NumberUtils.toInt(request.getParameter("pqb.pagingInfo.pageRows"),
				10));
		pagingInfo.setPageNum(NumberUtils.toInt(request.getParameter("pqb.pagingInfo.pageNum"), 1));
		/* 查询所有满足条件的列表 */
		this.domains = this.domainCacheManager.getDomains(this.domainObj.getDomainType(),
				this.domainObj.getDomainCode(), this.domainObj.getDomainName());
		/* 设置记录总数 */
		pagingInfo.setTotalRows(this.domains.size());
		/* 内存中进行分页 */
		List<Domain> pagingDomains = new ArrayList<Domain>();
		int startIdx = pagingInfo.getStartIdx();
		int endIndex = Math.min(pagingInfo.getEndIdx(), this.domains.size());
		/* 分页标签中的索引号是从1开始的 */
		for (int index = (startIdx - 1); index < endIndex; index++) {
			pagingDomains.add(this.domains.get(index));
		}
		this.domainsResult = new PagingResultBean<List<IdConvertionBean<Domain>>>();
		this.domainsResult.setPagingInfo(pagingInfo);
		/* 进行键值对的转换 */
		List<IdDomainBean> converFields = new ArrayList<IdDomainBean>();
		IdDomainBean idDomain = new IdDomainBean();
		idDomain.setDomainCode(SysConstants.DomainCode.DOMAIN_DOMAIN_TYPE);
		idDomain.setIdCode("domainType");
		converFields.add(idDomain);
		this.domainsResult.setResultList(this.idConverter.convert(pagingDomains, converFields));
		/* 释放对象 */
		this.domains = null;

		return "getDomain";
	}

	/**
	 * 添加Domain
	 * 
	 * @return
	 */
	public String addDomain() {
		this.flag = false;
		if (null == this.domainObj) {
			return "addDomain";
		}
		if (this.domainCacheManager.containsDomain(this.domainObj.getDomainCode())) {
			this.msg = "添加失败：值域代码存在，请重新填写";
			return "addDomain";
		}
		try {
			this.domainService.createDomain(this.domainObj);
			this.flag = true;
		} catch (Exception e) {
			this.flag = false;
		}
		// 入库正常，则更新缓存
		if (this.flag) {
			this.domainCacheManager.putDomain(this.domainObj);
		}
		return "addDomain";
	}

	/**
	 * 获取某个Domain详细信息
	 * 
	 * @return
	 */
	public String getDomainDetail() {
		if (this.domainObj != null) {
			this.domainObj = this.domainCacheManager.getDomain(this.domainObj.getDomainCode());
		}
		return "getDomainDetail";
	}

	/**
	 * 编辑Domain
	 * 
	 * @return
	 */
	public String editDomain() {
		this.flag = false;
		if (null == this.domainObj) {
			return "editDomain";
		}

		try {
			this.domainService.modifyDomain(this.domainObj);
			this.flag = true;
		} catch (Exception e) {
			this.flag = false;
		}
		if (this.flag) {
			this.domainCacheManager.putDomain(this.domainObj);
		}
		return "editDomain";
	}

	/**
	 * 删除Domain
	 * 
	 * @return
	 */
	public String deleteDomain() {
		StringBuilder failCode = new StringBuilder();
		this.flag = false;
		if (StringUtils.isNotBlank(this.domainCodeStr)) {
			String[] domainCodes = this.domainCodeStr.split(",");
			for (String domainCode2 : domainCodes) {
				try {
					this.domainService.removeDomain(this.domainCacheManager.getDomain(domainCode2),
							this.domainCacheManager.getDomainValues(domainCode2));
					this.flag = true;
				} catch (Exception e) {
					failCode.append(domainCode2).append(" ");
					this.flag = false;
				}
				if (this.flag) {
					/* 从缓存内移除Domain缓存(包括DomainValue) */
					this.domainCacheManager.removeDomain(domainCode2);
				}
			}
			this.flag = true;
		}
		if (StringUtils.isNotBlank(failCode.toString())) {
			this.flag = false;
			this.msg = "代码为:" + failCode.toString() + "的值域删除失败";
		} else {
			this.flag = true;
		}
		return "deleteDomain";
	}

	/**
	 * 获取DomainValue
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDomainValue() {
		if (this.domainObj != null) {
			List<DomainValue> originDomainValues = this.domainCacheManager
					.getDomainValues(this.domainObj.getDomainCode());
			for (DomainValue domainValue : originDomainValues) {
				domainValue.setDomainCode(this.domainObj.getDomainCode());
				domainValue.setDomainName(this.domainObj.getDomainName());
			}
			/* 进行键值对的转换 */
			List<IdDomainBean> converFields = new ArrayList<IdDomainBean>();
			IdDomainBean idDomain = new IdDomainBean();
			idDomain.setDomainCode(SysConstants.DomainCode.COMMON_ENABLED_FLAG);
			idDomain.setIdCode("enabledFlag");
			converFields.add(idDomain);
			this.domainValues = this.idConverter.convert(originDomainValues, converFields);
		}
		return "getDomainValue";
	}

	/**
	 * 启用/停用值域取值
	 * 
	 * @return
	 */
	public String editDomainValueSts() {
		this.flag = false;
		if (StringUtils.isNotBlank(this.domainCode) && StringUtils.isNotBlank(this.valueCodeStr)
				&& StringUtils.isNotBlank(this.enabledFlag)) {
			String[] valueCodes = this.valueCodeStr.split(",");
			List<DomainValue> domainValues = new ArrayList<DomainValue>();
			Map<String, String> valueEnableMap = new HashMap<String, String>();
			for (String valueCode : valueCodes) {
				DomainValue domainValue = this.domainCacheManager.getDomainValue(this.domainCode,
						valueCode);
				valueEnableMap.put(valueCode, domainValue.getEnabledFlag());
				domainValue.setEnabledFlag(this.enabledFlag);
				domainValues.add(domainValue);
			}
			try {
				this.domainService.batchModifyDomainValue(domainValues);
				this.flag = true;
			} catch (Exception e) {
				this.flag = false;
				// 还原缓存内的信息
				for (DomainValue domainValue : domainValues) {
					domainValue.setEnabledFlag(valueEnableMap.get(domainValue.getValueCode()));
				}
			}
		}
		return "editDomainValueSts";
	}

	/**
	 * 添加DomainValue
	 * 
	 * @return
	 */
	public String addDomainValue() {
		this.flag = false;
		if (null == this.domainValueObj || StringUtils.isBlank(this.domainCode)) {
			return "addDomainValue";
		}
		List<DomainValue> domainValues = this.domainCacheManager.getDomainValues(domainCode);
		for (DomainValue domainValue : domainValues) {
			if (domainValue.getValueCode().equals(this.domainValueObj.getValueCode())) {
				this.flag = false;
				this.msg = "添加失败：取值代码存在，请重新填写";
				return "addDomainValue";
			} else if (domainValue.getValueName().equals(this.domainValueObj.getValueName())) {
				this.flag = false;
				this.msg = "添加失败：取值名称存在，请重新填写";
				return "addDomainValue";
			}
		}
		try {
			// 取值代码和名称都不重复，则先入数据库
			Domain domain = this.domainCacheManager.getDomain(this.domainCode);
			this.domainValueObj.setDomainId(domain.getDomainId());
			this.domainService.createDomainValue(this.domainValueObj);
			this.flag = true;
		} catch (Exception e) {
			this.flag = false;
		}
		if (this.flag) {
			this.domainCacheManager.putDomainValue(this.domainValueObj);
		}
		return "addDomainValue";
	}

	/**
	 * 获取某个DomainValue详细信息
	 * 
	 * @return
	 */
	public String getDomainValueDetail() {
		if (StringUtils.isNotBlank(this.domainCode) && StringUtils.isNotBlank(this.valueCodeStr)) {
			this.domainValueObj = this.domainCacheManager.getDomainValue(this.domainCode,
					this.valueCodeStr);
		}
		return "getDomainValueDetail";
	}

	/**
	 * 编辑DomainValue
	 * 
	 * @return
	 */
	public String editDomainValue() {
		this.flag = false;
		if (null == this.domainValueObj) {
			return "editDomainValue";
		}
		try {
			Domain domain = this.domainCacheManager.getDomain(this.domainValueObj.getDomainId());
			List<DomainValue> domainValues = this.domainCacheManager.getDomainValues(domain
					.getDomainCode());
			for (DomainValue domainValue : domainValues) {
				if (!domainValue.getValueCode().equals(this.domainValueObj.getValueCode())
						&& domainValue.getValueName().equals(this.domainValueObj.getValueName())) {
					/* 不同的值域取值代码使用同一个值域取值名称，异常 */
					this.msg = "更新失败：取值名称存在，请重新输入";
					return "editDomainValue";
				}
			}
			this.domainService.modifyDomainValue(this.domainValueObj);
			this.flag = true;
		} catch (Exception e) {
			this.flag = false;
		}
		if (this.flag) {
			this.domainCacheManager.putDomainValue(this.domainValueObj);
		}
		return "editDomainValue";
	}

	/**
	 * 删除DomainValue
	 * 
	 * @return
	 */
	public String deleteDomainValue() {
		this.flag = false;
		if (StringUtils.isNotBlank(this.domainCode) && StringUtils.isNotBlank(this.valueCodeStr)) {
			String[] valueCodes = this.valueCodeStr.split(",");
			List<DomainValue> domainValues = new ArrayList<DomainValue>();
			for (String valueCode : valueCodes) {
				DomainValue domainValue = this.domainCacheManager.getDomainValue(this.domainCode,
						valueCode);
				domainValues.add(domainValue);
			}
			try {
				this.domainService.batchRemoveDomainValue(domainValues);
				this.flag = true;
			} catch (Exception e) {
				this.flag = false;
			}
			if (this.flag) {
				for (String valueCode : valueCodes) {
					this.domainCacheManager.removeDomainValue(this.domainCode, valueCode);
				}
			}
			this.flag = true;
		} else {
			this.flag = false;
		}
		return "deleteDomainValue";
	}

	/**
	 * 获取域值
	 * @Title: getValues
	 * @author Mao Xiaobing
	 * @return String 返回类型
	 * @throws
	 */
	public String getValuesByDomainId() {
		try {
			if(StringUtils.isNotBlank(domainIdStr)){
				domainDetailValues = new ArrayList<String>();
				Long domainId = NumberUtils.toLong(domainIdStr);
				List<DomainValue> values  = this.domainService.getDomainValues(domainId);
				if(null != values && values.size() > 0) {
					for(DomainValue dv:values) {
						this.domainDetailValues.add(dv.getValueName());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "getDomainDetailValues";
	}

	public DomainCacheManager getDomainCacheManager() {
		return domainCacheManager;
	}

	public void setDomainCacheManager(DomainCacheManager domainCacheManager) {
		this.domainCacheManager = domainCacheManager;
	}

	public DomainService getDomainService() {
		return domainService;
	}

	public void setDomainService(DomainService domainService) {
		this.domainService = domainService;
	}

	public Domain getDomainObj() {
		return domainObj;
	}

	public void setDomainObj(Domain domainObj) {
		this.domainObj = domainObj;
	}

	public List<Domain> getDomains() {
		return domains;
	}

	public void setDomains(List<Domain> domains) {
		this.domains = domains;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getDomainCodeStr() {
		return domainCodeStr;
	}

	public void setDomainCodeStr(String domainCodeStr) {
		this.domainCodeStr = domainCodeStr;
	}

	public String getDomainCode() {
		return domainCode;
	}

	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}

	public String getValueCodeStr() {
		return valueCodeStr;
	}

	public void setValueCodeStr(String valueCodeStr) {
		this.valueCodeStr = valueCodeStr;
	}

	public String getEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

	public DomainValue getDomainValueObj() {
		return domainValueObj;
	}

	public void setDomainValueObj(DomainValue domainValueObj) {
		this.domainValueObj = domainValueObj;
	}

	public PagingResultBean<List<IdConvertionBean<Domain>>> getDomainsResult() {
		return domainsResult;
	}

	public void setDomainsResult(PagingResultBean<List<IdConvertionBean<Domain>>> domainsResult) {
		this.domainsResult = domainsResult;
	}

	public List<IdConvertionBean<DomainValue>> getDomainValues() {
		return domainValues;
	}

	public void setDomainValues(List<IdConvertionBean<DomainValue>> domainValues) {
		this.domainValues = domainValues;
	}

	public void setIdConverter(@SuppressWarnings("rawtypes") IdConverter idConverter) {
		this.idConverter = idConverter;
	}

	public String getDomainIdStr() {
		return domainIdStr;
	}

	public void setDomainIdStr(String domainIdStr) {
		this.domainIdStr = domainIdStr;
	}

	public List<String> getDomainDetailValues() {
		return domainDetailValues;
	}

	public void setDomainDetailValues(List<String> domainDetailValues) {
		this.domainDetailValues = domainDetailValues;
	}

}