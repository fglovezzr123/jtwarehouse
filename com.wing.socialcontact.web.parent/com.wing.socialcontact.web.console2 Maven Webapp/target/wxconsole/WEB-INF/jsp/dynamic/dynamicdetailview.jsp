<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ taglib uri="/WEB-INF/tag/myTag.tld" prefix="my" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理
--%>



<div style="width: 100%;margin: 0 auto;" >
	<div style="width:100%;">						
		<table class="table table-bordered" style="margin-top:0px;">
			<tr>
				<th style="width:30%;">发布人：</th>
				<td >${dynamic.dynamicMap.nickname}</td>
			</tr>
			<tr>
				<th style="width:15%;">发布时间：</th>
				<td colspan=3><input type="text"value="<fmt:formatDate  value="${dynamic.dynamicMap.issued_date}" pattern="yyyy-MM-dd HH:mm:ss" />" readonly="readonly"   /></td>
			</tr>
			<tr>
				<th style="width:30%;">公司名称：</th>
				<td>
					${dynamic.dynamicMap.com_name}
				</td>
			</tr>
			<tr>
				<th style="width:30%;">职位：</th>
				<td >
					${dynamic.dynamicMap.job_name}
				</td>
			</tr>
			<tr>
				<th style="width:30%;">动态内容：</th>
				<td >
					${dynamic.dynamicMap.dy_content}
				</td>
			</tr>
			<c:if test="${not empty dynamic.picList}">
				<tr>
					<th>动态图片：</th>
					<td>
						<div class="hyjb_box">
		    				<div class="hyjb_tj">
		   						<c:forEach items="${dynamic.picList}" var="item">
		   						 <c:if test="${not empty item.picUrl}">
		    					<ul class="hyjb_dl">
									<li>
										<dl>
											<dt><img src="${ossurl}${item.picUrl}"  style="width:100%;height:100%;"/></dt>
										</dl>
									</li>
								</ul>
								</c:if>
		   						</c:forEach>
		    				</div>
						</div>
					</td>
				</tr>
			</c:if>
			<c:if test="${not empty dynamic.dynamicMap.media_id}">
			<tr>
				<th>语音：</th>
				<td colspan=3>
					<div class="hyjb_box">
						<span id="media_text"  style="cursor:pointer" onclick="mediaPlay('${dynamic.dynamicMap.media_id}')">播放</span>
					</div>
				</td>
			</tr>
			</c:if>
			<tr id="zdyh_tr_2">
			    	<th>好友可见：</th>
			    	<td>
	               		<input type="radio" name="visitType" value="2" style="margin-top: -2px;" /><span style="margin-right:20px;margin-left: 2px;">对所有人可见</span>
	               		<input type="radio" name="visitType" value="1" style="margin-top: -2px;"/><span style="margin-left: 2px;">仅对好友可见</span>
	               	</td>
			</tr>
			<tr>
				<th>点赞数：</th>
				<td colspan=3>${dynamic.dynamicMap.lickCount}</td>
			</tr>
			<tr>
				<th>评论数：</th>
				<td colspan=3>${dynamic.dynamicMap.commentCount}</td>
			</tr>
			<tr>
				<th>查看数：</th>
				<td colspan=3>${dynamic.dynamicMap.visit_quantity}</td>
			</tr>
			<tr>
				<th>转发数：</th>
				<td colspan=3>${dynamic.dynamicMap.forwardCount}</td>
			</tr>
			<tr>
				<th>打赏数：</th>
				<td colspan=3>${dynamic.dynamicMap.rewardCount}</td>
			</tr>
		</table>
	</div>
	<audio id="audio_id" src="">
	</audio>
	<div class="divider"></div>
	<table class="table table-nobordered " style="margin-top: 25px;">
		<tr>
				<th width="">
					手机号：
				</th>
				<td colspan="3">
					<input type="text" width="100px" id="mobile" class="easyui-numberbox"  />
				</td>
		</tr>
		<tr>
				<th>
					评论内容：
				</th>
				<td colspan="3">
					<textarea  id="cmeDesc" rows="2" style="width:150px" cols="100" maxlength="500"></textarea>
					<br/>
					<button style="margin-top:16px;" class="btn btn-primary" onclick="addComments()">评论</button>
				</td>
		</tr>
	 </table>	
	 <div class="divider"></div>
	 <div id="comments">
	    <!--  <table class="table table-bordered "  style="margin-top: 25px;">
			<tr>
					<th style="text-align:left;" width="25%">刘子健</th>
					<th colspan="2"  width="50%">   08-23 2017-3-15</th>
					<th  width="25%">  <button  class="btn clear"  onclick="showHf(this)">回复</button></th>
			</tr>
			<tr>
					<td colspan="4">呼吁人类珍惜每一滴水，不要自掘坟墓也一样你的佛你抵抗力反馈ererrfooeroiio共人体哦哦高热高克劳福德坑了</td>
			</tr>
			<tr>
					<th>王石磊 回复 刘子健 </th>
					<th colspan="3">08-23 2017-3-15</th>
			</tr>
			<tr>
					<td colspan="4">呼吁人类珍惜每一滴水，不要自掘坟墓也一样你的佛你抵抗力反馈ererrfooeroiio共人体哦哦高热高克劳福德坑了</td>
			</tr>
			
			<tr style="display:none;">
					<td colspan="4">
						<textarea   rows="2" cols="100" style="width: 560px" maxlength="500">safasdfa</textarea>
						<button  class="btn btn-primary"  onclick="saveHf(this)">
							回复评论
						</button>
					</td>
			</tr>
			
		 </table> -->
	 </div>
	 
	  
	
	 
	
</div>

<script type="text/javascript">	
<%--cmeType是评论类别 1:资讯   2：合作 3：话题  4：活动 5:动态--%>
var cmeType =5;
var fkId="${dynamic.dynamicMap.id}";
$(function(){
	$("input[name='visitType']").each(function(i){
		if($(this).val() == ${dynamic.dynamicMap.visit_type}){
			$(this).attr("checked","checked");
			return;
		}
	});
	initform();
});

function mediaPlay(meida_id){
	var audio=$("#audio_id")[0];
	audio.src=_oss_url+meida_id;
	if($("#media_text").text() == "播放"){
		audio.play();
	}else{
		audio.pause();
		$("#media_text").text("播放");
	}
	audio.onplay=function(){
		$("#media_text").text("停止");
	}
	audio.onended=function(){
		$("#media_text").text("播放");
	}
	//暂停时使用
	audio.onpause=function(){
		$("#media_text").text("播放");
	}
}

//保存属性
function initform() {
	$.ajax({
		url:"dynamic/selComments.do?fkId="+fkId+"&cmeType="+cmeType,
		type:"get",
		cache:false,
		dataType:"json",
		success:function(data){
			 
			$.each(data, function(i, n){
				///jQuery.messager.alert('提示:',n.id); 
				var s='';
				$.each(data, function(i, n){
					s += '<table class="table table-bordered "  style="margin-top: 25px;">';
					s += '<tr>';
					s += '			<th style="text-align:left;" width="25%">'+n.nickname+'('+n.job+'/'+n.industry+')'+'</th>';
					s += '			<th colspan="2"  width="50%"> '+'回复数：'+n.subcount+'</th>';
					s += '			<th  width="25%">  <button  class="btn clear"  onclick="showHf(this)">回复</button></th>';
					s += '</tr>';
					s += '<tr>';
					s += '		<td colspan="4">'+n.cmeDesc+'</td>';
					s += '</tr>';
					$.each(n.subCommentList, function(i, m){	
						s += '<tr>';
						s += '<th>'+m.trueName+' 回复'+n.nickname+' </th>';
						s += '<th colspan="3"></th>';
						s += '</tr>';
						s += '<tr>';
						s += '		<td colspan="4">'+m.cmeDesc+'</td>';
						s += '</tr>';
					});
					s += '<tr style="display:none;">';
					s += '			<td >手机号:';
					s += '			<input type="text" style="width:100px"  class="easyui-numberbox" />';
					s += '		    </td>';
					s += '			<td colspan="3">';
					s += '			<textarea   rows="3" cols="20" style="width:150px"></textarea>';
					s += '			<button  class="btn btn-primary" style="width:100px;margin-top:16px" parentId=\"'+n.id+'\" onclick="saveHf(this)">回复评论</button>';
					s += '		    </td>';
					s += '	</tr>';
					s += '	</table>';
				});
				
				
				$("#comments").html(s);
			});
			
			
		}
	});
}

function imgReplaceStyle(imgpath,style){
	if(imgpath.indexOf('?')>-1){
		imgpath=imgpath.substring(0,imgpath.indexOf('?'));
	}
	return imgpath+'?x-oss-process=style/'+style;
}

//保存属性
function addComments() {
	var mobile = $("#mobile").val();
	if(mobile==""){
		alert_back("手机号不能为空",function(){
			$("#mobile").focus();
		});
		return false;
	}
	var cmeDesc = $("#cmeDesc").val();
	if(cmeDesc==""){
		alert_back("评论不能为空",function(){
			$("#cmeDesc").focus();
		});
		return false;
	}
	$.ajax({
		url:"dynamic/addComment.do?fkId="+fkId+"&cmeType="+cmeType+"&mobile="+mobile+"&cmeDesc="+cmeDesc,
		type:"get",
		cache:false,
		dataType:"json",
		success:function(data){
			if(data){
				$("#mobile").val("");
				$("#cmeDesc").val("");
				initform();
			}else{
				jQuery.messager.alert('提示:',"不存在该手机用户");
			}
			///jQuery.messager.alert('提示:',eval(data));
			
		}
	});
}

function showHf(obj) {
	 $(obj).hide();
	 $(obj).parent().parent().parent().find("tr:hidden").show();
};
function saveHf(obj) {
	var mobile= $(obj).parent().parent().find("input").val();
	if(mobile==""){
		alert_back("手机号不能为空",function(){
			$("#mobile").focus();
		});
		return false;
	}
	var cmeDesc= $(obj).parent().find("textarea").val();
	if(cmeDesc==""){
		alert_back("评论不能为空",function(){
			$("#cmeDesc").focus();
		});
		return false;
	}
	var parentId= $(obj).attr("parentId");
	$.ajax({
		url:"dynamic/addComment.do?fkId="+fkId+"&cmeType="+cmeType+"&cmeDesc="+cmeDesc+"&parentId="+parentId+"&mobile="+mobile,
		type:"get",
		cache:false,
		dataType:"json",
		success:function(data){
			if(data){
				initform();
			}else{
				jQuery.messager.alert('提示:',"不存在该手机用户");
			}
			///jQuery.messager.alert('提示:',eval(data));
			
		}
	});
};
</script>
