<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="群标签">
		<title>群标签</title>
		<style type="text/css">
			.uList {
				width: 100%;
				background: #FFF;
				font-size: .24rem;
				display: flex;
				flex-wrap: wrap;
				box-sizing: border-box;
				padding-bottom: .30rem;
			}

			.uList li {
			    padding: 0 .15rem;
				line-height: 2;
				text-align: center;
				border-radius: .1rem;
				border:1px #666 solid;
			}

			.uList li.bg {
				background: #0f88eb;
				color: #fff;
				border-radius: .1rem;
				border:0;
			}

			.uList li {
				margin-top: .30rem;
				margin-left: .30rem;
			}

			.M-footer {
				width: 100%;
				height: 1rem;
				text-align: center;
				line-height: 1rem;
				background: #0f88eb;
				color: #fff;
				font-size: .30rem;
				position: fixed;
				bottom: 0;
				left: 0;
			}
		</style>
	</head>
	<body style="background: #f2f3f4;">
		<ul class="uList">
			
		</ul>
		<div class="M-footer active_A">
			完成
		</div>
		<script type="text/javascript">
		var qid = window.location.href.split('=')[1]
		zdy_ajax({
			url: _path+"/im/m/selAllFavs.do",
		    showLoading:false,
		    success: function(data,res){
				console.log(data)
				var ids = []
				$.each(data,function(index){
					ids.push(data[index].id) 
					var str ='<li id="'+data[index].id+'">'+data[index].listValue+'</li>'
					$('.uList').append(str)
				})
				$('li').on("click",function(){
					if(!$(this).hasClass('bg')){
						$(this).addClass('bg')
					}else{
						$(this).removeClass('bg')
					}
				})
				if(qid){
					var label = getSessionStorageValue('xgqbq')
					console.log(label)
					 for(var k =0;k<label.length;k++){
						for(var n =0;n<ids.length;n++){
						 		if(ids[n] == label[k]){
						 			$("#"+ids[n]).addClass('bg')
						 			
						 		}

						}
					}
					$('.M-footer').on("click",function(){
						var arr = []
						var bg = $('.bg')
						if(bg.length){
							$.each(bg,function(i){
								var id =bg[i].id;
								arr.push(id)
							})
							var str = kkk(arr)
							zdy_ajax({
								url: _path+"/im/m/updateGroupFavs.do",
							    showLoading:false,
							    data:{
							    	groupId:qid,
							    	favIds:str
							    },
							    success: function(data,res){
									layer.msg(res.msg)
									window.location.href=_path+"/wx/businessFriend/msg.do?qid="+qid;	
								},
							    error:function(e){
								   //alert(e);
							    }
							}); 
						}else{
							layer.msg("请选择群标签")
						}
						
					    }) 
				}else{
					var c = _path+"/wx/businessFriend/szq.do?cc="+999;	
					 kkkk(c)
				}
				
			},
		    error:function(e){
			   //alert(e);
		    }
		}); 
		 function kkk(label){//返回id
			 var k =''
			 for(var i = 0;i<label.length;i++){
				 k+=label[i]+','
			 }
			 var len = k.length
				var str2= k.slice(0,len-1);
	    	    console.log(str2)
	    	    return str2
		 }
	    function kkkk(c){
	    	 $('.M-footer').on("click",function(){
					var arr = []
					var bg = $('.bg')
					if(bg.length){
						$.each(bg,function(i){
							var id =bg[i].id;
							var label =bg[i].innerText;
							console.log( label)
							var obj = {id:id,label:label}
							arr.push(obj)
						})
						setSessionStorageValue("label",arr)
						window.location.href=c
					}else{
						layer.msg("请选择群标签")
					}
					
				    }) 
	    }
		</script>
	</body>

</html>


