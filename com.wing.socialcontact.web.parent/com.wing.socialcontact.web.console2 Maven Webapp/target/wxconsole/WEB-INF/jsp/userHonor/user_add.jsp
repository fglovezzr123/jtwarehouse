<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<%--
	模块：
--%>

	<form  action="userhonor/addUser.do"  id="user_form"  method="post"   onsubmit="return validateSubmitForm(this)">
	 <input id="imgUrl" name="imgUrl" value=""  type="hidden" />
		<div class="divider"></div>
		  <table class="table table-nobordered " style="margin-top: 25px;">
		 
		 	  <tr>
			    	<th style="width: 120px">用户图片：</th>
			    	<td >
                		<span id="nimgs" title="点击删除" ></span>
                	   	<input  onclick="doSelectPic1()"  type="button" value="上传图片" id="picturefile" class="form_shangchuan" />
	                    <iframe id="uploadPicFrame" src="" style="display:none;"></iframe>  
	                    <strong style="color:#F00">请上传jpg,jpeg,png格式图片,建议尺寸200*200px。</strong>       	
                	</td>
			  </tr>
			   <tr>
				<th>
					手机号：
				</th>
				<td>
					<input type="text" id="phone" name="mobile" class="easyui-numberbox"   required="true"  data-options="validType:['mPhone']" />
				</td>
			  </tr>
			  <tr>
			    	<th style="width: 120px">认证姓名：</th>
			    	<td>
			     		<input type="text" name="nickName" class="easyui-validatebox" required="true"  data-options="validType:['length[1,20]']"   maxlength="30" />
			    	</td>
			  </tr>
			 <!--  <tr>
			    	<th style="width: 120px">认证名称：</th>
			    	<td>
			     		<input type="text" name="username" class="easyui-validatebox" required="true"  data-options="validType:['length[1,20]']"   maxlength="30" />
			    	</td>
			  </tr> -->
		 	  <tr>
			    	<th style="width: 120px">性别：</th>
			    	<td >
			    	<select  name="sex" id="sex"  >
			    	<option  value="1">男</option>
			    	<option  value="2">女</option>
			    	</select>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 120px">个人简介：</th>
			    	<td>
			     		<input type="text" name="userProfile" class="easyui-validatebox"  />
			    	</td>
			  </tr>
			
			<tr>
			    	<th style="width: 120px">省：</th>
			    	<td >
			    	<select  name="province"  id="province"  onchange="querycitys()">
			    	<c:forEach items="${provinceList}" var="p">
			    	<option id="${p.id }" value="${p.id } ">${p.disName } </option>
			    	</c:forEach>
			    	</select>
			    	</td>
			  </tr>
		 
		 	  <tr>
			    	<th style="width: 120px">市：</th>
			    	<td id="">
				    	<select name="city" id="activitycitys" onchange="querycountys()">
							
						</select>
			    	</td>
			  </tr>
		 	  <tr>
			    	<th style="width: 120px">区：</th>
			    	<td id="">
				    	<select name="county" id="activitycountys">
							
						</select>
			    	</td>
			  </tr>
			  <tr style="display: none;">
			    	<th style="width: 120px">详细地址：</th>
			    	<td>
			     		<input type="text" name="address" class="easyui-validatebox"  data-options="validType:['length[1,100]']"   maxlength="40" />
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 120px">公司：</th>
			    	<td>
			     		<input type="text" name="comName" class="easyui-validatebox" required="true"  data-options="validType:['length[1,100]']"   maxlength="40" />
			    	</td>
			  </tr>
			 
			  <tr>
			    	<th style="width: 120px">职位：</th>
			    	<td >
			    	<select name="job"  style="width: 120px;">
						<c:forEach var="c" items="${jobs}">
						<option  value="${c.id}">${c.listValue}</option>
						</c:forEach>	
					</select>
			    	</td>
			  </tr>
			  
			  <tr>
			    	<th style="width: 120px">行业：</th>
			    	<td >
			    	<select name="industry"  style="width: 120px;">
						<c:forEach var="c" items="${industrys}">
						<option  value="${c.id}">${c.listValue}</option>
						</c:forEach>
					</select>
			    	</td>
			    	
			    	
			    		
			  </tr>
			  
			  <tr>
				 <th></th>
				 <td>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
						  <button type="button" class="btn btn-primary"  onclick="save()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
						  <button type="button" class="btn " id="zhikong" >清空</button>
					</div>
				 </td>
			  </tr>
		  
		  </table>
			<input type="hidden" name="datagridId" value="${param.rel }_datagrid" />
			<input type="hidden" name="currentCallback" value="close" />
	</form>
<<script type="text/javascript">



$(document.body).ready(function(){
	querycitys();
	querycountys()
	
    $("#zhikong").click(function(){
    	$("#402881ec3f74d2d5013f74dc2e3d0f11").attr("selected",true);
    	querycitys();
    	querycountys();
    	$("input").val("");
    	$("select").find("option:selected").attr("selected",false);
	});
})
function save(){
	var flag = true;
	var picUrl = "";
	var phone = $("#phone").val();
	//校验电话
	$.ajax({
            type: "post",
            url:"<%=request.getContextPath()%>/userhonor/validate.do",
            data: {"phone": phone},
            success: function (data) { 
            	if(data=="true"){
            	// 不可注册
            	Msg.alert("提示","此用户已注册！","warning",null);
            	return;
            	}
            },
    		error:function(e){
    		}
        });  
	
	$('.up_pic_img').each(function(){
		picUrl = $(this).attr("srcpath");
	});
	/*if($.trim(picUrl).length <= 0){
		Msg.alert("提示","请上传分类图片！","warning",null);
		return false;
	}*/
	
	$("#imgUrl").val(picUrl);
	
	if(flag){
		if(!validateSubmitForm($('#user_form'))){
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

var picUrl = $("#imgUrl").val();
if($.trim(picUrl).length > 0){
	$("#nimgs").html("");
	var imgHtml = "<img title='点击删除' onclick='delPic(this)'   srcpath='"+picUrl+"' src='"+picUrl+"' class='up_pic_img' style='width:240px; height:160px;'/>";
	$("#nimgs").append(imgHtml);
}
function doSelectPic1() {
	
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
initUploadPicFrame1();
function initUploadPicFrame1(){
	var frameSrc = "<%=basePath%>news/getUploadPicForm.do";
	var frameParam = new Object();
	frameParam.formId= "upload";
	frameParam.moduleName= "usercenter";
	frameParam.inputId= "pic";
	frameParam.inputOnChange = "parent.picChange";
	frameParam.jsonp = "parent.picUploadCallback";
	frameParam.ysStyle = "YS200200";
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
	if(!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(filename))
    {
      alert("图片类型必须是.gif,jpeg,jpg,png中的一种");
      initUploadPicFrame1();
    } else
	if (fileSize>2097152){
		alert("上传图片大小超过2M");
		initUploadPicFrame1();
	}else{
		$("#upload", $("#uploadPicFrame")[0].contentWindow.document).submit();
	}
}
function delPic(data){
	$(data).remove();
	initUploadPicFrame1();
}
//上传完成后回调的方法
function picUploadCallback(data){
	if (data.returnCode == "1"){
		var picUrl = data.picPath;
		if(picUrl.length > 0){
			var imgHtml = "<img title='点击删除' onclick='delPic(this)' srcpath='"+data.picPath+"' src='"+_oss_url+picUrl+"' class='up_pic_img' style='width:240px; height:160px;'/>";
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
	initUploadPicFrame1();
}


function querycitys(){
	//清空select
	$("#activitycitys").empty();
	//获取分类id
	var pid = $("#province").val();
	$.ajax({
		url : "activity/citys.do",
		dataType : "json",
		data : {
			"pid" :pid 
		},
		async : false,
		success : function(data){
			var opstr = "";
			data.forEach(function(obj, index, array) {
				  opstr+='<option value="'+obj.id+'">'+obj.disName+'</option>';
			});
			$("#activitycitys").append(opstr);
			querycountys();
		}
	});
	
}
function querycountys(){
	//清空select
	$("#activitycountys").empty();
	//获取分类id
	var pid = $("#activitycitys").val();
	$.ajax({
		url : "activity/citys.do",
		dataType : "json",
		data : {
			"pid" :pid 
		},
		async : false,
		success : function(data){
			var opstr = "";
			data.forEach(function(obj, index, array) {
				  opstr+='<option value="'+obj.id+'">'+obj.disName+'</option>';
			});
			$("#activitycountys").append(opstr);
		}
	});
	
}
</script>
