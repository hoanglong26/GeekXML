<%-- 
    Document   : ranking
    Created on : Oct 21, 2017, 2:58:06 PM
    Author     : hoanglong
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>

<c:set var="topGames" value="${requestScope.TOP_GAME}" />
<c:import var="xsldoc" url="WEB-INF/xslt/GameList.xsl" />

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <link rel="shortcut icon" href="content/img/favicon.ico" />
        <title>GeeK - Where players become gamers</title>
        <link href="content/css/HomePage.css" rel="stylesheet" type="text/css">
        <link href="content/css/article.css" rel="stylesheet" type="text/css">
        <link href="content/css/ranking.css" rel="stylesheet" type="text/css">

    </head>
    <body onload="picSwap()">

        <div class="background">
            <div class="container" style="background-image:url(content/img/test.png);">
                <c:import url="header.jsp" charEncoding="UTF-8" />

                <div>
                    <table>
                        <caption>
                            <h1>Bảng xếp hạng game</h1>
                        </caption>
                        <thead>
                            <tr>
                                <th>HẠNG</th>
                                <th>ẢNH BÌA</th>
                                <th>TÊN</th>
                                <th>NỀN TẢNG</th>
                                <th>ĐÁNH GIÁ</th>
                            </tr>
                        </thead>
                        <tbody>
                            <x:transform xml="${topGames}" xslt="${xsldoc}" />
                        </tbody>
                    </table>
                </div>

                <div id="myModal" class="modal">

                    <!-- The Close Button -->
                    <span class="close" onclick="document.getElementById('myModal').style.display = 'none'">&times;</span>

                    <!-- Modal Content (The Image) -->
                    <div class="modal-content">
                        <img id="img01">
                        <h1>haaaaaaaaaaaa</h1>
                    </div>

                    <!-- Modal Caption (Image Text) -->
                    <div id="caption"></div>
                </div>


                <c:import url="footer.jsp" charEncoding="UTF-8" />
            </div>
        </div>

        <script type="text/javascript" src="content/js/Geek.js"></script>
        <script>
                        var modal = document.getElementById('myModal');
                        var modalImg = document.getElementById("img01");
                        var captionText = document.getElementById("caption");

                        var elts = document.getElementsByClassName('gameRow');
                        for (var i = elts.length - 1; i >= 0; --i) {
                            elts[i].addEventListener("click", function () {
                                modal.style.display = "block";
                                var tdChild = this.childNodes[2];
                                var imgChild = tdChild.childNodes[0];
                                modalImg.src = imgChild.src;
                                captionText.innerHTML = imgChild.alt;
                            });
                        }

                        var span = document.getElementsByClassName("close")[0];
                        // When the user clicks on <span> (x), close the modal
                        span.onclick = function () {
                            modal.style.display = "none";
                        };

        </script>
    </body>
</html>
