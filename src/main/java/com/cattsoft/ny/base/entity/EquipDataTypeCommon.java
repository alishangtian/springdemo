package com.cattsoft.ny.base.entity;

public class EquipDataTypeCommon {
	//网关A 节点B 传感器C 气象站D 控制器
	public static final String gateway="1";
	public static final String node="3";
	public static final String sensor="C";
	public static final String qxstation="2";
	public static final String controller="6";
	//传感器类型  空气温湿度 土壤温湿度 二氧化碳	光照强度	土壤PH值	土壤电导率
	public static final String airhum="4";
	public static final String soilhum="5";
	public static final String co2="13";
	public static final String lightIntensity="12";
	public static final String soilpHValue="14";
	public static final String soilConductivity="15";
	//控制器类型  风机 灯光 水泵
	//空气温度 空气湿度  土壤温度 土壤湿度 太阳能充电电压 电池电压
	public static final String kq_Temperature="空气温度";
	public static final String kq_Humidity="空气湿度";
	public static final String tr_Temperature="土壤温度";
	public static final String tr_Humidity="土壤水分";
	public static final String tr_xdHumidity="土壤相对水分";
	public static final String tyn_Voltage="太阳能充电电压";
	public static final String dc_Voltage="电池电压";
	
}
