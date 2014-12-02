package com.cattsoft.baseplatform.func.sm.util;

/**
 * 系统常量。
 * 
 * @author wangcl
 */
public class SysConstants {
	/**
	 * 登录用户在Session中的Key值。
	 */
	public static final String LOGIN_USER = "LOGIN_USER";
	/**
	 * 用户初始密码：12ab!@的md5加密串
	 */
	public static final String INIT_PASSWORD = "756a28687778027aca7902188eaa7f4d";
	
	/**
	 * 评价模块 表达式解析
	 */
	public static final String EXPRESSION_REG="[(]|[+]|[-]|[*]|[/]|[%]|[)]|[<]|[>]|[=]";

	public static class Domain {
		public class DomainType {
			/**
			 * 值域类型：枚举型
			 */
			public static final String ENUMERATE = "E";
			/**
			 * 值域类型：输入型
			 */
			public static final String INPUT = "I";
		}
	}

	/**
	 * 显式使用的domainCode的定义
	 * 
	 * @author longtao
	 * 
	 */
	public class DomainCode {
		/* 系统日志的日志分类 */
		public static final String SYS_LOG_LOG_CATEGORY = "SYS_LOG.LOG_CATEGORY";
		/* 系统日志的日志级别 */
		public static final String SYS_LOG_LOG_LEVEL = "SYS_LOG.LOG_LEVEL";

		public static final String COMMON_ENABLED_FLAG = "COMMON.ENABLED_FLAG";

		public static final String DOMAIN_DOMAIN_TYPE = "DOMAIN.DOMAIN_TYPE";

		public static final String STAFF_STS = "STAFF.STS";
		
		public static final String EVAL_INDIDEF_DATATYPE = "EVAL_INDIDEF.DATATYPE";
		
		public static final String MONITOR_DEVICE_TYPE="MONITOR.MONITOR_DEVICE_TYPE";
		
		public static final String HOLIDAYINFO_HOLIDAY_TYPE="HOLIDAYINFO.HOLIDAY_TYPE";
		
		public static final String HOLIDAYINFO_TIME_TYPE="HOLIDAYINFO.TIME_TYPE";
		
		public static final String HOLIDAYINFO_STS="HOLIDAYINFO.STS";
		
		public static final String TIMERJOB_PARAM_ISEMPTY = "TIMERJOB.PARAM.ISEMPTY";
		
		public static final String TIMERJOB_PARAM_METHOD = "TIMERJOB.PARAM.METHOD";
		
		public static final String SMSG_SENT_SM_TYPE = "SMSG_SENT.SMTYPE";
		
		public static final String SMSG_SENT_RECEVIER_SEND_STATUS = "SMSG_SENT_RECEVIER.SEND_STATUS";
		
		public static final String SMSG_BIZSERVICE_SERVICE_TYPE = "SMSG_BIZSERVICE.SERVICE_TYPE";
		
		public static final String MONITOR_KPI_VIEW_VIEW_TYPE = "MONITOR_KPI_VIEW.VIEW_TYPE";
		
		public static final String MONITOR_KPI_VIEW_TEMPLATE_VIEW_TEMPLATE_TYPE = "MONITOR_KPI_VIEW_TEMPLATE.VIEW_TEMPLATE_TYPE";
		
		public static final String EXPR_TEMPLATE_EXPR_TYPE = "EXPR_TEMPLATE.EXPR_TYPE";
		
		public static final String STS = "STS";
		
		public static final String EVALOBJRULE_PARAM_DATATYPE = "EVALOBJRULE_PARAM.DATATYPE";
		
		public static final String EVALOBJRULE_PARAM_INPUTMETHOD = "EVALOBJRULE_PARAM.INPUTMETHOD";
		
		public static final String EVAL_TASK_INST_EXECWAY = "EVAL_TASK_INST.EXECWAY";
		
		public static final String EVAL_TASK_INST_EXECSTATUS = "EVAL_TASK_INST.EXECSTATUS";
		
		public static final String EVAL_TASK_INST_EXECRESULTSTATUS = "EVAL_TASK_INST.EXECRESULTSTATUS";
		
		public static final String EVAL_TASK_DEF_GETVALUE_WAY = "EVAL_TASK_DEF.GETVALUE_WAY";
		/**销售管理：法人体公司（壳公司）**/
		public static final String SALES_CONTRACT_SHELL_COM= "SALES_CONTRACT.SHELL_COM";
		/**销售管理：产出单元（事业部）**/
		public static final String SALES_CONTRACT_BU= "SALES_CONTRACT.BU";
		/**销售管理：合同归属单位**/
		public static final String SALES_CONTRACT_BELONG_UNIT= "SALES_CONTRACT.BELONG_UNIT";
		/**销售管理：行业类别**/
		public static final String SALES_CONTRACT_INDUSTRY= "SALES_CONTRACT.INDUSTRY";
		/**销售管理：销售省份**/
		public static final String SALES_CONTRACT_SELLS_PROVINCE= "SALES_CONTRACT.SELLS_PROVINCE";
		/**销售管理：管控模式**/
		public static final String SALES_CONTRACT_CONTROL_MODE= "SALES_CONTRACT.CONTROL_MODE";
		/**销售管理：市场归属**/
		public static final String SALES_CONTRACT_MARKET_BELONG= "SALES_CONTRACT.MARKET_BELONG";
		/**销售管理：产业板块**/
		public static final String SALES_CONTRACT_INDUS_PILLAR= "SALES_CONTRACT.INDUS_PILLAR";
		/**销售管理：合作单位**/
		public static final String SALES_CONTRACT_COOP_UNIT= "SALES_CONTRACT.COOP_UNIT";
		/**销售管理：区域中心**/
		public static final String SALES_CONTRACT_AREA_CENTER= "SALES_CONTRACT.AREA_CENTER";
		/**销售管理：关联交易类型**/
		public static final String SALES_CONTRACT_ASSO_TRAN_TYPE= "SALES_CONTRACT.ASSO_TRAN_TYPE";
		/**销售管理：是或者否**/
		public static final String SALES_CONTRACT_YES_OR_NO= "SALES_CONTRACT.YES_OR_NO";
		/**销售管理：销售立项信息**/
		public static final String SALES_CONTRACT_PROJECT_INF= "SALES_CONTRACT.PROJECT_INF";
		
		/**销售合同操作日志：功能模块编码**/
		public static final String SALES_CONTRACT_LOG_MODEL_NO= "SALES_CONTRACT_LOG.MODEL_NO";
		
		/**合同执行情况信息：到货验收证明状态**/
		public static final String SALES_CONTRACT_EXECUTION_ARRIVAL_ACCEP_STS= "SALES_CONTRACT_EXECUTION.ARRIVAL_ACCEP_STS";
		
		/**回款核销管理：核销状态**/
		public static final String PAYBACK_VERIFICATION_VER_STS= "PAYBACK_VERIFICATION.VER_STS";
		
		/**通用配置：年限选择**/
		public static final String COMMON_YEAR = "COMMON.YEAR";
		/**通用配置：月限选择**/
		public static final String COMMON_WEEK = "COMMON.WEEK";

	}

	/**
	 * 显式使用的系统参数的ParamCode的定义
	 * 
	 * @author longtao
	 * 
	 */
	public class SysParamCode {
		/* 系统日志写文件 */
		public static final String SYS_LOG_FILE = "SYS_LOG.FILE";
		/* 系统日志写数据库 */
		public static final String SYS_LOG_DB = "SYS_LOG.DB";
		/* 登录日志写文件 */
		public static final String LOGIN_LOG_FILE = "LOGIN_LOG.FILE";
		/* 登录日志写数据库 */
		public static final String LOGIN_LOG_DB = "LOGIN_LOG.DB";
	}

	/**
	 * 日志类型
	 * 
	 * @author longtao
	 * 
	 */
	public static class LogCategory {
		/* 登录日志 */
		public static final String LOG_LOGIN = "1";
		/* 登出日志 */
		public static final String LOG_LOGOUT = "2";
	}

	public static class BooleanFlag {
		/**
		 * 启用标识：启用
		 */
		public static final String TRUE = "T";
		/**
		 * 启用标识：停用
		 */
		public static final String FALSE = "F";
	}

	public static final String YES = "Y";
	public static final String NO = "N";

	public class Status {
		public static final String STS_A = "A";
		public static final String STS_P = "P";
	}

	public class Staff {
		/**
		 * 员工状态
		 * 
		 * @author longtao
		 * 
		 */
		public class Status {
			/**
			 * 员工状态：在职
			 */
			public static final String STS_A = "A";
			/**
			 * 员工状态：停薪留职
			 */
			public static final String STS_R = "R";
			/**
			 * 员工状态：离职
			 */
			public static final String STS_P = "P";
		}
	}

	/**
	 * 系统用户相关常量定义
	 * 
	 * @author longtao
	 * 
	 */
	public class SysUser {
		/**
		 * 系统用户所有者类型
		 * 
		 * @author longtao
		 * 
		 */
		public class OwnerType {
			/**
			 * 部门
			 */
			public static final String DEPT = "D";
			/**
			 * 员工
			 */
			public static final String STAFF = "S";
		}

		/**
		 * 系统用户的用户类型
		 * 
		 * @author longtao
		 * 
		 */
		public class UserType {
			/**
			 * 用户类型：超级用户
			 */
			public static final String SUPER = "S";
			/**
			 * 用户类型：普通用户
			 */
			public static final String COMMON = "C";
		}

	}

	/**
	 * 授权操作中传递的JSON数据中的key定义
	 * 
	 * @author longtao
	 * 
	 */
	public class AuthJsonKey {
		/**
		 * 节点标识（角色标识、功能标识、功能操作标识），取值类型Long
		 */
		public static final String ID = "id";
		/**
		 * 节点名称（角色名称、功能名称、功能操作名称），取值类型String
		 */
		public static final String TEXT = "text";
		/**
		 * 是否可委派，取值类型String，取值为Y or N
		 */
		public static final String DESIGNATE = "designate";
		/**
		 * 选中标识，取值类型boolean，取值为true or false
		 */
		public static final String CHECKED = "checked";
		/**
		 * 功能下包含的功能操作信息，取值类型JSONArray
		 */
		public static final String FUNC_ITEM_INFOS = "funcItemInfos";

	}
	
}
