<%-- 
    Document   : ranking
    Created on : Oct 21, 2017, 2:58:06 PM
    Author     : hoanglong
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>

<c:set var="newestArticles" value="${requestScope.NEWEST_ARTICLES}" />
<c:set var="lastArticle" value="${requestScope.LAST_ARTICLE}" />
<c:import var="xsldocNewest" url="content/xslt/NewestArticles.xsl" />

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
            getArticleOnLoad();
            hightlightMenuItem('Trang chủ');">
        <div class="background">
            <div class="container" style="background-image:url(content/img/test.png);">

                <c:import url="header.jsp" charEncoding="UTF-8" />

                <div class="content">

                    <div class="boxTag">
                        <span class="tag">Tin mới nhất</span>
                    </div>

                    <div class="accordian">
                        <x:transform xml="${newestArticles}" xslt="${xsldocNewest}" />
                    </div>

                    <div class="boxTag">
                        <span class="tag">Các tin khác</span>
                    </div>

                    <div id="" class="startnews">
                        <ul id="otherArticleList">
                            <%--<x:transform xml="${otherArticles}" xslt="${xsldocOther}" />--%>
                        </ul>
                        <button class="btn btnMore" onclick="getMoreArticle(null)">Xem thêm</button>
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

            function getArticleOnLoad() {
                var lastArticleId = ${lastArticle};
                if (sessionStorage.getItem("lastArticleId") === null) {
                    sessionStorage.setItem("lastArticleId", lastArticleId);
                } else {
                    if (sessionStorage.getItem("lastArticleId") !== lastArticleId) {
                        sessionStorage.clear();
                        sessionStorage.setItem("lastArticleId", lastArticleId);
                    }
                }
                getMoreArticle(6);
            }

            function getMoreArticle(from) {
                if (from === null) {
                    from = parseInt(sessionStorage.getItem('article_index')) + 10;
                } else {
                    from = from + 1;

                }
                sessionStorage.setItem("article_index", from);

                var ulRef = document.getElementById('otherArticleList');
                if (sessionStorage.getItem("geek_list_article_from_7") !== null) {
                    loadMoreArticle(ulRef, from);
                } else {
                    saveArticleListData(from, realPath, ulRef);
                }

                //future load
                saveArticleListData(from + 10, realPath, ulRef);
            }
        </script>

    </body>
</html>
