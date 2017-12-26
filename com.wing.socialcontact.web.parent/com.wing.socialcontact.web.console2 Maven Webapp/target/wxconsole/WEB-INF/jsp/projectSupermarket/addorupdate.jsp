<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<% 
	String path = request.getContextPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.getSession().setAttribute("path", path);
%>
<style>
	* { margin:0; padding:0;}
	.hyjb_box {}
	.hyjb_dl {float:left; margin: 12px 12px; width:125px; list-style:none; }
	.hyjb_dl dl { position:relative; width:125px; height:105px; }
	.hyjb_dl dl dt { width:120px; height:100px; border:1px solid #dddddd;}
	.hyjb_dl dl dd { position:absolute; right:-10px; top:-10px; }
	.hyjb_dl .hyjb_shuru { width:125px; margin-top:-22px;}
	.hyjb_dl .hyjb_shuru input { border:none; width:110px; height:26px; background:#f7f7f7; text-align:center;}
	.hyjb_tj { float:left; margin:5px 0px 0 5px; }
	#dataForm img{width:100%;height:100%;}
	.ke-icon-tel {
        background-image: url(resource/js/editor/themes/default/timg.jpg);
        width: 16px;
        height: 16px;
	}
	.distant1 {
	    color: green;
	    font-weight: bold;
	    text-shadow: 0 0 1px currentColor,-1px -1px 1px #030,0 -1px 1px #030,1px -1px 1px #030,1px 0 1px #030,1px 1px 1px #030,0 1px 1px #030,-1px 1px 1px #030,-1px 0 1px #030;
	}
</style>

<div style="width:100%;">						
<form  id="dataForm">

	<input type="hidden" name="id" value="${obj.id}"/>
	<input type="hidden" name="createTime"  value="${fns:fmt(obj.createTime,'yyyy-MM-dd HH:mm:ss')}">
	<input type="hidden" name="createUserId"  value="${obj.createUserId}">
	<input type="hidden" name="updateTime" value="${fns:fmt(obj.updateTime,'yyyy-MM-dd HH:mm:ss')}">
	<input type="hidden" name="updateUserId"  value="${obj.updateUserId}">
	<input type="hidden" name="images" id="images">
	<table class="table table-bordered" style="margin-top:0px;width:99%;">

		<tr>
			<th style="width:15%;">项目名称<font color="red">*</font>：</th>
			<td colspan=3>
				<input type="text" maxlength="100" id="projectName" name="name" value="${obj.name}" onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')" id="name"  class="span2" style="width:98%"/>
			</td>
		</tr>
		<tr>
			<th>项目介绍<font color="red">*</font>：</th>
			<td colspan=3>
				<textarea id="introduce"  name="introduce" style="width:700px;height:100px;">${obj.introduce}</textarea>
			</td>
		</tr>

		<tr name="allplace">
			<th style="width: 120px">合作模式：</th>
			<td >
				<select  name="cooperativeMode"  id="cooperativeMode" >
					<c:forEach items="${cooperativeModeList}" var="p">
						<option id="${p.id }" value="${p.id }" <c:if test="${p.id==obj.cooperativeMode}">selected</c:if>>${p.listValue} </option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<th>模式说明：</th>
			<td colspan=3>
				<textarea id="illustration"  name="illustration" style="width:700px;height:100px;">${obj.illustration}</textarea>
			</td>
		</tr>
		<tr>
			<th>项目亮点：</th>
			<td colspan=3>
				<textarea id="highlights"  name="highlights" style="width:700px;height:100px;">${obj.highlights}</textarea>
			</td>
		</tr>

		<tr name="allplace">
			<th style="width: 120px">省：</th>
			<td >
				<select  name="province"  id="provinceId"  onchange="queryCitys()">
					<c:forEach items="${provinceList}" var="p">
						<option id="${p.id}" value="${p.id}" <c:if test="${p.id==obj.province}">selected</c:if>>${p.disName} </option>
					</c:forEach>
				</select>
			</td>
		</tr>

		<tr name="allplace">
			<th style="width: 120px">市：</th>
			<td>
				<select name="city" id="city" onchange="queryCountys()">

				</select>
			</td>
		</tr>
		<tr name="allplace">
			<th style="width: 120px">区：</th>
			<td>
				<select name="county" id="county">

				</select>
			</td>
		</tr>

		<tr name="allplace">
			<th style="width: 120px">所在行业：</th>
			<td>
				<select name="industry" id="industry">
					<c:forEach items="${industryList}" var="p">
						<option id="${p.id }" <c:if test="${p.id==obj.industry}">selected</c:if> value="${p.id }">${p.listValue } </option>
					</c:forEach>
				</select>
			</td>
		</tr>

		<tr>
			<th>团队介绍：</th>
			<td colspan=3>
				<textarea id="teamPresentation"  name="teamPresentation" style="width:700px;height:100px;">${obj.teamPresentation}</textarea>
			</td>
		</tr>
		<tr>
			<th>产品介绍：</th>
			<td colspan=3>
				<div class="hyjb_box">
					<div class="hyjb_tj">
						<c:if test="${not empty obj}">
							<c:forEach items="${obj.projectSupermarketImagesList}" var="item">
								<ul class="hyjb_dl">
									<li>
										<dl>
											<dt><a href="javascript:void(0)"><img  class="guestImg" src="${item.imageUrl}" /></a></dt>
											<dd><a href="javascript:void(0)"><img onclick="removeImage(this)" src="${path}/resource/images/tjy/close_04.gif" width="25" height="25" /></a></dd>
										</dl>
									</li>
								</ul>
							</c:forEach>
						</c:if>

						<img id="addProjectSupermarketImages" onclick="uploadImg('projectSupermarket')"
							 src="${path}/resource/images/tjy/jia_03.png"
							 style="cursor:pointer;margin-top:12px;margin-left:12px;height:100px;width:120px;" width="112" height="112" />
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<th>是否显示：</th>
			<td>
				<select class="span2" name="isShow">
					<option value="1" <c:if test="${1==obj.isShow}">selected</c:if>>否</option>
					<option value="0" <c:if test="${0==obj.isShow}">selected</c:if>>是</option>
				</select>
			</td>
		</tr>

		<tr>
			<th>是否置顶：</th>
			<td>
				<select class="span2" name="isTop">
					<option value="0" <c:if test="${0==obj.isTop}">selected</c:if>>否</option>
					<option value="1" <c:if test="${1==obj.isTop}">selected</c:if>>是</option>
				</select>
			</td>
		</tr>

		<tr>
			<th style="width:15%;">权重<font color="red">*</font>：</th>
			<td colspan=3>
				<input type="text" maxlength="100" id="sort" name="sort" value="${obj.sort}"  onkeypress="return event.keyCode>=48&&event.keyCode<=57" ng-pattern="/[^a-zA-Z]/"  class="span2" style="width:50%"/>
			</td>
		</tr>

		<tr>
			<th style="width:15%;">发布人手机号<font color="red">*</font>：</th>
			<td colspan=3>
				<input type="text" maxlength="100" id="mobile" name="mobile" value="${obj.mobile}"  onkeypress="return event.keyCode>=48&&event.keyCode<=57" ng-pattern="/[^a-zA-Z]/"  class="span2" style="width:50%"/>
			</td>
		</tr>

		<tr>
			<th></th>
			<td colspan=3>
				<div  style="margin-top: 10px;margin-bottom: 10px;">
					  <button type="button" onclick="save()" class="btn btn-primary" >提交</button>&nbsp;&nbsp;&nbsp;&nbsp;
					  <%--<button type="button" onclick="cancel()" class="btn clear" >取消</button>--%>
				</div>
			</td>
		 </tr>
	</table>
</form>
<span class="typefile" id="filePicker"></span>
</div>
<script src="http://malsup.github.io/jquery.form.js"></script>
<script type="text/javascript">
/////////////////////////////图片上传
var uploader = null;
function delImg(){
	$(".removePic").hide();
	$("#uploadPic").empty();
	$("#uploadPic").append('<span style="display:block;width:80px;height:12px;padding:50px 40px;">点击上传图片</span>');
}
var imgType = "";
function uploadImg(type){
	imgType = type;
	$("#filePicker :file").click();
}
function initWebUpload(){
	uploader = WebUploader.create({
		auto: true,
		server: "projectSupermarket/uploadPic.do",
		pick: "#filePicker",
		fileNumLimit: 5000,//一次最多上传多少张照片
		fileSingleSizeLimit: 2 * 1024*1024,
		duplicate : true,
		accept: {
			title: "Images",
			extensions: "gif,jpg,jpeg,bmp,png",
			mimeTypes: 'image/jpg,image/jpeg,image/png'
		}
	});
	uploader.on("uploadSuccess", function( file, response ) {
		if(response.code==0){
			if(imgType=="hb"){
				$("#hb").attr("src",response.dataobj.img_url+"?x-oss-process=style/YSMAX640");
				$("#hb").attr("status","1");
				$("#hb").removeAttr("onclick")
				$("#deleteHb").show();
			}else if(imgType=="projectSupermarket"){
				var htmlStr =
						'<ul class="hyjb_dl">'
						+'<li>'
						+'<dl>'
						+'<dt><a href="javascript:void(0)"><img  class="guestImg" src="'+response.dataobj.img_url+'?x-oss-process=style/YS200200" /></a></dt>'
						+'<dd><a href="javascript:void(0)"><img onclick="removeImage(this)" src="${path}/resource/images/tjy/close_04.gif" width="25" height="25" /></a></dd>'
						+'</dl>'
						+'</li>'
						+'</ul>';
				$("#addProjectSupermarketImages").before(htmlStr)
			}
		}else{
			alert(response.msg)
		}
	});
	uploader.on( 'uploadError', function( file ) {
		alert("上传失败")
	});
	uploader.on('error', function(handler) {
		if(handler=="F_EXCEED_SIZE"){
			alert("请上传小于2M的PNG、JPG、JPEG图片，建议尺寸为"+(imgType==="hb"?"750*350":"100*100"))
			return;
		}else if(handler=="Q_TYPE_DENIED"){
			alert("请上传小于2M的PNG、JPG、JPEG图片，建议尺寸为"+(imgType==="hb"?"750*350":"100*100"))
			return;
		}
		alert("上传失败")
	});
}

function removeImage(obj){
	$(obj).parent().parent().parent().parent().parent().remove();
}

//////////////////////////////////////

	$(document.body).ready(function(){
		queryCitys();
		queryCountys();
		initWebUpload();
	})

//查询城市
	function queryCitys(){
		//清空select
		$("#city").empty();
		//获取分类id
		var pid = $("#provinceId").val();

		$.ajax({
			url : "projectSupermarket/getCitys.do",
			dataType : "json",
			data : {
				pid :pid
			},
			async : false,
			success : function(data){
				var opstr = "";
				data.forEach(function(obj, index, array) {
					if("${obj.city}"==obj.id){
						opstr+='<option id="'+obj.id+'" value="'+obj.id+'"  selected="selected">'+obj.disName+'</option>';
					}else{
						opstr+='<option id="'+obj.id+'" value="'+obj.id+'">'+obj.disName+'</option>';
					}
				});
				$("#city").append(opstr);
				queryCountys();
			}
		});
	}

	//查询区县
	function queryCountys(){
		//清空select
		$("#county").empty();
		//获取分类id
		var pid = $("#city").val();
		$.ajax({
			url : "projectSupermarket/getCitys.do",
			dataType : "json",
			data : {
				pid :pid
			},
			async : false,
			success : function(data){
				var opstr = "";
				data.forEach(function(obj, index, array) {
					if("${obj.county}"==obj.id){
						opstr+='<option id="'+obj.id+'" value="'+obj.id+'"  selected="selected">'+obj.disName+'</option>';
					}else{
						opstr+='<option id="'+obj.id+'" value="'+obj.id+'">'+obj.disName+'</option>';
					}
				});
				$("#county").append(opstr);
			}
		});
	}

//保存
function save(){

	if($("#projectName").val() == ""){
		alert("请填写项目名称");
		return;
	}
	if($("#introduce").val() == ""){
		alert("请填写项目介绍");
		return;
	}
	if($("#sort").val() == ""){
		alert("请填写权重");
		return;
	}
	if($("#mobile").val() == ""){
		alert("请填写发布人手机号");
		return;
	}

	var imageArr = [];
	//产品介绍
	$(".hyjb_dl .guestImg").each(function(){
		var imgUrl = $(this).attr("src");
		imageArr.push({imageUrl: imgUrl});
	});

	$("#images").val(JSON.stringify(imageArr));

	$("#dataForm").ajaxSubmit({
		url:"projectSupermarket/saveOrUpdate.do",
		type: 'post',	
		data: {},
		dataType:"json",
		success:function(json){
			if(json&&json["code"]==="0"){
				$("#project_datagrid").datagrid('reload');
				$("#addPage").dialog("destroy");
				$("#updatePage").dialog("destroy");
			}else{
				alert(json["msg"]);
			}
		}
	});
}

</script>