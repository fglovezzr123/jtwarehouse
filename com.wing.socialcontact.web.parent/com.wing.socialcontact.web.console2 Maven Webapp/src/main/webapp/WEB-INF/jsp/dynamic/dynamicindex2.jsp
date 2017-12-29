<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<div class="search-div" id="toolbar">
	<div class="search-content">
		<span>
			<label style="width: 100px;">广告内容：</label>
			<input type="text" id="dyContent"/>
		</span>
		<span>
			<label style="width: 100px;">发布人：</label>
			<input type="text" id="userName"/>
		</span>
		<span>
			<label style="width: 100px;">查看数：</label>
			<input type="text" id="visitQuantity"/>
		</span>
		<span>
			<label style="width: 100px;">发布时间：</label>
			<input id="issuedDate" style="width:90px;" size=15 class="Wdate time-field" 
				onfocus="WdatePicker({readOnly:true})"
				onClick="WdatePicker({readOnly:true})" 
				readonly="readonly"/>
			
		</span>
		<span style="padding-left:20px;">
			<button class="btn btn-primary btn-small" type="button" onclick="loadDynamic2()">查询</button>&nbsp;
		</span>
		<span>
			<button class="btn btn-primary btn-small" type="button" onclick="addDynamic2()">发布动态</button>&nbsp;
		</span>
	</div>
</div>
<table id="dynamicindex_datagrid" toolbar="#toolbar"></table>
<script type="text/javascript">	
$(function() {
	loadDynamic2();			
});
function loadDynamic2(){
	$("#dynamicindex_datagrid").datagrid({
		url : "dynamic/dynamicquery2.do",
		rownumbers: true,
		queryParams: { 
			dyContent: $("#dyContent").val(),
			userName: $("#userName").val(),
			visitQuantity: $("#visitQuantity").val(),
			issuedDate: $("#issuedDate").val()
		},
		columns : [[
			 {
				field : "dy_content",
				title : "广告内容",
				width : 180,
				align : "center"
			},{
				field : "nickname",
				title : "发布人",
				width : 60,
				align : "center"
			},{
				field : "mobile",
				title : "发布人电话",
				width : 60,
				align : "center"
			},{
				field : "visit_quantity",
				title : "查看数",
				width : 60,
				align : "center"
			},
			{
				field : "commentCount",
				title : "评论数",
				width : 60,
				align : "center"
			},
			{
				field : "lickCount",
				title : "点赞数",
				width : 60,
				align : "center"
			},
			{
				field : "is_stick",
				title : "是否置顶",
				width : 100,
				align : "center",
				formatter : function(value, row, index) {
					return value==1?"是":"否";
				}
			},
			{
				field : "issued_date",
				title : "发布时间",
				width : 100,
				align : "center",
				formatter : function(value, row, index) {
					return new Date(row.issued_date)
							.format("yyyy-MM-dd HH:mm");
				}
			},
			{
				field : "create_time",
				title : "创建时间",
				width : 100,
				align : "center",
				formatter : function(value, row, index) {
					return new Date(value)
							.format("yyyy-MM-dd HH:mm");
				}
			},
			{
				field : "op",
				title : "操作",
				align:"center",
				width : 120,
				formatter: function(value, row, index){
							var str = "<a href = 'javaScript:void(0)' onclick = detailMetting('"+row.id+"')>查看</a>&nbsp;|&nbsp;"
							+"<a href = 'javaScript:void(0)' onclick = deleteDynamic('"+row.id+"')>删除</a>&nbsp;|&nbsp;";
							if(row.is_stick==1){
								str +="<a href = 'javaScript:void(0)' onclick = updatetop('"+row.id+"',0)>取消置顶</a>";
							}else{
								str +="<a href = 'javaScript:void(0)' onclick = updatetop('"+row.id+"',1)>置顶</a>";
							}
							return str;
				}
			}
		]],
	});
}
//发布
function addDynamic2(){
	var params = { onClose:function(){  
        $("#dynamicaddpage").dialog('destroy');  
    }, closed: false,cache: false,modal:true,width:800,height:400,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('发布动态','dynamic/dynamicaddpage2.do',"dynamicaddpage",params)
}

//删除
function deleteDynamic(fid){
	confirmx("确定删除吗？",function(s){
		if(s==1){
			$.ajax({
				url:"dynamic/dynamicdel.do",
				type: 'post',	
				data: {id:fid},
				cache: false,
				dataType:"json",
				success:function(json){
					if(json&&json["code"]==="0"){
						$("#dynamicindex_datagrid").datagrid('reload');
					}else{
						alert("操作失败");
					}			
				}
			});
		}
	})
}
//修改置顶状态
function updatetop(fid,type){
	confirmx("确定修改置顶状态吗？",function(s){
		if(s==1){
			$.ajax({
				url:"dynamic/updatetop.do",
				type: 'post',	
				data: {id:fid,type:type},
				cache: false,
				dataType:"json",
				success:function(json){
					if(json&&json["code"]==="0"){
						$("#dynamicindex_datagrid").datagrid('reload');
					}else{
						alert("操作失败");
					}			
				}
			});
		}
	})
}
//明细
function detailMetting(fid){
	var params = { closed: false,cache: false,modal:true,width:1000,height:600,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('动态明细','dynamic/dynamicdetail2.do?id='+fid,"dynamicdetailpage",params);
}
</script>