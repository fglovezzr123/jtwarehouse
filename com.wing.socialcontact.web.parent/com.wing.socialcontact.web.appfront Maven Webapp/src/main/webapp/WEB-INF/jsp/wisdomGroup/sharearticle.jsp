<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">文章详情</title>
        <link rel="stylesheet" href="${path}/resource/css/text-design.css" />
		
    </head>
    
  
     <body>
    	
        <div class="wrapper"   id="alldetail">
        <!-- <h5></h5>
          <p id="articleBlock">
             
            
          </p> -->

       </div>
       
       <div class='share' id="share">
          <div>
             <div class="active_A" style="width:100%;height:100%;background:white" id="tocollect">   收藏</div>
          </div>
          <div class="active_A" onclick="fx();">分  享</div>
          <br class="clear"/>
       </div>
       <div class="com-backdrop" style="display: none;"></div>
	   <div class="tips-s-img" style="display: none;"></div>
	   <style>
	   
	   .zt{
	   		font-size:12px;
	   		height:10px;
	   }
	   .zt span:nth-child(1){
		    float:left;
		    width:40%;
		    text-align:left; 
		}
	   .zt span:nth-child(2){
		    float:left;
		    width:20%;
		    text-align:center; 
		}
	   .zt span:nth-child(3){
		    float:right;
		    width:40%;
		    text-align:right; 
		}
	   
	   </style>
       <script>
       		var fx_flag=false;
            var id="${id}";
            var cOrn=false; 
            
            $(function(){
            	if(type!=1){
            		$("#share").remove();
            	}
            })
             //通过文章ID获取文章
             zdy_ajax({
				   url:_path+"/library/m/detail.do",
				   showLoading:true,
				   data:{
					   'libraryid':id
				   },
				   success:function(data,res){
					   console.log(data);
					   var str="";
					   id=data.id;
					  if(data.iscollection){
						  cOrn=data.iscollection;
						  $('#tocollect').text('已收藏');
					  };
					  str+='<h5>'+data.title+'</h5>';
					  str+='<div class="zt"><span>'+data.tag+'</span><span>'+data.readtimes+'人阅读</span><span>'+formatDate(new Date(data.createTime))+'</span></div>'
					  if(!isEmpty(data.path)){
					  str+='<div class="zt" style="float:right;"><a style="float:right;color:0000FF" href="'+_oss_url+data.path+'">'+data.filename+'</a></div>'
					  }
					  str+='</br><p id="articleBlock">'+data.content+'</p>';  
						  
					   $("#alldetail").append(str);       
					             
						  
					 // jQuery("#articleBlock").append(data.content);
					  //jQuery("#articleBlock").append('<h5>'+data.title+'</h5>');
				   },
				   error:function(data){
					   
				   }
					
				});
             //添加或取消收藏
             $('#tocollect').click(function(){
            	if(!cOrn){
            		
            	 zdy_ajax({
  				   url:_path+"/mycollection/m/add.do",
  				   showLoading:false,
  				   data:{
  					   'id':(window.location.search.substr(1).split('='))[1],
  					   'type':1
  				   },
  				   success:function(data,bc){	   
  						console.log(data) 
  					   layer.msg(bc.msg);
  						cOrn=true;
  				   },
  				   error:function(data){
  					   
  				   }
  					
  				});
            	 $('#tocollect').text('已收藏');
            	}else if(cOrn){
            		 zdy_ajax({
        				   url:_path+"/mycollection/m/del.do",
        				   showLoading:false,
        				   data:{
        					   'id':(window.location.search.substr(1).split('='))[1],
        					   'type':1
        				   },
        				   success:function(data,bc){
        					     console.log(data) 
        						 layer.msg(bc.msg);
        					     cOrn=false;
        				   },
        				   error:function(data){
        					   
        				   }
        					
        				});
            		 $('#tocollect').text('收藏');
            	}; 
             });
             
             $(function(){
            	//绑定遮罩层关闭事件
         		$(".com-backdrop").click(function(){
         			var obj=$(this);
         			$(".tips-s-img").slideUp("slow",function(){
            			obj.hide();
         			});
         		});
             	$(".tips-s-img").click(function(){
             		$(".com-backdrop").click();
             	}); 
             });
             
           //分享
         	var fx=function(){
        	   	if(is_weixn()){
	         		fx_flag=true;
	         		wx_fx();
        	   	}else{
        	   		layer_msg("分享功能只能在微信端使用");
        	   	}
         	};
         	
         	var wx_fx=function(){
         		load_signature();
         	}
         	
         	var load_signature=function(ofid){
         		var _url=self.location.href;
         		if(_url.indexOf("#")!=-1){
         			_url=_url.substring(0,_url.indexOf("#"));
         		}
         		zdy_ajax({
         			url: '${path}/m/sys/getSignature.do',
         			type: 'post',
         			dataType: 'json',
         			data:{
         				url:_url
         			},
         			success: function(result){
         				if(!isEmpty(result) && !isEmpty(result.signature)){
         					$(".com-backdrop").show();
         	         		$(".tips-s-img").slideDown("slow");
         					init_wx(result);
         				}
         			}
         		});
         	}
         	
         	var wx_obj=null;
         	var init_wx=function(obj){
         		var appid=isEmpty(obj.appId)?'wx34c5f34afae4c27b':obj.appId;
         		wx.config({
         		    debug: false,
         		    appId: appid,   
         		    //appId: 'wx34c5f34afae4c27b',
         		    timestamp: obj.timestamp,
         		    nonceStr: obj.nonceStr,
         		    signature: obj.signature,
         		    jsApiList: ['checkJsApi','onMenuShareTimeline','onMenuShareAppMessage','hideMenuItems','hideAllNonBaseMenuItem','showMenuItems','showAllNonBaseMenuItem']
         		});
         		
         		wx.ready(function(){
         			wx.checkJsApi({
         			    jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage'],
         			    success: function(res) {
         			        if(res.checkResult.onMenuShareTimeline == false){
         			        	alert("您的微信版本过低，不支持分享功能");
         			        }else{
         			        	send_fx();
         			        }
         			        if(res.checkResult.onMenuShareAppMessage == false){
         			        	alert("您的微信版本过低，不支持分享功能");
         			        }else{
         			        	send_fx_f();
         			        }
         			        //if(res.checkResult.chooseImage == true){
         			        //	choose_img();
         			        //}else{
         			        //}
         			    },
         			    fail: function(res){
         			    	alert(res.errMsg);
         			    }
         			});
         			//隐藏按钮
         			hideWxMenus();
         			if(fx_flag){
         				showWxMenus();
         			}
         			// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
         		});
         			
         		wx.error(function(res){
         			alert(res.errMsg);
         		    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
         		});
         	}
         	
         	var send_fx=function(ofid){
         		var self_location=self.location.href+"&viewFlag=1";
         		var _link=self_location.replace("type=1&","");
         		var _title=$("#articleBlock").find("h5").eq(0).text();
         		var _imgUrl=$("#articleBlock").find("img").eq(0).attr("src");
         		wx.onMenuShareTimeline({
         		     title: _title, // 分享标题
         		     link: _link, // 分享链接
         		     imgUrl: _imgUrl, // 分享图标
                     success: function (res) {
                         alert_back("分享成功!!!",function(){
                        	 $(".com-backdrop").click();
                         });
                     },
                     cancel: function (res) {
                     	 alert("分享取消!!!");
                     },
                     fail: function (res) {
                         alert('wx.onMenuShareTimeline:fail: '+JSON.stringify(res));
                     }
         		});
         		//setTimeout('layer.msg("请点击屏幕右上角菜单中的“分享到朋友圈”",10,1);',1000);
         	}
         	
         	var send_fx_f=function(ofid){
         		var self_location=self.location.href+"&viewFlag=1";
         		var _link=self_location;
         		var _title=$("#articleBlock").find("h5").eq(0).text();
         		var _imgUrl=$("#articleBlock").find("img").eq(0).attr("src");
         		var _desc=_title;
         		wx.onMenuShareAppMessage({
         		    title: _title, // 分享标题
         		    desc: _desc, // 分享描述
         		    link: _link, // 分享链接
         		    imgUrl: _imgUrl, // 分享图标
         		    type: 'link', // 分享类型,music、video或link，不填默认为link
         		    success: function () {
         		        // 用户确认分享后执行的回调函数
         		    	alert_back("分享成功!!!",function(){
                       		$(".com-backdrop").click();
                        });
         		    },
         		    cancel: function () {
         		        // 用户取消分享后执行的回调函数
         		    	alert("分享取消!!!");
         		    }
         		});
         	}
         	
         	var hideWxMenus=function(){
         		//wx.hideAllNonBaseMenuItem();
         		wx.hideMenuItems({
         		    menuList: ['menuItem:share:appMessage','menuItem:share:timeline','menuItem:share:qq','menuItem:share:QZone','menuItem:share:weiboApp','menuItem:share:facebook','menuItem:share:QZone'] // 要隐藏的菜单项，所有menu项见附录3
         		});
         	}

         	var showWxMenus=function(){
         		wx.showMenuItems({
         		    menuList: ['menuItem:share:appMessage','menuItem:share:timeline'] 
         		});
         	}
         
         	
         	
         	function formatDate(now) { 
        		var year=now.getFullYear(); 
        		var month=now.getMonth()+1;
        		if(month<10){
        			month="0"+month;
        		}
        		var day=now.getDate();
        		if(day<10){
        			day="0"+day;
        		}
        		var hour=now.getHours();
        		if(hour<10){
        			hour="0"+hour;
        		}
        		var minute=now.getMinutes();
        		if(minute<10){
        			minute="0"+minute;
        		}
        		var second=now.getSeconds();
        		if(second<10){
        			second="0"+second;
        		}
        		return year +"/"+month+"/"+day+" "+hour+":"+minute; 
        		} 
           
       </script>
    </body>
</html>