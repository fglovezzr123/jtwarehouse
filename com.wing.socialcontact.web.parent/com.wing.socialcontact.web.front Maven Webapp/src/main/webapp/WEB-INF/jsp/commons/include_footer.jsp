<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String menuid="";
if(request.getParameter("menuid")!=null){
	menuid=request.getParameter("menuid");
}
%>
			<div class="footer">
				<a href="${path}/m/sys/index.do" class="footer_a <%if("1".equals(menuid)){%>f-active<%}%>">
					<img src="<%if("1".equals(menuid)){%>${path}/resource/img/icons/ys01.png<%}else{%>${path}/resource/img/icons/ys00.png<%}%>" class="iconImg"/>
					<span>首页</span>
				</a>
				<a href="${path}/wx/businessFriend/businessFriend.do" class="footer_a <%if("2".equals(menuid)){%>f-active<%}%>">
					<img src="<%if("2".equals(menuid)){%>${path}/resource/img/icons/ys02.png<%}else{%>${path}/resource/img/icons/ys2.png<%}%>" class="iconImg"/>
					<span>商友</span>
				</a>
				<a href="javascript:void(0)"  onclick="open_pub()" class="footer_a" style='position:relative'>
				  <img class="yunshang" src="${path}/resource/img/icons/indexJ.png"/>
				        <dl onclick="openIndexUrl(1)">
							<dt><img src="${path}/resource/img/pop/ys_jia_03.png"></dt>
							<dd>活动</dd>
						</dl>
						<dl onclick="openIndexUrl(2)">
							<dt><img src="${path}/resource/img/pop/ys_jia_05.png"></dt>
							<dd>合作</dd>
						</dl>
						<dl onclick="openIndexUrl(3)">
							<dt><img src="${path}/resource/img/pop/ys_jia_07.png"></dt>
							<dd>动态</dd>
						</dl>
						<dl onclick="openIndexUrl(4)">
							<dt><img src="${path}/resource/img/pop/ys_jia_09.png"></dt>
							<dd>话题</dd>
						</dl>
						<dl onclick="openIndexUrl(5)">
							<dt><img src="${path}/resource/img/pop/ys_jia_01.png"></dt>
							<dd style='width:1.4rem;margin-left:-.12rem'>悬赏</dd>
						</dl>
				</a>
				<a href="${path}/m/message/messagePage.do" class="footer_a <%if("3".equals(menuid)){%>f-active<%}%>">
					<img src="<%if("3".equals(menuid)){%>${path}/resource/img/icons/ys03.png<%}else{%>${path}/resource/img/icons/ys3.png<%}%>" class="iconImg"/>
					<span>消息</span>
				</a>
				<a href="${path}/m/my/my_center.do" class="footer_a <%if("4".equals(menuid)){%>f-active<%}%>">
					<img src="<%if("4".equals(menuid)){%>${path}/resource/img/icons/ys04.png<%}else{%>${path}/resource/img/icons/ys4.png<%}%>" class="iconImg"/>
					<span>我的</span>
				</a>
			</div>
			<div id="pop">
				<div id="public_info1">
					<div class="pop_box" style="display: none;" id="is_recon_true">
						 <div class="pop_box_p">您已成为天九共享认证用户<br/>私人秘书电话：<label id="kfTelephone"></label></div> 
						<div class="pop_box_tutton">
							<button type="button" class="bj_green" name="dhrz_button" value="${kfTelephone}">立即拨打</button>
						</div>
					</div>
					<div class="pop_box" id="is_recon_false">
						<div class="pop_box_p">申请认证，拥有专属真人秘书<br/>享受更贴心的服务</div>
						<div class="pop_box_tutton">
							<!-- <button type="button" class="bj_orange" name="dhrz_button" value="400-199-3423" id="rzkfTelephone">立即电话认证：400-199-3423</button> -->
							<button type="button" class="bj_green" id="zxrz_button">立即在线认证</button>
						</div>
					</div>
					
				</div>
			</div>					
			<style>
			.iconsIndex{
			  width:.36rem;
			  height:.36rem;
			}
	 .pub_info{width:100%;background-color:rgba(255,255,255,0)}
	   #pop{display:none;width:100%;}
      dl{text-align:center;-webkit-transition: -webkit-transform 200ms;z-index:-1;position:absolute;bottom: -.16rem;left: 0rem;width:1.17rem;height:1.17rem}
      dl dt img{width:.86rem; height:.86rem;}
      dl dd{font-size:.30rem;color:#fff;}
      dl.on:nth-of-type(1) {-webkit-transform: translate(-2.8rem, -1.80rem) rotate(720deg);}
      dl.on:nth-of-type(2) {-webkit-transform: translate(-1.6rem, -3rem) rotate(720deg);}
      dl.on:nth-of-type(3) {-webkit-transform: translate(0, -3.6rem) rotate(720deg);}
      dl.on:nth-of-type(4) {-webkit-transform: translate(1.6rem, -3rem) rotate(720deg);}
      dl.on:nth-of-type(5) {-webkit-transform: translate(2.8rem, -1.8rem) rotate(720deg);}
	
</style>		
<script>
	function openIndexUrl(num){
		var url = '';
		if(num == 1){
			url = "${path}/m/activity/addActivityPage.do";
		}else if(num == 2){
			url = "${path}/m/business/addBusinessPage.do"; 
		}else if(num == 3){
			url = "${path}/m/dynamic/publishDynamic.do";
		}else if(num == 4){
			url = "${path}/m/topic/topicAddPage.do"
		}
		else if(num == 5){
			url = "${path}/m/reward/addRewardPage.do"
		}
		if (window.localStorage) {
			  localStorage.setItem("activityurl","${path}/m/sys/index.do");
		}
		 window.location.href=url; 
		if (window.localStorage) {
			  localStorage.setItem("history_url", "${path}/m/sys/index.do");
		}
	}
	var layerlabel = "";
	function open_pub(){
		if(layerlabel==""){
			 $('dl').addClass("on")
			 layerlabel = layer.open({
			  type: 1,
			  title: false,
			  closeBtn:false,
			  shadeClose: true,
			  skin:'pub_info',
			  anim: 0,
			  shade:0.5,
			  offset: '20%',
			  content:$("#pop"),
			  end: function(){
				  $('.layui-layer-shade').hide()
				  $('dl').removeClass("on")
				  layerlabel="";
			  }
			});
			
		}else{
			layer.close(layerlabel);
			 $('.layui-layer-shade').hide()
			$('dl').removeClass("on")
			layerlabel="";
		}
	}
	
	$(function(){
		$("button[name='dhrz_button']").click(function(){
			var v=$(this).attr("value");
			self.location.href="tel:"+v;
		});
		$("#zxrz_button").click(function(){
			self.location.href="${path}/m/my/reconPage.do";
		});
		
		check_recon();
	});
	
	var check_recon=function(){
		zdy_ajax({
			url: "${path}/m/sys/getTjyUser.do",
		    showLoading:false,
		    async:true,
			success: function(dataobj,res){
				var reconStatus=dataobj.reconStatus;
				var kfTelephone=dataobj.kfTelephone;
				if(reconStatus == 2){
					$("#kfTelephone").text(kfTelephone);
					$("#is_recon_true").find("button[name='dhrz_button']").attr("value",kfTelephone);
					$("#is_recon_true").show();
					$("#is_recon_false").hide();
				}else{
					$("#rzkfTelephone").text("立即电话认证："+kfTelephone);
					$("#rzkfTelephone").attr("value",kfTelephone);
					$("#is_recon_true").hide();
					$("#is_recon_false").show();
				}
			}
		});
	}
	
	
	/*function open_pub2(){
		var str =   '<div id="public_info1" style="width:95%;margin-left:2.5%">'+
					'	<div class="pop_box">'+
					'		<div class="pop_box_p">申请认证，拥有专属真人秘书<br>享受更贴心的服务</div>'+
					'		<div class="pop_box_tutton">'+
					'			<button type="button" class="bj_orange">立即电话认证：400-199-3423</button>'+
					'			<button type="button" class="bj_green">立即在线认证</button>'+
					'		</div>'+
					'	</div>'+
					'	<div class="pop_menu">'+
					'		<dl onclick="openIndexUrl(1)">'+
					'			<dt><img src="${path}/resource/img/pop/ys_jia_03.png"></dt>'+
					'			<dd>活动</dd>'+
					'		</dl>'+
					'		<dl onclick="openIndexUrl(2)">'+
					'			<dt><img src="${path}/resource/img/pop/ys_jia_05.png"></dt>'+
					'			<dd>合作</dd>'+
					'		</dl>'+
					'		<dl onclick="openIndexUrl(3)">'+
					'			<dt><img src="${path}/resource/img/pop/ys_jia_07.png"></dt>'+
					'			<dd>动态</dd>'+
					'		</dl>'+
					'		<dl onclick="openIndexUrl(4)">'+
					'			<dt><img src="${path}/resource/img/pop/ys_jia_09.png"></dt>'+
					'			<dd>话题</dd>'+
					'		</dl>'+
					'		<div class="clearfix"></div>'+
					'	</div>'+
					'</div>'		
		layer.open({
		  title: false,
		  offset:'b',
		  closeBtn: 0,
		  skin: 'pub_info',
		  shadeClose: true,
		  content:str
		});
					
		$('#public_info1 .pop_box').attr("style","background:#fff; width:100%; margin:0 auto 0 auto; border-radius:0.12rem; overflow:hidden; padding-bottom:0.6rem;");
		$('#public_info1 .pop_box_p').attr("style","text-align:center; font-size:.36rem; color:#333; background:#f0f0f0; line-height:160%; padding:0.3rem 0.1rem;");
		$('#public_info1 .pop_box_tutton').attr("style","text-align:center;");
		$('#public_info1 .pop_box_tutton button').attr("style","width:92%; height:.86rem; border-radius:.42rem; color:#fff;");
		$('#public_info1 .bj_orange').attr("style","background:#ff811b;");
		$('#public_info1 .bj_green').attr("style","background:#4ccb40;");
		$('#public_info1 .pop_menu').attr("style","background:#fff;  width:100%; padding:0 0 0.3em 0;margin-top:1.5em");
		$('#public_info1 .pop_menu dl').attr("style","width:24%; float:left; text-align:center;margin-top:.2rem");
		$('#public_info1 .pop_menu dl dt img').attr("style"," width:.86rem; height:.86rem;");
		$('#public_info1 .pop_menu dl dd').attr("style","font-size:.26rem;");
		$('.bj_orange').attr("style","background: #ff811b;display: block;margin: .5rem auto .2rem auto;")
		$('#public_info1').addClass(".public_info1L")
		
					
	}*/
</script>