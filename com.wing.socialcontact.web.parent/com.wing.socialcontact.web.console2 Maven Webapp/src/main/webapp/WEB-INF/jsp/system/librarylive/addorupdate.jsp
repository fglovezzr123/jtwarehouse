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
	<input type="hidden" name="id" id="id" value="${obj.id}">
	<input type="hidden" name="imgpath" id="imgpath" value="${obj.imgpath}">
	<input type="hidden" name="imagepath" id="imagepath" value="${obj.imagepath}">
	<input type="hidden" name="createTime" id="createTime" value="${fns:fmt(obj.createTime,'yyyy-MM-dd HH:mm:ss')}">
	<input type="hidden" name="createUserId" id="createUserId" value="${obj.createUserId}">
	<input type="hidden" name="deleted" id="deleted" value="${obj.deleted}">
	<input type="hidden" name="guests" id="guests">
	<table class="table table-bordered" style="margin-top:0px;width:99%;">
	 	<!-- <tr>
			    	<th style="width: 80px">图片：</th>
			    	<td >
                		<span id="nimgs" title="点击删除" ></span>
                	   	<input  onclick="doSelectPic()"  type="button" value="上传图片" id="picturefile" class="form_shangchuan" />
	                    <iframe id="uploadPicFrame" src="" style="display:none;"></iframe>
	                    <strong style="color:#F00">请上传jpg,jpeg,png格式图片,建议尺寸640*320px。</strong>         	
                	</td>
			  </tr>
		<tr>
	 	<tr>
			    	<th style="width: 80px">会所图片：</th>
			    	<td >
                		<span id="nimgs" title="点击删除" ></span>
                	   	<input  onclick="doSelectPic()"  type="button" value="上传图片" id="picturefile" class="form_shangchuan" />
	                    <iframe id="uploadPicFrame" src="" style="display:none;"></iframe>
	                    <strong style="color:#F00">请上传jpg,jpeg,png格式图片,建议尺寸640*320px。</strong>         	
                	</td>
		</tr> -->
		<tr>
			<th style="width: 15%;vertical-align:middle">列表封面<font color="red">*</font>：</th>
			<td colspan=3>
				<div class="hyjb_box" style="margin:0 10px;height:110px;">
					<ul class="hyjb_dl" style="margin:0px;">
				    	<li>
				    		<dl style="margin-bottom:0px;">
				            	<dt>
				            		<a href="javascript:void(0)">
		            			<c:choose>
			            			<c:when test="${not empty obj.imgpath and obj.imgpath!=''}">
				            			<img status="1" style="width:100%;height:100%;" 
				            				src="${obj.imgpath}" id="hb"/>
			            			</c:when>
			            			<c:otherwise>
				            			<img status="0" style="width:100%;height:100%;" 
				            				src="${path}/resource/images/tjy/djsc.png" id="hb" onclick="uploadImg('hb')" />
			            			</c:otherwise>
		            			</c:choose>
				            		</a>
				            	</dt>
				            	<dd>
				            		<a href="javascript:void(0)">
				            			<img id="deleteHb" <c:if test="${empty obj.imgpath or obj.imgpath==''}">style="display:none;"</c:if> onclick="removeHb(this)" src="${path}/resource/images/tjy/close_04.gif" width="25" height="25" />
				            		</a>
				            	</dd>
				   	    	</dl>
				   	    </li>
				    </ul>
				</div>
		   	    <div style="color:gray;"><i>请上传小于2M的PNG、JPG、JPEG图片，建议尺寸为640*384</i></<div>
			</td>
		</tr>
		<tr>
			<th style="width: 15%;vertical-align:middle">详情封面<font color="red">*</font>：</th>
			<td colspan=3>
				<div class="hyjb_box" style="margin:0 10px;height:110px;">
					<ul class="hyjb_dl" style="margin:0px;">
				    	<li>
				    		<dl style="margin-bottom:0px;">
				            	<dt>
				            		<a href="javascript:void(0)">
		            			<c:choose>
			            			<c:when test="${not empty obj.imagepath and obj.imagepath!=''}">
				            			<img status="1" style="width:100%;height:100%;" 
				            				src="${obj.imagepath}" id="hb1"/>
			            			</c:when>
			            			<c:otherwise>
				            			<img status="0" style="width:100%;height:100%;" 
				            				src="${path}/resource/images/tjy/djsc.png" id="hb1" onclick="uploadImg('hb1')" />
			            			</c:otherwise>
		            			</c:choose>
				            		</a>
				            	</dt>
				            	<dd>
				            		<a href="javascript:void(0)">
				            			<img id="deleteHb1" <c:if test="${empty obj.imagepath or obj.imagepath==''}">style="display:none;"</c:if> onclick="removeHb1(this)" src="${path}/resource/images/tjy/close_04.gif" width="25" height="25" />
				            		</a>
				            	</dd>
				   	    	</dl>
				   	    </li>
				    </ul>
				</div>
		   	    <div style="color:gray;"><i>请上传小于2M的PNG、JPG、JPEG图片，建议尺寸为640*384</i></<div>
			</td>
		</tr>
		<tr>
			<th style="width:15%;">直播主题<font color="red">*</font>：</th>
			<td>
				<input type="text" maxlength="100" name="title" id="mau_titles" value="${obj.title}" class="span2" style="width:95%"/>
			</td>
			<th>是否显示：</th>
			<td>
				<select class="span2" name="isShow">
					<option value="1" <c:if test="${1==obj.isShow}">selected</c:if>>是</option>
					<option value="0" <c:if test="${0==obj.isShow}">selected</c:if>>否</option>
				</select>
			</td>
		</tr>
		<tr>
			<th style="width:15%;">直播类型：</th>
			<td style="width:35%;">
				<select class="span2" name="type" id="mau_types" >
					<option value="1" <c:if test="${1==obj.type}">selected</c:if>>俊卿解惑</option>
					<option value="2" <c:if test="${2==obj.type}">selected</c:if>>与总统谈心</option>
					<option value="3" <c:if test="${3==obj.type}">selected</c:if>>商界冠军直播秀</option>
					<option value="4" <c:if test="${4==obj.type}">selected</c:if>>总裁读书会</option>
				</select>
			</td>
			<th>书籍名称：</th>
			<td>
				<input type="text" name="bookname"  value="${obj.bookname}" style="width:95%;"/>
			</td>
		</tr>
		<tr>
			<th>直播时间<font color="red">*</font>：</th>
			<td>
				<input id="mau_startTime" name="startTime"  style="width:120px;" class="Wdate time-field" 
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true,isShowClear:false,isShowToday:false})"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true,isShowClear:false,isShowToday:false})" 
					value="${fns:fmt(obj.startTime,'yyyy-MM-dd HH:mm')}" readonly="readonly"/>~
				<input id="mau_endTime" name="endTime"  style="width:120px;" class="Wdate time-field" 
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true,isShowClear:false,isShowToday:false})"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true,isShowClear:false,isShowToday:false})" 
					value="${fns:fmt(obj.endTime,'yyyy-MM-dd HH:mm')}" readonly="readonly"/>
			</td>
			<th><p class="living">关联视频直播：</p></th>
			<td>
				<input class="living" readonly="readonly" type="text" id="mau_webinarSubject" name="webinarSubject" value="${obj.webinarSubject}" style="width:200px;"/>
				<input class="living" type="hidden" id="mau_webinarId" name="webinarId" value="${obj.webinarId}"/>
				<button class="btn btn-primary btn-small living" type="button" onclick="addVhallDialog()">选择直播</button>&nbsp;
			</td>
		</tr>
		<tr>
			<th>价格<font color="red">*</font>：</th>
			<td>
				<c:choose>
				<c:when test="${empty obj or empty obj.ticketPrice or obj.ticketPrice ==0 }">
					<select id="ticketPriceType" onchange="ticketTypeChange(this)">
						<option value="1" selected="selected">免费</option>
						<option value="2">收费</option>
					</select>
					<input type="text" style="display:none;" name="ticketPrice" id="mau_ticketPrice" value="${obj.ticketPrice}"
						onkeyup="num(this)" onafterpaste="return false" onpaste="return false"/>J币
				</c:when>
				<c:otherwise>
					<select id="ticketPriceType" onchange="ticketTypeChange(this)">
						<option value="1">免费</option>
						<option value="2" selected="selected">收费</option>
					</select>
					<input type="text" maxlength="10" name="ticketPrice" id="mau_ticketPrice" value="${obj.ticketPrice}"
						onkeyup="num(this)" onafterpaste="return false" onpaste="return false"/>J币
				</c:otherwise>
				</c:choose>
			</td>
			<th>直播结束后是否收费：</th>
			<td>
				<select class="span2" name="isEnd">
					<option value="1" <c:if test="${1==obj.isEnd}">selected</c:if>>是</option>
					<option value="0" <c:if test="${0==obj.isEnd}">selected</c:if>>否</option>
				</select>
			</td>
		</tr>
		<tr>
			<th>权重<font color="red">*</font>：</th>
			<td>
				<input type="text" name="sort"  onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" id="mau_sort"class="easyui-numberbox"   value="${obj.sort}"   maxlength="3" />
			</td>
		</tr>
		<tr>
			<th>直播嘉宾：</th>
			<td colspan=3>
				<div class="hyjb_box">
    				<div class="hyjb_tj">
   					<c:if test="${not empty obj}">
   						<c:forEach items="${obj.liveGuests}" var="item">
    					<ul class="hyjb_dl">
							<li>
								<dl>
									<dt><a href="javascript:void(0)"><img  class="guestImg" src="${item.imgUrl}" /></a></dt>
									<dd><a href="javascript:void(0)"><img onclick="removeGuest(this)" src="${path}/resource/images/tjy/close_04.gif" width="25" height="25" /></a></dd>
								</dl>
							</li>
							<li class="hyjb_shuru"><input maxlength="20" class="guestName" value="${item.name}" type="text" placeholder="请输入嘉宾名称" /></li>
						</ul>
   						</c:forEach>
   					</c:if>
    				
   						<img id="addGuest" onclick="uploadImg('guest')" 
   							src="${path}/resource/images/tjy/jia_03.png" 
   							style="cursor:pointer;margin-top:12px;margin-left:12px;height:100px;width:120px;" width="112" height="112" />
    				</div>
				</div>
			</td>
		</tr>
		<tr>
			<th>详情<font color="red">*</font>：</th>
			<td colspan=3>
				<textarea id="mau_myEditor" name="content" style="width:700px;height:100px;">
					${obj.content}
				</textarea>
			</td>
		</tr>
		<tr>
			<th></th>
			<td colspan=3>
				<div  style="margin-top: 10px;margin-bottom: 10px;">
					  <button type="button" onclick="save()" class="btn btn-primary" >提交</button>&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
			</td>
		 </tr>
	</table>
</form>
<span class="typefile" id="filePicker"></span>
</div>
<script type="text/javascript">
var options = {
        cssPath : 'resource/js/editor/plugins/code/prettify.css',
        filterMode : false,
		uploadJson:'news/upload.do?ysStyle=YS640&moduleName=live',
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
var uploader = null;
$(function() {
	if($("#ticketPriceType").val()==1){
		$("#mau_ticketPrice").val(0);
		$("#mau_ticketPrice").hide();
	}else{
		$("#mau_ticketPrice").show();
	}
	initWebUpload();
	editor = KindEditor.create('#mau_myEditor',options);
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
	    server: "meeting/uploadpic.do?moduleName=live",
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
				$("#hb").attr("src",response.dataobj.img_url);
				$("#hb").attr("status","1");
				$("#hb").removeAttr("onclick")
				$("#deleteHb").show();
			}else if(imgType=="hb1"){
				$("#hb1").attr("src",response.dataobj.img_url);
				$("#hb1").attr("status","1");
				$("#hb1").removeAttr("onclick")
				$("#deleteHb1").show();
			}else if(imgType=="guest"){
				var htmlStr = 
					'<ul class="hyjb_dl">'
						+'<li>'
							+'<dl>'
								+'<dt><a href="javascript:void(0)"><img  class="guestImg" src="'+response.dataobj.img_url+'?x-oss-process=style/YS200200" /></a></dt>'
								+'<dd><a href="javascript:void(0)"><img onclick="removeGuest(this)" src="${path}/resource/images/tjy/close_04.gif" width="25" height="25" /></a></dd>'
							+'</dl>'
						+'</li>'
						+'<li class="hyjb_shuru"><input class="guestName" type="text" placeholder="请输入嘉宾名称" /></li>'
					+'</ul>';
				$("#addGuest").before(htmlStr)
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
			alert("请上传小于2M的PNG、JPG、JPEG图片，建议尺寸为"+(imgType==="guest"?"100*100":"640*384"))
			return;
		}else if(handler=="Q_TYPE_DENIED"){
			alert("请上传小于2M的PNG、JPG、JPEG图片，建议尺寸为"+(imgType==="guest"?"100*100":"640*384"))
			return;
		}
		alert("上传失败")
	});
}
var validate = [
   {id: "mau_titles", text: "请填写直播主题且输入范围为1-100字符",maxlength:100,type:"text"},
   {id: "mau_startTime", text: "直播开始时间",type:"time"},
   {id: "mau_sort", text: "权重",type:"text"},
   {id: "mau_endTime", text: "直播结束时间",type:"time"},
   {id: "mau_ticketPrice", text: "门票价格(J币)"},
   {id: "mau_webinarId", text: "关联直播视频",type:"select"},
   {id: "mau_myEditor", text: "详情"}
];
function save(){
	editor.sync();//同步编辑器的数据到texterea
	//判断封面是否上传
	if($("#hb").attr("status")!="1"){
		alert("请上传列表封面图片");
		return;
	}
	if($("#hb1").attr("status")!="1"){
		alert("请上传详情封面图片");
		return;
	}
	for(var i in validate){
		if(validate[i]["id"]=="mau_myEditor"){
			if(editor.text().trim().length==0){
				alert("请填写"+validate[i]["text"]);
				return; 
			}else{
				continue;
			}
		}
		if(validate[i]["id"]=="mau_ticketPrice"){
			if($("#ticketPriceType").val()==1){
				$("#mau_ticketPrice").hide(0);
			}else{
				$("#mau_ticketPrice").show();
			}
		}
		var value = $("#"+validate[i]["id"]).val().trim()||"";
		var type = validate[i]["type"]||"";
		var maxlength = validate[i]["maxlength"]||1000;
		if("select"===type&&value==""){
			alert("请选择"+validate[i]["text"]);
			return;
		}
		
		if("text"===type&&(value==""||value.cnLength()>maxlength)){
			alert(validate[i]["text"]);
			return; 
		}
		
		if("select"!=type&&value==""){
			alert("请填写"+validate[i]["text"]);
			return; 
		}
	}
	if($("#mau_startTime").val()>=$("#mau_endTime").val()){
		alert("开始时间须小于结束时间");
		return;
	}
	if($("#ticketPriceType").val()==2&&
			($("#mau_ticketPrice").val()==0||$("#mau_ticketPrice").val()>999999.99)){
		alert("请输入门票价格且输入范围为1 ~  999999");
		return;
	}
	
	$("#imgpath").val($("#hb").attr("src"));
	$("#imagepath").val($("#hb1").attr("src"));
	var guestArr = [];
	//嘉宾名称判断
	var guestOk = true;
	$(".hyjb_dl .guestName").each(function(){
		if(guestOk&&($(this).val().trim()==""||$(this).val().cnLength()>20)){
			guestOk =false;
		}
	});
	if(!guestOk){
		alert("请输入嘉宾名称且输入范围为1-20字符");
		return;
	}
	//嘉宾
	$(".hyjb_dl .guestImg").each(function(){
		var imgUrl = $(this).attr("src");
		var name = $(this).parent().parent().parent().parent().next().children(":first").val();
		guestArr.push({imgUrl: imgUrl,name: name});
	});
	$("#guests").val(JSON.stringify(guestArr));
	
	//$("#dataForm").submit();
    $.ajax({
		url:"librarylive/addupdate.do",
		type: 'post',	
		async:false,
		data: $("#dataForm").serializeObject(),
		cache: false,
		dataType:"json",
		success:function(json){
			if(json&&json["code"]==="0"){
				$("#znt_zbgl_datagrid").datagrid('reload');
				$("#znt_zbgl_add").dialog("destroy");
				$("#znt_zbgl_update").dialog("destroy");
			}else{
				alert("操作失败");
			}			
		},
		error:function(){
		}
	});
}
function removeGuest(obj){
	$(obj).parent().parent().parent().parent().parent().remove();
}
function removeHb(){
	$("#hb").attr("src","${path}/resource/images/tjy/djsc.png");
	$("#hb").attr("status","0");
	$("#hb").attr("onclick","uploadImg('hb')");
	$("#deleteHb").hide();
}
function removeHb1(){
	$("#hb1").attr("src","${path}/resource/images/tjy/djsc.png");
	$("#hb1").attr("status","0");
	$("#hb1").attr("onclick","uploadImg('hb1')");
	$("#deleteHb1").hide();
}
//选择直播视频
function addVhallDialog(){
	var params = {closed: false,cache: false,modal:true,width:1000,height:400,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('直播选择','meeting/vhall/index.do?id=',"vhallindexformeeting",params)
}
function num(obj){
	obj.value = obj.value.replace(/[^\d]/g,""); //清除"数字"以外的字符
	obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字
	obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个, 清除多余的
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
}
function num2(obj){
	obj.value = obj.value.replace(/[^\d]/g,""); //清除"数字"和"."以外的字符
}
function ticketTypeChange(type){
	if($("#ticketPriceType").val()==1){
		$("#mau_ticketPrice").val(0);
		$("#mau_ticketPrice").hide();
	}else{
		$("#mau_ticketPrice").val(0);
		$("#mau_ticketPrice").show();
	}
}
</script>