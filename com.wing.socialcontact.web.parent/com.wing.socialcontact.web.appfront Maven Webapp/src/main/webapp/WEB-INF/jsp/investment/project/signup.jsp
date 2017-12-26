<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title">我有意向</title>
<link rel="stylesheet" type="text/css"	href="${path}/resource/css/libs/public.css?v=${sversion}" />
<link rel="stylesheet" type="text/css"	href="${path}/resource/css/invest-intention.css?v=${sversion}" />
</head>
<body>
<div class="wrapper">
    <div class="invest-intention"> 请选择类型：
    <c:forEach items="${list}" var="item">
       <div class="checkbox checkBox-off" data-id="${item.id}">${item.listValue}</div>
    </c:forEach>
    </div>
    <div class="invest-intention-head">意向说明：</div>
    <div class="invest-intention-text">
         <textarea placeholder="请简要说明您对项目的意向，不超过200字" id="willDesc" maxlength="200"></textarea>
         <div><span id="num_txt1">0/200</span></div>
    </div>
</div>
<div class="invest-submit active_A" onclick="doSubmit()">
    <div>提交</div>
    <div>提交后平台服务人员将与您联系</div>
</div>
<script type="text/javascript">
$(function(){
	$(".checkbox").click(function(){
		$(".checkbox").removeClass("checkBox-on").addClass("checkBox-off");
		$(this).removeClass("checkBox-off").addClass("checkBox-on");
	});
	$(".invest-intention").children(":first").removeClass("checkBox-off").addClass("checkBox-on");
})
function doSubmit(){
	var id = "${id}";
	var willType = $(".checkBox-on").attr("data-id");
	var willDesc = $("#willDesc").val();
	if(isEmpty($.trim(willDesc))){
		   alert("请填写意向描述！",function(){
			});
			return;
	   }
	
	zdy_ajax({
  		url: "${path}/add/m/project/signup/save.do",
  		data:{prjId: id, willType: willType, willDesc: willDesc},
  		success:function(dataobj){
  			if("0"===dataobj["result_code"]){
  				alert({
  					title:"提交成功",
  					callback: function(){
  		  				window.location.href = "${path}/m/project/detail/index.do?id="+id;
  	  				}
  				})
  			}else if("2"===dataobj["result_code"]){
  				alert({
  					title:"您已提交过此意向",
  					callback: function(){
  		  				window.location.href = "${path}/m/project/detail/index.do?id="+id;
  	  				}
  				})
  			}else{
  				alert({
  					title:"提交失败",
  					callback: function(){
  						window.location.href = "${path}/m/project/index1.do";
  	  				}
  				})
  			}
  		},
  		complete:function(){
  		}
  	})
}

$(function(){
	   $('#willDesc').on('keyup',function(){
	       var txtval = $('#willDesc').val().length;
	       console.log(txtval);
	      var str = parseInt(200-txtval);
	      console.log(str);
	        if(str > 0 ){
	          $('#num_txt1').html(txtval+'/200');
	      }else{
	          $('#num_txt1').html('剩余可输入0字');
	          $('#willDesc').val($('#willDesc').val().substring(0,200)); //这里意思是当里面的文字小于等于0的时候，那么字数不能再增加，只能是600个字
	        }
	        //console.log($('#num_txt').html(str));
	    });
	})
</script>
</body>
</html>