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
            overflow-y: scroll;
            max-height: 600px;
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
                <input type="text" placeHolder="Nhập từ khóa" class="search" onkeyup="doSearch();showResult();" id="key" />
                <span class="ltop">
                    <a href="DispatcherServlet?action=SEARCH"><img src="content/img/search.png" /></a>
                </span>

                <div class="startnews search-result" style="display:none;">
                    <ul id="search-result-zone">

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
        <a href="DispatcherServlet"><img src="content/img/Geekf.png" alt="Logo" class="logo"/></a>
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

    <script>
        var list = ["banner13.png", "banner16.png", "banner12.png", "banner10.jpg"];
        var i = 0;
        var time_id;
        function picSwap() {
            var tag = document.getElementById("pic");
            if (i < 0) {
                i = 0;
            }
            tag.src = "content/img/" + list[i];
            i++;
            i = i % 4;
            time_id = setTimeout("picSwap()", 2000);
        }
        function picStop(flag) {
            if (flag === true) {
                time_id = setTimeout("picSwap()", 2000);
            }
            else {
                clearTimeout(time_id);
            }
        }



    </script>

    <script>
        var xhttp;
        var xDom;
        function getXmlHtmlObj() {
            var xmlHttp = null;
            try {
                xmlHttp = new XMLHttpRequest();
            } catch (e) {
                try {
                    xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
                } catch (ex) {
                    xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
                }
            }
            return xmlHttp;
        }

        function loadData(url)
        {
            xhttp = getXmlHtmlObj();
            if (xhttp === null) {
                alert("Your browser doesn't support Ajax");
                return;
            }

            xhttp.open("GET", url, true);
            xhttp.contentType = "application/xml";
            xhttp.send(null);
        }

        function loadDataPOST(url, param)
        {
            xhttpPOST = getXmlHtmlObj();
            if (xhttpPOST === null) {
                alert("Your browser doesn't support Ajax");
                return;
            }

            xhttpPOST.open("POST", url, true);
            xhttpPOST.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
            xhttpPOST.send(param);
        }



        function getArticleSearchList(query, ele) {
            var realPath = '${pageContext.request.contextPath}';
//            var url = realPath + '/SearchArticleServlet?query=' + query;
            var url = realPath + '/SearchArticleServlet';
            loadDataPOST(url, query);
            xhttpPOST.onreadystatechange = function () {
                if (this.readyState === 4 && this.status === 200) {
                    var xmlRes = this.responseXML;
                    if (xmlRes !== null) {
                        var xslPath = realPath + "/content/xslt/LoadSearchArticles.xsl";
                        loadData(xslPath);
                        xhttp.onreadystatechange = function () {
                            if (this.readyState === 4 && this.status === 200) {
                                var xsl = this.responseXML;
                                xsltProcessor = new XSLTProcessor();
                                xsltProcessor.importStylesheet(xsl);
                                var resultDocument = xsltProcessor.transformToFragment(xmlRes, document);
                                loadMoreSearchArticle(ele, resultDocument.getElementById("searchList").innerHTML, query);

                                var xslPath2 = realPath + "/content/xslt/OtherArticles.xsl";
                                loadData(xslPath2);
                                xhttp.onreadystatechange = function () {
                                    if (this.readyState === 4 && this.status === 200) {
                                        var xsl = this.responseXML;
                                        xsltProcessor = new XSLTProcessor();
                                        xsltProcessor.importStylesheet(xsl);
                                        var resultDocument2 = xsltProcessor.transformToFragment(xmlRes, document);
                                        sessionStorage.setItem("searchResult", resultDocument2.getElementById("articleList").innerHTML);
                                        sessionStorage.setItem("searchQuery", query);
                                    }
                                };
                            }
                        };
                    }
                }
            };
        }

        function loadMoreSearchArticle(ele, result, query) {
            ele.innerHTML = "";
            var d = document.createElement("div");
            if (result === "") {
                result = "<h3>Không có kết quả</h3>";
            } else {
                var regEx = new RegExp(query, "ig");
                var queryInDocument = result.match(regEx)[0];
                result = result.replace(regEx, "<mark>" + queryInDocument + "</mark>");
            }
            d.innerHTML = result;
            ele.appendChild(d);
            ele.parentNode.setAttribute("style", "display: block");
        }

        var timeout = null;
        function doSearch() {
            clearTimeout(timeout);
            timeout = setTimeout(function () {
                var query = document.getElementById("key");
                var resultZone = document.getElementById("search-result-zone");
                if (query.value !== "") {
                    getArticleSearchList(query.value, resultZone);
                } else {
                    resultZone.innerHTML = "";
                    resultZone.parentNode.setAttribute("style", "display: none");
                }

                document.addEventListener('click', function (event) {
                    var isClickInsideSearchResult = resultZone.parentNode.contains(event.target);
                    var isClickInsideSearchBox = query.contains(event.target);

                    if (!isClickInsideSearchResult) {
                        if (!isClickInsideSearchBox) {
                            resultZone.innerHTML = "";
                            resultZone.parentNode.setAttribute("style", "display: none");
                        }
                    }
                });
            }, 700);
        }

        function showResult() {
            var check = document.getElementById("key");

            if (((event.keyCode === 13) && (check.value === "")) || ((window.event.which === 1) && (check.value === ""))) {
                alert("Xin nhập từ khóa");
                check.focus();
            }
            if (/\S/.test(check.value) === true) {

                if (((event.keyCode === 13) || (window.event.which === 1)) && (check.value !== "")) {
                    window.open().location = "DispatcherServlet?action=SEARCH";
                }
            }
           
        }


    </script>
</header>
