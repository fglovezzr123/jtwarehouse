<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_upload.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="发布动态">
		<title>发布动态</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/issuePk.css?v=${sversion}" />
		<style type="text/css">
		/*公共样式部分*/
     		.item{
     			padding: 0.15rem .3rem;
     			background: #F2F3F4;
     			display: flex;	
     		}
     		.item .it-img{
     			/*width: 2.5rem;*/
     			width: 36.23%;
     			/* height: 1.5rem; */
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
     		.item .it-cont .bot span{
     			line-height: 2.5;
     		}
		</style>
	</head>
	<body style="background: #f2f3f4;">
		<div class="I-Pk" style="background: #f2f3f4;width: 100%;">
			<div class="h_tit">
				<h3 class="h_active">发动态</h3>
			</div>
			<div class="wrapper" id="wrapper">
				<div class="scrollbar" id="scrollbar">
			<div class="p-content show" >
				<div class="pl">
					<textarea name="dyContent" id="dyContent" rows="66" cols="30" placeholder="分享此时新鲜事..." maxLength=200></textarea>
					<p><span id="dyContentLength">0</span>/200</p>
				</div>
				<div class="pl">
					<div class="item">
						<div class="it-img">
							<img src=_oss_url+"${obj.imgpath}"/>
						</div>
						<div class="it-cont">
							<h3>${obj.title }</h3>
							<div class="bot">
								<span>${fns:fmt(obj.createTime,'yy/MM/dd HH:mm')}</span>
								<span>摘自:${classobj.name }</span>
							</div>
						</div>
					</div>
				</div>
					<input value="${obj.id }" type="hidden"   id="articleid">
				<form class="label" action="">
					<div class="lx">
						<label for="">对所有用户开放</label><input type="radio" name="visitType"  value="2" checked/>
					</div>
					<div class="lx">
						<label for="">仅对我的好友开放</label><input type="radio" name="visitType"  value="1" />
					</div>
				</form>
				<div style="height: 0.9rem;"></div>
			</div>
			</div>
			</div>
			<div class="submit active_A" id="submit_button">发布</div>
		</div>
		<script src="${path}/resource/js/upload.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
		var r = /^(0|[1-9]\d*)$/
		//document.getElementById("dyContent").wxImeEmojiFix(); // 原生用法
		function isNull( str ){
			if ( str == "" ) return true;
			var regu = "^[ ]+$";
			var re = new RegExp(regu);
			return re.test(str);
		}
		//判断是否有emoji
		function isEmojiCharacter(substring) {  
		    for ( var i = 0; i < substring.length; i++) {  
		        var hs = substring.charCodeAt(i);  
		        if (0xd800 <= hs && hs <= 0xdbff) {  
		            if (substring.length > 1) {  
		                var ls = substring.charCodeAt(i + 1);  
		                var uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;  
		                if (0x1d000 <= uc && uc <= 0x1f77f) {  
		                    return true;  
		                }  
		            }  
		        } else if (substring.length > 1) {  
		            var ls = substring.charCodeAt(i + 1);  
		            if (ls == 0x20e3) {  
		                return true;  
		            }  
		        } else {  
		            if (0x2100 <= hs && hs <= 0x27ff) {  
		                return true;  
		            } else if (0x2B05 <= hs && hs <= 0x2b07) {  
		                return true;  
		            } else if (0x2934 <= hs && hs <= 0x2935) {  
		                return true;  
		            } else if (0x3297 <= hs && hs <= 0x3299) {  
		                return true;  
		            } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d || hs == 0x3030  
		                    || hs == 0x2b55 || hs == 0x2b1c || hs == 0x2b1b  
		                    || hs == 0x2b50) {  
		                return true;  
		            }  
		        }  
		    }  
		}
			
			var localId = ""; //本地语音id
			var serverId=""; //服务器语音id
			var download_localId = ""; //下载后语音id
			var mediaSeconds = 0; //语音描述
			var mediaPrice = 0;//语音价格
			
			var t; //定时器
			var t_minute = 0; //计时分钟数
			var t_seconds = 0; // 计时秒数
			
			var startFlag = true;
			
			
			
			startFlag = true;
			$(function(){
				localId = ""; //本地语音id
				serverId=""; //服务器语音id
				download_localId = ""; //下载后语音id
				mediaSeconds = 0; //语音描述
				mediaPrice = 0;//语音价格
				t_minute = 0; //计时分钟数
				t_seconds = 0; // 计时秒数
				
				
				var flag = false;
				//initUploadPicFrame("uploadPicFrame1","pic","upload1");
				//initUploadPicFrame("uploadPicFrame2","pic","upload2");
				//initUploadPicFrame("uploadPicFrame3","pic","upload3");

				$("#dyContent").bind("input propertychange", function() {
					var len = $(this).val().length;
					$("#dyContentLength").text(len);
				});
				
				$("#mediaP").bind("input propertychange", function() {
					if(!r.test($("#mediaP").val())){
						$("#mediaP").val("");
					}
					if(isNaN($("#mediaP").val())){
						$("#mediaP").val("");
					}
				});
				
				/*发布  */
				$("#submit_button").click(function(){
					if(flag){
						return;
					}else{
						flag = true;
					}
					var dyContent=jQuery.trim($("#dyContent").val());
					
					if(isEmpty(dyContent)){
						alert_back("动态内容不能为空",function(){
							$("#dyContent").focus();
						});
						flag = false;
						return;
					}
					
					if(isEmojiCharacter(dyContent)){
						alert_back("动态内容不能含表情",function(){
							$("#dyContent").focus();
						});
						return;
					}
					
					var visitType = $("input[name='visitType']:checked").val();
					if(isEmpty(visitType)){
						alert_back("请选择公开类型",function(){
						});
						flag = false;
						return;
					}
					
					serverId='';
					mediaPrice=0;
					mediaSeconds = 0;
					var zjImgerJson ="";
					var articleid = $("#articleid").val();
					var url ="";
					if('${type}'=='1'){
						url='${path}/add/m/dynamic/insertDynamic.do';
					}else{
						url='${path}/add/m/dynamic/insertForwardDynamic.do';
					}
					zdy_ajax({
						url: url,
						data:{
							dyContent:$("#dyContent").val(),
							visitType:visitType,
							mediaSeconds:mediaSeconds,
							mediaId:serverId,
							mediaPrice:mediaPrice,
							zjImgerJson:zjImgerJson,
							articleid:articleid,
							fromDynamicId:'${fromDynamicId}'
						},
						success: function(data,output){
							flag = false;
							if(output.code == 0){
								alert_back(output.msg,function(){
									var last_url=window.localStorage.libraryzfurl;
									top.location.href=last_url;
								});
							}
						},
						error:function(e){
							//alert(e);
							flag = false;
						}
						});
					
					flag = false;
				});
			});

		</script>
	</body>

</html>