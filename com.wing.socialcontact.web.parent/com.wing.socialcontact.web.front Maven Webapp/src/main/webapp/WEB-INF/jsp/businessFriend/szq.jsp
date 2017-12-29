<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<meta name="keywords" content="创建群聊">
		<title>创建群聊</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/manageGroupChat.css?v=${sversion}" />
		<style type="text/css">
			.box {
				width: 100%;
				font-size: .28rem;
				background: #fff;
				display: flex;
				justify-content: space-between;
				padding-left: 5%;
				padding-right: 5%;
				box-sizing: border-box;
			}	
			.box span {
				padding: .15rem 0;
			}	
			.box i {
				padding: .15rem .30rem;
				align-items: center;
			}
			
			.uList {
				width: 70%;
				display: flex;
				flex-wrap: wrap;
			}
			
			.uList li {
				padding: .15rem .30rem;
			}
			
			.clicking {
				background:url(${path}/resource/img/icons/arrow.png) no-repeat center right;
				background-size:0.36rem 0.36rem;
			}
			.con-r input{
			   width:100%
			}
		</style>
	</head>

	<body>
		<div class="wrapper">
			<div class="content" style="margin-top: 0;">

				<div class="content-item bottom-border active_A clear">
					<div class="con-l">群组名称</div>
					<div class="con-r"><input type="text" name="" id="qm" placeholder="请填写群名称" onpaste="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" 
		        oncontextmenu = "value=value.replace(/[^a-zA-Z0-9\u4E00-\u9FA5]/g,'');" maxlength="15" /></div>
				</div>

				<!-- <div class="content-item bottom-border active_A clear">
					<div class="con-l">群组描述</div>
					<div class="con-r"><input type="text" name="" id="" value="" placeholder="请填写群组描述" /></div>

				</div> -->
				<div class="content-item active_A bottom-border">
					<div style="float:left">私密群</div>
					<div class="switchBtm sm" yes='1' id='sm'></div>
					<div style="clear:both"></div>
				</div>
				<div class="box ">
				<span>群标签</span>
				<ul class="uList">

				</ul>
				<i class="clicking"></i>
			</div>
			</div>
			<div class="clearRecord active_A">完成</div>
		</div>
		<script type="text/javascript">
		    //单聊家人
		    var follow_id = ''
			var yes = 1;
			$('.switchBtm').on('click', function() {
				if ($(this).hasClass('on')) {
					$(this).addClass('switchBtm').removeClass('on')
					$(this).attr('yes', 1)
				} else {
					$(this).addClass('on').removeClass('switchBtm')
					$(this).attr('yes', 2)
				}
				 yes = $(this).attr('yes')
			})
			$('.box').on('click', function() {
				var qm = $('input').eq(0).val();
				var qms = $('input').eq(1).val();
				var objC = {qm:qm,qms:qms,yes:yes}
				setSessionStorageValue('jl',objC)
				window.location.href = _path + "/wx/businessFriend/GroupLabel.do";
			})
			var label = getSessionStorageValue("label")
			//判断是否是单聊加人
			var kkkk = window.location.href.split('=')[1]
			if(kkkk==3333){
				sessionStorage.removeItem('InviteFriends')
			}else{
				var InviteFriends = getSessionStorageValue("InviteFriends")
				if(InviteFriends){
					follow_id=InviteFriends.follow_user
				}
			}
			var ps = window.location.href.split('=')[1]
			if (ps==999) {
				if (label) {
					var objC =  getSessionStorageValue('jl')
					console.log(objC)
					if(objC){
						$('input').eq(0).val(objC.qm)
						$('input').eq(1).val(objC.qms)
						if(objC.yes==2){
							$('.switchBtm').attr('yes',2)
							$('.switchBtm').addClass('on').removeClass('switchBtm')
						}
					}
					$.each(label, function(i) {
						var str = '<li>' + label[i].label + '</li>'
						$('.uList').append(str)
					})
				}
			}
			$('.clearRecord').on('click', function() {
				var qm = $('input').eq(0).val(); //群组名称 */
				var yes  =$('#sm').attr('yes')
					if (qm && label) {
						console.log(yes)
						var qxx = {
							qm: qm,
							label: label,
							yes: yes,
							follow_id:follow_id
						};
						console.log(qxx)
						var qxx = JSON.stringify(qxx);
						sessionStorage.setItem('qunxx', qxx);
						window.location.href = _path + "/wx/businessFriend/myFriend.do";
					} else {
						if(!qm){
							layer.msg("请填写群名")
							return
						}
						if(!label){
							layer.msg("请填写群标签")
							return
						}
					} 
				
			});
			
			$("#qm").on("blur",function(){
				var text = $("#qm").val();
                var pattern = new RegExp("[`~!@#$^&*()=|{}':;' ,\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？%]");
                var result = text.match(pattern);
                if (!result)
                {
                    
                }else{
                var str=$("#qm").val().replace(/[&\|\\\*^￥……%$!！ .<>,:';'（）?"/——"{}【】‘；：”“'。，、？~()#@\-]/g,"");
					alert("含有特殊字符")
					$("#qm").val(str);
                    
                }
			})
		</script>
	</body>

</html>