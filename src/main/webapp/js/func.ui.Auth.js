(function($,undefined){
	$.widget("ui.RoleAndFuncAuth", {
		options: {
			classname: "",
			roleclick: null,
			disabled: false,
			data: null
		},
		_create: function(){
			var self = this, roleItemList = [];
			$.each(this.options.data, function(i, prop){
				roleItemList.push(self._createUi(prop, self.element));
			});
			this.element.data("roleItemList", roleItemList);
		},
		_createUi: function(data, target){
			var container = $("<div></div>");
			var authCheckBox = $("<input type='checkbox' />");
			var authName = $("<label></label>");
			var designate = $("<input type='checkbox' />");
			var designateLabel = $("<label></label>");
			var self = this;
			authName.html(data.text);
			if($.isFunction(this.options.roleclick)){
				authName.click(function(){
					self.options.roleclick(data, authCheckBox);
					return false;
				});
			}
			designateLabel.html("可委派");
			if(data.checked == true){
				authCheckBox.prop("checked", true);
			}
			if(data.designate == 'Y'){
				designate.prop("checked", true);
			}
			if(this.options.disabled == true){
				authCheckBox.prop("disabled", true);
				designate.prop("disabled", true);
			}
			container.addClass(this.options.classname);
			container.append(authCheckBox).append(authName).append("(").append(designate).append(designateLabel).append(")");
			target.append(container);
			return {
				data: data,
				container: container,
				authCheckBox: authCheckBox,
				authName: authName,
				designate: designate,
				designateLabel: designateLabel
			};
		},
		getData: function(){
			var roleItemList = this.element.data("roleItemList"), resultList = [];
			$.each(roleItemList, function(i, roleItem){
				var data = roleItem.data;
				data.checked = roleItem.authCheckBox.prop("checked");
				data.designate = roleItem.designate.prop("checked") == true ? "Y" : "N";
				resultList.push(data);
			});
			return resultList;
		}
	});
	$.widget("ui.FuncAuth", $.ui.RoleAndFuncAuth, {
		_create: function(){
		},
		_loadItem: function(){
			var self = this, funcItemList = [];
			$.each(this.options.data, function(i, prop){
				var container = $("<tr></tr>"), funcTd = $("<td></td>"), operTd = $("<td></td>");
				container.append(funcTd).append(operTd);
				var funcItem = self._createUi(prop, funcTd);
				funcItem.container.addClass("func-div");
				funcItem.oper = operTd;
				funcItemList.push(funcItem);
				self._createOper(prop, operTd);
				container.appendTo(self.element);
			});
			this.element.data("roleItemList", funcItemList);
		},
		load: function(option){
			this.element.empty();
			this.options = option;
			this._loadItem();
		},
		_createOper: function(data, target){
			if(data.funcItemInfos){
				var self = this, operList = [];
				$.each(data.funcItemInfos, function(i, prop){
					var operItem = self._createUi(prop, target);
					operItem.container.addClass("func-oper-div");
					operList.push(operItem);
				});
				target.data("funcItemInfos", operList);
			}else{
				target.html("&nbsp;");
			}
		},
		getData: function(){
			var roleItemList = this.element.data("roleItemList"), resultList = [];
			$.each(roleItemList, function(i, roleItem){
				var data = roleItem.data;
				data.checked = roleItem.authCheckBox.prop("checked");
				data.designate = roleItem.designate.prop("checked") == true ? "Y" : "N";
				data.funcItemInfos = [];
				var operItem = roleItem.oper;
				var funcItemInfos = operItem.data("funcItemInfos");
				$.each(funcItemInfos, function(k, prop){
					var operData = prop.data;
					operData.checked = prop.authCheckBox.prop("checked");
					operData.designate = prop.designate.prop("checked") == true ? "Y" : "N";
					data.funcItemInfos.push(operData);
				});
				resultList.push(data);
			});
			return resultList;
		}
	});
})(jQuery);