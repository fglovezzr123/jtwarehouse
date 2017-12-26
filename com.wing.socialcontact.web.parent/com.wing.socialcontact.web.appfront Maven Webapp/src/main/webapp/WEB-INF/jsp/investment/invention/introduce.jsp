<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">基金投资</title>
        
        <link rel="stylesheet" href="${path}/resource/css/fund-invest.css?v=${sversion}" />
        <script src="${path}/resource/js/libs/zepto.min.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
        <style>
             .banner_ul img{
                 width:100%;
                 height:100%;
             }
        </style>
    </head>
    
  
    <body>
    	
        <div class="wrapper">
            
             <div class="swiper-container banner" id="banner">
	             <jsp:include page="/WEB-INF/jsp/commons/include_banner.jsp" >
				    <jsp:param name="bannerid" value="e42a166136414028aac06c2ca6a2288b" />
				 </jsp:include>
                <%-- <ul class="swiper-wrapper banner_ul">
                  <li class="swiper-slide"><img src="${path}/resource/img/images/banner01_02.png" alt="" /></li>
                  <li class="swiper-slide"><img src="${path}/resource/img/images/banner01_02.png" alt="" /></li>
                  <li class="swiper-slide"><img src="${path}/resource/img/images/banner01_02.png" alt="" /></li>
                  <li class="swiper-slide"><img src="${path}/resource/img/images/banner01_02.png" alt="" /></li>
                </ul> --%>
             </div>
             
             <div class="tabBar">
                  <a href="javascript:void(0)" class="title " onclick="loadInfo(3)" id = "3">上市并购</a>
                  <a href="javascript:void(0)" class="title " onclick="loadInfo(1)" id = "1">定增投资</a>
                  <a href="javascript:void(0)" class="title " onclick="loadInfo(2)" id = "2">上市孵化</a>
                  <br class="clear"/>
             </div>
            <div class="alcontent" id="investview">
                        
            </div>

    </div>

    <div class="btms">
        <div class="active_A" onclick="openurl('${path}/m/investment/invention/list.do')"><span class="zxn-t" >我提交过的意向</span></div>
        <div class="active_A" onclick="openurl('${path}/m/investment/invention/add.do')"> <span class="zxn-t" >+我有投资意向</span></div>
        <br class="clear"/>
    </div>
        <script type="text/javascript">
      $(document).ready(function() {

        loadInfo(1);
      })
      
      //根据type查询数据
      function  loadInfo(type){
    	  
    	  $(".title").removeClass('hasBorder');
    	  $("#"+type).attr("class","title hasBorder");
    	  //$(".title").removeClass('title-t-active');
    	  // $("#"+type).attr("class","title title-t-active");
    	  
    	 zdy_ajax({
			 showLoading:false,
    		 url:'${path}/investment/m/investview.do',
			 data:{
				type:type
			},
			success: function(data1,data){
				if(data.code == 0){
					$("#investview").html("");
					var str = '';
					str+='<p><img   id = "imagePath" src="'+_oss_url+data.dataobj.imagePath+'"/>'
					+'<div class="head-img"></div><p>'+data.dataobj.introduce+'</p><div class="head-iii"></div><p>'+data.dataobj.feature+'</p></p>';
					$("#investview").append(str);
					
				}
			},
			error:function(e){
				//alert(e);
			}
    		 
    	  });
    	  
      }
      var height = $('.tabBar').offset().top
      $(window).scroll(function(){
  		var m = $(document).scrollTop();
  		/* console.log(m) */
  		if(m>=height){
  			$(".tabBar").addClass("fixed");
  		}else{
  			$(".tabBar").removeClass("fixed");
  		}
  	 })
      //调用跳转到页面方法
      function openurl(url){
			document.location.href=url;
		}
    </script>
        <%--   <script src="${path}/resource/js/index.js" type="text/javascript" charset="utf-8"></script> --%>
    </body>
</html>