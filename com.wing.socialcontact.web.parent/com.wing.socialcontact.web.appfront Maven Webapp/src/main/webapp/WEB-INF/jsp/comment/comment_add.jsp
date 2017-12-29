<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_upload.jsp"%>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="评论">
		<title>评论</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/comment.css" />
		<style>
		   .imagess{
		      width:1.3rem;
		      height:auto;
		    }
		</style>
	</head>

	<body style="background: #f2f3f4;>
		<div class="Z-register-login" >
		    <input id="fkId" name="fkId" value="${comment.fkId}"  type="hidden" />
		    <input id="cmeType" name="cmeType" value="${comment.cmeType}"  type="hidden" />
			<div class="gsj">
				<textarea id="cmeDesc" name="cmeDesc"  placeholder="评论不得超过200字" class="nerong"  maxLength=200 onkeyup="value=clearYEmoil1(value)" onpaste="value=clearYEmoil1(value)" oncontextmenu="value=clearYEmoil1(value)" >${comment.cmeDesc}</textarea>
				<span id="dyContentLength">0/200</span>
			</div>
			<div class="p-head">上传照片</div>
			<div class="photo">
				<!-- <div class="photo-oper" id="photo_list1">
					<div class="addimagess active_A" onclick="doSelectPic('uploadPicFrame1','pic1');" id="file_button_1"></div>
				    <iframe id="uploadPicFrame1" src="" style="display:none;"></iframe>
				</div> -->
			    <div class="photo-oper" id="photo_list1">
					<div class="addimagess active_A" onclick="doSelectPic1();" id="file_button_1"></div>
				    <iframe id="uploadPicFrame1" src="" style="display:none;"></iframe>
				</div> 
			</div>
			<br>	
			
		</div>
		<div class="submit active_A" onclick="comment_submit();">评论</div>
		<script type="text/javascript">
			  $('textarea').on('click', function () {
				 var interval = setTimeout(function() {
					    document.body.scrollTop = document.body.scrollHeight
					}, 100)
	    	  });
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
				var cmeDesc=$("#cmeDesc").val();
				if(cmeDesc == null || cmeDesc == undefined || cmeDesc == ''){
					
					alert_back("评论内容不能为空",function(){
						$("#cmeDesc").focus();
					});
					return;
				}
				
				if(isNull(cmeDesc)){
					alert_back("评论内容不能为空",function(){
						$("#cmeDesc").focus();
					});
					return;
				}
				//if(isEmojiCharacter(cmeDesc)){
				//	alert_back("评论内容不能含表情",function(){
				//		$("#cmeDesc").focus();
				//	});
				//	return;
				//}
				var fkId=$("#fkId").val();
				var cmeType=$("#cmeType").val();
				var imgUrl = "";
				$('.up_pic_img').each(function(){
					imgUrl = $(this).attr("srcpath");
				});
				
			
				zdy_ajax({
					url: '${path}/add/m/comment/addComment.do',
					data:{
						cmeDesc:cmeDesc,
						imgUrl:imgUrl,
						fkId:fkId,
						cmeType:cmeType
					},
					success: function(data,output){
						if(output.code == 0){
								//var last_url="${path}/"+${lastUrl};
								//var last_url="${path}/m/comment/comment_list.do";
								//if(parent){
								//	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
								//	parent.reload();
								//	//parent.layer.close(index);
								//}
								
								///window.parent.location.reload(); 
                               self.location=document.referrer;
								///self.location.href="";
						
						}
					},
					error:function(e){
						//alert(e);
					}
				});
			}
			
			function doSelectPic1() {
				zdy_chooseImg(function(data,res){
		    	 	if(res.code == 0){
		    	 		var imgHtml = "<img  srcpath='"
							+ data.pic_path
							+ "' src='"
							+ data.img_url
							+ "' class='up_pic_img' style=\"height:1.3rem\"/>";
						var str = '<div class="imagess">' + imgHtml
							+ '<i onclick="delPic(this);"></i></div>';
						$(str).insertBefore("#file_button_1");
						var imgSize = $("#photo_list1").find(
								"div[class='imagess']").length;
						if(imgSize == 1){
							$("#file_button_1").hide();
						}
		    	 	}
		    	 },'comment');
			}
			
			function doSelectPic(frameId,pic) {
				var imgSize = 0;
				if("uploadPicFrame1" == frameId){
					imgSize=$("#photo_list1").find("div[class='imagess']").length;
					if(imgSize > 0){
						alert("只能上传一张图片");
						return;
					}
				}
				$("#"+pic, $("#"+frameId)[0].contentWindow.document).click();
			}
			
			//上传完成后回调的方法
			function picUploadCallback(data,frameId,picId,formId){
				if (data.returnCode == "0"){
					var picUrl = data.picPath;
					if(picUrl.length > 0){
						var imgHtml = "<img  srcpath='"+data.picPath+"' src='"+data.img_url+"' class='up_pic_img' style='height:1.3rem'/>";
						var str='<div class="imagess">'+imgHtml+'<i onclick="delPic(this);"></i></div>';
						if("upload1" == formId){
							$(str).insertBefore("#file_button_1");
							var imgSize=$("#photo_list1").find("div[class='imagess']").length;
							if(imgSize == 1){
								$("#file_button_1").hide();
							}
						}
					}else{
						//alert("上传失败,请稍后再试");
					}
				}else{
					if(data.msg != ""){
						//alert(data.msg);
					}else{
						//alert("上传失败,请稍后再试");
					}
				}
				initUploadPicFrame(frameId,picId,formId);
			}
			
			function delPic(data){
				$(data).parent().parent().find("div[id^='file_button_']").show();
				$(data).parent().remove();
			}
			
			
			function Check(txt)
			{
			    TextCount=txt.value.length;  
			    $("#dyContentLength").text(TextCount+'/200');   
			    if(TextCount>=200){
			    	alert("评论内容不能大于200字");
			    	return;
			    }
			//获取文本框的长度
			    ///$("#dyContentLength").text(TextCount+'/200');   
			//将长度显示在div中
			}
			
			
			$("#cmeDesc1").change( function() {
				var len = getByteLen($(this).val());
				$("#dyContentLength").text(len+'/200');
			});
			
			function getByteLen(val) {
	            var len = 0;
	            for (var i = 0; i < val.length; i++) {
	                var a = val.charAt(i);
	                if (a.match(/[^\x00-\xff]/ig) != null) {
	                    len += 1;
	                }
	                else {
	                    len += 1;
	                }
	            }
	            return len;
	        }
			

		
		</script>
	</body>

</html>