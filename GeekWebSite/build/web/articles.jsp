<%-- 
    Document   : ranking
    Created on : Oct 21, 2017, 2:58:06 PM
    Author     : hoanglong
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>

<c:set var="newestArticles" value="${requestScope.NEWEST_ARTICLES}" />
<c:import var="xsldoc" url="content/xslt/NewestArticles.xsl" />

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
            hightlightMenuItem('Trang chủ');">
        <div class="background">
            <div class="container" style="background-image:url(content/img/test.png);">

                <c:import url="header.jsp" charEncoding="UTF-8" />

                <!-- BEGIN SLIDER-->
                <div class="content">

                    <div class="boxTag">
                        <span class="tag">Tin mới nhất</span>
                    </div>

                    <div class="accordian">
                        <x:transform xml="${newestArticles}" xslt="${xsldoc}" />
                    </div>
                    <!-- END SLIDER-->

                    <div class="boxTag">
                        <span class="tag">Các tin khác</span>
                    </div>


                    <div id="" class="startnews">
                        <ul>
                            <li class="topper">
                                <a href="aoshin.html" title="AO SHIN: THẦN RỒNG SÉT SẮP XUẤT HIỆN TRONG THẾ GIỚI LIÊN MINH HUYỀN THOẠI?"><img src="content/img/aoshin.jpg"></a>
                                <div class="maintitle"><a title="AO SHIN: THẦN RỒNG SÉT SẮP XUẤT HIỆN TRONG THẾ GIỚI LIÊN MINH HUYỀN THOẠI?" href="aoshin.html">AO SHIN: THẦN RỒNG SÉT SẮP XUẤT HIỆN TRONG THẾ GIỚI LIÊN MINH HUYỀN THOẠI?</a></div>
                                <div class="maincontent">Trên thực tế, cái tên Ao Shin đã được nhắc tới từ khá lâu, thế nhưng cho tới thời điểm hiện tại, cộng đồng Liên Minh
                                    Huyền Thoại vẫn chưa được biết nhiều thông tin về vị tướng này.</div>
                            </li>

                            <li class="topper">
                                <a href="kieutrinh.html" title="KIỀU TRINH: NỮ GAME THỦ LIÊN MINH HUYỀN THOẠI XINH ĐẸP"><img src="content/img/hotgirl2.jpg"></a>
                                <div class="maintitle"><a href="kieutrinh.html" title="KIỀU TRINH: NỮ GAME THỦ LIÊN MINH HUYỀN THOẠI XINH ĐẸP">KIỀU TRINH: NỮ GAME THỦ LIÊN MINH HUYỀN THOẠI XINH ĐẸP</a></div>
                                <div class="maincontent">Sở hữu vẻ đẹp ngọt ngào, trong sáng cùng sở thích chơi game, Nguyễn Hoàng Kiều Trinh nhận được nhiều tình cảm từ cộng
                                    đồng mạng.</div>
                            </li>

                            <li class="topper">
                                <a href="ti5.html" title="GIẢI DOTA 2 TI5 TỰ PHÁ VỠ KỈ LỤC TIỀN THƯỞNG CỦA MÌNH"><img src="content/img/ti5.png"></a>
                                <div class="maintitle"><a href="ti5.html" title="GIẢI DOTA 2 TI5 TỰ PHÁ VỠ KỈ LỤC TIỀN THƯỞNG CỦA MÌNH">GIẢI DOTA 2 TI5 TỰ PHÁ VỠ KỈ LỤC TIỀN THƯỞNG CỦA MÌNH</a></div>
                                <div class="maincontent">Trong những ngày vừa qua, cộng đồng DOTA 2 chưa hết vui mừng khi đạt được Immortal Treasure 2 thì nay, mốc tiền thưởng
                                    compendium của giải TI5 đã tiếp tục vượt qua 10 triệu USD.</div>
                            </li>

                            <li class="topper">
                                <a href="cat.html" title="NGỘ NGHĨNH VỚI HÌNH ẢNH MÈO BÊN MÁY CHƠI GAME"><img src="content/img/cat.jpg"></a>
                                <div class="maintitle"><a href="cat.html" title="NGỘ NGHĨNH VỚI HÌNH ẢNH MÈO BÊN MÁY CHƠI GAME">NGỘ NGHĨNH VỚI HÌNH ẢNH MÈO BÊN MÁY CHƠI GAME</a></div>
                                <div class="maincontent">Chắc chắn, bạn sẽ phải bất ngờ với những hình ảnh dễ thương của các chú mèo bên cạnh những loại máy chơi game phổ
                                    biến nhất hiện nay, từ PS3, Xbox 360, Wii U cho tới Nintendo 3DS, PS Vita...</div>
                            </li>

                            <li class="topper">
                                <a href="cat.html" title="NGỘ NGHĨNH VỚI HÌNH ẢNH MÈO BÊN MÁY CHƠI GAME"><img src="content/img/cat.jpg"></a>
                                <div class="maintitle"><a href="cat.html" title="NGỘ NGHĨNH VỚI HÌNH ẢNH MÈO BÊN MÁY CHƠI GAME">NGỘ NGHĨNH VỚI HÌNH ẢNH MÈO BÊN MÁY CHƠI GAME</a></div>
                                <div class="maincontent">Chắc chắn, bạn sẽ phải bất ngờ với những hình ảnh dễ thương của các chú mèo bên cạnh những loại máy chơi game phổ
                                    biến nhất hiện nay, từ PS3, Xbox 360, Wii U cho tới Nintendo 3DS, PS Vita...</div>
                            </li>

                            <li class="topper">
                                <a href="cat.html" title="NGỘ NGHĨNH VỚI HÌNH ẢNH MÈO BÊN MÁY CHƠI GAME"><img src="content/img/cat.jpg"></a>
                                <div class="maintitle"><a href="cat.html" title="NGỘ NGHĨNH VỚI HÌNH ẢNH MÈO BÊN MÁY CHƠI GAME">NGỘ NGHĨNH VỚI HÌNH ẢNH MÈO BÊN MÁY CHƠI GAME</a></div>
                                <div class="maincontent">Chắc chắn, bạn sẽ phải bất ngờ với những hình ảnh dễ thương của các chú mèo bên cạnh những loại máy chơi game phổ
                                    biến nhất hiện nay, từ PS3, Xbox 360, Wii U cho tới Nintendo 3DS, PS Vita...</div>
                            </li>


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
//            function getMoreGame(from) {
//                var gameTable = document.getElementById("gameListTable");
//                var lastRow = gameTable.rows[gameTable.rows.length - 1];
//                var idOfLastRow = lastRow.childNodes[1];
//                if (from !== 0) {
//                    from = parseInt(idOfLastRow.textContent) + 1;
//                } else {
//                    from = from + 1;
//                }
//
////                from = 21;
////                localStorage.setItem("geek_list_game_from_21", null);
//
//                initStorageTimeout(from);
//                var tableRef = document.getElementById('gameListTable').getElementsByTagName('tbody')[0];
//                saveGameListData(from, realPath, tableRef);
//            }
        </script>

    </body>
</html>
