/*
 * jQuery switchbutton
 *
 * Based on work by tdreyno on iphone-style-checkboxes for events management
 * (https://github.com/tdreyno/iphone-style-checkboxes)
 * 
 * Copyright 2011, L.STEVENIN
 * Released under the MIT license.
 *
 * Depends:
 *	jquery.ui.widget.js (jQuery UI Widget Factory - http://wiki.jqueryui.com/w/page/12138135/Widget%20factory)
 * 	jquery.tmpl.js (jQuery Templates - http://api.jquery.com/category/plugins/templates/)
 */

(function($, undefined){
	
	$.widget('dtui.dtswitchbutton', {
		options: {
			//add new 
			checked:true,
			disabled:false,
			
			classes: '',
			duration: 200,
			dragThreshold: 5,
			autoResize: true,
			disabledClass: 'ui-switchbutton-disabled ui-state-disabled'
		},
		_create: function() {
			if(!this.element.is(':checkbox')) {
				return;
			}
			this._wrapCheckboxInContainer();//组装 switchbutton
			this._attachEvents();
			this._globalEvents();
			this._disableTextSelection();
			//alert(this.element.prop('checked'));
			if(this.options.checked) {
				this.$container.toggleClass('ui-switchbutton-active', this.options.checked);
			}
			if(this.options.autoResize) {
				this._autoResize();
			}
			this._initialPosition();
		},
		_wrapCheckboxInContainer: function() {
			this.$container=$('<div class="ui-switchbutton ui-switchbutton-default"></div>');
			if(this.options.classes!=''){
				this.$container.addClass(this.options.classes);
			}
			this.$disabledLabel = $('<label class="ui-switchbutton-disabled"></label>');
			this.$disabledSpan  = $('<span>OFF</span>');
			this.$enabledLabel  = $('<label class="ui-switchbutton-enabled"></label>');
			this.$enabledSpan   = $('<span>ON</span>');
			this.$handle = $('<div class="ui-switchbutton-handle"></div>');
			this.$container.append(this.$disabledLabel);
			this.$container.append(this.$enabledLabel);
			this.$container.append(this.$handle);
			this.$disabledLabel.append(this.$disabledSpan);
			this.$enabledLabel.append(this.$enabledSpan);
			this.element.after(this.$container);
			this.element.remove();
			this.$container.append(this.element);
			
		},
		_attachEvents: function() {
			var obj = this;
			this.$container
				// A mousedown anywhere in the control will start tracking for dragging
				.bind('mousedown touchstart', function(event) {
					event.preventDefault();
					if(obj.options.disabled) { return; }
					var x = event.pageX || event.originalEvent.changedTouches[0].pageX;
					$.currentlyClicking	= obj.$handle;
					$.dragStartPosition	= x;
					$.handleLeftOffset	= parseInt(obj.$handle.css('left'), 10) || 0;
					$.dragStartedOn		= obj.element;
				})
				// Utilize event bubbling to handle drag on any element beneath the container
				.bind('iPhoneDrag', function(event, x) {
					event.preventDefault();
					if(obj.options.disabled) { return; }
					if(obj.element != $.dragStartedOn) { return; }
					var p = (x + $.handleLeftOffset - $.dragStartPosition) / obj.rightSide;
					if(p < 0) { p = 0; }
					if(p > 1) { p = 1; }
					obj.$handle.css({ 'left': p * obj.rightSide });
					obj.$enabledLabel.css({ 'width': p * obj.rightSide });
					obj.$disabledSpan.css({ 'margin-right': -p * obj.rightSide });
					obj.$enabledSpan.css({ 'margin-left': -(1 - p) * obj.rightSide });
				})
				// Utilize event bubbling to handle drag end on any element beneath the container
				.bind('iPhoneDragEnd', function(event, x) {
					if(obj.options.disabled) { return; }
					var willChangeEvent = jQuery.Event('willChange');
					obj.element.trigger(willChangeEvent);
					if(willChangeEvent.isDefaultPrevented()) {
						checked = obj.options.checked;
					}
					else {
						var checked;
						if($.dragging) {
							var p = (x - $.dragStartPosition) / obj.rightSide;
							checked = (p < 0) ? Math.abs(p) < 0.5 : p >= 0.5;
						}
						else {
							checked = !obj.options.checked;
						}
					}
					$.currentlyClicking = null;
					$.dragging = null;
					obj.options.checked=checked;
					obj.$container.toggleClass('ui-switchbutton-active', checked);
					obj.element.change();
					obj.element.trigger('didChange');
				});
			// Animate when we get a change event
			this.element.change(function() {
				obj.refresh();
				var new_left = obj.options.checked ? obj.rightSide : 0;
				obj.$handle.animate({ 'left': new_left }, obj.options.duration);
				obj.$enabledLabel.animate({ 'width': new_left+14 }, obj.options.duration);   //modify
				obj.$disabledSpan.animate({ 'margin-right': -new_left }, obj.options.duration);
				obj.$enabledSpan.animate({ 'margin-left': new_left - obj.rightSide }, obj.options.duration);
			});
		},
		_globalEvents: function() {
			if($.initComplete) {
				return;
			}
			var opt = this.options;
			$(document)
				// As the mouse moves on the page, animate if we are in a drag state
				.bind('mousemove touchmove', function(event) {
					if(!$.currentlyClicking) { return; }
					event.preventDefault();
					var x = event.pageX || event.originalEvent.changedTouches[0].pageX;
					if(!$.dragging && (Math.abs($.dragStartPosition - x) > opt.dragThreshold)) { 
						$.dragging = true; 
					}
					$(event.target).trigger('iPhoneDrag', [x]);
				})
				// When the mouse comes up, leave drag state
				.bind('mouseup touchend', function(event) {
					if(!$.currentlyClicking) { return; }
					event.preventDefault();
					var x = event.pageX || event.originalEvent.changedTouches[0].pageX;
					$($.currentlyClicking).trigger('iPhoneDragEnd', [x]);
				});
		},
		_disableTextSelection: function() {
			// Disable IE text selection, other browsers are handled in CSS
			/*if (!$.browser.msie) { return; }*/
			// Elements containing text should be unselectable
			$([this.$handle, this.$disabledLabel, this.$enabledLabel, this.$container]).attr('unselectable', 'on');
		},
		_autoResize: function() {
			var	onLabelWidth	= this.$enabledLabel.width(),
				offLabelWidth	= this.$disabledLabel.width(),
				spanPadding		= this.$disabledSpan.innerWidth() - this.$disabledSpan.width()
				handleMargins	= this.$handle.outerWidth() - this.$handle.innerWidth();
			var containerWidth = handleWidth = (onLabelWidth > offLabelWidth) ? onLabelWidth : offLabelWidth;
			this.$handle.css({ 'width': handleWidth });
			handleWidth = this.$handle.width();
			containerWidth += handleWidth + 6;
			spanWidth = containerWidth - handleWidth - spanPadding - handleMargins;
			this.$container.css({ 'width': containerWidth });
			this.$container.find('span').width(spanWidth);
		},
		_initialPosition: function() {
			this.$disabledLabel.css({ width: this.$container.width() - 5 });
			this.rightSide = this.$container.width() - this.$handle.outerWidth();
			if(this.options.checked) {
				this.$handle.css({ 'left': this.rightSide });
				this.$enabledLabel.css({ 'width': this.rightSide+14});
				this.$disabledSpan.css({ 'margin-right': -this.rightSide });
			}
			else {
				this.$enabledLabel.css({ 'width': 0 });
				this.$enabledSpan.css({ 'margin-left': -this.rightSide });
			}
			this.refresh();
		},
		check: function() {
			this.options.checked=true;
			this.refresh();
			return this._setOption('checked', true);
		},
		uncheck: function() {
			this.options.checked=false;
			this.refresh();
			return this._setOption('checked', false);
		},
		enable: function() {
			this.options.disabled=false;
			this.refresh();
			return this._setOption('disabled', false);
		},
		disable: function() {
			this.options.disabled=true;
			this.refresh();
			return this._setOption('disabled', true);
		},
		getChecked:function(){
			return this.options.checked;
		},
		getDisabled:function(){
			return this.options.disabled;
		},
		widget: function() {
			return this.$container;
		},
		refresh: function() {
			if(this.options.disabled) {
				this.$container.addClass(this.options.disabledClass);
				return false;
			}
			else {
				this.$container.removeClass(this.options.disabledClass);
			}
		}
	});
})(jQuery);