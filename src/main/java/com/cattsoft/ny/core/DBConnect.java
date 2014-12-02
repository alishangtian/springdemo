package com.cattsoft.ny.core;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 数据库连接 参数对象
 * @author yangzhuang
 * @date 2014-06-26
 */
public class DBConnect {
	
	private DBConnect(){}
	
	public DBConnect(String dbType)throws Exception{
		this.setDBType(dbType);
	}
	
	private final static Map<String,String> drivesMap = new HashMap<String,String>();
	static{
		drivesMap.put("oracle", "oracle.jdbc.driver.OracleDriver");
		drivesMap.put("mysql", "com.mysql.jdbc.Driver");
		drivesMap.put("sqlserver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
		drivesMap.put("odbc", "sun.jdbc.odbc.JdbcOdbcDriver");
		drivesMap.put("db2", "com.ibm.db2.jdbc.app.DB2Driver");
		drivesMap.put("sybase", "com.sybase.jdbc.Sybdriver");
	}
			
	//数据库连接串
	private String connect;
	//数据库用户名
	private String userName;
	//数据库用户密码
	private String password;
	//数据库类型
	private String DBType;
	
	private String driveClass;
	
	private Long key;

	public String getConnect() {
		return connect;
	}

	public void setConnect(String connect) {
		this.connect = connect;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDBType() {
		return DBType;
	}

	public void setDBType(String dBType)throws Exception{
		if(StringUtils.isBlank(dBType)){
			throw new Exception("数据库类型不能为空");
		}
		if(!drivesMap.containsKey(dBType)){
			throw new Exception("未找到"+dBType+"的驱动类，请重新配置");
		}
		this.driveClass = drivesMap.get(dBType);
		DBType = dBType;
	}

	public String getDriveClass() {
		return driveClass;
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

}
