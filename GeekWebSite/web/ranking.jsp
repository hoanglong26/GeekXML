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
        <title>GeeK - Trang tin công nghệ và game</title>
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
                    <button class="btn">Xem thêm</button>

                </div>

                <div id="myModal" class="modal">

                    <!-- The Close Button -->
                    <span class="close" onclick="document.getElementById('myModal').style.display = 'none'">&times;</span>

                    <!-- Modal Content (The Image) -->
                    <div class="modal-content">
                        <div class="game-modal-info">
                            <div class="col-xs-2">
                                <a id="game-link-img" href="https://www.gamerankings.com/wii/915692-super-mario-galaxy/index.html">
                                    <img id="img01">
                                </a>
                            </div>
                            <div class="col-xs-10">
                                <div><a id="game-link" href="https://www.gamerankings.com/wii/915692-super-mario-galaxy/index.html">
                                        <span class="tag">Tên game</span>
                                        <span class="game-modal-text" id="game-name">Alibaba và 70 tên cướp</span>
                                    </a></div>
                                <div><span class="tag">Nền tảng</span><span class="game-modal-text" id="game-platform">NES</span></div>
                                <div><span class="tag">Nhà phát hành</span><span class="game-modal-text" id="game-pulisher">Nintendo, 2001</span></div>
                                <div><span class="tag">Điểm</span><span class="game-modal-text" id="game-overallRating">97% với 70 đánh giá</span></div>
                            </div>
                        </div>

                        <table style="width: 80%;">
                            <caption>
                                <h1>Danh sách các đánh giá</h1>
                            </caption>
                            <thead>
                                <tr>
                                    <th>NGƯỜI ĐÁNH GIÁ</th>
                                    <th>ĐIỂM</th>
                                    <th>NGÀY ĐÁNH GIÁ</th>
                                </tr>
                            </thead>
                            <tbody id="rating-list">
                                <tr>
                                    <td>test</td>
                                    <td>test</td>
                                    <td>test</td>
                                </tr>
                            </tbody>
                        </table>
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
                        var nameText = document.getElementById("game-name");
                        var pulisherText = document.getElementById("game-pulisher");
                        var platformText = document.getElementById("game-platform");
                        var overallRatingText = document.getElementById("game-overallRating");
                        var linkHref = document.getElementById("game-link");
                        var linkImgHref = document.getElementById("game-link-img");
                        var ratingList = document.getElementById("rating-list");

                        var elts = document.getElementsByClassName('gameRow');
                        for (var i = elts.length - 1; i >= 0; --i) {
                            elts[i].addEventListener("click", function () {
                                modal.style.display = "block";
                                var tdImgChild = this.childNodes[2];
                                var imgChild = tdImgChild.childNodes[0];
                                modalImg.src = imgChild.src;

                                var hiddenGameInfo = tdImgChild.childNodes[1];
                                console.log(hiddenGameInfo);
                                linkImgHref.href = hiddenGameInfo.dataset.link;
                                linkHref.href = hiddenGameInfo.dataset.link;
                                nameText.innerHTML = hiddenGameInfo.dataset.name;
                                platformText.innerHTML = hiddenGameInfo.dataset.platform;
                                pulisherText.innerHTML = hiddenGameInfo.dataset.pulisher;
                                overallRatingText.innerHTML = hiddenGameInfo.dataset.score + " với " + hiddenGameInfo.dataset.vote;


                                ratingList.innerHTML = "";
                                var tdNameChild = this.childNodes[3];
                                var arrInput = tdNameChild.getElementsByTagName("input");
                                for (var i = 0; i < arrInput.length; i++) {
                                    ratingList.innerHTML += "<tr>\n" +
                                            "<td>" + arrInput[i].dataset.reviewer + "</td>" +
                                            "<td>" + arrInput[i].dataset.score + "</td>" +
                                            "<td>" + arrInput[i].dataset.reviewdate + "</td>" +
                                            "</tr>";
                                }
                            });
                        }

                        var span = document.getElementsByClassName("close")[0];
                        // When the user clicks on <span> (x), close the modal
                        span.onclick = function () {
                            modal.style.display = "none";
                        };
        </script>

        <script>
            function applyXSLforGameList(xml, start, end, noInitElem, elemId) {
                var xslPath = realPath + "WEB-INF/xslt/RatinngList.xsl";
                loadXMLDoc(xslPath);
                xhttp.onreadystatechange = function () {
                    if (this.readyState === 4 && this.status === 200) {
                        xsl = this.responseXML.childNodes[1];
                        xsltProcessor = new XSLTProcessor();
                        xsltProcessor.importStylesheet(xsl);
                        resultDocument = xsltProcessor.transformToFragment(xml, document);


                        document.getElementById(elemId).innerHTML = '';
                        document.getElementById(elemId).appendChild(div);
                    }
                }
                ;
            }
        </script>

    </body>
</html>
