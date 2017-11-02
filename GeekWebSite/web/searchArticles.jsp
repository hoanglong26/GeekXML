<%-- 
    Document   : ranking
    Created on : Oct 21, 2017, 2:58:06 PM
    Author     : hoanglong
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>

<%--<c:set var="newestArticles" value="${requestScope.NEWEST_ARTICLES}" />
<c:set var="lastArticle" value="${requestScope.LAST_ARTICLE}" />
<c:import var="xsldocNewest" url="content/xslt/NewestArticles.xsl" />--%>

<%--<c:set var="otherArticles" value="${requestScope.OTHER_ARTICLES}" />
<c:import var="xsldocOther" url="content/xslt/OtherArticles.xsl" />--%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <link rel="shortcut icon" href="content/img/favicon.ico" />
        <title>GeeK - Trang tin công nghệ và game</title>
        <link href="content/css/HomePage.css" rel="stylesheet" type="text/css">
        <link href="content/css/content.css" rel="stylesheet" type="text/css">
        <link href="content/css/slider.css" rel="stylesheet" type="text/css">

    </head>
    <body onload="picSwap();
            loadSearchResultPage();
            hightlightMenuItem('none');">
        <div class="background">
            <div class="container" style="background-image:url(content/img/test.png);">

                <c:import url="header.jsp" charEncoding="UTF-8" />

                <div class="content">

                    <div class="boxTag">
                        <span class="tag" style="width: max-content;
                              padding-left: 6px;
                              padding-right: 6px;">Kết quả tìm kiếm cho</span>
                    </div>

                    <div id="" class="startnews" style="width: 100%;">
                        <ul id="otherArticleList">
                            <%--<x:transform xml="${otherArticles}" xslt="${xsldocOther}" />--%>
                        </ul>
                    </div>
                </div>
                <c:import url="footer.jsp" charEncoding="UTF-8" />
            </div>
        </div>
        <script type="text/javascript" src="content/js/Geek.js"></script>
        <script>

        </script>

        <script>
            var realPath = '${pageContext.request.contextPath}';

            function loadSearchResultPage() {
                document.getElementById("key").value = sessionStorage.getItem("searchQuery");
                var query = sessionStorage.getItem("searchQuery");
                if (query === null || query === "null") {
                    query = "";
                }
                document.getElementsByClassName("tag")[0].innerHTML += " \"" + query + "\"";

                var ele = document.getElementById("otherArticleList");
                ele.innerHTML = "";
                var result = sessionStorage.getItem("searchResult");
                var d = document.createElement("div");
                if (result === null || result === "") {
                    result = "</br><h1 style='text-align: center;'>Không có kết quả</h1>";
                } 
                
                d.innerHTML = result;
                ele.appendChild(d);
            }
        </script>

    </body>
</html>
