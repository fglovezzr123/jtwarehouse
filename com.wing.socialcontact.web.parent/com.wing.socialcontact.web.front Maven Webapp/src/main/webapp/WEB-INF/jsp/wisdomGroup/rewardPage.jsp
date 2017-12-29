<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="èµè®¯">
		<title>打赏</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/swiper-3.3.1.min.css"/>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css?s=3" />
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/issuePk.css" />
		<style type="text/css">
		.pk-con{
		 position:relative;
		
		}
		.pk-con input{
		 width:50%;
		 border: 1px #f2f3f4 solid;
		 padding-bottom: 0; 
		}
		.C-z{
 	width: 20%;
    height: .52rem;
    font-size: .30rem;
    background: #95b8e7;
    color: #fff;
    text-align: center;
    line-height: .52rem;
    position: absolute;
    top: .56rem;
    right: .30rem;
    border-radius: 5px;
    opacity: 0.8;
}
 }
		</style>
	</head>

	<body>
		<body style="background: #f2f3f4;">
		<div class="I-Pk" style="background: #f2f3f4;width: 100%;">
			<div class="p-content show">
				<div class="pk-con">
				<p>请您输入打赏的J币数量</p>
					<input type="text" name="jcount" id="jcount" value=""  onchange='isNumberCount(this)' />
					<div class="C-z active_A" id="chongzhi" style="display:none" onclick="chongzhiPage()">充值</div>
					<p style="border-top: 1px solid #ece9e9; margin-top:0.1rem">您当前余额为：<span style="color:red">${jbAmount}J币</span></p>
				</div>
			</div>
			<div class="submit active_A" onclick="reward_submit();">打赏</div>
		</div>
		
		<script src="${path}/resource/js/libs/public.js" type="text/javascript" charset="utf-8"></script>
	</body>
<script type="text/javascript">
$(function(){
	showChongzhi();
});
 var showChongzhi = function(){
	 
	var jcount=jQuery.trim($("#jcount").val());
	if(!isEmpty(jcount)){
		if(parseInt(jcount) < parseInt('${jbAmount}')){
			$("#chongzhi").hide();
		}else{
			$("#chongzhi").show();
		}
	}
} 

var chongzhiPage = function(){
	if(zfflag){
		window.self.location='${path}/m/my/jb_cz.do';
	}else{
		layer.msg("该功能尚未开通")
	}
}
//点击打赏按钮
var reward_submit=function(){
	var jcount=$("#jcount").val();
	if(isEmpty(jcount)){
		alert_back("打赏数量不能为空",function(){
			$("#jcount").focus();
		});
		return;
	}
	zdy_ajax({
		url: '${path}/library/m/rewardJ.do',
		data:{
			fkId:'${fkId}',
			jcount:jcount
		},
		success: function(data,output){
			if(output.code == 0){
				alert_back("打赏成功！",function(){
					self.location=document.referrer;
				});
			}
		},
		error:function(e){
		}
	});
}
//判断是数字
function isNumberCount(obj){
	  var reg = new RegExp("^[0-9]*[1-9][0-9]*$");
	  if(obj.value.indexOf(" ")!= -1){
		  alert("您输入的值存在空格，请检查！");
		  obj.value="1";
		  return null;
	  }
	  if(obj.value != ""){  
		  if((!reg.test(obj.value))){
			   alert("打赏J币数量为1~99999999整数!");
			   obj.value="1";
			  }
		  if (parseInt(obj.value) > 99999999) {
				alert("打赏J币数量为1~99999999整数!");
				obj.value = "1";
		  }  
	  }
	  showChongzhi(); 
}
</script>

</html>