<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<dt:tabs id="opertab" cache="true">
	<dt:tabsheet label="代打电话" id="tab1">
		<table border="0">
  			<tbody><tr>
  				<th>转接号码：</th>
  				<td><input onblur="checkNums()" name="callTelnum" id="callTelnum"></td>
  				<th>姓名:</th>
  				<td>
  					<dt:comboBox id="name" url="demo/demoActiom!doGetNameList.action"></dt:comboBox>
  				</td>
  				<th>昵称：</th>
  				<td>
  					<dt:comboBox id="nick" url="demo/demoActiom!doGetNickList.action"></dt:comboBox>
  				</td>
  				<td><dt:button id="call" label="代拨电话" type="ok"></dt:button>
  				</td>
  			</tr>
  		</tbody></table>
  		<dt:grid id="his" jsonRoot="result" height="100%" url="demo/demoActiom!doHisList.action"
			dataType="json" showPage="true" caption="代打电话历史记录"
			altRows="true" sortable="true">
			<dt:gridColumn name="phone" width="100" sortable="true" label="来电号码"></dt:gridColumn>
			<dt:gridColumn name="name" width="100" sortable="true" label="姓名"></dt:gridColumn>
			<dt:gridColumn name="linkman" sortable="true" label="转接联系人姓名"></dt:gridColumn>
			<dt:gridColumn name="callphone" width="100" sortable="true" label="转接号码"></dt:gridColumn>
			<dt:gridColumn name="operdate" sortable="true" label="操作时间"></dt:gridColumn>
			<dt:gridColumn name="operstaff" width="100" sortable="true" label="操作员工"></dt:gridColumn>
		</dt:grid>
	</dt:tabsheet>
	<dt:tabsheet label="代发短信" id="tab2" url="demo/demoActiom!initDuanxin.action">
	</dt:tabsheet>
	<dt:tabsheet label="代设定时提醒" id="tab3">
	</dt:tabsheet>
	<dt:tabsheet label="电话簿代维护" id="tab4">
	</dt:tabsheet>
</dt:tabs>