<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>

<c:choose>
	<c:when test="${dto==null}">
		<c:set value="add" var="operateMethod"></c:set>
	</c:when>
	<c:when test="${dto.id==null or dto.id eq ''}">
		<c:set value="add" var="operateMethod"></c:set>
	</c:when>
	<c:otherwise>
		<c:set value="update" var="operateMethod"></c:set>
	</c:otherwise>
</c:choose>

<div style="width: 95%; margin: 0 auto;">
	<form action="messagebulk/${operateMethod}.do" id="messagebulk" method="post" enctype="multipart/form-data" onsubmit="return validateSubmitForm(this)">
		<table class="table table-bordered ">
			
			
			<tr>
				<th width="15%">
					推送类型：
				</th>
				<td width="85%">
					<select class="combox required" class="easyui-validatebox" required="true" id="msgType" name="msgType" sValue="${dto.msgType }">
							<option value="1">手机短信</option>
							 <option value="2">微信</option> 
							 <option value="3">系统消息</option> 
					</select>
				</td>
			</tr>
			<tr id="template" style="display: none">
				<th>
					短信消息模板：<br>
				    例：SMS_79060093
				</th>
				<td >
					<textarea  name="templateId" rows="1" cols="100" style="width: 98%;" placeholder ="SMS_79060093"><c:out value="${dto.templateId}"/></textarea>
				</td>
			</tr>
			<tr>
				<th colspan="2"  style="text-align: center;">
					消息内容：<br>
				   短信消息例： { name: "天九云" }  &nbsp;&nbsp; <font  color="red"> 注：  短信消息实体是在消息模块内定义的所以 短信消息在这里只传参数!</font><br>
				   微信和系统消息例：hello!天九云         &nbsp;&nbsp;  <font  color="red">注： 微信和系统消息直接输入要发送消息的内容!</font> <br>
				</th>
			
			</tr>
			<tr>
				
				<td  colspan="2">
					<textarea  name="content" id="content" rows="6" cols="100" style="width: 98%;" data-options="validType:['length[1,200]']" maxlength="200"><c:out value="${dto.content}"/></textarea>
				</td>
			</tr>
			<tr>
				<th>
					推送用户：
				</th>
				<td>
					<select class="combox required" class="easyui-validatebox" required="true" id="sendType"  name="sendType" sValue="${dto.sendType }">
							<option value="0">全部用户</option>
							<option value="1">认证用户</option>
							<option value="2">注册用户</option>
							<!-- <option value="3">企服云用户</option> -->
							<option value="4">自定义用户</option>
					</select>
				</td>
			</tr>
			<tr id="to_user_ids" style="display: none">
				<th>
					推送用户：
				</th>
				<td>
					<a href="user/lookUpPage2.do?type=2&rel=select_user" lookupGroup="user" title="用户查询">
						<textarea  id="names" readonly="readonly"  name="names"  toName="user.userName"   value="${dto.names}  rows="2" style="width: 400px"></textarea>
					</a>
					<input type="hidden" id="toUserIds" name="toUserIds" toName="user.id"   value="${dto.toUserIds}"  />
					<input type="hidden" id="mobiles"  name="mobiles" toName="user.mobiles"   value="${dto.mobiles}"  />
                    
					
					<a class="easyui-linkbutton clearLookup"  icon="icon-cancel" 	plain="true"  href="javascript:;"  clearLookup="user"  title="清空"></a>
				</td>
			</tr>
			
		
			<input type="hidden" name="datagridId" value="${param.rel }_datagrid" />
			<input type="hidden" name="currentCallback" value="close" />
			
			
				
				<c:if test="${dto.id!=null}">
					<input type="hidden" name="id" value="${dto.id}" />
					<input type="hidden" name="createTime" value="<fmt:formatDate  value="${dto.createTime}" pattern="yyyy-MM-dd hh:mm:ss" />"/>
					<input type="hidden" name="createUserId" value="${dto.createUserId}" />
					<input type="hidden" name="createUserName" value="${dto.createUserName}" />
					<input type="hidden" name="updateUserId" value="${dto.updateUserId}" />	
					<input type="hidden" name="updateTime" value="<fmt:formatDate  value="${dto.updateTime}" pattern="yyyy-MM-dd hh:mm:ss" />"/>			
				</c:if>
					
			
			
			
			
			<tr>
				<th>
				</th>
				<td colspan="3">
					<div style="margin-top: 10px; margin-bottom: 10px;">
						<button type="button"  class="btn btn-primary"  onclick="messageBulk_save()">
							保存
						</button>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="btn clear">
							清空
						</button>
					</div>
				</td>
			</tr>
		</table>
	</form>
</div>
<script type="text/javascript">
$(document).ready(function(){
  $("#msgType").change(function(){
	  var selectVal = $(this).val();
	  if(selectVal==1){
		  $("#template").show();
	  }else{
		  $("#template").hide();
	  }
    
  });
  $("#sendType").change(function(){
	  var selectVal = $(this).val();
	  if(selectVal==4){
		  $("#to_user_ids").show();
	  }else{
		  $("#names").val("");
		  $("#toUserIds").val("");
		  $("#mobiles").val("");
		  $("#to_user_ids").hide();
	  }
    
  });
  
  
});


function messageBulk_save(){

	var	content = $("#content").val();
	
	
	if($.trim(content).length <= 0){
		Msg.alert("提示","消息内容不能为空！","warning",null);
		return ;
	}else{
		if(!validateSubmitForm($('#messagebulk'))){
			return;
		}
	}

	
}
</script>