<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="资讯">
		<title>人脉圈</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/netWork.css?v=${sversion}" />
	   <style type="text/css">
	   .swiper-container1 {
        width: 100%;
        height: 1.70rem;
        overflow: hidden;
       background: #fff;
        }
	   
	   .swiper-container1	.swiper-wrapper a{
	   	display: flex;
        flex-direction: column;
        font-size: .24rem;
         color: #666;
        align-items: center;
        justify-content: center;
	   	
	   	}
	   	.swiper-container1	.swiper-wrapper a img{
	   	width: .76rem;
        height: .76rem;
        margin-bottom: .1rem; 	
	   	} 
	   
	   </style>
	</head>

	<body>
		<div class="T-netWork" style="background: #f2f3f4;width: 100%;">
			
					<jsp:include page="../commons/include_banner.jsp" >
						<jsp:param name="bannerid" value="55a34dcafbcc4057ae33f70c37eae7c1" />
					</jsp:include>
			<%-- <div class="imgBox">
					    <a href="${path}/wx/netWork/friendRecommend.do">
							<img src="${path}/resource/img/icons/rm_03.png" />
							<span>商友推荐</span>
						</a>
						<a href="${path}/m/topic/topicPage.do">
							<img src="${path}/resource/img/icons/h_03.png" />
							<span>话题PK</span>
						</a>
						<a href="${path}/m/activity/friendPlayPage.do">
							<img src="${path}/resource/img/icons/g_03.png" />
							<span>以玩会友</span>
						</a>
						<a href="${path}/m/activity/bookPlayPage.do">
							<img src="${path}/resource/img/icons/l_03.png" />
							<span>以书会友</span>
						</a>
						<a href="${path}/m/business/indexPage.do">
							<img src="${path}/resource/img/icons/f_03.png" />
							<span>合作洽谈</span>
						</a> 
						<a href="${path}/wx/netWork/jiutianClub.do">
							<img src="${path}/resource/img/icons/tj.png" />
							<span>天九会所</span>
						</a>  
			</div> --%>
			<div class="swiper-container1">
			   <div class="swiper-wrapper">
					    <a href="${path}/wx/netWork/friendRecommend.do" class="swiper-slide">
							<img src="${path}/resource/img/icons/rm_03.png" />
							<span>商友推荐</span>
						</a>
						<a href="${path}/m/topic/topicPage.do" class="swiper-slide">
							<img src="${path}/resource/img/icons/h_03.png" />
							<span>话题PK</span>
						</a>
						<a href="${path}/m/activity/friendPlayPage.do" class="swiper-slide">
							<img src="${path}/resource/img/icons/g_03.png" />
							<span>以玩会友</span>
						</a>
						<a href="${path}/m/activity/bookPlayPage.do" class="swiper-slide">
							<img src="${path}/resource/img/icons/l_03.png" />
							<span>以书会友</span>
						</a>
						<a href="${path}/m/business/indexPage.do" class="swiper-slide">
							<img src="${path}/resource/img/icons/f_03.png" />
							<span>合作洽谈</span>
						</a> 
						<a href="${path}/wx/netWork/jiutianClub.do" class="swiper-slide">
							<img src="${path}/resource/img/icons/tj.png" />
							<span>天九会所</span>
						</a>  
			   </div> 
            </div>
			<div class="tonghao">
				<div class="th">
					<h2>同好圈子</h2>
					<a href="#" class="th-a" style="display:none">
						<span>更多</span>
						<i class="iconfont ty">&#xe605;</i>
					</a>
				</div>
				<div class="timgBox" id="samehobbies">
					
				</div>
			</div>
			<div id="jxht">
			</div>
			
			<div class="z-active">
				<div class="th">
					<h2>热门活动</h2>
					<a href="#" class="th-a" style="display:none">
						<span>更多</span>
						<i class="iconfont ty">&#xe605;</i>
					</a>
				</div>
				<div class="timgBox"  id="rmhd">
					
				</div>
				<div style="clear:both"></div>
				<div class="loadingbox">
					<div class="page_loading" style="display:block;">加载中…</div>
					<div class="page_nodata" style="display:none;">暂无更多数据</div>
				</div>
			</div>
		</div>
		<script>
		var swiper = new Swiper('.swiper-container1', {
            slidesPerView: 5.5,
        });
		
    (function(){
    	zdy_ajax({
			   url:_path+"/im/m/selIdenticalHobbyWorld.do",
			   showLoading:false,
			   data:{
				   pageNum:1,
				   pageSize:10
			   },
			   success:function(data){
				  //console.log(data);
				 var str='';
				 var datalength='';
				 if(data.length>4){
					 datalength=4;
				 }else{
					 datalength=data.length;
				 }
				 for(var i=0;i<datalength;i++){
					 var headpath='';
					  if(!data[i].head_portrait){
						  headpath='${path}/resource/img/icons/qun.png';
					  }else{
						  headpath=data[i].head_portrait;
					  }
					 str += '<a href="${path}/wx/businessFriend/group-detail.do?follow_user='+data[i].group_id+'" class="active_A">'+
								'<div class="samehobbyCover"></div>'+
							    '<img src="'+headpath+'" alt="" />'+
								'<span>'+data[i].group_name+'('+data[i].userCount+'人)'+'</span>'+
							'</a>';
				 }
				   
                           $('#samehobbies').append(str);	
                           jxht();
			   },
			   error:function(data){
				   
			   }
				
			}); 
    }());
//精选话题		    
function jxht(){
	zdy_ajax({
		   url:"${path}/m/topic/selJxTopicList.do",
		   showLoading:false,
		   data:{
			   
		   },
		   success:function(data,res){
			   if(res.code == 0){
					var c = "";
					$.each(res.dataobj, function(i, n){
						var zd = parseInt(n.redCount)+parseInt(n.blueCount);
						c += '<div class="jingxuan">';
						if(i==0){
							c += '<div class="th"><h2>精选话题</h2><a style="display:none" href="${path}/m/topic/topicPage.do" class="th-a"><span>更多</span><i class="iconfont ty">&#xe605;</i></a></div>';
						}
						c += '<div class="info-l"><img src="'+n.headPortrait+'" class="p-img" />'+
						'<div class="person"><span class="p-name">'+n.nickname+'</span><span class="company">'+n.comName+'</span>'+
						'</div></div><div class="j-tit" onclick="openurl(\'${path}/m/topic/detailPage.do?id='+n.id+'\')">'+n.topicDesc+'</div><div class="t-info">'+
						'<span><b>'+n.createTime+'</b></span><span><b>'+zd+'人站队</b></span><span>'+
						'<b>'+n.countNum+'条评论</b></span></div></div>';
					});
					$("#jxht").append(c);
				}else{
					alert(res.msg);
				}					
		   },
		   error:function(data){
			   
		   }
			
		}); 
}
function openurl(url){
	document.location.href=url;
}

var page=1;
var end = false;
var pageSize = 10;
$(function(){
	activities();
	
	//滚动加载
    $(window).scroll(function(){
	    if($(window).scrollTop() >= $(document).height() - $(window).height()){
	        if(!end){
	        	activities(); 
	        }
	    }
	   // console.log($(window).scrollTop()+"  "+($(document).height() - $(window).height()));
	});
});
function activities(){
	if(!end){
	zdy_ajax({
	   showLoading:false,
		url:'${path}/m/activity/selActivityList.do',
		data:{
			  "page":page,
			  "size":pageSize,
			  "recommendEnable":1
		   },
	   success: function(data1,data){
			var str='';
			if(data.code == 0){
			    if(page==1 && !data.dataobj.length){
				   $('#rmhd').html('<div  class="searchInfo">没有数据</div>');
				   $(".page_loading").hide();
			    }else if(data.dataobj.length==0 || data.dataobj.length<pageSize){
					$(".page_loading").hide();
					$(".page_nodata").show();
				    end=true;
			    };
				$.each(data.dataobj, function(i, n){
					
					if(n.titles.length>9){
						n.titles=n.titles.substring(0,9)+"..."
					}
					str+='<a href="${path}/m/activity/activityDetailPage.do?id='+n.id+'">'
							+'<div class="activityCover"></div>'
							+'<img src="'+_oss_url+n.imagePath+'" /><span>'
							+n.titles+'</span><span>'+n.cou+' 人报名</span></a>';
							
				});
			$(".page_loading").hide();
			$("#rmhd").append(str);
			page++;
			}else{
				alert(data.msg);
			}
		},
		error:function(e){
		}
	});
	}
}
		</script>
	</body>

</html>