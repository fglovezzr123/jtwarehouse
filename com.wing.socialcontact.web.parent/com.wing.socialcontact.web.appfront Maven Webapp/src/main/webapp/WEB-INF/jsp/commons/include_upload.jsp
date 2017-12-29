<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

$(function(){
	//console.log(window.location.href)
	if(is_weixn()){
		zdy_ajax({
			url: '${path}/m/sys/getSignature.do',
			data:{
				url:window.location.href
			},
			success: function(data,output){
				if(output.code == 0){ 
					var configdata = output.dataobj;
					var jsApiList = [];
					jsApiList.push("chooseImage");
					jsApiList.push("uploadImage");
					jsApiList.push("downloadImage");
					jsApiList.push("startRecord");
					jsApiList.push("stopRecord");
					jsApiList.push("onVoiceRecordEnd");
					jsApiList.push("playVoice");
					jsApiList.push("pauseVoice");
					jsApiList.push("stopVoice");
					jsApiList.push("onVoicePlayEnd");
					jsApiList.push("uploadVoice");
					jsApiList.push("downloadVoice");
					jsApiList.push("previewImage");
					wx.config({
					    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
					    appId: localStorage.appid, // 必填，企业号的唯一标识，此处填写企业号corpid
					    timestamp:configdata.timestamp , // 必填，生成签名的时间戳
					    nonceStr: configdata.nonceStr, // 必填，生成签名的随机串
					    signature: configdata.signature,// 必填，签名，见附录1
					    jsApiList: jsApiList // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
					});
				}
			},
			error:function(e){
				//alert(e);
			}
		});
	}
});			

function zdy_chooseImg(callback,moduleName){
	if(is_weixn()){
		wx.chooseImage({
			count: 1,
			sizeType:['compressed'],
		    success: function (res) {
		        lid = res.localIds[0]; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
		        if(navigator.onLine){
		        	wx.uploadImage({
			    	    localId:lid, // 需要上传的图片的本地ID，由chooseImage接口获得
			    	    isShowProgressTips: 1, // 默认为1，显示进度提示
			    	    success: function (res) {
			    	        var serverId = res.serverId; // 返回图片的服务器端ID
			    	        zdy_ajax({
			    				url: '${path}/m/upload/wxUploadPic.do',
			    				data : {
			    					serverId : serverId,
			    					moduleName:moduleName
			    				},
			    				success: function(data,res){
			    					callback(data,res);
			    				}
			    			}); 
			    	    },
			    	    fail: function (res) {
			    	    	
			    	    }
			    	});
		        }else{
		        	offlinedeal()
		        }
			        
		    }
		});
	}else{
		layer.msg("请在微信中打开此系统！");
	}
}
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
