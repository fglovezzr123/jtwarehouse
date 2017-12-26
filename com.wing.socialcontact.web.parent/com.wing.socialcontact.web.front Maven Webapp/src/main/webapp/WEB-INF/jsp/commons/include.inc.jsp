<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<% 
	String path = request.getContextPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.getSession().setAttribute("path", path);
	request.getSession().setAttribute("sversion", "0.4333");
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1 
	response.setHeader("Pragma","no-cache"); //HTTP 1.0 
	response.setDateHeader ("Expires", 0);
	

	request.getSession().setAttribute("http", "https");
	
	
%>
<link rel="stylesheet" href="${path}/resource/css/main.css?v=${sversion}">
<link rel="stylesheet" href="${path}/resource/css/libs/public.css?v=${sversion}">
<link rel="stylesheet" href="${path}/resource/css/offlinepage.css?v=${sversion}">
<script type="text/javascript" src="${path}/resource/js/jquery/jquery-1.10.2.min.js?v=${sversion}"></script>
<script src="${path}/resource/js/layer3/layer.js?v=${sversion}"></script>
<script src="${path}/resource/js/jquery/jquery.lazyload.min.js?v=${sversion}"></script>
<script src="${path}/resource/js/login.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resource/js/util.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
<%-- <script type="text/javascript" src="${path}/resource/js/libs/strophe.min.js?v=${sversion}" charset="utf-8"></script> 
<script type="text/javascript" src="${path}/resource/js/libs/xmpp.mam.js?v=${sversion}" charset="utf-8"></script>
<script type="text/javascript" src="${path}/resource/js/jquery.qqFace.js?v=${sversion}" charset="utf-8"></script> --%>
<script src="${path}/resource/js/libs/public.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resource/js/libs/dropload.min.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resource/js/libs/text-slider.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
<!-- 文件上传组件 -->
<script src="${path}/resource/js/webuploader/webuploader.min.js?v=${sversion}" type="text/javascript"></script>
<script type="text/javascript">
	var zfflag=false;//false为关闭支付
	var screenflag = true;//true为隐藏积分等菜单
	var _path="${path}";
	//var _oss_url="http://tianjiu.oss-cn-beijing.aliyuncs.com/";
	var _oss_url="http://tianjiutest.oss-cn-beijing.aliyuncs.com/";
	//var home_path="${http}://tjy.tojoycloud.org";
	var home_path="${http}://www.tojoycloud.org";
	//var home_path="${http}://www.tojoycloud.com";
	//im 正式环境
	//var _im_path = "ims.tojoycloud.com";
	//im 测试环境
	//var _im_path = "im.tojoycloud.org";
	var _im_path = "47.93.1.27";
	//预生产环境
	//var _im_path = "123.56.218.91";
	var _im_path_http = "${http}://"+_im_path;
	
	//正式环境APPKEY
	var appkeys='e7382694a60dc94ecc2550f20d6612de';
	
	//测试环境APPKEY
	//var appkeys='fc9778516a23ad313909cfaf0a32191c';
	//var appid_ = localStorage.appid;
	
	//生产环境
	 var imPrefixion='tojoycloud_test_';
	//预生产环境
//	var imPrefixion='tojoycloud_qa_';
	//正式环境
//	var imPrefixion='tojoycloud_online_';

var imCurrentUserId=localStorage.getItem("currentUserId");
localStorage.setItem("currentUserIdfFromIm",imPrefixion+imCurrentUserId);
	
	var appid_ = null;
	if(null == appid_ || appid_.length == 0){
		$.ajax({
			url: _path + '/m/sys/getWxConfig.do',
			type: 'post',
			dataType: 'json',
			success: function(output){
				if(output.code == 0){
					localStorage.appid=output.dataobj.wx_appid;
				}
			}
		});
	}
</script>
<%-- <script src="${path}/resource/js/libs/outOfChating.js?v=${sversion}" type="text/javascript" charset="utf-8"></script> --%>
<script src="${path}/resource/js/NIM_Web_Chatroom_v4.1.0.js"></script>
 <script type="text/javascript" src="${path}/resource/js/NIM_Web_Netcall_v4.1.0.js"></script>
 <script type="text/javascript" src="${path}/resource/js/NIM_Web_NIM_v4.1.0.js"></script>
 <script src="${path}/resource/js/NIM_Web_SDK_v4.1.0.js"></script>
 <script src="${path}/resource/js/NIM_Web_WebRTC_v4.1.0.js"></script>
 <script src="${path}/resource/js/libs/IM163.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
<style>
	.banner_ul img {
		width: 100%;
		height: 100%;
	}	
</style>
<script type="text/javascript">document.write(unescape("%3Cspan style='display:none;' id='cnzz_stat_icon_1262265057'%3E%3C/span%3E%3Cscript src='https://s13.cnzz.com/z_stat.php%3Fid%3D1262265057' type='text/javascript'%3E%3C/script%3E")); document.getElementById("cnzz_stat_icon_1262265057").style.display = "none";</script>
<script src="${http}://res.wx.qq.com/open/js/jweixin-1.2.0.js" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resource/js/wxshare.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>