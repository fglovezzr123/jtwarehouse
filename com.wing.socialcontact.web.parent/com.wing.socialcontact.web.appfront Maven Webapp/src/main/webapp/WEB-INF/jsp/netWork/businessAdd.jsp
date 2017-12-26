<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>

<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="èµè®¯">
		<title>发布合作</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/issuePk.css" />	
	
	</head>

	<body>
		<body style="background: #f2f3f4;">
		<div class="I-Pk" style="background: #f2f3f4;width: 100%;">
			<div class="p-content show">
			 <input id="allowComment" name="allowComment" value="1"  type="hidden" />
			 <input id="isShow" name="isShow" value="2"  type="hidden" />
				<div class="hz_content">
					<div class="h-items">
						<p>合作标题:</p>
						<input type="text" name="titles" id="titles" value=""maxlength="50" placeholder="一句话描述你的合作内容，不得超过50个汉字" onkeyup="value=clearYEmoil(value)" onpaste="value=clearYEmoil(value)" oncontextmenu="value=clearYEmoil(value)" />
					</div>
					<div class="h-lb">
						<span>合作分类：</span>
						<ul id="hz_treelist" style="display: none;">  
						<c:forEach var="c" items="${list}">
						<li id="${c.id }" data-val="${c.className}"><c:out value="${c.className}"></c:out></li>
						</c:forEach>	
					   </ul>
					   <input type="hidden" id="bizType" name="bizType"/>
					   <i class="iconfont">&#xe605;</i>
					</div>
					<div class="s-fl">
						<p>诉求分类：</p>
						<div class="s-rad">
							<input type="radio" name="appealType" id="" value="1"  checked/><label for="">&nbsp;我有供给</label>
							<input type="radio" name="appealType" id="" value="2" /><label for="">&nbsp;我有需求</label>
						</div>
					</div>
					<div class="h-items">
						<p>合作诉求:</p>
						<input type="text" name="appealSummary" id="appealSummary"maxlength="50" value=""  placeholder="一句话描述你的诉求内容，不得超过50个汉字" onkeyup="value=clearYEmoil(value)" onpaste="value=clearYEmoil(value)" oncontextmenu="value=clearYEmoil(value)"/>
					</div>
					<div class="y-items">
						<p>有效期:</p>
						<div class="time">
							<input type="date" name="startTime" id="startTime"  value=""    style="width:120px;" />  
							<span>&nbsp;至&nbsp;</span>
							<input type="date" name="endTime"  id="endTime"  value="" style="width:120px;"  />
						</div>
					</div>
					<div class="x-items">
						<p>悬赏金额：</p>
						<div class="tex">
							<input type="text" name="reward" id="reward" value="" placeholder="请输入金额" onchange="isNumberCount(this)"/>
							<span>J币(非必填)</span>
						</div>
					</div>
					<div class="hz-con">
						<p>合作说明:</p>
						<textarea name="appealDesc" id="appealDesc" value="" maxlength="500" placeholder="详细描述您的合作需求，可以更快速促成合作，不超过500个汉字"  onkeyup="value=clearYEmoil(value)" onpaste="value=clearYEmoil(value)" oncontextmenu="value=clearYEmoil(value)"></textarea>
					</div>
				</div>
				<div class="cx">
					<div class="no">
						<p>仅好友可见</p>
						<img id="off1" src="${path}/resource/img/icons/OFF.png" onclick="show('1')"/>
						<img id="on1" src="${path}/resource/img/icons/oN.png" onclick="show('2')"/>
					</div>
				<%-- 	<div class="no">
						<p>不允许用户评论</p>
						<img id="off" src="${path}/resource/img/icons/OFF.png" onclick="isShow('2')"/>
						<img id="on" src="${path}/resource/img/icons/oN.png" onclick="isShow('1')"/>
					</div> --%>
				</div>
			</div>
			<div class="submit active_A" onclick="business_submit();">发布</div>
		</div>
	</body>
<script type="text/javascript">
$(document).ready(function() {
//	$("#on").hide();
	$("#on1").hide();
//	$("#allowComment").val("1");
});
function isShow(type){
	if(type=="2"){
		$("#on").show();
		$("#off").hide();
	}else if(type=="1"){
		$("#off").show();
		$("#on").hide();
	}
	$("#allowComment").val(type);
}
var business_submit=function(){
	var titles=$.trim($("#titles").val());
	var allowComment=$("#allowComment").val();
	var appealType=$("input[name='appealType']:checked").val();
	var appealSummary=$("#appealSummary").val();
	var reward=$("#reward").val();
	var isShow = $("#isShow").val();
	var appealDesc = $("#appealDesc").val();
	if(isEmpty(titles)){
		alert_back("合作标题不能为空",function(){
			$("#titles").focus();
		});
		return;
	}
	if(isEmpty(appealSummary)){
		alert_back("合作诉求不能为空",function(){
			$("#appealSummary").focus();
		});
		return;
	}
	if(isEmpty(appealDesc)){
		alert_back("合作说明不能为空",function(){
			$("#appealDesc").focus();
		});
		return;
	}
	var bizType=$("#bizType").val();
	if(isEmpty(bizType)){
		alert_back("合作分类不能为空",function(){
		
		});
		return;
	}
	var startTime=$("#startTime").val();
	var endTime=$("#endTime").val();
	var currTime = new Date();
	currTime=currTime.getFullYear()+'-'+(currTime.getMonth()+1)+"-"+currTime.getDate();
	var reg = new RegExp('-', 'g');
	var startTimef = startTime.replace(reg, '/');//正则替换
	currTime = currTime.replace(reg, '/');
	startTimef = new Date(parseInt(Date.parse(startTimef), 10));
	currTime = new Date(parseInt(Date.parse(currTime), 10));
	if(isEmpty(startTime)){
		alert_back("有效期开始时间不能为空",function(){
			
		});
		return;
	}
	if(startTimef<currTime){
		alert_back("有效期不能早于当前时间",function(){
			
		});
		return;
	}
	if(isEmpty(endTime)){
		alert_back("有效期结束时间不能为空",function(){
			
		});
		return;
	}
	if ($("#titles").val().length> 50) { 
        alert("合作标题最多为50汉字！");
        return ;
     }
	if ($("#appealSummary").val().length > 50) { 
        alert("合作诉求最多为50汉字！");
        return ;
     }
	if ($("#appealDesc").val().length > 500) { 
        alert("合作说明最多为500汉字！");
        return ;
     }
	if(isEmojiCharacter(titles)){
		alert_back("合作标题不能含表情",function(){
			$("#titles").focus();
		});
		return;
	}
	if(isEmojiCharacter(appealSummary)){
		alert_back("合作诉求不能含表情",function(){
			$("#appealSummary").focus();
		});
		return;
	}
	if(isEmojiCharacter(appealDesc)){
		alert_back("合作说明不能含表情",function(){
			$("#appealDesc").focus();
		});
		return;
	}
	if(startTime>=endTime){
		alert("有效期结束时间必须大于开始时间！");
		return;
	}
	/* if(!isEmpty(reward)){
		if (isNaN(reward)){
			alert_back("悬赏金额请输入数字",function(){
				$("#reward").focus();
			});
			return;
		} 
	} */
	zdy_ajax({
		url: '${path}/add/m/business/addBusiness.do',
		data:{
			titles:titles,
			bizType:bizType,
			appealType:appealType,
			appealSummary:appealSummary,
			startTime:startTime,
			endTime : endTime,
			reward : reward,
			allowComment:allowComment,
			isShow :isShow,
			appealDesc : appealDesc
		},
		success: function(data,output){
			if(output.code == 0){
				alert_back("发布成功！",function(){
					console.log(titles)
					 var history_url = localStorage.getItem("history_url");
					 top.location.href=history_url;
					 localStorage.removeItem("history_url"); 
					
				});
			}else{
				alert(output.msg);
			}
		},
		error:function(e){
		}
	});
}

$("#hz_treelist").mobiscroll().treelist({
    theme: mobile_type||'',
    lang: 'zh',
    display: 'bottom',
    multiline:2,
    placeholder: '请选择分类 ...',
    inputClass:'name',
    mode:'scroller',
    row:3,
    defaultValue: [Math.floor($('#hz_treelist li').length/2)],
    onSelect:function(valueText, inst){
    	var id=$('#hz_treelist').find("li[data-val='"+valueText+"']").attr("id");
    	$("#bizType").val(id);
    },
    onInit:function(inst){
    }
});
var getLength = function (str) {
	 ///<summary>获得字符串实际长度，中文2，英文1</summary>
    ///<param name="str">要获得长度的字符串</param>
    var realLength = 0, len = str.length, charCode = -1;
    for (var i = 0; i < len; i++) {
        charCode = str.charCodeAt(i);
        if (charCode >= 0 && charCode <= 128) realLength += 1;
        else realLength += 2;
    }
    return realLength;
};
function show(type){
	if(type=="1"){
		$("#on1").show();
		$("#off1").hide();
	}else if(type=="2"){
		$("#off1").show();
		$("#on1").hide();
	}
	$("#isShow").val(type);
}
function isNumberCount(obj) {
//	var reg = new RegExp("^[0-9]*[1-9][0-9]*$");
	var reg = new RegExp("^\\d+$");
	if (obj.value.indexOf(" ") != -1) {
		alert("您输入的值存在空格，请检查！");
		obj.value = "1";
		return null;
	}
	if (obj.value != "") {
		if ((!reg.test(obj.value))) {
			alert("请输入正确的数值!");
			obj.value = "1";
		}
		if (parseInt(obj.value) > 99999999||parseInt(obj.value)<1) {
			alert("请输入1-99999999的整数!");
			obj.value = "1";
		}
	}
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
 //首字符串为特殊符号
/* function firstCharacter(substring) {
	if(substring.indexOf("<")==0){
		substring = substring.replace("<","");
	}
	
	return substring;
}   */
</script>

</html>