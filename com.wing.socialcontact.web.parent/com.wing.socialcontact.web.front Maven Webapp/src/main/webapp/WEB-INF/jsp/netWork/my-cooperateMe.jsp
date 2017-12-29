<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="合作列表">
		<title>我的合作详情</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/mycooperate.css?v=${sversion}" />
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/coopDetail.css?v=${sversion}" />
	</head>

	<body>
		<div class="wrapper">
			 <div id="titles"></div>
			 <div id="wcxsdiv">
			 </div>
			 <div class="cooperate1">
			 	  <div>分类</div>
			 	 <div id="className"></div>
			 	  <br class="clear"/>
			 </div> 

			 <div class="cooperate1">
			 	  <div>有效期</div>
			 	   <div id="yxq"></div>
			 	  <br class="clear"/>
			 </div>

			 <div class="cooperate1">
			 	  <div>悬赏金额</div>
			 	 <div id="reward"></div>
			 	 <br class="clear"/>
			 </div>
          
			 <div class="cooperate1">
			 	  <div>合作诉求</div>
			 	  <div  id="appealSummary"></div>
			 	  <br class="clear"/>
			 </div>

			 <div class="cooperate2">
			 	  <div>合作说明</div>
			 	  <div id="appealDesc"></div>
			 	  <br class="clear"/>
			 	  <div class="cooperati5">
			 	  	 <span id="createTime"></span>
			 	  	 <span id="lookCount"></span>
			 	  	 <br class="clear"/>
			 	  </div>
			 </div>
			 <div class="btnW-span" id="cainaidiv">
			 	  
			 </div> 
 			
            <div class="cooperati4" id="content">
            	  
            </div>

   		</div>
    
	   <!-- <div class="mycooperateBtn">
	   	   <div class="active_A" onclick="att_add();" id="guanzhu"></div>
	   	   <div class="active_A" onclick="db_add();">发布商洽</div>
	   	   <div class="active_A">立刻沟通</div>
	   	   <br class="clear"/>
	   </div>	 --> 
	</body>
<script type="text/javascript">	
var rewardFinish = 1;
var userId = "";
var fbUserId = "";
var des= ''
$(document).ready(function() {
	zdy_ajax({
		url: "${path}/m/business/detail.do",
	    showLoading:false,
		data:{
			id:'${id}'
		},
		success: function(data,res){
			if(res.code == 0){
				var b = res.dataobj.business;
				userId = res.dataobj.userId;
				fbUserId = b.createUserId;
				$("#titles").append('<h1 class="fistH1" >'+b.titles+'</h1>');
				$("#className").append(b.className);
				$("#yxq").append(b.startTime+"至"+b.endTime);
				$("#reward").append(b.reward+" J币");
				$("#appealSummary").append(b.appealSummary);
				console.log(b.appealDesc)
				des = b.appealDesc
				if(b.appealDesc.length>100){
					$("#appealDesc").append(b.appealDesc.substring(0,100)+'...'+'<span class="more" onclick="stretch($(this).text())" style="margin-left:0.1rem;color:blue">更多</span>');
				}else{
					$("#appealDesc").append(b.appealDesc);
				}
				/* $("#appealDesc").append(b.appealDesc); */
				$("#createTime").append(b.createTime);
				$("#lookCount").append('<i>'+b.lookCount+'</i>人浏览');
				$("#guanzhu").append(b.countNum);
				$("#cainaidiv").append('<span class="bc active_A" id="wwcxs" onclick="caina()">采纳</span><span class="nobc active_A" id="wcxs" >已采纳</span>');
				if(b.rewardFinish=='1'){
					$("#wcxs").show();
					$("#wwcxs").hide();
				}else if(b.rewardFinish=='2'){
					$("#wwcxs").show();
					$("#wcxs").hide();
				}else if(b.rewardFinish=='3'){
					$("#wwcxs").hide();
					$("#wcxs").hide();
					$("#cainaidiv").append('<span class="nobc active_A"  >已过期</span>');
				}
				rewardFinish = b.rewardFinish;
				var con = "";
				if(!res.dataobj.bdlist.length){
					   $('#content').html('<div  class="searchInfo">无更多商洽内容</div>');
				}
				$.each(res.dataobj.bdlist, function(i, n){
					var backgrounds='background:url(${path}/resource/img/icons/good.jpg) no-repeat left center;background-size:0.32rem 0.30rem';
					if(n.isThumbup){
						backgrounds='background:url(${path}/resource/img/icons/bad.jpg) no-repeat left center;background-size:0.32rem 0.30rem';
					}
					if(n.isAccept=='1'){
						con += '<div class="cooperati4-item accepte">';
					}else if(n.isAccept=='2'){
						con += '<div class="cooperati4-item ">';
					}
					con += '<div class="cooper4-content"><div><div class="info-l" onclick="open_user_center('+n.userId+')"><img src="'+n.headPortrait+'" class="p-img" /></div></div> <div class="user-infor"><div>'+n.nickname;
					con += '</div><div>'+n.job+'/'+n.comName+'</div><p>'+n.content+'</p><div class="coop4-dates"><div>'+n.createTime+'</div>'+
					'<div><span class="active_A" onclick="comment_sub(\''+n.id+'\');"><b >'+n.subcount+'</b></span><span style="'+backgrounds+'" isThumbup='+n.isThumbup+' class="active_A givecomment" onclick="thumbup(this,\''+n.id+'\')"><b>'+
					n.count+'</b></span></div></div></div><br class="clear"/></div></div>';
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

//点击发布商洽按钮
var db_add=function(){
	if(rewardFinish==1){
		alert("悬赏完成，不能发布商洽！");
		return;
	}else if(rewardFinish==3){
		alert("该合作已过期！");
		return;
	}
	if(userId==fbUserId){
		alert("不可以给自己发布的合作发布商洽！");
		return;
	} 
	self.location.href='${path}/m/business/addBDPage.do?fkId=${id}';
}
//点击关注
function att_add(){
//	alert("不能收藏自己发布的合作！");
	/* var a = $("#guanzhu").html();
	zdy_ajax({
		url: '${path}/m/business/addAttention.do',
		data:{
			fkId:'${id}'
		},
		success: function(data,output){
			alert_back(output.msg,function(){
			//	window.location.reload(); 
				if(output.msg=="收藏成功"){
					$("#guanzhu").html(a*1+1);
				}else if(output.msg=="取消收藏"){
					$("#guanzhu").html(a*1-1);
				}
			});
		},
		error:function(e){
		}
	}); */
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
//点击回复
var comment_sub=function(_id){
	self.location.href='${path}/m/comment/selCommentByPid.do?parentId='+_id+'&type=1';
	///<!-- cmeType 1:资讯   2：合作 3：话题  4：活动 5:动态-->
	///self.location.href='${path}/m/comment/comment_add.do?fkId='+_id+'&cmeType=2';
}
//点击采纳
function caina(obj,_id){
	if(rewardFinish==3){
		alert("该合作已过期，不能采纳！");
		return;
	}else if(rewardFinish==1){
		alert("该合作已完成，不能采纳！");
		return;
	}
	zdy_ajax({
		url: "${path}/m/business/accept.do",
	    showLoading:false,
		data:{
			id:_id,
			fkId:'${id}'
		},
		success: function(data,res){
			if(res.code == 0){
				/* $(obj).html("已采纳");
				$(obj).removeClass('getIt active_A')
				$(obj).addClass('NogetIt active_A'); */
				window.location.reload(); 
			}else{
				alert(res.msg);
			}
		},
	    error:function(e){
		   //alert(e);
	    }
	}); 
}
//完成悬赏
function reward_finish(_id){
	if(rewardFinish==3){
		alert("该合作已过期，不能采纳！");
		return;
	}
	zdy_ajax({
		url: "${path}/m/business/rewardFinish.do",
	    showLoading:false,
		data:{
			id:_id
		},
		success: function(data,res){
			if(res.code == 0){
				$("#wcxs").show();
				$("#wwcxs").hide();
			}else{
				alert(res.msg);
			}
		},
	    error:function(e){
		   //alert(e);
	    }
	}); 
}
//查看他人主页
var open_user_center=function(userId){
	self.location.href="${path}/m/my/personal_home2.do?userId="+userId;
}

var show_zzc=function(t){
	$("body").bind("touchmove",function(event){
		event.preventDefault();
	});
	if(t && t == 1){
		scrollTop=$(document).scrollTop();
		$(document).scrollTop(0);
		$(window).bind("scroll",function(){
			$(document).scrollTop(0);
		});
	}
	
}

var hide_zzc=function(t){
	$("body").unbind("touchmove");
	if(t && t == 1){
		$(window).unbind("scroll");
		$(document).scrollTop(scrollTop);
	}
	
}

var reload=function(){
	self.location.href=self.location.href;
}
//点击采纳
function caina(){
	window.location.href = "${path}/m/business/acceptPage.do?id=${id}";  
}
function stretch(text){
	if(text=="更多"){
		$("#appealDesc").html(des+'<span class="more" onclick="stretch($(this).text())" style="margin-left:0.1rem;color:blue">收起</span>');
	}else{
		$("#appealDesc").html(des.substring(0,100)+'...'+'<span class="more" onclick="stretch($(this).text())" style="margin-left:0.1rem;color:blue">更多</span>');
	}
}
</script>
</html>