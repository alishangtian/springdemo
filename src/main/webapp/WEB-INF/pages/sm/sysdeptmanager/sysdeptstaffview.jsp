<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<dt:grid id="sysDeptStaffResultList" jsonRoot="resultList"
	dataSource="${staffList}" width="100%" height="325px" dataType="json"
	showPager="false" shrinkToFit="true">
	<dt:gridColumn name="entity.staffId" hidden="true" key="true" />
	<dt:gridColumn name="entity.staffCode" width="100" label="员工代码" />
	<dt:gridColumn name="entity.staffName" width="100" label="员工姓名" />
	<dt:gridColumn name="entity.mobileNbr" width="100" label="移动电话" />
	<dt:gridColumn name="entity.fixNbr" width="100" label="固定电话" />
	<dt:gridColumn name="entity.email" width="100" label="电子邮箱" />
	<dt:gridColumn name="idMap.sts" width="100" label="在岗状态" />
</dt:grid>