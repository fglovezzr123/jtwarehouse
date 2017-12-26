<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title">项目征集</title>
<link rel="stylesheet" href="${path}/resource/css/callon.css?v=${sversion}" />
<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/dropload.min.css?v=${sversion}" />
</head>
<body>
<div class="wrapper">
    <div claaa="imgBox" style="width:100%">
    	<img src="${path}/resource/img/images/xiangmu2.png" style="width:100%" />
    </div>
	<%-- <div class="search-box">
				      	<div id="search">搜索</div>
					</div>
	<jsp:include page="../../commons/include_banner.jsp" >
		<jsp:param name="bannerid" value="b89cd4fd8c0349018b71202d057e31b0" />
	</jsp:include>
	<div class="enterFor">
		<div class="active_A">
			 平台优质项目总数<br/>
			<span>${prjCount}</span>个
		</div>
		<div class="active_A">
		     参与项目会员总数<br/>
			<span>${userCount}</span>个
		</div>
		<br class="clear" />
	</div>
	<div class="tabBar">
	<c:choose>
	<c:when test="${type eq 2}">
		<a href="javascript:void(0)" type="1" id="prj1" pageIndex="1" pages="${pageSize1}">全部征集</a>
		<a href="javascript:void(0)" type="2" id="prj2" class="hasBorder" pageIndex="1" pages="${pageSize2}">我的自荐</a>
		<br class="clear" />
	</c:when>
	<c:otherwise>
		<a href="javascript:void(0)" type="1" id="prj1" class="hasBorder" pageIndex="1" pages="${pageSize1}">全部征集</a>
		<a href="javascript:void(0)" type="2" id="prj2" pageIndex="1" pages="${pageSize2}">我的自荐</a>
		<br class="clear" />
	</c:otherwise>
	</c:choose>	
	</div>
	<c:choose>
	<c:when test="${type eq 2}">
	<div class="pro-item1" style="display:none;">
		<c:forEach var="item" items="${prjList}">
		<div class="item active_A" data-id="${item.id}" onclick="showDetail(this)">
			<div>
				<p><strong>[${item.prjTypeName}]</strong>${item.prjName}</p>
				<p style="top:30px;text-overflow:ellipsis;white-space:wrap;overflow:hidden;">${item.prjDesc}</p>
			</div>
			<div style="background-image: url('${item.imgUrl}');"></div>
			<br class="clear" />
		</div>
		</c:forEach>
	</div>
	<div class="pro-item2 cur">
		<c:forEach var="item" items="${myPrjList}">
		<div class="item active_A" data-id="${item.id}" onclick="showDetail(this)">
			<div>
				<p><strong>[${item.prjTypeName}]</strong>${item.prjName }</p>
				<p style="top:30px;text-overflow:ellipsis;white-space:wrap;overflow:hidden;">${item.prjDesc}</p>
			</div>
			<div style="background-image: url('${item.imgUrl}');"></div>
			<br class="clear" />
		</div>
		</c:forEach>
	</div>
	</c:when>
	<c:otherwise>
	<div class="pro-item1 cur">
		<c:forEach var="item" items="${prjList}">
		<div class="item active_A" data-id="${item.id}" onclick="showDetail(this)">
			<div>
				<p><strong>[${item.prjTypeName}]</strong>${item.prjName}</p>
				<p style="top:30px;text-overflow:ellipsis;white-space:wrap;overflow:hidden;">${item.prjDesc}</p>
			</div>
			<div style="background-image: url('${item.imgUrl}');"></div>
			<br class="clear" />
		</div>
		</c:forEach>
	</div>
	<div class="pro-item2" style="display:none;">
		<c:forEach var="item" items="${myPrjList}">
		<div class="item active_A" data-id="${item.id}" onclick="showDetail(this)">
			<div>
				<p><strong>[${item.prjTypeName}]</strong>${item.prjName }</p>
				<p style="top:30px;text-overflow:ellipsis;white-space:wrap;overflow:hidden;">${item.prjDesc}</p>
			</div>
			<div style="background-image: url('${item.imgUrl}');"></div>
			<br class="clear" />
		</div>
		</c:forEach>
	</div>
	</c:otherwise>
	</c:choose> --%>
	<div class="btms active_A">
		<div id="recommend">我有自荐项目<br><span>好项目 值得分享</span></div>
        <br class="clear"/>
    </div>
</div>
<script type="text/javascript">
$(document).ready(function() {
	$("#recommend").click(function(){
		window.location.href = "${path}/m/project/recommend/index.do";
	});
	/* $(".tabBar a").click(function(){
		$(".tabBar a").removeClass("hasBorder");
		$(this).addClass("hasBorder");
		var type = $(this).attr("type");
		if(type!=1&&type!=2){
			return;
		}
		$(".cur").removeClass("cur").hide();
		$(".pro-item"+type).addClass("cur");
		$(".pro-item"+type).show();
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
	    	   if($(".tabBar a.hasBorder").length!=1){
			       me.resetload();
	    		   return;
	    	   }
	    	   var type = $(".tabBar a.hasBorder").attr("type");
	    	   if(type!=1&&type!=2){
	    		   layer.msg('无更多数据了'); 
	    		   me.resetload();
	    		   return;
	    	   }
	    	   
	  	  		var pageIndex = parseInt($("#prj"+type).attr("pageIndex")||0)+1;
	  	  		var pages = parseInt($("#prj"+type).attr("pages")||0);
	  	  		if(pageIndex>pages){
	  	  			layer.msg('无更多数据了'); 
	  	  		 	me.resetload();
	    		  	return;
	  	  		}
	  	  		pageIndex=1;
	  	  	    setTimeout(function(){
			  	  	zdy_ajax({
			  	  		url: "${path}/m/project/collect/listpage.do",
			  	  		dataType:"html",
			  	  		showLoading:true,
			  	  		data:{type: type, pageIndex: pageIndex},
			  	  		success:function(data){
			  	  			$(".pro-item"+type).append(data);
			  	  			$("#prj"+type).attr("pageIndex",pageIndex)
			  	  		},
			  	  		complete:function(){
			    		   me.resetload();
			  	  		}
			  	  	})
	  	  	   },500);
	      }
	});
	$("#search").bind('touchstart',function(){
		window.location.href = "${path}/m/project/collect/search.do";
	}); */
	/* function showDetail(obj){
		window.location.href =  "${path}/m/project/recommend/detail/index.do?id="+$(obj).attr("data-id");
	} */
})

</script>
</body>
</html>