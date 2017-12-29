<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title>九天会所（新增）</title>
		
		<link rel="stylesheet" href="${path}/resource/css/jiutian.css?v=${sversion}" />
		 
		
		<style>
			.banner_ul img {
				width: 100%;
				height: 100%;
			}
			#banner1 .banner_ul img{
				  width:92%;
			}
		</style>
	</head>

	<body>
		
		<div class="wrapper">
            <div class="jiutianSearch">
            	 <input type="text" placeholder='搜索'/>
            	 <input type="button" class="active_A" value="+"/>
            	 <img src="${path}/resource/img/icons/lens.png"/>
            	 <br class="clear"/>
            </div>
			<div class="swiper-container banner" id="banner">
				<ul class="swiper-wrapper banner_ul">
					<li class="swiper-slide"><img src="${path}/resource/img/images/banner01_02.png" alt="" /></li>
					<li class="swiper-slide"><img src="${path}/resource/img/images/banner01_02.png" alt="" /></li>
					<li class="swiper-slide"><img src="${path}/resource/img/images/banner01_02.png" alt="" /></li>
					<li class="swiper-slide"><img src="${path}/resource/img/images/banner01_02.png" alt="" /></li>
				</ul>
				<div class="swiper-pagination"></div>
			</div>

			<div class="club-content">
				  <div class="club-bar">
				  	 <div class="current active_A">全部会所</div>
				  	 <div class="active_A">收藏会所</div>
				  	 <br class="clear"/>
				  </div>

				  <div class="club-tab-content">
				  	    <div class="club-head">华东</div>
				  	    <div class="club-position">
				  	    	<div class="active_A">上海</div>
				  	    	<div class="active_A">上海</div>
				  	    	<div class="active_A">上海</div>
				  	    	<div class="active_A">上海</div>
				  	    	<div class="active_A">上海</div>
				  	    	<br class="clear"/>
				  	    </div>

				  	    <div class="club-head">华东</div>
				  	    <div class="club-position">
				  	    	<div class="active_A">上海</div>
				  	    	<div class="active_A">上海</div>
				  	    	<div class="active_A">上海</div>
				  	    	<div class="active_A">上海</div>
				  	    	<div class="active_A">上海</div>
				  	    	<br class="clear"/>
				  	    </div>

				  	    <div class="club-head">华东</div>
				  	    <div class="club-position">
				  	    	<div class="active_A">上海</div>
				  	    	<div class="active_A">上海</div>
				  	    	<div class="active_A">上海</div>
				  	    	<div class="active_A">上海</div>
				  	    	<div class="active_A">上海</div>
				  	    	<br class="clear"/>
				  	    </div>

				  	    <div class="club-head">华东</div>
				  	    <div class="club-position">
				  	    	<div class="active_A">上海</div>
				  	    	<div class="active_A">上海</div>
				  	    	<div class="active_A">上海</div>
				  	    	<div class="active_A">上海</div>
				  	    	<div class="active_A">上海</div>
				  	    	<br class="clear"/>
				  	    </div>
				  	    <div class="swiper-container banner" id="banner1">
							<ul class="swiper-wrapper banner_ul">
								<li class="swiper-slide"><img src="${path}/resource/img/images/banner01_02.png" alt="" /></li>
								<li class="swiper-slide"><img src="${path}/resource/img/images/banner01_02.png" alt="" /></li>
								<li class="swiper-slide"><img src="${path}/resource/img/images/banner01_02.png" alt="" /></li>
								<li class="swiper-slide"><img src="${path}/resource/img/images/banner01_02.png" alt="" /></li>
							</ul>
				            <div class="swiper-pagination"></div>
			           </div>
			           <div class="contactMethod">联系方式:15063953852</div>
				  </div>


			</div>
			
		</div>
		
		<script src="${path}/resource/js/index.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
	</body>

</html>