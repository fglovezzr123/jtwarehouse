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
<form method="post" action="" id="dataForm">
	<input type="hidden" name="id" id="id" value="${obj.id}">
	<input type="hidden" name="coverImg" id="coverImg" value="${obj.coverImg}">
	<input type="hidden" name="createTime" id="createTime" value="${fns:fmt(obj.createTime,'yyyy-MM-dd HH:mm:ss')}">
	<input type="hidden" name="createUserId" id="createUserId" value="${obj.createUserId}">
	<input type="hidden" name="createUserName" id="createUserName" value="${obj.createUserName}">
	<input type="hidden" name="deleted" id="deleted" value="${obj.deleted}">
	<input type="hidden" name="images" id="projectImages">
	<input type="hidden" name="contentVisibleRange" id="contentVisibleRange" >
	<input type="hidden" name="videoVisibleRange" id="videoVisibleRange" >
	<table class="table table-bordered" style="margin-top:0px;width:99%;">
		<tr>
			<th style="width: 15%;vertical-align:middle">项目封面<font color="red">*</font>：</th>
			<td colspan=3>
				<div class="hyjb_box"  style="margin:0 10px;height:110px;">
					<ul class="hyjb_dl" style="margin:0px;">
				    	<li>
				    		<dl style="margin:0px;">
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
				            			<img id="deleteHb"  <c:if test="${empty obj.coverImg or obj.coverImg==''}">style="display:none;"</c:if> onclick="removeHb(this)" src="${path}/resource/images/tjy/close_04.gif" width="25" height="25" />
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
			<th>权重<font color="red">*</font>：</th>
			<td>
				<input type="text" name="sort"  onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"  class="easyui-numberbox" id="sort"  value="${obj.sort}"   maxlength="3" />
			</td>
		</tr>
		<tr>
			<th style="width:15%;">项目标题<font color="red">*</font>：</th>
			<td colspan=3>
				<input type="text" maxlength="100" name="titles" onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')" id="prjmd_titles"  value="${obj.titles}" class="span2" style="width:98%"/>
			</td>
		</tr>
		<tr>
			<th style="width:15%;">项目名称2<font color="red">*</font>：</th>
			<td colspan=3>
				<input type="text" maxlength="100" name="titles2" id="prjmd_titles2"  onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')" value="${obj.titles2}" class="span2" style="width:98%"/>
			</td>
		</tr>
		<tr>
			<th style="width:15%;">项目类别：</th>
			<td>
				<select class="span2" name="prjType" id="prjType">
					<option value="8006001" <c:if test="${8006001==obj.prjType}">selected</c:if>>项目联营</option>
					<option value="8006002" <c:if test="${8006002==obj.prjType}">selected</c:if>>项目孵化</option>
				</select>
			</td>
			<th>融资主体：</th>
			<td>
				<input type="text" maxlength="100" name="financingSubject" id="prjmd_financingSubject"  style="width:95%" value="${obj.financingSubject}"/>
			</td>
		</tr>
		<tr>
			<th>融资金额：</th>
			<td>
				<input type="text" onkeyup="num(this)" onafterpaste="return false" onpaste="return false" 
					maxlength="10" name="financingAmount" id="prjmd_financingAmount"  style="width:50%" 
					<c:if test="${not empty obj.financingAmount}">value="${fns:fixed(obj.financingAmount)}"</c:if>
					/>万元
			</td>
			<th>融资方式：</th>
			<td>
				<input type="text" maxlength="100" name="financingType" id="prjmd_financingType"  style="width:95%" value="${obj.financingType}"/>
			</td>
		</tr>
		<tr>
			<th>融资用途：</th>
			<td colspan="3">
				<input type="text" maxlength="100" name="financingPurpose" id="prjmd_financingPurpose"  
					style="width:95%" value="${obj.financingPurpose}"/>
			</td>
		</tr>
		<tr>
			<th>所在地点：</th>
			<td>
				<input type="text" maxlength="100" name="region" id="prjmd_region"  style="width:95%" value="${obj.region}"/>
			</td>
			<th>所在行业：</th>
			<td>
				<input type="text" maxlength="100" name="industry" id="prjmd_industry"  style="width:95%" value="${obj.industry}"/>
			</td>
		</tr>
		<tr>
			<th>联系电话：</th>
			<td>
				<input type="text" placeholder="多个时用“;”号隔开" maxlength="100" name="mobiles" id="prjmd_mobiles"  style="width:98%" value="${obj.mobiles}"/>
			</td>
			<th>视频：</th>
			<td>
				<input class="living" readonly="readonly" type="text" id="mau_webinarSubject" name="webinarSubject" value="${obj.webinarSubject}" style="width:200px;"/>
				<input class="living" type="hidden" id="mau_webinarId" name="webinarId" value="${obj.webinarId}"/>
				<button class="btn btn-primary btn-small living" type="button" onclick="addVhallDialog()">选择直播</button>&nbsp;
				<button class="btn btn-primary btn-small living" type="button" onclick="clearall()">清空视频</button>&nbsp;
			</td>
		</tr>
		<tr>
			<th>可提供资料：</th>
			<td colspan=3>
				<input type="text" maxlength="100" name="availableData" id="prjmd_availableData"  style="width:98%" value="${obj.availableData}"/>
			</td>
		</tr>
		<tr>
			<th>是否显示：</th>
			<td>
			    <input type="hidden" name="isApl" value="1"/>
				<select class="span2" name="showEnable">
					<option value="0" <c:if test="${0==obj.showEnable}">selected</c:if>>否</option>
					<option value="1" <c:if test="${1==obj.showEnable}">selected</c:if>>是</option>
				</select>
			</td>
			<th>是否推荐：</th>
			<td>
				<select class="span2" name="isRecommend">
					<option value="0" <c:if test="${0==obj.isRecommend}">selected</c:if>>否</option>
					<option value="1" <c:if test="${1==obj.isRecommend}">selected</c:if>>是</option>
				</select>
			</td>
		</tr>
		<tr>
			<th>项目介绍<font color="red">*</font>：</th>
			<td colspan=3>
				<textarea name="prjPres" onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')" id="prjmd_prjPres" style="width:700px;height:100px;" maxlength="500">${obj.prjPres}</textarea>
			</td>
		</tr>
		<tr>
			<th>项目时间<font color="red">*</font>：</th>
			<td colspan=3>
				<input id="prjmd_startTime" name="startTime" style="width:100px;" size=15 class="Wdate time-field" 
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,isShowClear:false,isShowToday:false})"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,isShowClear:false,isShowToday:false})" 
					value="${fns:fmt(obj.startTime,'yyyy-MM-dd')}"
					readonly="readonly"/>~
				<input id="prjmd_endTime" name="endTime" style="width:100px;" size=15 class="Wdate time-field" 
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,isShowClear:false,isShowToday:false})"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,isShowClear:false,isShowToday:false})" 
					value="${fns:fmt(obj.endTime,'yyyy-MM-dd')}"
					readonly="readonly"/>
			</td>
		</tr>
		<tr>
			<th>项目详细<font color="red">*</font>：</th>
			<td colspan=3>
				<textarea id="prjmd_myEditor"  name="prjDesc" style="width:700px;height:100px;">${obj.prjDesc}</textarea>
				<input type="hidden" id="iscontents" value="0">
			</td>
		</tr>
		<tr>
			<th>项目图片：</th>
			<td colspan=3>
				<div class="hyjb_box" style="min-height:120px;">
    				<div class="hyjb_tj">
   					<c:if test="${not empty obj}">
   						<c:forEach items="${obj.projectImages}" var="item">
    					<ul class="hyjb_dl">
							<li>
								<dl>
									<dt><a href="javascript:void(0)"><img  class="projectImages" src="${item.imageUrl}" /></a></dt>
									<dd><a href="javascript:void(0)"><img onclick="removeProjectImages(this)" src="${path}/resource/images/tjy/close_04.gif" width="25" height="25"></a></dd>
								</dl>
							</li>
						</ul>
   						</c:forEach>
   					</c:if>
   					<c:choose>
   					<c:when test="${empty obj or empty obj.projectImages or obj.projectImages.size()<5}">
   						<img id="addImges" onclick="uploadImg('projectImages')" 
   							src="${path}/resource/images/tjy/jia_03.png" 
   							style="cursor:pointer;margin-top:12px;margin-left:12px;height:100px;width:120px;" width="112" height="112" />
   					</c:when>
   					<c:otherwise>
   						<img id="addImges" onclick="uploadImg('projectImages')" 
   							src="${path}/resource/images/tjy/jia_03.png" 
   							style="display:none;cursor:pointer;margin-top:12px;margin-left:12px;height:100px;width:120px;" width="112" height="112" />
   					</c:otherwise>
   					</c:choose>
    				</div>
				</div>
				<div style="color:gray;float:left;clear: both;"><i>请上传小于2M的PNG、JPG、JPEG图片，建议尺寸为640*384</i></<div>
			</td>
		</tr>
		<tr>
			<th>内容可视范围：</th>
			<td>
				<input type="checkbox" <c:if test="${contentYunYou==true}">checked</c:if> id="contentYunYou" name="contentYunYou" value="1"/>云友
				<input type="checkbox" <c:if test="${contentYunQin==true}">checked</c:if> id="contentYunQin" name="contentYunQin" value="2"/>云亲
				<input type="checkbox" <c:if test="${contentYunShang==true}">checked</c:if> id="contentYunShang" name="contentYunShang" value="4"/>云商
			</td>
			<th>视频可视范围</th>
			<td>
				<input type="checkbox" <c:if test="${videoYunYou==true}">checked</c:if>  id="videoYunYou" name="videoYunYou" value="1"/>云友
				<input type="checkbox" <c:if test="${videoYunQin==true}">checked</c:if> id="videoYunQin" name="videoYunQin" value="2"/>云亲
				<input type="checkbox" <c:if test="${videoYunShang==true}">checked</c:if> id="videoYunShang" name="videoYunShang" value="4"/>云商
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
var validate = [
      {id: "prjmd_titles", text: "请填写项目名称1",type:"text"},
      {id: "prjmd_titles2", text: "请填写项目名称2",type:"text"},
      {id: "prjmd_prjPres", text: "请填写项目介绍",type:"text"},
      {id: "prjmd_startTime", text: "项目开始时间",type:"time"},
      {id: "prjmd_endTime", text: "项目结束时间",type:"time"},
      {id: "prjmd_myEditor", text: "请填写项目详细"}
   ];
var options = {
		cssPath : 'resource/js/editor/plugins/code/prettify.css',
        filterMode : false,
		uploadJson:'qfy/entry/upload.do?ysStyle=YS640&moduleName=project',
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
					$("#iscontents").val(this.count('text'));
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
	initWebUpload();
	/* KindEditor.lang({tel : '电话'});
	KindEditor.plugin('tel', function(K) {
	    var self = this;
	    self.clickToolbar('tel', function() {
	    	var self = this;
		    // 点击图标时执行
		    var html = '<div style="margin:20px;">' +
					'<div><input type="text" id="kindeditor_tel"></div>' +
					'</div>';
			self.createDialog({
				name : 'tel',
				width : 300,
				title : '电话',
				yesBtn : {
					name : '确定',
					click : function(e) {
						var tel = $("#kindeditor_tel").val();
						self.hideDialog();
						if(tel.trim()!=""){
							self.insertHtml('<span>'+tel+'</span><a href="tel:'+tel+'" style="text-decoration:none;"><b class="distant1">拨打</b></a>');
						}
					}
				},
				body:html
			});
	    });
	}); */
	editor = KindEditor.create('#prjmd_myEditor',options);
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
	    server: "project/uploadpic.do",
	    pick:  {id : '#filePicker',multiple: false},
	    fileNumLimit: 5000,//一次最多上传多少张照片
	    fileSingleSizeLimit: 2 * 1024 * 1024*100,
	    duplicate : true,
	    accept: {
	        title: "Images",
	        extensions: "gif,jpg,jpeg,bmp,png",
	        mimeTypes: 'image/jpg,image/jpeg,image/png'
	    },
	    formData: {  
	    }
	});
	uploader.on("uploadSuccess", function( file, response ) {
		if(response.code==0){
			if(imgType=="hb"){
				$("#hb").attr("src",response.dataobj.img_url);
				$("#hb").attr("status","1");
				$("#hb").removeAttr("onclick")
				$("#deleteHb").show();
			}else if(imgType=="projectImages"){
				var len = $(".hyjb_dl .projectImages").length;
				if(len<5){
					var htmlStr = 
						'<ul class="hyjb_dl">'
							+'<li>'
								+'<dl>'
									+'<dt><a href="javascript:void(0)"><img  class="projectImages" src="'+response.dataobj.img_url+'?x-oss-process=style/YSMAX640" /></a></dt>'
									+'<dd><a href="javascript:void(0)"><img onclick="removeProjectImages(this)" src="${path}/resource/images/tjy/close_04.gif" width="25" height="25"></a></dd>'
								+'</dl>'
							+'</li>'
						+'</ul>';
					$("#addImges").before(htmlStr);
					
					len = $(".hyjb_dl .projectImages").length;
					if(len>=5){
						$("#addImges").hide();
					}else{
						$("#addImges").show();
					}
				}
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
			alert("请上传小于2M的PNG、JPG、JPEG图片，建议尺寸为640*384")
			return;
		}else if(handler=="Q_TYPE_DENIED"){
			alert("请上传小于2M的PNG、JPG、JPEG图片，建议尺寸为640*384")
			return;
		}
		alert("上传失败")
	});
}
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
		var value = $("#"+validate[i]["id"]).val()||"";
		var type = validate[i]["type"]||"";
		var maxlength = validate[i]["maxlength"]||1000;
		
		if("float"===type&&(value==""||value<0||value>9999999)){
			alert("请选择"+validate[i]["text"]);
			return;
		}
		if("text"===type&&(value==""||value.cnLength()>maxlength)){
			alert(validate[i]["text"]);
			return; 
		}
		
		if(value==""){
			alert("请填写"+validate[i]["text"]);
			return; 
		}
	}
	if($("#prjmd_startTime").val()>=$("#prjmd_endTime").val()){
		alert("项目开始时间须小于结束时间");
		return;
	}	
	
	$("#coverImg").val($("#hb").attr("src"));
	var projectImagesArr = [];
	$(".hyjb_dl .projectImages").each(function(){
		var imgUrl = $(this).attr("src");
		var name = $(this).parent().parent().parent().parent().next().children(":first").val();
		projectImagesArr.push({imageUrl: imgUrl,name: name});
	});
	
	var iscontents = $("#iscontents").val();
	if(iscontents<= 0){
		Msg.alert("提示","请填写项目详情！","warning",null);
    	return;
	}
	//项目
	var projArr=[];
	$(".projId").each(function(){
		var projId = $(this).attr("data-id");
		projArr.push(projId);
	});
	$("#projIds").val(projArr.join(","));
	$("#projectImages").val(JSON.stringify(projectImagesArr));

	//内容可视范围
	var contentVisibleRange = 0;

	if($("#contentYunYou").is(':checked')) {
		contentVisibleRange = contentVisibleRange | $("#contentYunYou").val();
	}
	if($("#contentYunQin").is(':checked')) {
		contentVisibleRange = contentVisibleRange | $("#contentYunQin").val();
	}
	if($("#contentYunShang").is(':checked')) {
		contentVisibleRange = contentVisibleRange | $("#contentYunShang").val();
	}

	if(contentVisibleRange==0){
		alert("请选择内容可视范围");
		return;
	}
	$("#contentVisibleRange").val(contentVisibleRange);
	//视频可视范围
	var videoVisibleRange = 0;
	if($("#videoYunYou").is(':checked')) {
		videoVisibleRange = videoVisibleRange | $("#videoYunYou").val();
	}
	if($("#videoYunQin").is(':checked')) {
		videoVisibleRange = videoVisibleRange | $("#videoYunQin").val();
	}
	if($("#videoYunShang").is(':checked')) {
		videoVisibleRange = videoVisibleRange | $("#videoYunShang").val();
	}
	if(videoVisibleRange==0){
		alert("请选择视频可视范围");
		return;
	}
	$("#videoVisibleRange").val(videoVisibleRange);

    $.ajax({
		url:"project/projectsaveorupdate.do",
		type: 'post',	
		data: $("#dataForm").serializeObject(),
		cache: false,
		dataType:"json",
		success:function(json){
			if(json&&json["code"]==="0"){
				$("#projectindex_datagrid").datagrid('reload');
				$("#projectaddpage").dialog("destroy");
				$("#projectupdatepage").dialog("destroy");
			}else{
				alert("操作失败");
			}			
		}
	});
}
function cancel(){
	$("#projectaddpage").dialog("destroy");
	$("#projectupdatepage").dialog("destroy");
}
function removeProjectImages(obj){
	$(obj).parent().parent().parent().parent().parent().remove();
	var len = $(".hyjb_dl .projectImages").length;
	if(len>=5){
		$("#addImges").hide();
	}else{
		$("#addImges").show();
	}
}
function removeHb(){
	$("#hb").attr("src","${path}/resource/images/tjy/djsc.png");
	$("#hb").attr("status","0");
	$("#hb").attr("onclick","uploadImg('hb')");
	$("#deleteHb").hide();
}
function delProj(){
	$("#projTbody input:checkbox[name=checkbox]:checked").each(function () {
	    $(this).parent().parent().remove();
	})
}
function num(obj){
	if($(obj).val()==""){
		return;
	}
	obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
	obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字
	obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个, 清除多余的
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数
}
//选择直播视频
function addVhallDialog(){
	var params = {closed: false,cache: false,modal:true,width:700,height:400,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('直播选择','news/vhallindex.do',"vhallindexfornews",params)
}
function clearall(){
	$("#mau_webinarSubject").val("");
	$("#mau_webinarId").val("");
}
</script>