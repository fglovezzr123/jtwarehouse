<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<%@ taglib prefix="tojo" uri="/WEB-INF/tlds/tojo.tld" %>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title">投融宝</title>
<link rel="stylesheet" href="${path}/resource/css/invest-merge-treasure.css?v=${sversion}" />
<style>
       .banner_ul img{
	     height:auto;
	 }
	 .t-cont{
	width: 100%;
	height: 1.70rem;
	display: flex;
	justify-content: space-around;
	align-items: center;
	background: #fff;
}
.t-cont a img{
	    width: .76rem;
    height: .76rem;
	margin-bottom: .1rem;
}
.t-cont a{
	display: flex;
	flex-direction: column;
	font-size: .24rem;
	color: #666;
	align-items:center ;
}
.fenghui{
 width:100%;
 padding:0 .30rem;
 box-sizing:border-box;
}	 
</style>
</head>
<body>
    <div class="wrapper" style="padding-top:0;">
		 <jsp:include page="../commons/include_banner.jsp" >
		 <jsp:param name="bannerid" value="a8b2d0ddfdbd4288817b9e26d21aec10" />
		 </jsp:include>
         <div class="t-cont menu">
						<a href="${path}/m/meeting/index.do">
							<img src="${path}/resource/img/icons/tr_03.jpg" />
							<span>投洽峰会</span>
						</a>
						<a href="${path}/m/project/index1.do">
							<img src="${path}/resource/img/icons/tr_05.jpg" />
							<span>项目联营</span>
						</a>
						<a href="${path}/m/project/collect/index.do">
							<img src="${path}/resource/img/icons/tr_07.png" />
							<span>项目征集</span>
						</a>
						<a href="${path}/m/investment/invention/introduce.do">
							<img src="${path}/resource/img/icons/tr_09.jpg" />
							<span>基金投资</span>
						</a>
						<a href="${path}/m/news/htmlDetailPage.do?id=ecbbb33f23a04cb79e161e2c0e9308b0">
							<img src="${path}/resource/img/icons/tr_11.png" />
							<span>金服联盟</span>
						</a>
					</div>
       	 <c:if test="${not empty list0 and list0.size() gt 0 }">
         <h3>峰会推荐</h3>
         <c:forEach items="${list0}" var="obj">
         <div class="fenghui">
         <div class="forecast" style="background: url('${obj.coverImg}') no-repeat;background-size:100% 100%;" data-id="${obj.id}"  onclick="detailMeeting(this)">
           <!-- <div class="activityCover"></div> -->
             <ul>
              <%--   <li>${obj.titles}</li>
                <li>${obj.place}</li>
                <li>${fns:fmt(obj.startTime,'yy/MM/dd HH:mm')}-${fns:fmt(obj.endTime,'yy/MM/dd HH:mm')}</li>
              	<br/> --%>
              	<tojo:mtg type="1" meeting="${obj}"/>
            </ul>
         </div>
         </div>
         
       	 </c:forEach>
       	 </c:if>
         <p class="option-ite" style="background-image: none;">精选项目</p>
         <div class="projects">
         	 <c:forEach var="item" items="${list}">
              <div onclick="gotodetail($(this),$(this).attr('hrefs'))" hrefs="${path}/m/project/detail/index.do?id=${item.id}" class="newProject ">
                   <c:choose>
              	   <c:when test="${8006001 eq item.prjType}">
                   <span>项目联营</span>
              	   </c:when>
              	   <c:when test="${8006002 eq item.prjType}">
                   <span>项目融资</span>
              	   </c:when>
              	   </c:choose>
                   <img src="${item.coverImg}"/>
                   <p>${item.titles }</p>
              </div>
         	  </c:forEach>
              <br class="clear"/>
         </div>
    </div>
<script type="text/javascript">
$(document).ready(function() {
  var deviceWidth = document.documentElement.clientWidth;
  document.documentElement.style.fontSize = deviceWidth / 7.5 + 'px';
})
function detailMeeting(obj){
	self.location.href = "${path}/m/meeting/detail/index.do?id="+$(obj).attr("data-id");
}

function gotodetail(t,url){
 
   window.location.href	=url;
}
</script>
</body>
</html>