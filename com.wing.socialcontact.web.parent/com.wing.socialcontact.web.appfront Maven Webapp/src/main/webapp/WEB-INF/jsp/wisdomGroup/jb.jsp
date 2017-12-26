<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title id="title">选择应答</title>
		<style type="text/css">
			body {
				background: #F3F4F5;
			}
			.xz{
				width: 100%;
				padding: 0 0.3rem;
				box-sizing: border-box;
				padding-top: .20rem;
				background: #fff;
				display: flex;
				justify-content: flex-start;
				align-items: center;
				font-size: .24rem;
				border-bottom: solid 1px #F3F4F5;
			}
			.item {
				padding-left:0.2rem;
			    display: flex;
                flex-direction: column;
                width: 100%;
			}
			.item .i-top {
				width: 100%;
				display: flex;
			}
			
			.item .i-top img {
				width: 0.73rem;
				height: 0.73rem;
			}
			
			.item .i-top .t-right {
				color: #B8B8B8;
				padding-left: 0.2rem;
			}
			
			.item .i-top .t-right p:nth-of-type(1) {
				color: #3b3b3b;
				line-height: .46rem;
			}
			.item .i-top .t-right p:nth-of-type(1) em{
				padding-left: .30rem;
			}
			
			.item .i-top .t-right p:nth-of-type(2) {
				line-height: .40rem;
			}
			.item .cont{
				padding-bottom: 0.2rem;
				line-height: .50rem;
			}
			input[type=checkbox] {
	           -webkit-appearance: none;
	           width:0.5rem;
	           height:0.5rem;
	           /*border-radius:100%;*/
	           background:url(${path}/resource/img/icons/check1.png) no-repeat;
	           background-size: 100%;
           }
           input[type=checkbox]:checked {
               background:url(${path}/resource/img/icons/check2.png) no-repeat;
               background-size: 100%
           }
           input[type="checkbox"][disabled]{
              background:url(${path}/resource/img/icons/check2.png) no-repeat;
              background-size: 100%;
              opacity:0.4;
           }
.mycooperateBtn{
	  height:1rem;
	  line-height:1rem;
	  text-align:center;
	  background:white;
     position:fixed;
     left:0;
     bottom:0;
     width:100%;
}
.mycooperateBtn>div{
	 float:left;
	 font-size:0.3rem;
	
}
.mycooperateBtn>div:nth-child(1){
   width:100%;
   background:#0f88eb;
   color:white;
   height:100%
}
#content{
 padding-bottom:1rem;
}
		</style>
	</head>
	<body>
	<div  id="content">
    		
    </div>
	<div class="mycooperateBtn">
	   	   <div class="active_A" onclick="reward_finish()" >完成</div>
	   	   <br class="clear"/>
	   </div>
	</body>
<script type="text/javascript">	
var userId = "";
var fbUserId = "";
$(document).ready(function() {
	zdy_ajax({
		url: "${path}/m/reward/detail.do",
	    showLoading:false,
		data:{
			id:'${id}'
		},
		success: function(data,res){
			if(res.code == 0){
				var con = "";
				if(!res.dataobj.ralist.length){
					   $('#content').html('<div  class="searchInfo">无更多内容</div>');
				}
				$.each(res.dataobj.ralist, function(i, n){
				con += '<div class="xz"><input type="checkbox" name="yang" id="'+n.id+'" value="'+n.id+'" /><div class="item"><div class="i-top"><img src="'+n.headPortrait+'" onclick="open_user_center('+n.userId+')"/>'+
				'<div class="t-right"><p>'+n.nickname+'<em>'+formatDate(new Date(n.createTime))+'</em></p><p>'+n.job+'/'+n.comName+'</p></div></div>'+
				'<p class="cont">'+n.content+'</p></div></div>';
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
//完成悬赏
function reward_finish(){
	var ids = [];
	$('input[name="yang"]:checked').each(function(){    
		   ids.push($(this).val());    
	});    
	 zdy_ajax({
		url: "${path}/m/reward/rewardFinish.do",
	    showLoading:false,
		data:{
			ids:ids,
			id:'${id}'
		},
		traditional: true,//这里设置为true
		success: function(data,res){
			if(res.code == 0){
				self.location=document.referrer;
			}else{
				alert(res.msg);
			}
		},
	    error:function(e){
		   //alert(e);
	    }
	});  
}
</script>    
</html>
