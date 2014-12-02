<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<button class="left_right left_icon"></button>
<div id="leftcontent" class="left_nav">
	<div class="F_switch">
		<span id="menuTitle">功能菜单</span>
		<button id="menuShortCutBtn" class="shortcut" title="快捷方式"></button>
		<button id="menuListBtn" class="list" title="功能菜单"></button>
		<div class="line clear"></div>
	</div>
	<div id="menuTree">
	</div>
	<div id="favourMenu" style="display: none;">
	</div>
</div>
<div class="min_left_nav" style="display: none;">
	<button class="left_right right_icon"></button>
</div>
<script src="pages/main/js/func.ui.leftmenu.js"></script>
<script src="pages/main/js/func.left.js"></script>