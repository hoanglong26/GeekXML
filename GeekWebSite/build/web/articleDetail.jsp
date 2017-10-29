<%-- 
    Document   : ranking
    Created on : Oct 21, 2017, 2:58:06 PM
    Author     : hoanglong
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>

<c:set var="articleDetail" value="${requestScope.ARTICLE_DETAIL}" />
<c:import var="xsldoc" url="content/xslt/ArticleDetail.xsl" />

<%--<c:set var="otherArticles" value="${requestScope.OTHER_ARTICLES}" />
<c:import var="xsldocOther" url="content/xslt/OtherArticles.xsl" />--%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">

        <link rel="shortcut icon" href="content/img/favicon.ico" />
        <title>GeeK - Trang tin công nghệ và game</title>
        <link href="content/css/HomePage.css" rel="stylesheet" type="text/css">
        <link href="content/css/content.css" rel="stylesheet" type="text/css">
        <link href="content/css/article.css" rel="stylesheet" type="text/css">

    </head>
    <body onload="picSwap();
            hightlightMenuItem('none');">
        <div class="background">
            <div class="container" style="background-image:url(content/img/test.png);">

                <c:import url="header.jsp" charEncoding="UTF-8" />

                <!-- BEGIN CONTENT-->
                <div class="article">
                    <x:transform xml="${articleDetail}" xslt="${xsldoc}" />

                </div>
                <c:import url="footer.jsp" charEncoding="UTF-8" />
            </div>
        </div>
        <script type="text/javascript" src="content/js/Geek.js"></script>
        <script>

        </script>

        <script>
            var realPath = '${pageContext.request.contextPath}';
            


//            function getMoreArticle(from) {
//                if (from === null) {
//                    from = parseInt(sessionStorage.getItem('article_index')) + 10;
//                } else {
//                    from = from + 1;
//
//                }
//                sessionStorage.setItem("article_index", from);
//
//                var ulRef = document.getElementById('otherArticleList');
//                if (sessionStorage.getItem("geek_list_article_from_7") !== null) {
//                    loadMoreArticle(ulRef, from);
//                } else {
//                    saveArticleListData(from, realPath, ulRef);
//                }
//
//                //future load
////                initStorageTimeout(from + 20);
//                saveArticleListData(from + 10, realPath, ulRef);
//            }
        </script>

    </body>
</html>
