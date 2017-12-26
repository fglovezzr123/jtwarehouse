<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">诸葛榜</title>  
        <link rel="stylesheet" href="${path}/resource/css/zhuge-toplist.css" />
    </head>
    <body>
    	
        <div class="wrapper">
             <div class="tabBar">
                  <div class="hasborder active_A" onclick="content('1')">征集榜</div>
                  <div class="active_A" onclick="content('2')">悬赏榜</div>
                  <div class="active_A" onclick="content('3')">应答榜</div>
                  <br class="clear"/>
             </div>

             <div class="tab-content" id="contentdiv">
                
             </div>
          
        </div>
    </body>
    <script> 
var end=false;
var tid = "1";
$(document).ready(function() {
	$('.tabBar div').on('click', function() {
		var index = $(this).index();
		$(this).addClass('hasborder active_A').siblings().removeClass('hasborder active_A')
	})
	//滚动加载
	 $(window).scroll(function(){
         if(scrollTop>=200){
				$(".title").css({marginTop:'0'})//置顶部分是否与上部分有间距
				$(".title").addClass("fixed");
			}else{
				$(".title").css({marginTop:'.08rem'})//置顶部分 间距复原
				$(".title").removeClass("fixed");
			}
	 }) ;
	content(tid);
});
function content(tid){
	$("#contentdiv").empty();
		zdy_ajax({
			url: "${path}/m/reward/topList.do",
			showLoading:false,
			data:{
				type:tid
			},
			success: function(data,res){
				if(res.code == 0){
					var con = "";
					con += '<div class="toCall"><div class="active_A"><a href="javascript:void(0)">当前排名'+res.dataobj.pm+'，快去悬赏征集吧！</a></div></div>';
					$.each(res.dataobj.list, function(i, n){
						if(i==0){
							con += '<div class="tab-item first active_A">';
						}else if(i==1){
							con += '<div class="tab-item active_A second">';
						}else if(i==2){
							con += '<div class="tab-item active_A third">';
						}else{
							con += '<div class="tab-item active_A "> <div class="serial">'+(i+1)+'</div>';
						}
						con += '<div class="i-top"><img src="'+n.headPortrait+'" onclick="open_user_center('+n.createUserId+')"/></div><ul><li><span>'+n.nickname+'</span> / '+n.job+'</li><li>';
						if(tid=='1'){
							con += '征集数:'+n.zjcount;
						}else if(tid=='2'){
							con += '悬赏额:'+n.xscount;
						}else if(tid=='3'){
							con += '应答数:'+n.ydcount;
						}
						con += '</li><li>'+n.comName+'</li></ul></div>';
					});
					$("#contentdiv").append(con);
				}
			}
		});
}
function openurl(url){
	document.location.href=url;
}
//查看他人主页
var open_user_center=function(userId){
	self.location.href="${path}/m/my/personal_home2.do?userId="+userId;
}
</script> 
</html>