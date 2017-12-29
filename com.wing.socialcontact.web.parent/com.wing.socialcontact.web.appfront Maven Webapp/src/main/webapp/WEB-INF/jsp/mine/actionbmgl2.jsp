<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
<meta name="keywords" content="积分明细">
<title>活动报名管理</title>

<style type="text/css">
.B-tit {
	width: 100%;
	height: .60rem;
	font-size: .24rem;
	padding: 0 .30rem;
	box-sizing: border-box;
	background: #fff;
	display: block;
}

.B-tit span {
	line-height: .60rem;
}

.B-tit span b {
	color: #0f88eb;
}

.B-tit span:nth-of-type(2) {
	margin-left: 1.30rem;
}

.b-cont {
	width: 100%;
	padding: .15rem .30rem;
	box-sizing: border-box;
	background: #fff;
	display: flex;
	margin-top: .10rem;
	align-items: center;
}

.b-r {
	margin-left: .20rem;
}

.b-r .items {
	display: flex;
	line-height: .46rem;
	font-size: .24rem;
}

.b-r .items p:nth-of-type(1) {
	width: 1.20rem;
}

.b-r .items p:nth-of-type(2) {
	margin-left: .90rem;
}

.items button {
	height: 0.4rem;
	font-size: 0.24rem;
	padding-left: 0.1rem;
	padding-right: 0.1rem;
	color: white;
	background: orange;
	border-radius: 0.1rem;
}

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
}

.list h3.h_active {
	border-bottom: 0.04rem #0f88eb solid;
	color: #0f88eb;
}

.items {
	width: 100%;
	padding: .0rem .10rem .0rem .30rem;
	background: #fff;
	margin-top: .10rem;
	box-sizing: border-box;
}

.hd {
	width: 6.9rem;
	height: 2.4rem;
	background: #a2a2a2;
	line-height: 2.40rem;
	font-size: .30rem;
	text-align: center;
}

.it {
	width: 100%;
	height: .50rem;
	display: flex;
	justify-content: space-between;
	align-items: center;
	font-size: .30rem;
	margin-top: .1rem;
}

.it img {
	width: .34rem;
	height: .33rem;
}

.items p {
	font-size: .24rem;
	line-height: .36rem;
}

.Y-items {
	display: none;
}

.Y-items.h-active {
	display: block;
}
.B-tit {
	display: none;
}

.B-tit.h-active {
	display: block;
}
/*报名中   报名开始*/
.bmz .sp {
	height: .70rem;
	display: flex;
	justify-content: flex-end;
	align-items: center;
}

.bmz .sp span {
	width: 1.54rem;
	height: .44rem;
	font-size: .24rem;
	color: #fff;
	background: #FF801B;
	line-height: .44rem;
	text-align: center;
}

.bmz .sp span+span {
	margin-left: .30rem;
}

.bmz .sp span:nth-of-type(1) {
	background: #A2A2A2;
}

.bmjs .sp {
	height: .70rem;
	display: flex;
	justify-content: flex-end;
	align-items: center;
}

.bmjs .sp span {
	width: 1.54rem;
	height: .44rem;
	font-size: .24rem;
	color: #fff;
	background: #FF801B;
	line-height: .44rem;
	text-align: center;
}

.bmjs .sp span+span {
	margin-left: .30rem;
}

.bmjs .sp span:nth-of-type(1) {
	background: #A2A2A2;
}

.jxz .sp {
	height: .70rem;
	display: flex;
	justify-content: flex-end;
	align-items: center;
}

.jxz .sp span {
	width: 1.54rem;
	height: .44rem;
	font-size: .24rem;
	color: #fff;
	background: #FF801B;
	line-height: .44rem;
	text-align: center;
}

.jxz .sp span+span {
	margin-left: .30rem;
}

.yjs .sp {
	height: .70rem;
	display: flex;
	justify-content: flex-end;
	align-items: center;
}

.yjs .sp span {
	width: 1.54rem;
	height: .44rem;
	font-size: .24rem;
	color: #fff;
	background: #FF801B;
	line-height: .44rem;
	text-align: center;
}

.yjs .sp span+span {
	margin-left: .30rem;
}
</style>
</head>

<body style="background: #f2f3f4;">
	<div class="list">
		<h3 class="h_active" onclick="loadList(1,1)" id="uncheck">未审核</h3>
		<h3 onclick="loadList(4,1)" id="accept">已通过</h3>
		<h3 onclick="loadList(5,1)" id="refuse">已拒绝</h3>
	</div>
	<div class="Y-items wks h-active">
		<div class="B-tit h_active" >
			<span>未审核<b id="number1"></b>人</span>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<span><button class="active_A" onclick="check(4)">通过</button></span>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<span><button class="redss active_A" onclick="check(5)">拒绝</button></span>
		</div>
		<div  id="datalist0">
	</div>
	<div class="Y-items bmz">
		<div class="B-tit">
			<span>已通过<b id="number4"></b>人</span>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<span><button class="redss active_A" onclick="refund()">拒绝</button></span>
		</div>
		<div  id="datalist1"></div>
	</div>
	<div class="Y-items bmjs">
		<div class="B-tit">
			<span>已拒绝<b id="number5"></b>人</span>
		</div>
		<div  id="datalist2"></div>
	</div>
	</div>
				<div class="loadingbox">
					<div class="page_loading" style="display:block;">加载中…</div>
					<div class="page_nodata" style="display:none;">暂无更多数据</div>
				</div>
	
	
</body>
</html>

<script type="text/javascript">
	onload=function(){
		document.getElementById("uncheck").click();
	}

	var page = 1;
	var status = 1;
	var end = false;
	var id="${id}";
	$('.list h3').on('click',function() {
				var index = $(this).index()
				$(this).addClass('h_active').siblings().removeClass("h_active")
				$('.Y-items').eq(index).addClass("h-active").siblings().removeClass('h-active')
				$('.B-tit').eq(index).addClass("h-active").siblings().removeClass('h-active')
			})
	$(function() {
		if (!end) {
			//滚动加载
			$(window).scroll(function() {
				var scrollTop = $(this).scrollTop();
				var scrollHeight = $(document).height();
				var windowHeight = $(this).height();
				if (scrollTop + windowHeight == scrollHeight) {
					loadList(status, 2);
					$(document).scrollTop($(document).height() * 0.7);
				}
				;
			})
		}
	})

	var pageSize = 10;
	//status 1-5     type 1 点击状态 2 上拉
	function loadList(status1, type) {
		if (type == 1) {
			page = 1;
			end = false;
		}
		if (!end) {
			zdy_ajax({
				url : '${path}/m/activity/bmgllist2.do',
				data : {
					"id":id,
					"page" : page,
					"size" : pageSize,
					"status" : status1
				},

				success : function(d, data) {
					var str = '';
					if (data.code == 0) {
						if(page==1 && !data.dataobj.length){
						   $('#activitys').html('<div  class="searchInfo">没有数据</div>');
						   $(".page_loading").hide();
						   $(".page_nodata").hide();
					    }else if(data.dataobj.length==0 || data.dataobj.length<pageSize){
							$(".page_loading").hide();
							$(".page_nodata").show();
						    end=true;
					    };
						console.log(data.dataobj);
						var a = data.dataobj.cou;
						$("#number"+status1).html(a);
						$.each(data.dataobj.list,function(i, n) {
							console.log(n)
							//人数统计
							a++;
							var sta="待审核";
							var phone="";
							var checkbostr = '<input type="checkbox" name="signupid'+status1+'" id="'+n.id+'" value="'+n.id+'" />';
							if(n.status==2){
								sta="用户取消报名";
							}
							if(n.status==3){
								sta="活动已取消";
							}
							if(n.status==4){
								sta="已通过";
							}
							if(n.status==5){
								sta="已拒绝";
								checkbostr="";
							}
							if(!isEmpty(n.phone)){
								phone=n.phone;
							}
							str+= '<div class="b-cont">'+checkbostr+'<div class="b-r"><div class="items"><p>姓名</p><p>'+
							n.userName+'</p></div><div class="items"><p>手机</p><p>'+phone
							+'</p></div><div class="items"><p>所属公司</p><p>'+n.company+'</p></div><div class="items">'+
							'<p>报名时间</p><p>'+formatDate(new Date(n.createTime))+'</p></div><div class="items"><p>报名状态</p><p>'+sta+'</p></div>';
							str+='</div></div>';
						});
						
						if (type == 1) {
							$("#datalist0").empty();
							$("#datalist1").empty();
							$("#datalist2").empty();
						};
						if (status1 == '1') {
							$("#datalist0").append(str)
						};
						if (status1 == '4') {
							$("#datalist1").append(str);
						};
						if (status1 == '5') {
							$("#datalist2").append(str);
						};
						page++;
					}
				},
				error : function(e) {
				}
			});
			status = status1;
		}
	}

	function formatDate(now) {
		var year = now.getFullYear();
		var month = now.getMonth() + 1;
		if (month < 10) {
			month = "0" + month;
		}
		var day = now.getDate();
		if (day < 10) {
			day = "0" + day;
		}
		var hour = now.getHours();
		if (hour < 10) {
			hour = "0" + hour;
		}
		var minute = now.getMinutes();
		if (minute < 10) {
			minute = "0" + minute;
		}
		var second = now.getSeconds();
		if (second < 10) {
			second = "0" + second;
		}
		return year + "/" + month + "/" + day + " " + hour + ":" + minute;
	}
	
	function check(type){
		var ids2 = $("input[name=signupid1]:checked");
		var a =[];
		$(ids2).each(function(){
		       //window.alert(this.value); 
			a+=($(this).val())+',';                 
		    }); 
		if(a.length==0){
			alert('你还没有选择任何内容！');
			return;
		}
		a=a.substring(0,a.length-1);
		zdy_ajax({
			url: '${path}/m/activity/bmglcheck2.do',
			data:{
				ids:a,
				type:type
			},
			success: function(d,data){
				if(data.code == 0){
					alert(data.msg);
					document.getElementById("uncheck").click();
				}
			},
			error:function(e){
			}
		});
	}
	function refund(){
		var ids2 = $("input[name=signupid4]:checked");
		var a ='';
		$(ids2).each(function(){
		       //window.alert(this.value); 
				a+=($(this).val())+',';                 
		    }); 
		if(a.length==0){
			alert('你还没有选择任何内容！');
			return;
		}
		a=a.substring(0,a.length-1);
		zdy_ajax({
			url: '${path}/m/activity/refundop2.do',
			data:{
				ids:a
			},
			success: function(d,data){
				if(data.code == 0){
					alert(data.msg);
					document.getElementById("accept").click();
				}
			},
			error:function(e){
			}
		});
	}
</script>