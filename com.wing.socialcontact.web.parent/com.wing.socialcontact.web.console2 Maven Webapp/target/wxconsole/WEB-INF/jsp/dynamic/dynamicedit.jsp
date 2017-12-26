<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
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
.Wdate{
	background-color:#fff !important;
	background-image:none !important;
}
</style>

<div style="width:100%;">						
<form method="post" action="" id="dataForm">
	<input type="hidden" name="zjImgerJson" id="guests"/>
	<input type="hidden" name="id" id="id" value="${dynamic.dynamicMap.id}">
	<input type="hidden" name="mediaId" id="mediaId" value="${dynamic.dynamicMap.mediaId}">
	<input type="hidden" name="mediaPrice" id="mediaPrice" value="${dynamic.dynamicMap.mediaPrice}">
	<input type="hidden" name="mediaSeconds" id="mediaSeconds" value="${dynamic.dynamicMap.mediaSeconds}">
	<input type="hidden" name="visitQuantity" id="visitQuantity" value="${dynamic.dynamicMap.visitQuantity}">
	<input type="hidden" name="allowReword" id="allowReword" value="${dynamic.dynamicMap.allowReword}">
	<input type="hidden" name="allowComment" id="allowComment" value="${dynamic.dynamicMap.allowComment}">
	<input type="hidden" name="articleid" id="articleid" value="${dynamic.dynamicMap.articleid}">
	<input type="hidden" name="atitle" id="atitle" value="${dynamic.dynamicMap.atitle}">
	<input type="hidden" name="aimgpath" id="aimgpath" value="${dynamic.dynamicMap.aimgpath}">
	<input type="hidden" name="aclassname" id="aclassname" value="${dynamic.dynamicMap.aclassname}">
	<input type="hidden" name="adate" id="adate" value="${dynamic.dynamicMap.adate}">
	<table class="table table-bordered" style="margin-top:0px;width:99%;">
		<tr>
			<th style="width:15%;">动态内容<font color="red">*</font>：</th>
			<td colspan=3>
				<textarea id="dy_content"  name="dyContent" rows="3" cols="100" style="width: 95%;" data-options="validType:['length[1,200]']" maxlength="200">${dynamic.dynamicMap.dy_content}</textarea>
			</td>
		</tr>
		<c:if test="${not empty dynamic}">
			<tr>
				<th>动态图片<font color="red">*</font>：</th>
				<td colspan=3>
							<div class="hyjb_box">
			    				<div class="hyjb_tj">
				   						<c:forEach items="${dynamic.picList}" var="item">
				    					<ul class="hyjb_dl">
											<li>
												<dl>
												<dt><a href="javascript:void(0)"><img  class="guestImg" src="${ossurl}${item.picUrl}" picPath="${item.picUrl}"/></a></dt>
												<dd><a href="javascript:void(0)"><img onclick="removeGuest(this)" src="${path}/resource/images/tjy/close_04.gif" width="25" height="25" /></a></dd>
												</dl>
											</li>
										</ul>
				   						</c:forEach>
			   						<img id="addGuest" onclick="uploadImg('guest')" 
			   							src="${path}/resource/images/tjy/jia_03.png" 
			   							style="cursor:pointer;margin-top:12px;margin-left:12px;height:100px;width:120px;" width="112" height="112" />
			    				</div>
							</div>
							<div style="color:gray;"><i>请上传jpg,jpeg,png格式图片,建议尺寸为640*320</i></<div>
				</td>
			</tr>
		</c:if>
		<tr>
			<th>发布人手机号<font color="red">*</font>:</th>
			<td colspan=3>
				<input type="text" id="mobile" style="width:95%;" value="${dynamic.dynamicMap.mobile}"/>
				<input type="hidden" id="userId" name="userId">
				<input type="hidden" id="dy_type" name="dyType" value="0">
			</td>
		</tr>
		<tr>
			<th style="width: 120px">发布时间：</th>
			<td>
				<c:choose>
					<c:when test="${published eq 0 }">
						<input type="text" name="issued_date" id="issued_date" value="<fmt:formatDate  value="${dynamic.dynamicMap.issued_date}" pattern="yyyy-MM-dd HH:mm:ss" />"  maxlength="50" class="easyui-validatebox" required="true"    onFocus="WdatePicker({skin:'whyGreen',startDate:'%y-%M-%d %H:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
					</c:when>
					<c:otherwise>
						<input type="text" name="issued_date" id="issued_date" value="<fmt:formatDate  value="${dynamic.dynamicMap.issued_date}" pattern="yyyy-MM-dd HH:mm:ss" />"  maxlength="50" class="easyui-validatebox" required="true"  readonly="readonly"   />
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<%-- <tr>
			<th style="width: 120px">是否置顶：</th>
			<td>
				<c:choose>
					<c:when test="${dynamic.dynamicMap.is_stick eq 0 }">
						<input type="radio" name="isStick" value="0" style="margin-top: -2px;" checked="checked"/><span style="margin-right:20px;margin-left: 2px;">否</span>
               		<input type="radio" name="isStick" value="1" style="margin-top: -2px;"/><span style="margin-left: 2px;">是</span>
					</c:when>
					<c:otherwise>
						<input type="radio" name="isStick" value="0" style="margin-top: -2px;" /><span style="margin-right:20px;margin-left: 2px;">否</span>
               		<input type="radio" name="isStick" value="1" style="margin-top: -2px;" checked="checked"/><span style="margin-left: 2px;">是</span>
					</c:otherwise>
				</c:choose>
			</td>
		</tr> --%>
		<tr id="zdyh_tr_2">
		    	<th>是否好友可见：</th>
		    	<td>
               		<input type="radio" name="visitType" value="2" style="margin-top: -2px;" checked="checked"/><span style="margin-right:20px;margin-left: 2px;">对所有人可见</span>
               		<input type="radio" name="visitType" value="1" style="margin-top: -2px;"/><span style="margin-left: 2px;">仅对好友可见</span>
               	</td>
		</tr>
		<!-- <tr id="zdyh_tr_2">
		    	<th>是否可打赏：</th>
		    	<td>
               		<input type="radio" name="allowReword" value="1" style="margin-top: -2px;" checked="checked"/><span style="margin-right:20px;margin-left: 2px;">是</span>
               		<input type="radio" name="allowReword" value="0" style="margin-top: -2px;"/><span style="margin-left: 2px;">否</span>
               	</td>
		</tr>
		<tr id="zdyh_tr_2">
		    	<th>是否可评论：</th>
		    	<td>
               		<input type="radio" name="allowComment" value="1" style="margin-top: -2px;" checked="checked"/><span style="margin-right:20px;margin-left: 2px;">是</span>
               		<input type="radio" name="allowComment" value="0" style="margin-top: -2px;"/><span style="margin-left: 2px;">否</span>
               	</td>
		</tr> -->
		<tr>
			<th></th>
			<td colspan=3>
				<div  style="margin-top: 10px;margin-bottom: 10px;">
					  <button type="button" onclick="save()" class="btn btn-primary" >提交</button>&nbsp;&nbsp;&nbsp;&nbsp;
					  <button type="button" onclick="cancel()" class="btn clear" >取消</button>
				</div>
			</td>
		 </tr>
	</table>
</form>
<span class="typefile" id="filePicker"></span>
</div>
<script type="text/javascript">
var uploader = null;
$(function() {
	initWebUpload();
	$("input[name='visitType']").each(function(i){
		if($(this).val() == ${dynamic.dynamicMap.visit_type}){
			$(this).attr("checked","checked");
			return;
		}
	});
});
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
			if(imgType=="guest"){
				var htmlStr = 
					'<ul class="hyjb_dl">'
						+'<li>'
							+'<dl>'
								+'<dt><a href="javascript:void(0)"><img  class="guestImg" src="'+response.dataobj.img_url+'" picPath="'+response.dataobj.picPath+'"/></a></dt>'
								+'<dd><a href="javascript:void(0)"><img onclick="removeGuest(this)" src="${path}/resource/images/tjy/close_04.gif" width="25" height="25" /></a></dd>'
							+'</dl>'
						+'</li>'
					+'</ul>';
				$("#addGuest").before(htmlStr);
			}
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
var flag = true;
function save(){
	if(flag){
		flag = false;
		if($("#dy_content").val() == ""){
			alert("请填写动态内容!");
			flag = true
			return;
		}
		
		var dytypeflag = $("#dytypeflag").val();
		var guestArr = [];
		//嘉宾
		$(".hyjb_dl .guestImg").each(function(){
			var imgUrl = $(this).attr("picPath");
			guestArr.push({"imgUrl": imgUrl});
		});
		if(guestArr.length == 0){
			alert("动态图片不能为空！");
			flag = true
			return ;
		}
		if(guestArr.length > 9){
			alert("动态图片最多能上传9张！");
			flag = true
			return ;
		}
		$("#guests").val(JSON.stringify(guestArr));
		
		if($("#mobile").val() == ""){
			alert("手机号不能为空!");
			flag = true
			return;
		}
		
		var isPhone=/^((\+?86)|(\+86))?(13[012356789][0-9]{8}|15[012356789][0-9]{8}|17[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/; 
		var visitType = $("input[name='visitType']:checked").val();
		if(isPhone.test($("#mobile").val())){
			 $.ajax({
					url:"dynamic/checkMobile.do",
					type: 'post',	
					data: {mobile:$("#mobile").val()},
					cache: false,
					dataType:"json",
					success:function(json){
						if(json&&json["code"]==="0"){
							var obj = json.dataobj;
							if(obj!=null){
								$("#userId").val(obj.id);
								if($("#userId").val() == null || $("#userId").val() == ""){
									alert("手机号对应的用户信息不存在，不能发布!");
									flag = true
									return;
								}
								var fomedate = $("#dataForm").serializeObject();
							    $.ajax({
									url:"dynamic/editdynamic.do",
									type: 'post',	
									data: fomedate,
									cache: false,
									dataType:"json",
									success:function(json){
										if(json&&json["code"]==="0"){
											$("#dynamicindex_datagrid").datagrid('reload');
											$("#dynamiceditpage").dialog("destroy");
										}else{
											alert("操作失败");
										}			
									}
								});
							}else{
								alert("手机用户不存在，请检查！");
							}
						}else{
							alert("操作失败");
						}
						flag = true
					}
				});
			 flag = true
		}else{
			alert("手机号格式不正确!");
			flag = true
			return;
		}
	}
}
function cancel(){
	$("#dynamiceditpage").dialog("destroy");
}
function removeGuest(obj){
	$(obj).parent().parent().parent().parent().parent().remove();
}
</script>