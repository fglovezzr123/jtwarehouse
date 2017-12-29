<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="quheye">
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/swiper-3.3.1.min.css" />
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/jhy.css" />
		<script src="${path }/resource/js/libs/swiper-3.3.1.min.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			var page_ossurl="${ossurl}";
			var viewFlag="${viewFlag}";
		</script>
		<title>${page.pageName}</title>
	</head>
	<body>
		<!--公共区域banner和nav快速导航-->
		<div class="swiper-container banner" id="banner" style="display:none;">
			<ul class="swiper-wrapper banner_ul">
				<c:if test="${not empty page.bannerList }">
					<c:forEach items="${page.bannerList }" var="banner"  varStatus="status">
					<c:if test="${status.index lt 5}">
						<li class="swiper-slide"><img src="${ossurl}${banner.picPath}" title="${quickEntry.title}" onclick="bannerClick('${banner.jumpUrl}');"/></li>
					</c:if>
					</c:forEach>
				</c:if>
				<c:if test="${empty page.bannerList }">
					<li class="swiper-slide" ><img  style="height:100%;" src="${path}/resource/img/images/banner/mr_bn.jpg"  width="50%"/></li>
				</c:if>
			</ul>
			<div class="swiper-pagination"></div>
		</div>
		<c:if test="${not empty page.pageQuickEntryList }">
			<div class="nav">
				<c:forEach items="${page.pageQuickEntryList }" var="quickEntry">
					<a href="javascript:void(0);">
						<img src="${ossurl}${quickEntry.imgUrl}" onclick="quickEntryClick('${quickEntry.linkUrl}');"/>
						<span>${quickEntry.name}</span>
					</a>
				</c:forEach>
			</div>
		</c:if>
		<!--公共区域banner和nav快速导航                       end-->
		<div id="lm_div">
			
			<div class="lm6" style="visibility: hidden;">
				<div class="lm6-header">
					<span>栏目名称</span>
					<a href="#">更多</a>
				</div>
				<div class="lm6-cont">
					<div class="item">
						<div class="lm6-img">
							<img src="${path}/resource/img/icons/lm4-1.png"/>
						</div>
					    <p>清晨阳关</p>
					    <p>清晨阳关加快对方尽快尽快</p>
						
					</div>
					<div class="item">
						<div class="lm6-img">
							<img src="${path}/resource/img/icons/lm4-1.png"/>
						</div>
					    <p>清晨阳关</p>
					    <p>清晨阳关加快对方尽快尽快</p>
						
					</div><div class="item">
						<div class="lm6-img">
							<img src="${path}/resource/img/icons/lm4-1.png"/>
						</div>
					    <p>清晨阳关</p>
					    <p>清晨阳关加快对方尽快尽快</p>
						
					</div><div class="item">
						<div class="lm6-img">
							<img src="${path}/resource/img/icons/lm4-1.png"/>
						</div>
					    <p>清晨阳关</p>
					    <p>清晨阳关加快对方尽快尽快</p>
						
					</div><div class="item">
						<div class="lm6-img">
							<img src="${path}/resource/img/icons/lm4-1.png"/>
						</div>
					    <p>清晨阳关</p>
					    <p>清晨阳关加快对方尽快尽快</p>
						
					</div><div class="item">
						<div class="lm6-img">
							<img src="${path}/resource/img/icons/lm4-1.png"/>
						</div>
					    <p>清晨阳关</p>
					    <p>清晨阳关加快对方尽快尽快</p>
						
					</div><div class="item">
						<div class="lm6-img">
							<img src="${path}/resource/img/icons/lm4-1.png"/>
						</div>
					    <p>清晨阳关</p>
					    <p>清晨阳关加快对方尽快尽快</p>
						
					</div>
				</div>
			</div>
		</div>
		<script src="${path }/resource/js/jhy.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			 $(function(){
				 var lbtFlag = ${page.lbtFlag};
				 if(lbtFlag==1){
					 $("#banner").show();
				 }else{
					 $("#banner").hide();
				 }
				 
				 var t=false;
				 var k = '';
				 var data = "${page.bannerList}";
				 var dataArr = data.split(',')
				 if(data!=null){
						if(dataArr.length==1){
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
					var swiper = new Swiper('.swiper-container', {
						loop:t,
					    pagination: k,
					    paginationClickable: true,
					    spaceBetween: 10,
					    autoplayDisableOnInteraction : false,
					    autoplay: 4000
					});

				 var lm_str='${page.pageColumnJsonStr}';
				 load_lm(lm_str);
				 //设置分享
				 jhyshare();
			 });
			 //banner跳转
			 function bannerClick(url){
				 if(!isEmpty(viewFlag)){
					 return;
				 }
				 if(isEmpty(url)){
					 return;
				 }
				 self.location.href=url;
			 }
			 function quickEntryClick(url){
				 if(!isEmpty(viewFlag)){
					 return;
				 }
				 if(isEmpty(url)){
					 return;
				 }
				 self.location.href=url;
			 }
		</script>
	</body>

</html>