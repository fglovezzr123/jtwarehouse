<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ taglib uri="/WEB-INF/tag/myTag.tld" prefix="my" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
  String bp = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"; 
%>    
<%--
	模块：系统管理
--%>
<div style="width: 800px;margin: 0 auto;" >
	<form  action="news/htmlupdate.do"   method="post"   onsubmit="return validateSubmitForm(this)" enctype="multipart/form-data">
		 <input id="createUserId" name="createUserId" value="${n.createUserId}"  type="hidden" />
		 <input id="createUserName" name="createUserName" value="${n.createUserName}"  type="hidden" />
		 <table class="table table-nobordered " style="margin-top: 25px;">
			  <tr>
			    	<th style="width: 90px">id：</th>
			    	<td>
			     		${n.id}
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 90px">访问地址：</th>
			    	<td>
			     		<div id="fwdz"></div>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 90px">内容详情：</th>
			    	<td>
			    	<textarea name="content" style="width:320px;height:600px;" id="content">${n.content}</textarea>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 90px">安卓下载链接：</th>
			    	<td>
			     		<input type="text" name="url" id="url" value="${n.url}" class="easyui-validatebox" required="true"  style="width:580px;"/>
			    	    <strong style="color:#F00">http:// 开头</strong>  
			    	</td>
			    	<td>&nbsp;</td>
			  </tr> 
			   <tr>
			    	<th style="width: 90px">ios下载链接：</th>
			    	<td>
			     		<input type="text" name="url2" id="url2" value="${n.url2}" class="easyui-validatebox"  style="width:580px;"/>
			    	    <strong style="color:#F00">http:// 开头</strong>  
			    	</td>
			    	<td>&nbsp;</td>
			  </tr> 
			   <tr style="display:none;">
			    	<th style="width: 90px">是否发布：</th>
			    	<td >
                		<select name="isShow" >
						<c:if test="${n.isShow==1}">
						<option  value="1" selected="selected">显示</option>
						</c:if>
						<c:if test="${n.isShow==2}">
						<option  value="2" selected="selected">不显示</option>
						</c:if>
						<c:if test="${n.isShow!=1}">
						<option  value="1" >显示</option>
						</c:if>
						<c:if test="${n.isShow!=2}">
						<option  value="2">不显示</option>
						</c:if>
					    </select>   	
                	</td>
			  </tr>
			  <tr>
				 <th></th>
				 <td>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
						  <button type="submit" class="btn btn-primary" >保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
						  <!-- <button type="button" class="btn clear" >清空</button> -->
					</div>
				 </td>
			  </tr>
		</table>	 
		  
		  <input type="hidden" name="id" id="id" value="${n.id}"/>
		  <input type="hidden" name="datagridId" value="${param.rel }_datagrid" />	
		  <input type="hidden" name="classRoot" value="${n.classRoot}"/>
		  <input type="hidden" name="types" value="${param.types}"/>
		  <input type="hidden" name="newsTitle" value="${param.newsTitle}"/>
		  <input type="hidden" name="createTime" value="<fmt:formatDate  value="${n.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />"/>
		   <input type="hidden" name="currentCallback" value="refresh" /> 
	       <input type="hidden" name="callback_fn" value="queryLeftClass"/>
	</form>
</div>

<script type="text/javascript">	
//options为编辑配置属性
var options = {
        cssPath : 'resource/js/editor/plugins/code/prettify.css',
        filterMode : false,
		uploadJson:'news/upload.do?ysStyle=YS640&moduleName=news',
		width : '320px',
		height:'600px',
		resizeType : 1,
		allowImageUpload : true,
		allowFlashUpload : false,
		allowMediaUpload : false,
		allowFileManager : false,
		syncType:"form",
		afterCreate : function() {
							var self = this;
							self.sync();
						},
		afterChange : function() {
							var self = this;
							self.sync();
						},
		afterBlur : function() {
							var self = this;
							self.sync();
						},
		items:['source', '|', 'fullscreen', 'undo', 'redo', 'print', 'cut', 'copy', 'paste',
			'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
			'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
			'superscript', '|', 'selectall', 'clearhtml','quickformat','|',
			'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
			'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image','flash', 'media', 'table', 'hr', 'emoticons', 'link', 'unlink', '|', 'about']
}; 
$(document.body).ready(function(){
	editor = KindEditor.create('#content',options);
	if($("#id").val()!=""&&$("#id").val()!=null){
		$("#fwdz").html('<%=bp%>front/m/news/htmlDetailPage.do?id=${n.id}');
	}
	
})
function save(){
	editor.sync();//同步编辑器的数据到texterea
	var flag = true;
	var jumpUrl = $.trim($("#url").val());
	if(jumpUrl!=''){
		if(jumpUrl.indexOf("http://")!=0){
			alert("幻灯片链接以 http:// 开头");
			return false;
		}
	}
	if(flag){
		if(!validateSubmitForm($('#news_form'))){
			return;
		}
	}
}
var saveCallback=function(obj,msg){
	if(msg.statusCode == 200){
		
	}else{
		Msg.alert("提示",msg.message,"error");
	}
}


</script>
