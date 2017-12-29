<!-- 必须认证 -->
<%@page pageEncoding="UTF-8"%>
<%@page import="com.wing.socialcontact.common.model.Member"%>
<%@page import="com.wing.socialcontact.util.Constants"%>
<% 
	String path_recon = request.getContextPath(); 
	Member me2=(Member)request.getSession().getAttribute("me");
	String userid = (String)request.getSession().getAttribute(Constants.SESSION_WXUSER_ID);
	String headpic = (String)request.getSession().getAttribute(Constants.SESSION_WXUSER_HDPIC);
	String nickname = (String)request.getSession().getAttribute(Constants.SESSION_WXUSER_NICKNAME);
%>
<script type="text/javascript">
$(function(){
	if(navigator.onLine){
		$.ajax({
			url: '${path}/m/sys/is_recon.do?s='+Date.parse(new Date()),
			type: 'post',
			dataType: 'json',
			success: function(data){
				if(data.code == 600){
					confirm("您还未认证，请认证后继续此操作。是否马上认证？",function(t){
						if(t == 1){
							self.location.href="${path}/m/my/reconPage.do";
						}else{
							if(isEmpty(document.referrer)){//不存在父页面
								self.location.href="${path}/m/sys/index.do";
							}else{
								history.go(-1);
							}
						}
					});
				}
			}
		});
		localStorage.setItem("currentUserId","<%=userid %>");
	}else{
			offlinedeal();
	}
})


function offlinedeal(){
	layer.open({
		type : 1,
		//skin: 'layui-layer-lan',
		title: false,
			closeBtn: 0, //不显示关闭按钮
		fix : true,
		shadeClose : true,
		maxmin : false,
		area : [ '100%', '100%' ],
		content : '<div class="wrapper1"><div class="right-signal1">网络异常，请检查手机网络</div><div class="active_A name-btn1" onclick="reloadjsp()">刷新</div></div>',
		shift : 2,
		scrollbar : false,
		success : function(layero, index) {
		},
		end : function() {
		},
		cancel : function(cancel) {
		}
	});
}
	
</script>

