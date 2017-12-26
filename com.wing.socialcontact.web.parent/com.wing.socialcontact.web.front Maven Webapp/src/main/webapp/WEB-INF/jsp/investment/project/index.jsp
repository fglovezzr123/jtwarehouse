<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title">项目联营</title>
<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/dropload.min.css?v=${sversion}" />
<link rel="stylesheet" type="text/css" href="${path}/resource/css/run-project-together.css?v=${sversion}" /> 
<style>
      .projectDescription{
         box-sizing:border-box;
         width:100%;
      /*    padding-left:0.3rem;
         padding-right:0.3rem; */
         margin:0 auto;
         position:relative;
       /*   padding-top:0.2rem; */
         
      }
       .projectDescription img{
          width:100%;
          height:3.75rem;
       }
      /*  .item{
                 box-sizing: border-box;
				    background: white;
				    padding-top: 0.2rem;
				     padding-bottom: 0.1rem;
				    width: 50%;
				    padding-left: 2.5%;
				    padding-right: 2.5%;
				    border-bottom: 1px solid #e6e6e6;
				    float: left;
				    padding-bottom:0.2rem;
       } */
      
       
      /*  .item>div:nth-child(1){
           height: 2rem;
		    width: 100%;
		    background: url(../img/images/bosss2.png) no-repeat center; 
		    background-size: 100% 2rem;
		    float: left;
		    margin: 0 auto;
       } */
       .the-projectTip{
          position:absolute;
          height:1.18rem;
          width:1.34rem;
          left:4%;
          top:0.3rem;
       }
</style>
</head>
<body>
<div class="wrapper">
	<div class="search-box">
				      	<div id="search">搜索</div>
					</div>
	<%-- <jsp:include page="../../commons/include_banner.jsp" >
		<jsp:param name="bannerid" value="a535b07256c54ff9ba26431d9fa59a6c" />
	</jsp:include> --%>
	<div class="projectDescription">
	    <a href="${path}/wx/investment/dxzf.do"><img src="${path}/resource/img/images/bussinessTogether.jpg"/></a>
	</div>
	<div class="pro-item1 cur" id="prj1" pageIndex="1" pages="${pageSize1}">
		<c:forEach var="item" items="${list1}">
		<div class="item active_A" data-id="${item.id}" onclick="showDetail(this)">
			<div style="background-image: url('${item.coverImg}');"></div> 
			<div>
				<p>${item.titles}</p>
				<p>
					<span>${fns:fmt(item.startTime,'yyyy.MM.dd')}-${fns:fmt(item.endTime,'yyyy.MM.dd')}</span><br /> 
					<span><b>${item.extProps.willCount}人</b>已报名</span>
				</p>
			</div>
			<!-- 状态为  抢光时-outofdate.png    秒抢中-isRuning.png -->
			<%-- <img class="the-projectTip" src="${path}/resource/img/icons/outofdate.png"/> --%>
			<br class="clear" />
		</div>
		</c:forEach>
	</div>
	<br class="clear" />
</div>

<div class="btms active_A">
	<div id="recommend">项目自荐</div>
	<br class="clear" />
</div>
<script type="text/javascript">
$(function() {
	$("#recommend").click(function(){
		window.location.href = "${path}/m/project/recommend/index.do";
	});
	$('.wrapper').dropload({
	  	  distance: 50,
	  	  scrollArea : window,
	    	domDown : {
	           domClass   : 'dropload-down',
	           domRefresh : '<div class="dropload-refresh">↑上拉刷新</div>',
	           domUpdate  : '<div class="dropload-update">↑释放更新</div>',
	           domLoad    : '',
	           domNoData  : '<div class="dropload-noData">暂无数据</div>'
	       },
	       loadDownFn : function(me){
	  	  		var pageIndex = parseInt($("#prj1").attr("pageIndex")||0)+1;
	  	  		var pages = parseInt($("#prj1").attr("pages")||0);
	  	  		if(pageIndex>pages){
	  	  			layer.msg('无更多数据了'); 
	  	  		 	me.resetload();
	    		  	return;
	  	  		}
	  	  	    setTimeout(function(){
			  	  	zdy_ajax({
			  	  		url: "${path}/m/project/listpage.do",
			  	  		dataType:"html",
			  	  		showLoading:true,
			  	  		data:{pageIndex: pageIndex},
			  	  		success:function(data,textStatus,jqXHR){
			  	  			$(".pro-item1").append(data);
			  	  			$("#prj1").attr("pageIndex",pageIndex)
			  	  		},
			  	  		complete:function(){
			    		   me.resetload();
			  	  		}
			  	  	})
	  	  	   },500);
	      }
	});
	
	$("#search").bind('touchstart',function(){
		window.location.href = "${path}/m/project/search.do";
	});
});
function showDetail(obj){
	window.location.href =  "${path}/m/project/detail/index.do?id="+$(obj).attr("data-id");
}
</script>
</body>
</html>