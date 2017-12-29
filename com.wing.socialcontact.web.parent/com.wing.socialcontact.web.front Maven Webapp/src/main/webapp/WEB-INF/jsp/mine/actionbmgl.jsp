<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="">
		<title>活动报名管理</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css?v=${sversion}" />
		<script src="${path}/resource/js/libs/public.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>

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
				color: #FF801B;
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
			.items button{
              height:0.4rem;
              font-size:0.24rem;
              padding-left:0.1rem;
              padding-right:0.1rem;
              color:white;
              background:orange;
              border-radius:0.1rem;
			}
		</style>
	</head>

	<body style="background: #f2f3f4;">
		<div id="datalist"></div>
	</body>

</html>
<script>
var id="${id}";
$(function(){
	loadlist();
});

function loadlist(){
	$("#datalist").empty();
	var a = 0;
	var j = 0;
	var s='<div class="B-tit">';
	zdy_ajax({
		url: '${path}/m/activity/bmgllist.do',
		data:{
			"id":id
		},
		success: function(d,data){
			if(data.code == 0){
				var str='';
				//alert(JSON.stringify(data));
				$.each(data.dataobj, function(i, n){
					var sta="待审核";
					var phone="";
					//报名人数统计
					a++;
					if(n.status==2){
						sta="用户取消报名";
					}
					if(n.status==3){
						sta="活动已取消";
					}
					if(n.status==4){
						//报名通过人数统计
						j++;
						sta="已通过";
					}
					if(n.status==5){
						sta="已拒绝";
					}
					if(!isEmpty(n.phone)){
						phone=n.phone;
					}
					str+=    '<div class="b-cont"><div class="b-r"><div class="items"><p>姓名</p><p>'+
					n.userName+'</p></div><div class="items"><p>手机</p><p>'+phone
					+'</p></div><div class="items"><p>所属公司</p><p>'+n.company+'</p></div><div class="items">'+
					'<p>报名时间</p><p>'+formatDate(new Date(n.createTime))+'</p></div><div class="items"><p>报名状态</p><p>'+sta+'</p></div>';
					
					if(n.actstatus<5){
						if(n.status==1){
							str+='<div class="items" style="width:100%">'+
							'<p><button class="active_A" onclick="check(4,'+"'"+n.id+"'"+')">通过</button></p>'+
							'<p><button class="redss active_A" onclick="check(5,'+"'"+n.id+"'"+')">拒绝</button></p>';
							/* if(n.orderStatus ==2){
								str+='<p><button class="redss active_A" onclick="refund('+"'"+n.id+"'"+')">退款</button></p>';
							}else if(n.orderStatus ==3){
								str+='<p><button class="redss active_A" ">已退款</button></p>';
							}else{
								str+='';
							} */
							str+='</div>';
						}else{
							str+='<div class="items" style="width:100%">';
							if(n.orderStatus ==2){
								str+='<p><button class="redss active_A" onclick="refund('+"'"+n.id+"'"+')">退款</button></p>';
							}else if(n.orderStatus ==3){
								str+='<p><button class="redss active_A" ">已退款</button></p>';
							}else{
								str+='';
							}
							str+='</div>';
						}
					}else if(n.actstatus=5){
							/* str+='<div class="items" style="width:100%">'+
							if(n.orderStatus ==2){
							'<p><button class="redss active_A" onclick="refund('+"'"+n.id+"'"+')">未退款</button></p>'+
							}else if(n.orderStatus ==3){
								'<p><button class="redss active_A" ">已退款</button></p>'+
							}
							'</div>'; */
					}else if(n.actstatus=6){
						str+='<div class="items" style="width:100%">';
							if(n.orderStatus ==2){
								str+='<p><button class="redss active_A" onclick="refund('+"'"+n.id+"'"+')">退款</button></p>';
							}else if(n.orderStatus ==3){
								str+='<p><button class="redss active_A" ">已退款</button></p>';
							}else{
								str+='';
							}
							str+='</div>';
					}
					str+='</div></div>';	
			});
				s+='<span>共报名<b>'+a+'</b>人</span><span>报名通过<b>'+j+'</b>人</span>'
				s=s+str;
				$("#datalist").append(s);
		}
	},
	error:function(e){
	}
	});
}

function check(type,id){
	zdy_ajax({
		url: '${path}/m/activity/bmglcheck.do',
		data:{
			id:id,
			type:type
		},
		success: function(d,data){
			if(data.code == 0){
				alert(data.msg);
				loadlist();
			}
		},
		error:function(e){
		}
	});
}
function refund(id){
	zdy_ajax({
		url: '${path}/m/activity/refundop.do',
		data:{
			id:id
		},
		success: function(d,data){
			if(data.code == 0){
				alert(data.msg);
				loadlist();
			}
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
	return year+"/"+month+"/"+day+" "+hour+":"+minute+":"+second; 
	} 
</script>
