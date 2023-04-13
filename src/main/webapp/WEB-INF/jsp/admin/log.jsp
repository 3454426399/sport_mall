<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<title>日志系统</title>

<div class="workingArea">
    <h1 class="label label-info" >日志系统</h1>

    <br>
    <br>

    <form action="admin_log_system_search" method="post" style="float: left">
        <div class="logSearchDiv" style="text-align: left;">
            <wui-date
                    format="yyyy-mm-dd"
                    placeholder="请选择日期（默认当前日期）"
                    id="date1"
                    name="date"
                    btns="{'ok':'确定','now':'此刻'}"
                    ng-model="date"
            >
            </wui-date>

            <input name="keyword" type="text" value="${param.keyword}" placeholder="info" style="width: 220px;height: 30px;border-color: #b2dba1">
            <input name="pageNo" type="hidden" value="0">
            <button  type="submit" class="searchButton" style="background-color: #b2dba1;width: 60px;height: 27px">搜索</button>
        </div>
    </form>


    <form action="admin_log_system_delete" method="post">
        <div class="logDeleteDiv" style="text-align: right;">
            <wui-date
                    format="yyyy-mm-dd"
                    placeholder="请选择日期（默认当前日期）"
                    id="date2"
                    name="date"
                    btns="{'ok':'确定','now':'此刻'}"
                    ng-model="date"
            >
            </wui-date>

            <button  type="submit" class="deleteButton" style="background-color: #b2dba1;width: 60px;height: 27px">删除</button>
        </div>
    </form>

    <br>

    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover  table-condensed">
            <thead>
            <tr class="success">
                <th>时间</th>
                <th>等级</th>
                <th>客户ip地址</th>
                <th>日志详情</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${logs}" var="log">
                <tr>
                    <td>${log.dateTime}</td>
                    <td>${log.level}</td>
                    <td>${log.ipAddress}</td>
                    <td>${log.message}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="pageDiv">
        <%@include file="../include/fore/SearchPage.jsp" %>
    </div>


</div>

<%@include file="../include/admin/adminFooter.jsp"%>
