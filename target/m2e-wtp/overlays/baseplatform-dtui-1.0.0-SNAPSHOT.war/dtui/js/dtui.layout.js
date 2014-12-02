/*
 * 布局控件
 */
(function($, undefined) {
	var slice = Array.prototype.slice;
		//有header的时候的选项，不能修改
		defaultBarOptions = {
			spacing_closed:			24,							//关闭后打开按钮所占区域的间隔，不建议修改
			togglerLength_closed:	21,							//面板关闭后打开按钮的位置，不建议修改
			togglerLength_open:		0,							//面板打开时按钮的位置，不建议修改
			resizerClass: 'ui-layout-resizer-bar',						//调整大小的按钮的class
			togglerClass : 'ui-layout-toggler-bar'						//打开关闭按钮的class
		};
	$.widget('dtui.dtlayout',{
		version : '1.0.1',
		_layout : null,
		
		/*collapsable：每一个pane都可以收合，而且还可以加上jQuery特效 
		resizable：每一个pane都可以缩放，而且还可以限制缩放的大小 
		hotkeys：每一个pane都可以用键盘的方向键来操作，甚至自订快速键(这个最酷！) */
		options: {
			//default options for 'all panes' - will be overridden by 'per-pane settings'
			togglerTip_open : "关闭面板",	//打开按钮文字提示
			togglerTip_closed : "打开面板",	//关闭按钮文字提示
			resizerTip : "调整面板大小",		//调整按钮文字提示
			hideTogglerOnSlide:		true,
			//------------------------------------------//
			applyDemoStyles: 		false,
			closable:				true,
			resizable:				true,
			slidable:				true,
			initClosed:				false,
			initHidden: 			false,
			//ELEMENT SIZE & SPACING
			minSize:				0,
			maxSize:				0,
			spacing_open:			6,
			spacing_closed:			6,
			togglerLength_open:		50,
			togglerLength_closed: 	50,
			togglerAlign_open:		"center",
			togglerAlign_closed:	"center",
			togglerContent_open:	"",
			togglerContent_closed:	"",
			//SLIDING OPTIONS
			sliderCursor:			"pointer",
			slideTrigger_open:		"click"	,
			slideTrigger_close:		"mouseleave",
			//多重组合布局
			children:               null,//详细例子见Nested.html
			//callback
			onshow_start:			null,		
			onshow_end:				null,		
			onhide_start:			null,		
			onhide_end:				null,		
			onopen_start:			null,		
			onopen_end:				null,		
			onclose_start:			null,		
			onclose_end:			null,		
			onresize_start:			null,		
			onresize_end:			null,		
			onsizecontent_start:	null,		
			onsizecontent_end:		null,		
			onswap_start:			null,		
			onswap_end:				null,		
			ondrag_start:			null,		
			ondrag_end:				null,		
			//------------------------------------------//
			//North面板配置选项
			north:{
				paneSelector:".ui-layout-north",
				size: 'auto',		//尺寸 数字或者是auto
				minSize : 50,		//最小尺寸
				maxSize : 200,		//最大尺寸	
				title : null,		//如果title不为空，在面板添加一个header
				pinBtn : false,		//是否在header添加钉子按钮
				toggleBtn : false	//是否在header添加切换按钮
			},
			//South面板配置选项
			south: {
				paneSelector:".ui-layout-south",
				size: 'auto',
				minSize : 50,
				maxSize : 200,
				title : null,
				pinBtn : false,
				toggleBtn : false
			},
			//West面板配置选项
			west: {
				paneSelector:".ui-layout-west",
				size: 200,
				minSize : 100,
				maxSize : 300,
				title : null,
				pinBtn : false,
				toggleBtn : false
			},
			//East面板配置选项
			east: {
				paneSelector:".ui-layout-east",
				size: 200,
				minSize : 100,
				maxSize : 300,
				title : null,
				pinBtn : false,
				toggleBtn : false
			},
			//center
			center: {
				paneSelector:".ui-layout-center",	
				minWidth:0,	
				minHeight:0
			}
		},
		_create : function() {
			var o = this.options;
			/*this._setOptions('north');
			this._setOptions('south');
			this._setOptions('west');
			this._setOptions('east');*/
			var layout = this.element.layout(o);
			this._setLayout(layout);
			/*this._initPanel('north');
			this._initPanel('south');
			this._initPanel('west');
			this._initPanel('east');*/
		},
		_setOptions : function(pos){
			var $panel = $('> .ui-layout-' + pos, this.element);
			if($panel.length < 1){
				return;
			}
			var options = this.options,
				$header = $('>.header', $panel),
				hasTitle = $header.length > 0,
				o = options[pos];
				
			this['$' + pos] = $panel;
			this['has_' + pos + '_title'] = hasTitle;
			
			if(o.title || o.pinBtn || o.toggleBtn){
				if(!hasTitle){
					$header = $('<div class="header"></div>').prependTo( $panel );
				}
			}
			if(o.pinBtn || o.toggleBtn){
				$.extend(options[pos], defaultBarOptions);
			}
			if(o.toggleBtn){
				if(pos == 'west' || pos == 'east'){
					o.togglerAlign_closed = 'top';
				}else{
					o.togglerAlign_closed = 'right';
				}
			}
			if(o.title){
				$header.html(o.title);
			}
		},
		_initPanel: function(pos){
			var $panel = $('> .ui-layout-' + pos, this.element);
			if($panel.length < 1){
				return;
			}
			var options = this.options,
				layout = this._getLayout(),
				pinSelector = '.ui-layout-' + pos + ' .pin-button',
				closeId = pos + '-closer',
				panelSelector = '> .ui-layout-' + pos,
				closeSelector = '#' + pos + '-closer',
				o = options[pos];
			
			if(o.pinBtn){
				$("<span></span>").addClass("pin-button").prependTo( $panel );
				layout.addPinBtn($(pinSelector, this.element), pos);
			}
			
			if(o.toggleBtn){
				$("<span></span>").attr("id", closeId).prependTo( $(panelSelector, this.element) );
				layout.addCloseBtn(closeSelector, pos);
			}
		},
		_destroy : function() {
			this._callMethod('destroy');
		},
		_getLayout : function() {
			return this._layout;
		},
		_setLayout : function(layout) {
			this._layout = layout;
		},
		_callMethod : function(action) {
			var input = slice.call(arguments, 1), layout = this
					._getLayout();
	
			layout[action].apply(layout, input);
		},
		resizeContent: function(pane) {
			this._callMethod('resizeContent', pane);
		},
		resizeAll: function() {
			this._callMethod('resizeAll');
		},
		//打开指定面板
		open : function(pane) {
			this._callMethod('open', pane);
		},
		//关闭指定面板
		close : function(pane) {
			this._callMethod('close', pane);
		},
		//显示指定面板
		show : function(pane,noAnimation) {
			this._callMethod('show', pane,noAnimation);
		},
		//隐藏指定面板
		hide : function(pane,noAnimation) {
			this._callMethod('hide', pane,noAnimation);
		},
		//切换指定面板
		toggle : function(pane){
			$console.log('toggle');
			this._callMethod('toggle', pane);
		},
		//-----------------------------------------------------------
		
		toggleslide:function(){
			this._callMethod('toggle', pane, true);
		},
		openslide:function(){
			this._callMethod('open', pane, true);
		},
		//设置指定面板的尺寸
		sizePane : function(pane, size_in_pixels){
			this._callMethod('sizePane', pane, size_in_pixels);
		},
		
		//menuBar 添加方式
		addToggleBtn:function(sel,pane){
			this._layout.addToggleBtn( sel,pane );
		},
		addOpenBtn:function(sel,pane){
			this._layout.addOpenBtn( sel,pane );
		},
		addCloseBtn:function(sel,pane){
			this._layout.addCloseBtn( sel,pane );
		},
		addPinBtn:function(sel,pane){
			this._layout.addPinBtn( sel,pane );
		},
		getWidget:function(){
			return this._layout;
		},
		
		//-----------------------------------------------------------
		//返回指定面板
		get_pane : function(pane) {
			if ($.inArray(pane, [ 'west', 'east', 'center', 'south',
					'north' ]) > -1) {
				var layout = this._getLayout();
				return layout[pane];
			}
			return undefined;
		}
	});
})(jQuery);
