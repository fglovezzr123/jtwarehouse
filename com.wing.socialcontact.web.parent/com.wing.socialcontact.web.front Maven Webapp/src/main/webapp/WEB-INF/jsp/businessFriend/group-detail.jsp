<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0" name="viewport">
        <title id="title">群详情</title>
        <link rel="stylesheet" href="${path}/resource/css/manageGroupChat.css?v=${sversion}"/>
        <style>
            .groupTag{
               line-height:0.8rem;
               box-sizing:border-box;
               padding-left:5%;
               padding-right:5%
            }
            .groupTag div:nth-child(1){
               width:30%;
               float:left;
               height:0.8rem;
             }
             .groupTag div:nth-child(2){
               width:70%;
               float:left;
             
             }
           .groupTag  .groupTag-tags span{
                display:inline-block;
                padding-left:0.1rem;
                padding-right:0.1rem;
              
                
             }
              .content-item  #groupImage{
                   padding-right:0;
                   height:0.7rem;
                   width:0.7rem;
                   margin-top:0.05rem;
             }
             .content-item  #groupImage img{
             width:100%;
             heigh:100%
             }
             .content-item .con-r{
                padding-right:0;
                background:none;
                overflow: hidden;
				text-overflow: ellipsis;
				white-space: nowrap;
                
              }
        </style>
    </head>
    <body>
        <div class="wrapper">
             <div class="F-ROW">
             		
                 <%-- <div class="icons active_A jr">
                     <img src="${path}/resource//img/icons/qunliaoguanli_03.png"/>
                 </div>
                 <div class="icons active_A sr">
                     <img src="${path}/resource//img/icons/subtract.png"/>
                 </div> --%>
                 <div style="clear:both"></div>
             </div>


              <div class="content">     
                 <div class="content-item bottom-border active_A clear" >
                     <div class="con-l">群组名称</div>
                     <div class="con-r mc"></div>
                   
                 </div>

                 <div class="content-item bottom-border active_A clear">
                     <div class="con-l">群图片</div>
                     <div class="con-r" id="groupImage"></div>
                    
                 </div>

                 <div class="content-item bottom-border active_A clear">
                     <div class="con-l" style="float:left">群主名称</div>
                     <div class="con-r qzm"></div>
                </div>
                <div class="groupTag">
                     <div>群标签</div>
                     <div class="groupTag-tags">
                     </div>
                     <br style="clear:both"/> 
                </div>         
             </div>  
             <div class="deleteAndQuit active_A">申请加入</div>
        </div>
		<script type="text/javascript">
		     var userId = '<%=userid %>';
	    	 var qid = window.location.href.split('=')[1]
	    	 zdy_ajax({
					url: _path+"/im/m/selGroupInfoById.do",
				    showLoading:false,
				    data:{
				    	groupId:qid	
				    },
					success: function(data,res){
						console.log(res)
						if(res.code == 0){
							var main_user = data.groupInfo.mainUser;
							var groupType = data.groupInfo.groupType;
							var groupName = data.groupInfo.groupName;
							var groupDesc = data.groupInfo.groupDesc;
							var headPortrait = data.groupInfo.headPortrait
							if(!headPortrait){
								headPortrait = '${path}/resource/img/icons/qun.png'
							}
							var img = '<img src="'+headPortrait+'"/>'
							$(".mc").html(groupName)
							$(".ms").html(groupDesc)
							$('#groupImage').html(img)
							$.each(data.users,function(index){
									var str  = '<div class="icons">'+
					                     '<img src="'+data.users[index].head_portrait+'" follow_user="'+data.users[index].user_id+'"/>'+
					                       '<i> '+data.users[index].nickname+' </i> '+    
					                 ' </div>'
					                 $('.F-ROW').prepend(str)
								
				                 if(data.users[index].user_id == main_user){
				                	 $('.qzm').html(data.users[index].nickname)
				                 }
							})
							$.each(data.favs,function(index){
									var str  = '<span>'+data.favs[index].list_value+'</span>'
					                 $('.groupTag-tags').append(str)
								
				                 /* if(data.users[index].user_id == main_user){
				                	 $('.qzm').html(data.users[index].nickname)
				                 } */
							})
						}	
					},
				    error:function(e){
					   //alert(e);
				    }
				});

	    	 $(".deleteAndQuit").on('click',function(){
	    		 window.location.href="${path}/wx/businessFriend/applyToAddGroup.do?groupId="+qid;
	    	 })
		</script>
    </body>
</html>