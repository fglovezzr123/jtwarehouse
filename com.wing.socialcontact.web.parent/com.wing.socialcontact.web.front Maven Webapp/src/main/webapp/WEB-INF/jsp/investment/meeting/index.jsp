<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<%@ taglib prefix="tojo" uri="/WEB-INF/tlds/tojo.tld" %>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title">投洽峰会</title>
<link rel="stylesheet" href="${path}/resource/css/instantlyEnterName.css?v=${sversion}" />
<link rel="stylesheet" href="${path}/resource/css/tqfh.css?v=${sversion}" />
<style>
.vedio a{
       display:block;
       height:0.7rem;
       width:2rem;
       text-align:center;
       line-height:0.7rem;
       color:white;
       background:#c4323f;
       position:absolute;
       bottom:0.2rem;
       margin-left:2.4rem;
}
</style>
</head>
<body>
<div class="wrapper">
	 <div class="search-box">
				      	<div id="search">搜索</div>
		</div>
	<c:if test="${not empty list1 and list1.size() gt 0}">
	<div class="wqhg">
		<a href="${path}/m/meeting/more/index.do?status=2">
			<span>火热报名中</span>
			<i class="iconfont" style="font-size: .26rem;">&#xe605;</i>
		</a>
		<c:forEach var="item" items="${list1}">
			<h3><b>${item.titles}</b></h3>
			<div class="sp" data-id="${item.id}" onclick="detailMeeting(this)">
				<span>${fns:fmt(item.startTime,'yy.MM.dd HH:mm')}&nbsp;</span>
				<span>${item.place}</span>
			</div>
			<div class="vedio" data-id="${item.id }" onclick="detailMeeting(this)">
				<img src="${item.coverImg}" />
				<tojo:mtg type="1" meeting="${item}"/>
			</div>
		</c:forEach>
	</div>
	</c:if>
	<c:if test="${not empty list2 and list2.size() gt 0}">
	<div class="wqhg">
		<a href="${path}/m/meeting/more/index.do?status=4">
			<span>进行中</span>
			<i class="iconfont" style="font-size: .26rem;">&#xe605;</i>
		</a>
		<c:forEach var="item" items="${list2}">
			<h3><b>${item.titles}</b></h3>
			<div class="sp" data-id="${item.id}" onclick="detailMeeting(this)">
				<span>${fns:fmt(item.startTime,'yy.MM.dd HH:mm')}&nbsp;</span>
				<span>${item.place}</span>
			</div>
			<div class="vedio" data-id="${item.id }" onclick="detailMeeting(this)">
				<img src="${item.coverImg}" />
				<tojo:mtg type="1" meeting="${item}"/>
			</div>
		</c:forEach>
	</div>
	</c:if>
	<c:if test="${not empty list3 and list3.size() gt 0}">
	<div class="fenghui">
		<a href="${path}/m/meeting/more/index.do?status=1">
			<span>峰会预热</span>
			<i class="iconfont" style="font-size: .26rem;">&#xe605;</i>
		</a>
		<div class="ctbox">
			<c:forEach var="item" items="${list3}">
			<div class="ct">
				<span class="f">${fns:fmt(item.startTime,'MM月')}</span>
				<div class="imgbox" data-id="${item.id}" onclick="detailMeeting(this)">
					<img src="${item.coverImg}" />
					<p>${item.titles}</p>
				</div>
				<div class="bt">
					<c:choose>
					<c:when test="${item.extProps.signupRemindStatus eq 1}">
					<span data-id="${item.id}">已设置提醒</span>
					</c:when>
					<c:otherwise>
					<span data-id="${item.id}" onclick="signupRemind(this)">预报名</span>
					</c:otherwise>
					</c:choose>
				</div>
			</div>
			</c:forEach>
		</div>
	</div>
	</c:if>
	<c:if test="${not empty list5 and list5.size() gt 0}">
	<div class="wqhg">
		<a href="${path}/m/meeting/more/index.do?status=3">
			<span>报名结束</span>
			<i class="iconfont" style="font-size: .26rem;">&#xe605;</i>
		</a>
		<c:forEach var="item" items="${list5}">
			<h3><b>${item.titles}</b></h3>
			<div class="sp" data-id="${item.id}" onclick="detailMeeting(this)">
				<span>${fns:fmt(item.startTime,'yy.MM.dd HH:mm')}&nbsp;</span>
				<span>${item.place}</span>
			</div>
			<div class="vedio" data-id="${item.id }" onclick="detailMeeting(this)">
				<img src="${item.coverImg}" />
				<tojo:mtg type="1" meeting="${item}"/>
			</div>
		</c:forEach>
	</div>
	</c:if>
	<c:if test="${not empty list4 and list4.size() gt 0}">
	<div class="wqhg">
		<a href="${path}/m/meeting/more/index.do?status=5">
			<span>往期回顾</span>
			<i class="iconfont" style="font-size: .26rem;">&#xe605;</i>
		</a>
		<c:forEach var="item" items="${list4}">
		<h3><b>${item.titles}</b></h3>
		<div class="sp" data-id="${item.id}" onclick="detailMeeting(this)">
			<span>${fns:fmt(item.startTime,'yy.MM.dd')}</span>
			<span>${item.place}</span>
			<c:if test="${not empty item.upperlimit and item.upperlimit gt 0}">
			<span>${item.getExtProp('signupCount', 0)}人参会</span>
			</c:if>
		</div>
		<div class="vedio" data-id="${item.id}" onclick="detailMeeting(this)">
			<img src="${item.coverImg}" />
			<tojo:mtg type="1" meeting="${item}"/>
			<div class="zbz-b">
				<span>${item.getExtProp('wacthCount', 0)}次播放</span>
				<span>评论：${item.getExtProp('chatCount', 0)}条</span>
			</div>
		</div>
		</c:forEach>
	</div>
	</c:if>
</div>
<script type="text/javascript">
$(document).ready(function() {
	
	$("#search").bind('touchstart',function(){
		window.location.href = "${path}/m/meeting/search.do";
	});
})
function detailMeeting(obj){
	self.location.href = "${path}/m/meeting/detail/index.do?id="+$(obj).attr("data-id");
}
function signupRemind(obj){
	zdy_ajax({
		url :"${path}/m/meeting/signupremind.do?id="+$(obj).attr("data-id"), 
	    type : 'post', 
	    dataType : 'json', 
	    success : function(dataobj){
	    	var isSuccess = "0"===dataobj["result_code"]?true:false;
	    	if(!isSuccess){
		    	alert(dataobj.result_msg)
	    		return;
	    	}else{
	    		$(obj).text("已设置提醒");
	    		$(obj).removeAttr("onclick");
	    	}
	    }
	});
}	
</script>
</body>
</html>