<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<title>我发布的活动</title>
		<style type="text/css">
			.list {
				width: 100%;
				padding: 0 .30rem;
				box-sizing: border-box;
				background: #fff;
				display: flex;
				justify-content: space-between;
				align-items: center;
				font-size: .28rem;
				height: .80rem;
			}
			
			.list h3 {
				height: .80rem;
				text-align: center;
				line-height: .80rem;
				font-weight: normal;
				font-size: .28rem;
			}
			
			.list h3.h_active {
				border-bottom: 2px #0f88eb solid;
				color: #0f88eb;
			}
			
			.items{
				width: 100%;
				padding: .40rem .30rem .10rem .30rem;
				background: #fff;
				margin-top: .10rem;
				box-sizing: border-box;
			}
			.hd{
				width: 6.9rem;
				height: 3.45rem;
				background: #a2a2a2;
				line-height: 3.45rem;
				font-size: .30rem;
				text-align: center;
			}
			.it{
				width: 100%;
				height: .50rem;
				display: flex;
				justify-content: space-between;
				align-items: center;
				font-size: .30rem;
				margin-top: .1rem;
			}
			.it img{
				width: .34rem;
				height: .33rem;
			}
			.items p{
				font-size: .24rem;
				line-height: .36rem;
			}
			.Y-items{
				display: none;
			}
			.Y-items.h-active{
				display: block;
			}
			/*报名中   报名开始*/
			.bmz .sp{
				height: .70rem;
				display: flex;
				justify-content: flex-end;
				align-items: center;
			}
			.bmz .sp span{
				width: 1.54rem;
				height: .44rem;
				font-size: .24rem;
				color: #fff;
				background: #0f88eb;
				line-height: .44rem;
				text-align: center;
			}
			
			.sp span a{
				color: #fff;
			}
			
			.bmz .sp span+span{
				margin-left: .30rem;
			}
			.bmz .sp span:nth-of-type(1){
				background: #A2A2A2;
			}
			.bmjs .sp{
				height: .70rem;
				display: flex;
				justify-content: flex-end;
				align-items: center;
			}
			.bmjs .sp span{
				width: 1.54rem;
				height: .44rem;
				font-size: .24rem;
				color: #fff;
				background: #0f88eb;
				line-height: .44rem;
				text-align: center;
			}
			.bmjs .sp span+span{
				margin-left: .30rem;
			}
			.bmjs .sp span:nth-of-type(1){
				background: #A2A2A2;
			}
			.jxz .sp{
				height: .70rem;
				display: flex;
				justify-content: flex-end;
				align-items: center;
			}
			.jxz .sp span{
				width: 1.54rem;
				height: .44rem;
				font-size: .24rem;
				color: #fff;
				background: #0f88eb;
				line-height: .44rem;
				text-align: center;
			}
			.jxz .sp span+span{
				margin-left: .30rem;
			}
			.yjs .sp{
				height: .70rem;
				display: flex;
				justify-content: flex-end;
				align-items: center;
			}
			.yjs .sp span{
				width: 1.54rem;
				height: .44rem;
				font-size: .24rem;
				color: #fff;
				background: #0f88eb;
				line-height: .44rem;
				text-align: center;
			}
			.yjs .sp span+span{
				margin-left: .30rem;
			}
		</style>
	</head>

	<body style="background: #f2f3f4;">
		<div class="list">
			<h3 class="h_active" onclick="loadList(1,1)" id="unaccepted">未通过</h3>
			<h3 onclick="loadList(2,1)" id="baomingzhong">报名中</h3>
			<h3 onclick="loadList(3,1)" id="yijiezhi">已截止</h3>
			<h3 onclick="loadList(4,1)">进行中</h3>
			<h3 onclick="loadList(5,1)">已结束</h3>
			<h3 onclick="loadList(6,1)">已取消</h3>
		</div>
		<div class="Y-items wks h-active bmz"  id="wks">
		</div>
		<div class="Y-items bmz" id="bmz">
		</div>
		<div class="Y-items bmjs" id="bmjs">
		</div>
		<div class="Y-items jxz" id="jxz">
		</div>
		<div class="Y-items yjs" id="yjs">
		</div>
		<div class="Y-items yxq bmz" id="yxq">
		</div>
		
		<div class="loadingbox" style="margin-top:0.2rem">
			<div class="page_loading" style="display:block;">加载中…</div>
			<div class="page_nodata" style="display:none;">暂无更多数据</div>
		</div>
	</body>
</html>

<script type="text/javascript">
var page=1;
var status =1;
var end=false;
$('.list h3').on('click', function() {
	var index = $(this).index()
	$(this).addClass('h_active').siblings().removeClass("h_active")
	$('.Y-items').eq(index).addClass("h-active").siblings().removeClass('h-active')
})
	
onload=function(){
		document.getElementById("unaccepted").click();
}

$(function(){
	//loadList(1,1);
	
	if(!end){
	//滚动加载
	  $(window).scroll(function(){
	       var scrollTop=$(this).scrollTop();
	        var scrollHeight = $(document).height();
	            var windowHeight = $(this).height();
	            if (scrollTop+windowHeight==scrollHeight) {
	            	page++;
	            	loadList(status,2); 
	            	$(document).scrollTop($(document).height()*0.7);
	            };
	    })
	}
})	


var pageSize = 10;
		//status 1-5     type 1 点击状态 2 上拉
		function loadList(status1,type){
				if(type==1){
					page=1;
					end=false;
				}
				if(!end){
				zdy_ajax({
					url: '${path}/m/activity/myactivities.do',
					data:{
						"page":page,
						"size":pageSize,
						"status":status1
					},
					
					success: function(d,data){
						//alert(JSON.stringify(data));
						var str='';
						if(data.code == 0){
							if(page==1 && !data.dataobj.length==0){
								  //$('.Y-items').html('<div  class="searchInfo">暂无数据/div>');
								   $(".page_loading").hide();
								   $(".page_nodata").show();
							    }else if(data.dataobj.length==0 || data.dataobj.length<pageSize){
									$(".page_loading").hide();
									$(".page_nodata").show();
								    end=true;
							    };
							$.each(data.dataobj, function(i, n){
								str+='<div class="items"><a href="${path}/m/activity/activityDetailPage.do?id='+n.id+'"><div class="hd"><img src="'+_oss_url+n.imagePath+'"  style="width:100%;height:100%;"/></a></div><div class="it"><a href="${path}/m/activity/activityDetailPage.do?id='+n.id+'"><h3>'+n.titles+'</h3></a>';
								if(status1=='1'){
									str +='<a href="${path}/m/activity/editaction.do?id='+n.id+'"><img src="${path}/resource/img/icons/edition.png"/></a>';
									str +='</div><p>'+formatDate(new Date(n.startTime))+'</p>';
									str +='<div class="sp">';
									if(n.iscancel=='0'){
										str +='<span id="'+n.id+'" status="'+status1+'" class="quxiao">取消活动</span>';
									}else{
										str +='<span id="'+n.id+'">活动取消申请中</span>';
									}
									str +='<span><a href="${path}/m/activity/bmglpage.do?id='+n.id+'">查看报名</a></span></div>';
								}
								if(status1=='2'||status1=='3'){
									str +='</div><p>'+formatDate(new Date(n.startTime))+'</p>';
									str +='<div class="sp">';
									if(n.iscancel=='0'){
										str +='<span id="'+n.id+'" status="'+status1+'" class="quxiao">取消活动</span>';
									}else{
										str +='<span id="'+n.id+'">取消申请中</span>';
									}
									if(n.isdelay=='0'){
										str +='<span name="'+n.id+'" status="'+status1+'" class="delay">延迟活动</span>';
									}else{
										str +='<span id="'+n.id+'">延期申请中</span>';
									}
									str +='<span><a href="${path}/m/activity/activityDetailPage.do?id='+n.id+'">查看活动</a></span><span><a href="${path}/m/activity/bmglpage.do?id='+n.id+'">查看报名</a></span></div>';
								}
								if(status1=='4'||status1=='5'||status1=='6'){
									str +='</div><p>'+formatDate(new Date(n.startTime))+'</p>';
									str +='<div class="sp"><span><a href="${path}/m/activity/bmglpage.do?id='+n.id+'">查看报名</a></span></div>';
								} 
								str+='</div>';
							});
							
							if(type==1){
								$("#wks").empty();
								$("#bmz").empty();
								$("#bmjs").empty();
								$("#jxz").empty();
								$("#yjs").empty();
								$("#yxq").empty();
							}
							if(status1=='1'){$("#wks").append(str);
								$('.quxiao').bind('click',function(){
									var id = $(this).attr('id');
									var status = $(this).attr('status');
									cancel(id,status);
								});
								};
							if(status1=='2'){$("#bmz").append(str);
								$('.quxiao').bind('click',function(){
									var id = $(this).attr('id');
									var status = $(this).attr('status');
									cancel(id,status);
								});
								$('.delay').bind('click',function(){
									var id = $(this).attr('name');
									var status = $(this).attr('status');
									delayedit(id,status);
								});
							};
							if(status1=='3'){$("#bmjs").append(str);
							$('.quxiao').bind('click',function(){
								var id = $(this).attr('id');
								var status = $(this).attr('status');
								cancel(id,status);
							});
							$('.delay').bind('click',function(){
								var id = $(this).attr('name');
								var status = $(this).attr('status');
								delayedit(id,status);
							});
							};
							if(status1=='4'){$("#jxz").append(str)};
							if(status1=='5'){$("#yjs").append(str)};
							if(status1=='6'){$("#yxq").append(str)};
							page++;
						}
				},
				error:function(e){
				}
				});
				status =status1;
				}
			}
			
	function  cancel(id,status){
		layer.open({
			type : 2,
			//skin: 'layui-layer-lan',
			title: false,
				closeBtn: 0, //不显示关闭按钮
			fix : true,
			shadeClose : true,
			maxmin : false,
			area : [ '100%', '100%' ],
			content : '${path}/m/activity/canceledit.do?id='+id+'&status'+status,
			shift : 2,
			scrollbar : false,
			success : function(layero, index) {
			},
			end : function() {
				if(status==1){
					document.getElementById("unaccepted").click();
				}else if(status==2){
					document.getElementById("baomingzhong").click();
				}else if(status==3){
					document.getElementById("yijiezhi").click();
				}
				
			},
			cancel : function(cancel) {
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
		return year+"/"+month+"/"+day+" "+hour+":"+minute; 
	} 
	
var delayedit = function(id,status) {
	console.log(status)
	layer.open({
		type : 2,
		//skin: 'layui-layer-lan',
		title: false,
			closeBtn: 0, //不显示关闭按钮
		fix : true,
		shadeClose : true,
		maxmin : false,
		area : [ '100%', '100%' ],
		content : '${path}/m/activity/delayedit.do?id=' + id+'&status'+status,
		shift : 2,
		scrollbar : false,
		success : function(layero, index) {
		},
		end : function() {
			if(status==1){
				document.getElementById("unaccepted").click();
			}else if(status==2){
				document.getElementById("baomingzhong").click();
			}else if(status==3){
				document.getElementById("yijiezhi").click();
			}
		},
		cancel : function(cancel) {
		}
	});
}
</script>