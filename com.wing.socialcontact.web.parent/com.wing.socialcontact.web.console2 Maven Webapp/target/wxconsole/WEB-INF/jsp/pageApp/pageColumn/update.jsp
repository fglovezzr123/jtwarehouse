<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理-- 聚合页面管理--栏目修改
--%>

<div style="width: 98%;margin: 0 auto;" >
	<form  action="pageConfigApp/pageColumn/update.do"  method="post" id="pageColumn_form" enctype="multipart/form-data" >
		<h3>栏目基础信息</h3>
		<table class="table table-bordered">
			<tr>
				<th width="10%">栏目名称:</th>
				<td width="40%"><input type="text" name="columnName" maxlength="10" value="${b.columnName }"/></td>
				<%--<td width="50%" rowspan="5" id="mode_1_3">
					<div style="border: 0px #ccc solid;margin: 5px;height: 100%;" id="yl_area">
						模板预览
					</div>
				</td>--%>
			</tr>
			<tr>
				<th>栏目类型:</th>
				<td>
					<select name="columnType" id="columnType" class="easyui-validatebox" onchange="change_type(this);" style="width: 120px;">
						<option value="1">普通栏目</option>
						<option value="2">动态栏目</option>
					</select>
				</td>
			</tr>
			<%--<tr id="mode_1_1">
				<th>展示模板:</th>
				<td>
					<select name="showStyle" class="easyui-validatebox" onchange="view_model(this);" style="width: 120px;">
						<option value="0">请选择</option>
						<option value="1">模板一</option>
						<option value="2">模板二</option>
						<option value="3">模板三</option>
						<option value="4">模板四</option>
						<option value="5">模板五</option>
						<option value="6">模板六</option>
						<option value="7">模板七</option>
					</select>
				</td>
			</tr>--%>
			<tr id="mode_2_1" style="display: none;">
				<th>元素数量:</th>
				<td>
					<input type="text" name="columnNum" id="columnNum" class="easyui-validatebox" maxlength="5" onkeyup="clearNoNum2(this);" value="${b.columnNum }"/>
				</td>
			</tr>
			<tr id="mode_2_2" style="display: none;">
				<th>元素内容:</th>
				<td>
					<select name="elementKey" id="elementKey" class="easyui-validatebox" style="width: 120px;">
					</select>
				</td>
			</tr>
			<%--<tr>
				<th>“更多”链接:</th>
				<td><input type="text" name="moreUrl" maxlength="150" value="${b.moreUrl }"/></td>
			</tr>--%>
			<tr>
				<th>排序:</th>
				<td><input type="text" name="orderNum" class="easyui-validatebox" required="true" maxlength="5" onkeyup="clearNoNum2(this);" value="${b.orderNum }"/></td>
			</tr>
			<tr>
				<th>是否快速导航:</th>
				<td>
					<input type="radio" name="columnStatus" value="0">否
					<input type="radio" name="columnStatus" value="1">是
				</td>
			</tr>
		</table>
		<div id="mode_1_2">
			<h3>栏目元素信息<a href="javascript:add_element();" style="float: right;font-weight: normal;font-size: 14px;">增加元素</a></h3>
			<div id="pageElement">
				<c:forEach items="${b.elementList }" var="element">
					<table class="table table-bordered">
						<!-- 
						<thead style="display: none;">
							<tr>
								<td colspan="4" style="text-align: center;font-weight: bold;"  id="title_num">元素1</td>
							</tr>
						</thead>
						-->
						<tr>
							<th width="10%">标题1:</th>
							<td width="40%"><input type="text" name="titleOne" class="easyui-validatebox" data-options="validType:['length[1,20]']"  maxlength="20" value="${element.titleOne }"/></td>
							<td width="40%" rowspan="4" style="text-align: center;vertical-align: middle;">
								<div style="position:relative;width: 100px;">
									<img id="upload_img" src="${ossurl}${element.imgUrl}" style="cursor:pointer;margin-top:12px;margin-left:12px;height:80px;width:80px;"/>
									<img onclick="removeGuest(this)" id="removeButton" src="${path}/resource/images/tjy/close_04.gif" width="25" height="25" style="position: absolute;left: 85px;top:0;"/>
									<img id="addGuest" onclick="uploadImg('element',this)" src="${path}/resource/images/tjy/jia_03.png" style="cursor:pointer;margin-top:12px;margin-left:12px;height:80px;width:80px;display: none;" title="上传入口图片"/>
									<input type="hidden" name="imgUrl" value="${element.imgUrl }"/>
									<span style="position: absolute;top:40px;margin-left: 20px;color: red;width: 160px;" name="jycc"></span>
								</div>
							</td>
							<td width="10%" rowspan="4" style="text-align: center;vertical-align: middle;">
								<a onclick="remove_element(this,'${element.id}');" href="javascript:void(0);">删除</a>
							</td>
						</tr>
						<tr>
							<th>标题2:</th>
							<td><input type="text" name="titleTwo" value="${element.titleTwo }" maxlength="20"/></td>
						</tr>
						<tr>
							<th>显示内容:</th>
							<td>
								<select name="contentTypeId" class="easyui-validatebox" onchange="change_url(this);" style="width: 120px;">
									<option value="0">请选择</option>
									<c:choose>
										<c:when test="${element.contentTypeId eq 'zdy'}">
											<c:forEach items="${pageContentTypeList }" var="ct">
												<option value="${ct.id }" listUrl="${ct.listUrl }" detailUrl="${ct.detailUrl }">${ct.name }</option>
											</c:forEach>
											<option value="zdy" selected="selected">自定义</option>
										</c:when>
										<c:otherwise>
											<c:forEach items="${pageContentTypeList }" var="ct">
												<c:choose>
													<c:when test="${ct.id eq element.contentTypeId }">
														<option value="${ct.id }" listUrl="${ct.listUrl }" detailUrl="${ct.detailUrl }" selected="selected">${ct.name }</option>
													</c:when>
													<c:otherwise>
														<option value="${ct.id }" listUrl="${ct.listUrl }" detailUrl="${ct.detailUrl }">${ct.name }</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
											<option value="zdy">自定义</option>
										</c:otherwise>
									</c:choose>
								</select>
							</td>
						</tr>
						<tr>
							<th>
								跳转类型
							</th>
							<td>
								<select name="jumpType">
									<option value="1" <c:if test='${element.jumpType=="1"}'>selected="selected"</c:if>>APP原生</option>
									<option value="2" <c:if test='${element.jumpType=="2"}'>selected="selected"</c:if>>H5</option>
									<option value="3" <c:if test='${element.jumpType=="3"}'>selected="selected"</c:if>>视频</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>内容链接:</th>
							<td>
								<a href="" lookupGroup="content" title="元素内容链接查询" width="1000" height="500" targetBox="td" warn="请先选择显示内容">
									<input type="text" name="contentName" toName="content.name" class="easyui-validatebox" readonly="readonly" value="${element.contentName }"/>
									<input type="hidden" name="contentId" toName="content.id" value="${element.contentId }"/>
								</a>
								<input type="text" name="detailUrl" value="${element.detailUrl }" class="easyui-validatebox" style="display: none;width: 280px;"/>
								<input type="hidden" name="elementId" value="${element.id }"/>
								<input type="hidden" name="columnId" value="${element.columnId }"/>
							</td>
						</tr>
					</table>
				</c:forEach>
			</div>
		</div>
		<div  style="margin-top: 10px;margin-bottom: 10px;">
		  	<button type="button" class="btn btn-primary"  onclick="save()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
		  	<!-- <button type="button" class="btn clear" >清空</button> -->
		</div>
		<input type="hidden" name="pageId" value="${b.pageId }"/>
		<input type="hidden" name="id" value="${b.id }"/>
		<input type="hidden" name="elementValue" id="elementValue" value="${b.elementValue }"/>
		<input type="hidden" name="elementStr" id="elementStr"/>
		<input type="hidden" name="datagridId" value="${param.rel }_datagrid" />
		<input type="hidden" name="currentCallback" value="close" />
	</form>
	<span class="typefile" id="filePicker"></span>
</div>
<div style="display: none;" id="demo">
	<table class="table table-bordered">
		<!-- 
		<thead style="display: none;">
			<tr>
				<td colspan="4" style="text-align: center;font-weight: bold;" id="title_num">元素1</td>
			</tr>
		</thead>
		-->
		<tr>
			<th width="10%">标题1:</th>
			<td width="40%"><input type="text" name="titleOne" class="easyui-validatebox" data-options="validType:['length[1,20]']"  maxlength="20" /></td>
			<td width="40%" rowspan="4" style="text-align: center;vertical-align: middle;">
				<div style="position:relative;width: 100px;">
					<img onclick="removeGuest(this)" id="removeButton" src="${path}/resource/images/tjy/close_04.gif" width="25" height="25" style="position: absolute;left: 85px;top:0;display:none;"/>
					<img id="addGuest" onclick="uploadImg('element',this)" src="${path}/resource/images/tjy/jia_03.png" style="cursor:pointer;margin-top:12px;margin-left:12px;height:80px;width:80px;" title="上传入口图片"/>
					<input type="hidden" name="imgUrl"/>
					<span style="position: absolute;top:40px;margin-left: 20px;color: red;width: 160px;" name="jycc"></span>
				</div>
			</td>
			<td width="10%" rowspan="4" style="text-align: center;vertical-align: middle;">
				<a onclick="remove_element(this);" href="javascript:void(0);">删除</a>
			</td>
		</tr>
		<tr>
			<th>标题2:</th>
			<td><input type="text" name="titleTwo" maxlength="20" /></td>
		</tr>
		<tr>
			<th>显示内容:</th>
			<td>
				<select name="contentTypeId" class="easyui-validatebox" required="true" onchange="change_url(this);" style="width: 120px;">
					<option value="0">请选择</option>
					<c:forEach items="${pageContentTypeList }" var="ct">
						<option value="${ct.id }" listUrl="${ct.listUrl }" detailUrl="${ct.detailUrl }">${ct.name }</option>
					</c:forEach>
					<option value="zdy">自定义</option>
				</select>
			</td>
		</tr>
		<tr>
			<th>
				跳转类型
			</th>
			<td>
				<select name="jumpType">
					<option value="1" <c:if test='${element.jumpType=="1"}'>selected="selected"</c:if>>APP原生</option>
					<option value="2" <c:if test='${element.jumpType=="2"}'>selected="selected"</c:if>>H5</option>
					<option value="3" <c:if test='${element.jumpType=="3"}'>selected="selected"</c:if>>视频</option>
				</select>
			</td>
		</tr>
		<tr>
			<th>内容链接:</th>
			<td>
				<a href="banner/lookUp.do" title="元素内容链接查询" width="1000" height="500" targetBox="td" warn="请先选择显示内容">
					<input type="text" name="contentName" toName="content_demo.name" class="easyui-validatebox" readonly="readonly"/>
					<input type="hidden" name="contentId" toName="content_demo.id"/>
				</a>
				<input type="text" name="detailUrl" class="easyui-validatebox" style="display: none;width: 280px;"/>
				<input type="hidden" name="elementId"/>
				<input type="hidden" name="columnId"/>
			</td>
		</tr>
	</table>
</div>
<script type="text/javascript">	
var uploader = null;
var view_height = 0;
$(function() {
	initWebUpload();
	initSelect();
    $("input[name='columnStatus'][value='${b.columnStatus}']").attr("checked",true);
});
function initSelect(){
	var showStyleOption = getOptionsByType(1);
	$("#showStyle").html(showStyleOption);
	var elementContentOption = getOptionsByType(2);
	$("#elementKey").html(elementContentOption);
	
	var columnType = "${b.columnType}";
	if(columnType == 1){
		$("select[name='showStyle']").val('${b.showStyle}').change();
		$("select[name='contentTypeId']").each(function(){
			change_url(this,1);
		});
	}else{
		$("#elementKey").val("${b.elementKey}");
	}
	$("#columnType").val(columnType).change();
}
function add(obj,type){
	$(obj).parent().parent().append($("#demo"+type).html());
}
var imgType = "";
var upload_img;
function uploadImg(type,obj){
	imgType = type;
	upload_img = obj;
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
			$(upload_img).before(str);
			$(upload_img).hide();
			$(upload_img).parent().find("img[id='removeButton']").show();
			$(upload_img).parent().find("input[name='imgUrl']").val(response.dataobj.picPath);
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
var flag=true;
function save(){
	flag=true;
	var columnType = $("#columnType").val();
	var element_str = "";
	if(columnType == 1){
		var showStyle = $("#showStyle").val();
		if(showStyle == 0){
			Msg.alert("提示","请选择展示模板","warning");
			return;
		}
		element_str="[";
		//组装元素
		$("#pageElement table").each(function(i){
			if(i > 0){
				element_str += ",";
			}
			var id=$(this).find("input[name='elementId']").val();
			var titleOne=$(this).find("input[name='titleOne']").val();
			var titleTwo=$(this).find("input[name='titleTwo']").val();
			var imgUrl=$(this).find("input[name='imgUrl']").val();
			var contentTypeId=$(this).find("select[name='contentTypeId']").val();
			var contentName=$(this).find("input[name='contentName']").val();
			var contentId=$(this).find("input[name='contentId']").val();
			var columnId=$(this).find("input[name='columnId']").val();
			var detailUrl=$(this).find("input[name='detailUrl']").val();
            var jumpType=$(this).find("select[name='jumpType']").val();
			var orderNum=i+1;
			element_str+="{";
			element_str+="\"id\":\""+id+"\",";
			element_str+="\"titleOne\":\""+titleOne+"\",";
			element_str+="\"titleTwo\":\""+titleTwo+"\",";
			if(isEmpty(titleOne)){
				Msg.alert("提示","请输入元素标题一","warning");
				flag = false;
				return false;
			}
			if(isEmpty(detailUrl)){
				Msg.alert("提示","请选择栏目内容","warning");
				flag = false;
				return false;
			}
			if(isEmpty(jumpType)){
				Msg.alert("提示","请选择栏目跳转类型","warning");
				flag = false;
				return false;
			}
			if(imgUrl && imgUrl.length > 0){
				element_str+="\"imgUrl\":\""+imgUrl+"\",";
			}else{
				Msg.alert("提示","请选择元素图片","warning");
				flag = false;
				return false;
			}
			element_str+="\"contentTypeId\":\""+contentTypeId+"\",";
			element_str+="\"contentName\":\""+contentName+"\",";
			element_str+="\"orderNum\":\""+orderNum+"\",";
			element_str+="\"contentId\":\""+contentId+"\",";
			element_str+="\"detailUrl\":\""+detailUrl+"\",";
			element_str+="\"jumpType\":\""+jumpType+"\",";
			element_str+="\"columnId\":\""+columnId+"\"";
			element_str+="}";
		});
		element_str+="]";
		if(!flag){
			return;
		}
	}else{
		var columnNum = $("#columnNum").val();
		if(isEmpty(columnNum)){
			Msg.alert("提示","请输入元素数量","warning");
			return;
		}
		var elementKey = $("#elementKey").val();
		if(elementKey == 0){
			Msg.alert("提示","请选择元素内容","warning");
			return;
		}
		var elementValue = $("#elementKey").find("option[value='"+elementKey+"']").text();
		$("#elementValue").val(elementValue);
	}
	$("#elementStr").val(element_str);
	if(!validateSubmitForm($('#pageColumn_form'))){
		return;
	}
}
function removeGuest(obj){
	$(obj).parent().find("img[id='upload_img']").remove();
	$(obj).parent().find("input[name='imgUrl']").val("");
	$(obj).hide();
	$(obj).parent().find("img[id='addGuest']").show();
}

function add_element(){
	var index=$("#pageElement").find("table").length+1;
	//$("#demo").find("#title_num").text("元素"+($("#pageElement").find("table").length+1));
	$("#demo").find("table a").attr("lookupGroup","content_"+index);
	$("#demo").find("table a input[name='contentId']").attr("toName","content_"+index+".id");
	$("#demo").find("table a input[name='contentName']").attr("toName","content_"+index+".name");
	var lm=$("#demo").find("table").clone(true);
	var $p = $(document);
	lm.find("a[lookupGroup]", $p).lookup();
	$("#pageElement").append(lm);
}
function remove_element(obj,elementId){
	var l = $("#pageElement").find("table").length;
	if( l == 1 ){
		Msg.alert("提示","最少必须保留一个元素","warning");
		return;
	}
	if(isEmpty(elementId)){
		$(obj).parent().parent().parent().parent().remove();
	}else{
		 $.ajax({
			url:"pageConfig/pageColumn/delElement.do",
			type: 'post',	
			data: {id:elementId},
			cache: false,
			dataType:"json",
			success:function(json){
				if(json&&json["code"]==="0"){
					Msg.alert("提示","删除成功","warning");
					$(obj).parent().parent().parent().parent().remove();
				}else{
					Msg.alert("提示","删除失败","warning");
				}
			}
		});
	}
}
function view_model(obj , v){
	var v = isEmpty(v) ? obj.value : v;
	//if(v == 2 || v == 3 || v == 4 || v == 6){
	//	$("input[name='titleTwo']").parent().parent().show();
	//}else{
	//	$("input[name='titleTwo']").parent().parent().hide();
	//}
	//if(v*1 % 2 == 0){
	var h=$("#yl_area").parent().height() - 10;
	$("#yl_area").html("<img src='${path}/resource/images/jhy/lm"+v+".jpg' style='height:"+h+"px;'/>");
	var jycc="";
	if(v == 1 || v == 2){
		jycc = "请上传jpg,jpeg,png格式图片,建议尺寸(690*414)";
	}else if(v == 3 || v == 4 || v == 7){
		jycc = "请上传jpg,jpeg,png格式图片,建议尺寸(250*150)";
	}else{
		jycc = "请上传jpg,jpeg,png格式图片,建议尺寸(333*200)";
	}
	$("span[name='jycc']").text(jycc);
	//}else{
	//	$("#yl_area").html("<img src='${path}/resource/images/more.png' style='height:100%;'/>");
	//}
}

function change_url(obj,t){
	var p_table=$(obj).parent().parent().parent();
	var v=obj.value;
	if(v == 0){
		p_table.find("a[targetBox='td']").attr("href","");
		p_table.find("input[name='contentId']").val("");
		p_table.find("input[name='contentName']").val("");
		p_table.find("input[name='detailUrl']").val("");
		p_table.find("input[name='detailUrl']").hide();
		p_table.find("input[name='contentId']").parent().show();
	}else{
		if(v == "zdy"){
			p_table.find("input[name='contentId']").parent().hide();
			if(!t){
				p_table.find("input[name='detailUrl']").val("");
			}
			p_table.find("input[name='detailUrl']").show();
			p_table.find("input[name='contentId']").val("zdy");
			p_table.find("input[name='contentName']").val("zdy");
		}else{
			var select_option=$(obj).find("option[value='"+v+"']");
			var listUrl=select_option.attr("listUrl");
			var detailUrl=select_option.attr("detailUrl");
			if(!t){
				p_table.find("input[name='contentId']").val("");
				p_table.find("input[name='contentName']").val("");
			}
			p_table.find("input[name='detailUrl']").val(detailUrl);
			p_table.find("a[targetBox='td']").attr("href",listUrl);
			p_table.find("input[name='detailUrl']").hide();
			p_table.find("input[name='contentId']").parent().show();
		}
	}
}

function change_type(obj){
	var v = $(obj).val();
	if(v == 1){
		$("tr[id^='mode_2_']").hide();
		$("tr[id^='mode_1_']").show();
		$("div[id^='mode_1_']").show();
		$("td[id^='mode_1_']").show();
		var l = $("#pageElement").find("table").length;
		if(l == 0){
			add_element();
		}
	}else{
		$("tr[id^='mode_2_']").show();
		$("tr[id^='mode_1_']").hide();
		$("div[id^='mode_1_']").hide();
		$("td[id^='mode_1_']").hide();
	}
}

</script>
