<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<%--
	模块：
--%>

	<form  action="activity/update.do"  id="activity_form"  method="post"   onsubmit="return validateSubmitForm(this)">
	 <input id="imagePath" name="imagePath" value="${dto.imagePath}"  type="hidden" />
		<div class="divider"></div>
		  <table class="table table-nobordered " style="margin-top: 25px;">
		 
		 	  <tr>
			    	<th style="width: 120px">活动封面图片：</th>
			    	<td >
                		<span id="nimgs" title="点击删除" ></span>
	                    <iframe id="uploadPicFrame" src="" style="display:none;"></iframe>  
                	</td>
			  </tr>
			  <tr>
			    	<th style="width: 120px">活动主题：</th>
			    	<td>
			     		<input type="text" name="titles"  readonly="readonly" class="easyui-validatebox" value="${dto.titles}" required="true"  data-options="validType:['length[1,20]']"   maxlength="30" />
			    	</td>
			  </tr>
		 	  <tr>
			    	<th style="width: 120px">活动分类：</th>
			    	<td >
			    	<select  name="classId" id="classId"  onchange="querytags(2)" disabled>
			    	<c:choose>
			    		<c:when test="${dto.classId eq '1' }">
			    			<option id="classId1" value="1" selected="selected">以玩会友</option>
			    			<option id="classId2" value="2">以书会友</option>
			    		</c:when>
			    		<c:otherwise>
			    			<option id="classId1" value="1" >以玩会友</option>
			    			<option id="classId2" value="2" selected="selected" >以书会友</option>
			    		</c:otherwise>
			    	</c:choose>
			    	</select>
			    	</td>
			  </tr>
		 	  <tr>
			    	<th style="width: 120px">活动标签：</th>
			    	<td >
				    	<select name="tag" id="activitytags" disabled>
							
						</select>
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 120px">活动方式：</th>
			    	<td >
			    	<select  name="pattern" disabled>
			    	<option id="pattern1" value="1">线下参与</option>
			    	<option id="pattern2" value="2">线上参与</option>
			    	</select>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 120px">发起者：</th>
			    	<td>
			     		<input type="text" name="sponsor" readonly="readonly" class="easyui-validatebox" value="${dto.sponsor}"  required="true"  data-options="validType:['length[1,20]']"   maxlength="20" />
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 120px">发起者介绍：</th>
			    	<td>
			     		<input type="text" name="sponsorIntroduce"  readonly="readonly" class="easyui-validatebox" value="${dto.sponsorIntroduce}"  required="true"  data-options="validType:['length[1,20]']"   maxlength="20" />
			    	</td>
			  </tr>
			  <tr>
				<th style="width: 120px">活动开始时间：</th>
				<td>
					<input type="text" name="startTime" id="starTime"  readonly="readonly" value="<fmt:formatDate  value="${dto.startTime}" pattern="yyyy-MM-dd HH:mm:ss" />"  maxlength="50" class="easyui-validatebox" required="true"    onFocus="WdatePicker({skin:'whyGreen',startDate:'%y-%M-%d %H:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
				</td>
			</tr>
			<tr>
				<th  style="width: 120px">活动结束时间：</th>
				<td>
					<input type="text" name="endTime" id="endTime"  readonly="readonly" value="<fmt:formatDate  value="${dto.endTime}" pattern="yyyy-MM-dd HH:mm:ss" />" maxlength="50" class="easyui-validatebox" required="true"   onFocus="WdatePicker({skin:'whyGreen',startDate:'%y-%M-%d %H:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
				</td>
			</tr>
			<tr>
				<th  style="width: 120px">报名截至时间：</th>
				<td>
					<input type="text"  id="signup" name="signupTime"  readonly="readonly" value="<fmt:formatDate  value="${dto.signupTime}" pattern="yyyy-MM-dd HH:mm:ss" />" class="easyui-validatebox" required="true"   onFocus="WdatePicker({skin:'whyGreen',startDate:'%y-%M-%d %H:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
				</td>
			</tr>
			<tr>
			    	<th style="width: 120px">省：</th>
			    	<td >
			    	<select  name="province"  id="province"  onchange="querycitys(2)"  disabled>
			    	<c:forEach items="${provinceList}" var="p">
			    	<c:choose>
			    		<c:when test="${dto.province eq p.id }">
			    		<option id="${p.id }" value="${p.id } " selected="selected">${p.disName } </option>
			    		</c:when>
			    		<c:otherwise>
			    		<option id="${p.id }" value="${p.id } ">${p.disName } </option>
			    		</c:otherwise>
			    	</c:choose>
			    	</c:forEach>
			    	</select>
			    	</td>
			  </tr>
		 
		 	  <tr>
			    	<th style="width: 120px">市：</th>
			    	<td id="">
				    	<select name="city" id="activitycitys" onchange="querycountys()" disabled>
							
						</select>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 120px">区：</th>
			    	<td id="">
				    	<select name="county" id="activitycountys" disabled>
							
						</select>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 120px">活动地点：</th>
			    	<td>
			     		<input type="text" name="place" class="easyui-validatebox" value="${dto.place}" required="true" readonly="readonly"  data-options="validType:['length[1,20]']"   maxlength="20" />
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 120px">门票价格：</th>
			    	<td>
			     		<input type="text" name="ticketPrice" value="${dto.ticketPrice}" class="easyui-numberbox" readonly="readonly" precision="2"   maxlength="20" />
			    	</td>
			  </tr>
			  <%--  <tr>
			    	<th style="width: 120px">退款说明：</th>
			    	<td>
			     		<input type="text" name="refundDescription" class="easyui-validatebox" value="${dto.refundDescription}"   data-options="validType:['length[1,100]']"   maxlength="100" />
			    	</td>
			  </tr> --%>
			  <tr>
			    	<th style="width: 120px">活动状态：</th>
			    	<td >
			    	<select   id="status"  disabled="disabled">
			    	<option id="status1" value="1">未审核</option>
			    	<option id="status2" value="2">报名中</option>
			    	<option id="status3" value="3">报名结束</option>
			    	<option id="status4" value="4">进行中</option>
			    	<option id="status5" value="5">已结束</option>
			    	<option id="status6" value="6">已取消</option>
			    	</select>
			    	<input type="hidden" value="${dto.status }"  name="status" readonly="readonly"/>
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 80px">活动详情：</th>
			    	<td>
					<textarea id="contents" name="contents" style="width:320px;height:600px;" readonly="readonly">${dto.contents }</textarea>
			    	</td>
			  </tr>
			  <tr>
				<th>
					用户可见：
				</th>
				<td>
					<input type="radio" name="showUser" id="showUser1" value="0" checked readonly="readonly"/> 所有用户可见 
				</td>
			  </tr>
			  <tr>
				<th>
					是否显示：
				</th>
				<td>
					<input type="radio" name="showEnable" id="showEnable1" value="1" readonly="readonly"> 是 
					<input type="radio" name="showEnable" id="showEnable0" value="0" readonly="readonly"> 否
				</td>
			  </tr>
			  <tr>
				<th>
					是否允许评论：
				</th>
				<td>
					<input type="radio" name="commentEnable" id="commentEnable1" value="1" readonly="readonly"> 是 
					<input type="radio" name="commentEnable" id="commentEnable0" value="0" readonly="readonly"> 否
				</td>
			  </tr>
			  <tr>
				<th>
					是否推荐首页：
				</th>
				<td>
					<input type="radio" name="recommendEnable" id="recommendEnable1" value="1" readonly="readonly"> 是
					<input type="radio" name="recommendEnable" id="recommendEnable0" value="0" readonly="readonly"> 否
				</td>
			  </tr>
			  <tr>
				<th>
					是否推荐列表：
				</th>
				<td>
					<input type="radio" name="recommendList" id="recommendList1" value="1" readonly="readonly" > 是 
					<input type="radio" name="recommendList" id="recommendList0" value="0" readonly="readonly"> 否
				</td>
			  </tr>
		  </table>
		  <input type="hidden" name="id" value="${dto.id }"/>
		  <input type="hidden" name="isdelay" value="${dto.isdelay }"/>
		  <input type="hidden" name="delaydays" value="${dto.isdelay }"/>
		  <input type="hidden" name="datagridId" value="${param.rel }_datagrid" />	
		  <input type="hidden" name="currentCallback" value="close" />
		  <input type="hidden" name="createTime" value="<fmt:formatDate  value="${dto.createTime}" pattern="yyyy-MM-dd hh:mm:ss" />"/>
		  <input type="hidden" name="createUserId" value="${dto.createUserId}"/>
		  <input type="hidden" name="createUserName" value="${dto.createUserName}"/>
	</form>
<<script type="text/javascript">


var options = {
        cssPath : 'resource/js/editor/plugins/code/prettify.css',
        filterMode : false,
		uploadJson:'news/upload.do?ysStyle=YS640',
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
	editor = KindEditor.create('#contents',options);
	querytags(1);
	querycitys(1);
	//活动状态
	$("#status"+${dto.status}).attr("selected","selected");
	//首页推荐
	$("#recommendEnable"+${dto.recommendEnable}).attr("checked","checked");
	//列表推荐
	$("#recommendList"+${dto.recommendList}).attr("checked","checked");
	//显示
	$("#showEnable"+${dto.showEnable}).attr("checked","checked");
	//评论
	$("#commentEnable"+${dto.commentEnable}).attr("checked","checked");
})
function save(){
	editor.sync();//同步编辑器的数据到texterea
	var flag = true;
	var picUrl = "";
	$('.up_pic_img').each(function(){
		picUrl = $(this).attr("srcpath");
	});
	if($.trim(picUrl).length <= 0){
		Msg.alert("提示","请上传分类图片！","warning",null);
		return false;
	}
	$("#imagePath").val(picUrl);
	var start=$("#starTime").val();
	var end=$("#endTime").val();
	var signup=$("#signup").val();
	if(start>=end){
		Msg.alert("提示","结束时间必须大于开始时间！","warning",null);
		return;
	}
	if(signup>=start){
		Msg.alert("提示","开始时间必须大于报名截止时间！","warning",null);
		return;
	}
	if(flag){
		if(!validateSubmitForm($('#activity_form'))){
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

var picUrl = $("#imagePath").val();
var oss = '${ossurl}';
if($.trim(picUrl).length > 0){
	$("#nimgs").html("");
	var imgHtml = "<img    srcpath='"+picUrl+"' src='"+_oss_url+picUrl+"' class='up_pic_img' style='width:240px; height:160px;'/>";
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
}
//上传完成后回调的方法
function picUploadCallback(data){
	if (data.returnCode == "1"){
		var picUrl = data.picPath;
		if(picUrl.length > 0){
			var imgHtml = "<img  srcpath='"+data.picPath+"' src='"+_oss_url+picUrl+"' class='up_pic_img' style='width:240px; height:160px;'/>";
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

function querytags(type){
	//清空select
	$("#activitytags").empty();
	//获取分类id
	var cid = $("#classId").val();
	$.ajax({
		url : "activitytag/tags.do",
		dataType : "json",
		data : {
			"cid" :cid 
		},
		async : false,
		success : function(data){
			var opstr = "";
			data.forEach(function(obj, index, array) {
				if("${dto.tag}"==obj.id){
				  opstr+='<option id="'+obj.id+'" value="'+obj.id+'"  selected="selected">'+obj.name+'</option>';
				}else{
				  opstr+='<option id="'+obj.id+'" value="'+obj.id+'">'+obj.name+'</option>';
				} 
			});
			$("#activitytags").append(opstr);
		}
	});
}
function querycitys(type){
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
				if("${dto.city}"==obj.id){
					  opstr+='<option id="'+obj.id+'" value="'+obj.id+'"  selected="selected">'+obj.disName+'</option>';
					}else{
				  		opstr+='<option id="'+obj.id+'" value="'+obj.id+'">'+obj.disName+'</option>';
					}
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
				if("${dto.city}"==obj.id){
					  opstr+='<option id="'+obj.id+'" value="'+obj.id+'"  selected="selected">'+obj.disName+'</option>';
					}else{
				  		opstr+='<option id="'+obj.id+'" value="'+obj.id+'">'+obj.disName+'</option>';
					}
			});
			$("#activitycountys").append(opstr);
		}
	});
}

</script>
