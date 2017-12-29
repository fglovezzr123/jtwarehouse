<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<style>
* { margin:0; padding:0;}
.hyjb_box {}
.hyjb_dl {float:left; margin: 12px 12px; width:125px; list-style:none; }
.hyjb_dl dl { position:relative; width:125px; height:105px; }
.hyjb_dl dl dt { width:120px; height:100px; border:1px solid #dddddd;}
.hyjb_dl dl dd { position:absolute; right:-10px; top:-10px; }
.hyjb_dl .hyjb_shuru { width:125px; margin-top:-22px;}
.hyjb_dl .hyjb_shuru input { border:none; width:110px; height:26px; background:#f7f7f7; text-align:center;}
.hyjb_tj { float:left; margin:5px 0px 0 5px; }
#dataForm img{width:100%;height:100%;}
</style>

<div style="width:100%;">
	<form  id="dataForm">


		<table class="table table-bordered" style="margin-top:0px;width:99%;">
			<tr>
				<th style="width:15%;">key：</th>
				<td colspan=3>
					${obj.keyWord}
				</td>
			</tr>
			<tr>
				<th>配置功能：</th>
				<th  colspan=3>
					<input type="checkbox"  <c:if test="${1==obj.collection}">checked</c:if> name="collection" value="1"/>收藏
					<input type="checkbox"  <c:if test="${2==obj.thumbUp}">checked</c:if>  name="thumbUp" value="2"/>点赞
					<input type="checkbox"   <c:if test="${4==obj.comment}">checked</c:if> name="comment" value="4"/>评论
					<input type="checkbox"  <c:if test="${8==obj.reward}">checked</c:if>  name="reward" value="8"/>打赏
					<input type="checkbox"  <c:if test="${16==obj.share}">checked</c:if>  name="share" value="16"/>分享
				</th>
			</tr>

		</table>
	</form>
<span class="typefile" id="filePicker"></span>
</div>
<script>

var options = {
		cssPath : 'resource/js/editor/plugins/code/prettify.css',
        filterMode : false,
	    uploadJson:'news/upload.do?ysStyle=YS640&moduleName=personalCustomization',
		width : '320px',
		height:'600px',
		resizeType : 1,
		allowImageUpload : true,
		allowFlashUpload : false,
		allowMediaUpload : false,
		allowFileManager : false,
		syncType:"form",
		afterCreate : function() {
							var self = this;
							self.sync();
						},
		afterChange : function() {
					var self = this;
					self.sync();
		},
		afterBlur : function() {
							var self = this;
							self.sync();
						},
						items:['source', '|', 'fullscreen', 'undo', 'redo', 'print', 'cut', 'copy', 'paste',
								'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
								'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
								'superscript', '|', 'selectall', 'clearhtml','quickformat','|',
								'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
								'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image','flash', 'media', 'table', 'hr', 'emoticons', 'link', 'unlink', '|', 'about']
};
$(function(){
	editor = KindEditor.create('#content',options);
})


</script>