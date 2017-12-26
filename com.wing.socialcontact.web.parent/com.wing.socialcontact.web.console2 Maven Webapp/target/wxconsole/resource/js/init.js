//页面加载完成之后执行  
$(function() {
	if($.browser.msie<8){
		Msg.alert('提示','抱歉，您使用浏览器版本太低，本系统不支持IE8(内核)以下浏览器。','error');
	}
	queryLeftMenuTree();//左侧菜单
	
	//queryLeftAllUsersTree();//左侧全部人员查询
	
});
//查询左侧操作菜单
function queryLeftMenuTree(){
	var $menu=$("#left_menu_tree");
	if($menu.length!=1)return ;
	$.ajax({
		url:"menu/mymenus.do",
		cache: false,
		dataType:"json",
		success:function(json){
			MUI.ajaxDone(json);
			if(json.statusCode != MUI.statusCode.ok)return false;	
			var setting = {
					view: {
						dblClickExpand: false
					},
					data: {
						simpleData: {
							enable: true
						}
					},
					callback: {
						onClick: zTreeClick
					}
			};
			var zNodes = new Array();
			$.each(json.menus, function(i, m) {
				zNodes.push({id : m.id,name : m.name,
					pId : m.pid,icon:m.icon,
					href:m.url,dwzTarget:m.target,
					rel:m.rel,external:m.external,fresh:m.fresh,
					open:m.open,clickOpen:true});
				
			});

			$.fn.zTree.init($menu, setting, zNodes);
			//一些基本用户信息
			loginUserId=json.userId;
			loginName=json.userName;
			loginDeptId=json.deptId;
			loginDeptName=json.deptName;
		}
	});
		
}

//退出系统
function goOutSystem(){
	
	$.messager.confirm('提示',"确定退出系统吗?", function(r){
           if (r){
				window.location.href="sy_login/out.do";
		   }
     });
	
}

