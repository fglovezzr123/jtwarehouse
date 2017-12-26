<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.com.jsp" %>
<%
String quickDoorId="";
if(request.getParameter("quickDoorId")!=null)
    quickDoorId=request.getParameter("quickDoorId");
%>
<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/swiper-3.3.1.min.css" />
<script src="${path }/resource/js/libs/swiper-3.3.1.min.js" type="text/javascript" charset="utf-8"></script>
<div class="swiper-container banner" id="banner">
   	<ul class="swiper-wrapper banner_ul" id="bannerul" style="height:150px;">

   	</ul>
   	<div class="swiper-pagination"></div>
</div>
<script type="text/javascript">
$(document).ready(function() {
	zdy_ajax({
		url: "${path}/m/qfy/quickBanner.do",
	    showLoading:false,
		data:{
			quickDoorId:"<%=quickDoorId %>"
		},
		success: function(data,res){
			if(res.code == 0){
				var s='';
				$.each(res.dataobj, function(i, n){
					s += '<li class="swiper-slide" onclick="openurl(\''+n.jumpUrl+'\')"><img src="'+_oss_url+n.imgPath+'"  width="50%"/></li>';
				}); 
				if(s==''){
					s += '<li class="swiper-slide"><img src="${path}/resource/images/lsy_banner.jpg"  width="50%"/></li>';
				}
				$("#bannerul").append(s);
				if((res.dataobj && res.dataobj.length == 1) || s==''){
					var swiper = new Swiper('.swiper-container', {
					    pagination: 'null',
					    //pagination: '.swiper-pagination',
					    paginationClickable: true,
					    spaceBetween: 30,
					    autoplayDisableOnInteraction : false,
					    autoplay: 4000
					});
				}else{
					var swiper = new Swiper('.swiper-container', {
					    pagination: '.swiper-pagination',
					    paginationClickable: true,
					    spaceBetween: 30,
					    autoplayDisableOnInteraction : false,
					    autoplay: 4000
					});
				}
				//swiper初始化
				/* var mySwiper = new Swiper('.swiper-container', {
					autoplay: 5000,//可选选项，自动滑动
					pagination : '.swiper-pagination'
			    }) */
			}
		},
	    error:function(e){
		   //alert(e);
	    }
	}); 
});
function openurl(url){
	document.location.href=url;
}
</script>
