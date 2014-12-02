/**
 * TAB组件
 */
(function( $ ) {
$.widget( "ui.funcTabs", {
	options: {
		id: "",
		maxTabs: -1,
		selected: 0,
		tabs: [
		       {
		    	   id: "",
		    	   label: "",
		    	   canClose: false,
		    	   useIframe: false,
		    	   html: "",
		    	   url: "",
		    	   ajaxOptions: null,
		    	   load: null,
		    	   beforeover: null,
		    	   data: null
		       }
		       ],
		buttons: [{
			title : "删除快捷方式",
			text : false,
			classname : "",
			click : function() {
				removeUserShortcut(dt$.tabs(this).option('selected'));
			}
		}],
		tabClass: "content ui-corner-all",
		tabHeadClass: "tab ui-corner-top",
		tabContentClass: "tabContent"
   	},
	_create: function() {
		this.element.addClass(this.options.tabClass);
		this._createElements();
		this._createTabHead();
		this.select(this.options.selected);
	},
	_createElements: function(){
		this._e = {
				head: $("<div class='" + this.options.tabHeadClass + "'>"),
				tabUl: $("<ul width='100%'></ul>"),
				tabLi: {},
				buttons: [],
				content: {}
		};
	},
	_createTabHead: function(){
		var self = this;
		this._e.head.append(this._e.tabUl).appendTo(this.element);
		$.each(this.options.tabs, function(i, tab){
			self.addTab(tab, true);
		});
		this._createTabButton();
	},
	addTab: function(tab, noSelected){
		var findTabId = this._getTabIdByTabData(tab);
		if(findTabId != null){
			this._selectByTabId(findTabId);
			return;
		}
		var tabLength = this._getTabLength();
		if(this.options.maxTabs != -1 && tabLength >= this.options.maxTabs){
			dt$.alert('您打开的页签已经达到系统允许的最大值！');
			return this;
		};
		var id = this._getId(), self = this;
		this._e.tabLi[id] = $("<li></li>");
		var title = $("<div class='middle'>" + tab.label + "</div>");
		title.attr("tabId", id);
		this._e.tabLi[id].append("<div class='left'></div>").append(title).append("<div class='right'></div>").appendTo(this._e.tabUl);
		
		this._e.content[id] = $("<div class='" + this.options.tabContentClass + "'>");
		this._e.content[id].attr("tabIndex", id).appendTo(this.element);
		this._e.content[id].data("tab", tab);
		if(noSelected != true){
			this._selectByTabId(id);
		}
		title.click(function(event){
			event.preventDefault();
			self._selectByTabId($(this).attr("tabId"));
		});
		if(tab.canClose == true){
			var closeBtn = $("<span class='ui-icon ui-icon-close ui-icon-close-default'>Remove Tab</span>"), closeDiv = $("<div class='middle-button'></div>");
			closeDiv.append(closeBtn);
			title.after(closeDiv);
			closeBtn.click(function(event){
				event.stopPropagation();
				self._removeTabByTabId(id);
			});
		}
		//alert(this._e.tabLi[id].width());
		//this._e.tabUl.width(this._e.tabUl.width() + this._e.tabLi[id].width());
	},
	_createTabButton: function(){
		var self = this;
		$.each(this.options.buttons, function(i, conf){
			var button = $("<button></button>");
			button.addClass(conf.classname);
			button.attr("title", conf.title);
			if(conf.text){
				button.text(conf.text);
			}
			button.click(function(event){
				if($.isFunction(conf.click)){
					var selectedTabId = self._getCurrentTabId();
					var tabData = self._e.content[selectedTabId].data("tab");
					conf.click.call(self, self.options.selected, tabData.data);
				}
			});
			self._e.head.append(button);
		});
	},
	select: function(index){
		var tabId = this._getTabIdByIndex(index);
		if(tabId){
			if(tabId==1){
				this._e.content[tabId].data("isLoad",false);
			}
			this._selectByTabId(tabId);
		}
		
	},
	_selectByTabId: function(tabId){
		var index = this._getIndexdByTabId(tabId);
		if(this.options.selected != index || this._e.content[tabId].data("isLoad") != true){
			$(".selected", this._e.tabUl).removeClass("selected");
			this._e.tabLi[tabId].addClass("selected");
			$(".tabContent-show", this.element).removeClass("tabContent-show").hide();
			this._e.content[tabId].addClass("tabContent-show");
			this._e.content[tabId].show();
			if(this._e.content[tabId].data("isLoad") != true){
				this._renderContent(this._e.content[tabId], this._e.content[tabId].data("tab"));
			}
		}
		this.options.selected = index;
	},
	_getIndexdByTabId: function(tabId){
		var i = 0;
		$.each(this._e.tabLi, function(p, tab){
			if(p == tabId){
				return false;
			}
			i++;
		});
		return i;
	},
	_getTabIdByIndex: function(index){
		var i = 0, id;
		$.each(this._e.tabLi, function(p, tab){
			if(i == index/1){
				id = p;
				return false;
			}
			i++;
		});
		return id;
	},
	_getCurrentTabId: function(){
		return this._getTabIdByIndex(this.options.selected);
	},
	_getTabIdByTabData: function(tab){
		var tabId = null;
		$.each(this._e.content, function(p, content){
			if(tab.url){
				if(content.data("tab").url == tab.url){
					tabId = p;
					return false;
				}
			}
		});
		return tabId;
	},
	_getTabLength: function(){
		var i = 0;
		$.each(this._e.tabLi, function(p, tab){
			i++;
		});
		return i;
	},
	_getId: function(){
		var tabIndex = this.element.data("tabIndex");
		if(tabIndex){
			tabIndex = tabIndex/1 + 1;
		}else{
			tabIndex = 1;
		}
		this.element.data("tabIndex", tabIndex);
		return tabIndex;
	},
	_renderContent: function(content, tab){
		content.data("isLoad", true);
		if(tab.useIframe == true){
			this._frame(content, tab);
		}else{
			this._html(content, tab);
		}
	},
	_removeTabByTabId: function(tabId){
		this._e.tabLi[tabId].unbind().removeClass().remove();
		this._e.content[tabId].unbind().removeClass().remove();
		delete this._e.tabLi[tabId];
		delete this._e.content[tabId];
		if($(".selected", this._e.tabUl).length == 0){
			this.options.selected = -1;
			var lastTabId = this._getTabLength();
			this.select(lastTabId - 1);
		}
	},
	_html: function(content, tab){
		var self = this;
		if(tab.url){
			var o = this.options;
			$.ajax( $.extend( {}, o.ajaxOptions, {
				url: tab.url,
				success: function( r, s ) {
					content.html("");
					content.html( r );
					if($.isFunction(tab.load)){
						tab.load.call(content, content.attr("tabIndex"), tab.data);
					}
				},
				error: function( xhr, s, e ) {
					content.html("");
					content.html( xhr );
					try {
						o.ajaxOptions.error( xhr, s, index, a );
					}
					catch ( e ) {}
				}
			} ) );
		}else if(tab.html){
			content.html("");
			content.html( tab.html );
			if($.isFunction(tab.load)){
				tab.load.call(content, content.attr("tabIndex"), tab.data);
			}
		}
	},
	_frame: function(content, tab){
		this._tabiframe(content, {height: this.element.height()-30, top:30, left:0, src: tab.url});
	},
	destroy: function(){
		$("*", this.element).unbind().removeClass().remove();
		return $.Widget.prototype.destroy.call( this );
	},
	_tabiframe : function(content, s) {
		function prop(n) {
		    return n && n.constructor === Number ? n + 'px' : n;
		}
		s = $.extend({
			top : 'auto',
			left : 'auto',
			width : 'auto',
			height : 'auto',
			opacity : true,
			src : 'javascript:false;'
		}, s);
		var html = '<iframe class="bgiframe"frameborder="0" frameIndex="-1"src="' + s.src + '" scrolling="no"'
				+ 'style="display:block;z-index:999;overflow:hidden;padding-bottom:10px;'
				+ 'width:' + (s.width == 'auto' ? '100%' : prop(s.width)) + ';'
				+ 'height:' + (s.height == 'auto' ? '100%' : prop(s.height)) + ';' + '" />',
			self = this;
		return content.each(function() {
					if ($(this).children('iframe.bgiframe').length > 0){
						$(this).children('iframe.bgiframe').remove();
					}
					var iframe = $(html);
					var tabIndex = $(this).attr("tabIndex");
					iframe.attr("frameIndex", tabIndex);
					$(".middle", self._e.tabLi[tabIndex]).before("<div class='middle-button loading-icon'><div class='loading'></div></div>");
					iframe.bind("load", function(){
						try {
							self._resizeFrame(this);
							self._initFrame();
						} catch (ex) {
						}
						$(".loading-icon", self._e.tabLi[tabIndex]).remove();
					})
					this.insertBefore(iframe[0],
							this.firstChild);
				});
	},
	_initFrame: function(){
		var self = this;
		if(this.element.data("_interval")){
			this.element.data("_interval").clearInterval();
		}
		var _interval = setInterval(function(){
			var TtabId = self._getCurrentTabId();
			var frame = $("iframe[frameIndex=" + TtabId + "]")[0];
			if(frame){
				self._resizeFrame(frame);
			}
		}, 500);
		this.element.data("_interval", _interval);
	},
	_resizeFrame: function(frame){
		if(!frame){
			return;
		}
		try{
			var bHeight = frame.contentWindow.document.body.scrollHeight;
			var dHeight = frame.contentWindow.document.documentElement.scrollHeight;
			var minHeight = 500;
			var height = Math.max(bHeight, dHeight, minHeight);
			if(height > 0){
				$(frame).height(height);
			}
		}catch(e){}
	}
});
}( jQuery ));