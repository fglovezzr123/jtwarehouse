<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0" name="viewport">

        <title id="title">话题</title>
        <link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css" />
		
        <link rel="stylesheet" href="${path}/resource/css/myfootPrint-topic.css" />

    </head>
    
  
    <body>
        <div class="wrapper">
              <div class="Print-tabBox">
                   <div class="current">我参与的话题</div>
                   <div>我发布的话题</div>
                   <div>我收藏的话题</div>
                   <br class="clear"/>
              </div>

              <div class="print-conetnt">
                    <div class="print-conetnt-head">
                       <div>
                            <div class="userIcon"></div>
                       </div>
                       <div>
                           <h1>
                              城厢镇
                              <span>2016-03-16</span>
                           </h1>
                           <p>CEO/北京环球技术有限责任公司</p>
                       </div>
                       <br class="clear"/>
                    </div>

                    <div class="topic-discussion">
                        <h2>创新是企业存在的根本吗？</h2>
                        <div>
                             <div>红方观点 : </div>
                             <div>观点创新是企业存在的根本吗创新是企业存在的根本吗创新是企业存在的根本吗创新是企业存在的根本吗创新是企业存在的根本吗创新是企业存在的根本吗创新是企业存在的根本吗</div>
                             <br class="clear"/>
                        </div>
                        <div>
                             <div class="blue-side">蓝方观点 : </div>
                             <div>观点创新是企业存在的根本吗创新是企业存在的根本吗创新是企业存在的根本吗创新是企业存在的根本吗创新是企业存在的根本吗创新是企业存在的根本吗创新是企业存在的根本吗</div>
                             <br class="clear"/>
                        </div>
                    </div>




              </div>

              <div class="print-witchSide">
                          <div class="active_A">我选红方观点</div>
                          <div class="active_A">我选蓝方观点</div>
                          <br class="clear"/>
              </div>
        </div>

    </body>
</html>