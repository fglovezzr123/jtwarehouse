<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0" name="viewport">

        <title id="title">评论</title>
        <link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css" />
		
        <link rel="stylesheet" href="${path}/resource/css/myFootPrintComment.css" />

    </head>
    
  
    <body>
        <div class="wrapper">
              <div class="print-comment active_A" onclick="my_comment(1)">
                    <div class="com1"></div>
                    <div>动态评论</div>
                    <br class="clear"/>
              </div>

              <div class="print-comment active_A" onclick="my_comment(2)">
                    <div id="com2"></div>
                    <div>资讯评论</div>
                    <br class="clear"/>
              </div>

              <div class="print-comment active_A" onclick="my_comment(3)">
                    <div id="com3"></div>
                    <div>话题评论</div>
                    <br class="clear"/>
              </div>

              <div class="print-comment active_A" onclick="my_comment(4)">
                    <div id="com4"></div>
                    <div>活动评论</div>
                    <br class="clear"/>
              </div>
              
              <div class="print-comment active_A" onclick="my_comment(5)" style="display:none">
                    <div id="com4"></div>
                    <div>合作评论</div>
                    <br class="clear"/>
              </div>

			<div class="print-comment active_A" onclick="my_comment(6)">
                    <div id="com4"></div>
                    <div>合作商洽评论</div>
                    <br class="clear"/>
              </div>
        </div>
    <script type="text/javascript">
		 var my_comment=function(flag){
			if(flag==1){
				self.location.href="${path}/m/comment/mydynamic_comment.do";
			}else if(flag==2){
				self.location.href="${path}/m/comment/mynews_comment.do";
			}else if(flag==3){
				self.location.href="${path}/m/comment/mytopic_comment.do";
			}else if(flag==4){
				self.location.href="${path}/m/comment/myactivity_comment.do";
			}else if(flag==5){
				self.location.href="${path}/m/comment/mybusinessDisscuss_comment.do";
			}else if(flag==6){
				self.location.href="${path}/m/comment/mybd_comment.do";
			}
		 }
	</script>
    </body>
</html>