<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理-- 静态页管理--添加
--%>

<div style="width: 800px;margin: 0 auto;" >
	<form  action="news/htmladd.do"  method="post" id="news_form" afterCallback="saveCallback" enctype="multipart/form-data" >
		  <input id="imagePath" name="imagePath" value=""  type="hidden" />
		  <table class="table table-nobordered " style="margin-top: 25px;">
		 
			  <tr>
			    	<th style="width: 80px">信息标题：</th>
			    	<td >
			     		<input type="text" name="newsTitle" class="easyui-validatebox" required="true"  data-options="validType:['length[1,20]']"   maxlength="20" />
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">内容详情：</th>
			    	<td>
			    	<textarea name="content" style="width:100%;height:300px;visibility:hidden;" id="content"></textarea>
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 80px">下载链接：</th>
			    	<td>
			     		<input type="text" name="url" class="easyui-validatebox" required="true"   maxlength="100" />
			     		<strong style="color:#F00">http:// 开头</strong>  
			    	</td>
			    	<td>&nbsp;</td>
			  </tr>
			   <tr  style="display:none;">
			    	<th style="width: 80px">是否发布：</th>
			    	<td >
                		<select name="isShow" >
						<option  value="1">显示</option>
						<option  value="2">不显示</option>
					    </select>   	
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
		  
		  <input type="hidden" name="datagridId" value="${param.rel }_datagrid" />	
		  <input type="hidden" name="currentCallback" value="close" />
		
	</form>
	
	
</div>

<script type="text/javascript">	
//options为编辑配置属性
var options = {
        cssPath : 'resource/js/editor/plugins/code/prettify.css',
        filterMode : false,
		uploadJson:'news/upload.do?ysStyle=YS640&moduleName=news',
		width : '500px',
		height:'300px',
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
})
function save(){
	editor.sync();//同步编辑器的数据到texterea
	var flag = true;
	var picUrl = "";
	if(flag){
		if(!validateSubmitForm($('#news_form'))){
			return;
		}
	}
}
var saveCallback=function(obj,msg){
	if(msg.statusCode == 200){
		$("#${param.rel}_datagrid").datagrid('reload');
		$("#${param.rel}_add").dialog("close");
	}else{
		Msg.alert("提示",msg.message,"error");
	}
}

</script>
