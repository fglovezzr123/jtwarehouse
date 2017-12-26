<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ taglib uri="/WEB-INF/tag/myTag.tld" prefix="my" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理
--%>
<div style="width: 600px;margin: 0 auto;" >
	<form  action="topic/update.do"   method="post" id="news_form"  onsubmit="return validateSubmitForm(this)">
		 <input id="createTime" name="createTime" value="<fmt:formatDate  value='${t.createTime}' pattern='yyyy-MM-dd HH:mm:ss' />"  type="hidden" />
		 <input id="createUserName" name="createUserName" value="${t.createUserName}"  type="hidden" />
		 <input id="createUserId" name="createUserId" value="${t.createUserId}"  type="hidden" />
		 <input id="redCount" name="redCount" value="${t.redCount}"  type="hidden" />
		 <input id="blueCount" name="blueCount" value="${t.blueCount}"  type="hidden" />
		 <input id="isAd" name="isAd" value="${t.isAd}"  type="hidden" />
		  
		 <table class="table table-nobordered " style="margin-top: 25px;">
			 <tr>
			    	<th style="width: 100px">发布人：</th>
			    	<td >
			     		<div id="fbr"></div>
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">创建时间：</th>
			    	<td >
			     		<div id="cjsj"></div>
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">话题PK：</th>
			    	<td >
			     		<input type="text" name="topicDesc" id="topicDesc" readonly="readonly" value="${t.topicDesc}" class="easyui-validatebox" required="true"  data-options="validType:['length[1,100]']"   maxlength="100" style="width: 400px;" placeholder="抛出本次PK话题，不超过100个汉字"/>
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">红方观点：</th>
			    	<td >
			     		<input type="text" name="redPoint" id="redPoint"  readonly="readonly" value="${t.redPoint}" class="easyui-validatebox" required="true"  data-options="validType:['length[1,50]']"   maxlength="50" style="width: 400px;" placeholder="不超过50个汉字"/>
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">蓝方观点：</th>
			    	<td >
			     		<input type="text" name="bluePoint" id="bluePoint"  readonly="readonly" value="${t.bluePoint}" class="easyui-validatebox" required="true"  data-options="validType:['length[1,50]']"   maxlength="50" style="width: 400px;" placeholder="不超过50个汉字"/>
			    	</td>
			  </tr>
			    <tr>
			    	<th style="width: 100px">话题阐述：</th>
			    	<td >
			     	 <textarea  name="topicExplain" id="topicExplain" readonly="readonly" rows="5" maxlength="500" style="width:400px">${t.topicExplain}</textarea>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">权重：</th>
			    	<td>
			     	<input type="text" name="sort" readonly="readonly" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" class="easyui-numberbox" required="true" maxlength="8" min="0" max="99999999" value="${t.sort}"/>
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">是否允许评价：</th>
			    	<td >
			     		<select name="allowComment" id="allowComment" style="width: 120px;" readonly="readonly">
						<option  value="1" <c:if test="${t.allowComment==1}">selected</c:if>>允许</option>
						<option  value="2"  <c:if test="${t.allowComment==2}">selected</c:if>>不允许</option>
					</select>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">仅好友可见：</th>
			    	<td >
			     		<select name="isShow" id="isShow" style="width: 120px;" readonly="readonly">
						<option  value="1" <c:if test="${t.isShow==1}">selected</c:if>>是</option>
						<option  value="2"  <c:if test="${t.isShow==2}">selected</c:if>>否</option>
					</select>
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">是否推荐：</th>
			    	<td >
                		<select name="isRecommend" id="isRecommend" readonly="readonly">
						<option  value="1" <c:if test="${t.isRecommend==1}">selected</c:if>>推荐</option>
						<option  value="2"  <c:if test="${t.isRecommend==2}">selected</c:if>>不推荐</option>
					    </select>   	
                	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">话题状态：</th>
			    	<td >
                		<select name="status" id="status" readonly="readonly">
						<option  value="1" <c:if test="${t.status==1}">selected</c:if>>显示</option>
						<option  value="2"  <c:if test="${t.status==2}">selected</c:if>>不显示</option>
					    </select>   	
                	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">备注：</th>
			    	<td >
			     	 <textarea  name="remark" id="remark" rows="3" maxlength="50" readonly="readonly" style="width:400px">${t.remark}</textarea>
			    	</td>
			  </tr>
			  
		</table>	 
		  
		  <input type="hidden" name="id" value="${t.id }"/>
		  <input type="hidden" name="currentCallback" value="close" />
		  <input type="hidden" name="datagridId" value="${param.rel }_datagrid" />	
		   
	</form>
</div>

<script type="text/javascript">	
$(function() {
//	var cj = new Date($("#createTime").val()).format("yyyy-MM-dd HH:mm:ss");
	var createUserName = $("#createUserName").val();
	if(createUserName!=null&&createUserName!=""){
		$("#fbr").html(createUserName);
	}else{
		$("#fbr").html("管理员");
	}
	$("#cjsj").html($("#createTime").val());
});
function save(){
	var flag = true;
	if(flag){
		if(!validateSubmitForm($('#news_form'))){
			return;
		}
	}
}
function clear1(){
	$("#topicDesc").val('${t.topicDesc}');
	$("#redPoint").val('${t.redPoint}');
	$("#bluePoint").val('${t.bluePoint}');
	$("#isShow option[value=" + '${t.isShow}' + "]").attr("selected", true); 
	$("#isRecommend option[value=" + '${t.isRecommend}' + "]").attr("selected", true); 
	$("#allowComment option[value=" + '${t.allowComment}' + "]").attr("selected", true); 
	$("#status option[value=" + '${t.status}' + "]").attr("selected", true); 
	
	$("#remark").val('${t.remark}');
}

</script>
