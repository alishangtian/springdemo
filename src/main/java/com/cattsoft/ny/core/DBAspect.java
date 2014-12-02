package com.cattsoft.ny.core;

import java.lang.reflect.Field;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.aspectj.lang.JoinPoint;

public class DBAspect {

	
	private static DBUtil dbUtil;
	//调用component之前进行 mapper 重新配置
	 public void doBefore(JoinPoint jp)throws Exception{
		 SqlSessionFactory sqlSessionFactory = dbUtil.getSqlSessionFactoryBean(1L);
		 Configuration configuration = sqlSessionFactory.getConfiguration();
		 Object targetObj = jp.getTarget();
		 Field[] fields = targetObj.getClass().getDeclaredFields();
		 for(int i=0;i<fields.length;i++){
			 String mapperObjName = fields[i].getName();
			 if(mapperObjName!=null&&mapperObjName.indexOf("Mapper")>0){
				 Class<?> mapperName = fields[i].getClass();
				 if(!configuration.hasMapper(mapperName))
					 configuration.addMapper(mapperName);
				 fields[i].setAccessible(true);
				 fields[i].set(targetObj,configuration.getMapper(mapperName,sqlSessionFactory.openSession()));
			 }
			 
		 }
	 }
	@SuppressWarnings("static-access")
	public void setDbUtil(DBUtil dbUtil) {
		this.dbUtil = dbUtil;
	}
}
