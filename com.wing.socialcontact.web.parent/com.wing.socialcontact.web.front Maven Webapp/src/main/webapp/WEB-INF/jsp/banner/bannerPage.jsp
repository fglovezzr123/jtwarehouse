<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.getSession().setAttribute("path", path);
%>
<%
String m="3d1ba858eaab4f93b6d4d5ead09014c8";
if(request.getParameter("m")!=null)m=request.getParameter("m");
%>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
        <title></title>
       
        <link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css" />
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/swiper-3.3.1.min.css" />
		<script src="${path }/resource/js/libs/jquery-2.2.3.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${path }/resource/js/libs/swiper-3.3.1.min.js" type="text/javascript" charset="utf-8"></script>
		
    </head>
    <body>
   
    	<div class="swiper-container banner" id="banner">
    	<ul class="swiper-wrapper banner_ul" id="bannerul">
    	<li class="swiper-slide">
    	</ul>
    	<div class="swiper-pagination"></div>
		</div>
	
	<script type="text/javascript">
	$(document).ready(function() {
		zdy_ajax({
			url: "${path}/m/banner/selBannerList.do",
			data:{
				columnType:"<%=m%>"
			},
			success: function(data,res){
				if(res.code == 0){
					var s='';
					$.each(res.dataobj, function(i, n){
						s += '<li class="swiper-slide"><img src="'+_oss_url+n.picPath+'"  width="50%"/></li>';
					});
					$("#bannerul").append(s); 
					var swiper = new Swiper('.swiper-container', {
						loop : true,
					    pagination: '.swiper-pagination',
					    paginationClickable: true,
					    spaceBetween: 30,
					    autoplayDisableOnInteraction : false,
					    autoplay: 3000
					});
				}
			},
		    error:function(e){
			   //alert(e);
		    }
		}); 
	});


</script>
    </body>

</html>