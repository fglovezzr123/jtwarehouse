<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0"/>
    <meta content="yes" name="apple-mobile-web-app-capable" /> 
    <meta content="black" name="apple-mobile-web-app-status-bar-style" /> 
    <meta content="telephone=no" name="format-detection" />
	<title>加载中...</title>
</head>
<body>
    <table id="loginTable">
        <tr class="table3-rows table3-arrow">
            <th width="120px" class="table3-label">用户名</th>
            <td class="table3-target">
                <label class="widget-text1"><input type="text" id="user_name" name="user_name"  placeholder="请填写用户名/手机号" /></label>
            </td>
        </tr>
        <tr class="table3-rows table3-arrow">
            <th width="120px" class="table3-label">密码</th>
            <td class="table3-target">
                <label class="widget-text1">
                	<input type="password" id="password" name="password" placeholder="请填写用户密码"  />
                </label>
            </td>
        </tr>
        <tr class="table3-rows table3-arrow">
            <td class="table3-target pm_fbBtn" colspan="2" style="text-align:center;">
            	<a href="javascript:login();">登  录</a>
            	<%-- <a href="${path}/m/sys/guidePage.do">注  册</a> --%>
            	<%-- <a href="${path}/m/sys/regPage.do">注  册</a> --%>
            </td>
        </tr>
    </table>
</body>
<script type="text/javascript">
	$(function(){
		//layer.load(1, {shade: [0.1,'#393D49']});
		if(is_weixn()){
			$("#loginTable").hide();
		}

		gotologin(1);
	});
	var login=function(){
		var _user_name=$("#user_name").val();
		if(isEmpty(_user_name)){
			alert_back("用户名不能为空",function(){
				$("#user_name").focus();
			});
			return;
		}
		
		var _password=$("#password").val();
		if(isEmpty(_password)){
			alert_back("用户密码不能为空",function(){
				$("#password").focus();
			});
			return;
		}
	
		zdy_ajax({
			url: '${path}/m/sys/login.do',
			type: 'post',
			dataType: 'json',
			data:{
				userName:_user_name,
				pwd:_password
			},
			success: function(output){
				alert_back("登录成功",function(){
					var last_url=self.location.href.split("last_url=")[1];
					if(!last_url || last_url.indexOf('loginPage.do') != -1){
						last_url="${path}/m/sys/index.do";
					}
					top.location.href=last_url;
				});
			}
		});
	}
	
	var submitOrHidden = function(evt){
		evt = window.event || evt;
		if(evt.keyCode==13){//如果取到的键值是回车
			login();
     	}
	}


</script>
</html>