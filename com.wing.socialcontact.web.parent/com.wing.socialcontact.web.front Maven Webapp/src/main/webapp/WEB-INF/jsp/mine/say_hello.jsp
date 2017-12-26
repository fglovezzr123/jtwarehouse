<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="留言">
		<title>打招呼</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/leaveMsg.css" />
	</head>
	
	<body style="background: #f2f3f4;">
		<div class="msg">
			<textarea id ="askmessage" name="" rows="" cols="" placeholder="请输入请求说明"></textarea>
			<p id="num_txt1">0/200</p>
		</div>
		<div class="pst">
			<div class="M-footer active_A" onclick="say_hello();">
				完成
			</div>
		</div>
		
		<script type="text/javascript">
			var friendUser="${userId}";
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
			var say_hello=function(){
				var askmessage=$("#askmessage").val();
         		if(askmessage == null || askmessage == undefined || askmessage == ''){
					
					alert_back("请输入打招呼内容",function(){
						$("#askmessage").focus();
					});
					return;
				}
				
				if(isNull(askmessage)){
					alert_back("请输入打招呼内容",function(){
						$("#askmessage").focus();
					});
					return;
				}
				$.ajax({
					url: "${path}/m/my/addusersgreetings.do",
					type : 'post',
					dataType : "json",
					data:{
						friendUser:friendUser,
						askmessage:askmessage
					},
					success: function(data,res){
			           console.log(res)
						if(res == "success"){
							alert_back("打招呼发送成功",function(){
							    history.go(-1);
			  				})
						}
					},
				    error:function(e){
					   //alert(e);
				    }
				}); 
			}
			
			$(function(){
				   $('#askmessage').on('input',function(){
				       var txtval = $('#askmessage').val().trim().length;
				        var str = parseInt(200-txtval);
				        /*   console.log(str); */
				        if(txtval>=200){
				          txtval=200
				        }
				        if(str > 0 ){
				          $('#num_txt1').html(txtval+'/200');
				        }else{
				          $('#num_txt1').html('剩余可输入0字');
				          $('#askmessage').val($('#askmessage').val().substring(0,200)); //这里意思是当里面的文字小于等于0的时候，那么字数不能再增加，只能是600个字
				        }
				        //console.log($('#num_txt').html(str));
				    });
				})
			
			
			
			
			
		</script>
	</body>

</html>