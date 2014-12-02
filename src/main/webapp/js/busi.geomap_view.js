var map_view;
var initMap_view = {
	// 初始化地图
	initMap : function(point) {
		map_view = new BMap.Map("basemap");
		map_view.centerAndZoom(point, 13);
		map_view.enableScrollWheelZoom(true);
		var top_left_control = new BMap.ScaleControl({
			anchor : BMAP_ANCHOR_BOTTOM_LEFT
		});
		var top_left_navigation = new BMap.NavigationControl();
		var top_right_maptype = new BMap.MapTypeControl({
			anchor : BMAP_ANCHOR_TOP_RIGHT
		});
		initMap_view.addControl(top_left_control, top_left_navigation,
				top_right_maptype);
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
	// 添加空间
	addControl : function(top_left_control, top_left_navigation,
			top_right_maptype) {
		map_view.addControl(top_left_control);
		map_view.addControl(top_left_navigation);
		map_view.addControl(top_right_maptype);
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
