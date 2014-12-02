/**
 * 未完成
 * 1、事件为封装完成
 * 2、导入导出方法 未测试完成 js不能读写本地文件可能是导致测试失败的根本原因
 * 3、url 测试未测试
 */

(function( $, undefined ) {

   var gridPrototype = {};
	
   $.extend(gridPrototype,$.fn.jgrid, {
		options:{
			width: '100%',
			height: 150,
			caption: "",
			titleAlign:"left",                //解决了
			//是否显示隐藏表格按钮
			hidegrid: false,
			forceFit : false,
    		colModel:[],
    		url:"",
    		requestType:"get",                  //解决了
    		datatype: "json",
    		rownumbers:false,                   //是否显示行号
    		showPager:true,                     //是否分页 已解决
     		
    		rowNum:20,
            multiselect: false,
            cellEdit: false,
            editurl: null,

            altRows: true,
            shrinkToFit: false,
            sortable:false,
            sortname:null,
            sortorder:"asc",
            jsonReader: {},
            prmNames:{page:"page",rows:"rows", sort: "sidx",order: "sord", search:"_search", nd:"nd", id:"id",oper:"oper",editoper:"edit",addoper:"add",deloper:"del", subgridid:"id", npage: null, totalrows:"totalrows"},
            
            //callback
            afterInsertRow: null,
            beforeSelectRow: null,
            gridComplete: null,
            loadComplete: null,
            loadError: null,
            onSelectRow: null,
            onCellSelect: null,
            dblClickRow: null,
            rightClickRow: null
		   },
		
		proxyObj:undefined,
		
		_setOption:function(){
			
			this.options.mtype=this.options.requestType;
			//this.options.showPager==false?(this.options.height="100%"):"";
			if (this.options.showPager){
				this.options.pager = '#'+this.pagerId;
			}
		},
		
		_create:function(){
			this._createResizeDiv();
			this._createPager();
			this._setOption();
			this.proxyObj=this.element.jqGrid(this.options);
			this._bindResizeEvent();
		},
		
		_createPager: function() {
			this.pagerId = this.element.attr('id') + '_pager';
			this.element.after("<div id='"+this.pagerId+"'></div>");
		},
		_createResizeDiv: function() {
			if (typeof this.options.width == 'string' && this.options.width.indexOf('%')>0) {
				this.resizeDiv = $('<div style="width:'+this.options.width+'"></div>')
					.attr('id', this.element.attr('id') + '_div');
				this.resizeDiv.insertAfter(this.element);
			}
		},
		_bindResizeEvent: function() {
			var that = this,
				resizeDiv = this.resizeDiv;  
			if (typeof this.options.width == 'string' && this.options.width.indexOf('%')>0) {
				$(window).on('resize', function() {
					//alert(resizeDiv.prop('clientWidth')+','+resizeDiv.prop('clientWidth')+
					//		','+resizeDiv.attr('clientWidth')+','+resizeDiv.attr('clientWidth'));
					var width = resizeDiv.prop('clientWidth');
					if (width == null || width < 1){width = resizeDiv.prop('offsetWidth');}
					width = width - 2; 					
					//alert(that.proxyObj.width());
					if (width > 0 &&  Math.abs(width - that.proxyObj.width()) > 5){
						that.setGridWidth(width);
					}
				})
				.trigger('resize');
			}
		},
		_init:function(){
			//set  titleAlign  location
			this.options.titleAlign!="left"?this.setTitleAlign(this.options.titleAlign):"";
			this.options.showHideButton==false?this.showHideButton():"";
		},
		_setOptions: function(options){
			this._super(options);
			this.proxyObj.setGridParam(options);
		},
		option: function( key, value ){
			this._super(key, value);
			if ( typeof key === "string" && value === undefined) {
				return this.proxyObj.getGridParam(key);
			}
		},
		addRowData : function(rowid,rdata,pos,src){
			this.proxyObj.addRowData(rowid,rdata,pos,src);
		},
		
		delRowData : function(rowid){
			this.proxyObj.delRowData(rowid);
		},
		
		getRowData : function( rowid ){
			return this.proxyObj.getRowData(rowid);
		},
		
		setRowData : function(rowid, data, cssp) {
			this.proxyObj.setRowData(rowid, data, cssp);
		},
		
		clearGridData : function(clearfooter) {
			this.proxyObj.clearGridData(clearfooter);
		},
		
		getCell : function(rowid,col) {
			this.proxyObj.getCell(rowid,col);
		},
		
		getCol : function (col, obj, mathopr) {
			this.proxyObj.getCol(col, obj, mathopr);
		},
		
		getDataIDs : function (){
			this.proxyObj.getDataIDs();
		},
		
		getInd : function(rowid,rc){
			this.proxyObj.getInd();
		},
		
		hideCol : function (colname) {
			this.proxyObj.hideCol(colname);
		},
		
		showCol : function(colname) {
			this.proxyObj.showCol(colname);
		},
		
		setCaption : function (newcap){
			this.proxyObj.setCaption(newcap);
		},
		
		setCell : function(rowid,colname,nData,cssp,attrp, forceupd){
			this.proxyObj.setCell(rowid,colname,nData,cssp,attrp, forceupd);
		},
		
		setGridHeight : function (nh) {
			this.proxyObj.setGridHeight(nh);
		},
		
		setGridWidth : function(nwidth, shrink) {
			this.proxyObj.setGridWidth(nwidth, shrink);
		},
		
		setLabel : function(colname, nData, prop, attrp ){
			this.proxyObj.setLabel(colname, nData, prop, attrp);
		},
		
		resetSelection : function(){
			this.proxyObj.resetSelection();
		},
		setSelection : function(selection,onsr, e){
			this.proxyObj.setSelection(selection,onsr, e);
		},
		
		viewGridRow : function(rowid, p){
			this.proxyObj.viewGridRow(rowid, p);
		},
		
		getGridParam : function(pName){
			return this.proxyObj.getGridParam(pName);
		},
		
		setGridParam : function (newParams){
			this.proxyObj.setGridParam(newParams);
		},
		
		editGridRow : function(rowid, p){
			this.proxyObj.editGridRow(rowid, p);
		},
		
		delGridRow : function(rowids,p) {
			this.proxyObj.delGridRow(rowids, p);
		},
		
		//update selection row data
		updateRowData : function(rowid, data, cssp){
			return this.proxyObj.setRowData(rowid, data, cssp);
		},
		
		//return selection row
		getSelection : function(){
			var id=this.getGridParam('selrow');
			//console.log(id);
			return this.proxyObj.getRowData(id);
		},
		
	    //edit row data
		editRow : function(rowid,keys,oneditfunc,successfunc, url, extraparam, aftersavefunc,errorfunc, afterrestorefunc){
			this.proxyObj.editRow(rowid,keys,oneditfunc,successfunc, url, extraparam, aftersavefunc,errorfunc, afterrestorefunc);
		},
		//save row data
		saveRow : function(rowid, successfunc, url, extraparam, aftersavefunc,errorfunc, afterrestorefunc){
			this.proxyObj.saveRow(rowid, successfunc, url, extraparam, aftersavefunc,errorfunc, afterrestorefunc);
		},
		//cancel row data
		restoreRow : function(rowid, afterrestorefunc){
			this.proxyObj.restoreRow(rowid, afterrestorefunc);
		},
		reload: function() {
			this.proxyObj.trigger('reloadGrid');
		},
		excelExport: function(o){
			this.proxyObj.excelExport(o);
		},
        exportData:function(o){
        	//alert("aas");
        	this.proxyObj.jqGridExport(o);
		},
		
		importData : function(o){
			this.proxyObj.jqGridImport(o);
		},
		
		//set caption location
		setTitleAlign: function(){
			if(this.options.titleAlign=="center"){
				$(this.proxyObj).closest("div.ui-jqgrid-view")
		           .children("div.ui-jqgrid-titlebar")
		           .css("text-align", "center")
		           .children("span.ui-jqgrid-title")
		           .css("float", "none");
			}else if(this.options.titleAlign=="right"){
				$(this.proxyObj).closest("div.ui-jqgrid-view")
		           .children("div.ui-jqgrid-titlebar")
		           .css("height","18.578125px")
		           .children("span.ui-jqgrid-title")
		           .css({"float":"none","position":"absolute","right":"24px"});
			}
       },
       
       //set button show or hide
       showHideButton:function(){
    	   $(this.proxyObj).closest("div.ui-jqgrid-view")
           .children("div.ui-jqgrid-titlebar")
           .children("a")
           .css("display","none");
       }
	});
	
	$.widget( "dtui.dtgrid", gridPrototype);

	$.widget( "dtui.dtpaginggrid", $.dtui.dtgrid, {
		_create:function(){
			var o = this.options;
			
			o.datatype = o.datatype||'json';
			var jsonRoot = o.jsonReader.root?o.jsonReader.root:"resultList";
			$.extend(o.jsonReader, {
				"root": jsonRoot,
				"page": 'pagingInfo.pageNum',
				"total": 'pagingInfo.totalPages',
				"records": 'pagingInfo.totalRows',
				"repeatitems": false,
				"id": '0'
			});
			
			$.extend(o.prmNames, {
				page:'pqb.pagingInfo.pageNum', 
				rows:'pqb.pagingInfo.pageRows', 
				id:this._getKeyFieldName()||'id'
			});
			this._super();
		},
		
		_getKeyFieldName: function() {
			for (var i=0; i< this.options.colModel.length; i++) {
				var col = this.options.colModel[i];
				if (col.key)
					return col.jsonmap?col.jsonmap:col.name;
			}
			return null;
		}
	});
})( jQuery );