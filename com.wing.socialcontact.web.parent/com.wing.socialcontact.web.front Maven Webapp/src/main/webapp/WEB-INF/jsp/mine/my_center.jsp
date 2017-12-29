<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="个人中心" />
		<title>个人中心</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/person.css?v=${sversion}"/>
	</head>
	<body>
		<div class="B-friendInfo" style="background: #f2f3f4;padding-bottom:.88rem">
			<div class="info">
				<div class="info-l active_A">
					<c:choose>
						<c:when test="${not empty tjyuser.headPortrait }">
							<c:choose>
								<c:when test="${fn:indexOf(tjyuser.headPortrait,'http') ne -1 }">
									<img id="user-icon" alt="" src="${tjyuser.headPortrait}"/>
								</c:when>
								<c:otherwise>
									<img id="user-icon" alt="" src="${ossurl}${tjyuser.headPortrait}"/>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<img id="user-icon" alt="" src="${path}/resource/img/icons/weixinHeader.jpg"/>
						</c:otherwise>
					</c:choose>
					
				</div>
				<div class="person">
						<p onclick="my_level()">
							<b>${tjyuser.nickname}</b>
						    <span style="margin-left:0.1rem;margin-top:.1rem;">
								  <c:choose>
									<c:when test="${tjyuser.isRealname eq 1 }">
										${tjyuser.job}
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${tjyuser.reconStatus eq 2 }">
												${tjyuser.job}
											</c:when>
											<c:otherwise>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							 ${user.level}</span>
							 <c:choose>
									<c:when test="${tjyuser.honorFlag == 'honor_001' }">
										<span style="margin-left: .1rem;"><img src="${path}/resource/img/icons/tjjr.png"  class="tj" /></span>
									</c:when>
									<c:when test="${tjyuser.honorFlag == 'honor_002' }">
										<span style="margin-left: .1rem;"><img src="${path}/resource/img/icons/tjyq.png"  class="tj" /></span>
									</c:when>
									<c:when test="${tjyuser.honorFlag == 'honor_003'}">
										<span style=" margin-left: .1rem;"><img src="${path}/resource/img/icons/tjhb.png"  class="tj" /></span>
									</c:when>
									<c:when test="${tjyuser.honorFlag == 'honor_004'}">
										<span style=" margin-left: .1rem;"><img src="${path}/resource/img/icons/tjfws.png"  class="tj" /></span>
									</c:when>
								</c:choose>	 
						</p>
						<c:choose>
							<c:when test="${tjyuser.isRealname eq 1 }">
								<p class="company">${tjyuser.comName}</p>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${tjyuser.reconStatus eq 2 }">
										<p class="company">${tjyuser.comName}</p>
									</c:when>
									<c:otherwise>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
						
					</div>
				<div class="info-r active_A" onclick="edit_person();">
					<i class="iconfont">&#xe606;</i>
					<span>编辑</span>
				</div>
			</div>
			<div class="per active_A my" onclick="my_home();">
				<div class="per-l" style="width:3rem;text-align:left">
					我的主页
				</div>
				<div class="per-r" >
					<span class="per-index">访问量<fmt:formatNumber type="number" value="${tjyuser.visitQuantity} " maxFractionDigits="0"/></span>
					<i class="iconfont">&#xe605;</i>
				</div>
			</div>
			<div class="per active_A my" onclick="my_kf();">
				<div class="per-l" style="width:3rem;text-align:left">
					我的客服
				</div>
				<div class="per-r"  >
					<span class="per-index"></span>
					<i class="iconfont">&#xe605;</i>
				</div>
			</div>
			<c:if test="${not empty userhonors}">
				<div class="sz">
					<div class="szs">
						<span>我的勋章</span>
					</div>
					<div class="simgBox">
						<c:forEach  var="userhonor" items="${userhonors}" > 
						   <img src="${ossurl}${userhonor.pic_path}" onclick="my_honor_info('${userhonor.honor_id}');"/> 
						</c:forEach>
					</div>
				</div>
			</c:if>
			<!-- <div class="per contentPer active_A">
				<div class="per-l" onclick="my_home();">
					<i class="iconfont" style="font-size:0.6rem;margin-left:-0.14rem;">&#xe661;</i>
					<span>个人主页</span>
				</div>
				<div class="per-r">
					<i class="iconfont">&#xe605;</i>
				</div>
			</div> -->
			<!-- <div class="per contentPer active_A" onclick="mall_ddzx();">
				<div class="per-l" >
					<i class="iconfont">&#xe61d;</i>
					<span>商城订单中心</span>
				</div>
				<div class="per-r" >
					<i class="iconfont">&#xe605;</i>
				</div>
			</div> -->
			<div class="per contentPer active_A" onclick="personal_wallet();">
				<div class="per-l" >
					<i class="iconfont">&#xe615;</i>
					<span>我的钱包</span>
				</div>
				<div class="per-r" >
					<i class="iconfont">&#xe605;</i>
				</div>
			</div>
			<div class="per contentPer active_A" onclick="invite_record();">
				<div class="per-l" >
					<i class="iconfont">&#xe615;</i>
					<span>邀请好友</span>
				</div>
				<div class="per-r" >
					<i class="iconfont">&#xe605;</i>
				</div>
			</div>
			<div class="per contentPer active_A" onclick="open_hzb();">
				<div class="per-l" >
					<i class="iconfont">&#xe615;</i>
					<span>开通互助宝</span>
				</div>
				<div class="per-r" >
					<i class="iconfont">&#xe605;</i>
				</div>
			</div>
			<div class="per contentPer active_A" onclick="personal_recon();">
				<div class="per-l" >
					<i class="iconfont">&#xe615;</i>
					<span>认证信息</span>
				</div>
				<div class="per-r">
					<c:choose>
						<c:when test="${tjyuser.isRealname eq 1 }">
							<span class="per-index">认证通过</span>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${tjyuser.reconStatus eq 1 }">
									<span class="per-index">认证中</span>
								</c:when>
								<c:when test="${tjyuser.reconStatus eq 2 }">
									<span class="per-index">认证通过</span>
								</c:when>
								<c:when test="${tjyuser.reconStatus eq 3 }">
									<span class="per-index">认证不通过</span>
								</c:when>
								<c:otherwise>
									<span class="per-index">未认证</span>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
					<i class="iconfont">&#xe605;</i>
				</div>
			</div>
			
			<div class="per conten active_A" onclick="my_setting();">
				<div class="per-l" style="width:3rem;" >
					<i class="iconfont">&#xe63a;</i>
					<span>设置</span>
				</div>
				<div class="per-r" >
					<i class="iconfont">&#xe605;</i>
				</div>
			</div>
			
			<div class="per  active_A" style="text-align:left;display:block;line-height:0.76rem;">
			           我的足迹
				<!-- <div class="per-l">
					<span>我的足迹</span>
				</div> -->
				<!-- <div class="per-r">
					<i class="iconfont">&#xe605;</i>
				</div> -->
			</div>
			<div class="fimgBox">
				<div class="imgbox"  onclick="myfoot(1);">
					<img src="${path}/resource/img/icons/my_03.png"/>
					<span>活动</span>
				</div>
				<div class="imgbox" onclick="myfoot(2);">
					<img src="${path}/resource/img/icons/my-05_03.png"/>
					<span>话题</span>
				</div>
				<div class="imgbox" onclick="myfoot(3);">
					<img src="${path}/resource/img/icons/my_09_03.png"/>
					<span>项目</span>
				</div>
				<div class="imgbox" onclick="myfoot(4);">
					<img src="${path}/resource/img/icons/my_07_03.png"/>
					<span>评论</span>
				</div>
				<div class="imgbox" onclick="myfoot(6);">
					<img src="${path}/resource/img/icons/xq.png" class="c"/>
					<span>合作</span>
				</div>
				
				 <div class="imgbox" onclick="myfoot(7);">
					<img src="${path}/resource/img/icons/my_05_03.png"/>
					<span>悬赏</span>
				</div> 
				<div class="imgbox" onclick="myfoot(8);">
					<img src="${path}/resource/img/icons/wk.png" class="c"/>
					<span>文库</span>
				</div>
				<div class="imgbox" onclick="myfoot(9);">
					<img src="${path}/resource/img/icons/hy1.png" class="c1"/>
					<span>会议</span>
				</div>
				<div class="imgbox" onclick="myfoot(10);">
					<img src="${path}/resource/img/icons/hy1.png" class="c1"/>
					<span>直播</span>
				</div>
				<!-- 店铺隐藏 -->
				<div class="imgbox" ><!-- onclick="myfoot(5);" -->
				<%-- 	<img src="${path}/resource/img/icons/dipu.png" class="c"/>
					<span>店铺</span> --%>
				</div>
				<!-- <div class="imgbox">
				</div> -->
			</div>
			<jsp:include page="../commons/include_footer.jsp" >
				<jsp:param name="menuid" value="4" />
			</jsp:include>
		</div>
	    <script type="text/javascript">
			 var edit_person=function(){
				self.location.href="${path}/m/my/person_setting.do";
			 }
			 var my_home=function(){
				self.location.href="${path}/m/my/personal_home.do";
			 }
			 var my_level=function(){
				 if(!screenflag){
					self.location.href="${path}/m/my/my_level_info.do";
				 }
			 }
			 var my_kf=function(){
				self.location.href="${path}/m/my/leaveMsgPage2.do";
			 }
			 var personal_wallet=function(){
				self.location.href="${path}/m/my/personal_wallet.do";
			 }
			 var mall_ddzx=function(){
				self.location.href=home_path+"/phoneh5_zh/aboutMe.html";
			 }
			 var my_honor_info=function(id){
					self.location.href="${path}/m/my/my_honor_info.do?id="+id;
			 }
			 var invite_record=function(){
				 self.location.href="${path}/m/my/invite_record.do";
			 }
			 var open_hzb=function(){
				var hzbOpenFlag="${user.hzbOpenFlag}";
				if(hzbOpenFlag == 2){
       				alert("您的互助宝账户已停用，请联系客服");
       				return;
       			}
				if(zfflag){
				self.location.href="${path}/m/hzb/first.do";
				}else{
       				layer.msg("该功能暂未开通");
       			}
			 }
			 var personal_recon=function(){
				self.location.href="${path}/m/my/reconPage.do";
			 }
			 var my_setting=function(){
				self.location.href="${path}/m/my/my_setting.do";
			 }
			 var myfoot=function(flag){
			    if(flag==1){
					self.location.href="${path}/m/my/myfootPrint_activity.do";
				}else if(flag==2){
					self.location.href="${path}/m/topic/myCenterTopicPage.do";
				}else if(flag==3){
					self.location.href="${path}/m/my/myfootPrint_project.do";
				}else if(flag==4){
					self.location.href="${path}/m/my/myfootPrint_comment.do";
				}else if(flag==5){
					jdp();
				}else if(flag==6){
					self.location.href="${path}/m/business/selMyHzPage.do";
				}else if(flag==7){
				//	alert("暂无数据");
				   self.location.href="${path}/m/reward/myCenterRewardPage.do";
				}else if(flag==8){
					self.location.href="${path}/m/my/myfootPrint_textHouseware.do";
				}else if(flag==9){
					self.location.href="${path}/m/my/myfootPrint_meeting.do";
				}else if(flag==10){
					self.location.href="${path}/library/m/live/myfootPrint_live.do";
				}
					
			 }
			 
			 function jdp(){
				 $.ajax({
					url: home_path+"/mobile/seller/my_store_info",
					cache: false,
					dataType:"json",
				    data:{user_id:"<%=userid %>"}, 
					success: function(data){
						 if(data.status&&data.statusStr==null){
                             if (data.data.storeStatus==1) {
                               //店铺正在审核;
                                window.location.href=home_path+"/phoneSaler/identityAudit.html?saleShop=2";
                             }else if(data.data.storeStatus==-1){
                               //审核失败;
                                window.location.href=home_path+"/phoneSaler/salerauditNothing.html?saleShop=2";
                             }else if(data.data.storeStatus==3){
                               alert("违规关闭");
                               return;
                             }else if(data.data.storeStatus==2){
                               window.location.href=home_path+"/phoneSaler/salermyShop.html?type=2&saleShop=2";
                             } 

                         }else if(data.status==false&&data.statusStr<0){
                             //还未开店铺：
                            window.location.href=home_path+"/phoneSaler/freeShop.html?saleShop=2"; 
                           
                         }
					}
				});
			 }
		</script>
	</body>
	

</html>