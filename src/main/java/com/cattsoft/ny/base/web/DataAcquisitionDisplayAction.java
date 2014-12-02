package com.cattsoft.ny.base.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.EquipDataEnvdataInfo;
import com.cattsoft.ny.base.entity.EquipStateData;
import com.cattsoft.ny.base.entity.EquipStateDataInfo;
import com.cattsoft.ny.base.entity.querybean.EquipStateDataQB;
import com.cattsoft.ny.base.service.EquipDataEnvdataService;
import com.cattsoft.ny.base.service.EquipStateDataService;

public class DataAcquisitionDisplayAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private EquipStateData equipStateData;
	private  List<EquipStateDataInfo> equipStateDataResult;
	private PagingQueryBean<EquipStateDataQB> pqb;
	private EquipStateDataService equipStateDataService;
	private EquipDataEnvdataService equipDataEnvdataService;
	
	@Override
	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		return "dataAcquisitionDisplayInfo";
	}
	
	public String getDataAcquisitionDisplay() {
		try{
		HttpServletRequest request = ServletActionContext.getRequest();
		String typeinfo = request.getParameter("typeinfo");
		if("1".equals(typeinfo)){
			EquipStateDataQB equipStateDataQB = new EquipStateDataQB();
			this.equipStateDataResult =  equipStateDataService.getEquipStateDatasInfo(equipStateDataQB);
			request.setAttribute("equipStateDataResult", equipStateDataResult);
		}else{
			//2查询 空气传感器,3 查询土壤传感器
			List<EquipDataEnvdataInfo> edes  = equipDataEnvdataService.getEquipDataEnvdatasInfo(Long.parseLong(typeinfo) );
			request.setAttribute("edes", edes);
		}
		return "dataAcquisitionDisplay";
		}catch (Exception e) {
			return null;
		}
	}

	public EquipStateData getEquipStateData() {
		return equipStateData;
	}

	public void setEquipStateData(EquipStateData equipStateData) {
		this.equipStateData = equipStateData;
	}


	public PagingQueryBean<EquipStateDataQB> getPqb() {
		return pqb;
	}

	public void setPqb(PagingQueryBean<EquipStateDataQB> pqb) {
		this.pqb = pqb;
	}

	public EquipStateDataService getEquipStateDataService() {
		return equipStateDataService;
	}

	public void setEquipStateDataService(EquipStateDataService equipStateDataService) {
		this.equipStateDataService = equipStateDataService;
	}

	public List<EquipStateDataInfo> getEquipStateDataResult() {
		return equipStateDataResult;
	}

	public void setEquipStateDataResult(
			List<EquipStateDataInfo> equipStateDataResult) {
		this.equipStateDataResult = equipStateDataResult;
	}

	public EquipDataEnvdataService getEquipDataEnvdataService() {
		return equipDataEnvdataService;
	}

	public void setEquipDataEnvdataService(
			EquipDataEnvdataService equipDataEnvdataService) {
		this.equipDataEnvdataService = equipDataEnvdataService;
	}

}
