<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<style>
	* { margin:0; padding:0;}
	.hyjb_box {}
	.hyjb_dl {float:left; margin: 12px 12px; width:125px; list-style:none; }
	.hyjb_dl dl { position:relative; width:125px; height:105px; }
	.hyjb_dl dl dt { width:120px; height:100px; border:1px solid #dddddd;}
	.hyjb_dl dl dd { position:absolute; right:-10px; top:-10px; }
	.hyjb_dl .hyjb_shuru { width:125px; margin-top:0px;margin-left: 50px;}
	.hyjb_dl .hyjb_shuru input { border:none; width:110px; height:26px; background:#f7f7f7; text-align:center;}
	.hyjb_tj { float:left; margin:5px 0px 0 5px; }
	#dataForm img{width:100%;height:100%;}
</style>
<div style="width:100%;">						
	<table class="table table-bordered" style="margin-top:0px;">
		<tr>
			<th style="width:15%;">发布人：</th>
			<td colspan=3>${dynamic.dynamicMap.nickname}</td>
		</tr>
		<tr>
			<th style="width:15%;">发布时间：</th>
			<td colspan=3><input type="text"value="<fmt:formatDate  value="${dynamic.dynamicMap.issued_date}" pattern="yyyy-MM-dd HH:mm:ss" />" readonly="readonly"   /></td>
		</tr>
		<tr>
			<th style="width:15%;">公司名称：</th>
			<td colspan=3>
				${dynamic.dynamicMap.com_name}
			</td>
		</tr>
		<tr>
			<th style="width:15%;">职位：</th>
			<td colspan=3>
				${dynamic.dynamicMap.job_name}
			</td>
		</tr>
		<tr>
			<th style="width:15%;">动态内容：</th>
			<td colspan=3>
				${dynamic.dynamicMap.dy_content}
			</td>
		</tr>
		<tr>
			<th style="width:15%;">动态广告Url:</th>
			<td colspan=3>
				${dynamic.dynamicMap.ad_url}
			</td>
		</tr>
		<tr>
			<th>动态图片：</th>
			<td colspan=3>
				<div class="hyjb_box">
    				<div class="hyjb_tj">
   					<c:if test="${not empty dynamic}">
   						<c:forEach items="${dynamic.picList}" var="item">
    					<ul class="hyjb_dl">
							<li>
								<dl>
									<dt><img src="${ossurl}${item.picUrl}"  style="width:100%;height:100%;"/></dt>
								</dl>
							</li>
						</ul>
   						</c:forEach>
   					</c:if>
    				</div>
				</div>
			</td>
		</tr>
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
		    	<th>是否好友可见：</th>
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
	</table>
</div>
<audio id="audio_id" src="">
</audio>
<script type="text/javascript">
$(function() {
	$("input[name='visitType']").each(function(i){
		if($(this).val() == ${dynamic.dynamicMap.visit_type}){
			$(this).attr("checked","checked");
			return;
		}
	});
	//console.log(${dynamic.dynamicMap.visit_type});
});

function mediaPlay(meida_id){
	var audio=$("#audio_id")[0];
	audio.src=meida_id;
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
</script>
