<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0" name="viewport">

        <title id="title">活动评论</title>
        <link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css" />
		
        <link rel="stylesheet" href="${path}/resource/css/modifyActiveInfo.css" />

    </head>
    
  
    <body>
        <div class="wrapper">
           <img class="meeting-poster" src="${path}/resource/img/images/text-1.png"/> 
           <div class="modify-box">
               <div class="modify-head">北京第二节有机生态农产品交流会</div>
               <p>北京第二节有机生态农产品交流会北京第二节有机生态农产品交流会北京第二节有机生态农产品交流会北京第二节有机生态农产品交流会
                  北京第二节有机生态农产品交流会北京第二节有机生态农产品交流会北京第二节有机生态农产品交流会北京第二节有机生态农产品交流会
               </p>

               <div class="modify-photo">
                    <div>图片</div>
                    <div class="active_A"></div>
                    <br class="clear"/>
               </div>

               <div class="word-num">100/1000</div>
           </div> 

           <div class="modify-option" >
                <div class="active_A">
                     <div>开始时间</div>
                     <div>2017-03-19  14：00：00</div>
                     <br class="clear"/>
                </div>

                 <div class="active_A">
                     <div>结束时间</div>
                     <div>2017-03-19  14：00：00</div>
                     <br class="clear"/>
                </div>

                 <div class="active_A">
                     <div>活动地点</div>
                     <div>北京XXXX中心</div>
                     <br class="clear"/>
                </div>
                <div class="active_A">
                     <div>报名截止</div>
                     <div>2017-03-19  14：00：00</div>
                     <br class="clear"/>
                </div>

                <div class="active_A">
                     <div>活动收费</div>
                     <div>
                         <span>99元</span>
                         <div class="modify-btn"></div>
                    </div>
                     <br class="clear"/>
                </div>
                 <div class="active_A modifing" style="border-top:0.1rem solid #f0f0f0;">
                     <div>对所有用户公开</div>
                     <div class="modify-yeah">
                          .
                    </div>
                     <br class="clear"/>
                </div>

                <div class="active_A modifing">
                     <div>仅好友可见</div>
                     <div class="modify-no">
                          .
                    </div>
                     <br class="clear"/>
                </div>
           </div>  
        </div>
        <div class="modify-btn">
             <div class="active_A">取消</div>
             <div class="active_A">保存</div>
             <br class="clear"/>
        </div>
    </body>
</html>