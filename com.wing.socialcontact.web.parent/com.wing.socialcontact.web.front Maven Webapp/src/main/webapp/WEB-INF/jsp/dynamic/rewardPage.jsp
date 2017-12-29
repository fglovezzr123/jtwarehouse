<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="打赏">
		<title>打赏</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/issuePk.css?v=${sversion}" />
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

		</style>
	</head>

	<body>
		<body style="background: #f2f3f4;">
		<div class="I-Pk" style="background: #f2f3f4;width: 100%;">
			<div class="p-content show" style="margin-top: 0;">
				<div class="pk-con">
				    <p>请您输入打赏的J币数量</p>
					<input type="number" name="jcount" id="jcount" value=""/>
					<div class="C-z active_A" id="chongzhi"  onclick="chongzhiPage()">充值</div>
					<p style="border-top: 1px solid #ece9e9; margin-top:0.1rem">您当前余额为：<span style="color:red">${jbAmount}J币</span></p>
				</div>
			</div>
			<div class="submit active_A" onclick="reward_submit();" >打赏</div>
		</div>
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
		url: "${path}/m/dynamic/isReward.do",
		data:{
			dynamicId:'${dynamicId}',
		},
		success: function(data,res){
			if(res.code == 0){
				if(res.msg==0){
					reward_submit1();
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
var reward_submit1=function(){
	var jcount=jQuery.trim($("#jcount").val());
	var r = /^(0|[1-9]\d*)$/
	if(isNaN(jcount)){
		alert_back("打赏J币数量为1~99999999整数！",function(){
			$("#jcount").focus();
		});
		return;
	}else if(!r.test(jcount)){
		alert_back("打赏J币数量为1~99999999整数！",function(){
			$("#jcount").focus();
		});
		return;
	}else if(parseInt(jcount)>99999999 || parseInt(jcount) ==0){
		alert_back("打赏J币数量为1~99999999整数！",function(){
			$("#jcount").focus();
		});
		return;
	}
	showChongzhi();
	zdy_ajax({
		url: '${path}/m/dynamic/rewardJ.do',
		data:{
			dynamicId:'${dynamicId}',
			jcount:jcount
		},
		success: function(data,output){
			if(output.code == 0){
				alert_back("打赏成功",function(){
					/*if(parent){
						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						parent.location.reload(); // 父页面刷新  
						parent.layer.close(index);
					}*/
					window.self.location='${fromUrl}';
				});
			}
		},
		error:function(e){
			//alert(e);
		}
	});
}
</script>

</html>