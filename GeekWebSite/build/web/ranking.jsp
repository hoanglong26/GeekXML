<%-- 
    Document   : ranking
    Created on : Oct 21, 2017, 2:58:06 PM
    Author     : hoanglong
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>

<c:set var="topGames" value="${requestScope.TOP_GAME}" />
<c:import var="xsldoc" url="content/xslt/GameList.xsl" />

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
    <body onload="picSwap();
            bindingModalClick(false);">

        <div class="background">
            <div class="container" style="background-image:url(content/img/test.png);">
                <c:import url="header.jsp" charEncoding="UTF-8" />

                <div>
                    <table id="gameListTable">
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
                    <button class="btn" onclick="getMoreGame()">Xem thêm</button>

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
                        

        </script>

        <script>
            var realPath = '${pageContext.request.contextPath}';

            function getMoreGame() {
                var gameTable = document.getElementById("gameListTable");
                var lastRow = gameTable.rows[gameTable.rows.length - 1];
                var idOfLastRow = lastRow.childNodes[1];
                var from = parseInt(idOfLastRow.textContent) + 1;

//                from = 21;
//                localStorage.setItem("geek_list_game_from_21", null);

                initStorageTimeout(from);
                var tableRef = document.getElementById('gameListTable').getElementsByTagName('tbody')[0];
                console.log(tableRef);
                saveGameListData(from, realPath, tableRef);
                var xml = localStorage.getItem("geek_list_game_from_" + from);
                applyXSL(xml, realPath, "/content/GameList.xsl", tableRef);
//                tableRef.appendChild()
//// Insert a row in the table at the last row
//                var newRow = tableRef.insertRow(tableRef.rows.length);
//
//// Insert a cell in the row at index 0
//                var newCell = newRow.insertCell(0);
//
//// Append a text node to the cell
//                var newText = document.createTextNode('New row');
//                newCell.appendChild(newText);
            }
        </script>

    </body>
</html>
