<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String bannerid="";
String width = "7.5";
String height = "3.75";
if(request.getParameter("bannerid")!=null)bannerid=request.getParameter("bannerid");
if(request.getParameter("height")!=null)height=request.getParameter("height");
if(request.getParameter("width")!=null)width=request.getParameter("width");
%>
<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/swiper-3.3.1.min.css" />
<script src="${path }/resource/js/libs/swiper-3.3.1.min.js" type="text/javascript" charset="utf-8"></script>
<div class="swiper-container banner" id="banner">
   	<ul class="swiper-wrapper banner_ul" id="bannerul" >
   	</ul>
   	<div class="swiper-pagination"></div>
</div>
<script type="text/javascript">
$(document).ready(function() {
	zdy_ajax({
		url: "${path}/m/banner/selBannerList.do",
	    showLoading:false,
		data:{
			columnType:"<%=bannerid %>"
		},
		success: function(data,res){
			if(res.code == 0){
				var s='',t=true,k="";
				if(res.dataobj==null||res.dataobj.length==0){
					s += '<li class="swiper-slide" ><img  style="height:100%;" src="${path}/resource/img/images/banner/mr_bn.jpg"  width="50%"/></li>';
				}else{
					$.each(res.dataobj, function(i, n){
						if(i<5){
							s += '<li class="swiper-slide" onclick="openurlb(\''+n.jumpUrl+'\')"><img src="'+_oss_url+imgReplaceStyle(n.picPath,'YSMAX1024')+'"  width="50%"/></li>';
						}
					});
				}
				$("#bannerul").append(s);
				if(data!=null){
					if(data.length==1||data.length==0){
						var t=false;
						var k = '';
					}else{
						var t = true;
						var k = '.swiper-pagination';
					}
				}else{
					var t=false;
					var k = '';
				}
				 /* console.log(t) */
				var swiper = new Swiper('.swiper-container', {
					loop:t,
				    pagination: k,
				    paginationClickable: true,
				    spaceBetween: 10,
				    autoplayDisableOnInteraction : false,
				    autoplay: 4000
				});

			}
		},
	    error:function(e){
		   //alert(e);
	    }
	}); 
});
function openurlb(url){
	zdy_ajax({
		url : '${path}/m/my/addLntAndEmp.do',
		type : 'post',
		dataType : 'json',
		data : {
			taskId : "task_0002"
		},
		success : function(data, res) {
			if (res.dataobj) {
				if(url!=''&&url!=null){
					document.location.href=url;
				}
			} 
		}
	});
	
}
</script>
