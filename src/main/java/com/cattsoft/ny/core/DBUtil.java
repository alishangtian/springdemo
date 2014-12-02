package com.cattsoft.ny.core;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;

import com.cattsoft.baseplatform.core.util.ApplicationContextUtil;
import com.cattsoft.ny.base.entity.Portalcfg;
import com.cattsoft.ny.base.service.PortalcfgService;

/**
 * 数据库操作相关
 * DBUtil 为单例
 * @author yangzhuang
 * @date 2014-06-25
 */
public class DBUtil {

	private Properties dbcpConfig = new Properties();
	
	private static DBUtil dbUtil;
	
	private Map<Long,SqlSessionFactory> sqlSessioneMap = new  HashMap<Long,SqlSessionFactory>();
	
	private static ApplicationContextUtil appContext;
	
	private static PortalcfgService portalcfgService;
	
	private DBUtil() throws Exception{
	}
	
	@SuppressWarnings("static-access")
	public DBUtil getDBUtil()throws Exception{
		if(dbUtil==null){
			dbUtil = new DBUtil();
			dbUtil.init();
			dbUtil.sqlSessioneMap.put(-1L,(SqlSessionFactory)appContext.getBean("sqlSessionFactory"));
			List<Portalcfg> dbs =  portalcfgService.getAllPortalcfgs();
			if(dbs!=null&&dbs.size()>0){
				for(int i=0;i<dbs.size();i++){
					Portalcfg db = dbs.get(i);
					DBConnect dbConnect = new DBConnect("mysql");
					String connectStr = "jdbc:mysql://"+db.getDatabaseIp()+"/"+db.getDatabaseName();
					dbConnect.setConnect(connectStr);
					dbConnect.setKey(db.getCustId());
					dbConnect.setUserName(db.getUserName());
					dbConnect.setPassword(db.getUserPasswd());
					dbUtil.initDataSource(dbConnect);
				}
		}
		}
		return dbUtil;
	}
	
	/**
	 * 初始化信息
	 */
	private void init()throws Exception{
		InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("dbcp-config.properties");
		dbcpConfig.load(in);
	}
	
	/**
	 * 初始化数据源信息
	 */
	public void initDataSource(DBConnect dbConnect)throws Exception{
		SqlSessionFactory sqlSessionFactory = sqlSessioneMap.get(-1L);
		BasicDataSource dataSource = this.getBasicDataSource();
		dataSource.setDriverClassName(dbConnect.getDriveClass());
		dataSource.setUrl(dbConnect.getConnect());
		dataSource.setUsername(dbConnect.getUserName());
		dataSource.setPassword(dbConnect.getPassword());
		Configuration config = sqlSessionFactory.getConfiguration();
		TransactionFactory transactionFactory = config.getEnvironment().getTransactionFactory();
		Environment evment = new Environment(dbConnect.getKey().toString(), transactionFactory, dataSource);
		Configuration configuration = new Configuration(evment);
		sqlSessioneMap.put(dbConnect.getKey(), new SqlSessionFactoryBuilder().build(configuration));
	}
	
	private BasicDataSource getBasicDataSource()throws Exception{
		BasicDataSource dataSource = new BasicDataSource();
		if(dbcpConfig == null) this.init();
		dataSource.setMaxIdle(Integer.valueOf(dbcpConfig.getProperty("dbcp.maxIdle")).intValue());
		dataSource.setMaxActive(Integer.valueOf(dbcpConfig.getProperty("dbcp.maxActive")).intValue());
		dataSource.setMinEvictableIdleTimeMillis(Integer.valueOf(dbcpConfig.getProperty("dbcp.minEvictableIdleTimeMillis")).intValue());
		dataSource.setTimeBetweenEvictionRunsMillis(Integer.valueOf(dbcpConfig.getProperty("dbcp.timeBetweenEvictionRunsMillis")).intValue());
		dataSource.setDefaultAutoCommit(Boolean.getBoolean(dbcpConfig.getProperty("dbcp.defaultAutoCommit")));
		dataSource.setTestOnBorrow(Boolean.getBoolean(dbcpConfig.getProperty("dbcp.testOnBorrow")));
		dataSource.setTestOnReturn(Boolean.getBoolean(dbcpConfig.getProperty("dbcp.testOnReturn")));
		dataSource.setTestWhileIdle(Boolean.getBoolean(dbcpConfig.getProperty("dbcp.testWhileIdle")));
		dataSource.setValidationQuery(dbcpConfig.getProperty("dbcp.validationQuery"));
		return dataSource;
	}

	public SqlSessionFactory getSqlSessionFactoryBean(Long key){
		return this.sqlSessioneMap.get(key);
	}
	
	public void setAppContext(ApplicationContextUtil appContext) {
		this.appContext = appContext;
	}

	private ApplicationContextUtil getAppContext() {
		return appContext;
	}

	public void setPortalcfgService(PortalcfgService portalcfgService) {
		this.portalcfgService = portalcfgService;
	}
}
