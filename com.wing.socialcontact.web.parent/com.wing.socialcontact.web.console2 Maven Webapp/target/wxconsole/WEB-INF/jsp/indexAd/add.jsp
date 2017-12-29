<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理-- 聚合页面管理--添加
--%>

<div style="width: 98%;margin: 0 auto;" >
	<form  action="indexAd/add.do"  method="post" id="indexAd_form" enctype="multipart/form-data" >
		<table class="table table-bordered">
		  	<tr>
				<th style="width:20%">用户范围：</th>
		    	<td style="width:80%">
		    		<select name="userRange" id="userRange" class="easyui-validatebox" required="true" style="width: 120px;">
		     			<option></option>
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
		    	<th style="vertical-align: middle;">图片封面：</th>
		    	<td>
		    		<div style="position:relative;">
						<img id="addGuest" onclick="uploadImg('kjrk',this);" src="${path}/resource/images/tjy/jia_03.png" style="cursor:pointer;margin-top:12px;margin-left:12px;height:80px;width:80px;" title="上传入口图片"/>
						<img onclick="removeGuest(this)" id="removeButton" src="${path}/resource/images/tjy/close_04.gif" width="25" height="25" style="position: absolute;left: 80px;top:0;display:none;"/>
						<input type="hidden" name="imgUrl" id="imgUrl"/>
					</div>
               	</td>
			</tr>
			<tr>
				<th>图片名称：</th>
		    	<td>
		    		<input type="text" name="imgName" class="easyui-validatebox" required="true" maxlength="20" />
		    	</td>
		  	</tr>
			<tr>
				<th>图片链接：</th>
		    	<td>
		    		<input type="text" name="imgLink" id="imgLink" class="easyui-validatebox" required="true" maxlength="150" /><font color="red">(注意:必须以“http://”开头)</font>
		    	</td>
		  	</tr>
		  	<tr>
				<th>排序:</th>
				<td><input type="text" name="orderNum" class="easyui-validatebox" required="true" maxlength="5" onkeyup="clearNoNum2(this);"/></td>
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
		<input type="hidden" name="regUserFlag" value="1"/>
		<input type="hidden" name="adType" value="1"/>
		<input type="hidden" name="status" value="0" />
		<input type="hidden" name="datagridId" value="${param.rel }_datagrid" />
		<input type="hidden" name="currentCallback" value="close" />
	</form>
	<span class="typefile" id="filePicker"></span>
</div>

<script type="text/javascript">
var uploader = null;
$(function() {
	initWebUpload();
	$("#userRange").change(function(){
		var v=$(this).val();
		if(v == 1){
			$("tr[id^='zdyh_tr_']").hide();
		}else{
			$("tr[id^='zdyh_tr_']").show();
		}
	});
});
function uploadImg(type,obj){
	$("#filePicker :file").click();
}
function initWebUpload(){
	uploader = WebUploader.create({
		auto: true,
	    server: "dynamic/uploadpic.do",
	    pick: "#filePicker",
	    fileNumLimit: 5000,//一次最多上传多少张照片
	    fileSingleSizeLimit: 2 * 1024 * 1024*100,
	    duplicate : true,
	    accept: {
	        title: "Images",
	        extensions: "jpg,jpeg,png",
	        mimeTypes: 'image/jpg,image/jpeg,image/png'
	    },
	    formData: {  
	    }
	});
	uploader.on("uploadSuccess", function( file, response ) {
		if(response.code==0){
			var str='<img id="upload_img" src="'+response.dataobj.img_url+'" picPath="'+response.dataobj.picPath+'" style="cursor:pointer;margin-top:12px;margin-left:12px;height:80px;width:80px;"/>';
			$("#addGuest").before(str);
			$("#addGuest").hide();
			$("#removeButton").show();
			$("#imgUrl").val(response.dataobj.picPath);
		}else{
			alert(response.msg)
		}
	});
	uploader.on( 'uploadError', function( file ) {
		alert("error");
	});
	uploader.on('error', function(handler) {
		alert("上传失败");
	});
}
function removeGuest(obj){
	$("#upload_img").remove();
	$("#addGuest").show();
	$("#removeButton").hide();
}

function save(){
	var levelName="";
	$("input[name='userLevel']:checked").each(function(i){
		if(i > 0){
			levelName+=",";
		}
		levelName+=$(this).attr("v_text");
	});
	var userRange = $("#userRange").val();
	if(userRange == 2){
		if(isEmpty(levelName)){
			Msg.alert("提示","用户范围为指定用户时，<br/>用户等级不能为空","warning");
			return;
		}
	}
	$("#userLevelName").val(levelName);
	var imgUrl=$("#imgUrl").val();
	if(imgUrl.length == 0){
		Msg.alert("提示","请选择图片","warning");
		return;
	}
	var imgLink=$("#imgLink").val();
	if(!checkurl(imgLink)){
		Msg.alert("提示","图片链接格式不正确","warning")
		return;
	}
	if(!validateSubmitForm($('#indexAd_form'))){
		return;
	}
}
</script>
