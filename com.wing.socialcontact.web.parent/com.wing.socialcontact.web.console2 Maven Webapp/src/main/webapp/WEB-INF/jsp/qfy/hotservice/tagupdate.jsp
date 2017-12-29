<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<%--
	模块：
--%>

	

<div style="height: 400px;width: 380px;float:left; " >	
<form  action="qfy/hotservice/update.do"  id="hotservice_form"  method="post"   onsubmit="return validateSubmitForm(this)">
	    <input id="imgPath" name="imgPath" value="${dto.imgPath}"  type="hidden" />
		<div class="divider"></div>
		  <table class="table table-nobordered " style="margin-top: 25px;">
			  <tr>
			    	<th style="width: 120px">标签名称：</th>
			    	<td>
			     		<input type="text" name="name" class="easyui-validatebox" value="${dto.name}" readonly="readonly"  />
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 120px">热门服务序号：</th>
			    	<td>
			     		<input type="text" name="hotSort" class="easyui-numberbox" value="${dto.hotSort}"   />
			    	</td>
			  </tr>
			  <tr>
				 <th></th>
				 <td>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
						  <button type="button" class="btn btn-primary"  onclick="tijiao()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
						  <!-- <button type="button" class="btn clear" >清空</button> -->
					</div>
				 </td>
			  </tr>
		  </table>
		  <input type="hidden" name="id" value="${dto.id}"/>
		  <input type="hidden" name="hotEntryId"  id="entryId" />
		  <input type="hidden" name="datagridId" value="${param.rel }_datagrid" />
		  <input type="hidden" name="currentCallback" value="close" />
	</form>				
</div>	
<div style="height: 400px; width: 760px;float: left;">
<div id="entry_prise_load_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'entry_prise_load_datagrid');" >
				<div class="search-content">
				<span>
					<label style="width: 100px;">企业名称：</label>
					<input type="text" name="name"/>
				</span>
				<span>
					<label style="width: 100px;">联盟标题：</label>
					<input type="text" name="title"/>
				</span>
				<span>
					<label style="width: 100px;">所属用户：</label>
					<input type="text" name="ssUserId"/>
				</span>
				<span>
					<label style="width: 100px;">创建时间：</label>
					<input type="text"   name="createTime"   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 150px;"/>
				</span>
				</div>
				<div class="search-toolbar">
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
						<button class="btn btn-small clear" type="button">清空</button>&nbsp;
					</span>
		
				</div>
			</form>
		</div>

<table id="entry_prise_load_datagrid"   toolbar="entry_prise_load_toolbar"></table>

</div>

<script type="text/javascript" >
$(function() {
	loadMain();			
});

function loadMain(){
	$('#entry_prise_load_datagrid').datagrid(
		{
			url : "qfy/entry/query.do", 
			columns : [ [
					{
							field : "name",
							title : "企业名称",
							width : $(this).width() * 0.15,
							align : "center"
					},
					{
							field : "title",
							title : "联盟标题",
							width : $(this).width() * 0.15,
							align : "center"
					},
					{
							field : "serviceCount",
							title : "服务数量",
							width : $(this).width() * 0.15,
							align : "center"
					},
					{
						field : "ssUserId",
						title : "所属用户ID",
						width : $(this).width() * 0.15,
						align : "center"
					}
			] ],
			
		});
}
	function tijiao(){
		
		var row = $('#entry_prise_load_datagrid').datagrid('getSelected');
		if(row==null){
			Msg.alert("提示","请选择一条记录！","warning",null);
		}else{
			$("#entryId").val(row.id);
			$("#hotservice_form").submit();
		}
	}
//-->
</script>
