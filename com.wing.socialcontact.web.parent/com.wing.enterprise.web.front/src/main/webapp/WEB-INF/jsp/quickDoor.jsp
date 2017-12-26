<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.com.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<title></title>
		<style type="text/css" media="all">
			.swiper-container1{
				width:100%;
				height:.86rem;
				overflow:hidden;
	        }
			.swiper-container1 .swiper-slide{
				line-height:.86rem;
				text-align:center;
				box-sizing: border-box;
			}
			     .tab_ul_hv {border-bottom:2px solid #479eef; color:#333;}
			      strong,b{font-weight:normal;}
       
			#header {
				position: absolute;
				top: 0;
				left: 0;
				width: 100%;
			}
			
			#wrapper {
				position: absolute;
				z-index: 1;
				top: .83rem;
				bottom: 0;
				left: 0;
				width: 100%;
				overflow: auto;
			}
			
			#scroller {
				position: relative;
				/* -webkit-touch-callout:none;*/
				-webkit-tap-highlight-color: rgba(0, 0, 0, 0);
				width: 100%;
			}
			#scroller ul {
				position: relative;
				width: 100%;
			}
			
			#pullDown,#pullUp {
				background: #fff;
				height: 40px;
				line-height: 40px;
				font-size: 14px;
				color: #888;
				text-align:center
			}
		</style>
	</head> 
	<body>
				<header id='header'>
				<div class="table1">
					<span class="header_return"><img src="${path }/resource/images/return.png" onclick="backForAndroid()"></span>
					<span class="header_search"><img src="${path }/resource/images/search.png"/></span>
					<h1>${quick.quickName }</h1>
				</div>  
				<div class="table2" style="display:none;">
			      <div class="lsy_sskbox " id="lsy_sskbox">
					        <input id="field1" type="text" onfocus="onf()" onblur="off()"  placeholder="  搜索相关服务" 
					        onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" onpaste="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" 
			        oncontextmenu = "value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')"/>
					         <span class="search" style="margin-top:0.1rem;" onclick="canSearch()"><div class="st" style="color:#f2f2f2;">取消</div></span>
					         <span class="search" style="margin-top:0.1rem;" onclick="search()"><div class="st" style="color:#f2f2f2;">搜索</div></span>
					</div>
			   </div>
			</header>

		<div id="wrapper">
			<div id="scroller">
				<div id="pullDown">
					<span class="pullDownLabel"></span>
				</div>
				<ul id="thelist">
					<!-- banner -->
					<div class="lineheight0">
						<jsp:include page="commons/include_quick_banner.jsp">
							<jsp:param name="quickDoorId" value="${quick.id}" />
						</jsp:include>
					</div>
					<div class="box_box">
						<!-- 分类 -->
					  	<div class="swiper-container1">	
							<div class="swiper-wrapper" id="detailClass">
							</div>
						 </div>	
						<!--  企服列表 -->
						<div class="zxlm_list_box" id="entrys">
						</div>
				    </div>
			         <!--  <div id="pullUp" style="text-align: center;"><span class="pullUpLabel"></span></div> -->
				</ul>
				<div id="pullUp">
				  <span class="pullUpLabel"></span>
				</div>

			</div>
		</div>

		

		<script type="text/javascript">
		var myScroll,
		pullDownEl, pullDownOffset,
		pullUpEl, pullUpOffset
		var page = 0;
		var size = 5;
		var totalPage;
		var oldpageSize = '';
		var searchName = '';
		var detailClassId = ''
			
    	   initLoadQuickEntrys();
			page = 1;
			if (location.href.indexOf("&page") != -1) {
				page = 1;
				oldpageSize=Size;
				size= Number(location.href.split('&page=')[1])*size;
				 loadQuickEntrys(detailClassId);
				page=Number(location.href.split('&page=')[1]);
				size=oldpageSize;
				$("body").scrollTop(Number(location.href.split('&page=')[0].split('scroll=')[1]));
				
			} else {
				   loadQuickEntrys(detailClassId);
				   
			}
			document.addEventListener('touchmove', function(e) {
				e.preventDefault();
			}, false);
			document.addEventListener('DOMContentLoaded', loaded, false);	
	function pullUpAction() {
		//console.log("--------")
		setTimeout(function() { 
			if (Number(totalPage)>page) {
				if ($(".pullUpLabel").css("display") != "none"&& window.innerHeight + document.body.scrollTop + 60 >= document.body.scrollHeight) {
					if (page>= Number(totalPage)) {
						//已无更多可加载 ，去除加载更多的按钮
						return;
					}
					page++;
					loadQuickEntrys(detailClassId);	
				}
			}else{
				$(".pullUpLabel").text("暂无更多数据");
			} 
			myScroll.refresh(); 
		}, 1000); 
	}
	function loaded() {
		pullDownEl = document.getElementById('pullDown');
		pullDownOffset = pullDownEl.offsetHeight;
		pullUpEl = document.getElementById('pullUp');
		pullUpOffset = pullUpEl.offsetHeight;
		myScroll = new iScroll('wrapper', {
			/* scrollbarClass: 'myScrollbar', */
			/* 重要样式 */
			useTransition: false,
			/* 此属性不知用意，本人从true改为false */
			topOffset: pullDownOffset,
			onRefresh: function() {
				// console.log(22)
			},
			onScrollMove: function() {
				//console.log(this.y)
				//console.log(this.maxScrollY)
				if(this.y<this.maxScrollY - 50){
					 pullUpEl.className = 'flip'; 
				 }
			},
			onScrollEnd: function() {
			 if (pullUpEl.className.match('flip')) {
					pullUpEl.className = '';
					pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中...';
					pullUpAction(); // Execute custom function (ajax call?)
				}
				
			}
		});

		setTimeout(function() {
			document.getElementById('wrapper').style.left = '0';
		}, 800);
	}
	//初始化绑定iScroll控件 
	
		function initLoadQuickEntrys(){
			zdy_ajax({
				url: "${path}/m/qfy/selQucik.do",
				showLoading:false,
				data:{
					page:page,
					size:size,
					quickDoorId:"${quick.id}"
				},
				success: function(data,res){
					if(res.code == 0){
						//快捷入口
						if(data.classes){
							if(data.classes.length<=1){
								$("#detailClass").css('display','none'); 
								$(".swiper-container1").css('display','none'); 
							}else{
								var classString = '<div class="swiper-slide swiper-slide-active tab_ul_hv" onclick="selQuickEntrys(\'\',this)">全部</div>';
								$.each(data.classes, function(i, n){
									classString+='<div class="swiper-slide" onclick="selQuickEntrys(\''+n.id+'\',this)">'+n.className+'</div>';
								});
								$("#detailClass").html(classString);
								
								 var mySwiper1 = new Swiper('.swiper-container1', {
								   /*  spaceBetween: 5, */
						             slidesPerView: 3,
						            /* slidesOffsetBefore : 0, */
						          })
							}
						}else{
							$("#detailClass").css('display','none'); 
						}
						//总条数 
						totalPage = Math.ceil(data.totolSize / size);
					}
				},
			    error:function(e){
			    }
			}); 
		}
		function selQuickEntrys(classId,t){
			detailClassId = classId;
			if(!$(t).hasClass("tab_ul_hv")){
			    $(".tab_ul_hv").removeClass("tab_ul_hv");
			    $(t).addClass("tab_ul_hv");
			 }
			$("#entrys").empty();
			page = 1;
			loadQuickEntrys(classId);
		}
		function loadQuickEntrys(detailClassId){
			zdy_ajax({
				url: "${path}/m/qfy/selQucik.do",
				showLoading:false,
				data:{
					page:page,
					size:size,
					//quickDoorId:"${quickDoorId}",
					quickDoorId:"${quick.id}",
					detailClassId:detailClassId,
					searchName:searchName
				},
				success: function(data,res){
					//console.log(11)
					if(res.code == 0){
						//企服列表
						var entrysString = '';
						if(data.entrys){
							$.each(data.entrys , function(i, n){
								entrysString += '<dl onclick="openEntryDetail(\''+n.id+'\')">';
								entrysString += '<dt><img src="'+_oss_url+n.bannerPath+'" /></dt>';
								entrysString += '<dd>';
								entrysString += '<span>已服务<ii>'+n.serviceCount+'</ii>家</span>';
								entrysString += '<h3>'+n.name+'</h3>';
								entrysString += '</dd></dl>';
							});
							
							if(entrysString){
								$("#entrys").append(entrysString);
								$("#entrys").css('display','block'); 
							}else{
								$("#entrys").css('display','none'); 
							}
							//总条数 
							totalPage = Math.ceil(data.totolSize / size);
						}else{
							$(".pullUpLabel").text("暂无更多数据");
						}
						if(entrysString == ''){
							$(".pullUpLabel").text("暂无更多数据");
						}
						myScroll.refresh();
					}
				},
			    error:function(e){
			    }
			}); 
		}
		/* window.onscroll = function() {
			if (Number(totalPage)>page) {
				$(".pullUpLabel").text("正在加载中");
				if ($(".pullUpLabel").css("display") != "none"
					&& window.innerHeight + document.body.scrollTop + 60 >= document.body.scrollHeight) {
				
					if (page>= Number(totalPage)) {
						//已无更多可加载 ，去除加载更多的按钮
						return;
					}
					page++;
					loadQuickEntrys(detailClassId);
				}
			}else{
				$(".pullUpLabel").text("暂无更多数据");
			} 
		} */

		function openEntryDetail(id){
			window.location.href="${path}/m/qfy/entryDetailPage.do?entryId="+id+"&page=quickIndexPage.do?id="+'${quick.id}'; 
		}
		function openNewUrl(url){
			window.location.href=url;
		}

		function backForAndroid(){
			window.location.href="indexPage.do";
		}
		function search(){
			searchName=$("#field1").val();
			$("#entrys").empty();
			page = 1;
			loadQuickEntrys(detailClassId);
		}
		function canSearch(){
			$("#field1").val("");
			$(".table1").show();
			$(".table2").hide();
			searchName='';
			$("#entrys").empty();
			page = 1;
			loadQuickEntrys(detailClassId);
		}
		$(".table1 .header_search").click(function(){
			$(".table1").hide();
			$(".table2").show();
			$("#field1").css({"background":"#fff url('${path }/resource/images/lsy_ss_icon.jpg') no-repeat .07rem center","background-size":".28rem auto"});
		})

		function onf(){
			$("#field1").css({"background":"#fff url('') no-repeat .07rem center"});
		}

		function off(){
			if(!$("#field1").val()){
				$("#field1").css({"background":"#fff url('${path }/resource/images/lsy_ss_icon.jpg') no-repeat .07rem center","background-size":".28rem auto"});
			}else{
				$("#field1").css({"background":"#fff url('') no-repeat .07rem center"});
			}
		}
		</script>
	</body>
</html>
