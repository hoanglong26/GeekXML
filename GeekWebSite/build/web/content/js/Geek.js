function showResult() {
    var check = document.getElementById("key")

    if (((event.keyCode == 13) && (check.value == "")) || ((window.event.which == 1) && (check.value == ""))) {
        alert("Xin nhập từ khóa");
        check.focus();
    }
    if (/\S/.test(check.value) == true) {

        if (((event.keyCode == 13) || (window.event.which == 1)) && (check.value != "")) {
            window.open().location = "https://www.google.com/#q=" + check.value + "+site:gamek.vn";
        }
    }
    else
        check.value = "";
}


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
    if (flag == true) {
        time_id = setTimeout("picSwap()", 2000);
    }
    else {
        clearTimeout(time_id);
    }
}


var m = 1;
var n = 3;
function expand(begin, end) {
    m = m + 3;
    n = n + 3;

    if (m > begin || n > end) {
        m = m - 3;
        n = n - 3;
    }

    for (i = m; i <= n; i++) {
        document.getElementById("page" + i).style.visibility = "visible";
    }
}


function hide(begin, end) {

    if (m >= begin || n >= end) {
        for (i = m; i <= n; i++) {
            document.getElementById("page" + i).style.visibility = "hidden";
        }
    }
    m = m - 3;
    n = n - 3;
    if (m < 1 || n < 3) {
        m = 1;
        n = 3;
    }
}

function bindingModalClick(isLoadFromXSLT) {
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

            var indexOfTd;
            var indexOfImg;
            var indexOfGameInfo;
            var indexOfRatingList;

            if (isLoadFromXSLT) {
                indexOfTd = 3;
                indexOfImg = 1;
                indexOfGameInfo = 2;
                indexOfRatingList = 5;
            } else {
                indexOfTd = 2;
                indexOfImg = 0;
                indexOfGameInfo = 1;
                indexOfRatingList = 3;

            }

            var tdImgChild = this.childNodes[indexOfTd];
            var imgChild = tdImgChild.childNodes[indexOfImg];
            modalImg.src = imgChild.src;

            var hiddenGameInfo = tdImgChild.childNodes[indexOfGameInfo];
            linkImgHref.href = hiddenGameInfo.dataset.link;
            linkHref.href = hiddenGameInfo.dataset.link;
            nameText.innerHTML = hiddenGameInfo.dataset.name;
            platformText.innerHTML = hiddenGameInfo.dataset.platform;
            pulisherText.innerHTML = hiddenGameInfo.dataset.pulisher;
            overallRatingText.innerHTML = hiddenGameInfo.dataset.score + " với " + hiddenGameInfo.dataset.vote;


            ratingList.innerHTML = "";
            var tdNameChild = this.childNodes[indexOfRatingList];
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
}

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

function saveGameListData(from, realPath, ele) {
    var savedData = localStorage.getItem("geek_list_game_from_" + from);
    if (savedData === null || savedData === "null" || isLocalStorageExpired(from)) {
        var url = realPath + '/GetGameListJavaScript?from=' + (from - 1) + '&maxResult=20';
        loadData(url);
        xhttp.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                var xmlRes = this.responseXML;
                if (xmlRes !== null) {

//                    var xslUrl = realPath + "/content/GameList.xsl";

//                    var xsl = new ActiveXObject('Msxml2.FreeThreadedDOMDocument.6.0');
//                    xsl.load(xslUrl);

                    var xslPath = realPath + "/content/xslt/LoadMoreGameList.xsl";
                    loadData(xslPath);

                    xhttp.onreadystatechange = function () {
                        if (this.readyState === 4 && this.status === 200) {
                            xsl = this.responseXML;

                            xsltProcessor = new XSLTProcessor();
                            xsltProcessor.importStylesheet(xsl);
                            var resultDocument = xsltProcessor.transformToFragment(xmlRes, document);

                            var htmlResult = resultDocument.getElementById("remove-tag");

//                            var childNodes = htmlResult.childNodes;

                            var b = document.createElement("tbody");
                            b.innerHTML = htmlResult.innerHTML;

//                            for (var i = 0, len = childNodes.length; i < len; i++) {
//                                if (childNodes[i] !== undefined) {
//                                    if (childNodes[i].tagName === "TR") {
//                                        ele.appendChild(childNodes[i]);
//                                    }
//                                }
//                            }

                            localStorage.setItem("geek_list_game_from_" + from, b.innerHTML);
//                            while (ele.firstChild) {
//                                ele.removeChild(ele.firstChild);
//                            }
//                            var d = document.createElement("tbody");
//                            d.innerHTML = localStorage.getItem("geek_list_game_from_" + from);
//                            ele.replaceWith(d);
//                            bindingModalClick(true);
                            loadMoreGame(ele,from);
                        }
                    };
                }
            }
        };
    } else {
        loadMoreGame(ele, from);
    }
}

function loadMoreGame(ele, from) {
    while (ele.firstChild) {
        ele.removeChild(ele.firstChild);
    }
    var d = document.createElement("tbody");
    d.innerHTML = localStorage.getItem("geek_list_game_from_" + from);
    ele.replaceWith(d);
    bindingModalClick(true);
    scroll(0,0);

}


function isLocalStorageExpired(from) {
    var lastUpdatedDate = localStorage.getItem("geek_last_date_list_from_" + from);
    var lastDate = new Date(lastUpdatedDate);

    var currentDate = new Date();
    currentDate.setHours(0, 0, 0, 0);

    if (lastDate !== null) {
        lastDate.setHours(0, 0, 0, 0);
        lastDate.setDate(lastDate.getDate() + 7);
        if (lastDate <= currentDate) {
            return true;
        } else {
            return false;
        }
    } else {
        return true;
    }
}

function clearStorage(from) {
    var currentDate = new Date();
    currentDate.setHours(0, 0, 0, 0);
    localStorage.setItem("geek_list_game_from_" + from, null);
    localStorage.setItem("geek_last_date_list_from_" + from, currentDate);
}

function initStorageTimeout(from) {
    var lastUpdatedDate = localStorage.getItem("geek_last_date_list_from_" + from);
    var lastDate = new Date(lastUpdatedDate);

    var currentDate = new Date();
    currentDate.setHours(0, 0, 0, 0);

    if (lastDate !== null) {
        lastDate.setHours(0, 0, 0, 0);
        lastDate.setDate(lastDate.getDate() + 7);
        if (lastDate <= currentDate) {
            localStorage.setItem("geek_last_date_list_from_" + from, currentDate);
        }
    } else {
        localStorage.setItem("geek_last_date_list_from_" + from, currentDate);
    }
}


function applyXSL(xml, realPath, xslFilePath, ele) {
//    var xslPath = realPath + xslFilePath;
//    loadData(xslPath);

//    var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
//    xmlDoc.async = "false";
//    xmlDoc.loadXML(xml);
//    console.log(xmlDoc);

//    xhttp.onreadystatechange = function () {
//        if (this.readyState === 4 && this.status === 200) {
//            xsl = this.responseXML;

//            xsltProcessor = new XSLTProcessor();
//            xsltProcessor.importStylesheet(xsl);
//
//            var oParser = new DOMParser();
//            var oDOM = oParser.parseFromString(xml, "text/xml");
//            console.log(oDOM);
//            var resultDocument = xsltProcessor.transformToFragment(oDOM, document);
//            console.log(resultDocument);
//
//            var htmlResult = resultDocument;
////            document.getElementById(eleId).innerHTML = '';
//            ele.appendChild(htmlResult);
//        }
//    }
//    ;
}