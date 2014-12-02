var map;
$(function() {
	$(".map-left").css("height", "200px");
	$("#basemap").css("height", "200px").css("margin-top", "8px");
	showALLBase();
	$(".operationClass").mouseover(function() {
	});
});
function expandAsyn() {
	var nodes = dttreen.dttree('getNodes');
	if (nodes != null && nodes.length > 0) {
		dttreen.expandNode(nodes[0], true, false, true);
	}
}
function clickOper() {
	var node = arguments[2];
	if (node) {
		var type = node.type;
		var nodeId = node.nodeTreeId;
		if (type == 1) {
			var houseName = node.nodeTreeName; // 给caption赋值
			baseInfoResultList.dtpaginggrid('setCaption', '查询结果：' + houseName);
			showOneBase(nodeId);
			showHouseBase(nodeId);
		} else if (type == 2) {
			var houseName = node.nodeTreeName; // 给caption赋值
			baseInfoResultList.dtpaginggrid('setCaption', '查询结果：' + houseName);
			showOneGreenHouse(nodeId);
			showHouseSensorInfo(nodeId);
			showHouse(nodeId);
		}
	}
}
// 点击基地查询所有基地下面的温室 温室下面的设备分别为 传感器 控制器的设备数量
function showHouseBase(nodeId) {
	baseInfoResultList
			.dtpaginggrid(
					"setGridParam",
					{
						url : 'busi/greedHoseClickAction!queryEquipHouseBaseList.action?baseId='
								+ nodeId
					});
	baseInfoResultList.dtpaginggrid("option", "page", 1);
	baseInfoResultList.dtpaginggrid("reload");
}
function showHouse(houseId) {
	baseInfoResultList
			.dtpaginggrid(
					"setGridParam",
					{
						url : 'busi/greedHoseClickAction!queryEquipHouseBaseList.action?houseId='
								+ houseId
					});
	baseInfoResultList.dtpaginggrid("option", "page", 1);
	baseInfoResultList.dtpaginggrid("reload");
}
function showHouseSensorInfo(houseId) {
	if (houseId) {
		$.getJSON("busi/greedHoseClickAction!selectEquipData.action?equipId="
				+ houseId, function(data) {
			$("#eqinfo").empty();
			var checkItem = "";
			$.each(data, function(i, item) {
				checkItem += "<li onClick='DataInfo(this)'>";
				/* checkItem += "<span class='alerm-icon'></span>"; */
				checkItem += "<em>" + item.value + item.units + "</em>";
				if (item.name == "空气湿度") {
					checkItem += "<span class='one-equip'></span>";
				} else if (item.name == "空气温度") {
					checkItem += "<span class='one-equip two-equip'></span>";
				} else if (item.name == "相对水率") {
					checkItem += "<span class='one-equip three-equip'></span>";
				} else if (item.name == "土壤湿度") {
					checkItem += "<span class='one-equip four-equip'></span>";
				} else if (item.name == "土壤温度") {
					checkItem += "<span class='one-equip five-equip'></span>";
				} else if (item.name == "co2含量") {
					checkItem += "<span class='one-equip six-equip'></span>";
				} else if (item.name == "土壤PH值") {
					checkItem += "<span class='one-equip two-equip'></span>";
				} else if (item.name == "光照度") {
					checkItem += "<span class='one-equip six-equip'></span>";
				} else if (item.name == "土壤电导率") {
					checkItem += "<span class='one-equip two-equip'></span>";
				}
				checkItem += "<i>" + item.name + "</i>";
				checkItem += "</li>";
			});
			$("#eqinfo").html(checkItem + "");
		});
	}
}
function showOneBase(baseId) {
	$.getJSON("busi/equipInfoAction!getOneBaseInfo.action?baseId=" + baseId,
			function(data) {
				map.clearOverlays();
				$.each(data, function(i, item) {
					if (i == 0) {
						var geoInfo = item.geoInfo;
						var points = initMap.parsePolygonPoints(geoInfo);
						var polygon = new BMap.Polygon(points,
								initMap.styleoption);
						map.addOverlay(polygon);
						map.centerAndZoom(points[0], 10);
					}
				});
			});
}
function showOneGreenHouse(houseId) {
	$.getJSON("busi/equipInfoAction!getOneGreenHouseInfo.action?houseId="
			+ houseId, function(data) {
		map.clearOverlays();
		$.each(data, function(i, item) {
			if (i == 0) {
				var geoInfo = item.geoInfo;
				var points = initMap.parsePolygonPoints(geoInfo);
				var polygon = new BMap.Polygon(points, initMap.styleoption);
				map.addOverlay(polygon);
				map.centerAndZoom(points[0], 12);
			}
		});
	});
}

function vt() {
	$("#operationInfoDiv").vTicker('init', {
		speed : 1500,
		showItems : 4,
		pause : 0,
		padding : 1
	});
	$("#baojing").vTicker('init', {
		speed : 1500,
		pause : 0,
		showItems : 4,
		padding : 1
	});
}
function showALLBase() {
	$.getJSON("busi/equipInfoAction!getAllBaseInfo.action", function(data) {
		if (data && data.length > 0) {
			$.each(data, function(i, item) {
				var name = item.name;
				var geoInfo = item.geoInfo;
				var points = initMap.parsePolygonPoints(geoInfo);
				if (points && points.length > 0) {
					var point = points[0];
					if (i == 0) {
						initMap.initMap("basemap", point, 8);
					}
					var marker = new BMap.Marker(point);
					map.addOverlay(marker);
					var label = new BMap.Label(name, {
						offset : new BMap.Size(20, -10)
					});
					marker.setLabel(label);
				}
			});
		} else {
			initMap.initMap("basemap", "北京", 8);
		}
	});
}
var initMap = {
	// 初始化地图
	initMap : function(divid, point, zoom) {
		map = new BMap.Map(divid);
		map.centerAndZoom(point, zoom);
		map.enableScrollWheelZoom(true);
		var top_left_control = new BMap.ScaleControl({
			anchor : BMAP_ANCHOR_BOTTOM_LEFT
		});
		var top_left_navigation = new BMap.NavigationControl();
		var top_right_maptype = new BMap.MapTypeControl({
			anchor : BMAP_ANCHOR_TOP_RIGHT
		});
		initMap.addControl(top_left_control, top_left_navigation,
				top_right_maptype);
	},
	// 添加空间
	addControl : function(top_left_control, top_left_navigation,
			top_right_maptype) {
		map.addControl(top_left_control);
		map.addControl(top_left_navigation);
		map.addControl(top_right_maptype);
	},
	// 画图样式
	styleoption : {
		strokeColor : "red", // 边线颜色。
		fillColor : "red", // 填充颜色。当参数为空时，圆形将没有填充效果。
		strokeWeight : 3, // 边线的宽度，以像素为单位。
		strokeOpacity : 0.8, // 边线透明度，取值范围0 - 1。
		fillOpacity : 0.6, // 填充的透明度，取值范围0 - 1。
		strokeStyle : 'solid' // 边线的样式，solid或dashed。
	},
	/**
	 * 解析坐标点
	 * 
	 * @param geoPoints
	 *            实例：116.293203:39.95959,116.287166:39.944324,116.311313:39.942333
	 * @returns {Array}
	 */
	parsePolygonPoints : function(geoPoints) {
		var polygonPoints = [];
		if (geoPoints) {
			var points = geoPoints.split(",");
			if (points && points.length > 0) {
				for ( var i = 0; i < points.length; i++) {
					var point = points[i];
					var pointLngLat = point.split(":");
					if (pointLngLat && pointLngLat.length == 2) {
						var lng = pointLngLat[0];
						var lat = pointLngLat[1];
						var po = new BMap.Point(lng, lat);
						if (po) {
							polygonPoints[i] = po;
						}
					}
				}
			}
		}
		return polygonPoints;
	},
	/**
	 * 生成新的polygon
	 * 
	 * @param bMap
	 *            地图实例
	 * @param points
	 *            BMap.Point 数组
	 * @param style
	 *            样式
	 */
	newPolygon : function(bMap, points, style) {
		var polygon = new BMap.Polygon(points, style);
		bMap.addOverlay(polygon);
	}
};
// ********地图相关结束************

// *********消息栏相关开始***********

function clickOperation(o) {
	var pid = $(o).parent().val();
	window
			.open(
					'busi/pickWorksInfoAction!viewPickWorksInfo.action?pickWorksInfoId='
							+ pid,
					'newwindow',
					'height=300px,width=800px,top=200px,left=250px,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,toolbar=no');
}
function clickmsgAlarm(o) {
	var pid = $(o).parent().val();
	window
			.open(
					'busi/msgAlarmAction!viewMsgAlarm.action?msgAlarmID=' + pid,
					'newwindow',
					'height=350px,width=800px,top=200px,left=250px,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,toolbar=no');
}
function clickPurchaseIntention(o) {
	window
			.open(
					'busi/purchaseIntentionAction!viewPurchaseIntention.action',
					'newwindow',
					'height=350px,width=800px,top=200px,left=250px,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,toolbar=no');
}
function showBulletin(obj) {
	var $this = $(obj);
	var value = $this.val();
	window
			.open(
					'busi/bulletinAction!viewBulletin.action?bulletinId='
							+ value,
					'newwindow',
					'height=350px,width=800px,top=200px,left=250px,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,toolbar=no');
}
// *********消息栏相关结束***********
