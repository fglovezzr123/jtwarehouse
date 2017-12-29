<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%--
	模块：系统管理--字典值管理
--%>
<div class="easyui-layout" fit="true" >
   <!-- 左侧-->
	<div region="west" title="字典类型" split="true" 	style="width:220px;" >
	
	   	<ul id="${param.rel }_leftTree" class="ztree"></ul>
		
	</div>
  	
	<div  region="center" border="false">
		<%@ include file="/WEB-INF/jsp/system/list_values/query.jsp"%>
	</div>
   
</div>

<script type="text/javascript">
<!--
	$(function(){
		
		var setting = {
				view: {
					showIcon: false
				},
				callback: {
					onClick: updateListType
				}
		};
		var rel='<%= request.getParameter("rel") %>_datagrid';
	
		var zNodes =[
		     /* {name:"个人办公",open:false,
		    		children:[
							 {name:"通讯录分组",param : {"listType":4},datagrid:rel}
							,{name:"日程类型",param : {"listType":6},datagrid:rel}
		    		
				    ]
		    	 
		     },
		 	{name:"协同办公",open:false,
		 		children:[
		 				 {name:"通知类型",param : {"listType":1},datagrid:rel}	
		 				,{name:"日志类型",param : {"listType":5},datagrid:rel}	
		 				,{name:"工作计划类型",param : {"listType":60},datagrid:rel}
		 		]
		 				
		 	},
		 	{name:"公共平台",open:false,
		 		children:[
						{name:"会议类型",param : {"listType":2},datagrid:rel}
					   ,{name:"新闻类型",param : {"listType":3},datagrid:rel}
					   ,{name:"公告类型",param : {"listType":7},datagrid:rel}
					   ,{name:"规章类型",param : {"listType":8},datagrid:rel}
					   ,{name:"法规类型",param : {"listType":9},datagrid:rel}	
		 		]
		 		
		 	},
		 	{name:"行政办公",open:false,
		 		children:[
						 {name:"车辆类型",param : {"listType":10},datagrid:rel}
						,{name:"办公用品类型",param : {"listType":16},datagrid:rel}
		 		]
		 		
		 	},
		 	{name:"工作流程",open:false,
		 		children:[
						 {name:"工作流程类型",param : {"listType":17},datagrid:rel}
						
		 		]
		 		
		 	},
		 	{name:"人事管理",open:false,
		 		children:[
						 {name:"员工类型",param : {"listType":11},datagrid:rel}
						,{name:"学历",param : {"listType":13},datagrid:rel}
						,{name:"离职原因",param : {"listType":14},datagrid:rel}
						,{name:"退休原因",param : {"listType":15},datagrid:rel}
						,{name:"值班类型",param : {"listType":21},datagrid:rel}
		 		]
		 		
		 	},
		 	{name:"CRM",open:false,
		 		children:[
						 {name:"合同类型",param : {"listType":701},datagrid:rel}
						,{name:"合同状态",param : {"listType":702},datagrid:rel}
						,{name:"客户类型",param : {"listType":401},datagrid:rel}
						,{name:"客户来源",param : {"listType":402},datagrid:rel}
						,{name:"客户等级",param : {"listType":403},datagrid:rel}
						,{name:"客户状态",param : {"listType":404},datagrid:rel}
						,{name:"客户阶段",param : {"listType":405},datagrid:rel}
						,{name:"客户关系",param : {"listType":406},datagrid:rel}
						,{name:"所属行业",param : {"listType":407},datagrid:rel}
						,{name:"客户类别",param : {"listType":408},datagrid:rel}
						,{name:"联系人种类",param : {"listType":501},datagrid:rel}
						,{name:"联系人类型",param : {"listType":502},datagrid:rel}
						,{name:"机会来源",param : {"listType":601},datagrid:rel}
						,{name:"机会状态",param : {"listType":602},datagrid:rel}
						,{name:"活动方式",param : {"listType":603},datagrid:rel}
		 		]
		 		
		 	},
		 	{name:"项目管理",open:false,
		 		children:[
						 {name:"项目类型",param : {"listType":331},datagrid:rel}
						,{name:"重要程度",param : {"listType":332},datagrid:rel}
						
		 		]
		 		
		 	}, */
		 	{name:"设置",open:false,
		 		children:[
						 {name:"首页热推资讯设置",param : {"listType":801},datagrid:rel},
						 {name:"栏目类别",param : {"listType":802},datagrid:rel},
						 {name:"静态页",param : {"listType":803},datagrid:rel}/*,
						 {name:"J币比例",param : {"listType":777},datagrid:rel} ,
						 {name:"敏感词",param : {"listType":805},datagrid:rel} */
		 		]
		 		
		 	},
		 	{name:"天九云",open:false,
		 		children:[
						 {name:"职位",param : {"listType":12},datagrid:rel},
						 {name:"工作",param : {"listType":8000},datagrid:rel},
						 {name:"行业",param : {"listType":8001},datagrid:rel},
						 {name:"爱好",param : {"listType":8002},datagrid:rel},
						 {name:"好友印象",param : {"listType":8003},datagrid:rel},
						 {name:"我需要",param : {"listType":8004},datagrid:rel},
						 {name:"我能提供",param : {"listType":8005},datagrid:rel},
						 {name:"项目联营",param : {"listType":8006001},datagrid:rel},
			           	 {name:"项目孵化",param : {"listType":8006002},datagrid:rel},
			           	 {name:"项目征集",param : {"listType":8006003},datagrid:rel},
			           	 {name:"咨询类型",param : {"listType":8006004},datagrid:rel},
			           	 {name:"对公账号",param : {"listType":8007},datagrid:rel},
					     {name:"合作模式",param : {"listType":8008},datagrid:rel},
		 		]
		 		
		 	},
		 	{name:"搜索热词",open:false,
		 		children:[
						/*  {name:"文库标签",param : {"listType":9000},datagrid:rel}, */
						 {name:"投资分类",param : {"listType":9001},datagrid:rel},
						 {name:"活动搜索热词",param : {"listType":9002},datagrid:rel},
						 {name:"首页搜索热词",param : {"listType":9003},datagrid:rel},
						 {name:"资讯搜索热词",param : {"listType":9004},datagrid:rel},
						 {name:"项目联营搜索热词",param : {"listType":9005},datagrid:rel},
						 {name:"合作洽谈搜索热词",param : {"listType":9006},datagrid:rel},
						 {name:"话题PK搜索热词",param : {"listType":9007},datagrid:rel}
		 		]
		 		
		 	}
		 		      
		 ];
		
		$.fn.zTree.init($("#<%= request.getParameter("rel") %>_leftTree"), setting, zNodes);
				
		
	});
	function updateListType(event,treeId, treeNodeJSON){
		$("#cleanAll").click();
		if(treeNodeJSON.param){
			$('#<%= request.getParameter("rel") %>_listType').val(treeNodeJSON.param.listType);
			treeNodeJSON.updateTitle=treeNodeJSON.name;
			refreshDatagrid(event,treeId, treeNodeJSON);
		}
	}
//-->
</script>


	

