/*!
 * jQuery UI Tabs 1.10.1
 * http://jqueryui.com
 *
 * Copyright 2013 jQuery Foundation and other contributors
 * Released under the MIT license.
 * http://jquery.org/license
 *
 * http://api.jqueryui.com/tabs/
 *
 * Depends:
 *	jquery.ui.core.js
 *	jquery.ui.widget.js
 */
(function( $, undefined ) {

var tabId = 0,
	rhash = /#.*$/;

function getNextTabId() {
	return ++tabId;
}

function isLocal( anchor ) {
	//modify by xychun 2013-05-17
	//return anchor.hash.length > 1 &&
	//	decodeURIComponent( anchor.href.replace( rhash, "" ) ) ===
	//		decodeURIComponent( location.href.replace( rhash, "" ) );
	return anchor.hash && anchor.hash.charAt(0) ==='#';
}

$.widget( "dtui.dttabs", {
	version: "1.10.1",
	delay: 300,
	options: {
		active: null,
		collapsible: false,
		event: "click",
		heightStyle: "content",
		hide: null,
		show: null,
		
		maxTabs: -1,
		canClose: false,
		canDup: false,
		useIframe: false,
		alwaysReload: false,
		showPopupMenu: false,
		
		urlField: 'url',
		captionField: 'name',

		// callbacks
		activate: null,
		beforeActivate: null,
		beforeLoad: null,
		load: null
	},

	_create: function() {
		var that = this,
			options = this.options;

		this.running = false;

		this.element
			.addClass( "ui-tabs ui-widget ui-widget-content ui-corner-all" )
			.toggleClass( "ui-tabs-collapsible", options.collapsible )
			// Prevent users from focusing disabled tabs via click
			.delegate( ".ui-tabs-nav > li", "mousedown" + this.eventNamespace, function( event ) {
				if ( $( this ).is( ".ui-state-disabled" ) ) {
					event.preventDefault();
				}
			})
			// support: IE <9
			// Preventing the default action in mousedown doesn't prevent IE
			// from focusing the element, so if the anchor gets focused, blur.
			// We don't have to worry about focusing the previously focused
			// element since clicking on a non-focusable element should focus
			// the body anyway.
			.delegate( ".ui-tabs-anchor", "focus" + this.eventNamespace, function() {
				if ( $( this ).closest( "li" ).is( ".ui-state-disabled" ) ) {
					this.blur();
				}
			})
			.delegate( ".ui-tabs-anchor", "contextmenu" + this.eventNamespace, function(event) {
				var tab = $( this ).closest( "li" ),
					disabled = tab.is( ".ui-state-disabled" ),
					hasClose = tab.find("span.ui-icon-close").length>0;
				if ( disabled || !that.options.showPopupMenu) {
					return ;
				}
				that._menuElem.data('tab', tab);
				that._menuElem.show().position({
					my: "left top",
					at: "center center",
					of: this
				});
				that._updatePopupMenuState(tab);
				$( document ).one( "click", function() {
					if (that._menuElem && that._menuElem.length>0){
						that._menuElem.hide();
					}
				});
				return false;
			});
		
		// close icon: removing the tab on click
		this.element.delegate( "span.ui-icon-close", "click", function() {
			var tab = $(this).closest("li");
			that.close(tab);
		});

		this._initTabs();
		this._processTabs();
		options.active = this._initialActive();

		// Take disabling tabs via class attribute from HTML
		// into account and update option properly.
		if ( $.isArray( options.disabled ) ) {
			options.disabled = $.unique( options.disabled.concat(
				$.map( this.tabs.filter( ".ui-state-disabled" ), function( li ) {
					return that.tabs.index( li );
				})
			) ).sort();
		}

		// check for length avoids error when initializing empty list
		if ( this.options.active !== false && this.anchors.length ) {
			this.active = this._findActive( options.active );
		} else {
			this.active = $();
		}

		this._refresh();

		if ( this.active.length ) {
			that.load( options.active );
		}
		
		if (this.options.heightStyle === 'fill'){
			//console.log('parent');
			//console.log(this.element.parent());
			$(this.element.parent()).resize(function(){
				//console.log('parent resize');
				//console.log(that.element.parent());
				//console.log(that.element.parent().height());
				that._setupHeightStyle(that.options.heightStyle);

				/*
				var $panel = self._getActivePanel();
				if ($panel.length > 0){
					var ifrm = $panel.children('iframe');
					if (ifrm.length>0){
						var h = self._calcIframeHeight($panel);
						console.log('panelresize:'+h);
						$(ifrm).height(h);
					}
				}
				*/
			});
			
		}
		if (this.options.showPopupMenu) {
			this._createPopupMenu();
		}
	},
	
	_createPopupMenu: function() {
		var that = this;
		this._menuElem = $('<ul>').hide().appendTo(document.body);
		this._closeMenu = $('<li><a href="#">关闭当前页</a></li>').appendTo(this._menuElem);
		this._closeOtherMenu = $('<li><a href="#">关闭其它页</a></li>').appendTo(this._menuElem);
		this._closeAllMenu = $('<li><a href="#">关闭所有页</a></li>').appendTo(this._menuElem);
		this._refreshMenu = $('<li><a href="#">刷新</a></li>').appendTo(this._menuElem);
		this._menuElem.width(80);
		this._menuElem.menu();
		this._closeMenu.click(function(){
			if ($(this).prop('disabled'))
				return false;
			that.close(that._menuElem.data('tab'));
		});
		this._closeOtherMenu.click(function(){
			if ($(this).prop('disabled'))
				return false;
			that.closeOther(that._menuElem.data('tab'));
		});
		this._closeAllMenu.click(function(){
			if ($(this).prop('disabled'))
				return false;
			that.closeAll();
		});
		this._refreshMenu.click(function(event){
			if ($(this).prop('disabled'))
				return false;
			that._loadTab(that._menuElem.data('tab'), event, true);
		});
	},
	
	_updatePopupMenuState: function(tab){
		var hasClose = tab.find("span.ui-icon-close").length>0,
			tabCount = this.tabs.length;
		function disableMenu(menu, disabled){
			menu.prop('disabled', disabled);
			disabled?menu.addClass('ui-state-disabled'): menu.removeClass('ui-state-disabled');
		}
		disableMenu(this._closeMenu, !hasClose);
		disableMenu(this._closeOtherMenu, tabCount===1);
		disableMenu(this._closeAllMenu, (tabCount===1) && !hasClose);
		//this._refreshMenu = $('<li><a href="#">刷新</a></li>').appendTo(this._menuElem);
	},

	_initialActive: function() {
		var active = this.options.active,
			collapsible = this.options.collapsible,
			locationHash = location.hash.substring( 1 );

		if ( active === null ) {
			// check the fragment identifier in the URL
			if ( locationHash ) {
				this.tabs.each(function( i, tab ) {
					if ( $( tab ).attr( "aria-controls" ) === locationHash ) {
						active = i;
						return false;
					}
				});
			}

			// check for a tab marked active via a class
			if ( active === null ) {
				active = this.tabs.index( this.tabs.filter( ".ui-tabs-active" ) );
			}

			// no active tab, set to false
			if ( active === null || active === -1 ) {
				active = this.tabs.length ? 0 : false;
			}
		}

		// handle numbers: negative, out of range
		if ( active !== false ) {
			active = this.tabs.index( this.tabs.eq( active ) );
			if ( active === -1 ) {
				active = collapsible ? false : 0;
			}
		}

		// don't allow collapsible: false and active: false
		if ( !collapsible && active === false && this.anchors.length ) {
			active = 0;
		}

		return active;
	},

	_getCreateEventData: function() {
		return {
			tab: this.active,
			panel: !this.active.length ? $() : this._getPanelForTab( this.active )
		};
	},

	_tabKeydown: function( event ) {
		/*jshint maxcomplexity:15*/
		var focusedTab = $( this.document[0].activeElement ).closest( "li" ),
			selectedIndex = this.tabs.index( focusedTab ),
			goingForward = true;

		if ( this._handlePageNav( event ) ) {
			return;
		}

		switch ( event.keyCode ) {
			case $.ui.keyCode.RIGHT:
			case $.ui.keyCode.DOWN:
				selectedIndex++;
				break;
			case $.ui.keyCode.UP:
			case $.ui.keyCode.LEFT:
				goingForward = false;
				selectedIndex--;
				break;
			case $.ui.keyCode.END:
				selectedIndex = this.anchors.length - 1;
				break;
			case $.ui.keyCode.HOME:
				selectedIndex = 0;
				break;
			case $.ui.keyCode.SPACE:
				// Activate only, no collapsing
				event.preventDefault();
				clearTimeout( this.activating );
				this._activate( selectedIndex );
				return;
			case $.ui.keyCode.ENTER:
				// Toggle (cancel delayed activation, allow collapsing)
				event.preventDefault();
				clearTimeout( this.activating );
				// Determine if we should collapse or activate
				this._activate( selectedIndex === this.options.active ? false : selectedIndex );
				return;
			default:
				return;
		}

		// Focus the appropriate tab, based on which key was pressed
		event.preventDefault();
		clearTimeout( this.activating );
		selectedIndex = this._focusNextTab( selectedIndex, goingForward );

		// Navigating with control key will prevent automatic activation
		if ( !event.ctrlKey ) {
			// Update aria-selected immediately so that AT think the tab is already selected.
			// Otherwise AT may confuse the user by stating that they need to activate the tab,
			// but the tab will already be activated by the time the announcement finishes.
			focusedTab.attr( "aria-selected", "false" );
			this.tabs.eq( selectedIndex ).attr( "aria-selected", "true" );

			this.activating = this._delay(function() {
				this.option( "active", selectedIndex );
			}, this.delay );
		}
	},

	_panelKeydown: function( event ) {
		if ( this._handlePageNav( event ) ) {
			return;
		}

		// Ctrl+up moves focus to the current tab
		if ( event.ctrlKey && event.keyCode === $.ui.keyCode.UP ) {
			event.preventDefault();
			this.active.focus();
		}
	},

	// Alt+page up/down moves focus to the previous/next tab (and activates)
	_handlePageNav: function( event ) {
		if ( event.altKey && event.keyCode === $.ui.keyCode.PAGE_UP ) {
			this._activate( this._focusNextTab( this.options.active - 1, false ) );
			return true;
		}
		if ( event.altKey && event.keyCode === $.ui.keyCode.PAGE_DOWN ) {
			this._activate( this._focusNextTab( this.options.active + 1, true ) );
			return true;
		}
	},

	_findNextTab: function( index, goingForward ) {
		var lastTabIndex = this.tabs.length - 1;

		function constrain() {
			if ( index > lastTabIndex ) {
				index = 0;
			}
			if ( index < 0 ) {
				index = lastTabIndex;
			}
			return index;
		}

		while ( $.inArray( constrain(), this.options.disabled ) !== -1 ) {
			index = goingForward ? index + 1 : index - 1;
		}

		return index;
	},

	_focusNextTab: function( index, goingForward ) {
		index = this._findNextTab( index, goingForward );
		this.tabs.eq( index ).focus();
		return index;
	},

	_setOption: function( key, value ) {
		if ( key === "active" ) {
			// _activate() will handle invalid values and update this.options
			this._activate( value );
			return;
		}

		if ( key === "disabled" ) {
			// don't use the widget factory's disabled handling
			this._setupDisabled( value );
			return;
		}

		this._super( key, value);

		if ( key === "collapsible" ) {
			this.element.toggleClass( "ui-tabs-collapsible", value );
			// Setting collapsible: false while collapsed; open first panel
			if ( !value && this.options.active === false ) {
				this._activate( 0 );
			}
		}

		if ( key === "event" ) {
			this._setupEvents( value );
		}

		if ( key === "heightStyle" ) {
			this._setupHeightStyle( value );
		}
	},

	_tabId: function( tab ) {
		return tab.attr( "aria-controls" ) || "ui-tabs-" + getNextTabId();
	},

	_sanitizeSelector: function( hash ) {
		return hash ? hash.replace( /[!"$%&'()*+,.\/:;<=>?@\[\]\^`{|}~]/g, "\\$&" ) : "";
	},

	refresh: function() {
		var options = this.options,
			lis = this.tablist.children( ":has(a[href])" );

		// get disabled tabs from class attribute from HTML
		// this will get converted to a boolean if needed in _refresh()
		options.disabled = $.map( lis.filter( ".ui-state-disabled" ), function( tab ) {
			return lis.index( tab );
		});

		this._processTabs();
		// was collapsed or no tabs
		if ( options.active === false || !this.anchors.length ) {
			options.active = false;
			this.active = $();
		// was active, but active tab is gone
		} else if ( this.active.length && !$.contains( this.tablist[ 0 ], this.active[ 0 ] ) ) {
			// all remaining tabs are disabled
			if ( this.tabs.length === options.disabled.length ) {
				options.active = false;
				this.active = $();
			// activate previous tab
			} else {
				this._activate( this._findNextTab( Math.max( 0, options.active - 1 ), false ) );
			}
		// was active, active tab still exists
		} else {
			// make sure active index is correct
			options.active = this.tabs.index( this.active );
		}

		this._refresh();
	},

	_refresh: function() {
		this._setupDisabled( this.options.disabled );
		this._setupEvents( this.options.event );
		this._setupHeightStyle( this.options.heightStyle );

		this.tabs.not( this.active ).attr({
			"aria-selected": "false",
			tabIndex: -1
		});
		this.panels.not( this._getPanelForTab( this.active ) )
			.hide()
			.attr({
				"aria-expanded": "false",
				"aria-hidden": "true"
			});

		// Make sure one tab is in the tab order
		if ( !this.active.length ) {
			this.tabs.eq( 0 ).attr( "tabIndex", 0 );
		} else {
			this.active
				.addClass( "ui-tabs-active ui-state-active" )
				.attr({
					"aria-selected": "true",
					tabIndex: 0
				});
			this._getPanelForTab( this.active )
				.show()
				.attr({
					"aria-expanded": "true",
					"aria-hidden": "false"
				});
		}
	},

	_initTabs: function(){
		var tablist = this.tablist = this._getList();
		
		tablist.wrap('<div class="dtui-tabs-tablist-container"></div>');
		var tablistContainer = this.tablistContainer = this.tablist.parent();
		tablistContainer.wrap('<div class="ui-widget-header ui-corner-all ui-helper-clearfix"></div>');
		var $left = $('<a class="dtui-tab-header-left">Left</a>');
		var $right = $('<a class="dtui-tab-header-right">Right</a>');
		tablistContainer.before($left);
		tablistContainer.before($right);
		$left.dtbutton({
			icons: {
				primary: "ui-icon-arrowthick-1-w"
			},
			text: false,
			click: function(){
				var pos = tablist.position();
				var newLeft = pos.left + 20;
				if(newLeft > 0){
					newLeft = 0;
				}
				tablist.css({left: newLeft + 'px'});
			}
		});
		$right.dtbutton({
			icons: {
				primary: "ui-icon-arrowthick-1-e"
			},
			text: false,
			click: function(){
				var pos = tablist.position();
				var newLeft = pos.left - 20 ;
				var maxLeft = 0 - tablist.width() + tablistContainer.width();
				if(newLeft < maxLeft){
					newLeft = maxLeft;
				}
				tablist.css({left: newLeft+ 'px'});
			}
		});
	},
	_processTabs: function() {
		var that = this;

		this.tablist.addClass( "ui-tabs-nav ui-helper-reset" )
			.attr( "role", "tablist" );
		
		this.tabs = this.tablist.find( "> li:has(a[href])" )
			.addClass( "ui-state-default ui-corner-top" )
			.attr({
				role: "tab",
				tabIndex: -1
			});
		
		this.anchors = this.tabs.map(function() {
				return $( "a", this )[ 0 ];
			})
			.addClass( "ui-tabs-anchor" )
			.attr({
				role: "presentation",
				tabIndex: -1
			});

		this.panels = $();

		this.anchors.each(function( i, anchor ) {
			var selector, panel, panelId,
				anchorId = $( anchor ).uniqueId().attr( "id" ),
				tab = $( anchor ).closest( "li" ),
				originalAriaControls = tab.attr( "aria-controls" );

			// inline tab
			if ( isLocal( anchor ) ) {
				selector = anchor.hash;
				panel = that.element.find( that._sanitizeSelector( selector ) );
			// remote tab
			} else {
				panelId = that._tabId( tab );
				selector = "#" + panelId;
				panel = that.element.find( selector );
				if ( !panel.length ) {
					panel = that._createPanel( panelId );
					panel.insertAfter( that.panels[ i - 1 ] || that.tablistContainer.parent() );
					//----begin----
					//add by xychun 2013-4-25 iframe
					if (that.options.useIframe) {
						that._createIframe(panel);
					}
					//----end----
				}
				panel.attr( "aria-live", "polite" );
			}

			if ( panel.length) {
				that.panels = that.panels.add( panel );
			}
			if ( originalAriaControls ) {
				tab.data( "ui-tabs-aria-controls", originalAriaControls );
			}
			tab.attr({
				"aria-controls": selector.substring( 1 ),
				"aria-labelledby": anchorId
			});
			panel.attr( "aria-labelledby", anchorId );
		});

		this.panels
			.addClass( "ui-tabs-panel ui-widget-content ui-corner-bottom" )
			.attr( "role", "tabpanel" );
		
		//等结构渲染完成之后再计算宽度
		var widthSum = 0;
		$('li', this.tablist).each(function(i){
			var w =  $(this).outerWidth(true);
			widthSum += w ;
		});
		//似乎是由于字体导致的计算不准确，例如：中文设置为英文字体或英文设置为中文字体
		widthSum += 19;
		that.tablist.width(widthSum);
		if(widthSum > that.tablistContainer.width()){
			that.tablistContainer.parent().addClass('dtui-tabs-scroll');
		}
		else{
			that.tablist.css({left: 0});
			that.tablistContainer.parent().removeClass('dtui-tabs-scroll');
		}
	},

	// allow overriding how to find the list for rare usage scenarios (#7715)
	_getList: function() {
		return this.element.find( "ol,ul" ).eq( 0 );
	},

	_createPanel: function( id ) {
		return $( "<div>" )
			.attr( "id", id )
			.addClass( "ui-tabs-panel ui-widget-content ui-corner-bottom" )
			.data( "ui-tabs-destroy", true );
	},
	
	_calcIframeHeight: function($panel){
		//将12px转换成12
		function pxToNum(s){
			if ($.type(s) === 'string') {
				return s.substr(0, s.length-2);
			}
			return s;
		}

		//console.log($panel.css('height'));
		return pxToNum($panel.css('height')) - 3;
	},
	
    _createIframe: function(par) {
    	var self = this;
		var ifrm = $("<iframe frameborder='0' scrolling='auto' allowtransparency='true' width='100%' src=''> </iframe>");
		
		ifrm.addClass("dtui-tabs-iframe").appendTo(par);
		
		//将12px转换成12
		function pxToNum(s){
			if ($.type(s) === 'string') {
				return s.substr(0, s.length-2);
			}
			return s;
		}
		
		function getHostAndPort(url) {
			var index = url.indexOf('/', 8);
			return url.substr(0, index);
		}
		function isCrossDomain(iframe) {
			var mainUrlPart = getHostAndPort(window.location.href);
			var iframeUrlPart = getHostAndPort(iframe.src);
			//console.log(mainUrlPart);
			//console.log(iframeUrlPart);
			return mainUrlPart != iframeUrlPart;
		}
		
		ifrm.load(function(event) {
			/*
			var ifrmdoc = this.Document || this.contentDocument;
			
			//var par_pad_left =  pxToNum(par.css('padding-left')),
			//	par_pad_right =  pxToNum(par.css('padding-right'));
			//$(this).width(par.innerWidth() - par_pad_left - par_pad_right - 10);

			
			var par_pad_top =  pxToNum(par.css('padding-top')),
				par_pad_bottom =  pxToNum(par.css('padding-bottom')),
				par_height = par.innerHeight() - par_pad_top - par_pad_bottom - 5;
			//console.log(par.attr('id'));
			//console.log(ifrmdoc.body);
			//iframe的src不跨域
			//if (ifrmdoc.body){
			if (!isCrossDomain(this)){
				//console.log($(ifrmdoc.body).height()+','+par_height);
				$(this).height(Math.max($(ifrmdoc.body).height()+10, par_height));
			}
			else {
				//iframe的src跨域
				$(this).height(par_height);
			}
			*/
			/*
			var iframe_content = ifrm.contents().find('body');
			// Bind the resize event. When the iframe's size changes, update its height as
			// well as the corresponding info div.
			iframe_content.resize(function(){
				var elem = $(this);
				// Resize the IFrame.
				//console.log(elem.outerHeight( true ));
				ifrm.css({ height: elem.outerHeight( true ) });
				//$(this).outerWidth(par.innerWidth() - par_pad_left - par_pad_right);
			});
			iframe_content.resize();
			*/
			var h = self._calcIframeHeight(par);
			//console.log('load:'+h);
			ifrm.height(h);
			//$console.log(ifrm.contents().find("body"));
			ifrm.contents().find("body").click(function(){
				if (self._menuElem && self._menuElem.length>0){
					self._menuElem.hide();
				}
			}); 
		});
    },	
    
	_setupDisabled: function( disabled ) {
		if ( $.isArray( disabled ) ) {
			if ( !disabled.length ) {
				disabled = false;
			} else if ( disabled.length === this.anchors.length ) {
				disabled = true;
			}
		}

		// disable tabs
		for ( var i = 0, li; ( li = this.tabs[ i ] ); i++ ) {
			if ( disabled === true || $.inArray( i, disabled ) !== -1 ) {
				$( li )
					.addClass( "ui-state-disabled" )
					.attr( "aria-disabled", "true" );
			} else {
				$( li )
					.removeClass( "ui-state-disabled" )
					.removeAttr( "aria-disabled" );
			}
		}

		this.options.disabled = disabled;
	},
	
	_getActivePanel: function(){
		var tab = this._findActive(this.options.active);
		return this._getPanelForTab(tab);
	},

	_setupEvents: function( event ) {
		var events = {
			click: function( event ) {
				event.preventDefault();
			}
		};
		if ( event ) {
			$.each( event.split(" "), function( index, eventName ) {
				events[ eventName ] = "_eventHandler";
			});
		}

		this._off( this.anchors.add( this.tabs ).add( this.panels ) );
		this._on( this.anchors, events );
		this._on( this.tabs, { keydown: "_tabKeydown" } );
		this._on( this.panels, { keydown: "_panelKeydown" } );

		this._focusable( this.tabs );
		this._hoverable( this.tabs );
		/*
		//父容器改变大小时，重新设置高度
		if (this.options.heightStyle === "fill") {
			
			var self = this;
			self.panels.resize(function(){
				var ifrm = $(this).children('iframe');
				if (ifrm.length>0){
					var h = self._calcIframeHeight($(this));
					//console.log($(this));
					//console.log('panelresize:'+h);
					$(ifrm).height(h);
				}				
			});
		}
		*/
	},

	_setupHeightStyle: function( heightStyle ) {
		var maxHeight,
			parent = this.element.parent();

		if ( heightStyle === "fill" ) {
			maxHeight = parent.height();
			maxHeight -= this.element.outerHeight() - this.element.height();

			this.element.siblings( ":visible" ).each(function() {
				var elem = $( this ),
					position = elem.css( "position" );

				if ( position === "absolute" || position === "fixed" ) {
					return;
				}
				maxHeight -= elem.outerHeight( true );
			});

			this.element.children().not( this.panels ).each(function() {
				maxHeight -= $( this ).outerHeight( true );
			});

			var that = this;
			this.panels.each(function() {
				//console.log('setHeightStyle');
				//console.log(that.element);
				//console.log($(this));
				var newHeight = Math.max( 0, maxHeight -
						$( this ).innerHeight() + $( this ).height() );
				var oldHeight = $(this).height();
				//为防止死循环，只有高度改变时才重新设置高度
				if (Math.abs(oldHeight - newHeight) > 3){
					//console.log(oldHeight+','+newHeight);
					$( this ).height( newHeight );
					
					var ifrm = $(this).children('iframe');
					if (ifrm.length>0){
						var h = that._calcIframeHeight($(this));
						//console.log($(this));
						//console.log('panelresize:'+h);
						$(ifrm).height(h);
					}				
					
				}
			});
			//当使用iframe时，让iframe的高度等于panel的高度，隐藏panel的滚动条，使用iframe的滚动条
			if (!this.options.useIframe){
				this.panels.css( "overflow", "auto" );
			}
		} else if ( heightStyle === "auto" ) {
			maxHeight = 0;
			this.panels.each(function() {
				maxHeight = Math.max( maxHeight, $( this ).height( "" ).height() );
			}).height( maxHeight );
		}
	},

	_eventHandler: function( event ) {
		var options = this.options,
			active = this.active,
			anchor = $( event.currentTarget ),
			tab = anchor.closest( "li" ),
			clickedIsActive = tab[ 0 ] === active[ 0 ],
			collapsing = clickedIsActive && options.collapsible,
			toShow = collapsing ? $() : this._getPanelForTab( tab ),
			toHide = !active.length ? $() : this._getPanelForTab( active ),
			eventData = {
				oldTab: active,
				oldPanel: toHide,
				newTab: collapsing ? $() : tab,
				newPanel: toShow
			};

		event.preventDefault();

		if ( tab.hasClass( "ui-state-disabled" ) ||
				// tab is already loading
				tab.hasClass( "ui-tabs-loading" ) ||
				// can't switch durning an animation
				this.running ||
				// click on active header, but not collapsible
				( clickedIsActive && !options.collapsible ) ||
				// allow canceling activation
				( this._trigger( "beforeActivate", event, eventData ) === false ) ) {
			return;
		}

		options.active = collapsing ? false : this.tabs.index( tab );

		this.active = clickedIsActive ? $() : tab;
		if ( this.xhr ) {
			this.xhr.abort();
		}

		if ( !toHide.length && !toShow.length ) {
			$.error( "jQuery UI Tabs: Mismatching fragment identifier." );
		}

		if ( toShow.length ) {
			this.load( this.tabs.index( tab ), event );
		}
		this._toggle( event, eventData );
	},

	// handles show/hide for selecting tabs
	_toggle: function( event, eventData ) {
		var that = this,
			toShow = eventData.newPanel,
			toHide = eventData.oldPanel;

		this.running = true;

		function complete() {
			that.running = false;
/*
			//var tab = $(event.currentTarget).closest( "li" ),
			//	panel = that._getPanelForTab( tab );
			//console.log(that.options.useIframe +','+ that.options.heightStyle);
			if (that.options.useIframe && that.options.heightStyle ==='fill'){
				var ifrm = toShow.children('iframe');
				if (ifrm.length > 0 ){
					var h = that._calcIframeHeight(toShow);
					console.log('active:'+h);
					ifrm.height(h);
				}
			}
			*/
			that._trigger( "activate", event, eventData );
			
		}

		function show() {
			eventData.newTab.closest( "li" ).addClass( "ui-tabs-active ui-state-active" );

			if ( toShow.length && that.options.show ) {
				that._show( toShow, that.options.show, complete );
			} else {
				toShow.show();
				complete();
			}
		}

		// start out by hiding, then showing, then completing
		if ( toHide.length && this.options.hide ) {
			this._hide( toHide, this.options.hide, function() {
				eventData.oldTab.closest( "li" ).removeClass( "ui-tabs-active ui-state-active" );
				show();
			});
		} else {
			eventData.oldTab.closest( "li" ).removeClass( "ui-tabs-active ui-state-active" );
			toHide.hide();
			show();
		}

		toHide.attr({
			"aria-expanded": "false",
			"aria-hidden": "true"
		});
		eventData.oldTab.attr( "aria-selected", "false" );
		// If we're switching tabs, remove the old tab from the tab order.
		// If we're opening from collapsed state, remove the previous tab from the tab order.
		// If we're collapsing, then keep the collapsing tab in the tab order.
		if ( toShow.length && toHide.length ) {
			eventData.oldTab.attr( "tabIndex", -1 );
		} else if ( toShow.length ) {
			this.tabs.filter(function() {
				return $( this ).attr( "tabIndex" ) === 0;
			})
			.attr( "tabIndex", -1 );
		}

		toShow.attr({
			"aria-expanded": "true",
			"aria-hidden": "false"
		});
		eventData.newTab.attr({
			"aria-selected": "true",
			tabIndex: 0
		});
	},

	_activate: function( index ) {
		var anchor,
			active = this._findActive( index );
		
		// trying to activate the already active panel
		if ( active[ 0 ] === this.active[ 0 ] ) {
			return;
		}

		// trying to collapse, simulate a click on the current active header
		if ( !active.length ) {
			active = this.active;
		}

		anchor = active.find( ".ui-tabs-anchor" )[ 0 ];
		this._eventHandler({
			target: anchor,
			currentTarget: anchor,
			preventDefault: $.noop
		});
		

	},

	_findActive: function( index ) {
		return index === false ? $() : this.tabs.eq( index );
	},

	_getIndex: function( index ) {
		// meta-function to give users option to provide a href string instead of a numerical index.
		if ( typeof index === "string" ) {
			index = this.anchors.index( this.anchors.filter( "[href$='" + index + "']" ) );
		}

		return index;
	},

	_destroy: function() {
		if ( this.xhr ) {
			this.xhr.abort();
		}

		this.element.removeClass( "ui-tabs ui-widget ui-widget-content ui-corner-all ui-tabs-collapsible" );

		this.tablist
			.removeClass( "ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all" )
			.removeAttr( "role" );

		this.anchors
			.removeClass( "ui-tabs-anchor" )
			.removeAttr( "role" )
			.removeAttr( "tabIndex" )
			.removeUniqueId();

		this.tabs.add( this.panels ).each(function() {
			if ( $.data( this, "ui-tabs-destroy" ) ) {
				$( this ).remove();
			} else {
				$( this )
					.removeClass( "ui-state-default ui-state-active ui-state-disabled " +
						"ui-corner-top ui-corner-bottom ui-widget-content ui-tabs-active ui-tabs-panel" )
					.removeAttr( "tabIndex" )
					.removeAttr( "aria-live" )
					.removeAttr( "aria-busy" )
					.removeAttr( "aria-selected" )
					.removeAttr( "aria-labelledby" )
					.removeAttr( "aria-hidden" )
					.removeAttr( "aria-expanded" )
					.removeAttr( "role" );
			}
		});

		this.tabs.each(function() {
			var li = $( this ),
				prev = li.data( "ui-tabs-aria-controls" );
			if ( prev ) {
				li
					.attr( "aria-controls", prev )
					.removeData( "ui-tabs-aria-controls" );
			} else {
				li.removeAttr( "aria-controls" );
			}
		});

		this.panels.show();

		if ( this.options.heightStyle !== "content" ) {
			this.panels.css( "height", "" );
		}
	},

	enable: function( index ) {
		var disabled = this.options.disabled;
		if ( disabled === false ) {
			return;
		}

		if ( index === undefined ) {
			disabled = false;
		} else {
			index = this._getIndex( index );
			if ( $.isArray( disabled ) ) {
				disabled = $.map( disabled, function( num ) {
					return num !== index ? num : null;
				});
			} else {
				disabled = $.map( this.tabs, function( li, num ) {
					return num !== index ? num : null;
				});
			}
		}
		this._setupDisabled( disabled );
	},

	disable: function( index ) {
		var disabled = this.options.disabled;
		if ( disabled === true ) {
			return;
		}

		if ( index === undefined ) {
			disabled = true;
		} else {
			index = this._getIndex( index );
			if ( $.inArray( index, disabled ) !== -1 ) {
				return;
			}
			if ( $.isArray( disabled ) ) {
				disabled = $.merge( [ index ], disabled ).sort();
			} else {
				disabled = [ index ];
			}
		}
		this._setupDisabled( disabled );
	},
	
	_loadTab: function(tab, event, forceLoad){
		var that = this;
		function getTabIndex(tab){
			for (var i=0; i<that.tabs.length; i++){
				if (that.tabs[i] == tab[0]){
					return i;
				}
			}
			return -1;
		}
		
		var anchor = tab.find( ".ui-tabs-anchor" ),
			panel = this._getPanelForTab( tab ),
			eventData = {
				index: getTabIndex(tab),
				tab: tab,
				panel: panel
			};

		// not remote
		if ( isLocal( anchor[ 0 ] ) ) {
			return;
		}
		
		if (this.options.useIframe) {
			var iframe = panel.find('iframe');
			//即使this.options.useIframe=true,如果是静态创建的tab,panel中将不包含iframe
			if (iframe){
				if (!iframe.attr("src")){
					iframe.attr("src", anchor.attr( "href" ));
				}
				else {
					if (this.options.alwaysReload || forceLoad){
						iframe.attr("src", this._urlAppendTimeTag(anchor.attr( "href" )));
					}
				}
			}
		}
		else {
			this.xhr = $.ajax( this._ajaxSettings( anchor, event, eventData ) );
	
			// support: jQuery <1.8
			// jQuery <1.8 returns false if the request is canceled in beforeSend,
			// but as of 1.8, $.ajax() always returns a jqXHR object.
			if ( this.xhr && this.xhr.statusText !== "canceled" ) {
				tab.addClass( "ui-tabs-loading" );
				panel.attr( "aria-busy", "true" );
	
				this.xhr
					.success(function( response ) {
						// support: jQuery <1.8
						// http://bugs.jquery.com/ticket/11778
						setTimeout(function() {
							panel.html( response );
							that._trigger( "load", event, eventData );
						}, 1 );
					})
					.complete(function( jqXHR, status ) {
						// support: jQuery <1.8
						// http://bugs.jquery.com/ticket/11778
						setTimeout(function() {
							if ( status === "abort" ) {
								that.panels.stop( false, true );
							}
	
							tab.removeClass( "ui-tabs-loading" );
							panel.removeAttr( "aria-busy" );
	
							if ( jqXHR === that.xhr ) {
								delete that.xhr;
							}
						}, 1 );
					});
			}
		}
	},

	load: function( index, event ) {
		index = this._getIndex( index );
		var tab = this.tabs.eq( index );
		this._loadTab(tab, event);
	},
	
	url: function(index, url){
		if (index >=0 && index < this.anchors.length){
			this.anchors[index].href = url;
			$(this.anchors[index]).data('url', url);
		}
		return this;
	},
	
	/**
	 * 增加一个Tab页
	 * @param url	页面链接地址，将该链接指向的页面展示在新的tab中
	 * @param label	tab页头显示的文本
	 * @param isOpen	是否打开新增的tab页 true打开，false不打开
	 * @param notClose	是否显示关闭按钮，true不显示，false显示
	 * @param index	新增tab页的位置
	 * @returns {___anonymous719_29036}
	 */
	add: function( url, label, isOpen, notClose, index) {
		if (this.options.canDup!==true) {
			var anchorIndex = -1;
			this.anchors.each(function(i, a){
				//if ($(a).attr('href') === url) {
				//if (a.hash === url) {
				if ($(a).data('url') === url) {
					anchorIndex = i;
					return false;
				}
			});
			
			if (anchorIndex != -1) {
				//alert('您准备加入的页签已经存在！');
				this._activate(anchorIndex);
				return this;
			}
		}
		
		if (this.options.maxTabs != -1 && this.anchors.length >= this.options.maxTabs) {
			alert('您打开的页签已经达到系统允许的最大值！');
			return this;
		}

		var self = this,
			o = this.options,
			//id = "tabs-" + tabCounter,
			tabTemplate = "<li><a href='#{href}'>#{label}</a> </li>",
			$li = $( tabTemplate.replace( /#\{href\}/g, url ).replace( /#\{label\}/g, label ) );
			$li.find('a').data('url', url);

		if(!notClose){
			$li.append("<span class='ui-icon ui-icon-close' role='presentation'>Remove Tab</span>");
		}
		var activeIndex = 0;
		if ( index === undefined ) {
			$li.appendTo( this.tablist );	//this.tablist的长度不会发生变化
			if (isOpen) activeIndex = this.tabs.length;
		} else {
			$li.insertBefore( this.tabs[ index ] );
			if (isOpen) activeIndex = index;
		}
		
		this.refresh();
		
		if (isOpen) {
			//o.active = activeIndex;
			this._activate(activeIndex);
		}
		
		var i = index;
		if (index === undefined)
			i = this.tabs.length - 1;
			
		this._trigger( "add", null, this._ui( this.tabs[ i ], this.panels[ i ] )  );
		return this;
	},
	
	addSheet: function(sheetData, isOpen, notClose, index) {
		var url = sheetData[this.options.urlField],
			label = sheetData[this.options.captionField];
		
		if (this.options.canDup!==true) {
			var anchorIndex = -1;
			this.anchors.each(function(i, a){
				//if ($(a).attr('href') === url) {
				//if (a.hash === url) {
				if ($(a).data('url') === url) {
					anchorIndex = i;
					return false;
				}
			});
			
			if (anchorIndex != -1) {
				//alert('您准备加入的页签已经存在！');
				this._activate(anchorIndex);
				return this;
			}
		}
		
		if (this.options.maxTabs != -1 && this.anchors.length >= this.options.maxTabs) {
			alert('您打开的页签已经达到系统允许的最大值！');
			return this;
		}

		var self = this,
			o = this.options,
			//id = "tabs-" + tabCounter,
			tabTemplate = "<li><a href='#{href}'>#{label}</a> </li>",
			$li = $( tabTemplate.replace( /#\{href\}/g, url ).replace( /#\{label\}/g, label ) );
		
		$li.data('relaObject', sheetData);
		$li.find('a').data('url', url);

		if(!notClose){
			$li.append("<span class='ui-icon ui-icon-close' role='presentation'>Remove Tab</span>");
		}
		var activeIndex = 0;
		if ( index === undefined ) {
			$li.appendTo( this.tablist );	//this.tablist的长度不会发生变化
			if (isOpen) activeIndex = this.tabs.length;
		} else {
			$li.insertBefore( this.tabs[ index ] );
			if (isOpen) activeIndex = index;
		}
		
		this.refresh();
		
		if (isOpen) {
			//o.active = activeIndex;
			this._activate(activeIndex);
		}
		
		var i = index;
		if (index === undefined)
			i = this.tabs.length - 1;
			
		this._trigger( "add", null, this._ui( this.tabs[ i ], this.panels[ i ] )  );
		return this;		
	},
	
	getData: function(index){
		if (index>=0 && index < this.tabs.length){
			var o = $(this.tabs[index]).data('relaObject');
			//不能直接返回undefined，undefined经过widget包装后，会返回当前对象
			if (o !== undefined)
				return o;
		}
		return null;
	},
	_getNextVisibleTabPos: function(index) {
		for (var i= index+1; i<this.tabs.length; i++){
			var tab = this.tabs.eq(i);
			if (tab.css('display') != 'none'){
				return i;
			}
		}
		return null;
	},
	_getPrevVisibleTabPos: function(index) {
		//$console.log(this.tabs);
		for (var i= index-1; i>=0; i--){
			var tab = this.tabs.eq(i);

			if (tab.css('display') != 'none'){
				return i;
			}
		}
		return null;
	},
	hide: function ( index ) {
		if (index < 0 || index >=this.tabs.length || this.tabs.length<=1){
			return;
		}
		var tab = this.tabs.eq(index),
			panel = this._getPanelForTab( tab );
		if (index = this.options.active) {
			var nextActive = null;
			if (index == this.tabs.length -1){
				nextActive = this._getPrevVisibleTabPos(index);
				if (nextActive == null)
					nextActive = this._getNextVisibleTabPos(index);
			}
			else {
				nextActive = this._getNextVisibleTabPos(index);
				if (nextActive == null)
					nextActive = this._getPrevVisibleTabPos(index);
			}
			this.options.active = nextActive;
			if (nextActive != null)
				this._activate(nextActive);
		}
		panel.hide();
		tab.hide();
		this.refresh();
	},
	
	show: function ( index ) {
		if (index < 0 || index >=this.tabs.length || this.tabs.length<=1){
			return;
		}
		var tab = this.tabs.eq(index),
			panel = this._getPanelForTab( tab );
		panel.show();
		tab.show();
		this.refresh();
	},

	_ui: function( tab, panel ) {
		return {
			tab: tab,
			panel: panel,
			index: this.anchors.index( tab )
		};
	},

	remove: function( index) {
		this._removeTab(index);
		this.refresh();
		//return this;
	},	
	
	_removeTab: function(index){
		var tab = this.tabs.eq(index);
		this._removeByTab(tab);
	},
	
	_removeByTab: function(tab){
		if (tab.length == 0 || tab.find('span.ui-icon-close').length == 0) 
			return false;
		tab.find("a").removeData('url');
		var panelId = tab.remove().attr( "aria-controls" );
		$( "#" + panelId ).remove();
		return true;
	},
	
	_canClose: function(index){
		return this.tabs.eq(index).find('span.ui-icon-close').length>0;
	},
	
	close: function(tab){
		if (!tab){
			tab = this.active;
		}
		this._removeByTab(tab);		
		this.refresh();
	},
	closeOther: function(activeTab){
		if (!activeTab){
			activeTab = this.active;
		}
		for (var i= this.tabs.length - 1; i>=0; i--){
			if (this.tabs[i] == activeTab[0]) continue;
			this._removeTab(i);
		}
		this.refresh();
		
	},
	closeAll: function(){
		for (var i= this.tabs.length - 1; i>=0; i--){
			this._removeTab(i);
		}
		this.refresh();
	},
	
	/**
	 * 在url后面增加时间戳
	 * @param url 
	 * @returns {String}
	 */
	_urlAppendTimeTag: function(url){
		if (url){			
			var s = url.indexOf("?")>=0?"&":"?";
			return url + s+"_timetag="+ (new Date()).getTime();
		}
		else 
			return "";
	},

	_ajaxSettings: function( anchor, event, eventData ) {
		var that = this,
			url = '';
		if (this.options.alwaysReload){
			url = this._urlAppendTimeTag(anchor.attr( "href" ));
		}
		else {
			url = anchor.attr( "href" );
		}
		return {
			//url: anchor.attr( "href" ),
			url: url,
			beforeSend: function( jqXHR, settings ) {
				return that._trigger( "beforeLoad", event,
					$.extend( { jqXHR : jqXHR, ajaxSettings: settings }, eventData ) );
			}
		};
	},

	_getPanelForTab: function( tab ) {
		var id = $( tab ).attr( "aria-controls" );
		return this.element.find( this._sanitizeSelector( "#" + id ) );
	}
});

})( jQuery );
