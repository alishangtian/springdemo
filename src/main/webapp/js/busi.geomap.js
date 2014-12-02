var map, baseDrawingManagerObject, geomDecoder;
// ***********地图相关开始**********
var initMap = {
	// 初始化地图
	initMap : function() {
		map = new BMap.Map("basemap");
		map.centerAndZoom("北京", 13);
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
	//初始化带点map
	initPointMap:function(point){
		map = new BMap.Map("basemap");
		map.centerAndZoom(point, 13);
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
	// 初始化画图工具
	initDrawTools : function() {
		baseDrawingManagerObject = new BMapLib.DrawingManager(map, {
			isOpen : false,
			enableDrawingTool : false,
			enableCalculate : true,
			polygonOptions : initMap.styleoption
		});
		baseDrawingManagerObject.setDrawingMode(BMAP_DRAWING_POLYGON);
		baseDrawingManagerObject.addEventListener("overlaycomplete",
				function(e) {
					if (e.label) {
						map.removeOverlay(e.label);
					}
					var polygon = e.overlay;
					var caculate = e.calculate;
					initMap.showPolygonInfo(polygon.getPath(), caculate);
				});
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
	// 显示区域信息
	showPolygonInfo : function(points, caculate) {
		var text = "";
		for ( var i = 0; i < points.length; i++) {
			var pointLng = points[i].lng;
			var pointLat = points[i].lat;
			text += pointLng + ":" + pointLat;
			if (i < points.length - 1) {
				text += ",";
			}
		}
		$("#geoinfo").val(text);
		$("#geoInfo").val(text);
		var area = (caculate / 666.667).toFixed(2) + "亩";
		if (!$.trim($("#acreage").val())) {
			$("#acreage").val(area);
			$("#area").val(area);
		}
		geomDecoder = new BMap.Geocoder();
		geomDecoder.getLocation(points[0], function(rs) {
			var locationName = "";
			var addComp = rs.addressComponents;
			locationName += addComp.province;
			if (addComp.city) {
				locationName += ", " + addComp.city;
			}
			if (addComp.district) {
				locationName += ", " + addComp.district;
			}
			if (addComp.street) {
				locationName += ", " + addComp.street;
			}
			if (addComp.streetNumber) {
				locationName += ", " + addComp.streetNumber;
			}
			$("#address").val(locationName);
			$("#gps").val(locationName);
		});
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
	 * @param bMap 地图实例
	 * @param points BMap.Point 数组
	 * @param style 样式
	 */
	newPolygon : function(bMap, points, style) {
		var polygon = new BMap.Polygon(points, style);
		bMap.addOverlay(polygon);
	}
};
// 搜索地图
function searchMap() {
	var value = $.trim($("#key").val());
	if (value) {
		var myGeo = new BMap.Geocoder();
		myGeo.getPoint(value, function(point) {
			if (point) {
				showLocation(point, myGeo);
			}
		});
	}
}
// 显示搜索区域
function showLocation(pt, geoc) {
	geoc.getLocation(pt, function(rs) {
		map.centerAndZoom(pt, 15);
	});
}
// 开始绘制
function startdraw() {
	baseDrawingManagerObject.open();
}
// 开始绘制
function enddraw() {
	baseDrawingManagerObject.close();
}
// 清除绘制
function cleardraw() {
	map.clearOverlays();
	$("#geoinfo").val("");
	$("#geoInfo").val("");
	$("#acreage").val("");
	$("#area").val("");
	$("#address").val("");
	$("#gps").val("");
	startdraw();
}
// ********地图相关结束************
