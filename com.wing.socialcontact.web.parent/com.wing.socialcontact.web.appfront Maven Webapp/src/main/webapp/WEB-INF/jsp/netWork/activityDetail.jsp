 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0" name="viewport">

        <title id="title">活动详情</title>
        <link rel="stylesheet" href="${path}/resource/css/activity-detail.css?v=${sversion}" />
		<link rel="stylesheet" href="${path}/resource/css/fund-invest.css?v=${sversion}" />
        <style>
          #comment_dec .btn{
             height:0.6rem;
             padding-left:0.6rem;
             padding-right:0.6rem;
             margin-left:0.24rem;
             color:white;
             border-radius:0.1rem;
             background:#ff811b;
             margin-bottom:0.1rem;
          }
          #comment_dec br{
           display:none
          }
          .sub-header em{
          font-weight:bold
          }
          span{
           white-space:normal!important;
          }
        </style>
    </head>
    
  
    <body>
        <div class="wrapper">
             <div class="activeDetail-box">
             	  <img class="activePoster" id="imagePath" />
             </div>

             <div class="sub-header" id="titles">
             <button id="status"  ></button>
             </div> 

             <div class="sub-header">
                 <img src="${path}/resource/img/icons/onmeeting.jpg"/><span>活动时间</span>：<i id="times" ></i>
             </div> 

             <div class="sub-header">
                <img src="${path}/resource/img/icons/onmeeting1.png"/><span>活动地点</span>：<i id="place"></i>
             </div> 

             <div class="sub-header">
                 <img src="${path}/resource/img/icons/onmeeting2.jpg"/><span>报名截止</span>：<i id="remaintime"></i>
             </div> 

             <div class="sub-header"  id="activityprice" style="display:none;">
                 <img src="${path}/resource/img/icons/onmeeting3.jpg"/><span>门票价格</span>：<i id="price"></i>
             </div> 

              <div class="sub-header margin-bottoms">
                 <img src="${path}/resource/img/icons/onmeeting4.jpg"/><span>主办方</span>：<i id="sponsor"></i>
                 <p id="sponsorIntroduce"></p>
             </div> 
              
              <div class="sub-header" >
                                                   <em> 活动详情</em>
             </div> 
             <div  class="actIntroduction margin-bottoms">
	             <div class="actIntroduction_content"  id="contents">
	             </div>
             </div> 
                	<%-- <jsp:include page="../commons/include_comment.jsp">
						<jsp:param name="id" value="${id}" />
						<jsp:param name="cmeType" value="4" />
					</jsp:include>cmeType 1:资讯   2：合作 3：话题  4：活动 --%>
             <div id="commentOna" class="active_A sub-header margin-bottoms">
                <div style="height:auto" onclick="lookCommentInfo()" >
                <em> 活动评论  </em>
                </div>       
             </div> 
			

             <div class="interestBox" id="actlists">
             	  <div class="sub-header" style="padding-left:0">
              <em>  您可能感兴趣的活动    </em>
                 </div> 

             </div>
        </div>
        <div class="buttonss">
        	<div  id="collectss" onclick="toCollect($(this))">
        	
        	</div>
        	<div id="baoming" class="baomingbg"></div>
        	<br class="clear"/>
        </div>
        
    </body>
<script type="text/javascript">
var id ='${id}';
var pinglun=1;
var issignup ='${issignup}';
//查看评论内容
function lookCommentInfo(){
	if(!pinglun){
		alert("此活动禁止评论！");
		return;
	}
	var id = "${id}"
	window.location.href="${path}/m/activity/activityComment.do?id="+id;
}

	
	$(function(){
		//alert(id);
		actdetail();
		uploadlist();
		if(zfflag){
			$("#activityprice").show();
		}else{
			$("#activityprice").hide();
		}
	});
	function actdetail(){
		zdy_ajax({
			url:'${path}/m/activity/activityDetail.do',
			showLoading:false,
			data:{
				  "id":id
			},
		    success: function(d,data){
				var str='';
				//alert(JSON.stringify(data));
				if(data.code == 0){
					var n = data.dataobj;
					pinglun = n.commentEnable;
					var baomings = "报名结束";
					//报名状态
					var status ="活动未通过审核";
					if(n.status==2){
						status="报名中"
						baomings='<a style="color:white" href="${path}/m/my/signupPage.do?id='+n.id+'" class="anniu">立刻报名</a>';
					}else
					if(n.status==6){
						status="活动已取消"
					}else
					if(n.status==3){
						status ="报名结束";
					}else
					if(n.status==4){
						status ="进行中";
					}else
					if(n.status==5){
						status ="已结束";
					}
					
					if(n.status!=2){
						$("#baoming").css("background-color","gray");
					}
					/* if(issignup=='1'){
						baomings='<a >您已报名</a>';
					} */
					$("#baoming").html(baomings);
					$("#imagePath").attr("src",_oss_url+n.imagePath);
					$("#titles").html(n.titles);
					$("#status").append(status);
					$("#times").append(formatDate(new Date(n.startTime))+"-"+formatDate(new Date(n.endTime)));
					$("#place").html(n.proName+n.cityName+n.countyName+n.place);
					if(n.ticketPrice>0){
					$("#price").append(n.ticketPrice+'元');
					}else{
					$("#price").append("免费");
					}
					$("#sponsor").append(n.sponsor);
					$("#sponsorIntroduce").append(n.sponsorIntroduce);
					var date3=new Date(n.signupTime)-new Date().getTime();  //时间差的毫秒数
					if(date3<0){
					$("#remaintime").append("已截止");
					}else{
						var days = date3 / 1000 / 60 / 60 / 24;
						var daysRound   = Math.floor(days);
						var hours    = date3/ 1000 / 60 / 60 - (24 * daysRound);
					$("#remaintime").append("剩余"+parseInt(days)+"天"+Math.round(hours)+"小时");
					}
					var strHtml=n.contents.replace(/&lt;/g,"<").replace(/&gt;/g,">");
					$("#contents").html(strHtml);
					if(n.iscollection){
						$('#collectss').text('');
						$('#collectss').append('<img src="${path }/resource/img/icons/gzsuccess.png"><span>取消收藏</span>');
					 }else{
						 $('#collectss').text('');
						 $('#collectss').append('<img src="${path }/resource/img/icons/gz.png"><span>收藏</span>');
					 } 
				}
				//分享设置
				var _title = "活动详情";
				var _imgUrl = "";
				if(n.titles.length > 0){
					_title = n.titles;
				}
				if(n.imagePath.length > 0){
					_imgUrl =_oss_url+n.imagePath;
				}
				var _link = home_path+_path+"/m/activity/activityDetailPage.do?id="+n.id;
				wxsharefun(_link,_title,_imgUrl,1);
			},
			error:function(e){
			}
		});
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
		return month+"/"+day+" "+hour+":"+minute; 
		} 
	function  uploadlist(){
		zdy_ajax({
			url:'${path}/m/activity/selActivityList.do',
			showLoading:false,
			data:{
				'size':2
			},
		    success: function(d,data){
				var str='';
				//alert(JSON.stringify(data));
				if(data.code == 0){
					$.each(data.dataobj, function(i, n){
						if(n.titles.length>10){
							n.titles=n.titles.substring(0,10)+"...";
						}
  						str+='<a href="${path}/m/activity/activityDetailPage.do?id='+n.id+'"><p><img class="activePoster" src="'+_oss_url+n.imagePath+'"/><div class="sub-header" style="padding-left:0;border:none">'
  						+n.titles+'<time>'+formatDate(new Date(n.startTime))+'</time></div></p></a>' ;
  							
					});
					$("#actlists").append(str);
				}
			},
			error:function(e){
			}
		});
	}
	
	function toCollect(current){
		   if(current.text()=='取消收藏'){
			   zdy_ajax({
				   url:_path+"/mycollection/m/del.do",
				   showLoading:false,
				   data:{
					 'id':(window.location.search.substr(1).split('='))[1],
					 'type':3
					
				   },
				   success:function(data,be){
					layer.msg('已取消收藏');
					$('#collectss').text('');
					$('#collectss').append('<img src="${path }/resource/img/icons/gz.png"><span>收藏</span>');
				   },
				   error:function(data){
					   
				   }
					
				}); 
		   }else{
		   zdy_ajax({
			   url:_path+"/mycollection/m/add.do",
			   showLoading:false,
			   data:{
				 'id':(window.location.search.substr(1).split('='))[1],
				 'type':3
				
			   },
			   success:function(data,be){
				layer.msg("收藏成功");
				$('#collectss').text('');
				$('#collectss').append('<img src="${path }/resource/img/icons/gzsuccess.png"><span>取消收藏</span>');
			   },
			   error:function(data){
				   
			   }
				
			}); 
		   };
	   }
</script>
</html>