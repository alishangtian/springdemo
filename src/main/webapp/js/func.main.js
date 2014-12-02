(function(w){
	w.Main = {}, w.worktab = null;
	Main.init=function(){
		this.initNav();
		$('#worktab').show();
		this.initTab();
	}
	Main.initTab = function(){
		worktab = $('#worktab').dttabs({
			heightStyle: 'fill',
			//height: '400px',
			useIframe: true,
			maxTabs: 9,
			canClose: true,
			add: function(event, ui){
				//设置tab内容显示区域的padding为0，使界面看起来紧凑些
				$(ui.panel).css('padding', 0);
				$(this).dttabs('refresh');
			}
		});
		worktab.dttabs('add','sm/welcomeAction.action','主页', true, true);
		worktab.dttabs('remove', 0);
	}
	Main.initNav = function(){
		//使用第一个横向菜单二级菜单的子菜单初始化左侧菜单
		$.ajax({
			url:"sm/mainMenuAction!getTopMenu.action",
			type:'post',
			async:false,
			success:function(datasource){
				var data = $.parseJSON(datasource);
				//加载头部菜单
				for(var i=0;i<data.length;i++){
					data[i].subMenu = Main.findSub(data[i].subMenu);
				}
				$("#topmenu").dtnavbar({
					menuItems : data,
					childrenField: 'subMenu',
					textField: 'menuName',
					idField: 'menuId',
					select: Main.doNavItemSelect
				});
			},
			error: function(e){
				alert(e);
			}
		});	
	}
	Main.findSub = function(data){
		var dataJsonTmp = [];
		if(!(data instanceof Array))
			dataJsonTmp.push(data);
		else{
			for(var i=0;i<data.length;i++){
				var subMenuTmp = data[i].subMenu;
				if(subMenuTmp && subMenuTmp.length>0){
					dataJsonTmp = dataJsonTmp.concat(arguments.callee(subMenuTmp));
				}else{
					dataJsonTmp.push(data[i]);
				}
			}
		}
		return dataJsonTmp;
	}
	Main.exitSys = function(){
		$.ajax({url:path+'/Home!loginOut.action',
			type:'post',
			cache:false,
			success:function(responseText){
				if(responseText)
					alert(responseText);
				else
					window.location.reload();
			},
			error: function(e){
				alert(e);
			}
	});
	}
	Main.doNavItemSelect = function(event, ui) {
		if ($(ui.item).data('level') == 0){
			return;
		}
		event.preventDefault();
		//var subMenuData = $(ui.item).data('menudata').subMenu;
		//$('#treemenu').dttreemenu('refreshMenuItems', subMenuData);
		var funcData = $(ui.item).data('menudata');
		worktab.dttabs('add', funcData.menuUrl, funcData.menuName,true);
		return false;
	}
})(window);