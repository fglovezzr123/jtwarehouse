<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title">活动搜索</title>
<link rel="stylesheet" type="text/css" href="${path}/resource/css/search-meeting.css?v=${sversion}" />
</head>
<body>
<form id="dataForm" action="${path}/m/activity/activitysearchlist.do" method="post">
<input type="hidden" name="keywords" id="_keywords"/>
<input type="hidden" name="classid" id="classid" value="${classid}"/>
</form>
<div class="wrapper">
   <div class="meeting-search">
       <div>
            <input type="text" placeholder="输入活动关键词" id="keywords"  maxlength="20"/>
            <img src="${path}/resource/img/icons/lens.png"/>
       </div>
       <div>
			<span id="search">搜索</span>&nbsp;&nbsp;
			<span id="cancel">取消</span>
		</div> 
       <br class="clear"/>
   </div>
   <div class="hot-search">热门搜索</div>
   <div class="hot-search-content">
       <c:forEach items="${list}" var="item">
       <div class="active_A" style="margin-bottom:5px;" onclick="setKeywords(this)">${item.listValue}</div>
       </c:forEach>
       <br class="clear"/>
   </div>
   <div class="hot-search">历史搜索</div>
   <div class="search-history">
   </div>
<div class="active_A modify-btn" onclick="clearHis()">清空历史搜索</div>

<script type="text/javascript">
$(document).ready(function() {
  var deviceWidth = document.documentElement.clientWidth;
  document.documentElement.style.fontSize = deviceWidth / 7.5 + 'px';
  $("#cancel").click(function(){
	  var last_url=localStorage.activityurl;
	  window.location.href = last_url; 
  });
  
  $("#search").click(function(){
	  var keywords = $("#keywords").val();
	  if(keywords.trim()==""){
		  layer.msg('请输入搜索信息')
		  return;
	  }
	  $("#_keywords").val(keywords);
	  var ss = JSON.parse(localStorage.getItem("activitykeys"))||[];
      var arr = [];
      arr.push(keywords);
      for(var i=0;i<ss.length&&i<7;i++){
    	  if(keywords!=ss[i]){
	    	  arr.push(ss[i]);
    	  }
      }
      localStorage.setItem("activitykeys", JSON.stringify(arr));
      //$(".search-history").empty();
      for(var i=0;i<arr.length;i++){
	      //$(".search-history").append("<div class=\"active_A\">"+arr[i]+"</div>");
      }
      doSearch(keywords);
  });
  if (window.localStorage) {
      var ss = JSON.parse(localStorage.getItem("activitykeys"));
      if(!ss) return;
      for(var i=0;i<ss.length;i++){
	      $(".search-history").append("<div class=\"active_A\" onclick=\"setKeywords(this)\">"+ss[i]+"</div>");
      }
   }
})
function clearHis(){
	 $(".search-history").empty();
	 localStorage.removeItem("activitykeys");
}
function doSearch(keywords){
	
	$("#_keywords").val(keywords);
	$("#dataForm").submit();
}
function setKeywords(obj){
	$("#keywords").val($(obj).text().trim());
	if($("#keywords").val()!=""){
		doSearch($("#keywords").val());
	}
}
</script>
</body>
