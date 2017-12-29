<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title">联营项目搜索</title>
<link rel="stylesheet" type="text/css" href="${path}/resource/css/search-meeting.css?v=${sversion}" />
<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/dropload.min.css?v=${sversion}" />
<link rel="stylesheet" type="text/css" href="${path}/resource/css/run-project-together.css?v=${sversion}" /> 
<style>
</style>
</head>
<body>
<form id="dataForm" action="${path}/m/project/searchlist.do" method="post">
<input type="hidden" name="keywords" id="_keywords"  />
</form>
<div class="wrapper" style="padding-bottom: 0.2rem;">
   <div class="meeting-search">
       <div id="keywordsdiv">
            <input type="text" placeholder="输入项目关键词" id="keywords" maxlength="20"  onpaste="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')"  oncontextmenu = "value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')"/>
            <img src="${path}/resource/img/icons/lens.png"/>
       </div>
       <div>
			<span id="search" class="active_A">搜索</span>&nbsp;&nbsp;
			<span id="cancel">取消</span>
		</div> 
       <br class="clear"/>
   </div>
   <div id="selectkey">
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
   </div>
   <div  class="pro-item1 cur"  id="projectlist">
   </div>
   <%-- <c:choose>
		<c:when test="${empty list or list.size() eq 0}">
			<div class="dropload-noData" style="text-align:center;">暂无数据</div>
		</c:when>
		<c:otherwise>
			<div class="pro-item1 cur" id="prj1" pageIndex="1" pages="${pages}">
				<c:forEach var="item" items="${list}">
				<div class="item active_A" data-id="${item.id}" onclick="showDetail(this)">
					<div style="background-image: url('${item.coverImg}');"></div>
					<div>
						<p>${item.titles}</p>
						<p>
							<span>${fns:fmt(item.startTime,'yyyy.MM.dd')}-${fns:fmt(item.endTime,'yyyy.MM.dd')}</span><br /> 
							<span><b>${item.extProps.willCount}人</b>已报名</span>
						</p>
					</div>
					
					<br class="clear" />
				</div>
				</c:forEach>
			</div>
		</c:otherwise>
	</c:choose> --%>
</div>
<script type="text/javascript">
 var page = 1 ;
 var pageSize = 10;
 var end = false;
$(document).ready(function() {
  $("#cancel").click(function(){
	  window.location.href = "${path}/m/project/index1.do";  
  });
  $("#search").click(function(){
	  var keywords = $("#keywords").val();
	  if(keywords.trim()==""){
		   alert("请输入关键词");
		  return;
	  }
	  $("#_keywords").val(keywords);
      page = 1 ;
      $("#selectkey").hide();
      $("#projectlist").html('');
      end=false;
      doSearch();
  });
  $("#keywordsdiv").click(function(){
	  $("#selectkey").show();
      $("#projectlist").html('');
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
function doSearch(){
	  var keywords = $("#keywords").val();
	  if(keywords.trim()==""){
		  /* alert("请输入关键词"); */
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
   	 if(!end){
	    zdy_ajax({
			showLoading:false,
			url:'${path}/m/project/keywordlist.do',
			data:{
				  "pageIndex":page,
				  "keywords" : keywords
			   },
		   success: function(dataobj,data){
			   console.log(dataobj);
				var str='';
				if(data.code == 0){
				    if(page==1 && !data.dataobj.length){
				    	layer.msg("暂无更多数据");
				    	return;
				    }else if(data.dataobj.length<10){
				    	end=true;
				    }
					$.each(data.dataobj, function(i, n){
							str+='<div class="item active_A" data-id="'+n.id+'" onclick="showDetail(this)">'+
							'<div style="background-image: url(\''+n.coverImg+'\');"></div>'+
							'<div><p>'+n.titles+'</p><p><span>'+formatDate(new Date(n.startTime))+'-'+
							formatDate(new Date(n.endTime))+'</span><br /><span><b> '+
							n.extProps.willCount+'人</b>已报名</span></p></div><br class="clear" /></div>';	
					});
					
				$("#projectlist").append(str);
				page++;
				}
			},
			error:function(e){
			}
		});
   	 }else{
   		layer.msg("暂无更多数据");
   	 }
}
function setKeywords(obj){
	$("#keywords").val($(obj).text().trim());
	if($("#keywords").val()!=""){
		$("#selectkey").hide();
	    $("#projectlist").html('');
		end=false;
		page=1;
		doSearch();
	}
}

$(function() {
	  $(window).scroll(function(){
	       var scrollTop=$(this).scrollTop();
	        var scrollHeight = $(document).height();
	            var windowHeight = $(this).height();
	            if (scrollTop+windowHeight==scrollHeight) {
	            	doSearch(); 
	            };
	    })
	
});
function showDetail(obj){
	window.location.href =  "${path}/m/project/detail/index.do?id="+$(obj).attr("data-id");
}

function formatDate(now) { 
	var year=now.getFullYear(); 
	var month=now.getMonth()+1;
	if(month<10){
		month="0"+month;
	}
	var day=now.getDate();
	if(day<10){
		day="0"+day;
	}
	var hour=now.getHours();
	if(hour<10){
		hour="0"+hour;
	}
	var minute=now.getMinutes();
	if(minute<10){
		minute="0"+minute;
	}
	var second=now.getSeconds();
	if(second<10){
		second="0"+second;
	}
	return year+"/"+month+"/"+day; 
	} 
</script>
</body>
</html>