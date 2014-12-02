package com.cattsoft.ny.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cattsoft.ny.base.entity.EquipDataEnvdata;
import com.cattsoft.ny.base.entity.EquipDataType;
import com.cattsoft.ny.base.entity.EquipDataTypeCommon;
import com.cattsoft.ny.base.entity.EquipStateData;
import com.cattsoft.ny.base.entity.EquipStateType;
import com.cattsoft.ny.base.entity.querybean.EquipDataEnvdataQB;
import com.cattsoft.ny.base.entity.querybean.EquipDataTypeQB;
import com.cattsoft.ny.base.entity.querybean.EquipStateDataQB;
import com.cattsoft.ny.base.entity.querybean.EquipStateTypeQB;
import com.cattsoft.ny.base.service.EquipDataEnvdataService;
import com.cattsoft.ny.base.service.EquipDataTypeService;
import com.cattsoft.ny.base.service.EquipStateDataService;
import com.cattsoft.ny.base.service.EquipStateTypeService;

public class EquipDataTypeUtil {
	public EquipDataTypeService equipDataTypeService;
	public EquipStateTypeService equipStateTypeService;
	public EquipDataEnvdataService equipDataEnvdataService;
	public EquipStateDataService equipStateDataService;
	/**
	 * 新增 采集类型表 设备状态表 
	 * @param Id 设备id
	 * @param type 设备类型
	 * @param factory  设备厂家
	 */
	public void insertEquipDataType(Long Id, String type,String factory,String cgqType){
			EquipDataType edt = new EquipDataType();
			if(EquipDataTypeCommon.airhum.equals(type)){
				//空气温湿度 新增  EQUIP_DATA_TYPE两条数据 一条 温度 一条湿度
				List<EquipDataType> listEdt = new ArrayList<EquipDataType>();
				edt.setEquipInfoId(Id);
				edt.setTime(new Date());
				edt.setName(EquipDataTypeCommon.kq_Temperature);
				edt.setUnits("°C");
				edt.setAcquType(cgqType);
				listEdt.add(edt);
				equipDataTypeService.createEquipDataType(edt);
				edt=null;
				edt =new EquipDataType();
				edt.setEquipInfoId(Id);
				edt.setTime(new Date());
				edt.setName(EquipDataTypeCommon.kq_Humidity);
				edt.setUnits("°C");
				edt.setAcquType(cgqType);
				listEdt.add(edt);
				equipDataTypeService.createEquipDataType(edt);
				//可以批量插入数据库
			}
			if(EquipDataTypeCommon.soilhum.equals(type)){
				//土壤温湿度
				List<EquipDataType> listEdt = new ArrayList<EquipDataType>();
				edt.setEquipInfoId(Id);
				edt.setTime(new Date());
				edt.setName(EquipDataTypeCommon.tr_Temperature);
				edt.setUnits("°C");
				edt.setAcquType(cgqType);
				listEdt.add(edt);
				equipDataTypeService.createEquipDataType(edt);
				edt=null;
				edt =new EquipDataType();
				edt.setEquipInfoId(Id);
				edt.setTime(new Date());
				edt.setName(EquipDataTypeCommon.tr_Humidity);
				edt.setUnits("°C");
				edt.setAcquType(cgqType);
				listEdt.add(edt);
				equipDataTypeService.createEquipDataType(edt);
				edt=null;
				edt =new EquipDataType();
				edt.setEquipInfoId(Id);
				edt.setTime(new Date());
				edt.setName(EquipDataTypeCommon.tr_xdHumidity);
				edt.setUnits("°C");
				edt.setAcquType(cgqType);
				listEdt.add(edt);
				equipDataTypeService.createEquipDataType(edt);
				//批量插入数据库
			}
		if(EquipDataTypeCommon.node.equals(type)){
			//节点
			EquipStateType est = new EquipStateType();
			List<EquipStateType> listEst = new ArrayList<EquipStateType>();
			est.setEquipInfoId(Id);
			est.setName(EquipDataTypeCommon.tyn_Voltage);
			est.setUnits("V");
			listEst.add(est);
			equipStateTypeService.createEquipStateType(est);
			est=null;
			est =  new EquipStateType();
			est.setEquipInfoId(Id);
			est.setName(EquipDataTypeCommon.dc_Voltage);
			est.setUnits("V");
			listEst.add(est);
			equipStateTypeService.createEquipStateType(est);
			//批量插入数据库
		}
	}


	/**
	 * 得到类型数据
	 * @param id
	 * @param type
	 * @param  
	 * @return
	 */
	public List  equipListInfo(Long id,String type,String cgqType){
		if(""!=type && null!=type){
			if(EquipDataTypeCommon.node.equals(type)){
				EquipStateTypeQB queryBean = new EquipStateTypeQB();
				queryBean.setEquipInfoId(id);
				//查询EQUIP_STATE_TYPE表
				return  equipStateTypeService.getEquipStateTypes(queryBean );
			}
			if(EquipDataTypeCommon.airhum.equals(type)||EquipDataTypeCommon.soilhum.equals(type)){
				EquipDataTypeQB queryBean = new EquipDataTypeQB(); 	
				queryBean.setEquipInfoId(id);
				queryBean.setAcquType(cgqType);
				//根据id 查询EQUIP_DATA_TYPE表
				return equipDataTypeService.getEquipDataTypes(queryBean );
			}
		}
		return null;
	}
	/**
	 * 根据采集时间 和采集类型id 查询EQUIP_DATA_ENVDATA表 是否已经有数据 c_time EQUIP_DATA_TYPE_ID为条件
	 * 有数据返回  true  否则返回 false
	 * @return
	 */
	public boolean isNotEquipDataEnvInfo(Date dateTime,Long id){
		EquipDataEnvdataQB queryBean = new EquipDataEnvdataQB();
		queryBean.setCtime(dateTime);
		queryBean.setEquipDataTypeId(id);
		List<EquipDataEnvdata> listInfo = equipDataEnvdataService.getEquipDataEnvdatasTime(queryBean);
		if(null!=listInfo && listInfo.size()>0){
			return true;
		}
		return false;	
	}
	/**
	 * 根据采集时间 和采集类型id  查询EQUIP_STATE_DATA表 是否已经有数据 
	 * 有数据返回  true  否则返回 false
	 * @return
	 */
	public boolean isNotEquipStateInfo(Date dateTime,Long id){
		EquipStateDataQB queryBean = new EquipStateDataQB();
		queryBean.setTime(dateTime);
		queryBean.setEquipStateTypeId(id);
		List<EquipStateData> listInfo = equipStateDataService.getEquipStateDatas(queryBean);
		if(null!=listInfo&&listInfo.size()>0){
			return true;
		}
		return false;
	}

	public EquipDataTypeService getEquipDataTypeService() {
		return equipDataTypeService;
	}


	public void setEquipDataTypeService(EquipDataTypeService equipDataTypeService) {
		this.equipDataTypeService = equipDataTypeService;
	}


	public EquipStateTypeService getEquipStateTypeService() {
		return equipStateTypeService;
	}


	public void setEquipStateTypeService(EquipStateTypeService equipStateTypeService) {
		this.equipStateTypeService = equipStateTypeService;
	}


	public EquipDataEnvdataService getEquipDataEnvdataService() {
		return equipDataEnvdataService;
	}


	public void setEquipDataEnvdataService(
			EquipDataEnvdataService equipDataEnvdataService) {
		this.equipDataEnvdataService = equipDataEnvdataService;
	}


	public EquipStateDataService getEquipStateDataService() {
		return equipStateDataService;
	}


	public void setEquipStateDataService(EquipStateDataService equipStateDataService) {
		this.equipStateDataService = equipStateDataService;
	}

}
