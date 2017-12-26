<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%
String bannerid="";
String width = "7.5";
String height = "15";
if(request.getParameter("bannerid")!=null)bannerid=request.getParameter("bannerid");
if(request.getParameter("height")!=null)height=request.getParameter("height");
if(request.getParameter("width")!=null)width=request.getParameter("width");
%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/swiper-3.3.1.min.css" />
<title>新手引导</title>
<script src="${path }/resource/js/libs/swiper-3.3.1.min.js" type="text/javascript" charset="utf-8"></script>
<style>

#nav{
    width: 2.4rem;
    height: .8rem;
    border: 0.04rem solid #fff;
    position: fixed;
    z-index: 1;
    font-size: .5rem;
    text-align: center;
    line-height: 0.8rem;
    bottom: 1.08rem;
    color: #fff;
    left: 50%;
    border-radius: .26rem;
    margin:0 0 0 -1.2rem
}
	
	.banner1{
		width:100%;
		
	}
	/* .swiper-pagination-bullet{
	width:.6rem;
	height:.6rem;
	
	} */
</style>
</head>
<body>
<div class="swiper-container banner1" id="banner" >
   	<ul class="swiper-wrapper banner_ul" id="bannerul" >
   	</ul>
   	<div class="swiper-pagination"></div>
</div>
<div id="nav"  onclick="openurlb()">立即注册</div>
<script type="text/javascript">
$(document).ready(function() {
	zdy_ajax({
		url: "${path}/m/banner/selguideBannerList.do",
	    showLoading:false,
		data:{
		},
		success: function(data,res){
			//console.log(res.code);
			if(res.code == 0){
				var s='';
				$.each(res.dataobj, function(i, n){
					s += '<li class="swiper-slide" ><img src="'+_oss_url+imgReplaceStyle(n.picPath,'YSMAX1024')+'"  width="50%"/></li>';
				});
				if(s==''){
					document.location.href="${path}/m/sys/regPage.do";
				}
				$("#bannerul").append(s);
				var swiper = new Swiper('.swiper-container', {
				    pagination: '.swiper-pagination',
				    paginationClickable: true,
				    spaceBetween: 30,
				    autoplayDisableOnInteraction : false
				});
			}
		},
	    error:function(e){
		   //alert(e);
	    }
	}); 
});
function openurlb(){
		document.location.href="${path}/m/sys/regPage.do";
}
</script>
</body>
</html>
