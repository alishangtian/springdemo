/*----------------------------------------------
//文件名：        年月选择下拉框
//文件功能描述：   只选择年份和月份的日期下拉框
//             
//----------------------------------------------------*/
(function($,undefined){

	$.widget("dtui.dtmonthpicker",$.dtui.dtabscombobox,{
		options:{
			dropPanelHeight: 'auto',
			dropPanelWidth: '220px',
			months:['一月','二月','三月','四月','五月','六月',
			        '七月','八月','九月','十月','十一月','十二月'],
			valueFormat: 'yyyymm',
			textFormat: 'yyyy年m月'
		},
		
		
		_init: function() {
			this._super();
						
			if (this.options.initValue){
				var ym = this._parseYearMonth(this.options.initValue);
				this.selectYear = ym.year;
				this.selectMonth = ym.month;
				this._adjustYearPanelContent();
				this._refreshInputValue();
				this._e.valueText.attr('value', this._e.valueText.val());
				this._e.inputText.attr('value', this._e.inputText.val());
			}
			else {
				var date = new Date();
				this.selectYear = date.getFullYear();
				this.selectMonth = date.getMonth()+1;
			}
			this._refreshSelection();
		},
		
		_adjustYearPanelContent: function() {
			if (this.selectYear >= this.beginYear && this.selectYear < this.beginYear + 10){
				return;
			}
			if (this.selectYear >= this.beginYear + 10) {
				while(this.selectYear >= this.beginYear + 10){
					this.beginYear += 10;
				}
				this._refreshYearPanel();
			}
			else if (this.selectYear < this.beginYear){
				while(this.selectYear < this.beginYear){
					this.beginYear -= 10;
				}
				this._refreshYearPanel();
			}
		},
		
		_parseYearMonth: function(yearMonth) {
			if (yearMonth instanceof Date){
				//由于getMonth返回的值是0~11，所以要+1
				return {year:yearMonth.getFullYear(), month: yearMonth.getMonth()+1};
			}
			else {
				var vf = this.options.valueFormat,
					yStartPos = vf.indexOf('yyyy'),
					mStartPos = vf.indexOf('mm'),
					mLength = 2;
				if (mStartPos >= 0) {
					monthStr = yearMonth.substr(mStartPos, 2);
					mLength = 2;
				}
				else {
					mStartPos = vf.indexOf('m');
					var nextChar = yearMonth[mStartPos+1],
						reg = /^\d+$/;
				    if (!nextChar.match(reg)){
				    	mLength = 1;
				    }else{    
				        mLength = 2;        
				    }    					
				}
				return {year:Number(yearMonth.substr(yStartPos,4)), month: Number(yearMonth.substr(mStartPos,mLength))};
			}
		},
		
		_refreshMonthSelection: function() {
			var month = this.selectMonth;
		
			this.monthPanel.find('li.item').removeClass('ui-state-active');
			//根据月份获取该月份所在的li的位置
			var index = ((month-1)%6) * 2 +  Math.floor((month-1)/6);
			this.monthPanel.find('li.item').eq(index).addClass('ui-state-active');
			
		},
		
		_refreshYearSelection: function() {
			this.yearPanel.find('li.item').removeClass('ui-state-active');
			//年份相对于当前页第一年的偏移量
			var yearOffset = this.selectYear - this.beginYear;
			if (yearOffset>=0 && yearOffset<10){
				//根据年份获取该年份所在的li的位置
				var index = (yearOffset%5) * 2 +  Math.floor(yearOffset/6);
				this.yearPanel.find('li.item').eq(index).addClass('ui-state-active');
			}
		},
		
		/**
		 * 根据年月值设置年月面板对应年月的选中状态
		 * @param yearMonth
		 */
		_refreshSelection: function() {
			this._refreshMonthSelection();
			this._refreshYearSelection();
		},
						
		
		_buildContent:function(){
			 var dropPanel=this._e.selectOption;
			 dropPanel.css('minHeight', 230);
			 this.totalContainer.addClass("ui-monthpicker");
			 //dropPanel.addClass('ui-monthpicker');
			 this.yearMonthContainer = $('<div/>').addClass('yearmonthcontainer').appendTo(dropPanel);
			 this.monthPanel = $('<ul/>').addClass("monthpanel").appendTo(this.yearMonthContainer);
			 this.yearContainer = $('<div/>').addClass("yearcontainer").appendTo(this.yearMonthContainer);
			 //清除浮动
			 $('<div style="clear:both;"></div>').appendTo(this.yearMonthContainer);
			 this._buildMonthElem();
			 this._buildYearElem();
			 this._buildButtonPanel();
		},
		
		_buildMonthElem: function(){
			var months = this.options.months; 
			for (var i=0; i<months.length/2; i++){
				$('<li/>').addClass('item').text(months[i]).data('value', i+1).appendTo(this.monthPanel);
				$('<li/>').addClass('item').text(months[i+6]).data('value', i+7).appendTo(this.monthPanel);
			}
		},
		
		_buildYearElem: function() {
			var buttonPanel = this.buttonPanel = $('<div/>').addClass('pagingpanel').appendTo(this.yearContainer);
			this.prevButton = $('<a class="ui-datepicker-prev ui-corner-all" data-handler="prev" data-event="click" title=""><span class="ui-icon ui-icon-circle-triangle-w"></span></a>')
			.appendTo(buttonPanel);
			this.nextButton = $('<a class="ui-datepicker-next ui-corner-all" data-handler="next" data-event="click" title=""><span class="ui-icon ui-icon-circle-triangle-e"></span></a>')
			.appendTo(buttonPanel);
			
			var yearPanel = this.yearPanel = $('<ul/>').addClass("yearpanel").appendTo(this.yearContainer);
			
			var date = new Date();
			var year = date.getFullYear();
			var beginYear = this.beginYear = year - 4;
			for (var i=beginYear; i <= year; i++){
				$('<li/>').addClass('item').text(i).appendTo(this.yearPanel);
				$('<li/>').addClass('item').text(i+5).appendTo(this.yearPanel);
			}
		},
		
		_buildButtonPanel: function() {
			this.buttonPanel = $('<div/>').addClass('buttonpanel ui-widget ui-widget-content').appendTo(this._e.selectOption);
			var btnOpt = {width: 60, height:30};
			this.btnOk = $('<button type="button">确定</button>').dtbutton(btnOpt).appendTo(this.buttonPanel);
			this.btnClear = $('<button type="button">清空</button>').dtbutton(btnOpt).appendTo(this.buttonPanel);
			this.btnCurMonth = $('<button type="button">本月</button>').dtbutton(btnOpt).appendTo(this.buttonPanel);
		},
		
		_refreshInputValue: function() {
			if(!this.selectYear || !this.selectMonth) {
				this._e.inputText.val("");
				this._e.valueText.val("");
				return;
			}
			var valueText = this._FormatYearMonth(this.selectYear, this.selectMonth, this.options.valueFormat),
				inputText = this._FormatYearMonth(this.selectYear, this.selectMonth, this.options.textFormat);
			this._e.valueText.val(valueText);
			this._e.inputText.val(inputText);
		},
				
		/*
		 * 根据beginYear的值刷新年面板中显示的年份
		 */
		_refreshYearPanel: function() {
			var self = this;
			self.yearPanel.find('li.item').each(function(i){
				//var year = Number($(this).text());
				//$(this).text(year-10);
				var offset = Math.floor(i/2)+(i%2)*5;
				$(this).text(self.beginYear + offset);
			});
		},
		
		//binds checkbox events
		_bindEvent:function(){
			this._super();
			this._hoverable(this._e.selectOption.find('li.item'));
			var self=this;
			this.prevButton.click(function(){
				self.beginYear = self.beginYear - 10;
				self._refreshYearPanel();
				self._refreshYearSelection();
			});
			this.nextButton.click(function(){
				self.beginYear = self.beginYear + 10;
				self._refreshYearPanel();
				self._refreshYearSelection();
			});
			this.yearPanel.find('li.item').click(function(){
				self.yearPanel.find('li.item').removeClass('ui-state-active');
				$(this).addClass('ui-state-active');
				self.selectYear = Number($(this).text());
				self._refreshInputValue();
				self._trigger("change");
			});
			this.monthPanel.find('li.item').click(function(){
				self.monthPanel.find('li.item').removeClass('ui-state-active');
				$(this).addClass('ui-state-active');	
				self.selectMonth = Number($(this).data('value'));
				self._refreshInputValue();
				self._trigger("change");
			});			
			this.btnOk.click(function() {
				self._refreshInputValue();
				self._hideOption();				
			});
			this.btnClear.click(function() {
				self._clear();
				self._hideOption();
				self._trigger("change");
			});
			this.btnCurMonth.click(function() {
				var date = new Date();
				self.selectYear = date.getFullYear();
				self.selectMonth = date.getMonth()+1;
				self._adjustYearPanelContent();
				self._refreshSelection();
				self._refreshInputValue();
				self._trigger("change");
			});
		},
		
		_showOption: function(){
			this._adjustYearPanelContent();
			this._refreshYearSelection();
			this._super();
		},
		//after addItem  and removeItem rebuild menu  and rebinds checkbox  event
		refresh:function(){
			var self=this;
		},
		_clear: function() {
			var self = this;
			if (self._e.inputText.val() && self._e.valueText.val()){
				var date = new Date();
				self.selectYear = date.getFullYear();
				self.selectMonth = date.getMonth()+1;
				self._adjustYearPanelContent();
				self._refreshSelection();
				self._e.inputText.val('');
				self._e.valueText.val('');
				return true;
			}
			return false;
		},
				
        /**
         * 根据当前的值刷新内容区域的选择状态
         */
        _refreshContentState: function() {
			//使valueText和inputText中的内容与selection对象的内容保持一致
        	//需要处理空值
			var ym = this._parseYearMonth(this._e.valueText.val());
			this.selectYear = ym.year;
			this.selectMonth = ym.month;
			this._adjustYearPanelContent();
			this._refreshSelection();
        },
        
        /**
         * 根据值获取对应的文本
         * @param value
         */
        _getText: function(value){
        	if(value){
        		var ym = this._parseYearMonth(value);
    			return this._FormatYearMonth(ym.year, ym.month, this.options.textFormat);
        	}
        	else {
        		return '';
        	}
        },
        _FormatYearMonth: function(year, month, format){
			var v = format;
			v = v.replace('yyyy', year);
			v = v.replace('mm', (month<10?'0':'') + month);
			v = v.replace('m', month);
			return v;
        }
        
	});
})(jQuery);