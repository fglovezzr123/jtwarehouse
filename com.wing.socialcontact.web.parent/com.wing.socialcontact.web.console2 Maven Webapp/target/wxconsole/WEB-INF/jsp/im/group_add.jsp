<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理-- 聚合页面管理--添加
--%>

<div style="width: 98%;margin: 0 auto;" >
	<form  action="im/add.do"  method="post" id="groupInfo_form" enctype="multipart/form-data" >
		<table class="table table-bordered">
		  
		   <tr>
				<th style="width: 15%">群名称：</th>
		    	<td style="width: 85%">
		    		<input type="text" name="groupName" class="easyui-validatebox" required="true" maxlength="20" />
		    	</td>
		  	</tr>
		   <tr>
				<th style="width: 15%">群权重：</th>
		    	<td style="width: 85%">
		    		<input type="text" name="groupPower" class="easyui-validatebox" required="true" maxlength="100" onkeyup="clearNoNum2(this);"   value="999"/>
		    	</td>
		  	</tr>
			<tr >
		    	<th>群标签：</th>
		    	<td>
		    		<c:forEach items="${favs }" var="fav">
		    			<input type="checkbox" value="${fav.id }" v_text="${fav.listValue }" name="favs" style="margin-top: -2px;"/><span style="margin-right:20px;margin-left: 2px;">${fav.listValue }</span>
		    		</c:forEach>
               	</td>
			</tr>
			 <!--  <tr>
				<th>群主：</th>
		    	<td>
		    		
		    		<a   href="user/lookUpPage2.do?type=1&rel=select_user" lookupGroup="user" title="用户查询">
						<input type="text" name="creator_name" eadonly="readonly" class="easyui-validatebox" required="true" maxlength="100"  toName="user.userName" />
					</a>
					<input type="hidden"  id="creator" name="creator" toName="user.id"    callback="_back"   />
					<a class="easyui-linkbutton clearLookup"  icon="icon-cancel" 	plain="true"  href="javascript:;"  clearLookup="user"  title="清空"></a>
		    	</td>
		  	</tr> -->
			  <tr>
				<th>群主(手机号)：</th>
		    	<td>
		    		<input type="text" id="mobile1"  name="mobile" style="width:95%;"/>
					<!-- <input type="hidden"  id="creator" name="creator"   /> -->
		    	</td>
		   </tr>
		
		  	</tr>
		  	
		  <!-- <tr id="to_user_ids" >
				<th>
					群成员：
				</th>
				<td>
					<a id="users"  href="#" lookupGroup="groupUsers" title="用户查询">
						<textarea  readonly="readonly"  name="names"  toName="groupUsers.userName"    rows="2" style="width: 400px"></textarea>
					</a>
					<input type="hidden"  name="users" toName="groupUsers.id"  />
					<a class="easyui-linkbutton clearLookup"  icon="icon-cancel" 	plain="true"  href="javascript:;"  clearLookup="groupUsers"  title="清空"></a>
				</td>
			</tr> -->	
		  	
		 <tr id="to_user_ids" >
				<th>
					群成员(手机号,多个手机号逗号隔开)：
				</th>
				<td>
					<textarea name="users"  id="users"  rows="2" style="width:95%;" required="true"></textarea>
				</td>
			</tr> 
			<tr >
		    	<th>私密群：</th>
		    	<td>
               		<input type="radio" name="groupType" value="1" style="margin-top: -2px;" checked="checked"/><span style="margin-right:20px;margin-left: 2px;">非私密群</span>
               		<input type="radio" name="groupType" value="2" style="margin-top: -2px;"/><span style="margin-left: 2px;">私密群</span>
               	</td>
			</tr>
		  	<tr>
				<th></th>
				<td>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
					  	<button type="button" class="btn btn-primary"  onclick="save()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
					  	<!-- <button type="button" class="btn clear" >清空</button> -->
					</div>
			 	</td>
		  	</tr>
		</table>
		<input type="hidden" name="regUserFlag" value="1"/>
		<input type="hidden" name="adType" value="1"/>
		<input type="hidden" name="status" value="0" />
		<input type="hidden" name="datagridId" value="${param.rel }_datagrid" />
		<input type="hidden" name="currentCallback" value="close" />
	</form>
	<span class="typefile" id="filePicker"></span>
</div>

<script type="text/javascript">
var _back=function(){
	var creator=$("#creator").val();
	$("#users").prop("href","<%=request.getContextPath()%>/user/lookUpPage2.do?type=3&rel=select_users&userId="+creator);
}

function save(){
	
	var favsName="";
	$("input[name='favs']:checked").each(function(i){
		if(i > 0){
			favsName+=",";
		}
		favsName+=$(this).attr("v_text");
	});
	
	if(isEmpty(favsName)){
		Msg.alert("提示","群标签至少选一个","warning");
		return;
	}
	
	if($("#mobile1").val() == ""){
		alert("手机号不能为空!");
		flag = true
		return;
	}
	
	var _users=$("#users").val();
	if(isEmpty(_users)){
		Msg.alert("提示","群成员不能为空!");
		return;
	}
	
	var isPhone=/^((\+?86)|(\+86))?(13[012356789][0-9]{8}|15[012356789][0-9]{8}|17[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/; 
	
	if(isPhone.test($("#mobile1").val())){
		 $.ajax({
				url:"im/checkMobile.do",
				type: 'post',	
				data: {mobile:$("#mobile1").val()},
				cache: false,
				dataType:"json",
				success:function(json){
					if(json&&json["code"]==="0"){
						var obj = json.dataobj;
						if(obj!=null){
							if(!validateSubmitForm($('#groupInfo_form'))){
								return;
							}
						}else{
							alert("手机用户不存在，请检查！");
						}
					}else{
						alert("操作失败");
					}
					flag = true
				}
			});
		 flag = true
	}else{
		alert("手机号格式不正确!");
		flag = true
		return;
	}
	
	
}
</script>
