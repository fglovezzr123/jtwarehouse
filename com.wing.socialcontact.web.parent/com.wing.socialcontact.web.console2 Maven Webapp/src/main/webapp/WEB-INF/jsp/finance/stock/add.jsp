<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<%--
	模块：
--%>

	<form  action="financeProductStack/add.do"  id="financeProductStack_form"  method="post"   onsubmit="return validateSubmitForm(this)">
 	 <%--<input id="imgPath" name="imgPath" value=""  type="hidden" />--%>
		<div class="divider"></div>
		<table class="table table-nobordered " style="margin-top: 25px;">
			<tr>
				<th style="width: 120px">项目头图：</th>
				<td >
					<span id="productImgs" title="点击删除" ></span>
					<input  onclick="doSelectPic1()"  type="button" value="上传图片" id="pictureProductfile" class="form_shangchuan" />
					<iframe id="uploadProductPicFrame" src="" style="display:none;"></iframe>
					<strong style="color:#F00">请上传jpg,jpeg,png格式图片,建议尺寸640*320px，大小限制2M。</strong>
				</td>
			</tr>
			<tr>
				<th style="width: 120px">项目名称：</th>
				<td>
					<input type="text" name="productName" class="easyui-validatebox" required="true"  data-options="validType:['length[1,30]']"   maxlength="30" />
				</td>
				<th style="width: 120px">权重：</th>
				<td>
					<input type="text" name="ratio" class="easyui-validatebox" required="true"  data-options="validType:['length[1,30]']"   maxlength="30" />
				</td>
			</tr>
			<tr>
				<th style="width: 120px">项目平台：</th>
				<td>
					<input type="text" name="orgName" class="easyui-validatebox" required="true"  data-options="validType:['length[1,30]']"   maxlength="30" />
				</td>
				<th style="width: 120px">是否显示：</th>
				<td>
					<select name="isShow" id="isShow" required="true" style="width: 120px;">
						<%--<c:forEach var="c" items="${quicks}">--%>
							<%--<option  value="${c.id}">${c.quickName}</option>--%>
						<%--</c:forEach>--%>
					</select>
				</td>
			</tr>
			<tr>
				<th style="width: 120px">项目进度：</th>
				<td>
					<select name="financeType" id="financeType" required="true" style="width: 120px;">
						<%--<c:forEach var="c" items="${quicks}">--%>
							<%--<option  value="${c.id}">${c.quickName}</option>--%>
						<%--</c:forEach>--%>
					</select>
				</td>
				<th style="width: 120px">是否推荐：</th>
				<td>
					<select name="isRecommend" id="isRecommend" required="true" style="width: 120px;">
						<%--<c:forEach var="c" items="${quicks}">--%>
							<%--<option  value="${c.id}">${c.quickName}</option>--%>
						<%--</c:forEach>--%>
					</select>
				</td>
			  </tr>

			  <tr>
			    	<th style="width: 120px">目标金额：</th>
			    	<td>
							万元
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 120px">一句话亮点：</th>
			    	<td>

			    	</td>
			  </tr>
			<tr>
				<th style="width: 120px">项目详情：</th>
				<td>
					<input type="text" name="financeAmount" />
				</td>
			</tr>
			<%--<tr>--%>
				<%--<th style="width: 120px">亮点二：</th>--%>
				<%--<td>--%>
					<%--<input type="text" name="liangdian" />--%>
				<%--</td>--%>
			<%--</tr>--%>
			<tr>
				<th style="width: 120px">企业资料：</th>
			</tr>
			<tr>
				<th style="width: 120px">公司注册信息：</th>
			</tr>
			<tr>
				<th style="width: 120px">公司名称：</th>
				<td>
					<input type="text" name="financeAmount" />
				</td>
				<th style="width: 120px">成立日期：</th>
				<td>
					<input type="text" name="financeAmount" />
				</td>
			</tr>
			<tr>
				<th style="width: 120px">注册地址：</th>
				<td>
					<input type="text" name="financeAmount" />
				</td>
				<th style="width: 120px">法定代表人：</th>
				<td>
					<input type="text" name="financeAmount" />
				</td>
			</tr>

			<tr>
				<th style="width: 120px">融资基本信息（更多信息请查看下方融资计划书）：</th>
			</tr>
			<tr>
				<th style="width: 120px">项目估值：</th>
				<td>
					<input type="text" name="financeAmount" />
				</td>
			</tr>
			<tr>
				<th style="width: 120px">项目所属阶段：</th>
				<td>
					<input type="text" name="financeAmount" />
				</td>
			</tr>
			<tr>
				<th style="width: 120px">最低投资额：</th>
				<td>
					<input type="text" name="financeAmount" />万元
				</td>
			</tr>
			<tr>
				<th style="width: 120px">机构投资人：</th>
			</tr>
			<tr>
				<th style="width: 120px">名称：</th>
				<td>
					<input type="text" name="financeAmount" />
				</td>
				<th style="width: 120px">投资额：</th>
				<td>
					<input type="text" name="financeAmount" />
				</td>
			</tr>
			<tr>
				<th style="width: 120px">融资文件：</th>
			</tr>
			<tr>
				<th style="width: 120px">顾问头像：</th>
				<td >
					<span id="consultantImgs" title="点击删除" ></span>
					<input  onclick="doSelectPic1()"  type="button" value="上传图片" id="pictureConsultantfile" class="form_shangchuan" />
					<iframe id="uploadConsultantFrame" src="" style="display:none;"></iframe>
					<strong style="color:#F00">请上传jpg,jpeg,png格式图片,建议尺寸640*320px，大小限制2M。</strong>
				</td>
			</tr>
			<tr>
				<th style="width: 120px">职务：</th>
				<td>

				</td>
			</tr>
			<tr>
				<th style="width: 120px">机构：</th>
				<td>

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
<<script type="text/javascript">
function save(){
	var flag = true;
	var picUrl = "";
	$('.up_pic_img').each(function(){
		picUrl = $(this).attr("srcpath");
	});
//	if($.trim(picUrl).length <= 0){
//		Msg.alert("提示","请上传幻灯片封面！","warning",null);
//		return false;
//	}
	$("#imgPath").val(picUrl);
	if(flag){
		if(!validateSubmitForm($('#financeProduct_form'))){
			return;
		}
	}
}

var picUrl = $("#imgPath").val();
if($.trim(picUrl).length > 0){
	$("#consultantImgs").html("");
	var imgHtml = "<img title='点击删除' onclick='delPic(this)'   srcpath='"+picUrl+"' src='"+picUrl+"' class='up_pic_img' style='width:375px; height:180px;'/>";
	$("#consultantImgs").append(imgHtml);
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
initUploadTagPicFrame();
function initUploadTagPicFrame(){
	var frameSrc = "<%=basePath %>qfyBaseUploadPic/getUploadPicForm.do";
	var frameParam = new Object();
	frameParam.formId= "upload";
	frameParam.inputId= "pic";
	frameParam.inputOnChange = "parent.picChange";
	frameParam.jsonp = "parent.picUploadCallback";
	frameParam.picRepository = "qfyQuickDetailBanner";
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
      initUploadTagPicFrame();
    } else
	if (fileSize>2097152){
		alert("上传图片大小超过2M");
		initUploadTagPicFrame();
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
			var imgHtml = "<img title='点击删除' onclick='delPic(this)' srcpath='"+data.picPath+"' src='"+_oss_url+picUrl+"' class='up_pic_img' style='width:375px; height:180px;'/>";
    		$("#consultantImgs").append(imgHtml);
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
	initUploadTagPicFrame();
}
</script>
