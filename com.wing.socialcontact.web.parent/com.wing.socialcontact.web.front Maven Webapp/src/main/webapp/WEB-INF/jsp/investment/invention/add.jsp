<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">投资意向表单</title>
        <link rel="stylesheet" href="${path}/resource/css/invest-intention.css?v=${sversion}" /> 
    </head>
    <body>
    	
        <div class="wrapper">
            <div class="invest-intention">请选择类型：
                   <span>
                   <input type ="checkbox" name="className" value="定增投资" />定增投资&nbsp;&nbsp;
                   <input type ="checkbox" name="className" value="上市孵化" />上市孵化&nbsp;&nbsp;
                   <input type ="checkbox" name="className" value="上市并购" />上市并购
                   </span>
            </div>


            <div class="invest-intention">
                   投资额度：
                   <input type="text" name="position"maxlength="11" id="position"  onkeyup="clearNoNum(this)"  placeholder="金额（最多为 9999 9999）"/>
                   <strong>RMB(元)</strong>
            </div>
            <div class="invest-intention">
                   联系人：
       <input type="text" maxlength="10"  name="username" id="username" onblur="value=value.replace(/[^(\`~!#$\^&@%*+\-\(\)=\|\{\}\':;\',\\.\<\>\/?~！#￥……&*（）——{}【】‘；：”“'。，、？\]\[‘'《》·_)|(a-zA-Z0-9\u4E00-\u9FA5)]/g,'')" onpaste="value=value.replace(/[^(\`~!#$\^&@%*+\-\(\)=\|\{\}\':;\',\\.\<\>\/?~！#￥……&*（）——{}【】‘；：”“'。，、？\]\[‘'《》·_)|(a-zA-Z0-9\u4E00-\u9FA5)]/g,'')" oncontextmenu="value=value.replace(/[^(\`~!#$\^&@%*+\-\(\)=\|\{\}\':;\',\\.\<\>\/?~！#￥……&*（）——{}【】‘；：”“'。，、？\]\[‘'《》·_)|(a-zA-Z0-9\u4E00-\u9FA5)]/g,'')" placeholder="请输入联系人姓名" />
            </div>
            <div class="invest-intention"> 联系电话：  <input type="text" maxlength="11" name="phone" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"  id="phone" placeholder="请输入联系电话"/>
            </div>
            
            <div class="invest-intention-head">
                                                    投资意向留言 ：  
            </div>
            
            <div class="invest-intention-text">
                   <textarea  name="message"  id="message" onblur="value=value.replace(/[^(\`~!#$\^&@%*+\-\(\)=\|\{\}\':;\',\\.\<\>\/?~！#￥……&*（）——{}【】‘；：”“'。，、？\]\[‘'《》·_)|(a-zA-Z0-9\u4E00-\u9FA5)]/g,'')" onpaste="value=value.replace(/[^(\`~!#$\^&@%*+\-\(\)=\|\{\}\':;\',\\.\<\>\/?~！#￥……&*（）——{}【】‘；：”“'。，、？\]\[‘'《》·_)|(a-zA-Z0-9\u4E00-\u9FA5)]/g,'')" oncontextmenu="value=value.replace(/[^(\`~!#$\^&@%*+\-\(\)=\|\{\}\':;\',\\.\<\>\/?~！#￥……&*（）——{}【】‘；：”“'。，、？\]\[‘'《》·_)|(a-zA-Z0-9\u4E00-\u9FA5)]/g,'')"  placeholder="请简要说明您对推荐项目的情况，不超过200字" ></textarea>
                   <div><span id="num_txt1">0/200</span></div>
            </div>
    </div>
     <div class="invest-submit active_A">
         <div onclick="save()">提交</div>
         <div>提交后平台服务人员将与您联系</div>
     </div>

    
    <script type="text/javascript">
    
    
    function clearNoNum(obj){
        obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
        obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的  
        obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$","."); 
        obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数  
        if(obj.value.indexOf(".")< 0 && obj.value !=""){//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额 
            obj.value= parseFloat(obj.value); 
        } 
    } 
    	function save(){
    		
    		var obj=document.getElementsByName('className'); //选择所有name="'test'"的对象，返回数组 
    		//取到对象数组后，我们来循环检测它是不是被选中 
    		var s=''; 
    		for(var i=0; i<obj.length; i++){ 
    		if(obj[i].checked) s+=obj[i].value+','; //如果选中，将value添加到变量s中 
    		} 
    		if(s==''){
    			alert("您还没有选择任何分类！");
    			return;
    		}
    		var  classname = s.substring(0, s.length-1);
    		var  position= $("#position").val();
    		if(isEmpty(position)){
				alert_back("投资金额不能为空",function(){
					$("#position").focus();
				});
				return;
			}
    		if (isNaN(position)) { 
	    		alert_back("投资金额请输入数字",function(){
					$("#position").focus();
				});
    		　　　　return ;
    		　　}
    		if(position>99999999){
    			alert_back("投资金额最多为 9999 9999 RMB(元)",function(){
					$("#position").focus();
				});
    		　　　　return ;
    		}
    		if(position<0){
    			alert_back("投资金额不能为负数",function(){
					$("#position").focus();
				});
    		　　　　return ;
    		}
    		var  username= $("#username").val();
    		if(isEmpty(username)){
				alert_back("联系人不能为空",function(){
					$("#username").focus();
				});
				return;
			}
    		var  phone= $("#phone").val();
    		if(isEmpty(phone)){
				alert_back("联系电话不能为空",function(){
					$("#phone").focus();
				});
				return;
			}
    		if(isMobile(phone)){
				alert_back("联系电话格式不正确",function(){
					$("#phone").focus();
				});
				return;
			}
    		var  message= $("#message").val();
    		if(isEmpty(message)){
				alert_back("投资意向留言不能为空",function(){
					$("#message").focus();
				});
				return;
			}
    		
    		zdy_ajax({
 			   showLoading:false,
				url: '${path}/add/investment/m/addInvestment.do',
				data:{
					className:classname,
					position:position,
					username:username,
					phone:phone,
					message:message,
				},
				success: function(data1,data){
					if(data.code == 0){
							alert_back("投资意向已提交",function(){
							    history.go(-1);
			  				})
					}else{
						layer.msg(data.msg);
					}
				},
				error:function(e){
					//alert(e);
				}
			});
    		
    	}
    	$(function(){
    		   $('#message').on('input',function(){
    		       var txtval = $('#message').val().length;
    		       console.log(txtval);
    		      var str = parseInt(200-txtval);
    		       console.log(str);
    		        if(str > 0 ){
    		          $('#num_txt1').html(txtval+'/200');
    		      }else{
    		          $('#num_txt1').html('剩余可输入0字');
    		          $('#message').val($('#message').val().substring(0,200)); //这里意思是当里面的文字小于等于0的时候，那么字数不能再增加，只能是600个字
    		        }
    		        //console.log($('#num_txt').html(str));
    		    });
    		})
    </script>
   
    </body>
</html>