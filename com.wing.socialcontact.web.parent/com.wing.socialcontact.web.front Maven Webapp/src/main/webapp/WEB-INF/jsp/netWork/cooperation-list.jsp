<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
	    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="合作列表">
		<title>合作列表</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/cooperateList.css?v=${sversion}" />
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/cooperate.css?v=${sversion}" />
	</head>
	<body>
		<div class="T-infor" style="background: #f2f3f4;width: 100%; height:100%;">
		<div class="wrapper" id="wrapper">
		<div class="tuhao" id="contentdiv">
		</div>
		<div class="loadingbox">
				<div class="page_loading" style="display:block;">加载中…</div>
				<div class="page_nodata" style="display:none;">暂无更多数据</div>
		</div>
		
		</div>
		<div class="c-footer">
				<span class="want active_A" onclick="openurl('${path}/m/business/selMyHzPage.do')">我的合作</span>
				<div class="active_A" onclick="business_add();">
					<i class="iconfont">&#xe637;</i>
					<span>发布合作</span>
				</div>
		</div>
  </div>
	</body>
<script type="text/javascript">
var page = 1;
var pageSize = 10;
var end=false;
var bizType = '${bizType}';
$(document).ready(function() {
	content();
	//滚动加载
	 $(window).scroll(function(){
	       var scrollTop=$(this).scrollTop();
	       var scrollHeight = $(document).height();
	       var windowHeight = $(window).height();
	       if (scrollTop>=scrollHeight-windowHeight) {
    	     content(); 
      };
	}) ;
});
function content(){
	if(!end){
		zdy_ajax({
			showLoading:false,
			url: "${path}/m/business/selListByType.do",
			data:{
				bizType:bizType,
				page : page,
				size:pageSize
			},
			success: function(data,res){
				//console.log(data);
				if(res.code == 0){
					if(page==1 && !res.dataobj.length){
						//   $('#contentdiv').html('<div  class="searchInfo">暂无更多数据</div>');
						   $(".page_loading").hide();
						   $(".page_nodata").show();
					   }else if(res.dataobj.length==0 || res.dataobj.length<pageSize){
						   $(".page_loading").hide();
						   $(".page_nodata").show();
						   end=true;
					   };
					   var con = "";
						$.each(res.dataobj, function(i, n){
							con += '<div class="cooperate-item active_A" onclick="openurl(\'${path}/m/business/detailPage.do?type=1&id='+n.id+'\')"><div class="cooperate-item-l">';
							if(n.appealType==1){
							con += '<div style="background:url(${path}/resource/img/icons/gong.png) no-repeat center;background-size:100% 100%"></div>';/* 供 */
							}else if(n.appealType==2){
								con += '<div class="asking" style="background:url(${path}/resource/img/icons/qiu.png) no-repeat center;background-size:100% 100%"></div>';/* 求 */
							}
							con += '</div><div class="cooperate-item-r"><h3>'+n.titles+'</h3><div>'+
							'<span>'+n.createTime+'</span><span>'+n.lookCount+'人浏览</span>';
							if(n.reward!=0){
								con += 	'<span>悬赏：'+n.reward+'J币</span>';
							}
							con += '<br class="clear"/></div></div><br class="clear"/></div>';
						});
						$("#contentdiv").append(con);
						page++;
				}
			}
		});
	}
}
function openurl(url){
	document.location.href=url;
}
var business_add=function(){
	var url = '${path}/m/business/addBusinessPage.do';
	window.location.href=url;
	if (window.localStorage) {
		  localStorage.setItem("history_url", "${path}/m/business/listPage.do?bizType=${bizType}");
	}
}
</script>
</html>

</html>