<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<meta name="keywords" content="天九共享网">
		<title>天九共享网</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/index.css?v=${sversion}" />
		<style> 
			 body{
			  background:#f2f3f4;
			 }
			 .layui-layer-loading .layui-layer-content{
			      width:100%
			 }
			 .banner_ul img{
			     height:auto;
			 }
			 .tRb-T-t{
			  z-index:1000;
			 }
			 #about-tj img{
			  width:100%
			 }
			
           /*文章部分*/
     		.item{
     			padding: 0.15rem .2rem;
     			background: #F2F3F4;
     			display: flex;
     		}
     		.item .it-img{
     			width: 2.04rem;
     			height: 1.02rem;
     		}
     		.item .it-img img{
     			width: 100%;
     		}
     		.item .it-cont{
     			/*width: 4.4rem;*/
     			width: 63.77%;
     			font-size: .26rem;
     			padding-left: .15rem;
     			position: relative;
     			box-sizing: border-box;
     		}
     		.item .it-cont h3{
     			font-size: .25rem;
     			font-weight: normal;
			    word-break: break-all;
			     text-overflow: ellipsis;
			     display: -webkit-box; /** 对象作为伸缩盒子模型显示 **/
			     -webkit-box-orient: vertical; /** 设置或检索伸缩盒对象的子元素的排列方式 **/
			     -webkit-line-clamp: 2; /** 显示的行数 **/
			     overflow: hidden;  /** 隐藏超出的内容 **/
     		}
     		.item .it-cont .bot{
     			width: 100%;
     			display: flex;
     			justify-content: space-between;
     			font-size: .24rem;
     			position: absolute;
     			 bottom: 0;
     			left: 0;
     			padding-left: .15rem;
     			box-sizing: border-box;
     		}
     		/* .item .it-cont .bot span{
     			line-height: 2.5;
     		} */
		</style>
  
	</head>
	<body>
		      <div class="search-box">
				 <div id="search">搜索</div>
			 </div>
			<div class="wrapper" id="wrapper">
				<div class="scrollbar" id="scrollbar" >
					<jsp:include page="commons/include_banner.jsp" >
						<jsp:param name="bannerid" value="3d1ba858eaab4f93b6d4d5ead09014c8" />
						<jsp:param name="width" value="7.5" />
						<jsp:param name="height" value="3" />
					</jsp:include>
					<div class="content">
						<div class="tRb content-t">
							<div class="tRb-T">
								<img src="${path}/resource/img/icons/trb.png"/>
								<span class="tRb-T-t  active_A" onclick="openurl('${path}/m/investment/index.do')">投融宝</span>
							</div>
							<div class="tRb-D">
								<span class="tRb-D-l active_A" onclick="openurl('${path}/m/meeting/index.do')">投洽峰会</span>
								<span class="tRb-D-r active_A" onclick="openurl('${path}/m/project/index1.do')">项目联营</span>
							</div>
							<div class="tRb-D">
								<span class="tRb-D-l active_A" onclick="openurl('${path}/m/project/collect/index.do')">项目征集</span>
								<span class="tRb-D-r active_A" onclick="openurl('${path}/m/news/htmlDetailPage.do?id=ecbbb33f23a04cb79e161e2c0e9308b0')">金服联盟</span>
							</div>
						</div>
						<div class="tRb content-t">
							<div class="tRb-T">
								<img src="${path}/resource/img/icons/jyh.png" />
								<span class="tRb-T-t active_A" onclick="openurl('https://www.tojoycloud.org/phoneh5_zh/trade.html')">交易会</span>
 								<%-- <span class="tRb-T-t active_A" onclick="openurl('${path}/wx/notOpen.do')">交易会</span>  --%>
							</div>
							<div class="tRb-D">
								<span class="tRb-D-l active_A" onclick="openurl('https://www.tojoycloud.org/phoneh5_zh/index.html')">互助商城</span>
								<%-- <span class="tRb-D-l active_A" onclick="openurl('${path}/wx/notOpen.do')">互助商城</span> --%>
								<%-- <span class="tRb-D-r active_A" onclick="openurl('${path}/wx/notOpen.do')">互助团购</span> --%>
								<span class="tRb-D-r active_A" onclick="openurl('https://www.tojoycloud.org/phoneh5_zh/hzbIndex.html')" >互助团购</span>
							</div>
							<div class="tRb-D">
								<span class="tRb-D-l active_A" onclick="openurl('https://www.tojoycloud.org/phoneh5_zh/productRecommend.html?hzb_flag=1')">爆品推荐</span>
								<%-- <span class="tRb-D-l active_A" onclick="openurl('${path}/wx/notOpen.do')">爆品推荐</span> --%>
								<span class="tRb-D-r active_A" onclick="openurl('${path}/m/news/htmlDetailPage.do?id=856afb78405e41bebf759f6544d5221d')">营销联盟</span>
							</div>
						</div> 
						
						<div class="tRb content-t " >
							<div class="tRb-T">
								<img src="${path}/resource/img/icons/rmq.png" />
								<span class="tRb-T-t active_A" onclick="openurl('${path}/wx/netWork/netWork.do')">人脉圈</span>
							</div>
							<div class="tRb-D">
							<span class="tRb-D-l active_A" onclick="openurl('${path}/m/topic/topicPage.do')">话题&nbsp;P&nbsp;K</span>
							<span class="tRb-D-r active_A" onclick="openurl('${path}/m/activity/friendPlayPage.do')">以玩会友</span>	
								
							</div>
							<div class="tRb-D">
							<span class="tRb-D-l active_A" onclick="openurl('${path}/wx/netWork/friendRecommend.do')">商友推荐</span>
								<span class="tRb-D-r active_A" onclick="openurl('${path}/m/business/indexPage.do')">合作洽谈</span>
								
							</div>
						</div>
						<div class="tRb content-t " >
							<div class="tRb-T">
								<img src="${path}/resource/img/icons/zmt.png"  />
								<%-- <span class="tRb-T-t active_A" onclick="openurl('${path}/m/jhy/view.do?id=e9d180976722411aa39b011031dd548e')">智囊团</span> --%>
								<span class="tRb-T-t active_A" onclick="openurl('${path}/wx/wisdomGroup/zntindex.do')">智囊团</span>
							</div>
							<div class="tRb-D">
								<span class="tRb-D-l active_A" onclick="openurl('${path}/library/m/all-classes.do?id=c993ec37dec341d28c205bfdba6441b1&level=2')">老板悄悄话</span>
								<span class="tRb-D-r active_A" onclick="openurl('${path}/m/reward/indexPage.do')">企业知道</span>
							</div>
							<div class="tRb-D">
								<span class="tRb-D-l active_A" onclick="openurl('${path}/wx/wisdomGroup/zsmsindex.do')">知识秘书</span>
								<span class="tRb-D-r active_A" onclick="openurl('${path}/m/news/htmlDetailPage.do?id=6441e97b64c84c21b8499f3681f759c3')">咨询联盟</span>
							</div>
						</div>
					</div>
					<div class="zixun">
					  <%--   <p style="font-size:.30rem;width:100%;background:url(${path}/resource/img/icons/phone.png);background-repeat: no-repeat; background-size: 0.42rem 0.35rem;background-position-x: 0.6rem;padding-left: 1.2rem;">测试问题请拨打：<a href="tel:010-53118922" style="color:#0f88eb;">010-53118922</a></p> --%>
						<%--  <img src="${path}/resource/img/icons/zx.png" class="zxn-t" onclick="openurl('${path}/m/news/newsPage.do')"/>
						<img src="${path}/resource/img/icons/jiantou.png" class="iconsRight"/> --%>
						 <p style="font-size:.30rem;width:10%;height:100%;background:url(${path}/resource/img/icons/phone.png) center;background-repeat: no-repeat; background-size: 0.42rem 0.35rem;"></p>
						<div id="scrollTextdiv" style="width:90%">
							<ul id="notice_list">
								<li class="zxn-c" ><a href="javascript:void(0);" >11月30日前为试运行期，欢迎反馈建议或意见</a></li>
								<li class="zxn-c" ><a href="tel:010-53118922" >以便完善升级，电话:<em style="color:#0f88eb;">010-53118922</em></a></li>
							</ul>
						</div>
						
					</div>
					<div class="dc">
						<div class="dc-t">
							<span class="myDc span dc-t-active" onclick="showDynamicInfo('businessFriend',true)">商友动态</span>
							<span class="myFoucs span " onclick="showDynamicInfo('follow',true)">我的关注</span>
							<span class="myFoucs span" onclick="abouttj()">关于我们</span>
						</div>
						<div class="dtTit" style="display:none" onclick="loadnewdynamic()">
						         有<em id="newDynamicCount">10</em>条新动态
					    </div>
						<ul class="ul-hide">
							<li class="show-active"  id="dynamicPage">
							</li>
							<li id="myfollowdynamic">
							  
							</li>
							<li id="about-tj">
								<%-- <img class="img1" src="${path}/resource/img/icons/tjjs.jpg">
								<img class="img2" src="${path}/resource/img/icons/tjjs2.jpg"><br>
								<img class="img3" src="${path}/resource/img/icons/tjjs3.jpg"> --%>
							</li>
						</ul>
					</div>
					<div class="loadingbox">
						<div class="page_loading" style="display:block;">加载中…</div>
						<div class="page_nodata" style="display:none;">暂无更多数据</div>
					</div>
				</div>
			</div>
			<jsp:include page="commons/include_footer.jsp" >
				<jsp:param name="menuid" value="1" />
			</jsp:include>

		<audio id="audio_id" src="">
		</audio>		
</body>	
<script type="text/javascript">
 function openurl(url){
    if(url=='${path}/m/reward/indexPage.do'){
      layer.msg('正在开发中，敬请期待')
      return
    }
	window.location.href=url;  
} 
var curPageNum=1;
var loadType = "businessFriend";
var isEnd = false;
var hasmore = true;
var nowPlayMedia={};
function showimgs(id,img,dy_type,dy_url){	
	if(dy_type==1){
		window.location.href=dy_url;
	}else{
		if(is_weixn()){
			var imgs = [];
			$("#img_box_"+id+" img").each(function(){
				imgs.push($(this).data("imgurl"));
			});
			wx.previewImage({
			    current: img, // 当前显示图片的http链接
			    urls: imgs // 需要预览的图片http链接列表
			});
		}
	}
}
  function abouttj(){
	//获取默认配置
	    $("#about-tj").addClass('show-active').siblings().removeClass('show-active');
	   $("#about-tj").empty();
		zdy_ajax({
			url : _path+'/m/news/configdetail.do',
			type : 'post',
			dataType : 'json',
			showLoading:false,
			data : {
				type:2
			},
			success : function(data,result) {
			  $('.loadingbox').hide();
			  $('.dtTit').hide();
			var b=setTimeout(function(){
				 $("#about-tj").html(data.news.content);
			  },500);
			}
		})
  }
var showDynamicInfo = function(type,flag){
	
	$('.loadingbox').show()
	 //$('.dtTit').show()
	isEnd = false;
	loadType = type;
	if(flag == true){
		dynamicloadtime = getSysNowTime();
		curPageNum = 1;
		hasmore = true;
		$('.dtTit').hide();
	}
	var url = "${path}/m/dynamic/selectMyFriendDynamic.do";
	if(type == "follow"){
		url = "${path}/m/dynamic/selectMyFollowDynamic.do";
		if(flag == true){
			$("#myfollowdynamic").empty();
		}
		$("#myfollowdynamic").addClass('show-active').siblings().removeClass('show-active');
		$(".page_nodata").hide();
		$(".page_loading").show();
	}else{
		if(flag==true){
			$("#dynamicPage").empty();
		}
		$("#dynamicPage").addClass('show-active').siblings().removeClass('show-active');
		$(".page_nodata").hide();
		$(".page_loading").show();
	}
	zdy_ajax({
		url: url,
		async:true,
		data:{
			pageNum:curPageNum,
			pageSize:10,
			dynamicloadtime:dynamicloadtime
		},
	    showLoading:false,
		success: function(dataobj,res){
			console.log(dataobj)
			var data = dataobj.dynamicList;
			if(res.code == 0){
				if(data.length==0 || data.length<10){
					$(".page_loading").hide();
					$(".page_nodata").show();
					hasmore=false;
				}		
				$.each(data, function(i, n){
					var dynamic = data[i];
					var honor_flag = dynamic.dynamicMap.honor_flag,tjIdImg="";
					/*  console.log(honor_flag)  */
					if(honor_flag=="honor_001"){
						tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjjr.png"  class="tj" /></div>'
					}else if(honor_flag=="honor_002"){
						tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjyq.png"  class="tj" /></div>'
					}else if(honor_flag=="honor_003"){
						tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjhb.png"  class="tj" /></div>'
					}else if(honor_flag=="honor_004"){
						tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjfws.png"  class="tj" /></div>'
					}
					var str = '<div class="msgBox">'+
					   '<div class="msg-l" style="height:0.8rem;width:0.8rem;;background:url('+dynamic.dynamicMap.head_portrait+') no-repeat center;background-size:100% 100%;border-radius:50%;" id="'+dynamic.dynamicMap.user_id+'">'+
					   '</div>'+
					   '<div class="msg-r">'+
							'<div class="t" data-id="'+dynamic.dynamicMap.user_id+'" onclick=showUserDetial(this)>'+
								'<h2 >'+dynamic.dynamicMap.nickname+'</h2>'+ 
								tjIdImg+
								'<span class="time">'+dynamic.dynamicMap.minute_flag+'</span>'+
							'</div>'+
							'<p class="ph" data-id="'+dynamic.dynamicMap.user_id+'" onclick=showUserDetial(this)>'+dynamic.dynamicMap.job_name+' /'+dynamic.dynamicMap.com_name+'</p>'+
							'<p onclick=lookCommentInfo("'+dynamic.dynamicMap.id+'","'+dynamic.dynamicMap.dy_type+'","'+dynamic.dynamicMap.ad_url+'") class="p2">'+dynamic.dynamicMap.dy_content+'</p>';
							if(dynamic.dynamicMap.articleid!= undefined && dynamic.dynamicMap.articleid!= "undefined" && dynamic.dynamicMap.articleid!= null && dynamic.dynamicMap.articleid!= ""){
								str += '<div   onclick=jumparticle("'+dynamic.dynamicMap.articleid+'"); class="item"><div class="it-img"><img src="'+_oss_url+dynamic.dynamicMap.aimgpath+'"/></div><div class="it-cont">'+
								'<h3>'+dynamic.dynamicMap.atitle+'</h3><div class="bot"><span>'+formatDate(new Date(dynamic.dynamicMap.adate))+'</span><span>摘自:'+dynamic.dynamicMap.aclassname+'</span></div></div></div>';
							}
							str +='<div id="img_box_'+dynamic.dynamicMap.id+'">';
							if(dynamic.picList.length != 0 && dynamic.picList.length != 1){
								str += '<div class="imgBox_3">';
								$.each(dynamic.picList, function(k, n){
									if(k%3 == 0 && k !=0){
										str += '</div>';
										str += '<div class="imgBox_3">';
									}
									var img200 = imgReplaceStyle(dataobj.ossurl+dynamic.picList[k].picUrl,'YS200200');
									var img640 = imgReplaceStyle(dataobj.ossurl+dynamic.picList[k].picUrl,'YSMAX1024');
									str +='<div class="insideIMGbox"  onclick=showimgs("'+dynamic.dynamicMap.id+'","'+img640+'","'+dynamic.dynamicMap.dy_type+'","'+dynamic.dynamicMap.ad_url+'")><span></span><img class="lazy" data-original="'+img200+'" data-imgurl="'+img640+'" /></div>';
								});
								str += '</div>';
							}
							if(dynamic.picList.length == 1){
								var img640 = imgReplaceStyle(dataobj.ossurl+dynamic.picList[0].picUrl,'YSMAX1024');
								str += '<div class="imgBox_1" >'+
								'<img class="lazy" data-original="'+img640+'" data-imgurl="'+img640+'" onclick=showimgs("'+dynamic.dynamicMap.id+'","'+img640+'","'+dynamic.dynamicMap.dy_type+'","'+dynamic.dynamicMap.ad_url+'") />'+
							'</div>';
							}
							str+='</div>';
							if(dynamic.dynamicMap.media_id != '' && dynamic.dynamicMap.media_seconds != '' && dynamic.dynamicMap.media_seconds > 0){
								var media_length = dynamic.dynamicMap.media_seconds;
								var length_media = media_length > 10 ? media_length:10;
								str +='<div class="fb_yyt" id="fb_yyt" onclick=playMedia("'+_oss_url+dynamic.dynamicMap.media_id+'","'+dynamic.dynamicMap.id+'","'+dynamic.dynamicMap.media_price+'","'+dynamic.dynamicMap.overtime_flag+'",this)>'+
	                            '<span class="fb_yyt_tu" style="width:'+length_media+'%"><img src="${path}/resource/img/images/yy01.png"></span>'+
	                            '<span class="fb_yyt_m">'+media_length+'\"</span>'+
	                            '<div class="clearfix"></div>';
	                            
	                            if(dynamic.dynamicMap.overtime_flag == 0){
	                            	 if(dynamic.dynamicMap.media_price > 0){
	 	                            	if(dynamic.dynamicMap.pay_flag == 1){
	 	                            		 str+='<div class="t">'+
	 			                            	'<h2></h2>'+
	 											'<span class="time" id="'+dynamic.dynamicMap.id+'1">该语音已付费</span>'+
	 										'</div>';
	 	                            	}else if(dynamic.dynamicMap.pay_flag == 0){
	 	                            		str+='<div class="t">'+
	 		                            	'<h2></h2>'+
	 										'<span class="time" id="'+dynamic.dynamicMap.id+'1">该语音需要付'+dynamic.dynamicMap.media_price+'J币收听</span>'+
	 									'</div>';
	 	                            	}else if(dynamic.dynamicMap.pay_flag == -1){
	 	                            		str+='<div class="t" id="'+dynamic.dynamicMap.id+'1">'+
	 		                            	'<h2></h2>'+
	 										'<span class="time">该语音需要付'+dynamic.dynamicMap.media_price+'J币收听</span>'+
	 									'</div>';
	 	                            	}
	 	                            }else{
	 	                            	 str+='<div class="t">'+
	 		                            	'<h2></h2>'+
	 										'<span class="time">免费</span>'+
	 									'</div>';
	 	                            }
	                            }else{
	                            	
	                            	if(dynamic.dynamicMap.media_id.indexOf(".mp3") == -1){
		                            	str+='<div class="t" id="'+dynamic.dynamicMap.id+'1">'+
			                            	'<h2></h2>'+
											'<span class="time">该语音已经超过3天，不能收听！</span>'+
										'</div>';
	                            	}else{
	                            		
	                            		if(dynamic.dynamicMap.media_price > 0){
		 	                            	if(dynamic.dynamicMap.pay_flag == 1){
		 	                            		 str+='<div class="t">'+
		 			                            	'<h2></h2>'+
		 											'<span class="time" id="'+dynamic.dynamicMap.id+'1">该语音已付费</span>'+
		 										'</div>';
		 	                            	}else if(dynamic.dynamicMap.pay_flag == 0){
		 	                            		str+='<div class="t">'+
		 		                            	'<h2></h2>'+
		 										'<span class="time" id="'+dynamic.dynamicMap.id+'1">该语音需要付'+dynamic.dynamicMap.media_price+'J币收听</span>'+
		 									'</div>';
		 	                            	}else if(dynamic.dynamicMap.pay_flag == -1){
		 	                            		str+='<div class="t" id="'+dynamic.dynamicMap.id+'1">'+
		 		                            	'<h2></h2>'+
		 										'<span class="time">该语音需要付'+dynamic.dynamicMap.media_price+'J币收听</span>'+
		 									'</div>';
		 	                            	}
		 	                            }else{
		 	                            	 str+='<div class="t">'+
		 		                            	'<h2></h2>'+
		 										'<span class="time">免费</span>'+
		 									'</div>';
		 	                            }
	                            	}
	                            }
	                       		str+= '</div>';
							}
		                    var clicked='iconfonthead';
		                    if(dynamic.dynamicMap.like_flag){  
		                    	clicked='iconfont-badhead'
		                    };
		                    
							str+='<div class="tubiao">'+
								'<div class="tb">'+
									'<i class=" iconfont1" onclick=lookCommentInfo("'+dynamic.dynamicMap.id+'","","","'+dynamic.dynamicMap.allow_comment+'")></i>'+
									'<span>'+dynamic.commonCount+'</span>'+
								'</div>'+
								'<div class="tb">'+
									'<i class="iconfont '+clicked+'" goodid="'+dynamic.dynamicMap.id+'" onclick=insertLick($(this).attr("goodid"),$(this))>'+dynamic.lickCount+'</i>'+
									//'<span id="lick_'+dynamic.dynamicMap.id+'">'+dynamic.lickCount+'</span>'+
								'</div>';
								if(dynamic.dynamicMap.dy_type !=1){
									str+='<div class="tb">'+
										'<i class=" iconfont3" onclick=insertForward("'+dynamic.dynamicMap.id+'","'+dynamic.dynamicMap.media_id+'","'+dynamic.dynamicMap.articleid+'")></i>'+
										'<span id="forward_'+dynamic.dynamicMap.id+'">'+dynamic.forwardCount+'</span>'+
									'</div>';
								}
								
								str+='<div class="tb mri">'+
									'<i class="iconfont4" onclick=insertGratuity("'+dynamic.dynamicMap.id+'","'+dynamic.dynamicMap.allow_reword+'")></i>'+
								'</div>'+
							'</div>';
							
							if(dynamic.commonCount !=0){
								str+='<div class="pl">'+'<p class="c"><b>'+dynamic.commonUser.nickname+'</b>：<span>'+dynamic.pcomment.cmeDesc+'</span></p>';
								if(dynamic.subCommonCount != 0){
									str+='<p class="c"><b>'+dynamic.subCommonUser.nickname+'&nbsp;</b>回复<b>&nbsp;'+dynamic.commonUser.nickname+'</b>:<span>'+dynamic.subcomment.cmeDesc+'</span></p>';
								} 
								str+='<p class="more" onclick=lookCommentInfo("'+dynamic.dynamicMap.id+'")>查看所有评论</p>'+'</div>';
							}
							str+='</div>'+'</div>';
							

					if(type == "follow"){
						$("#myfollowdynamic").append(str);
					}else{
						$("#dynamicPage").append(str);
					}
					
					
					 $('.msg-l').bind('click',function(){
						   window.location.href="${path}/m/my/personal_home2.do?userId="+$(this).attr('id');
					 });
				});
				 curPageNum ++;

			    $("img.lazy").lazyload({
			         effect : "fadeIn"
			         ,threshold : 400
			         //,placeholder : "${path}/resource/img/lazyload.gif"
			    });
				if(type == "follow" && flag == true){
					if(data.length == 0){
						$(".page_nodata").hide();
						zdy_ajax({
							url: '${path}/im/m/selMyFollowCount.do',
							success: function(data,output){
								if(output.code == 0){
									
									//console.log(output.dataobj);
									if(output.dataobj != 0){
										$("#myfollowdynamic").append('<h3 style="font-size: .28rem;line-height: .50rem;text-align:center;">暂无动态</h3>');
									}else{
										$("#myfollowdynamic").append('<h3 style="font-size: .28rem;line-height: .50rem;text-align:center;padding:0 .30rem;box-sizing:border-box;">您当前未关注任何用户，请前往人脉圈寻找感兴趣的用户进行关注</h3>');
									}
								}
							},
							error:function(e){
							}
						});	
						
					}
				}
				isEnd=true;
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			$(".page_nodata").hide();
			$(".page_loading").hide();
		}
 	}); 
}
var dynamicloadtime="";
var tdynamicCount; //定时器
$(function(){
	$('#search').on('click',function(){
		window.location.href = _path+ "/wx/businessFriend/inSearch.do"
	})
	$('.dc .dc-t span').on('click', function() {
		var index = $(this).index();
		$(this).addClass('dc-t-active').siblings().removeClass('dc-t-active');
	});
	dynamicloadtime = getSysNowTime();
	tdynamicCount = setTimeout("getNewMyFriendDynamicCount()",1000*30);
	/*加载第一页*/
	showDynamicInfo(loadType,true);
	 var top = $(".dc-t").offset().top; //获取指定位置 
	$(window).scroll(function(){
		var scrollTop = $(window).scrollTop()
		if (scrollTop >top) { //滑动到该位置时执行代码
			$(".search-box").addClass("active1");
			$(".dc-t").addClass("d-active");
		} else {
			$(".search-box").removeClass("active1");
			$(".dc-t").removeClass("d-active");
		}
		
	    if($(window).scrollTop() >= $(document).height() - $(window).height()){
	    	if($("#about-tj").hasClass('show-active')){
	    		//关于天久不加载信息
	    	}else{
	    		if(curPageNum != 1){
	        		if(isEnd && hasmore){
	        			showDynamicInfo(loadType,false); 
	        		}
	        	}
	    	}
        	
	    }
	    //console.log($(window).scrollTop()+"  "+($(document).height() - $(window).height()));
	});
	//语音相关
	var nowPlayMedia={};
	   
	var configdata = {};

	if(is_weixn()){
		zdy_ajax({
			url: '${path}/m/sys/getSignature.do',
			async: false,
			data:{
				url:window.location.href
			},
			success: function(data,output){
				if(output.code == 0){
					configdata = output.dataobj;
				}
			},
			error:function(e){
			}
		});
		var jsApiList = [];
		jsApiList.push("startRecord");
		jsApiList.push("stopRecord");
		jsApiList.push("onVoiceRecordEnd");
		jsApiList.push("playVoice");
		jsApiList.push("pauseVoice");
		jsApiList.push("stopVoice");
		jsApiList.push("onVoicePlayEnd");
		jsApiList.push("uploadVoice");
		jsApiList.push("downloadVoice");
		jsApiList.push("previewImage");
		
		
		wx.config({
		    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		    appId: localStorage.appid, // 必填，企业号的唯一标识，此处填写企业号corpid
		    timestamp:configdata.timestamp , // 必填，生成签名的时间戳
		    nonceStr: configdata.nonceStr, // 必填，生成签名的随机串
		    signature: configdata.signature,// 必填，签名，见附录1
		    jsApiList: jsApiList // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
		});
		
		wx.ready(function(){
			

		});
		
		wx.error(function(res){
			//alert(res);
		});
	}
	
	var area = document.getElementById('notice_list');
    area.innerHTML += area.innerHTML;
     var timer;
    $("#scrollTextdiv").textSlider({line:1,speed:1000,timer:3000}); 
	
    //资讯轮播
  /* zdy_ajax({
		url: _path+"/m/news/selNewsIndex.do",
	    showLoading:false,
		success: function(data,res){
			console.log(data)
			$.each(data.list,function(i){
			var str = '<li class="zxn-c" ><a href="${path}/m/news/hotDetailPage.do?id='+data.list[i].id+'">'+data.list[i].newsTitle+'</a></li>'
				$('.zixun ul').append(str)
			})
			  var area = document.getElementById('notice_list');
              area.innerHTML += area.innerHTML;
               var timer;
            $("#scrollTextdiv").textSlider({line:1,speed:1000,timer:2000});
		},
	    error:function(e){
	    }
	});   */
	
	//if(curPageNum && curPageNum === 1){
		//index_check_recon();
	//}
	
   });
   //是否显示index.do广告
   
   var d_ggw = getSessionStorageValue("d_ggw")
	if(!d_ggw){
		guangGao();
		setSessionStorageValue('d_ggw',"yangdaxian")
	};
   function guangGao(){
	   if('${indexAd.imgName}' != '' && '${indexAd.imgName}'.length > 0){
			//页面层
			layer.open({
			  type: 1,
			  title:false,
			  closeBtn: 0,
			  shadeClose: true,
			  area: ['6.4rem', '3.2rem'], //宽高
			  content: '<img src="'+_oss_url+'/${indexAd.imgUrl}" onclick="indexAdClick(\'${indexAd.imgLink}\');" title="${indexAd.imgName}" width="100%" height="100%" style="cursor:pointer;"/>'
			});
		}
   }

var indexAdClick=function(url){
	window.open(url);
}

//查看评论内容
function lookCommentInfo(id,dy_type,ad_url,allow_comment){
	if(dy_type==1){
		window.location.href=ad_url;
	}else{
		window.location.href="${path}/m/dynamic/commentPage.do?id="+id;
		/*if(allow_comment == "1" || allow_comment == null || "" == allow_comment){
			window.location.href="${path}/m/dynamic/commentPage.do?id="+id;
		}else{
			alert("此动态不允许评论！");
		}*/
		
	}
	
}
//查看广告
function lookad(ad_url){
	window.location.href=ad_url;
}
//点赞
function insertLick(_id,th){
		var oldLickCount=th.html();
		if(th.hasClass("iconfonthead")){
			th.removeClass("iconfonthead").addClass("iconfont-badhead");
		}else{
			th.removeClass("iconfont-badhead").addClass("iconfonthead");
		}
		zdy_ajax({
			url: "${path}/m/dynamic/thumbup.do",
			data:{
				id:_id,
			},
			success: function(data,res){	
				if(res.code == 0){
					if(res.msg==0){
						alert("点赞成功！");
						th.html(oldLickCount*1+1);
					}else{
						alert("点赞已取消！");
						th.html(oldLickCount*1-1);
					}
				}
			},
		    error:function(e){
			   //alert(e);
		    }
		}); 
}
function insertGratuity(_id,allowReword){
	/*if(allowReword == "0"){
		alert("此动态不允许打赏!");
		return;
	}*/
	zdy_ajax({
		url: "${path}/m/dynamic/isReward.do",
		data:{
			dynamicId:_id,
		},
		success: function(data,res){
			if(res.code == 0){
				if(res.msg==0){
					window.self.location='${path}/m/dynamic/rewardPage.do?dynamicId='+_id+'&fromUrl='+window.self.location;
				}else{
					alert("你已打赏过此动态");
				}
			}
		},
	    error:function(e){
		   //alert(e);
	    }
	}); 
}

//转发
var insertForward=function(_id,mediaId,articleid){
	if (window.localStorage) {
		  localStorage.setItem("libraryzfurl","${path}/m/sys/index.do");
	}
	if(mediaId == undefined || mediaId == "undefined" || mediaId == null || mediaId == ""){
		
		var oldForwardCount=$("#forward_"+_id).html();
		zdy_ajax({
			url: "${path}/m/dynamic/isForward.do",
			data:{
				dynamicId:_id,
			},
			success: function(data,res){
				if(res.code == 0){
					if(res.msg==0){
						//$("#forward_"+_id).html(oldForwardCount*1+1);
						if(articleid == undefined || articleid == "undefined" || articleid == null || articleid == ""){
							window.self.location.href = "${path}/m/dynamic/forwardDynamic.do?dynamicId="+_id+"&fromurl="+window.self.location;
						}else{
							window.location.href= _path+"/library/m/dynamiczf.do?type=2&id="+articleid+"&fromDynamicId="+_id; 
						}
					}else{
						alert("你已转发过此动态");
					}
				}
			},
		    error:function(e){
			   //alert(e);
		    }
		}); 
	}else{
		
			alert("语音动态不能转发");
	}
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

var global_audio = null;
var global_k = null;

function playMedia(mediaId,dynamicId,price,overtime_flag,k){
	if(is_weixn()){
		var url = "${path}/m/dynamic/isPayofMedia.do";
		zdy_ajax({
			url: url,
			data:{
				dynamicId:dynamicId
			},
		    showLoading:false,
			success: function(obj,res){
				if(res.code == 0){
					if(res.msg == 1){
						if(mediaId.indexOf(".mp3") != -1){
							var audio=$("#audio_id")[0];
							var global_imgsrc = $(global_k).find('img').attr('src');
							var global_mediaId = null;
							if(global_audio != null){
								global_mediaId = global_audio.src;
							}
							audio.src=mediaId;
							if(global_audio == null){
								audio.pause();
								audio.play();
								audio.onplay=function(){
									$(k).find('img').attr('src','${path}/resource/img/images/yy03.gif');
								}
								audio.onended=function(){
									$(k).find('img').attr('src','${path}/resource/img/images/yy01.png');
								}
								//暂停时使用
								audio.onpause=function(){
									$(k).find('img').attr('src','${path}/resource/img/images/yy01.png');
								}
							}else{
								
								//同一个已经播放完毕完毕
								if(global_imgsrc == '${path}/resource/img/images/yy01.png'){
									audio.pause();
									audio.play();
									audio.onplay=function(){
										$(k).find('img').attr('src','${path}/resource/img/images/yy03.gif');
									}
									audio.onended=function(){
										$(k).find('img').attr('src','${path}/resource/img/images/yy01.png');
									}
									//暂停时使用
									audio.onpause=function(){
										$(k).find('img').attr('src','${path}/resource/img/images/yy01.png');
									}
								}else if(global_imgsrc == '${path}/resource/img/images/yy03.gif'){
									global_audio.pause();
									$(global_k).find('img').attr('src','${path}/resource/img/images/yy01.png');
									if(global_mediaId != mediaId){
										audio.pause();
										audio.play();
										audio.onplay=function(){
											$(k).find('img').attr('src','${path}/resource/img/images/yy03.gif');
										}
										audio.onended=function(){
											$(k).find('img').attr('src','${path}/resource/img/images/yy01.png');
										}
										//暂停时使用
										audio.onpause=function(){
											$(k).find('img').attr('src','${path}/resource/img/images/yy01.png');
										}
									}
								}
							}
							
							global_audio = audio;
							global_k = k;
							
						}else{
							if(overtime_flag == 1){
								alert_back("该语音已超过3天，不能播放！",function(){
								});
								return;
							}
							if(!isEmpty(nowPlayMedia)){
								if(!isEmpty(nowPlayMedia.localId)){
									wx.stopVoice({
									    localId: nowPlayMedia.localId 
									});
								}
							}
							nowPlayMedia.mediaId = mediaId;
							nowPlayMedia.dynamicId = dynamicId;
							downloadVoice(mediaId,k);
						}
					}else{
						confirm("确定要支付"+price+"个J币吗？",function(t){
							if(t == 1){
								//window.self.location="${path}/m/dynamic/paymediaPage.do?dynamicId="+dynamicId;	
								
								 zdy_ajax({
										url: '${path}/m/dynamic/paymedia.do',
										data:{
											dynamicId:dynamicId,
											jcount:price
										},
										success: function(data,output){
											if(output.code == 0){
												alert_back(output.msg,function(){
													$("#"+dynamicId+"1").html("该语音已付费");
												});
												
											}else{
												alert_back(output.msg,function(){
												});
											}
										},
										error:function(e){
										}
									});
								
								
								
							}
						});
					}
				}
			},
		    error:function(e){
			   //alert(e);
		    }
	 	}); 
	}else{
		layer.msg("请在微信中打开此系统！");
	}
}

//下载播放录音
function downloadVoice(serverId,k){
	wx.downloadVoice({
	 	serverId: serverId, // 需要下载的音频的服务器端ID，由uploadVoice接口获得
	    isShowProgressTips: 1,// 默认为1，显示进度提示
	    success: function (res) {
	    	var download_localId = res.localId; // 返回音频的本地ID
	    	nowPlayMedia.localId = res.localId;
	    	//开始播放语音
	    	$(k).find('img').attr('src','${path}/resource/img/images/yy03.gif');
	    	wx.playVoice({
			    localId: download_localId // 需要播放的音频的本地ID，由stopRecsord接口获得
			});
	    	wx.onVoicePlayEnd({
			    success: function (res) {
			        //结束播放语音
			        $(k).find('img').attr('src','${path}/resource/img/images/yy01.png');
                    //在此加入结束动态效果
			        nowPlayMedia = {};
			    }
			});
	    }
	});
};

function showUserDetial(obj){
	window.location.href="${path}/m/my/personal_home2.do?userId="+$(obj).data("id");
}

/**
 * 获取系统时间戳
 */
function getSysNowTime(){
	var sysnowtime = "";
	zdy_ajax({
		url: "${path}/m/dynamic/sysdatetime.do",
		async:false,
		data:{},
		success: function(data,res){
			if(res.code == 0){
				sysnowtime = res.dataobj;
			}
		},
	    error:function(e){
		   //alert(e);
	    }
	});
	return sysnowtime;
}

var isShowTip = false;
function getNewMyFriendDynamicCount(){
	zdy_ajax({
		url: "${path}/m/dynamic/selectNewMyFriendDynamicCount.do",
		data:{dynamicloadtime:dynamicloadtime},
		success: function(data,res){
			if(res.code == 0){
				var rowCount = res.dataobj;
				if(rowCount > 0){
					$(".dtTit").show();
					$("#newDynamicCount").text(rowCount);
					
					if(!isShowTip){
						layer.msg('有'+rowCount+'条新动态');
						isShowTip = true;
					}
					
				}
				tdynamicCount = setTimeout("getNewMyFriendDynamicCount()",1000*30);
			}
		},
	    error:function(e){
		   //alert(e);
	    }
	});
}

function loadnewdynamic(){
	$(".dtTit").hide();
	showDynamicInfo('businessFriend',true);
}

var index_check_recon=function(){
	zdy_ajax({
		url: "${path}/m/sys/getTjyUser.do",
		showLoading:false,
		async:true,
		success: function(dataobj,res){
			var reconStatus=dataobj.reconStatus;
			var kfTelephone=dataobj.kfTelephone;
			var trueName = dataobj.trueName;
			if(reconStatus == 2){
				$("#kfTelephone").text(kfTelephone);
				$("#is_recon_true").find("button[name='dhrz_button']").attr("value",kfTelephone);
				$("#is_recon_true").show();
				$("#is_recon_false").hide();
				zdy_ajax({
					url: "${path}/m/sys/getWxUser.do",
					showLoading:false,
					async:true,
					success: function(data,res){
						if(data.logincount < 1){
							if(null == hasShow){
								//TODO sessionStorage
								sessionStorage.setItem("hasShow", "Y");
								alert("<span style=\"display:inline-block;  text-indent:2;\">&nbsp;&nbsp;&nbsp;&nbsp;尊敬的"+trueName+"，恭喜您已成为天九共享会尊贵<br>的会员！点击此链接查看：天九共享会功能<br>介绍及用户公约(<a style=\"color:blue;\" href=\"http://t.cn/R9tLmMS\">http://t.cn/R9tLmMS</a>)。</span>");
							}
						}
					}
				});
			}
		}
	});
}

function jumparticle(articleid){
	window.location.href= _path+"/library/m/librarydetail.do?type=1&id="+articleid; 
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
</script>

</html>
