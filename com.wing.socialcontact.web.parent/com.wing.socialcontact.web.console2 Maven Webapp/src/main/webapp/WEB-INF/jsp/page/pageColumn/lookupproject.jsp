<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<div id="projectindex_toolbar" class="search-div">
	<div class="search-content">
		<span>
			<label style="width: 80px;">项目名称：</label>
			<input type="text" id="titles" style="width:120px;"/>
		</span>
		<span>
			<label style="width: 80px;">项目类别：</label>
			<select id="prjType" style="width:120px;">
				<option value="">全部</option>
				<option value="8006001">项目联营</option>
				<option value="8006002">项目孵化</option>
			</select>
		</span>
		<span>
			<label style="width: 80px;">是否显示：</label>
			<select id="showEnable" style="width:70px;">
				<option value="">全部</option>
				<option value="1">是</option>
				<option value="0">否</option>
			</select>
		</span>
		<span>
			<label style="width: 100px;">创建时间：</label>
			<input id="createTime" style="width:90px;" size=15 class="Wdate time-field" 
				onfocus="WdatePicker({readOnly:true})"
				onClick="WdatePicker({readOnly:true})" 
				readonly="readonly"/>
		</span>
		<span style="padding-left:20px;">
			<button class="btn btn-primary btn-small" type="button" onclick="loadProject()">查询</button>&nbsp;
		</span>
	</div>
</div>
<table id="projectindex_datagrid" toolbar="#projectindex_toolbar"></table>
<script type="text/javascript">	
$(function() {
	loadProject();			
});
function loadProject(){
	$("#projectindex_datagrid").datagrid({
		url : "project/projectquery.do",
		rownumbers: true,
		queryParams: { 
			titles: $("#titles").val(),
			prjType: $("#prjType").val(),
			showEnable: $("#showEnable").val(),
			createTime: $("#createTime").val()
		},
		fitColumns: false,
		columns : [[
		            
			{
				field : "选择",
				title : "选择",
				align : "center",
				width : $(this).width() * 0.05,
				formatter : function(value, row, index) {
					return "<a href='javascript:;' onclick=$.bringBack({id:'" + row.id + "',name:'" + row.titles + "'})>选择</a>";
				}
			},
			 {
				field : "titles",
				title : "项目名称",
				width : 400,
				align : "left",
			},{
				field : "prjType",
				title : "项目类别",
				width : 60,
				align : "center",
				formatter : function(value, row, index) {
					return 8006001==value?"项目联营":"项目孵化";
				}
			},{
				field : "statusDesc",
				title : "项目状态",
				width : 60,
				align : "center"
			},
			{
				field : "sort",
				title : "权重",
				width : 60,
				align : "center"
			},
			{
				field : "isRecommend",
				title : "是否推荐",
				width : 60,
				align : "center",
				formatter : function(value, row, index) {
					return 1==value?"推荐":"不推荐";
				}
			},
			{
				field : "showEnable",
				title : "是否显示",
				width : 60,
				align : "center",
				formatter : function(value, row, index) {
					return 1==value?"是":"否";
				}
			},
			{
				field : "createTime",
				title : "创建时间",
				width : 120,
				align : "center",
				formatter : function(value, row, index) {
					return value==null?"":new Date(value).format("yyyy-MM-dd HH:mm");
				}
			},
			{
				field : "op",
				title : "操作",
				align:"center",
				width : 150,
				formatter: function(value, row, index){
					return   "<a href = 'javaScript:void(0)' onclick = detailProject('"+row.id+"')>查看</a>";
				}
			}
		]],
	});
}
//发布
function addProject(fid){
	var params = {closed: false,cache: false,modal:true,width:1000,height:600,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('发布项目','project/projectaddpage.do?id=',"projectaddpage",params)
}
//报名用户
function showSignup(fid){
	var params = { closed: false,cache: false,modal:true,width:1000,height:600,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('咨询用户','project/signupindex.do?prjId='+fid,"projectsinguppage",params)
}
//编辑
function updateProject(fid){
	var params = { closed: false,cache: false,modal:true,width:1000,height:600,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('编辑项目','project/projectupdatepage.do?id='+fid,"projectupdatepage",params)
}
//删除
function deleteProject(fid){
	confirmx("确定删除吗？",function(s){
		if(s==1){
			$.ajax({
				url:"project/projectdel.do",
				type: 'post',	
				data: {id:fid},
				cache: false,
				dataType:"json",
				success:function(json){
					if(json&&json["code"]==="0"){
						$("#projectindex_datagrid").datagrid('reload');
					}else{
						alert("操作失败");
					}			
				}
			});
		}
	})
}
//明细
function detailProject(fid){
	var params = { closed: false,cache: false,modal:true,width:1000,height:600,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('项目明细','project/projectdetail.do?id='+fid,"projectdetailpage",params)
}
</script>