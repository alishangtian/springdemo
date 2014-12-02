package com.cattsoft.ny.base.web;


import java.util.List;

import com.cattsoft.baseplatform.core.entity.DomainValue;
import com.cattsoft.baseplatform.core.service.DomainService;
import com.cattsoft.ny.base.entity.MsgAlarm;
import com.cattsoft.ny.base.service.BaseGreenHouseInfoService;
import com.cattsoft.ny.base.service.EquipInfoService;
import com.cattsoft.ny.base.service.EquipStateTypeService;
import com.cattsoft.ny.base.service.MsgAlarmService;
import com.cattsoft.ny.base.service.MsgTypeService;



/**
 * 
 * 施肥管理action Copyright:DATANG SOFTWARE CO.LTD<br>
 * 
 * @author Administrator
 * 
 */
public class MsgAlarmAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MsgAlarm msgAlarm;
	private String msgAlarmID;
	private MsgAlarmService msgAlarmService;
	private DomainService domainService;
	private BaseGreenHouseInfoService baseGreenHouseInfoService;
	private MsgTypeService msgTypeService;
	private EquipStateTypeService equipStateTypeService;
	private EquipInfoService equipInfoService;
//*********************************************************************************************
	public String execute() {
		return "jumpMsgAlarmManager";
	}

	
	/**
	 * 告警查看
	 */
	public String viewMsgAlarm(){
		try {
			
			msgAlarm = msgAlarmService.getMsgAlarm(Long.parseLong(msgAlarmID));
			 List<DomainValue> domainValues = domainService.getDomainValues(96L);
			 for(DomainValue dv :domainValues){
					if(msgAlarm.getAlarmLevel().equals(dv.getValueCode())){
						msgAlarm.setAlarmLevelName(dv.getValueName());
						break;
					}
				}
			 msgAlarm.setHouseName(baseGreenHouseInfoService.getBaseGreenHouseInfo(msgAlarm.getHouseId()).getName());
			 msgAlarm.setAlarmTypeName(msgTypeService.getMsgType(msgAlarm.getAlarmTypeId()).getName());
			 msgAlarm.setEquipStateTypeName(equipStateTypeService.getEquipStateType(msgAlarm.getEquipStateTypeId()).getName()); 
			 msgAlarm.setSensorName(equipInfoService.getEquipInfo(msgAlarm.getSensorId()).getName());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return "viewMsgAlarm";
	}
//*********************************************************************************************

	public MsgAlarm getMsgAlarm() {
		return msgAlarm;
	}

	public void setMsgAlarm(MsgAlarm msgAlarm) {
		this.msgAlarm = msgAlarm;
	}


	public String getMsgAlarmID() {
		return msgAlarmID;
	}


	public void setMsgAlarmID(String msgAlarmID) {
		this.msgAlarmID = msgAlarmID;
	}


	public MsgAlarmService getMsgAlarmService() {
		return msgAlarmService;
	}


	public void setMsgAlarmService(MsgAlarmService msgAlarmService) {
		this.msgAlarmService = msgAlarmService;
	}


	public DomainService getDomainService() {
		return domainService;
	}


	public void setDomainService(DomainService domainService) {
		this.domainService = domainService;
	}


	public BaseGreenHouseInfoService getBaseGreenHouseInfoService() {
		return baseGreenHouseInfoService;
	}


	public void setBaseGreenHouseInfoService(
			BaseGreenHouseInfoService baseGreenHouseInfoService) {
		this.baseGreenHouseInfoService = baseGreenHouseInfoService;
	}


	public MsgTypeService getMsgTypeService() {
		return msgTypeService;
	}


	public void setMsgTypeService(MsgTypeService msgTypeService) {
		this.msgTypeService = msgTypeService;
	}


	public EquipStateTypeService getEquipStateTypeService() {
		return equipStateTypeService;
	}


	public void setEquipStateTypeService(EquipStateTypeService equipStateTypeService) {
		this.equipStateTypeService = equipStateTypeService;
	}


	public EquipInfoService getEquipInfoService() {
		return equipInfoService;
	}


	public void setEquipInfoService(EquipInfoService equipInfoService) {
		this.equipInfoService = equipInfoService;
	}
	
}

