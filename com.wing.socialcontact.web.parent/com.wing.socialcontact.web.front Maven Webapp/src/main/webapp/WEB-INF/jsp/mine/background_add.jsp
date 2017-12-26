<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_upload.jsp"%>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="注册">
		<title></title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/comment.css" />
		<script type="text/javascript">
			var _path="${path}";
		</script>
	</head>

	<body>
		<div class="Z-register-login" style="background: #f2f3f4;width: 100%;height: 100%;">
			<div class="p-head">上传照片</div>
			<div class="photo">
				<div class="photo-oper" id="photo_list1">
					<div class="addimagess active_A" onclick="doSelectPic1();" id="file_button_1"></div>
				    <iframe id="uploadPicFrame1" src="" style="display:none;"></iframe>
				</div>
			</div>
		</div>
		<div class="submit active_A" onclick="bg_submit();">上传</div>
		<script src="${path}/resource/js/libs/public.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
		    ////initUploadPicFrame("uploadPicFrame1","pic1","upload1");
			var imgUrl = "";
			var bg_submit=function(){
				
				$('.up_pic_img').each(function(){
					imgUrl = $(this).attr("srcpath");
				});
				
				if(isEmpty($.trim(imgUrl))){
					layer.msg("请上传图片")
					return;
				}
				
				zdy_ajax({
					url: '${path}/m/my/add/addusers.do',
					data:{
						homepagePic:imgUrl
					},
					success: function(data,output){
						if(output.code == 0){
							self.location=document.referrer;
						}
					},
					error:function(e){
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
		    	 },"usercenter");
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
						var imgHtml = "<img  srcpath='"+data.picPath+"' src='"+data.img_url+"' class='up_pic_img' style='width:240px; height:160px;'/>";
						var str='<div class="imagess">'+imgHtml+'<i onclick="delPic(this);"></i></div>';
						if("upload1" == formId){
							$(str).insertBefore("#file_button_1");
							var imgSize=$("#photo_list1").find("div[class='imagess']").length;
							if(imgSize == 1){
								$("#file_button_1").hide();
							}
						}
					}else{
						alert("上传失败,请稍后再试");
					}
				}else{
					if(data.msg != ""){
						alert(data.msg);
					}else{
						alert("上传失败,请稍后再试");
					}
				}
				initUploadPicFrame(frameId,picId,formId);
			}
			
			function delPic(data){
				$(data).parent().parent().find("div[id^='file_button_']").show();
				$(data).parent().remove();
			}
		</script>
	</body>

</html>