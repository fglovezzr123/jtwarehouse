<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：邀请好友页面配置
--%>

<div style="width: 800px;margin: 0 auto;" >
	<form  action="config/manager.do"  method="post" id="manager_form" afterCallback="saveCallback" enctype="multipart/form-data" >
		  <table class="table table-nobordered " style="margin-top: 10px;">
			  <tr>
			    	<td>
			    		<textarea name="listDesc" style="width:520px;height:400px;" id="content">${yqConfig.listDesc }</textarea>
			    	</td>
			  </tr>
			  <tr>
				 <td>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
						  <button type="button" class="btn btn-primary"  onclick="save()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
						  <!-- <button type="button" class="btn clear" >清空</button> -->
					</div>
				 </td>
			  </tr>
		  </table>
		  <input type="hidden" name="id" value="${yqConfig.id }"/>
		  <input type="hidden" name="listType" value="${yqConfig.listType }"/>
		  <input type="hidden" name="listValue" value="${yqConfig.listValue }"/>
		  <input type="hidden" name="sortno" value="${yqConfig.sortno }"/>
		  <input type="hidden" name="deleted" value="${yqConfig.deleted }"/>
	</form>
</div>

<script type="text/javascript">	
//options为编辑配置属性
var options = {
        cssPath : 'resource/js/editor/plugins/code/prettify.css',
        filterMode : false,
		uploadJson:'news/upload.do?ysStyle=YS640&moduleName=other',
		width : '520px',
		height:'400px',
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
	if(!validateSubmitForm($('#manager_form'))){
		return;
	}
}
function saveCallback($form){
	var tabId=$form.attr("tabPanel_Id");
	if(tabId){
		$("#"+tabId).panel('refresh');
	}
}
</script>
