<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="user-scalable=1, initial-scale=1,maximum-scale=1, minimum-scale=1, width=device-width, height=device-height"/>
	<%@ include file="/WEB-INF/jsp/commons/include.com.jsp" %>
	<%@ include file="/WEB-INF/jsp/commons/include_login.jsp" %>
    <title></title>
    <style type="text/css">
    	   i {
				font-style: normal;
				/* margin-left:.2rem */
			}
			
			.city-box {
				width: 100%;
				height: 6rem;
				position: absolute;
				left: 0;
				top: 1rem;
				z-index:2;
				background: #fff;
			}
			
			.city-box ul {
				width:50%;
				height: 5.4rem;
				 overflow-y:overlay; 
			}
			.city-box ul::-webkit-scrollbar{
                     width: 8px;
                    height: 8px;
                    background-color: #f5f5f5;
               }
			.city-box ul li {
				
				height: .6rem;
				line-height: .6rem;
				text-align: center;
				font-size: .28rem;
			}
			.city{
				background: #fff;
			}
			.city .county {
				border-bottom: 1px beige dashed;
			}
			
			.city-box .city1 {
				position: absolute;
				left: 50%;
				top: .6rem;
				display: none;
				height: 4.8rem;
				background: #fff;
			}
			
			.city1.show {
				display: block;
			}
			.city1 .county1 {
				display: flex;
				justify-content: space-between;
				align-items: center;
				border-bottom: 1px solid #f3eded;
				box-sizing: border-box;
				background: #ffffff;
/* 				background: #f7f7f7; */
				padding:0 .20rem;
			}
			
			.bx {
				width: 50%;
				height: .6rem;
				position: absolute;
				left: 50%;
				top: 0;
				display: flex;
				justify-content: space-between;
				align-items: center;
				border-bottom: 1px solid #f3eded;
/* 				border-bottom: 1px solid #999; */
				box-sizing: border-box;
				padding:0 .20rem;
			}
			
			.fot {
				width: 100%;
				height: .6rem;
				font-size: .36rem;
				background: #479eef;
				position: absolute;
				bottom: 0;
				left: 0;
				box-sizing: border-box;
				color: #fff;
			}
			.fot span {
				display: block;
				width: 49%;
				height: .6rem;
				text-align: center;
				line-height: .6rem;
				box-sizing: border-box;
				float: left;
			}
			.fot span+span{
				    border-left: 1px solid #f8f8f8;
			}
			.on {
				background: #B4D7F7 url(${path }/resource/images/selected.png) no-repeat center;
				background-position-x: 0.1rem;
				background-size: 10%;	
			}
           .wc{
           font-size:0.3rem
           }
           .cz{font-size:0.3rem
              
           }
           .time{
              position:relative;
           }
           .fuwu{
              position:relative;  
           }
         strong,b{font-weight:normal;}
         header{
	         position: fixed;
		    top: 0;
		    left: 0;
		    z-index: 1;
         }
         section{
              
        	   margin-top:1.66rem; 
         }
         
         /*iscroll定位*/
         #wrapper {
				/* position: absolute; */
				z-index: 1;
				top: 1.65rem;
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
			
			#pullDown{
				background: #fff;
				/* height: 40px;
				line-height: 40px;  */
				font-size: 14px;
				color: #888;
				text-align:center
			}
			#pullUp{
			 
				height: 3rem;
				line-height: 0.5rem;
				font-size: 14px;
				color: #888;
				text-align:center;
				/* padding-bottom:2rem; */
			}
			.fllb_ul_box{
			   position:fixed;
			   left:0;
			   top:0;
			   width:100%;
			   z-index:100;
			}
			.pullUpLabel{
			   display:block;
			   margin:0 auto;
			   height:1rem;
			   line-height:1rem;
			   background:white
			}
    </style>
</head>
<body>
<div class="wrap">
	<header>
	   <div class="table1 " style="position:relative;">
    	<span class="header_return"><img src="${path }/resource/images/return.png" onclick="backForAndroid()"></span>
        <h1>${classService.className }</h1>
        <span class="header_search" style="position:absolute;top:0;right:0%;"><img src="${path }/resource/images/search.png"/></span>
        </div>
           <div class="table2" style="display:none;">
      <div class="lsy_sskbox " id="lsy_sskbox">
		        <input id="field1" type="text" onfocus="onf()" onblur="off()" placeholder="  搜索需要的服务" 
		        onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" onpaste="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" 
		        oncontextmenu = "value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')"/>
		         <!-- <span class="header_search" style="margin-top:0.1rem;"><div class="st" style="color:#f2f2f2;">搜索</div></span> -->
				 <span class="search" style="margin-top:0.1rem;" onclick="canSearch()"><div class="st" style="color:#f2f2f2;">取消</div></span>
		         <span class="search" style="margin-top:0.1rem;" onclick="search()"><div class="st" style="color:#f2f2f2;">搜索</div></span>
		</div>
      </div>
    </header>
    <div class="fllb_ul_box" style="margin-top:0.86rem">
	            <ul class="fllb_ul">
	                <li class="fllb_ul_select">地区&nbsp;&nbsp;&nbsp;
	                	<img src="${path }/resource/images/dq_jt_up.png" class="dq_jt_class">
	                </li>
	<!--                 <li><select name="" class="fllb_ul_select">地区</select></li> -->
	                <li class="fuwu" onclick="changeStatus()">已服务数&nbsp;&nbsp;&nbsp;
	                    <img src="${path }/resource/images/list_03.png" id="serviceCountS" class="hover-total">
	                </li>
	                <li class="time" onclick="changeStatusTime()">时间&nbsp;&nbsp;&nbsp;
	                    <img src="${path }/resource/images/list_03.png" class="hover-total2" id="serviceTimeS">
	                </li>
	                
	                   
	                <%-- <img src="${path }/resource/images/top.png" class="hover-li">
	                 <img src="${path }/resource/images/top.png" class="hover-time"> --%>
	            </ul>
	            <div class="city-box" style="display:none">
	<!--             	省 -->
					<ul class="city">
						<c:forEach items="${provinceList}" var="province" varStatus="indexs">
							<c:choose>
								<c:when test="${indexs.index == 0 }">
									<li data-val="${province.disName}" id="${province.id}" class="county"> ${province.disName} </li>
								</c:when>
								<c:otherwise>
									<li data-val="${province.disName}" id="${province.id}" class="county"> ${province.disName} </li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</ul>
					<c:forEach items="${provinceList}" var="province" varStatus="indexs">
						<c:choose>
							<c:when test="${indexs.index == 0 }">
								<ul id="${province.id}" class="city1 show" data-val="${province.disName}">
						          <c:forEach items="${cityList }" var="city">
							           <c:if test="${city.superId eq province.id}">
							          	 <li class="county1"><i>${city.disName}</i> <input data-val="${province.disName}" type="checkbox" name="k" id="${city.id}" value="${city.id}" /></li>
							          </c:if>
							       </c:forEach>
						     	 </ul>
							</c:when>
							<c:otherwise>
								<ul id="${province.id}" class="city1" data-zt="0" data-val="${province.disName}">
						          <c:forEach items="${cityList }" var="city">
							           <c:if test="${city.superId eq province.id}">
							          	 <li class="county1"><i>${city.disName}</i> <input data-val="${province.disName}" type="checkbox" name="k" id="${city.id}" value="${city.id}" /></li>
							          </c:if>
							       </c:forEach>
						       </ul>
							</c:otherwise>
						</c:choose>
				   </c:forEach>
					<div class="bx">
						<label for="r" style="margin-left:.2rem">不限</label><input type="checkbox" name="r" id="r" value="" />
					</div>
					<div class="fot">
						<span class="cz">重置</span>
						<span class="wc">完成</span>
					</div>
			</div>
	           
			
	        </div>
    <div id="wrapper">
	  <div id="scroller">
		<div id="pullDown">
			<span class="pullDownLabel"></span>
		</div>
		<section style="overflow-y: inherit;">
	    	
	   	    <div class="box_box" id="entryList"></div>
	   	    <div id="pullUp" style="text-align: center;">
	           <span class="pullUpLabel"></span>
		  <!-- <span class="pullUpLabel">奋力加载中...</span> -->
	        </div>
	    </section>
	  	
	   </div>
	  </div>  
	    
	</div>
</body>
</html>
<script type="text/javascript">

//var m = "${me.getId()}";
//asc 上   desc  下
var myScroll;
var timeSort = ''; 
var serviceCountSort = '';
// var timeSort = 'asc'; 
// var serviceCountSort = 'asc';
var classId = '${classService.id }';  
var totalPage;
var oldpageSize = '';

var prov = '';
var city = '';
var page = 0;
var size = 10;
var searchName = '';

	document.addEventListener('touchmove', function(e) {
		e.preventDefault();
	}, false);
	document.addEventListener('DOMContentLoaded', loaded, false);	
	

$(function() {
	page = 1;
	//toNextPage();
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
	
function toNextPage(){
	//console.log(prov+"...."+city);
	zdy_ajax({
		url: "${path}/m/qfy/classTable.do",
		showLoading:false,
		data:{
			//m:m,
			timeSort:timeSort,
			serviceCountSort:serviceCountSort,
			classId:classId,
			prov:prov,
			city:city,
			page:page,
			size:size,
			searchName:searchName
		},
		success: function(data,res){
			if(res.code == 0){
				//console.log(data)
				var entryString = ''; 
				if(data.lst){
					$.each(data.lst , function(i, n){
			            entryString += '<div class="zxlm_list_box">';
			            entryString += '<dl onclick="openEntryDetail(\''+n.id+'\')">';
			            entryString += '<dt><img src="'+_oss_url+n.bannerPath+'"></dt>';
			            entryString += '<dd><span>已服务<ii>'+n.serviceCount+'</ii>家</span><h3>'+n.name+'</h3></dd>';
			            entryString += '</dl> </div>';
					});
					$("#entryList").append(entryString);
					totalPage = Math.ceil(data.totalSize / size);
					
					if(totalPage == 0){
						$(".pullUpLabel").text("暂无更多数据");
					}else{
						$(".pullUpLabel").text("上拉加载");
					} 
				}else{
					$(".pullUpLabel").text("暂无更多数据");
					$("#entryList").css('display','none'); 
				}
				if(entryString == ''){
					$(".pullUpLabel").text("暂无更多数据");
				}
			}
			myScroll.refresh();
		},
	    error:function(e){
	    }
	}); 
}
function sortSearchPage(){
	zdy_ajax({
		url: "${path}/m/qfy/classTable.do",
		showLoading:false,
		//async:false,
		data:{
			//m:m,
			timeSort:timeSort,
			serviceCountSort:serviceCountSort,
			classId:classId,
			prov:prov,
			city:city,
			page:page,
			size:size,
			searchName:searchName
		},
		success: function(data,res){
			if(res.code == 0){
				var entryString = ''; 
				if(data.lst){
					$.each(data.lst , function(i, n){
			            entryString += '<div class="zxlm_list_box">';
			            entryString += '<dl onclick="openEntryDetail(\''+n.id+'\')">';
			            entryString += '<dt><img src="'+_oss_url+n.bannerPath+'"></dt>';
			            entryString += '<dd><span>已服务<i>'+n.serviceCount+'</i>家</span><h3>'+n.name+'</h3></dd>';
			            entryString += '</dl> </div>';
					});
					$("#entryList").html(entryString);
					totalPage = Math.ceil(data.totalSize / size);
					/* if(totalPage == 0){
						$(".pullUpLabel").css("display","none");
					} */
				}else{
					$(".pullUpLabel").text("暂无更多数据");
					$("#entryList").css('display','none'); 
				}
				if(entryString == ''){
					$(".pullUpLabel").text("暂无更多数据");
				}
				
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
			toNextPage();
			//$(".pullUpLabel").text("暂无更多数据");
		}
	}else{
		$(".pullUpLabel").text("暂无更多数据");
	} 
}  */

function openEntryDetail(id){
	window.location.href="${path}/m/qfy/entryDetailPage.do?entryId="+id+"&page=classTablePage.do?classId="+classId
}
function openNewUrl(url){
	window.location.href=url;
}
function backForAndroid(){
	document.location.href= "classIndexPage.do";
}
//1 up 2 down 0 init   切换服务数排序
var serviceStatus = 0;
function changeStatus(t){
	if(serviceStatus == 1){
		$("#serviceCountS").attr("src","${path }/resource/images/bottom.png")
		serviceStatus = 2;
		serviceCountSort="desc";
		page = 1;
	}else if(serviceStatus == 2 || serviceStatus == 0 ){
		$("#serviceCountS").attr("src","${path }/resource/images/top.png")
		serviceStatus = 1;
		serviceCountSort="asc";
		page = 1;
	}
	timeSort="";
	$("#serviceTimeS").attr("src","${path }/resource/images/list_03.png")
	erviceStatusTime = 0;
	$("#entryList").empty();
	sortSearchPage();
}

//切换时间排序  //1 up 2 down 0 init
var serviceStatusTime = 0;
function changeStatusTime(t){
	if(serviceStatusTime == 1){
		$("#serviceTimeS").attr("src","${path }/resource/images/bottom.png")
		serviceStatusTime = 2;
		timeSort="desc";
		page = 1;
	}else if(serviceStatusTime == 2 || serviceStatusTime == 0){
		$("#serviceTimeS").attr("src","${path }/resource/images/top.png")
		serviceStatusTime = 1;
		timeSort="asc";
		page = 1;
	}
	serviceCountSort="";
	$("#serviceCountS").attr("src","${path }/resource/images/list_03.png")
	serviceStatus = 0;
	$("#entryList").empty();
	sortSearchPage();
}

function search(){
	searchName=$("#field1").val();
	$("#entryList").empty();
	page = 1;
	sortSearchPage();
}
function canSearch(){
	$("#field1").val("");
	$(".table1").show();
	$(".table2").hide();
	searchName='';
	$("#entryList").empty();
	page = 1;
	sortSearchPage();
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
	$("#entryList").empty();
	searchName=$("#field1").val();
	page = 1;
	toNextPage();
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


$(".fllb_ul_select").on("click", function() {
	if($('.city-box').css('display')=="block"){
		$('.city-box').css({'display':'none'})
		$(".dq_jt_class").attr("src","${path }/resource/images/dq_jt_up.png");
	}else{
		$('.city-box').css({'display':'block'})
		$(".dq_jt_class").attr("src","${path }/resource/images/dq_jt.png");
	}
		
})
//城市选着
$(".city .county").on("click", function() {
	var index = $(this).index()
	$(".city1").eq(index).addClass('show').siblings().removeClass("show")
	var k=0;
	allC(k);
	/* $(this).addClass('on').siblings().removeClass('on') */
})
//不限
$('#r').on('click', function() {
	var all = $(this).prop("checked")
	var  id = $(".city1.show").attr('id')
	getId(id,all)
	if($(this).prop("checked")){
		$(".city1.show").attr('data-zt',1)
		
	}else{
		$(".city1.show").attr('data-zt',0)
	}
	$(".city1.show").find('input').each(function() {
		$(this).prop("checked", all);
		$(this).attr("disabled",all)
	})
})
//获取city li 的id
function getId(id,all){
	$('.city li').each(function(i){
		if(id==$(this).attr('id')){
		   if(all){
			   $(this).addClass('on')
		   }else{
			   $(this).removeClass('on')
		   }
		   return
		}
	})
}
//重置
$('.cz').on('click', function() {
	$("input").each(function() {
		if($(this).prop("checked")){
			$(this).prop("checked", false);
		}	
	})
	$(".city1.show").attr('data-zt',0)
	$(".city1").attr('data-zt',0)
	$('.city li').each(function(i){
		if($(this).hasClass('on')){
			$(this).removeClass('on')
		}
	})
	
})
//判断是否全选
function allC(k){
	$(".city1.show").find('input').each(function() {
		if($(this).prop("checked")){
			k++
		}
	})
	if(k == $(".city1.show").find('input').length){
		$('#r').prop('checked',true)
	}else{
		$('#r').prop('checked',false)
	}
}
$('.city1 li input').on('click',function(){
	var all = $(this).prop("checked")
	var  id = $(".city1.show").attr('id')
	getId(id,all)
})

$('.wc').on('click',function(){
	//var provName = "";
	var str='',str2=[],cityListId='', str1='';
	$(".city1").each(function(k,n){
		if($('.city1').eq(k).attr('data-zt')==1){
			str+="'"+$('.city1').eq(k).attr('id')+"',";
			//provName = $('.city1').eq(k).attr('data-val')
		}
	})
	$(".city1 input[type=checkbox]").each(function(k,n){
		if($(".city1 input[type=checkbox]").eq(k).prop("checked")){
			if($(".city1 input[type=checkbox]").eq(k).parents('.city1').attr('data-zt')==1){
				
			}else{
				provName = $(".city1 input[type=checkbox]").eq(k).parents('.city1').attr('data-val')
				str2.push($(".city1 input[type=checkbox]").eq(k).parents('.city1').attr('id'))
			    cityListId+="'"+$(".city1 input[type=checkbox]").eq(k).attr('id')+"',"
			}
		}
	})
	//if(provName){
		//$(".fllb_ul_select").html(provName) 
	//}
	var str2 = unique1(str2);
	if(str2){
		var  len = str2.length;
		for(var i= 0;i<len;i++){
			str1 +="'"+str2[i]+"'," 
		}
	}
	prov = jq(str1+str);
	city = jq(cityListId);
	
	//if(!prov){
		//$(".fllb_ul_select").html("地区");
	//}
	
	$('.city-box').hide();
	$("#entryList").empty();
	toNextPage();
	myScroll.refresh();
})
function jq(str){
	var str = str.slice(0,str.length-1)
	return str
}
// 最简单数组去重法 
function unique1(array){ 
	var n = []; //一个新的临时数组 
	//遍历当前数组 
	for(var i = 0; i < array.length; i++){ 
		//如果当前数组的第i已经保存进了临时数组，那么跳过， 
		//否则把当前项push到临时数组里面 
		if (n.indexOf(array[i]) == -1) 
			n.push(array[i]); 
	} 
	return n; 
}



//iscroll翻页
          
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
			 
		}, 1000); 
		myScroll.refresh();
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
			topOffset: '',
			onRefresh: function() {
				 //console.log(22)
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
				 myScroll.refresh();
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







</script>
