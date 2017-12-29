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
<form method="post" action="" id="collectDetailForm">
	<table class="table table-bordered" style="margin-top:0px;width:99%;">
		<tr>
			<th>企业名称：</th>
			<td>${obj.comName}</td>
		</tr>
		<tr>
			<th style="width:15%;">注册资本：</th>
			<td><fmt:formatNumber value="${obj.registeredCapital}" pattern="#.##" minFractionDigits="2" > </fmt:formatNumber>(万元)</td>
		</tr>
		<tr>
			<th>项目类型：</th>
			<td>${obj.prjTypeName}</td>
		</tr>
		<tr>
			<th style="width:15%;">项目名称：</th>
			<td>${obj.prjName}</td>
		</tr>
		<tr>
			<th>项目描述：</th>
			<td>${obj.prjDesc}</td>
		</tr>
		<tr>
			<th>项目介绍：</th>
			<td><c:out value="${obj.prjProfile}"></c:out></td>
		</tr>
		<tr>
			<th>项目图片：</th>
			<td>
				<div class="hyjb_box">
    				<div class="hyjb_tj">
   					<c:if test="${not empty obj}">
   						<c:forEach items="${obj.projectImages}" var="item">
    					<ul class="hyjb_dl">
							<li>
								<dl>
									<dt><a href="javascript:void(0)"><img  class="guestImg" src="${item.imageUrl}" style="max-height:100px;"/></a></dt>
								</dl>
							</li>
						</ul>
   						</c:forEach>
   					</c:if>
    				</div>
				</div>
			</td>
		</tr>
		<tr>
			<th style="width:120px;">提交时间：</th>
			<td style="width:120px;">${fns:fmt(obj.createTime,'yyyy-MM-dd HH:mm') }</td>
		</tr>
		<tr>
			<th style="width:120px;">提交人：</th>
			<td>${obj.userName}</td>
		</tr>
		<tr>
			<th style="width:120px;">联系方式：</th>
			<td>${obj.mobile }</td>
		</tr>
		<tr>
			<th>处理状态：</th>
			<td>
				<c:choose>
					<c:when test="${0 eq obj.status}">
						<input type="radio" name="status" id="status0" value="0" checked> 不通过
						<input type="radio" name="status" id="status1" value="1">通过
					</c:when>
					<c:when test="${1 eq obj.status}">
						<input type="radio" name="status" id="status0" value="0"> 不通过
						<input type="radio" name="status" id="status1" value="1" checked>通过
					</c:when>
					<c:otherwise>
						<input type="radio" name="status" id="status0" value="0"> 不通过
						<input type="radio" name="status" id="status1" value="1">通过
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<th>是否显示：</th>
			<td>
				<c:choose>
					<c:when test="${0 eq obj.showEnable}">
						<input type="radio" name="showEnable" id="showEnable0" value="0" checked> 不显示
						<input type="radio" name="showEnable" id="showEnable1" value="1">显示
					</c:when>
					<c:when test="${1 eq obj.showEnable}">
						<input type="radio" name="showEnable" id="showEnable0" value="0"> 不显示
						<input type="radio" name="showEnable" id="showEnable1" value="1" checked>显示
					</c:when>
					<c:otherwise>
						<input type="radio" name="showEnable" id="showEnable0" value="0" checked> 不显示
						<input type="radio" name="showEnable" id="showEnable1" value="1">显示
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
		status : $('#collectDetailForm input:radio[name="status"]:checked').val()||"",
		showEnable : $('#collectDetailForm input:radio[name="showEnable"]:checked').val()
	};
	if(params.status==""){
		alert("请先选择处理状态");
		return;
	}
	
    $.ajax({
		url:"project/collect/saveorupdate.do",
		type: 'post',	
		data: params,
		cache: false,
		dataType:"json",
		success:function(json){
			if(json&&json["code"]==="0"){
				$("#projectzjindex_datagrid").datagrid('reload');
				$("#collectdetailpage").dialog("destroy");
			}else{
				alert("操作失败");
			}			
		}
	});
}
function cancel(){
	$("#collectdetailpage").dialog("destroy");
}
</script>