<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="user-scalable=1, initial-scale=1,maximum-scale=1, minimum-scale=1, width=device-width, height=device-height"/>
	<%@ include file="/WEB-INF/jsp/commons/include.com.jsp" %>
	<%@ include file="/WEB-INF/jsp/commons/include_login.jsp" %>
	<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
    <title>编辑</title>
    <style>
     #demo2_dummy{
           border: none;
           height: 0.86rem;
           line-height: 0.86rem;
           width:75%;
           text-align:right;
           background: none;
    }   
    
    #truenames{
          border: none;
           height: 0.86rem;
           line-height: 0.86rem;
           width:75%;
           text-align:right;
           background: none;
    }
         strong,b{font-weight:normal;}
         section {
    
    overflow-y: initial;
}
body{
 height:100%;
 background:#f0f0f0;
}
    </style>
</head>
<body>
<div class="wrap">
	<header>
    	<span class="header_return" onclick="backForAndroid()"><img src="${path}/resource/images/return.png"></span>
<!--     	<a href="${path}/m/qfy/mineIndex.do"><span class="header_return" ><img src="${path}/resource/images/return.png"></span></a> -->
        <h1>编辑</h1>
        
    </header>
	<section>
    	<div class="bianji_tx box_box" style="margin-top:0">
        	<span>
<!--         		<img style="width:1.12rem;height:1.12rem" src="${user.head_portrait}" /> -->
        		<c:if test="${user.head_portrait != null}">
            		<img style="width:1.12rem;height:1.12rem" src="${user.head_portrait}" />
            	</c:if>
            	<c:if test="${user.head_portrait == null}">
            		<img style="width:1.12rem;height:1.12rem" src="${path}/resource/images/default_head.png" />
            	</c:if>
			</span>
            <b>头像</b>
            <div class="clear"></div>
        </div>
        <ul class="bianji_xx box_box">
        	<li><b>昵称</b><input id="truenames" readonly='readonly' value="${user.nickname}"/></li>
            <li style="position:relative;height:auto"><b>所在地区</b>
	            <div>
	              <ul id="demo2" style="display: none;"><c:forEach items="${provinceList}" var="province"><li data-val="${province.disName}">${province.disName}<ul><c:forEach items="${cityList }" var="city"><c:if test="${city.superId eq province.id}"><li data-val="${city.disName}">${city.disName}<ul><c:forEach items="${areaList}" var="area"><c:if test="${area.superId eq city.id }"><li data-val="${area.disName}" provinceid="${province.id}" cityid="${city.id}" areaid="${area.id}" searchName="${province.disName} ${city.disName} ${area.disName}">${area.disName}</li></c:if></c:forEach></ul></li></c:if></c:forEach></ul></li></c:forEach></ul>
	            </div>
	            <div id="showArea" style="width:75%;float:right"></div>
	            <br style="clear:both"/>
            </li>
        </ul>
  </section>
</div>
</body>
</html>
<script>

function backForAndroid(){
	
	document.location.href= "${path}/m/qfy/mineIndex.do";
}

var oldName = '${user.trueName}';
var isRecon = '${isRecon}';


$("#truenames").click(function(){
	if(isRecon == 0){
		$("#truenames").removeAttr("readonly");
	}
});
$("#truenames").blur(function(){
	var name = $("#truenames").val();
	if(!name){
		return;
	}
	if(oldName == name){
		return;
	}
	if(name.length > 20){
		layer.msg("昵称长度限制20个汉字");
		return;
	}
	$("#truenames").attr("readonly","readonly")
	zdy_ajax({
		url : "${path}/m/qfy/addusers.do",
		showLoading : false,
		data : {
			nickname : name
		},
		success : function(data, res) {
		},
		error : function(e) {
		}
	});
});

$('#demo2').mobiscroll().treelist({
	theme : mobile_type || '',
	lang : 'zh',
	display : 'bottom',
	fixedWidth : [ 160, 160, 160 ],
	multiline : 2,
	placeholder : '请选择地区 ...',
	//inputClass : 'con-r',
	labels : [ '省', '市', '区' ],
	//showLabel:true,
	mode : 'scroller',
	row : 3,
       defaultValue:['${user.province}','${user.city}','${user.county}'],
	onSelect : function(valueText, inst) {
		var obj = $('#demo2').find(
				"li[searchName='" + valueText + "']");
		console.log(valueText.length);
		$("#showArea").html(valueText);
		if(valueText.length>19){
  			$("#showArea").css('line-height','0.5rem');
  		}else{
  			$("#showArea").css('line-height','0.86rem');
  		}
		//console.log(obj);
		//console.log(obj.attr("provinceid"));
		var provinceid = obj.attr("provinceid");
		var cityid = obj.attr("cityid");
		var areaid = obj.attr("areaid");

		zdy_ajax({
			url : "${path}/m/qfy/addusers.do",
			showLoading : false,
			data : {
				province : provinceid,
				city : cityid,
				county : areaid,
			},
			success : function(data, res) {
			},
			error : function(e) {
			}
		});
	},
	onInit : function(inst) {
      		$("#demo2_dummy").val('${user.province}'+" "+'${user.city}'+" "+'${user.county}');
      		$("#demo2_dummy").css('opacity','0');
      		$("#demo2_dummy").css('position','absolute');
      		$("#demo2_dummy").css('z-index','100');
      		$("#demo2_dummy").css('height','1.7rem');
      		$("#demo2_dummy").css('right','0');
      		$("#demo2_dummy").css('top','0');
      		$("#showArea").text($("#demo2_dummy").val());
      		
      		if($("#demo2_dummy").val().length>19){
      			$("#showArea").css('line-height','0.5rem');
      		}else{
      			$("#showArea").css('line-height','0.86rem');
      		}
      		
	}
});
</script>