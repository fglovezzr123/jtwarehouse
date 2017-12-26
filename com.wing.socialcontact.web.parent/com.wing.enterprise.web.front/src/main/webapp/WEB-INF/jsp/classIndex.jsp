<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="user-scalable=no,initial-scale=1,maximum-scale=1, minimum-scale=1, width=device-width, height=device-height"/>
	<%@ include file="/WEB-INF/jsp/commons/include.com.jsp" %>
	<%@ include file="/WEB-INF/jsp/commons/include_login.jsp" %>
    <title>分类</title>
    <style>
         strong,b{font-weight:normal;}
         header{
	         position: fixed;
		    top: 0;
		    left: 0;
		    z-index: 600;
         }
         section{
        	 margin-top:.83rem;
         }
         #wrapper {
				/* position: absolute; */
				z-index: 1;
				top: 0.83rem;
				bottom: 0rem;
				left: 0;
				width: 100%;
				overflow: auto;
				height:100%;
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
			
			#pullUp {
				background: #f0f0f0;
				height: 80px;
				line-height: 80px;
				font-size: 14px;
				color: #888;
				text-align:center
			}
			#pullDown,{
			    background: #fff;
				/* //height: 40px;
				line-height: 40px; */
				font-size: 14px;
				color: #888;
				text-align:center
			}
    </style>
</head>
<body>
	<div class="wrap">
		<header>
			<div class="table1">
				<!--<span class="header_return"><img src="${path }/resource/images/return.png" onclick="openNewUrl('indexPage.do')"/></span> -->
		        <span class="header_search"><img src="${path }/resource/images/search.png"/></span>
		        <h1>所有分类</h1>
		    </div>  
		    <div class="table2" style="display:none;">
		      <div class="lsy_sskbox " id="lsy_sskbox">
				  <input id="field1" type="text" onfocus="onf()" onblur="off()" placeholder="  搜索相关分类" 
				  onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" onpaste="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" 
		        oncontextmenu = "value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')"/>
				  <!--<span class="header_search" style="margin-top:0.1rem;"><div class="st" style="color:#f2f2f2;">搜索</div></span> -->
				  <span class="search" style="margin-top:0.1rem;" onclick="canSearch()"><div class="st" style="color:#f2f2f2;">取消</div></span>
		          <span class="search" style="margin-top:0.1rem;" onclick="search()"><div class="st" style="color:#f2f2f2;">搜索</div></span>
			  </div>
		     </div>
	   	</header>
	   	<div id="wrapper">
	   	      
			<div id="scroller">
			    
				<section>
				<div id="pullDown">
					<span class="pullDownLabel"></span>
				</div>
			    	<div class="fenlei_box"></div>
			    </section>
			  <div id="pullUp" style="text-align: center;">
				<span class="pullUpLabel"></span>
			  </div>
			</div>
		</div>	  
	</div>
</body>
<script type="text/javascript">
var searchName = '';
var myScroll='';
function loadDetail(){
	zdy_ajax({
		url: "${path}/m/qfy/classIndex.do",
		showLoading:false,
		data:{
			//m:m
			searchName:searchName
		},
		success: function(data,res){
			if(res.code == 0){
				
				var classString = ''; 
				
				if(data.classes){
					$.each(data.classes , function(i, n){
						if(n.sencond.length > 0){
				            classString += '<h2 id='+n.id+'>'+n.className;
				            classString += '</h2>';
				            classString += '<ul>';
				            $.each(n.sencond , function(j, m){
					            classString += '<li><a href="${path}/m/qfy/classTablePage.do?classId='+m.id+'">'+m.className+'</a></li>';
				            });
				            classString += '<div class="clear"></div>';
				            classString += '</ul>';
						}
					});
					$(".fenlei_box").html(classString);
					$(".pullUpLabel").text("");
					myScroll.refresh(); 
				}else{
					$(".pullUpLabel").text("暂无更多数据");
				}
				if(classString == ''){
					$(".pullUpLabel").text("暂无更多数据");
				}
			}
		},
	    error:function(e){
		   //alert(e);
	    }
	}); 
}
$(document).ready(function() {
	loadDetail();
	//myScroll.refresh(); 
});

function openNewUrl(url){
	window.location.href=url;
}

function search(){
	searchName=$("#field1").val();
	loadDetail();
}
function canSearch(){
	$("#field1").val("");
	$(".table1").show();
	$(".table2").hide();
	searchName='';
	loadDetail();
}

$(".table1 .header_search").click(function(){
	$(".table1").hide();
	$(".table2").show();
	$("#field1").css({"background":"#fff url('${path }/resource/images/lsy_ss_icon.jpg') no-repeat .07rem center","background-size":".28rem auto"});
	/* $("section").click(function(){
		$(".table1").show();
		$(".table2").hide();
	}) */
})
/* $(".table2 .header_search").click(function(){
	searchName=$("#field1").val();
	loadDetail();
}) */
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


		document.addEventListener('touchmove', function(e) {
			e.preventDefault();
		}, false);
		document.addEventListener('DOMContentLoaded', loaded, false);	
		function pullUpAction() {
			//console.log("--------")
			setTimeout(function() {  
			myScroll.refresh(); 
			}, 1000); 
		}
		function loaded() {
			pullDownEl = document.getElementById('pullDown');
			pullDownOffset = pullDownEl.offsetTop ;
			pullUpEl = document.getElementById('pullUp');
			pullUpOffset = pullUpEl.offsetTop ;
		
		myScroll = new iScroll('wrapper', {
		/* scrollbarClass: 'myScrollbar', */
		/* 重要样式 */
		useTransition: false,
		/* 此属性不知用意，本人从true改为false */
		topOffset:pullDownOffset,
		onRefresh: function() {
			 //console.log(22)
		},
		onScrollMove: function() {
			//console.log(this.y)
			if(this.y<this.maxScrollY - 50){
				 pullUpEl.className = 'flip'; 
				 
			 }
			 
		},
		onScrollEnd: function() {
			//console.log(this.y)
			if (pullUpEl.className.match('flip')) {
					pullUpEl.className = '';
					//pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中...';
					//pullUpAction(); // Execute custom function (ajax call?)
				}
			myScroll.refresh();
		}
		});
		
		setTimeout(function() {
		document.getElementById('wrapper').style.left = '0';
		}, 800);
		}
</script>
</html>