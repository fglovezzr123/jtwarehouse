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
	<input type="hidden" name="coverImg" id="coverImg" value="${obj.coverImg}">
	<input type="hidden" name="createTime" id="createTime" value="${fns:fmt(obj.createTime,'yyyy-MM-dd HH:mm:ss')}">
	<input type="hidden" name="createUserId" id="createUserId" value="${obj.createUserId}">
	<input type="hidden" name="createUserName" id="createUserName" value="${obj.createUserName}">
	<input type="hidden" name="deleted" id="deleted" value="${obj.deleted}">
	<input type="hidden" name="projIds" id="projIds">
	<input type="hidden" name="meetingIds" id="meetingIds">
	<input type="hidden" name=webinarIds id="webinarIds">
	<input type="hidden" name="guests" id="guests">
	<table class="table table-bordered" style="margin-top:0px;width:99%;">
		<tr>
			<th style="width: 15%;vertical-align:middle">会议封面<font color="red">*</font>：</th>
			<td>
				<div class="hyjb_box" style="margin:0 10px;height:110px;">
					<ul class="hyjb_dl" style="margin:0px;">
				    	<li>
				    		<dl style="margin-bottom:0px;">
				            	<dt>
				            		<a href="javascript:void(0)">
		            			<c:choose>
			            			<c:when test="${not empty obj.coverImg and obj.coverImg!=''}">
				            			<img status="1" style="width:100%;height:100%;" 
				            				src="${obj.coverImg}" id="hb"/>
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
				            			<img id="deleteHb" <c:if test="${empty obj.coverImg or obj.coverImg==''}">style="display:none;"</c:if> onclick="removeHb(this)" src="${path}/resource/images/tjy/close_04.gif" width="25" height="25" />
				            		</a>
				            	</dd>
				   	    	</dl>
				   	    </li>
				    </ul>
				</div>
		   	    <div style="color:gray;"><i>请上传小于2M的PNG、JPG、JPEG图片，建议尺寸为640*320</i></<div>
			</td>
			<th style="width: 15%;vertical-align:middle">是否显示：</th>
			<td style="vertical-align:middle">
				<select class="span2" name="showEnable">
					<option value="0" <c:if test="${0==obj.showEnable}">selected</c:if>>否</option>
					<option value="1" <c:if test="${1==obj.showEnable}">selected</c:if>>是</option>
				</select>
			</td>
		</tr>
		<tr>
			<th>权重<font color="red">*</font>：</th>
			<td>
				<input type="text" name="sort"  onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" id="sort" class="easyui-numberbox"  value="${obj.sort}"   maxlength="3" />
			</td>
			<th style="width:15%;">会议主题<font color="red">*</font>：</th>
			<td>
				<input type="text" maxlength="100" name="titles" onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')" id="mau_titles" value="${obj.titles}" class="span2" style="width:95%"/>
			</td>
			
		</tr>
		<tr>
			<th>省<font color="red">*</font>：</th>
			<td>
				<select  name="province"  id="province"  onchange="querycitys(2)">
			    	<c:forEach items="${provinceList}" var="p">
			    	<c:choose>
			    		<c:when test="${obj.province eq p.id }">
			    		<option id="${p.id }" value="${p.id } " selected="selected">${p.disName } </option>
			    		</c:when>
			    		<c:otherwise>
			    		<option id="${p.id }" value="${p.id } ">${p.disName } </option>
			    		</c:otherwise>
			    	</c:choose>
			    	</c:forEach>
			    	</select>
			</td>
			<th>市<font color="red">*</font>：</th>
			<td>
				<select name="city" id="activitycitys" onchange="querycountys()" >
				</select>
			</td>
		</tr>
		<tr>
			<th>区<font color="red">*</font>：</th>
			<td>
				<select name="county" id="activitycountys">
				</select>
			</td>
			<th>详细地址<font color="red">*</font>：</th>
			<td>
				<input type="text" maxlength="100" name="place" onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')" id="mau_place"  style="width:95%" value="${obj.place}"/>
			</td>
		</tr>
		<tr>
			<th>会议时间<font color="red">*</font>：</th>
			<td>
				<input id="mau_startTime" name="startTime"  style="width:120px;" class="Wdate time-field" 
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',readOnly:true,isShowClear:false,isShowToday:false})"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',readOnly:true,isShowClear:false,isShowToday:false})" 
					value="${fns:fmt(obj.startTime,'yyyy-MM-dd HH:mm')}" readonly="readonly"/>~
				<input id="mau_endTime" name="endTime"  style="width:120px;" class="Wdate time-field" 
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',readOnly:true,isShowClear:false,isShowToday:false})"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',readOnly:true,isShowClear:false,isShowToday:false})" 
					value="${fns:fmt(obj.endTime,'yyyy-MM-dd HH:mm')}" readonly="readonly"/>
			</td>
			<th>报名时间<font color="red">*</font>：</th>
			<td>
				<input id="mau_query_startSignupTime" name="startSignupTime" style="width:120px;" size=15 class="Wdate time-field" 
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',readOnly:true,isShowClear:false,isShowToday:false})"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',readOnly:true,isShowClear:false,isShowToday:false})" 
					value="${fns:fmt(obj.startSignupTime,'yyyy-MM-dd HH:mm')}"
					readonly="readonly"/>~
				<input id="mau_query_endSignupTime" name="endSignupTime" style="width:120px;" size=15 class="Wdate time-field" 
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',readOnly:true,isShowClear:false,isShowToday:false})"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',readOnly:true,isShowClear:false,isShowToday:false})" 
					value="${fns:fmt(obj.endSignupTime,'yyyy-MM-dd HH:mm')}"
					readonly="readonly"/>
			</td>
		</tr>
		<tr>
			<th style="width:15%;">会议类型：</th>
			<td style="width:35%;">
				<select class="span2" name="types" id="mau_types" onchange="typesChange(this)">
					<option value="1" <c:if test="${1==obj.types}">selected</c:if>>线上会议</option>
					<option value="2" <c:if test="${2==obj.types}">selected</c:if>>线下会议</option>
					<option value="3" <c:if test="${3==obj.types}">selected</c:if>>线上会议+线下会议</option>
				</select>
			</td>
			<th>主办方<font color="red">*</font>：</th>
			<td>
				<input type="text" name="sponsor" id="mau_sponsor" value="${obj.sponsor}"  maxlength="100" style="width:95%;"/>
			</td>
		</tr>
		<tr class="living">
			<th><p class="living">关联视频直播：</p></th>
			<td>
				<input  readonly="readonly" type="text" id="mau_webinarSubject" name="webinarSubject" value="${obj.webinarSubject}" style="width:200px;"/>
				<input  type="hidden" id="mau_webinarId" name="webinarId" value="${obj.webinarId}"/>
				<button class="btn btn-primary btn-small" type="button" onclick="addVhallDialog()">选择直播</button>&nbsp;
			</td> 
			<th>选集视频：</th>
			<td style="padding:0;">
				<table style="width:100%;margin-top:0;" class="table table-bordered" >
					<thead>
						<tr>
							<td colspan=3>
								<button class="btn btn-primary btn-small living" type="button" onclick="addVhallOver()">选择视频</button>&nbsp;
								<button class="btn btn-primary btn-small" type="button" onclick="delVhall('del')">删除视频</button>&nbsp;
							</td>
						</tr>
						<tr>
							<th style="width:15px;text-align: center;"></th>
							<th style="text-align: center;">视频名称</th>
						</tr>
					</thead>
					<tbody id="vhallTbody">
					  
					<c:if test="${not empty obj}">
   						<c:forEach items="${obj.meetingRelationList}" var="item">
						<tr data-id="${item.webinarId}">
							<td  class="webinarId" data-id="${item.webinarId}"><input type="checkbox" name="checkbox"></td>
							<td id="mau_webinarSubject" name="webinarSubject">${item.webinarSubject}</td>
						
						</tr>
   						</c:forEach>
   					</c:if>		
					</tbody>
				</table>
			</td>
		</tr>
		<tr>
			<th>会议可视范围：</th>
			<td>
				<input type="checkbox" name="meetingRange" value="1"/>云友
				<input type="checkbox" name="meetingRange" value="2"/>云亲
				<input type="checkbox" name="meetingRange" value="3"/>云商
			</td>
			<th>视频可视范围</th>
			<td>
				<input type="checkbox" name="videoRange" value="1"/>云友
				<input type="checkbox" name="videoRange" value="2"/>云亲
				<input type="checkbox" name="videoRange" value="3"/>云商
			</td>
		</tr>
		<%--<tr>
			<th>推荐投融宝首页：</th>
			<td>
				<select class="span2" name="recommendEnable">
					<option value="0" <c:if test="${0==obj.recommendEnable}">selected</c:if>>否</option>
					<option value="1" <c:if test="${1==obj.recommendEnable}">selected</c:if>>是</option>
				</select>
			</td>
			<th>推荐投洽峰会首页</th>
			<td>
				<select class="span2" name="investmentEnable">
					<option value="0" <c:if test="${0==obj.investmentEnable}">selected</c:if>>否</option>
					<option value="1" <c:if test="${1==obj.investmentEnable}">selected</c:if>>是</option>
				</select>
			</td>
		</tr>--%>
		<tr>
			<th>人数上限：</th>
			<td>
				<input maxlength="8" type="text" name="upperlimit" id="mau_upperlimit" value="${obj.upperlimit}" style="width:60px;" 
						onkeyup="num2(this)" onafterpaste="return false" onpaste="return false"/>(0时表示无限制)
			</td>
			<th>门票价格<font color="red">*</font>：</th>
			<td>
				<c:choose>
				<c:when test="${empty obj or empty obj.ticketPrice or obj.ticketPrice ==0 }">
					<select id="ticketPriceType" onchange="ticketTypeChange(this)">
						<option value="1" selected="selected">免费</option>
						<option value="2">收费</option>
					</select>
					<input type="text" style="display:none;" name="ticketPrice" id="mau_ticketPrice" value="${obj.ticketPrice}"
						onkeyup="num(this)" onafterpaste="return false" onpaste="return false"/>元
				</c:when>
				<c:otherwise>
					<select id="ticketPriceType" onchange="ticketTypeChange(this)">
						<option value="1">免费</option>
						<option value="2" selected="selected">收费</option>
					</select>
					<input type="text" maxlength="10" name="ticketPrice" id="mau_ticketPrice" value="${obj.ticketPrice}"
						onkeyup="num(this)" onafterpaste="return false" onpaste="return false"/>元
				</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<th>会议嘉宾：</th>
			<td colspan=3>
				<div class="hyjb_box">
    				<div class="hyjb_tj">
   					<c:if test="${not empty obj}">
   						<c:forEach items="${obj.meetingGuests}" var="item">
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
			<th>会议详情<font color="red">*</font>：</th>
			<td colspan=3>
				<textarea id="mau_myEditor" name="contents" style="width:700px;height:100px;">
					${obj.contents}
				</textarea>
			</td>
		</tr>
		<tr>
			<th>关联项目：</th>
			<td colspan=3>
				<table style="width:100%;" class="table table-bordered" >
					<thead>
						<tr>
							<td colspan=3>
								<button class="btn btn-primary btn-small" type="button" onclick="addProjDialog('add')">选择项目</button>&nbsp;
								<button class="btn btn-primary btn-small" type="button" onclick="delProj('del')">删除项目</button>&nbsp;
							</td>
						</tr>
						<tr>
							<th style="width:15px;text-align: center;"></th>
							<th style="width:60%;text-align: center;">项目名称</th>
							<th style="width:40%;text-align: center;">项目类型</th>
						</tr>
					</thead>
					<tbody id="projTbody">
					<c:if test="${not empty obj}">
   						<c:forEach items="${obj.meetingProjects}" var="item">
						<tr data-id="${item.id}">
							<td class="projId" data-id="${item.id}"><input type="checkbox" name="checkbox"></td>
							<td>${item.titles}</td>
							<td>${item.prjTypeName}</td>
						</tr>
   						</c:forEach>
   					</c:if>		
					</tbody>
				</table>
			</td>
		</tr>
		<tr>
			<th>往届会议：</th>
			<td colspan=3>
				<table style="width:100%;" class="table table-bordered" >
					<thead>
					<tr>
						<td colspan=3>
							<button class="btn btn-primary btn-small" type="button" onclick="addMeetingDialog('add')">选择会议</button>&nbsp;
							<button class="btn btn-primary btn-small" type="button" onclick="delMeetingSuccessive('del')">删除会议</button>&nbsp;
						</td>
					</tr>
					<tr>
						<th style="width:15px;text-align: center;"></th>
						<th style="width:100%;text-align: center;">会议名称</th>
					</tr>
					</thead>
					<tbody id="meetingTbody">
					<c:if test="${not empty obj}">
						<c:forEach items="${obj.meetingSuccessiveList}" var="item">
							<tr data-id="${item.id}">
								<td class="projId" data-id="${item.id}"><input type="checkbox" name="checkbox"></td>
								<td>${item.titles}</td>
							</tr>
						</c:forEach>
					</c:if>
					</tbody>
				</table>
			</td>
		</tr>
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
var options = {
        cssPath : 'resource/js/editor/plugins/code/prettify.css',
        filterMode : false,
		uploadJson:'news/upload.do?ysStyle=YS640&moduleName=meeting',
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
	typesChange();
	initWebUpload();
	querycitys(1);
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
	    server: "meetingApp/uploadpic.do?moduleName=meeting",
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
			alert("请上传小于2M的PNG、JPG、JPEG图片，建议尺寸为"+(imgType==="hb"?"750*350":"100*100"))
			return;
		}else if(handler=="Q_TYPE_DENIED"){
			alert("请上传小于2M的PNG、JPG、JPEG图片，建议尺寸为"+(imgType==="hb"?"750*350":"100*100"))
			return;
		}
		alert("上传失败")
	});
}
var validate = [
   {id: "mau_titles", text: "请填写会议主题",type:"text"},
   {id: "mau_place", text: "请填写会议地点",type:"text"},
   {id: "mau_startTime", text: "会议开始时间",type:"time"},
   {id: "mau_endTime", text: "会议结束时间",type:"time"},
   {id: "mau_query_startSignupTime", text: "报名开始时间",type:"time"},
   {id: "mau_query_endSignupTime", text: "报名截止时间",type:"time"},
   {id: "mau_ticketPrice", text: "门票价格"},
   {id: "mau_sponsor", text: "请填写主办方",type:"text"},
   {id: "mau_webinarId", text: "关联直播视频",type:"select"},
   {id: "mau_myEditor", text: "会议详情"}
];
function save(){
	editor.sync();//同步编辑器的数据到texterea
	//判断封面是否上传
	if($("#hb").attr("status")!="1"){
		alert("请上传封面图片");
		return;
	}
	if($("#sort").val()==''){
		alert("请填写权重");
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
		if($("#mau_types").val()==1||$("#mau_types").val()==3){
			$(".living").show();
		}else{
			$(".living").hide();
		}
		
		var value = $("#"+validate[i]["id"]).val().trim()||"";
		var type = validate[i]["type"]||"";
		var maxlength = validate[i]["maxlength"]||1000;
		if("select"===type&&value==""&&($("#mau_types").val()==1||$("#mau_types").val()==3)){
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
		alert("会议开始时间须小于结束时间");
		return;
	}
	if($("#mau_query_startSignupTime").val()>=$("#mau_query_endSignupTime").val()){
		alert("报名开始时间须小于报名结束时间");
		return;
	}
	if($("#mau_query_endSignupTime").val()>$("#mau_endTime").val()){
		alert("报名结束时间须小于会议结束时间");
		return;
	}
	
	if($("#ticketPriceType").val()==2&&
			($("#mau_ticketPrice").val()==0||$("#mau_ticketPrice").val()>999999.99)){
		alert("请输入门票价格且输入范围为0.01 ~  999999.99");
		return;
	}
	if($("#mau_upperlimit").val()>99999999){
		alert("人数上限不能大于99999999");
		return;
	}
	
	
	$("#coverImg").val($("#hb").attr("src"));
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
	//项目
	var projArr=[];
	$(".projId").each(function(){
		var projId = $(this).attr("data-id");
		projArr.push(projId);
	});
	 
	$("#projIds").val(projArr.join(","));

	//往届会议
	var meetingIdSuccArr=[];
	$(".meetingIdSucc").each(function(){
		var meetingId = $(this).attr("data-id");
		meetingIdSuccArr.push(meetingId);
	});

	$("#meetingIds").val(meetingIdSuccArr.join(","));
	
	//选集视频
	var webinarIdArr=[];
	$(".webinarId").each(function(){
		var webinarId = $(this).attr("data-id");
		webinarIdArr.push(webinarId);
	});
	
	$("#webinarIds").val(webinarIdArr.join(","));
	
	$("#guests").val(JSON.stringify(guestArr));
	
    $.ajax({
		url:"meetingApp/meetingsaveorupdate.do",
		type: 'post',	
		data: $("#dataForm").serializeObject(),
		cache: false,
		dataType:"json",
		success:function(json){
			if(json&&json["code"]==="0"){
				$("#meetingindex_datagrid").datagrid('reload');
				$("#meetingaddpage").dialog("destroy");
				$("#meetingupdatepage").dialog("destroy");
			}else{
				alert("操作失败");
			}			
		}
	});
}
function cancel(){
	$("#meetingaddpage").dialog("destroy");
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
//选择会议(往届会议)
function addMeetingDialog(){
	var params = {closed: false,cache: false,modal:true,width:1000,height:400,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('项目选择','meetingApp/meetingSuccessive/index.do?id=',"meetingSuccessiveindexformeeting",params)
}
//选择项目
function addProjDialog(){
	var params = {closed: false,cache: false,modal:true,width:1000,height:400,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('项目选择','meetingApp/projet/index.do?id=',"projectindexformeeting",params)
}
//选择直播视频
function addVhallDialog(){
	var params = {closed: false,cache: false,modal:true,width:1000,height:400,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('直播选择','meetingApp/vhall/index.do?id=',"vhallindexformeeting",params)
}
//选择往期视频
function addVhallOver(){
	var params = {closed: false,cache: false,modal:true,width:1000,height:400,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('直播选择','meetingApp/vhallapp/index.do?id=',"vhallindexformeeting",params)
}

//往届会议
function addMeetingSuccessive(arr){

	for(var i=0;i<arr.length;i++){
		if($("#meetingTbody tr[data-id='"+arr[i].id+"']").length>0){
			continue;
		}
		var $tr = $("<tr data-id=\""+arr[i].id+"\"></tr>");
		$tr.append('<td class="meetingIdSucc" data-id="'+arr[i].id+'"><input type="checkbox" name="checkbox"></td>');
		$tr.append('<td>'+arr[i].titles+'</td>');
		$("#meetingTbody").append($tr);
	}
}
function delMeetingSuccessive(){
	if($("#meetingTbody input:checkbox[name=checkbox]:checked").length==0){
		Msg.alert("提示","请选择会议！","warning",null);
		return;
	}
	$("#meetingTbody input:checkbox[name=checkbox]:checked").each(function () {
		$(this).parent().parent().remove();
	})
}

function addProject(arr){

	for(var i=0;i<arr.length;i++){
		if($("#projTbody tr[data-id='"+arr[i].id+"']").length>0){
			continue;
		}
		var $tr = $("<tr data-id=\""+arr[i].id+"\"></tr>");
		$tr.append('<td class="projId" data-id="'+arr[i].id+'"><input type="checkbox" name="checkbox"></td>');
		$tr.append('<td>'+arr[i].titles+'</td>');
		$tr.append('<td>'+arr[i].prjTypeName+'</td>');
		$("#projTbody").append($tr);
	}
}
function delProj(){
	if($("#projTbody input:checkbox[name=checkbox]:checked").length==0){
		Msg.alert("提示","请选择项目！","warning",null);
		return;
	}
	$("#projTbody input:checkbox[name=checkbox]:checked").each(function () {
	    $(this).parent().parent().remove();
	})
}
//删除视频
function delVhall(){
	if($("#vhallTbody input:checkbox[name=checkbox]:checked").length==0){
		Msg.alert("提示","请选择视频！","warning",null);
		return;
	}
	$("#vhallTbody input:checkbox[name=checkbox]:checked").each(function () {
	    $(this).parent().parent().remove();
	})
}
function addVhall(arr){

	for(var i=0;i<arr.length;i++){
		if($("#vhallTbody tr[data-id='"+arr[i].id+"']").length>0){
			continue;
		}
		var $tr = $("<tr data-id=\""+arr[i].id+"\"></tr>");
		$tr.append('<td class="webinarId" data-id="'+arr[i].id+'"><input type="checkbox" name="checkbox"></td>');
		/* $tr.append('<td>'+arr[i].id+'</td>'); */
		$tr.append('<td>'+arr[i].subject+'</td>');
		$("#vhallTbody").append($tr);
	}
}


function num(obj){
	obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
	obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字
	//obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个, 清除多余的
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数
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
function typesChange(obj){
	if($("#mau_types").val()==1||$("#mau_types").val()==3){
		$(".living").show();
	}else{
		$(".living").hide();
	}
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
				if("${obj.city}"==obj.id){
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
				if("${obj.county}"==obj.id){
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