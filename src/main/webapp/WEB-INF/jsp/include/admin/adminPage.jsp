<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>

<nav>
  <ul class="pagination">

    <%-- 跳转首页的按钮 --%>
    <li>
      <a  href="?start=0${page.param}" aria-label="Previous" >
        <span aria-hidden="true">&laquo;</span>
      </a>
    </li>

    <%-- 跳转至上一页的按钮 --%>
    <li>
      <a href="?start=${(page.start-page.count) lt 0?0:(page.start-page.count)}${page.param}" aria-label="Previous" >
        <span aria-hidden="true">&lsaquo;</span>
      </a>
    </li>

    <%-- 如果总页数小于等于8次，则显示所有的页面跳转按钮 --%>
    <c:if test="${page.totalPage le 8}" var="result" scope="page">

      <c:forEach begin="0" end="${page.totalPage-1}" varStatus="status">

        <li>
          <a href="?start=${status.index*page.count}${page.param}">${status.count}</a>
        </li>

      </c:forEach>

    </c:if>

    <%-- 如果总页数大于8次，则显示所有的页面跳转按钮 --%>
    <c:if test="${page.totalPage gt 8}" var="result" scope="page">
      <%-- 前4页 --%>
      <c:forEach begin="0" end="3" varStatus="status">

        <li>
          <a href="?start=${status.index*page.count}${page.param}">${status.count}</a>
        </li>

      </c:forEach>

      <%-- 中间页码输入框 --%>
      <li>
        <a><input class="pageNumINput" style="height: 20px;width: 50px" onkeypress="getkey();"></a>
      </li>

      <%-- 后4页 --%>
      <c:forEach begin="${page.totalPage-4}" end="${page.totalPage-1}" varStatus="status">

        <li>
          <a href="?start=${status.index*page.count}${page.param}">${page.totalPage - 4 + status.count}</a>
        </li>

      </c:forEach>

    </c:if>

    <%-- 跳转至下一页的按钮 --%>
    <li>
      <a href="?start=${(page.start+page.count) gt page.last?page.last:(page.start+page.count)}${page.param}" aria-label="Next">
        <span aria-hidden="true">&rsaquo;</span>
      </a>
    </li>

    <%-- 跳转至最后一页的按钮 --%>
    <li>
      <a href="?start=${page.last}${page.param}" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
  </ul>
  <br>
</nav>