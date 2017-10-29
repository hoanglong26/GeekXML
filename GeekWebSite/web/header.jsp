<%-- 
    Document   : header
    Created on : Oct 21, 2017, 3:01:11 PM
    Author     : hoanglong
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<header>
    <style>
        .search-result{
            position:  absolute;
            display:  block;
            width: 35%;
            margin-top: -5px;
            background-color: #0000009c;
            border-radius: 5px;
        }

        .topper-search-result{
            /*width: 90%;*/
            padding: 10px;
            border-radius: 5px;
            /*margin-bottom:  5px;*/
            margin-top:  5px;
            color:  black;
        }

        .mainbody-search-result{
            width: 100%;
            font-size:  14px;
        }

    </style>
    <div class="top">
        <div class="topcontainer">
            <div style="display: inline-block;">
                <input type="text" placeHolder="Nhập từ khóa" class="search" onkeyup="showResult();" id="key" />
                <span class="ltop">
                    <img src="content/img/search.png"  onMouseDown="showResult();"/>
                </span>

                <div class="startnews search-result" >
                    <ul>
                        <li class="topper topper-search-result" style="width: 90%;">
                            <div class="maintitle mainbody-search-result"><a href="cat.html" title="NGỘ NGHĨNH VỚI HÌNH ẢNH MÈO BÊN MÁY CHƠI GAME">NGỘ NGHĨNH VỚI HÌNH ẢNH MÈO BÊN MÁY CHƠI GAME</a></div>
                            <div class="maincontent mainbody-search-result">
                                Chắc chắn, bạn sẽ phải bất ngờ với những hình ảnh dễ thương của các chú mèo bên cạnh những loại máy chơi game phổ
                                biến nhất hiện nay, từ PS3, Xbox 360, Wii U cho tới Nintendo 3DS, PS Vita...
                            </div>
                        </li>
                        <li class="topper topper-search-result" style="width: 90%;">
                            <div class="maintitle mainbody-search-result"><a href="cat.html" title="NGỘ NGHĨNH VỚI HÌNH ẢNH MÈO BÊN MÁY CHƠI GAME">NGỘ NGHĨNH VỚI HÌNH ẢNH MÈO BÊN MÁY CHƠI GAME</a></div>
                            <div class="maincontent mainbody-search-result">
                                Chắc chắn, bạn sẽ phải bất ngờ với những hình ảnh dễ thương của các chú mèo bên cạnh những loại máy chơi game phổ
                                biến nhất hiện nay, từ PS3, Xbox 360, Wii U cho tới Nintendo 3DS, PS Vita...
                            </div>
                        </li>
                    </ul>
                </div>
            </div>


            <span class="rtop">
                <a href="https://www.facebook.com/" ><img src="content/img/facebook.png"/></a>
                <a href="http://www.vimeo.com/" ><img src="content/img/vimeo.png"/></a>
                <a href="https://www.yahoo.com/" ><img src="content/img/yahoo.png"/></a>
                <a href="http://store.steampowered.com/" ><img src="content/img/steam.png"/></a>
            </span>
        </div>
    </div>

    <div class="header">
        <a href="Geek.html"><img src="content/img/Geekf.png" alt="Logo" class="logo"/></a>
        <img src="content/img/banner15.png" class="banner" />
        <img src="content/img/banner12.png" id="pic" onMouseOver="picStop(false);" onMouseOut="picStop(true)" class="banner2" />
        <!-- end .header -->
    </div>

    <div class="menu row">
        <ul>
            <li class="zone-2">|</li>
            <li class="mlink zone-2"><a href="DispatcherServlet">Trang chủ</a></li>
            <li class="zone-2">|</li>
            <li class="mlink zone-2"><a href="DispatcherServlet?action=RANKING">Bảng xếp hạng</a></li>
            <li class="zone-2">|</li>
            <li class="zone-1"></li>
        </ul>
    </div>
</header>
