<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>

<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title>话题详情</title>
	    <link rel="stylesheet" type="text/css" href="${path}/resource/css/talkPKDetail.css?v=${sversion}" /> 
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/informationDetail.css?v=${sversion}" />
		<style>
			
		   .infor{
		          padding:0px;
		          border-bottom:1px solid #e6e6e6;
		          padding-top:0.2rem;
		          padding-bottom:0.2rem;
		   }
		   .msgBox{
		        position:relative;
		        padding-top:0px
		   }
		   .loadingbox{
		      position:absolute;
		      bottom:-0.8rem;;
		      left:0;
		      text-align:center
		   }
		   .c-footer .talk{
		      font-size:0.36rem;
		   }
		 .infor .infor-r .rt .topicDetails{
		      font-size:0.26rem;
		 }
		 #comment_dec img{
		    max-width:6.0rem;
		    max-height:6.06rem;
		 }
		 .pk-sm{
			width: 100%;
			display: flex;
			padding: 0 0.3rem;
			box-sizing: border-box;
			justify-content: space-around;
			align-items: center;
			background: #fff;
			margin-bottom: .02rem;
			padding-bottom:0.1rem;
			font-size: .30rem;
			display:block;
		}
		#gzimg{
			height: 0.3rem;
			width: 0.3rem;
		}
		</style>
	</head>

	<body style="background: #f2f3f4;">
		<div class="T-detail" style="width: 100%;padding-bottom:1rem;">
			<div class="info active_A">
				<div class="info-l">
					<img src=""  id="imgdiv"/>
				</div>
				<div class="person">
				    <p><span class="p-name" id="xm"></span><span class="profession" id="zw"></span></p>
				    <p class="company" id="gs"></p>
			   </div>
				<div class="info-r">
					<span id="zddiv"></span>
					<span onclick="report_add();" style="color:#808080"><img style="height:0.35rem;width:0.35rem;vertical-align:middle" src="${path}/resource/img/icons/report1.png"/>举报</span>
				</div>
			</div>
			<div class="pk">
				<h2 id="titlediv"></h2>
				<div class="pk-sm" id="topicExplain">
			    </div>
				<div class="pk-s">
					<div class="zy" id="zy">
					</div>
					<div class="pk-img" id="pkimg">
					<img src='${path}/resource/img/images/issuePk03.jpg' />
					<span>观点战队</span>
					</div>
					<div class="bzy" id="bzy">
						
					</div>
				</div>
				<!-- <div class="img-box" id="fbsj">
				
				</div> -->
				<div class="btnSpan">
					<span class="active_A redside" onclick="addvote('1')" id="redside">
					        红方观点<img src="${path}/resource/img/icons/haveChoosed.png"/>
					</span>
					<span class="active_A blueside" onclick="addvote('2')" id="blueside">
					       蓝方观点<img src="${path}/resource/img/icons/haveChoosed.png">
					</span>
				</div>
			</div>
			<div class="msgBox" id="pinglundiv">
			<jsp:include page="../commons/include_comment.jsp" >
			<jsp:param  name="id" value="${id}" />
			<jsp:param name="cmeType" value="3" />
		    </jsp:include><!-- cmeType 1:资讯   2：合作 3：话题  4：活动 5:动态-->
			</div>
			<div class="wrapper" id="wrapper">
				<div class="scrollbar" id="scrollbar">

				</div>
			</div>
			<div class="c-footer">
				<div class="foucs active_A" onclick="att_add();">
					<div class="fF fF1">
						<img   id="gzimg"  src="">
						<span id="guanzhu" ></span>
					</div>
				</div>
				<div class="meg active_A" id="commentdiv">
					<div class="fF">
						<i class="iconfont">&#xe649;</i>
						<span id="pinglun"></span>
					</div>
				</div>
				<span class="talk active_A" onclick="reward_add();">打赏</span>
			</div>
		</div> 
	</body>
<script type="text/javascript">	
var _id="${id}";
var _cmeType="3";
var des = '';
$(document).ready(function() {
	zdy_ajax({
		url: "${path}/m/topic/detail.do",
		data:{
			id:'${id}'
		},
		success: function(data,res){
			if(res.code == 0){
				console.log(data);
				if(data.voteType=="1"){
					$('#redside img').css("display",'block');
					$('#redside img').addClass('choosed');
				}else if(data.voteType=="2"){
					$('#blueside img').css("display",'block');
					$('#blueside img').addClass('choosed');
				}
				var topic = res.dataobj.topic;
				var zd = parseInt(topic.redCount)+parseInt(topic.blueCount);
				$("#zddiv").append('<b>'+zd+"</b>人站队");
				$("#xm").append(topic.nickname);
				$("#zw").append("/"+topic.job);
				$("#gs").append(topic.comName);
				des = topic.topicExplain;
				$("#imgdiv").attr('src',topic.headPortrait); 
				var zy = "<span>"+topic.redPoint+"</span><span><b>"+topic.redCount+"</b>人</span>";
				$("#zy").append(zy);
				$("#bzy").append("<span>"+topic.bluePoint+"</span><span><b>"+topic.blueCount+"</b>人</span>");
				console.log(topic.topicExplain)
				$("#titlediv").append(topic.topicDesc);
				if(topic.topicExplain!=null&&topic.topicExplain!=""){
					if(topic.topicExplain.length>100){
						$("#topicExplain").append(topic.topicExplain.substring(0,100)+'...'+'<span class="more" onclick="stretch($(this).text())" style="margin-left:0.1rem;color:blue">更多</span>');
					}else{
						$("#topicExplain").append(topic.topicExplain);
					}
				}
				$("#pinglun").append(topic.countNum+"条评论");
				$("#guanzhu").append('<b>'+res.dataobj.attentionCount+"</b>人收藏");
				if(data.iscollected==0){
					$("#gzimg").attr("src","${path }/resource/img/icons/gz.png");
				}else{
					$("#gzimg").attr("src","${path }/resource/img/icons/gzsuccess.png");
				}
				if(topic.allowComment==1){
					$("#pinglundiv").show();
					$("#commentdiv").show();
					$("#commentdiv").on("click",function(){
						comment_add();
					})
				}else if(topic.allowComment==2){
				     /*  $("#pinglundiv").hide();
					  $("#commentdiv").hide(); */
					  $("#commentdiv").on("click",function(){
							layer.msg('亲，你不能评论该话题')
						})
				}
				$("#titlediv").after('<p style="font-size:0.24rem;font-weight:normal;color:#808080;padding:0 .3rem;text-align:right;box-sizing:border-box;background:#fff"><i class="iconfont">&#xe639;</i>'+formatDate(new Date(topic.createTime))+'</p>');
				$('#imgdiv').click(function(){
					self.location.href="${path}/m/my/personal_home2.do?userId="+topic.createUserId;
				});
				
				//分享设置
				var _title = "话题详情";
				var _imgUrl ="http://tianjiu.oss-cn-beijing.aliyuncs.com/upload/system/a3a14eac-c89a-4e6a-bccd-99602c3f74ad.png";
				if(topic.topicDesc.length > 0){
					_title = topic.topicDesc;
				}
				var _link = home_path+_path+"/m/topic/detailPage.do?id="+topic.id;
				wxsharefun(_link,_title,_imgUrl);
			}
		}
	}); 
});
function addvote(type){
	
	if(type=='1' && !$('#blueside img').hasClass('choosed') ){
		$('#redside img').css("display",'block');
		$('#redside img').addClass('choosed');
	}else if(type=='2'  && !$('#redside img').hasClass('choosed')){
		$('#blueside img').css("display",'block');
		$('#blueside img').addClass('choosed');
	}
	zdy_ajax({
		url: '${path}/m/topic/addVote.do',
		data:{
			voteType:type,
			fkId:'${id}'
		},
		success: function(data,output){
			if(output.code == 0){
				/* alert_back("投票成功！",function(){
					if(type=='1'){
						var a=$('#zy').find("b").html();
						$('#zy').find("b").html(a*1+1);
					}else if(type=='2'){
						var a=$('#bzy').find("b").html();
						$('#bzy').find("b").html(a*1+1);
					}
					var b=$('#zddiv').find("b").html();
					$('#zddiv').find("b").html(b*1+1);
				}); */
				alert("投票成功！");
				if(type=='1'){
					var a=$('#zy').find("b").html();
					$('#zy').find("b").html(a*1+1);
				}else if(type=='2'){
					var a=$('#bzy').find("b").html();
					$('#bzy').find("b").html(a*1+1);
				}
				var b=$('#zddiv').find("b").html();
				$('#zddiv').find("b").html(b*1+1);
			}
		},
		error:function(e){
		}
	});
}
//点击评论按钮
var comment_add=function(){
	self.location.href='${path}/m/comment/comment_add.do?fkId='+_id+'&cmeType='+_cmeType;
}

//点击举报按钮
var report_add=function(){
	self.location.href='${path}/m/topic/reportPage.do?fkId='+_id;
}
//点击关注
function att_add(){
	var a = $("#guanzhu").find("b").html();
	zdy_ajax({
		url: '${path}/m/topic/addAttention.do',
		data:{
			fkId:'${id}'
		},
		success: function(data,output){
			alert_back(output.msg,function(){
		//		window.location.reload(); 
				if(output.msg=="收藏成功"){
					$("#guanzhu").find("b").html(a*1+1);
					$("#gzimg").attr("src","${path }/resource/img/icons/gzsuccess.png");
				}else if(output.msg=="取消收藏"){
					$("#guanzhu").find("b").html(a*1-1);
					$("#gzimg").attr("src","${path }/resource/img/icons/gz.png");
				}
			});
		},
		error:function(e){
		}
	});
}
//点击打赏
var reward_add=function(){
	self.location.href='${path}/m/topic/rewardPage.do?fkId='+_id;
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
	
function stretch(text){
	if(text=="更多"){
		$("#topicExplain").html(des+'<span class="more" onclick="stretch($(this).text())" style="margin-left:0.1rem;color:blue">收起</span>');
	}else{
		$("#topicExplain").html(des.substring(0,100)+'...'+'<span class="more" onclick="stretch($(this).text())" style="margin-left:0.1rem;color:blue">更多</span>');
	}
}
</script>
</html>