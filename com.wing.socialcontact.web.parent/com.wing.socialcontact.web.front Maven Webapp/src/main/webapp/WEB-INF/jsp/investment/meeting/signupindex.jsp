<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title">立刻报名</title>
<link rel="stylesheet" href="${path}/resource/css/liveAplay.css?v=${sversion}" />
<style type="text/css">
.ps-cont .ps-contt .items.items1 {
    height:auto;
    min-height: .84rem;
}

.ps-cont .ps-contt .items .inps{
    font-size: .26rem;
    width: 5.00rem;
    padding:.24rem;
    padding-right: 0.3rem;
   /*  box-sizing: border-box; */
}


</style>
</head>
<body>
<form action="" id="dataForm" method="post">
	<input type="hidden" id="ticketPrice" value="${fns:fixed(obj.ticketPrice)}">
	<input type="hidden" name="meetingId" value="${obj.id}">
	<div class="T-fPlay" style="background: #f2f3f4;width: 100% ;">
		<div class="tishi">
			<div class="ts-l">温馨提示：</div>
			<div class="ts-r">
				<span>尊敬的会员，欢迎入会！请完善你的信息与需求</span>
				<span>如果有问题，请致电010-53118922或与你的专属秘书联系</span>
			</div>
		</div>
		<p class="psel">个人信息</p>
		<div class="ps-cont">
			<span class="col" style="width:0rem"></span>
			<div class="ps-contt">
				<div class="items">
					<h3 style="text-align:left;">姓名</h3>
					<span class="inp">${tjyUser.nickname}</span>
				</div>
				<div class="items">
					<h3 style="text-align:left;">性别</h3>
					<span class="inp">${(2 ne wxUser.sex)?'男':'女'}</span>
				</div>
				<div class="items">
					<h3 style="text-align:left;">出生日期</h3>
					<span class="inp"><fmt:formatDate value='${wxUser.birthday}' pattern='yyyy-MM-dd'/></span>
				</div>
				<div class="items">
					<h3 style="text-align:left;">手机号码</h3>
					<span class="inp">${tjyUser.mobile }</span>
				</div>
			</div>
		</div>
		<p class="qiye">企业信息</p>
		<div class="ps-cont qi-cont">
			<span class="col" style="width:0rem"></span>
			<div class="ps-contt">
				<div class="items items1">
					<h3 style="text-align:left;">企业名称</h3>
					<span class="inps">${tjyUser.comName}</span>
				</div>
				<div class="items">
					<h3 style="text-align:left;">职位</h3>
					<span class="inp">${tjyUser.job}</span>
				</div>
				<div class="items">
					<h3 style="text-align:left;">所属行业</h3>
					<span class="inp">${tjyUser.industry}</span>
				</div>
				<div class="items">
					<h3 style="text-align:left;">所在地区</h3>
					<span class="inp">${tjyUser.province} ${tjyUser.city} ${tjyUser.region}</span>
				</div>
				<%-- <div class="items">
					<h3>主营业务</h3>
					<input type="text" maxlength="10" name="mainBusiness" id="mainBusiness" value="" class="inp" placeholder="请填写主营业务">
				</div>
				<div class="items">
					<h3>注册资金</h3>
					<div class="inp">
						<c:if test="${not empty tjyUser.reconCapital}">
						<span class="inp">${fns:fixed(tjyUser.reconCapital)} 万</span>
						</c:if>
						<input type="hidden" name="regCapital" id="regCapital" value="${tjyUser.reconCapital}">
					</div>
				</div>
				<div class="items">
					<h3>实缴资本</h3>
					<div class="inp">
						<input type="text" maxlength="10" name="payCapital" id="payCapital" value="" onkeyup="num(this)" onafterpaste="return false" onpaste="return false"><span>万<b>(必填)</b></span>
					</div>	
				</div>

				<div class="items">
					<h3>总资产</h3>
					<div class="inp">
						<input type="text" maxlength="10" name="totalAssets" id="totalAssets" value="" onkeyup="num(this)" onafterpaste="return false" onpaste="return false"><span>万<b>(必填)</b></span>
					</div>	
				</div>
				<div class="items">
					<h3>年销售额</h3>
					<div class="inp">
						<input type="text" maxlength="9" name="annualSales" id="annualSales" value="" onkeyup="num(this)" onafterpaste="return false" onpaste="return false"><span>万</span>
					</div>	
				</div> --%>
			</div>
		</div>
		<div class="ch">
			<p>参会方式</p>
	<c:choose>
		<c:when test="${obj.types eq 1}">
			<div class="rad">
				<input type="radio" name="attendType" id="" value="1" checked="checked">
				<h3>网上直播<i>(&nbsp;线上同步直播会议，需要网络支持&nbsp;)</i></h3>
			</div>
		</c:when>
		<c:when test="${obj.types eq 2}">
			<div class="rad">
				<input type="radio" name="attendType" id="" value="2" checked="checked">
				<h3>现场会议<i>(&nbsp;到达指定会场，参与路演与互动&nbsp;)</i></h3>
			</div>
		</c:when>
		<c:otherwise>
			<div class="rad">
				<input type="radio" name="attendType" id="" value="1" checked="checked">
				<h3>网上直播<i>(&nbsp;线上同步直播会议，需要网络支持&nbsp;)</i></h3>
			</div>
			<div class="rad">
				<input type="radio" name="attendType" id="" value="2">
				<h3>现场会议<i>(&nbsp;到达指定会场，参与路演与互动&nbsp;)</i></h3>
			</div>
		</c:otherwise>
	</c:choose>
		</div>
		<div class="gsj">
			<p>其他需求</p>
			<textarea name="otherReq" id="otherReq" rows="" cols="" maxlength="100" placeholder="请简述其他需求，不得超过100字" class="nerong"></textarea>
			<span id="num_txt1">0/100</span>
		</div>
		<div class="tj">
		       <div class="container"> 
				<h3>天九联系人：</h3>
				<input type="text" maxlength="5" name="tjLinkMan" id="tjLinkMan" value="" class="tijiu">
			   </div>
			</div>
			<div class="tj tlast">
			   <div class="container">	
				<h3>推荐人：</h3>
				<input type="text" maxlength="5" name="recLinkMan" id="recLinkman" value="" class="tijiu">
			   </div>
			</div>
			<div style="height:.2rem">
			</div>
			<div class="submit active_A" onclick="doSubmit()">立即报名</div>
	</div>
	</form>
</body>
<script type="text/javascript">
function doSubmit(){
	if(navigator.onLine){
		if(new Date().getTime()>=  '${endSignupTime}'){
			alert("报名期限已过，禁止报名")
		}else{
					$.ajax({
						url: _path+'/m/sys/is_recon.do?s='+Date.parse(new Date()),
						type: 'post',
						dataType: 'json',
						success: function(data){
							if(data.code == 600){
								confirm("您还未认证，请认证后继续此操作。 是否马上认证？",function(t){
									if(t == 1){
										self.location.href=_path+"/m/my/reconPage.do";
									}else{
										if(isEmpty(document.referrer)){//不存在父页面
											self.location.href=_path+"/m/sys/index.do";
										}else{
											self.location.href=_path+"/m/meeting/index.do";
										}
									}
								});
							}else{
								var params = $("#dataForm").serializeObject();
								if(params["mainBusiness"]==""){
									alert("请输入主营业务");
									return;
								}
								if(params["payCapital"]==""){
									alert("请输入实缴资本");
									return;
								}
								if(params["totalAssets"]==""){
									alert("请输入总资产");
									return;
								}
								
								if(isNaN(parseInt($("#ticketPrice").val()))){
									alert("门票价格错误，报名失败");
									return;
								}
								if(zfflag){
								$("#dataForm").attr("action","${path}/m/my/meetingsignup.do");
								}else{
								$("#dataForm").attr("action","${path}/m/my/meetingsignup2.do");
								}
								$("#dataForm").submit();
							}
						}
					});
		}
				}else{
						offlinedeal();
				}
}
function num(obj){
	obj.value = obj.value.replace(/[^\d]/g,""); //娓呴櫎"鏁板瓧"鍜�."浠ュ鐨勫瓧绗�	//obj.value = obj.value.replace(/[^\d.]/g,""); //娓呴櫎"鏁板瓧"鍜�."浠ュ鐨勫瓧绗�	//obj.value = obj.value.replace(/^\./g,""); //楠岃瘉绗竴涓瓧绗︽槸鏁板瓧
	//obj.value = obj.value.replace(/\.{2,}/g,"."); //鍙繚鐣欑涓�釜, 娓呴櫎澶氫綑鐨�	//obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	//obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //鍙兘杈撳叆涓や釜灏忔暟
}
$(function(){
   $('#otherReq').on('keyup',function(){
       var txtval = $('#otherReq').val().length;
      var str = parseInt(100-txtval);
      console.log(str);
        if(str > 0 ){
          $('#num_txt1').html(txtval+'/100');
      }else{
          $('#num_txt1').html('剩余可输入0字');
          $('#otherReq').val($('#otherReq').val().substring(0,100)); //这里意思是当里面的文字小于等于0的时候，那么字数不能再增加，只能是600个字
        }
    });
})
</script>
</html>