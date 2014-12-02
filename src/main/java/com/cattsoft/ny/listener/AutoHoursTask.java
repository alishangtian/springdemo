package com.cattsoft.ny.listener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.cattsoft.ny.base.entity.EquipDataEnvdata;
import com.cattsoft.ny.base.entity.EquipDataType;
import com.cattsoft.ny.base.entity.EquipDataTypeCommon;
import com.cattsoft.ny.base.entity.EquipInfo;
import com.cattsoft.ny.base.entity.EquipStateData;
import com.cattsoft.ny.base.entity.EquipStateType;
import com.cattsoft.ny.base.entity.SeaboMxn880StatusResultL;
import com.cattsoft.ny.base.entity.SeaboMxs1201ResultL;
import com.cattsoft.ny.base.entity.SeaboMxs1402aResultL;
import com.cattsoft.ny.base.entity.querybean.EquipInfoQB;
import com.cattsoft.ny.base.service.EquipDataEnvdataService;
import com.cattsoft.ny.base.service.EquipDataTypeService;
import com.cattsoft.ny.base.service.EquipInfoService;
import com.cattsoft.ny.base.service.EquipStateDataService;
import com.cattsoft.ny.base.service.SeaboMxn880StatusResultLService;
import com.cattsoft.ny.base.service.SeaboMxs1201ResultLService;
import com.cattsoft.ny.base.service.SeaboMxs1402aResultLService;
import com.cattsoft.ny.core.EquipDataTypeUtil;

public class AutoHoursTask {
	//节点状态表
	public SeaboMxn880StatusResultLService seaboMxn880StatusResultLService;
	//空气温湿度表
	public SeaboMxs1201ResultLService seaboMxs1201ResultLService;
	//土壤温湿度 
	public SeaboMxs1402aResultLService seaboMxs1402aResultLService;
	//设备表
	public EquipInfoService equipInfoService;
	public EquipDataTypeService equipDataTypeService;
	public EquipDataTypeUtil equipDataTypeUtil;
	public EquipDataEnvdataService equipDataEnvdataService;
	public EquipStateDataService equipStateDataService;
	public void autoHoursStart(){
		try{
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
			System.out.println("开始自动扫描"+sf.format(new Date()));
			//第一步 扫描数据表    第二步 得到数据后 按相应规则插入到相应表中
			//seabo_mxs1402a_result_l 土壤温湿度 1分钟刷新一次
			//seabo_mxs1201_result_l 空气温湿度表  seabo_mxn880_status_result_l 节点状态表 3分钟执行一次
			List<SeaboMxn880StatusResultL>	sm880 = seaboMxn880StatusResultLService.getAllSeaboMxn880StatusResultLs();
			List<SeaboMxs1201ResultL> sm1201 = seaboMxs1201ResultLService.getAllSeaboMxs1201ResultLs();
			List<SeaboMxs1402aResultL> sm1402 = seaboMxs1402aResultLService.getAllSeaboMxs1402aResultLs();
			//节点
			this.handleNodeMethod(sm880);
			//空气
			this.handleAirMethod(sm1201);
			//土壤
			this.handleSoilMethod(sm1402);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	//处理节点信息 
	public void handleNodeMethod(List<SeaboMxn880StatusResultL> list){
		try {
			if(null!=list || list.size()>0){
				for(int i=0;i<list.size();i++){
					EquipInfo einfo = new EquipInfo();
					einfo.setDeviceId(list.get(0).getUid());
					//根据uuid 查询设备表 equip_info 得到网关信息            条件："list会带 uuid"
					List<EquipInfo> eqList =  this.oneEquipInfo(einfo);
					//得到信息 取 客户id  和传过来的类型作为条件查询 设备表
					if(null!=eqList||eqList.size()>0){
						//einfo = null;
						einfo = new EquipInfo();
						//客户编号
						einfo.setCustId(eqList.get(0).getCustId());
						einfo.setDeviceId(list.get(0).getNodeid()+"");
						List<EquipInfo> ei = this.oneEquipInfo(einfo);
						//根据 设备id  设备类型 查询 EQUIP_STATE_TYPE （设备）设备状态指标表
						List<EquipStateType> ls = equipDataTypeUtil.equipListInfo(ei.get(0).getId(), EquipDataTypeCommon.node,"");
						if(null==ls || ls.isEmpty()){
							equipDataTypeUtil.insertEquipDataType(ei.get(0).getId(), EquipDataTypeCommon.node, ei.get(0).getFactory(),"");
							ls = equipDataTypeUtil.equipListInfo(ei.get(0).getId(), EquipDataTypeCommon.node,"");
						}
						EquipStateData esd = new EquipStateData();
						esd.setTime(list.get(i).getTime());
						for(int j=0;j<ls.size();j++){
							if(!equipDataTypeUtil.isNotEquipStateInfo(list.get(i).getTime(), ls.get(j).getId())){
								 if(EquipDataTypeCommon.tyn_Voltage.equals(ls.get(j).getName())){
									 //存空气温度信息
									 esd.setEquipStateTypeId(ls.get(j).getId());
									 esd.setValue(Double.parseDouble(list.get(i).getChargeVol()+""));
								 }
								 if(EquipDataTypeCommon.dc_Voltage.equals(ls.get(j).getName())){
									 //空气湿度信息
									 esd.setEquipStateTypeId(ls.get(j).getId());
									 esd.setValue(Double.parseDouble(list.get(i).getBattVol()+""));
								 }
								 equipStateDataService.createEquipStateData(esd);
							}
						}

					}
				}
			}else{
				System.out.println("节点数据为空！");	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**处理空气信息
	 * 1、根据 传过来的 设备id 查询 设备表（EQUIP_INFO） 物理设备id 是否存在
	 * 2、如果不存在  则不做相应操作..  停止下一步操作
	 *    如果存在     则根据 物理设备id 查询 采集类型表（EQUIP_DATA_TYPE） 查询是否 存在 空气温度 空气湿度 
	 * 3、如果存在 则 取 采集类型表中信息  LIST，拿到 采集类型id --->相应的 值 id 插入到 采集设备表 （EQUIP_DATA_ENVDATA）   批量插入或者 单条插入
	 *    如果不存在 则新增 采集类型表（EQUIP_DATA_TYPE）新增时 需要物理设备id 采集设备类型
	 *    -->然后再将值 存到 采集设备表（EQUIP_DATA_ENVDATA）
	 */
	public void handleAirMethod(List<SeaboMxs1201ResultL> list){
		try {
			if(null!=list || list.size()>0){
				for(int i=0;i<list.size();i++){
					EquipInfo einfo = new EquipInfo();
					einfo.setDeviceId(list.get(0).getUid());
					//根据uuid 查询设备表 equip_info 得到网关信息            条件："list会带 uuid"
					List<EquipInfo> eqList =  this.oneEquipInfo(einfo);
					//得到信息 取 客户id  和传过来的类型作为条件查询 设备表
					if(null!=eqList||eqList.size()>0){
						//einfo = null;
						einfo = new EquipInfo();
						//客户编号
						einfo.setCustId(eqList.get(0).getCustId());
						einfo.setDeviceId(list.get(0).getNodeid()+"");
						List<EquipInfo> ei = this.oneEquipInfo(einfo);
						//根据 设备id  设备类型 查询 EQUIP_STATE_TYPE （设备）设备状态指标表
						List<EquipDataType> ls = equipDataTypeUtil.equipListInfo(ei.get(0).getId(), EquipDataTypeCommon.airhum,list.get(0).getBoardId()+"");
						if( null==ls || ls.isEmpty()){
							equipDataTypeUtil.insertEquipDataType(ei.get(0).getId(), EquipDataTypeCommon.airhum, ei.get(0).getFactory(),list.get(0).getBoardId()+"");
							ls = equipDataTypeUtil.equipListInfo(ei.get(0).getId(), EquipDataTypeCommon.airhum,list.get(0).getBoardId()+"");
						}
						EquipDataEnvdata ede = new EquipDataEnvdata();
						ede.setCtime(list.get(i).getTime());
						for(int j=0;j<ls.size();j++){
							//判断EQUIP_DATA_ENVDATA表 是否已经有数据 c_time EQUIP_DATA_TYPE_ID为条件
							if(!equipDataTypeUtil.isNotEquipDataEnvInfo(list.get(i).getTime(), ls.get(j).getId())){
								 if(EquipDataTypeCommon.kq_Temperature.equals(ls.get(j).getName())){
									 //存空气温度信息
									 ede.setEquipDataTypeId(ls.get(j).getId());
									 ede.setValue(list.get(i).getHumtemp());
								 }
								 if(EquipDataTypeCommon.kq_Humidity.equals(ls.get(j).getName())){
									 //空气湿度信息
									 ede.setEquipDataTypeId(ls.get(j).getId());
									 ede.setValue(list.get(i).getHumid());
								 }
								 equipDataEnvdataService.createEquipDataEnvdata(ede);
							}
						}
					}
				}
			}else{
				System.out.println("空气数据为空！");	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//处理土壤信息
	public void handleSoilMethod(List<SeaboMxs1402aResultL> list){
		try {
			if(null!=list || list.size()>0){
				for(int i=0;i<list.size();i++){
					EquipInfo einfo = new EquipInfo();
					einfo.setDeviceId(list.get(0).getUid());
					//根据uuid 查询设备表 equip_info 得到网关信息            条件："list会带 uuid"
					List<EquipInfo> eqList =  this.oneEquipInfo(einfo);
					//得到信息 取 客户id  和传过来的类型作为条件查询 设备表
					if(null!=eqList||eqList.size()>0){
						//einfo = null;
						einfo = new EquipInfo();
						//客户编号
						einfo.setCustId(eqList.get(0).getCustId());
						einfo.setDeviceId(list.get(0).getNodeid()+"");
						List<EquipInfo> ei = this.oneEquipInfo(einfo);
				
						//物理设备id 查询 采集类型表（EQUIP_DATA_TYPE） 查询是否 存在   
						List<EquipDataType> ls = equipDataTypeUtil.equipListInfo(ei.get(0).getId(), EquipDataTypeCommon.soilhum,list.get(0).getBoardId()+"");
						if(null==ls||ls.isEmpty()){
							equipDataTypeUtil.insertEquipDataType(ei.get(0).getId(),EquipDataTypeCommon.soilhum, ei.get(0).getFactory(),list.get(0).getBoardId()+"");
							ls = equipDataTypeUtil.equipListInfo(ei.get(0).getId(), EquipDataTypeCommon.soilhum,list.get(0).getBoardId()+"");
						}	
							EquipDataEnvdata ede = new EquipDataEnvdata();	
							ede.setCtime(list.get(i).getTime());
							for(int j=0;j<ls.size();j++){
								//判断EQUIP_DATA_ENVDATA表 是否已经有数据 c_time EQUIP_DATA_TYPE_ID为条件
								if(!equipDataTypeUtil.isNotEquipDataEnvInfo(list.get(i).getTime(), ls.get(j).getId())){
								 if(EquipDataTypeCommon.tr_Temperature.equals(ls.get(j).getName())){
									 //存土壤温度信息
									 ede.setEquipDataTypeId(ls.get(j).getId());
									 ede.setValue(list.get(i).getSoilTemperature());
								 }
								 if(EquipDataTypeCommon.tr_Humidity.equals(ls.get(j).getName())){
									 //土壤湿度信息
									 ede.setEquipDataTypeId(ls.get(j).getId());
									 ede.setValue(list.get(i).getSoilhumid());
								 }
								 if(EquipDataTypeCommon.tr_xdHumidity.equals(ls.get(j).getName())){
									 //土壤相对水率
									 ede.setEquipDataTypeId(ls.get(j).getId());
									 ede.setValue(list.get(i).getSoilhumidRelative());
								 }
								 equipDataEnvdataService.createEquipDataEnvdata(ede);
								}
							}
					}	
				}			
			}else{
				System.out.println("土壤数据为空！");	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询设备表EQUIP_INFO
	 */
	public List<EquipInfo> allEquipInfo(){
		return  equipInfoService.getAllEquipInfos();
	}
	/**
	 * 条件查询 设备表
	 * deviceId 设备物理id
	 */
	public List<EquipInfo> oneEquipInfo(EquipInfo equipInfo){
		EquipInfoQB queryBean = new EquipInfoQB();
		queryBean.setDeviceId(equipInfo.getDeviceId());
		queryBean.setCustId(equipInfo.getCustId());
		queryBean.setType(equipInfo.getType());
		return equipInfoService.getEquipInfos(queryBean);
	}
	 


	public void setSeaboMxn880StatusResultLService(
			SeaboMxn880StatusResultLService seaboMxn880StatusResultLService) {
		this.seaboMxn880StatusResultLService = seaboMxn880StatusResultLService;
	}

	public SeaboMxs1201ResultLService getSeaboMxs1201ResultLService() {
		return seaboMxs1201ResultLService;
	}

	public void setSeaboMxs1201ResultLService(
			SeaboMxs1201ResultLService seaboMxs1201ResultLService) {
		this.seaboMxs1201ResultLService = seaboMxs1201ResultLService;
	}

	public SeaboMxs1402aResultLService getSeaboMxs1402aResultLService() {
		return seaboMxs1402aResultLService;
	}

	public void setSeaboMxs1402aResultLService(
			SeaboMxs1402aResultLService seaboMxs1402aResultLService) {
		this.seaboMxs1402aResultLService = seaboMxs1402aResultLService;
	}
	public EquipInfoService getEquipInfoService() {
		return equipInfoService;
	}
	public void setEquipInfoService(EquipInfoService equipInfoService) {
		this.equipInfoService = equipInfoService;
	}
	public EquipDataTypeService getEquipDataTypeService() {
		return equipDataTypeService;
	}
	public void setEquipDataTypeService(EquipDataTypeService equipDataTypeService) {
		this.equipDataTypeService = equipDataTypeService;
	}
	public EquipDataTypeUtil getEquipDataTypeUtil() {
		return equipDataTypeUtil;
	}
	public void setEquipDataTypeUtil(EquipDataTypeUtil equipDataTypeUtil) {
		this.equipDataTypeUtil = equipDataTypeUtil;
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
