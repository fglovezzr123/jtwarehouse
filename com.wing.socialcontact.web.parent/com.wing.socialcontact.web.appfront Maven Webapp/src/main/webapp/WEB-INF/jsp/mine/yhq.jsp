<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="quheye">
		<title>优惠券</title>
		<style type="text/css">
		  /*公共样式部分*/
		   body{
       		width: 100%;
      		background: #F2F3F4;
           }
           .y-tit{
           	width: 100%;
           	height: .80rem;
           	padding: 0 .30rem;
           	box-sizing: border-box;
           	background: #fff;
           	display: flex;
           	justify-content: space-around;
           	align-items: center;
           	font-size: .28rem;
           }
           .y-tit span{
           width: 2.0rem;
           	height: .8rem;
           	line-height: .8rem;
           	text-align: center;
           	box-sizing: border-box;
           }
           .y-tit span.bott{
           	border-bottom: 0.04rem solid #0F88EB;
           	color: #0F88EB;
           }
           /*优惠券*/
           .y-item{
           	width: 6.9rem;
           	height: 1.86rem;
           	margin: 0 auto;
           	margin-top: .20rem;
           	display: flex;
           }
           .y-item  .y-imgBox{
           	width: 2.19rem;
           	height: 1.86rem;
           	position: relative;
           }
           .y-item  .y-imgBox img{
           	width: 100%;
           }
           .y-item  .y-imgBox .y-maney{
           	width: 2.19rem;
           	height: 1.86rem;
           	position: absolute;
           	top: 0;
           	left: 0;
           	font-size: .30rem;
           	color: #fff;
           }
           .y-item  .y-imgBox .y-maney p:nth-of-type(1){
           	  margin-top: .40rem;
           	  margin-left: .35rem;
           }
           .y-item  .y-imgBox .y-maney p:nth-of-type(1) span{
           	 font-size: .64rem;
           }
           .y-item  .y-imgBox .y-maney p:nth-of-type(2){
           	font-size: .22rem;
           	margin-left: .60rem;
           }
           .y-itemRight{
           	width: 4.71rem;
           	height: 1.86rem;
           	background: #fff;
           	font-size: .20rem;
           	position: relative;
           }
           .y-itemRight .top{
           	padding-left: .20rem;
           	min-height: .70rem;
           	display: flex;
           }
            .y-itemRight .top span:nth-of-type(1){
            	width: .66rem;
            	height: .30rem;
            	background: #0F88EB;
            	text-align: center;
            	line-height: .30rem;
            	color: #fff;
            	border-radius: .05rem;
            	margin-top: .22rem;
            }
            .y-itemRight .top span:nth-of-type(2){
            	font-size: .26rem;
            	flex: 1;
			    padding: .18rem 0;
			    box-sizing: border-box;
			    margin-left: 0.06rem;
            }
            .y-itemRight .btm{
             	position: absolute;
             	left: 0;
             	bottom: 0;
             }
            .y-itemRight p:nth-of-type(1){
            	line-height: .50rem;
            	margin-top: .1rem;
            	border-bottom: 1px #f2f3f4 solid;
            	padding-left: .20rem;
            	box-sizing: border-box;
            	color: #808080;
            }
            .y-itemRight p:nth-of-type(2){
            	line-height: .56rem;
            	padding-left: .20rem;
            	box-sizing: border-box;
            	color: #808080;
            	white-space: nowrap;
            	overflow: hidden;
            	text-overflow: ellipsis;
            }
            .y-itemRight .sy{
            	position: absolute;
            	top: .75rem;
            	right: .14rem;
            	background: #fff;
            	font-size: .22rem;
            	width: 1.15rem;
            	height: .42rem;
            	border: 1px #0F88EB solid;
            	box-sizing: border-box;
            	color: #0F88EB;
            	border-radius: .2rem;
            	text-align: center;
            	line-height: .40rem;
            }
            .y-itemRight .sybox{
            	position: absolute;
            	top: .35rem;
            	right: .19rem;
            	width: 1.28rem;
            	height: 1.28rem;
            	z-index: 100;
            }
            .y-itemRight .sybox img{
            	width: 100%;
            }
            .y-itemRight .top span.ygq{
            	background: #c2c2c2;
            }
		</style>
	</head>
	<body>
		<div class="y-tit">
			<span class="bott" onclick="initloadpage('1')">未使用</span>
			<span onclick="initloadpage('2')">已使用</span>
			<span onclick="initloadpage('3')">已过期</span>
		</div>
		<div class="yhq-item" id="contentdiv">
			
		</div>
		<div class="loadingbox">
				<div class="page_loading" style="display:block;">加载中…</div>
				<div class="page_nodata" style="display:none;">暂无更多数据</div>
		</div>
	</body>
	
	
<script> 
var page = 1;
var pageSize = 10;
var end=false;
var tid = "1";
$(document).ready(function() {
	$('.y-tit span').on('click', function() {
		var index = $(this).index();
		$(this).addClass('bott').siblings().removeClass('bott')
	})
	//滚动加载
	 $(window).scroll(function(){
	     var scrollTop=$(window).scrollTop();
	     var scrollHeight = $(document).height();
         var windowHeight = $(window).height();
         if (scrollTop>=scrollHeight-windowHeight) {
        	 if(!end){
        		 content(tid); 
        	 }
         };
         if(scrollTop>=200){
				$(".title").css({marginTop:'0'})//置顶部分是否与上部分有间距
				$(".title").addClass("fixed");
			}else{
				$(".title").css({marginTop:'.08rem'})//置顶部分 间距复原
				$(".title").removeClass("fixed");
			}
	 }) ;
	initloadpage(tid);
});

function initloadpage(_tid){
	page = 1;
	end=false;
	tid = _tid;
	$("#contentdiv").empty();
	$(".page_nodata").hide();
	$(".page_loading").show();
	content(_tid);
}
var initflag = true;
function content(tid){
	if(!end&&initflag){
		initflag=false;
		zdy_ajax({
			url: "${path}/m/coupon/selMyCouponList.do",
			showLoading:false,
			data:{
				type:tid,
				page : page,
				size:pageSize
			},
			success: function(data,res){
				if(res.code == 0){
					if(page==1 && !res.dataobj.clist.length){
						   $(".page_loading").hide();
						   $(".page_nodata").show();
					   }else if(res.dataobj.clist.length==0 || res.dataobj.clist.length<pageSize){
						   $(".page_loading").hide();
						   $(".page_nodata").show();
						   end=true;
					   };
					var con = "";
					$.each(res.dataobj.clist, function(i, n){
						con += '<div class="y-item"><div class="y-imgBox">';
						if(tid=='1'){
							con += '<img src="${path}/resource/img/icons/yhqyes.png"/>';
						}else if(tid=='2'||tid=='3'){
							con += '<img src="${path}/resource/img/icons/yhqno.png"/>';
						}
					    console.log(String(n.couponAmount))
						console.log(String(n.couponAmount).length)
						var _s = String(n.couponAmount).length
						if(_s==4){
							con += '<div class="y-maney"><p><em>￥</em><span style="font-size:.56rem">'+n.couponAmount+'</span></p><p>';
						}else if(_s==5){
							con += '<div class="y-maney"><p><em>￥</em><span style="font-size:.48rem">'+n.couponAmount+'</span></p><p>';
						}else if(_s==6){
							con += '<div class="y-maney"><p><em>￥</em><span style="font-size:.40rem">'+n.couponAmount+'</span></p><p>';
						}else if(_s==7){
							con += '<div class="y-maney"><p><em>￥</em><span style="font-size:.32rem">'+n.couponAmount+'</span></p><p>';
						}else if(_s==8){
							con += '<div class="y-maney"><p><em>￥</em><span style="font-size:.32rem">'+n.couponAmount+'</span></p><p>';
						}else if(_s>8){
							con += '<div class="y-maney"><p><em>￥</em><span style="font-size:.20rem">'+n.couponAmount+'</span></p><p>';
						}else{
							con += '<div class="y-maney"><p><em>￥</em><span>'+n.couponAmount+'</span></p><p>';
						}	
						if(n.couponType=='1'){
							con += '代金券';
						}else if(n.couponType=='2'){
							con += '满'+n.orderMinBuy+'可用';
						}
						con +='</p></div></div><div class="y-itemRight"><div class="top">';
						if(tid=='1'){
							con += '<span>';
						}else if(tid=='2'||tid=='3'){
							con += '<span class="ygq">';
						}
						if(n.couponType=='1'){
							con += '代金';
						}else if(n.couponType=='2'){
							con += '满减';
						}
						con +='</span><span>'+n.couponName+'</span></div><div class="btm"><p>'+formatDate(new Date(n.startTime))+'-'+formatDate(new Date(n.endTime))+'</p><p>使用范围：';
						if(n.useRange=='1'){
							con += '全平台';
						}else if(n.useRange=='2'){
							con += '会议';
						}else if(n.useRange=='3'){
							con += '活动';
						}else if(n.useRange=='4'){
							con += '商城';
						}
						con += '</div>';
						if(tid=='1'){
						//	con += '<div class="sy">立即使用</div>';
						}else if(tid=='2'){
							con += '<div class="sybox"><img src="${path}/resource/img/icons/ysy.png"/></div>';
						}else if(tid=='3'){
							con += '<div class="sybox"><img src="${path}/resource/img/icons/gq.png"/></div>';
						}
						con += '</div></div>';
					});
					$("#contentdiv").append(con);
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
function formatDate(now) { 
	var year=now.getFullYear(); 
	var month=now.getMonth()+1;
	if(month<10){
		month="0"+month;
	}
	var day=now.getDate();
	if(day<10){
		day="0"+day;
	}
	return year+"-"+month+"-"+day; 
	} 
</script> 
</html>