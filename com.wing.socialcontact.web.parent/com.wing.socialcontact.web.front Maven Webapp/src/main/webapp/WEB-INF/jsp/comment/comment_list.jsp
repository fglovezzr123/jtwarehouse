<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="注册">
		<title>评论详情</title>
		<link rel="stylesheet" type="text/css" href="<%=path%>/resource/css/commentList.css" />
	</head>
	<body>
		<div class="Z-register-login" style="background: #f2f3f4;width: 100%;height: 100%;">
		     <input id="pid" name="pid" value="${comment.id}"  type="hidden" />
			<div class="t-cont">
				<div class="msgBox">
					<div class="msg-l" onclick="open_user_center(${user.id})" style="background:url(${user.head_portrait}) no-repeat center;background-size:0.73rem 0.73rem;border-radius:50%">
					    <%-- <img src="${user.head_portrait}"> --%>
						<!--背景图-->
					</div>
					<div class="msg-r">
						<div class="t">
							<h2>${user.nickname}</h2>
					      <c:if test="${isThumbup}">
							<span  style="background:url(${path}/resource/img/icons/bad.jpg) no-repeat left center;background-size:0.32rem 0.30rem" class="time tuobiao" onclick="thumbup(this,'${comment.id}')">${count}</span>
						  </c:if>
					      <c:if test="${!isThumbup}">
							<span  style="background:url(${path}/resource/img/icons/good.jpg) no-repeat left center;background-size:0.32rem 0.30rem" class="time tuobiao" onclick="thumbup(this,'${comment.id}')">${count}</span>
						  </c:if>
						</div>
						<!-- <p>08-23&nbsp;2017-3-15&nbsp;&nbsp;CEO/北京了几哟西按公司</p> -->
						<p><fmt:formatDate value='${comment.createTime}' pattern='yyyy-MM-dd  HH:mm '/>&nbsp;&nbsp;${user.jobName}/${user.industryName}</p> 
					</div>
				</div>
				<h3>${comment.cmeDesc}</h3>
				<c:if test="${not empty comment.imgUrl}">
				<div class="imgBox_1">
					<img onclick="showimgs2(this)" src="${ossurl}${comment.imgUrl}" style="width:100%" />
				</div>
				</c:if>
			</div>
			<div class="mgtop">
			</div>
			<div style="padding-bottom:1rem">
			   <c:forEach  var="subComment" items="${subCommentList}" >
			  <div class="msgBox1 ">
					<div class="msg-l"  onclick="open_user_center(${subComment.userId})" style="background:url(${subComment.head_portrait}) no-repeat center;background-size:0.73rem 0.73rem;border-radius:50%">
						<%--  <img src="${subComment.head_portrait}"><!--背景图--> --%>
					</div>
					<div class="msg-r">
						<div class="t">
							<h2><b>${subComment.formname}</b>&nbsp;回复&nbsp;<i>${user.nickname}</i>&nbsp;:</h2>
							<span class="time"><fmt:formatDate value='${subComment.createTime}' pattern='yyyy-MM-dd HH:mm '/></span>
						</div>
						<h3>${subComment.cmeDesc} </h3>
					</div>

				</div>
			</c:forEach>
			</div>
			
				<div class="shuru">
					<input type="text" name="huihu" id="huihu" value="" maxLength=200 placeholder="请回复"  onblur="value=clearYEmoil(value)" onpaste="value=clearYEmoil(value)" oncontextmenu="value=clearYEmoil(value)" />
					<button onclick="comment_submit();">回复</button>
				</div>
		</div>
		<script type="text/javascript">
		var bfscrolltop = document.body.scrollTop;
			$('#huihu').on('focus',function(event){      
		       //自动反弹 输入法高度自适应
		        setTimeout(function(){
		        	//alert("s");
		        	this.scrollIntoViewIfNeeded();
		        	//this.scrollIntoView(true);
		        },500);
		       $(this).css("margin-bottom","0.1rem")
		       
		           document.body.scrollTop = document.body.scrollHeight;//获取焦点后将浏览器内所有内容高度赋给浏览器滚动部分高度
		       
		    });
			$('#huihu').on('blur',function(event){
				
				 
			        document.body.scrollTop = bfscrolltop;
			}) 
			
			 function showimgs2(obj){	
			 		primgs = [];
			 		primgs.push($(obj).attr("src"));
			 		wx.previewImage({
			 		    current: $(obj).attr("src"), // 当前显示图片的http链接
			 		    urls: primgs // 需要预览的图片http链接列表
			 		});
			 }
			 
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
			
			var comment_submit=function(){
				var cmeDesc=$("#huihu").val();
				if(isEmpty(cmeDesc)){
					alert_back("回复内容不能为空",function(){
						$("#cmeDesc").focus();
					});
					return;
				}
                if(cmeDesc == null || cmeDesc == undefined || cmeDesc == ''){
					
					alert_back("回复内容不能为空",function(){
						$("#cmeDesc").focus();
					});
					return;
				}
				
				if(isNull(cmeDesc)){
					alert_back("回复内容不能为空",function(){
						$("#cmeDesc").focus();
					});
					return;
				}
				///if(isEmojiCharacter(cmeDesc)){
				///	alert_back("评论内容不能含表情",function(){
				///		$("#cmeDesc").focus();
				///	});
				///	return;
				//}
				var pid=$("#pid").val();
				zdy_ajax({
					url: '${path}/add/m/comment/addComment.do',
					data:{
						cmeDesc:cmeDesc,
						parentId:pid,
						cmeType:'${comment.cmeType}'
					},
					success: function(data,output){
						if(output.code == 0){
							  self.location=document.referrer;
						}
					},
					error:function(e){
						//alert(e);
					}
				});
			}
			
			//查看他人主页
			var open_user_center=function(userId){
				self.location.href="${path}/m/my/personal_home2.do?userId="+userId;
			}
			
			function Check(txt)
			{
				TextCount = txt.value.length;  
			    //alert(TextCount)
			//获取文本框的长度
			    if(TextCount >= 200){
			    	alert("回复内容不能大于200字")
			    }
			//将长度显示在div中
			}
			
			
			var thumbup=function(obj,_id){
				var a=$(obj).html();
				zdy_ajax({
					url: "${path}/m/comment/Thumbup.do",
					data:{
						id:_id,
					},
					success: function(data,res){
						if(res.code == 0){
							if(res.msg==0){
								$(obj).css({"background":"url(${path}/resource/img/icons/bad.jpg) no-repeat left center","background-size":"0.32rem 0.30rem"}); 
								$(obj).html(a*1+1);
								//$(obj).attr('isThumbup',true);
							}else{
								confirm("是否取消赞？",function(t){
									if(t == 1){
										zdy_ajax({
											url: "${path}/m/comment/cancelThumbup.do",
											data:{
												id:_id,
											},
											success: function(data,res){
												if(res.code == 0){
													if(res.msg==0){
														$(obj).css({"background":"url(${path}/resource/img/icons/good.jpg) no-repeat left center","background-size":"0.32rem 0.30rem"});
														$(obj).html(a*1-1);
														//$(obj).attr('isThumbup',false);
													}else{
														
													}
												}
											},
										    error:function(e){
											   //alert(e);
										    }
										}); 	
									}
								});
								//alert("你已点赞");
							}
						}
					},
				    error:function(e){
					   //alert(e);
				    }
				}); 
				
				//$(obj).find("b").html(a*1+1);
			}
			
			
			
			
		</script>
	</body>

</html>