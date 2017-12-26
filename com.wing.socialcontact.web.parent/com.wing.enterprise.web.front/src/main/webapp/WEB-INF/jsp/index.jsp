<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="user-scalable=no,initial-scale=1,maximum-scale=1, minimum-scale=1, width=device-width, height=device-height"/>
	<%@ include file="/WEB-INF/jsp/commons/include.com.jsp" %>
	<%@ include file="/WEB-INF/jsp/commons/include_login.jsp" %>
    <title>首页</title>
    <style>
         #theCsover{
             position:fixed;
             left:0;
             top:0;
             background:black;
             opacity:0.4;
         }
         .lsy_sskbox{
			background-color:#f2f2f2;
			position:absolute;
			top:.83rem;
			left:0;
			width:100%;
			box-sizing:border-box;
         }
         .pullUpLabel{
         	padding-bottom: 20px;
         }
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
				top: 1.65rem;
				bottom: 0rem;
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
<body style='background:f0f0f0'>
            <header id='header'>
				<h1>首页</h1>
			</header>
			<div class="lsy_sskbox " id="lsy_sskbox">
		        <input id="field1" type="text" onfocus="onf()" onblur="off()"  placeholder="  搜索需要的服务" 
		        onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" onpaste="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" 
		        oncontextmenu = "value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')"/>
<!-- 		        <input id="field1" type="text" readonly="readonly" onfocus="onf()" onblur="off()" placeholder="搜索需要的服务" /> -->
		        <a class="search" onclick="canelSearch()">取消</a>
		        <div class="search" onclick="searchIndex()">搜索</div>
		       </div>

		<div id="wrapper">
			<div id="scroller">
				<div id="pullDown">
					<span class="pullDownLabel"></span>
				</div>
				<ul id="thelist">
					<!-- banner -->
					<div class="lsy_banner lineheight0" id="index_banner">
						<jsp:include page="commons/include_banner.jsp" >
							<jsp:param name="bannerid" value="74d8f50751d84170a7e4e5f2a5b86a62" />
						</jsp:include>
					</div> 
					<!-- 快捷入口 -->
					<ul class="lsy_nrnav_list" id="lsy_nrnav_list"></ul>
					<!-- 企服介绍 -->
					<div id="qfyIndexDesc"></div>
					<!-- 热门服务 -->
					<div class="lsy_hotfw" id="lsy_hotfw">
						<h2>热门服务</h2>
						<ul class="clear" id="hotService">
						</ul>
					</div>
					<!-- 精选服务 -->
					<div class="lsy_jxqf" id="goodService" >
						<h2>精选企服</h2>
					</div>
	    

                <div id="theCsover" style="z-index:500;display:none" onclick="clo()"></div>
				</ul>
				<div id="pullUp">
				  <span class="pullUpLabel"></span>
				</div>

			</div>
		</div>
	 
	 
</body>
</html>
<script type="text/javascript">

var page = 0;
var size = 10;
var totalPage;
var oldpageSize = '';

var searchName = '';

$(function() {
	initData();
	page = 1;
	if (location.href.indexOf("&page") != -1) {
		page = 1;
		oldpageSize=Size;
		size= Number(location.href.split('&page=')[1])*size;
		toNextPage();
		page=Number(location.href.split('&page=')[1]);
		size=oldpageSize;
		$("body").scrollTop(Number(location.href.split('&page=')[0].split('scroll=')[1]));
		
	} else {
		toNextPage();
	}
})
	
		document.addEventListener('touchmove', function(e) {
				e.preventDefault();
			}, false);
			document.addEventListener('DOMContentLoaded', loaded, false);	
	function pullUpAction() {
		//console.log("--------")
		setTimeout(function() { 
			if (Number(totalPage)>page) {
				$(".pullUpLabel").text("正在加载中");
				if ($(".pullUpLabel").css("display") != "none"
					&& window.innerHeight + document.body.scrollTop + 60 >= document.body.scrollHeight) {
				
					if (page>= Number(totalPage)) {
						//已无更多可加载 ，去除加载更多的按钮
						return;
					}
					page++;
					toNextPage();
					//$(".pullUpLabel").text("暂无更多数据");
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
  function toNextPage(){ 
	zdy_ajax({
		url: "${path}/m/qfy/index.do",
		
		data:{
			page:page,
			size:size,
			searchName:searchName
		},
		
		success: function(data,res){
			if(res.code == 0){
				//精选服务
				var goodService = '';
				if(data.entrys){
					$.each(data.entrys , function(i, n){
						goodService += '<dl class="clear" onclick="openEntryDetail(\''+n.id+'\')">';
						if(n.logoImgPath){
							goodService += '<dt><img src="'+_oss_url+n.logoImgPath+'" /></dt>';
						}else{
							goodService += '<dt><img src="${path }/resource/images/logo_default.png" /></dt>';
						}
						goodService += '<dd>';
						goodService += '<p class="clear"><b>'+n.name+'</b><span>已服务<i>'+n.serviceCount+'</i>家</span></p>';
						goodService += '<p class="clear">';
						$.each(n.entryTags , function(j, m){
							goodService += '<a>'+m.tagName+'</a>';
						});
						goodService += '</p></dd></dl>';
					});
					$("#goodService").append(goodService);
					//总条数 
					totalPage = Math.ceil(data.totalSize / size);
					
				}else{
					$(".pullUpLabel").text("暂无更多数据");
				}
				if(goodService == ''){
					$(".pullUpLabel").text("暂无更多数据");
				}
				if(searchFlag){
					$("#index_banner").css('display','none'); 
					$("#lsy_nrnav_list").css('display','none'); 
					$("#qfyIndexDesc").css('display','none'); 
					$("#lsy_hotfw").css('display','none');
				}else{
					if( $("#index_banner").css("display")=='none' ){
						$("#index_banner").css('display','block'); 
					}
					if( $("#lsy_nrnav_list").css("display")=='none' ){
						$("#lsy_nrnav_list").css('display','-webkit-box'); 
					}
					if( $("#qfyIndexDesc").css("display")=='none' ){
						$("#qfyIndexDesc").css('display','block'); 
					}
					if( $("#lsy_hotfw").css("display")=='none' ){
						$("#lsy_hotfw").css('display','block'); 
					}
				}
				myScroll.refresh();
			}
		},
	    error:function(e){
	    }
	}); 
};
/* window.onscroll = function() {
	var top=$("#lsy_sskbox").offset().top;
	if(top>47){
		$("#lsy_sskbox").addClass("lsy_sskbox2")
	}else{
		$("#lsy_sskbox").removeClass("lsy_sskbox2")
	} 
	
	
	if (Number(totalPage)>page) {
		$(".pullUpLabel").text("正在加载中");
		if ($(".pullUpLabel").css("display") != "none"
			&& window.innerHeight + document.body.scrollTop + 60 >= document.body.scrollHeight) {
		
			if (page>= Number(totalPage)) {
				//已无更多可加载 ，去除加载更多的按钮
				return;
			}
			page++;
			toNextPage();
			//$(".pullUpLabel").text("暂无更多数据");
		}
	}else{
		$(".pullUpLabel").text("暂无更多数据");
	} 
} 
 */
function openQuickDetail(id,quickName){
	window.location.href="${path}/m/qfy/quickIndexPage.do?id="+id;
}
function openEntryDetail(id){
	window.location.href="${path}/m/qfy/entryDetailPage.do?entryId="+id+"&page=indexPage.do"
}
function openNewUrl(url){
	window.location.href=url; 
}

function initData(){ 
	zdy_ajax({
		url: "${path}/m/qfy/index.do",
		data:{
			page:page,
			size:size,
			searchName:searchName
		},
		success: function(data,res){
			if(res.code == 0){
				//快捷入口
				var qdsString = '';
				$.each(data.qds, function(i, n){
					qdsString+='<li onclick="openQuickDetail(\''+n.id+'\',\''+n.quickName+'\')"><a style="background:url('+_oss_url+n.quickImgPath+') no-repeat center .35rem;background-size: 1.1rem;">'+n.quickName+'</a></li>';
				});
				$("#lsy_nrnav_list").html(qdsString);
				
				//企服介绍
				var qfyIndexDescString = '<img style="height:1.95rem;width:100%;border:0;" onclick="openNewUrl(\''+data.indexDescConfig.link+'\')" src="'+_oss_url+data.indexDescConfig.imgPath+'" />';
				$("#qfyIndexDesc").html(qfyIndexDescString);
				
				//热门服务
				var hotString = '';
				$.each(data.hots, function(i, n){
					hotString+= '<li onclick="openEntryDetail(\''+n.hotEntryId+'\')"><a style="background:url('+_oss_url+n.img_path+') no-repeat center .25rem;background-size: 0.58rem;">'+n.name+'</a></li>';
				});
				$("#hotService").html(hotString);
				//总条数 
				totalPage = Math.ceil(data.totalSize / size);
			}
		},
	    error:function(e){
	    }
	}); 
};

var searchFlag = false;
searchIndex = function(){
  searchName=$("#field1").val();
  $("#goodService").html('');
  if(searchName){
	  searchFlag = true; 
  }else{
	  searchFlag = false; 
  }
  page=1;
  toNextPage();
}
function canelSearch(){
	  $("#field1").val("");
	  searchName='';
	  $("#goodService").html('');
	  if(searchName){
		  searchFlag = true; 
	  }else{
		  searchFlag = false; 
	  }
	  page=1;
	  toNextPage();
	$("#field1").css({"background":"#fff url('${path }/resource/images/lsy_ss_icon.jpg') no-repeat .07rem center","background-size":".28rem auto"});
}
function onf(){
	/* $("#theCsover").css({"height":$(document).height(),"width":$(window).width(),"display":"block"}); */
	/* $("#lsy_sskbox").css({"width":"94%"}); */
	//$("#field1").removeAttr("readonly");
	$("#field1").css({"background":"#fff url('') no-repeat .07rem center"});
}

function off(){
	$("#theCsover").css("display","none");
	$("#lsy_sskbox").css({"z-index":600,"position":""});
	//$("#field1").attr("readonly","readonly");
	if(!$("#field1").val()){
		$("#field1").css({"background":"#fff url('${path }/resource/images/lsy_ss_icon.jpg') no-repeat .07rem center","background-size":".28rem auto"});
	}else{
		$("#field1").css({"background":"#fff url('') no-repeat .07rem center"});
	}
}
function clo(){
	$("#field1").css({"background":"#fff url('${path }/resource/images/lsy_ss_icon.jpg') no-repeat .07rem center","background-size":".28rem auto"});
	$("#field1").val("");
	$("#theCsover").css("display","none");
	$("#lsy_sskbox").css({"z-index":600,"position":""});
}
</script>
