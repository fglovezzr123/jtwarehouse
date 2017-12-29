<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="合作列表">
		<title>合作详情</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/mycooperate.css?v=${sversion}" />
	</head>

	<body>
		<div class="wrapper">
			 <div id="titles"></div>
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

            <div class="cooperati4" id="content">
            	  
            </div>

   		</div>
    
	   <div class="mycooperateBtn">
	   	   <div class="active_A" onclick="att_add();" id="guanzhu"></div>
	   	   <div class="active_A" onclick="db_add();" >发布商洽</div>
	   	   <div class="active_A" style="display:none">立刻沟通</div>
	   	   <br class="clear"/>
	   </div>	 
	</body>

<script type="text/javascript">	
var userId = "";
var fbUserId = "";
var rewardFinish = 1;
var cooperationdes='';
var startTime;
$(document).ready(function() {
	
	zdy_ajax({
		url: "${path}/m/business/detail.do",
	    showLoading:false,
		data:{
			id:'${id}'
		},
		success: function(data,res){
			console.log(data.isAttention);
			if(data.isAttention){
				$("#guanzhu").text("已收藏");
				$("#guanzhu").attr("style","text-indent:0.3rem;width:50%;background: url("+_path+"/resource/img/icons/gzsuccess.png) no-repeat left center;background-size:0.30rem 0.27rem;background-position-x:1.3rem;");
			}else{
				$("#guanzhu").text("收藏");
				$("#guanzhu").attr("style","text-indent:0.3rem;width:50%;background: url("+_path+"/resource/img/icons/gz.png) no-repeat  left center;background-size:0.30rem 0.27rem;background-position-x:1.3rem;");
			}
			if(res.code == 0){
				userId = res.dataobj.userId;
				var b = res.dataobj.business;
				fbUserId = b.createUserId;
				cooperationdes=b.appealDesc;
				$("#titles").append('<h1 class="fistH1" >'+b.titles+'</h1>');
				$("#className").append(b.className);
				$("#yxq").append(b.startTime+"至"+b.endTime);
				$("#reward").append(b.reward+" J币");
				$("#appealSummary").append(b.appealSummary);
				if(b.appealDesc.length>45){
					$("#appealDesc").append(b.appealDesc.substring(0,45)+'...'+'<span class="more" onclick="stretch($(this).text())" style="margin-left:0.1rem;color:blue">更多</span>');
				}else{
					$("#appealDesc").append(b.appealDesc);
				}
				startTime = b.startTime;
				$("#createTime").append(b.createTime);
				$("#lookCount").append('<i>'+b.lookCount+'</i>人浏览');
				//关注人数显示 $("#guanzhu").append(b.countNum);
				rewardFinish = b.rewardFinish;
				var con = "";
				if(!res.dataobj.bdlist.length){
					   $('#content').html('<div class="page_nodata">无更多商洽内容</div>');
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
					var honor_flag = n.honor_flag,tjIdImg="";
					if(honor_flag=="honor_001"){
						tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjjr.png"  class="tj" /></div>'
					}else if(honor_flag=="honor_002"){
						tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjyq.png"  class="tj" /></div>'
					}else if(honor_flag=="honor_003"){
						tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjhb.png"  class="tj" /></div>'
					}else if(honor_flag=="honor_004"){
						tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjfws.png"  class="tj" /></div>'
					}
					con += '<div class="cooper4-content"><div><div class="info-l" onclick="open_user_center('+n.userId+')"><img src="'+n.headPortrait+'" class="p-img" /></div></div> <div class="user-infor"><div style="display:flex;align-items: flex-start;">'+n.nickname+tjIdImg+
					'</div><div>'+n.job+'/'+n.comName+'</div><p>'+n.content+'</p><div class="coop4-dates"><div>'+n.createTime+'</div>'+
					'<div><span class="active_A" onclick="comment_sub(\''+n.id+'\');"><b >'+n.subcount+'</b></span><span style="'+backgrounds+'" isThumbup='+n.isThumbup+' class="active_A givecomment" onclick="thumbup(this,\''+n.id+'\')"><b>'+n.count+'</b></span></div></div></div><br class="clear"/></div></div>';
				});
				$("#content").append(con);
				
				//分享设置
				var _title = "合作详情";
				var _imgUrl = "http://tianjiu.oss-cn-beijing.aliyuncs.com/upload/system/a3a14eac-c89a-4e6a-bccd-99602c3f74ad.png";
				if(b.titles.length > 0){
					_title = b.titles;
				}
				var _link = home_path+_path+"/m/business/detailPage.do?type=1&id="+b.id;
				wxsharefun(_link,_title,_imgUrl);
				
			}else{
				alert(res.msg);
			}
		},
	    error:function(e){
		   //alert(e);
	    }
	}); 
	
	
	
	
});
function stretch(text){
	if(text=="更多"){
		$("#appealDesc").html(cooperationdes+'<span class="more" onclick="stretch($(this).text())" style="margin-left:0.1rem;color:blue">收起</span>');
	}else{
		$("#appealDesc").html(cooperationdes.substring(0,45)+'...'+'<span class="more" onclick="stretch($(this).text())" style="margin-left:0.1rem;color:blue">更多</span>');
	}
}

//点击发布商洽按钮
var db_add=function(){
	var currTime = new Date();
	currTime=currTime.getFullYear()+'-'+(currTime.getMonth()+1)+"-"+currTime.getDate();
	var reg = new RegExp('-', 'g');
	var startTimef = startTime.replace(reg, '/');//正则替换
	currTime = currTime.replace(reg, '/');
	startTimef = new Date(parseInt(Date.parse(startTimef), 10));
	currTime = new Date(parseInt(Date.parse(currTime), 10));
	if(startTimef>currTime){
		alert_back("该合作未开始，不能发布商洽",function(){
			
		});
		return;
	}
	if(userId==fbUserId){
		alert("不可以给自己发布的合作发布商洽！");
		return;
	} 
	if(rewardFinish==1){
		alert("合作完成，不能发布商洽！");
		return;
	}else if(rewardFinish==3){
		alert("该合作已过期！");
		return;
	}
	self.location.href='${path}/m/business/addBDPage.do?fkId=${id}';
}
//点击关注
function att_add(){
	var a = $("#guanzhu").html();
	/* if(userId==fbUserId){
		alert("不能收藏自己发布的合作！");
	} */
	zdy_ajax({
		url: '${path}/m/business/addAttention.do',
		data:{
			fkId:'${id}'
		},
		success: function(data,output){
			alert_back(output.msg,function(){
				//window.location.reload(); 
				if(output.msg=="收藏成功"){
					//$("#guanzhu").html(a*1+1);
					$("#guanzhu").text("已收藏");
					$("#guanzhu").attr("style","text-indent:0.3rem;width:50%;background: url("+_path+"/resource/img/icons/gzsuccess.png) no-repeat left center;background-size:0.30rem 0.27rem;background-position-x:1.3rem;");
				}else if(output.msg=="取消收藏"){
					//$("#guanzhu").html(a*1-1);
					$("#guanzhu").text("收藏");
					$("#guanzhu").attr("style","text-indent:0.3rem;width:50%;background: url("+_path+"/resource/img/icons/gz.png) no-repeat left center;background-size:0.30rem 0.27rem;background-position-x:1.3rem;");
				}
			});
		},
		error:function(e){
		}
	});
	
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
	/* layer.open({
        type: 2,
        title: '回复商洽',
        fix: true,
        shadeClose: true,
        maxmin: false,
        area: ['100%', '100%'],
        content: '${path}/m/comment/selCommentByPid.do?parentId='+_id+'&type=1',
        shift: 2,
        scrollbar: false,
        success: function(layero, index){
        	show_zzc(1);
        },
        end: function(){
        	hide_zzc(1);
        },
        cancel: function(cancel){
        }
    }); */
	self.location.href='${path}/m/comment/selCommentByPid.do?parentId='+_id+'&type=1';
	///<!-- cmeType 1:资讯   2：合作 3：话题  4：活动 5:动态-->
	///self.location.href='${path}/m/comment/comment_add.do?fkId='+_id+'&cmeType=2';
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
</script>
</html>