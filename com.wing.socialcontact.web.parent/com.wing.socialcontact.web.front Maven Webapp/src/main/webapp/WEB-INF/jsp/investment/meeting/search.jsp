<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title">会议搜索</title>
<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css?v=${sversion}"/>
<link rel="stylesheet" type="text/css" href="${path}/resource/css/search-meeting.css?v=${sversion}" />
<script src="${path}/resource/js/libs/zepto.min.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
<style>
</style>
</head>
<body>
<form id="dataForm" action="${path}/m/meeting/searchlist.do" method="post">
<input type="hidden" name="keywords" id="_keywords"  />
</form>
<div class="wrapper">
   <div class="meeting-search">
       <div>
            <input type="text" placeholder="输入会议关键词" id="keywords" maxlength="20" onblur="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" onpaste="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')"  oncontextmenu = "value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')"/>
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
       <div class="active_A" style="margin-bottom:5px;" onclick="setKeywords(this)">${item.keywords}</div>
       </c:forEach>
       <br class="clear"/>
   </div>
   <div class="hot-search">历史搜索</div>
   <div class="search-history">
   </div>
<div class="active_A modify-btn" onclick="clearHis()">清空历史搜索</div>

<script type="text/javascript">
$(document).ready(function() {
  $("#cancel").click(function(){
	  window.location.href = "${path}/m/meeting/index.do";  
  });
  $('.meeting-search input').bind('keydown', function (e) {
      var key = e.which;
      if (key == 13) {
          e.preventDefault();
          $("#search").click();
          $("#keywords").blur();
      }
  });
  $("#search").click(function(){
	  var keywords = $("#keywords").val();
	  if(keywords.trim()==""){
		  alert("请输入关键词");
		  return;
	  }
	  $("#_keywords").val(keywords);
	  var ss = JSON.parse(localStorage.getItem("keys"))||[];
      var arr = [];
      arr.push(keywords);
      for(var i=0;i<ss.length&&i<7;i++){
    	  if(keywords!=ss[i]){
	    	  arr.push(ss[i]);
    	  }
      }
      localStorage.setItem("keys", JSON.stringify(arr));
      //$(".search-history").empty();
      for(var i=0;i<arr.length;i++){
	      //$(".search-history").append("<div class=\"active_A\">"+arr[i]+"</div>");
      }
      doSearch(keywords);
  });
  if (window.localStorage) {
      var ss = JSON.parse(localStorage.getItem("keys"));
      if(!ss) return;
      for(var i=0;i<ss.length;i++){
	      $(".search-history").append("<div class=\"active_A\" onclick=\"setKeywords(this)\">"+ss[i]+"</div>");
      }
   }
})
function clearHis(){
	 $(".search-history").empty();
	 localStorage.removeItem("keys");
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
</html>