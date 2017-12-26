<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<div class="search-div" id="signup_toolbar">
		<div class="search-content">
			<span>
				<label style="width: 100px;">用户姓名：</label>
				<input	type="text" name="nickname" id="nickname_sub" class="span2" onkeypress=""/>
			</span>
			<span>
				<label style="width: 100px;">职位：</label>
				<select name="job" id="job_sub" style="width: 120px;">
					<option value="">全部</option>
					<c:forEach var="c" items="${jobs}">
					<option  value="${c.id}">${c.listValue}</option>
					</c:forEach>	
				</select>
			</span>
			<span>
				<label style="width: 100px;">行业：</label>
				<select name="industry" id="industry_sub" style="width: 120px;">
				<option value="">全部</option>
				<c:forEach var="c" items="${industrys}">
				<option  value="${c.id}">${c.listValue}</option>
				</c:forEach>
				</select>
			</span>
			<span style="padding-left:20px;">
				<button class="btn btn-primary btn-small" type="button" onclick="serchData()">查询</button>&nbsp;
				<button class="btn btn-primary btn-small" type="button" onclick="sureDakaUser()">确定</button>&nbsp;
				<button class="btn btn-primary btn-small" type="button" onclick="closeDakaaddpage()">取消</button>&nbsp;
			</span>
		</div>
</div>
<table id="signup_datagrid" toolbar="#signup_toolbar"></table>
<script type="text/javascript">	
$(function() {
	loadMain();	
	$("#nickname_sub").keypress(function(event){
		if(event.keyCode == 13){
			serchData();
		}
		
		event.stopPropagation();
	});

});

function loadMain(){
	$("#signup_datagrid").datagrid({
		url : "daka/query.do?isDk=0", 
		rownumbers: true,
		queryParams: { 
			nickname: $("#nickname_sub").val(),
			job: $("#job_sub").val(),
			industry: $("#industry_sub").val()
		},
		columns : [[
	             {
				    	field:"ck",
				    	title : "勾选",
				    	checkbox:true
				 },
				 {
						field : "nickname",
						title : "用户姓名",
						width : $(this).width() * 0.15,
						align : "center"
					},
				 {
						field : "com_name",
						title : "公司名称",
						width : $(this).width() * 0.15,
						align : "center",
				},
				{
					field : "job_name",
					title : "职位",
					width : $(this).width() * 0.15,
					align : "center"
				},
				{
					field : "industry_name",
					title : "行业",
					width : $(this).width() * 0.15,
					align : "center"
				},
				{
					field : "province_name",
					title : "所在省",
					width : $(this).width() * 0.15,
					align : "center"
				},
				{
					field : "city_name",
					title : "所在市",
					width : $(this).width() * 0.15,
					align : "center"
				}
				
		]],
		
	});
}

function serchData(){
	$("#signup_datagrid").datagrid('reload',{
		nickname: $("#nickname_sub").val(),
		job: $("#job_sub").val(),
		industry: $("#industry_sub").val()
	});
}

function closeDakaaddpage(){
	$("#dakaaddpage").dialog("destroy");
}

function sureDakaUser(){
	var rows = $("#signup_datagrid").datagrid("getChecked");
	if(rows&&rows.length==0){
		alert("请先选择用户");
	}else{
		var ids=[];
		for(var i=0;i<rows.length;i++){
			var row = rows[i];
			ids.push(row.id);
		}
		var obj = {};
		obj.ids = ids;
		$.ajax({
			type : "post",
			timeout : "10000",
			url : "daka/addDaka.do",
			traditional: true,
			data:obj,
			async : false,
			dataType : "json",
			success: function(json){
				if(json&&json["statusCode"]==="200"){
					closeDakaaddpage();
					$("#main_subimt").click();
				}else{
					alert("操作失败");
				}
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) {
				bool = null;
			}
		});
		
	}
}
</script>