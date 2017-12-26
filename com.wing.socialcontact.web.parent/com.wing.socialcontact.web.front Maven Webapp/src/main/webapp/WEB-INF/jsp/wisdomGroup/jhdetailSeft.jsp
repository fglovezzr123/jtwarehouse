<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<!DOCTYPE html>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">我的解惑详情</title>
        <link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css"/>
        <style type="text/css">
        	body{
        		background: #F3F4F5;
        	}
            .item{
            	width: 100%;
            	padding-top: .20rem;
            	background: #fff;
            	position: relative;
            }
            .item .i-top{
            	width: 100%;
            	display: flex;
            	box-sizing: border-box;
            	padding: 0 0.3rem;
            }
            .item .i-top img{
            	width: 0.73rem;
            	height: 0.73rem;
            	
            }
            .item .i-top .t-right{
            	font-size: .20rem;
            	color: #B8B8B8;
            	padding-left: 0.2rem;
            	
            }
            .item .i-top .t-right p:nth-of-type(1){
            	font-size: .30rem;
            	color: #3b3b3b;
            	line-height: .46rem;
            }
            .item .i-top .t-right p:nth-of-type(2){
            	line-height: .40rem;
            	width: 4.9rem;
            }
             .item h2{
             	width: 100%;
             	box-sizing: border-box;
             	padding: 0 .30rem;
             	font-size: .30rem;
             	line-height: .70rem;
             	border-bottom: #F3F4F5 solid 1px;
             }
             .item .icon{
             	width: 100%;
             	height:.70rem;
             	padding:0 0.3rem;
             	box-sizing: border-box;
             	display: flex;
             	justify-content: space-between;
             	align-items: center;
             	font-size: .24rem;
             }
             .item .icon span:nth-of-type(2){
             	background: url(${path}/resource/img/icons/zhuge-a8.png) no-repeat left center;
        		background-size: 0.33rem;
        		color: #f8ce61;
        		padding-left: .40rem;
             }
             .item .seft{
             	position: absolute;
             	width: .90rem;
             	height: .60rem;
             	font-size: .24rem;
             	color: #fff;
             	background: #0f88eb;
             	text-align: center;
             	line-height: .60rem;
             	top: .20rem;
             	right: .30rem;
             	border-radius: 0.1rem;
             }
              .item .noseft{
             	position: absolute;
             	width: .90rem;
             	height: .60rem;
             	font-size: .24rem;
             	background:#f0f0f0;
             	color: #fff;
             	text-align: center;
             	line-height: .60rem;
             	top: .20rem;
             	right: .30rem;
             	border-radius: 0.1rem;
             }
             .items{
             	margin-top: .20rem;
             }
             .items .list{
             	width: 100%;
             	padding-top: .20rem;
             		display: flex;
             	background: #fff;
             	position: relative;
             }
             .list img{
             	width: 0.73rem;
            	height: 0.73rem;
            	padding-left: .30rem;
             }
             .list .right{
             	width: calc(100% - 1.03rem);
             	padding-left: 0.2rem;
             }
             .list .right p:nth-of-type(1){
             	font-size: .30rem;
            	color: #3b3b3b;
            	line-height: .46rem;
            	box-sizing: border-box;
             	padding-right: .30rem;
             }
             .list .right p:nth-of-type(2){
             	font-size: .20rem;
            	line-height: .40rem;
            	box-sizing: border-box;
             	padding-right: .30rem;
             }
             .list .right h2{
             	box-sizing: border-box;
             	padding-right: .30rem;
             	font-size: .28rem;
             	line-height: .46rem;
             }
             .icons{
             	height:.46rem;
             	box-sizing: border-box;
             	padding-right: .30rem;
             	padding-bottom: .1rem;
             	display: flex;
             	justify-content: space-between;
             	align-items: center;
             	font-size: .24rem;
             	border-bottom: #F3F4F5 solid 1px;
             }
             .icons span:nth-of-type(2){
             	background: url(${path}/resource/img/icons/zhuge-a9.png) no-repeat left center;
        		background-size: 0.26rem;
        		padding-left: .40rem;
             }
             .list .cn{
             	width: .86rem;
             	height: .73rem;
             	position: absolute;
             	top:0;
             	right: 0;
             }
            

        </style>
    </head>
    <body>
    	<div class="item">
    		<div class="i-top">
    			<img src="${path}/resource/img/icons/weixinHeader.jpg" id="imgdiv"/>
    			<div class="t-right">
    				<p id="xm"></p>
    				<p id="gs"></p>
    			</div>
    		</div>
    		<h2 id="titlediv"></h2>
    		<div class="icon">
    			<span id="xssjdiv"></span>
    			<span id="jbdiv"></span>
    		</div>
    		<div  id="cainaidiv"></div>
    	</div>
    	<div class="items" id="content">
    		
    	</div>
    </body>
<script type="text/javascript">	
var userId = "";
var fbUserId = "";
var rewardFinish = 2;
$(document).ready(function() {
	zdy_ajax({
		url: "${path}/m/reward/detail.do",
	    showLoading:false,
		data:{
			id:'${id}'
		},
		success: function(data,res){
			console.log(data.isAttention);
			if(data.isAttention){
				$("#guanzhu").text("已收藏");
			}else{
				$("#guanzhu").text("收藏")
			}
			if(res.code == 0){
				var reward = res.dataobj.reward;
				$("#imgdiv").attr('src',reward.headPortrait);
				$('#imgdiv').click(function(){
					self.location.href="${path}/m/my/personal_home2.do?userId="+reward.createUserId;
				});
				userId = res.dataobj.userId;
				fbUserId = reward.createUserId;
				$("#xm").append(reward.nickname);
				$("#gs").append(reward.createTime+'&nbsp;&nbsp;'+reward.job+'/'+reward.comName);
				$("#titlediv").append(reward.question);
				$("#xssjdiv").append('悬赏时间：'+formatDate(new Date(reward.startTime))+"至"+formatDate(new Date(reward.endTime)));
				$("#jbdiv").append(reward.reward);
				rewardFinish = reward.status;
				$("#cainaidiv").append('<span class="seft active_A" id="wwcxs" onclick="caina()">采纳</span><span class="noseft active_A" id="wcxs" >已采纳</span>');
				if(rewardFinish=='4'){
					$("#wcxs").show();
					$("#wwcxs").hide();
				}else if(rewardFinish=='2'){
					$("#wwcxs").show();
					$("#wcxs").hide();
				}else if(rewardFinish=='5'){
					$("#wwcxs").hide();
					$("#wcxs").hide();
					$("#cainaidiv").append('<span class="noseft active_A"  >已过期</span>');
				}
				var con = "";
				if(!res.dataobj.ralist.length){
					   $('#content').html('<div  class="searchInfo">无更多内容</div>');
				}
				$.each(res.dataobj.ralist, function(i, n){
					var backgrounds='background:url(${path}/resource/img/icons/good.jpg) no-repeat left center;background-size:0.32rem 0.30rem';
					if(n.isThumbup){
						backgrounds='background:url(${path}/resource/img/icons/bad.jpg) no-repeat left center;background-size:0.32rem 0.30rem';
					}
					con += '<div class="list"><img src="'+n.headPortrait+'" onclick="open_user_center('+n.userId+')"/>'+
					'<div class="right"><p>'+n.nickname+'</p><p>'+n.job+'/'+n.comName+'</p><h2>'+n.content+'</h2><div class="icons">'+
					'<span>'+formatDate2(new Date(n.createTime))+'</span><span onclick="comment_sub(\''+n.id+'\');">'+n.subcount+'</span></div></div>';
					if(n.isAccept=='1'){
						con += '<img src="${path}/resource/img/icons/jhd.png"/ class="cn">';
					}
					con += '</div></div>';
				});
				$("#content").append(con);
			}else{
				alert(res.msg);
			}
		},
	    error:function(e){
		   //alert(e);
	    }
	}); 
});

//点击回复
var comment_sub=function(_id){
	self.location.href='${path}/m/comment/selCommentByPid.do?parentId='+_id+'&type=2';
	///<!-- cmeType 1:资讯   2：合作 3：话题  4：活动 5:动态-->
	///self.location.href='${path}/m/comment/comment_add.do?fkId='+_id+'&cmeType=2';
}

//查看他人主页
var open_user_center=function(userId){
	self.location.href="${path}/m/my/personal_home2.do?userId="+userId;
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
//点击采纳
function caina(){
	window.location.href = "${path}/m/reward/acceptPage.do?id=${id}";  
}
function formatDate2(now) { 
	var year=now.getFullYear(); 
	var month=now.getMonth()+1;
	if(month<10){
		month="0"+month;
	}
	var day=now.getDate();
	if(day<10){
		day="0"+day;
	}
	var hour=now.getHours();
	if(hour<10){
		hour="0"+hour;
	}
	var minute=now.getMinutes();
	if(minute<10){
		minute="0"+minute;
	}
	var second=now.getSeconds();
	if(second<10){
		second="0"+second;
	}
	return year+"/"+month+"/"+day+" "+hour+":"+minute; 
	} 
</script>    
</html>