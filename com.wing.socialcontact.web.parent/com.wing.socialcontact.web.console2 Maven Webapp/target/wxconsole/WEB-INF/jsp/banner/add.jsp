<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理-- banner管理--添加
--%>

<div style="width: 800px;margin: 0 auto;" >
	<form  action="banner/add.do"  method="post" id="news_form" afterCallback="saveCallback" enctype="multipart/form-data" >
		  <input id="picPath" name="picPath" value=""  type="hidden" />
		  <table class="table table-nobordered " style="margin-top: 25px;">
		 	 <tr>
			    	<th style="width: 80px">项目栏目：</th>
			    	<td >
			     	<select name="columnType"  id="columnType" style="width: 120px;">
						<c:forEach var="c" items="${values}">
						<c:if test="${param.columnType==c.id}">
						<option  value="${c.id}" selected>${c.listValue}</option>
						</c:if>
						<c:if test="${param.columnType!= c.id}">
						<option  value="${c.id}">${c.listValue}</option>
						</c:if>
						</c:forEach>	
					</select>
			    	</td>
			  </tr>
			  <tr>
				<th style="width:20%">用户范围：</th>
		    	<td style="width:80%">
		    		<select name="userRange" id="userRange" class="easyui-validatebox" required="true" style="width: 120px;">
		     			<option value="1">全部</option>
		     			<option value="2">指定用户</option>
		     		</select>
		    	</td>
		  	</tr>
			<tr id="zdyh_tr_1">
		    	<th>哪些等级用户可见：</th>
		    	<td>
		    		<c:forEach items="${userLevelList }" var="level">
		    			<input type="checkbox" value="${level.id }" v_text="${level.level }" name="userLevel" style="margin-top: -2px;"/><span style="margin-right:20px;margin-left: 2px;">${level.level }</span>
		    		</c:forEach>
               	</td>
			</tr>
			<tr id="zdyh_tr_2">
		    	<th>用户类型：</th>
		    	<td>
               		<input type="radio" name="reconUserFlag" value="1" style="margin-top: -2px;" checked="checked"/><span style="margin-right:20px;margin-left: 2px;">认证用户可见</span>
               		<input type="radio" name="reconUserFlag" value="2" style="margin-top: -2px;"/><span style="margin-left: 2px;">非认证用户可见</span>
               	</td>
			</tr>
			  <tr>
			    	<th style="width: 80px">幻灯片封面：</th>
			    	<td >
                		<span id="nimgs" title="点击删除" ></span>
                	   	<input  onclick="doSelectPic()"  type="button" value="上传图片" id="picturefile" class="form_shangchuan" />
	                    <iframe id="uploadPicFrame" src="" style="display:none;"></iframe>
	                    <strong style="color:#F00">请上传jpg,jpeg,png格式图片,建议尺寸640*320px，大小限制2M。</strong>           	
                	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">幻灯片名称：</th>
			    	<td >
			     		<input type="text" name="title" class="easyui-validatebox" required="true"  data-options="validType:['length[1,20]']"   maxlength="20" />
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">幻灯片链接：</th>
			    	<td >
			     		<input type="text" name="jumpUrl" id="jumpUrl" class="easyui-validatebox" data-options="validType:['length[0,150]']"   maxlength="150" />
			     		<strong style="color:#F00">http:// 开头</strong> 
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">跳转类型：</th>
				    <td style="width:80%">
						<select name="jumpType" id="jumpType" class="easyui-validatebox" required="true" style="width: 120px;">
							<option value="0">无</option>
							<option value="1">H5</option>
							<option value="2">native</option>
						</select>
					</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">排序：</th>
			    	<td >
			     		<input type="text" name="orderNum" class="easyui-numberbox" required="true"   maxlength="3" min="0" max="999" />
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
		  <input type="hidden" name="userLevelName" id="userLevelName"/>
		  <input type="hidden" name="datagridId" value="${param.rel }_datagrid" />	
		  <input type="hidden" name="currentCallback" value="close" />
		
	</form>
	
	
</div>

<script type="text/javascript">	
$(function() {
	$("tr[id^='zdyh_tr_']").hide();
	$("#userRange").change(function(){
		var v=$(this).val();
		if(v == 1){
			$("tr[id^='zdyh_tr_']").hide();
		}else{
			$("tr[id^='zdyh_tr_']").show();
		}
	});
});
function save(){
	var flag = true;
	var picUrl = "";
	$('.up_pic_img').each(function(){
		picUrl = $(this).attr("srcpath");
	});
	if($.trim(picUrl).length <= 0){
		alert("请上封面图片");
		return false;
	}
	$("#picPath").val(picUrl);
	var jumpUrl = $.trim($("#jumpUrl").val());
	if(jumpUrl!=''){
		if(jumpUrl.indexOf("http://")!=0){
			alert("幻灯片链接以 http:// 开头");
			return false;
		}
	}
	var levelName="";
	$("input[name='userLevel']:checked").each(function(i){
		if(i > 0){
			levelName+=",";
		}
		levelName+=$(this).attr("v_text");
	});
	$("#userLevelName").val(levelName);
	if($("#userRange").val()=="2"){
		if(levelName==""){
			alert("请选择哪些等级用户可见！");
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
		$("#${param.rel}_datagrid").datagrid('reload');
		$("#${param.rel}_add").dialog("close");
	}else{
		Msg.alert("提示",msg.message,"error");
	}
}
function doSelectPic() {
	var imgSize = 0;
	$('.up_pic_img').each(function(){
		imgSize = imgSize+1;
	});
	if(imgSize <= 0 ){
		$("#pic", $("#uploadPicFrame")[0].contentWindow.document).click();
	}else{
		alert("仅能上传一张图片");
	}
	return false;
}
initUploadPicFrame();
function initUploadPicFrame(){
	var frameSrc = "<%=basePath%>news/getUploadPicForm.do";
	var frameParam = new Object();
	frameParam.formId= "upload";
	frameParam.moduleName= "banner";
	frameParam.inputId= "pic";
	frameParam.inputOnChange = "parent.picChange";
	frameParam.jsonp = "parent.picUploadCallback";
	frameParam.ysStyle = "YS640320";
	frameSrc += "?"+parseParam(frameParam);
	$("#uploadPicFrame").attr("src", frameSrc);
}
//公共方法,用来将对象转化为URL参数
function parseParam(param, key){
  	var paramStr="";
  	if(param instanceof String||param instanceof Number||param instanceof Boolean){
    	paramStr+="&"+key+"="+encodeURIComponent(param);
  	}else{
    	jQuery.each(param,function(i){
          	var k=key==null?i:key+(param instanceof Array?"["+i+"]":"."+i);
          	paramStr+='&'+parseParam(this, k);
    	});
  	}
  	return paramStr.substr(1);
}
function picChange(inputFile){
	var fileSize = 0;
	var filename = "";
	if (navigator.userAgent.indexOf('MSIE') >= 0){
	}else{
		var files = inputFile.files;
		if (files.length>0){
			var targetFile = files[0];
			fileSize = targetFile.size;
			filename = targetFile.name;
		}
		if(files.length > 1){
			alert("请单张上传");
    		initUploadPicFrame();
		}
	}
	/* if(!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(filename))
    {
      alert("图片类型必须是.gif,jpeg,jpg,png中的一种");
      initUploadPicFrame();
    }else  */
    if (fileSize>2097152){
		alert("上传图片大小超过2M");
		initUploadPicFrame();
	}else{
		$("#upload", $("#uploadPicFrame")[0].contentWindow.document).submit();
	}
}
function delPic(data){
	$(data).remove();
}
//上传完成后回调的方法
function picUploadCallback(data){
	if (data.returnCode == "1"){
		var picUrl = data.picPath;
		if(picUrl.length > 0){
			var imgHtml = "<img title='点击删除' onclick='delPic(this)' srcpath='"+data.picPath+"' src='"+data.img_url+"' class='up_pic_img' style='width:240px; height:160px;'/>";
    		$("#nimgs").append(imgHtml);
		}else{
			alert("上传失败,请稍后再试");
		}
	}else{
		if(data.msg != ""){
			alert(data.msg);
		}else{
			alert("上传失败,请稍后再试");
		}
	}
	initUploadPicFrame();
}
</script>
