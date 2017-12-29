<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<% String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/"; %>
<%--
	模块：系统管理-- pageContentType管理--添加
--%>

<div style="width: 95%; margin: 0 auto;">
    <form action="pageContentType/add.do" method="post" onsubmit="return validateSubmitForm(this)">
        <table class="table table-bordered ">

            <tr>
                <th width="15%">
                    分类名称：
                </th>
                <td width="85%">
                    <input type="text" name="name" required="required" maxlength="100"/>
                </td>
            </tr>
            <tr>
                <th style="width: 80px">排序：</th>
                <td>
                    <input type="text" name="orderNum"
                           onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                           onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                           class="easyui-numberbox" required="required" maxlength="4"/>
                </td>
            </tr>
            <tr>
                <th>
                    列表页地址
                </th>
                <td>
                    <textarea name="listUrl" rows="1" cols="100" required="required" style="width: 98%;"></textarea>
                </td>
            </tr>
            <tr>
                <th>
                    详情页面url
                </th>
                <td>
                    <textarea name="detailUrl" rows="1" cols="100" required="required" style="width: 98%;"></textarea>
                </td>
            </tr>
            <tr>
                <th>
                    键值
                </th>
                <td>
                    <input type="text" name="contentKey" required="required" maxlength="100"/>
                </td>
            </tr>
            <tr>
                <th></th>
                <td>
                    <div style="margin-top: 10px; margin-bottom: 10px;">
                        <button type="submit" class="btn btn-primary">
                            保存
                        </button>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="button" class="btn clear">
                            清空
                        </button>
                    </div>
                </td>
            </tr>
        </table>

        <input type="hidden" name="datagridId" value="${param.rel }_datagrid"/>
        <input type="hidden" name="currentCallback" value="close"/>

    </form>


</div>

