<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>

<script>
  function search() {
    var currentParh = window.location.pathname;
    var path = currentParh.substring(12,currentParh.length);

    if (event.keyCode == 13){
      var totalPage = ${totalPage};
      var inputNumber = $(".searchPageNumINput").val();
      var keyword = '${keyword}'

      if (inputNumber <= 0 || inputNumber > totalPage){
        alert("您要查询的数据不存在");
        return false;
      }

      var pageNo = inputNumber - 1;

      window.location.href = path + "?keyword=" + keyword + "&pageNo=" + pageNo;
    }
  }
</script>
<nav style="text-align: center">
  <span style="color: #A8A8A8">共有${total}条记录符合您的搜索条件，共计${totalPage}页</span><br>
  <ul class="pagination">

    <%-- 如果总页数小于等于8次，则显示所有的页面跳转按钮 --%>
    <c:if test="${totalPage le 8}" var="result" scope="page">

      <c:forEach begin="0" end="${totalPage-1}" varStatus="status">

        <li>
          <a href="?keyword=${keyword}&pageNo=${status.index}" methods="post">${status.count}</a>
        </li>

      </c:forEach>

    </c:if>

    <%-- 如果总页数大于8次，则显示所有的页面跳转按钮 --%>
    <c:if test="${totalPage gt 8}" var="result" scope="page">
      <%-- 前4页 --%>
      <c:forEach begin="0" end="3" varStatus="status">

        <li>
          <a href="?keyword=${keyword}&pageNo=${status.index}">${status.count}</a>
        </li>

      </c:forEach>

      <%-- 中间页码输入框 --%>
      <li>
        <a><input class="searchPageNumINput" style="height: 15px;width: 50px" onkeypress="search();"></a>
      </li>

      <%-- 后4页 --%>
      <c:forEach begin="${totalPage-4}" end="${totalPage-1}" varStatus="status">

        <li>
          <a href="?keyword=${keyword}&pageNo=${status.index}">${totalPage - 4 + status.count}</a>
        </li>

      </c:forEach>

    </c:if>

  </ul>
  <br>
</nav>