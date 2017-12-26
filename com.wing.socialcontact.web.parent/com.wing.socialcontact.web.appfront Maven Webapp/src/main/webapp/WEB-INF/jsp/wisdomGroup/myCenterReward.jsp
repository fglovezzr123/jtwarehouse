<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title>个人中心-悬赏</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/myfootPrint-topic.css?v=${sversion}" />
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/talkPk.css?v=${sversion}" />
		 <link rel="stylesheet" href="${path}/resource/css/zhuge-list.css" />
	</head>

	<body>
		<div class="T-infor" style="background: #f2f3f4;width: 100%; height:100%;">
			<div class="wrapper" id="wrapper">
				<div class="tlist">
					<span class="show-active" type="1" >我参加的悬赏</span>
					<span type="2" >我的悬赏</span>
					<span type="3" >我收藏的悬赏</span>
				</div>
				
				<div id="topiclist1" ></div>	
				<div class="loadingbox">
				<div class="page_loading" style="display:block;">加载中…</div>
				<div class="page_nodata" style="display:none;">暂无更多数据</div>
			   </div>
			</div>
		</div>
		<script src="${path}/resource/js/libs/public.js" type="text/javascript" charset="utf-8"></script>		
	</body>
<script type="text/javascript">
var page = 1;
var pageSize = 10;
var end=false;
var types = "1";
$(document).ready(function() {
	initloadpage(types);
	$('.tlist span').click(function(e){
		var index = $(this).index();
		$(this).addClass('show-active').siblings().removeClass('show-active');
		initloadpage($(this).attr("type"));
		types = $(this).attr("type");
    })
  //滚动加载
	 $(window).scroll(function(){
	    var scrollTop=$(window).scrollTop();
	    var scrollHeight = $(document).height();
        var windowHeight = $(window).height();
        if (scrollTop>=scrollHeight-windowHeight) {
       	 if(!end){
       		 content(types); 
       	 }
        };
        if(scrollTop>=200){
				$(".tlist").css({marginTop:'0'})//置顶部分是否与上部分有间距
				$(".tlist").addClass("fixed");
			}else{
				$(".tlist").css({marginTop:'.08rem'})//置顶部分 间距复原
				$(".tlist").removeClass("fixed");
			}
	 }) ;
});
function initloadpage(_type){
	page = 1;
	end=false;
	types = _type;
	$("#topiclist1").empty();
	$(".page_nodata").hide();
	$(".page_loading").show();
	content(_type);
}
var initflag = true;
function content(_type){
	if(!end&&initflag){
		initflag=false;
		zdy_ajax({
			url: "${path}/m/reward/selMyCenterRewardList.do",
			data:{
				types:_type
			},
			success: function(data,res){
				$("#topiclist1").empty();
				if(res.code == 0){
					var s='';
					if(page==1 && !res.dataobj.rList.length){
						   $(".page_loading").hide();
						   $(".page_nodata").show();
					   }else if(res.dataobj.rList.length==0 || res.dataobj.rList.length<pageSize){
						   $(".page_loading").hide();
						   $(".page_nodata").show();
						   end=true;
					   };
					$.each(res.dataobj.rList, function(i, n){
						s += '<div class="items"><div class="i-top"><img src="'+n.headPortrait+'" onclick="open_user_center('+n.createUserId+')"/>'+
						'<p onclick="open_user_center('+n.createUserId+')"><b>'+n.nickname+'</b>&nbsp;&nbsp;'+n.job+'/'+n.comName+'</p><p class="time">'+n.createTime+'</p></div>';
						if(_type=='1'||_type=='3'){
							s += '<h2 onclick="openurl(\'${path}/m/reward/detailPage.do?id='+n.id+'\')">';
						}else if(_type=='2'){
							s += '<h2 onclick="openurl(\'${path}/m/reward/myDetailPage.do?id='+n.id+'\')">';
						}
						s += n.question+'</h2>';
						if(_type=='3'){
							s += '<div class="icon"><div data-id="'+n.id+'" onclick="attentionDel(this)" class="active_A">取消收藏</div></div>';
						}
						/* else{
							s += '<div class="icon"><span>'+n.reward+'</span><span>'+n.countNum+'</span></div>';
						} */
						s += '</div>';
					});
					$("#topiclist1").append(s);
					page++;
					initflag=true;
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
  		url: "${path}/m/reward/addAttention.do",
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
//查看他人主页
var open_user_center=function(userId){
	self.location.href="${path}/m/my/personal_home2.do?userId="+userId;
}

</script>

</html>