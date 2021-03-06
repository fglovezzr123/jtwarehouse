<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title id="title">选择商洽</title>
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
   background:#1087eb;
   color:white;
}

.coop4-dates>div{
    width:50%;
    float:left;
    font-size:0.2rem;
    color:#808080;
}
.coop4-dates>div:nth-child(2){
	  text-align:right;
}
.coop4-dates>div>span:nth-child(1){
	 display:inline-block;
	 padding-left:0.37rem;
	 background:url(${path}/resource/img/icons/cccccccccc.jpg) no-repeat center left;
	 background-size:0.31rem 0.28rem;
	 margin-right:0.1rem;
	 height:0.28rem;
	 line-height:0.28rem
}
.coop4-dates>div>span:nth-child(2){
	 display:inline-block;
	 padding-left:0.37rem;
	 margin-left:0.1rem;
	 background:url({path}/resource/img/icons/good.jpg) no-repeat center left;
	 background-size:0.3rem 0.3rem;
	 height:0.32rem;
	 line-height:0.32rem;
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
var rewardFinish = 1;
$(document).ready(function() {
	zdy_ajax({
		url: "${path}/m/business/detail.do",
	    showLoading:false,
		data:{
			id:'${id}'
		},
		success: function(data,res){
			if(res.code == 0){
				var con = "";
				if(!res.dataobj.bdlist.length){
					   $('#content').html('<div  class="searchInfo">无更多内容</div>');
				}
				$.each(res.dataobj.bdlist, function(i, n){
				var backgrounds='background:url(${path}/resource/img/icons/good.jpg) no-repeat left center;background-size:0.32rem 0.30rem';
				if(n.isThumbup){
					backgrounds='background:url(${path}/resource/img/icons/bad.jpg) no-repeat left center;background-size:0.32rem 0.30rem';
				}
				con += '<div class="xz"><input type="checkbox" name="yang" id="'+n.id+'" value="'+n.id+'" /><div class="item"><div class="i-top"><img src="'+n.headPortrait+'" onclick="open_user_center('+n.userId+')"/>'+
				'<div class="t-right"><p>'+n.nickname+'</p><p>'+n.job+'/'+n.comName+'</p></div></div>'+
				'<p class="cont">'+n.content+'</p><div class="coop4-dates"><div>'+n.createTime+'</div>'+
				'<div><span class="active_A" onclick="comment_sub(\''+n.id+'\');"><b >'+n.subcount+'</b></span><span style="'+backgrounds+'" isThumbup='+n.isThumbup+' class="active_A givecomment" onclick="thumbup(this,\''+n.id+'\')"><b>'+
				n.count+'</b></span></div></div>'+
				'</div></div>';
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
	return year+"-"+month+"-"+day; 
	} 
//完成悬赏
function reward_finish(){
	var ids = [];
	$('input[name="yang"]:checked').each(function(){    
		   ids.push($(this).val());    
	});
	confirm("您已选择采纳"+ids.length+"个商洽，确定后悬赏J币将按照整数进行平均分配，若有剩余分给采纳人中第一个商洽的人！",function(t){
		if(t == 1){
			zdy_ajax({
				url: "${path}/m/business/rewardFinish.do",
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
		
	});
	  
}

//点击回复
var comment_sub=function(_id){
	self.location.href='${path}/m/comment/selCommentByPid.do?parentId='+_id+'&type=1';
	///<!-- cmeType 1:资讯   2：合作 3：话题  4：活动 5:动态-->
	///self.location.href='${path}/m/comment/comment_add.do?fkId='+_id+'&cmeType=2';
}
//点赞
var thumbup=function(obj,_id){
	var a=$(obj).find("b").html();
	zdy_ajax({
		url: "${path}/m/comment/Thumbup.do",
		data:{
			id:_id,
		},
		success: function(data,res){
			if(res.code == 0){
				if(res.msg==0){
					$(obj).css({"background":"url(${path}/resource/img/icons/bad.jpg) no-repeat left center","background-size":"0.32rem 0.30rem"}); 
					$(obj).find("b").html(a*1+1);
					$(obj).attr('isThumbup',true);
				}else{
					confirm("是否取消赞？",function(t){
						if(t == 1){
							zdy_ajax({
								url: "${path}/m/comment/cancelThumbup.do",
								data:{
									id:_id,
								},
								success: function(data,res){
									if(res.code == 0){
										if(res.msg==0){
											$(obj).css({"background":"url(${path}/resource/img/icons/good.jpg) no-repeat left center","background-size":"0.32rem 0.30rem"});
											$(obj).find("b").html(a*1-1);
										}else{
											
										}
									}
								},
							    error:function(e){
								   //alert(e);
							    }
							}); 	
						}
					});
					//alert("你已点赞");
				}
			}
		},
	    error:function(e){
		   //alert(e);
	    }
	}); 
}
</script>    
</html>
