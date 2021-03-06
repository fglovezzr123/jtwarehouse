<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title">合作洽谈搜索</title>
<link rel="stylesheet" type="text/css" href="${path}/resource/css/search-meeting.css?v=${sversion}" />
<link rel="stylesheet" type="text/css" href="${path}/resource/css/cooperateList.css?v=${sversion}" />
<style>
</style>
</head>
<body>
<form id="dataForm" action="${path}/m/business/searchList.do" method="post">
<input type="hidden" name="keywords" id="_keywords"/>
</form>
<div class="wrapper">
   <div class="meeting-search">
       <div>
            <input type="text" placeholder="输入合作关键词"  id="keywords" name="search" onblur="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" onpaste="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')"  oncontextmenu = "value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')"/>
            <img src="${path}/resource/img/icons/lens.png"/>
       </div>
      <div>
			<span id="search">搜索</span>&nbsp;&nbsp;
			<span id="cancel">取消</span>
	 </div>
       <br class="clear"/>
   </div>
	<div class="hot-search">历史搜索</div>
       <div class="search-history">
    </div>
     <div class="active_A modify-btn" onclick="clearHis()">清空历史搜索</div>
    <div class="wrapper" id="contentdiv">
   
   </div>
</div>
<script type="text/javascript">
$(document).ready(function() {
  $("#cancel").click(function(){
	  window.location.href = "${path}/m/business/indexPage.do";  
  });
  $("#search").click(function(){
	  var keywords = $("#keywords").val();
	  if(keywords.trim()==""){
		  alert("请输入搜索关键词！");
		  return;
	  }
	  $("#_keywords").val(keywords);
	  var ss = JSON.parse(localStorage.getItem("coopkeys"))||[];
      var arr = [];
      arr.push(keywords);
      for(var i=0;i<ss.length&&i<7;i++){
    	  if(keywords!=ss[i]){
	    	  arr.push(ss[i]);
    	  }
      }
      console.log(arr)
      localStorage.setItem("coopkeys", JSON.stringify(arr));
      doSearch(keywords);
  });
  if (window.localStorage) {
      var ss = JSON.parse(localStorage.getItem("coopkeys"));
      console.log(ss)
      if(!ss) return;
      for(var i=0;i<ss.length;i++){
	      $(".search-history").append("<div class=\"active_A\" onclick=\"setKeywords(this)\">"+ss[i]+"</div>");
      }
   }
})
function clearHis(){
	 $(".search-history").empty();
	 localStorage.removeItem("coopkeys");
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

function openurl(url){
	document.location.href=url;
}
</script>
</body>
</html>