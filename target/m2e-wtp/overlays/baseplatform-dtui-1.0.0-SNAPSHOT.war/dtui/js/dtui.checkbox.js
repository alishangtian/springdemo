/**
 * 
 */

(function( $, undefined ) {
//alert('a');


$.widget( "dtui.dtcheck", {
	version: "1.10.1",
	options: {
		disabled: null,
		name:'',
		text: '',
		value: '',
		checked: false,
		height: null,
		width: null,
		click:null
	},
	_create: function() {
		var self = this,
			o = this.options;
		var checkElement = $('<input type="checkbox">').attr({value:o.value, checked:o.checked, name:o.name}).bind('click', function(event){
				self._trigger('click',event);
			}),
			labelElement = $('<span></span>').text(o.text).bind('click',function(event){
				if (o.disabled) return;
				checkElement.prop('checked',!checkElement.prop('checked'));
				self._trigger('click',event);
			}),
			linkElement = $('<a href="javascript:void(0)"></a>').append(labelElement),
			spanElement = $('<span></span>').addClass('dtui-check').append(checkElement).append(linkElement);
		
		//保证单选按钮垂直居中
		this.element.addClass('dtui-check-container');
		
		this.element.append(spanElement);

		if (o.height) {
			this.element.height(o.height);
		}
		if (o.width) {
			this.element.width(o.width);
		}
	},


	widget: function() {
		return this.element;
	},
	
	value: function(v){
		if (arguments.length ===0) {
			return this.options.value;
		}
		else {
			this.options.value = v;
		}
	},
	
	check: function(checked){
		if (arguments.length ===0) {
			return $(':checkbox', this.element).prop('checked');
		}
		else {
			$(':checkbox', this.element).prop('checked', checked);
		}
	},

	_destroy: function() {
		this.element.empty();
	},

	_setOption: function( key, value ) {
		this._super( key, value );
		if ( key === "disabled" ) {
			if ( value ) {
				$('*', this.element).attr("disabled", true );
			} else {
				$('*', this.element).attr("disabled", false );
			}
			return;
		}
	}


});

$.widget( "dtui.dtcheckgroup", {
	version: "1.10.1",
	options: {
		disabled: null,
		name:'',
		required: false,
		items:[],
		col: 1,
		value: null,
		title: '',
		height: null,
		width: null,
		keyField:"id",
		valueField:"name",
		padding_left:15,
		change:null
	},
		
	_create: function() {
		var self = this,
			o = this.options;
		
		this._checkControls = [];
		this._starDiv = $.dtui.util.createStarElem(this.options.required);
		 
		var setElement = $('<fieldset></fieldset>').addClass('dtui-checkgroup-fieldset').append($('<legend></legend>').text(o.title));
		//setElement.css('float','left');
		setElement.css('margin-right', '8px');
		if ($.isArray(o.items)) {
			$.each(o.items, function(i, item){
				self._createCheck(setElement, i, item[self.options.keyField], item[self.options.valueField]);
			});
		}
		else {
			var i=0;
			$.each(o.items, function(value, text){
				self._createCheck(setElement, i++, value, text);
			});
		}
		
		this.element.addClass('dtui-checkgroup');
		this.element.append(this._starDiv);
		this.element.append(setElement);
		
		//this.element.css("display","inline-block");//整体外结构是行结构元素
		if (o.height) {
			this.element.height(o.height);
			setElement.outerHeight(o.height);
		}
		if (o.width) {
			this.element.width(o.width);
		}	
		if (o.value){
			this.value(o.value);
		}
	},

	_createCheck: function(setElement, i, value, text) {
		var self = this,
			o = this.options;
		var checkClick = function(event, ui) {
			self._trigger('change', event, $(this));
		};
		
		var checkControl = $('<div></div').addClass('dtui-checkgroup-item').prop('id', self.element.prop('id')+'_'+value).dtcheck({
			value:value,
			checked: false,
			name:o.name,
			text:text,
			click: checkClick
		});
		
		
		if ((i+1)%o.col !==1)
			checkControl.css('padding-left', o.padding_left);
		
		this._checkControls.push(checkControl);
		setElement.append(checkControl);
		if ((i+1)%o.col ===0)
			setElement.append('<p></p>');
	},

	widget: function() {
		return this.element;
	},
	
	value: function(v){
		var that = this;
		if (arguments.length ===0) {
			var result = '';
			$.each(this._checkControls, function(i, checkControl){
				if (checkControl.dtcheck('check')){
					if (result)
						result += ',';
					result += checkControl.dtcheck('value');
				}
			});
			return result;
		}
		else {
			$.each(this._checkControls, function(i, checkControl){
				checkControl.dtcheck('check',false);
			});
			if (v){
				var valueArray = v.split(',');
				$.each(valueArray, function(i, val){
					that.element.find('input[value="'+val+'"]').prop('checked', true);
				});
			}
		}
	},

	_destroy: function() {
		this.element.empty();
	},

	_setOption: function( key, value ) {
		this._super( key, value );
		if ( key === "disabled" ) {
			if ( value ) {
				$('*', this.element).attr("disabled", true );
			} else {
				$('*', this.element).attr("disabled", false );
			}
			return;
		}
	}


});


})( jQuery );