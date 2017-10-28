<%-- 
    Document   : header
    Created on : Oct 21, 2017, 3:01:11 PM
    Author     : hoanglong
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<header>
<div class="top">
    <div class="topcontainer">
        <input type="text" placeHolder="Nhập từ khóa" class="search" onkeyup="showResult();" id="key" />
        <span class="ltop">
            <img src="content/img/search.png"  onMouseDown="showResult();"/>
        </span>

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
