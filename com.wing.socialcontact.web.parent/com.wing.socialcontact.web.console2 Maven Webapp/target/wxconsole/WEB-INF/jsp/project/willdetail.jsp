<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<style>
* { margin:0; padding:0;}
.hyjb_box {}
.hyjb_dl {float:left; margin: 12px 12px; width:125px; list-style:none; }
.hyjb_dl dl { position:relative; width:125px; height:105px; }
.hyjb_dl dl dt { width:120px; height:100px; border:1px solid #dddddd;}
.hyjb_dl dl dd { position:absolute; right:-10px; top:-10px; }
.hyjb_dl .hyjb_shuru { width:125px; margin-top:-22px;}
.hyjb_dl .hyjb_shuru input { border:none; width:110px; height:26px; background:#f7f7f7; text-align:center;}
.hyjb_tj { float:left; margin:5px 0px 0 5px; }
#dataForm img{width:100%;height:100%;}
</style>

<div style="width:100%;">						
<form method="post" action="" id="willDetailForm">
	<table class="table table-bordered" style="margin-top:0px;width:99%;">
		<tr>
			<th style="width:15%;">项目名称：</th>
			<td>${obj.titles2}</td>
		</tr>
		<tr>
			<th>咨询类型：</th>
			<td>${obj.willTypeName}	</td>
		</tr>
		<tr>
			<th>意向说明：</th>
			<td>${obj.willDesc}</td>
		</tr>
		<tr>
			<th>姓名：</th>
			<td>${obj.userName}</td>
		</tr>
		<tr>
			<th>手机号码：</th>
			<td>${obj.mobile}</td>
		</tr>
		<tr>
			<th>所属公司：</th>
			<td>${obj.comName}</td>
		</tr>
		<tr>
			<th>咨询时间：</th>
			<td>${fns:fmt(obj.createTime,'yyyy-MM-dd HH:mm') }</td>
		</tr>
		<tr>
			<th>是否处理：</th>
			<td>
				<c:choose>
					<c:when test="${0 eq obj.status}">
						<input type="radio" name="status" id="status0" value="0" checked> 未处理
						<input type="radio" name="status" id="status1" value="1">已处理
					</c:when>
					<c:when test="${1 eq obj.status}">
						<input type="radio" name="status" id="status0" value="0"> 未处理
						<input type="radio" name="status" id="status1" value="1" checked>已处理
					</c:when>
					<c:otherwise>
						<input type="radio" name="status" id="status0" value="0" checked> 未处理
						<input type="radio" name="status" id="status1" value="1">已处理
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<th></th>
			<td colspan=5>
				<div  style="margin-top: 10px;margin-bottom: 10px;">
					  <button type="button" onclick="save()" class="btn btn-primary" >提交</button>&nbsp;&nbsp;&nbsp;&nbsp;
					  <button type="button" onclick="cancel()" class="btn clear" >返回</button>
				</div>
			</td>
		 </tr>
	</table>
</form>
</div>
<script>
function save(){
	var params = {
		id: "${obj.id}",
		status : $('#willDetailForm input:radio[name="status"]:checked').val()
	};
    $.ajax({
		url:"project/signup/update.do",
		type: 'post',	
		data: params,
		cache: false,
		dataType:"json",
		success:function(json){
			if(json&&json["code"]==="0"){
				try{
					$("#projectwill_datagrid").datagrid('reload');
					$("#willdetailpage").dialog("destroy");
				}catch(e){}
				try{
					$("#projectsignup_datagrid").datagrid('reload');
					$("#willdetailpage").dialog("destroy");
				}catch(e){}
			}else{
				alert("操作失败");
			}			
		}
	});
}
function cancel(){
	$("#willdetailpage").dialog("destroy");
}
</script>