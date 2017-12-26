<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>

<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="我的合作">
		<title>我的合作</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/myhz.css?v=${sversion}" />
		<script type="text/javascript" src="${path}/resource/js/jsObject.Expand.js?v=${sversion}"></script>
	</head>
	
	<body style="background: #f2f3f4;">
			<div class="M-tit">
				<h3 class="h-active" type="1">我发布的合作</h3>
				<h3 type="3">我的商洽</h3>
				<h3 type="2">我收藏的合作</h3>
			</div>
			<div class="cont" id="contdiv">
				
			</div>
		<div class="loadingbox">
				<div class="page_loading" style="display:block;">加载中…</div>
				<div class="page_nodata" style="display:none;">暂无更多数据</div>
	    </div>
	</body>
<script type="text/javascript">
var page = 1;
var pageSize = 5;
var end=false;
var type = "1";
$(document).ready(function() {
	content(type);
	$('.M-tit h3').click(function(e){
		var index = $(this).index();
		$(this).addClass('h-active').siblings().removeClass('h-active');
		initloadpage($(this).attr("type"));
    })
  //滚动加载
	 $(window).scroll(function(){
	       var scrollTop=$(this).scrollTop();
	       var scrollHeight = $(document).height();
	       var windowHeight = $(window).height();
	       if (scrollTop>=scrollHeight-windowHeight) {
      	     content(type); 
        };
        if(scrollTop>=200){
				$(".M-tit").css({marginTop:'0'})//置顶部分是否与上部分有间距
				$(".M-tit").addClass("fixed");
			}else{
				$(".M-tit").css({marginTop:'.08rem'})//置顶部分 间距复原
				$(".M-tit").removeClass("fixed");
			}
	    }) ;
});
function initloadpage(_tid){
	page = 1;
	end=false;
	type = _tid;
	$("#contdiv").empty();
	$(".page_nodata").hide();
	$(".page_loading").show();
	content(_tid);
}
function content(type){
	if(type==""||type==null){
		type = "1";
	}
	if(!end){
		zdy_ajax({
	//		showLoading:false,
			url: "${path}/m/business/selMyBusinessList.do",
			data:{
				type:type,
				page : page,
				size:pageSize
			},
			success: function(data,res){
				if(res.code == 0){
					console.log(data)
					if(page==1 && !res.dataobj.length){
						   $(".page_loading").hide();
						   $(".page_nodata").show();
					   }else if(res.dataobj.length==0 || res.dataobj.length<pageSize){
						   $(".page_loading").hide();
						   $(".page_nodata").show();
						   end=true;
					   };
					var s='';
					$.each(res.dataobj, function(i, n){
						s += '<div>';
						if(type=='1'){
							s += '<h3 onclick="openurl(\'${path}/m/business/myDetailPage.do?id='+n.id+'\')">'
						}else if(type=='2'){
							s += '<h3 onclick="openurl(\'${path}/m/business/detailPage.do?id='+n.id+'\')">'
						}
						if(type=='3'){
							s += '<h3 onclick="openurl(\'${path}/m/business/detailPage.do?id='+n.bid+'\')">'+n.titles+'</h3><div class="items"><span>商洽时间：</span><span>'+new Date(n.createTime).format("yyyy-MM-dd HH:mm")+
							'</span></div><div class="items"><span>商洽内容：</span><span>'+n.content+'</span></div>';
						}else{
							s += n.titles+'</h3><div class="items"><span>分类：</span><span>'+n.className+
							'</span></div><div class="items"><span>有效期：</span><span>'+new Date(n.startTime).format("yyyy-MM-dd")+
							'至'+new Date(n.endTime).format("yyyy-MM-dd")+
							'</span></div><div class="items"><span>合作诉求：</span><span>'+n.appealSummary+'</span></div>';
							if(type=='2'){
								s += '<div class="gz"><span data-id="'+n.id+'"  onclick="attentionDel(this)" class="active_A">取消收藏</span></div>';
							}
						}
						s += '</div>';
					});
					$("#contdiv").append(s);
					page++;
				}
			}
		}); 
	}
	
}
function openurl(url){
	document.location.href=url;
}
function attentionDel(obj){
	zdy_ajax({
  		url: "${path}/m/business/addAttention.do",
  		data:{id: $(obj).attr("data-id")},
  		success: function(data,output){
			alert_back(output.msg,function(){
				if(output.msg=="取消收藏"){
					$(obj).parent().parent().remove();
				}
			});
		},
  		complete:function(){
  		}
  	})
}
</script>

</html>