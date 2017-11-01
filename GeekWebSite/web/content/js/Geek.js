
function hightlightMenuItem(itemName) {
    var menuRow = document.getElementsByClassName("menu");
    for (var i = 0; i < menuRow.length; i++) {
        var liRow = menuRow[i].getElementsByTagName("a");
        for (var j = 0; j < liRow.length; j++) {
            if (liRow[j].textContent === itemName) {
                console.log();
                liRow[j].parentNode.setAttribute("style", "background-color:#C60000;");
            }
        }
    }
}


// When the user scrolls down 20px from the top of the document, show the button
window.onscroll = function () {
    scrollFunction();
};
function scrollFunction() {
    if (document.body.scrollTop > 40 || document.documentElement.scrollTop > 40) {
        document.getElementById("topBtn").style.display = "block";
        document.getElementById("top-zone").style.position = "fixed";

    } else {
        document.getElementById("topBtn").style.display = "none";
        document.getElementById("top-zone").style.position = "relative";

    }
}

function scrollToTop(scrollDuration) {
    var scrollStep = -window.scrollY / (scrollDuration / 15);
    var scrollInterval = setInterval(function () {
        if (window.scrollY !== 0) {
            window.scrollBy(0, scrollStep);
        }
        else
            clearInterval(scrollInterval);
    }, 15);
}

//// When the user clicks on the button, scroll to the top of the document
//function topFunction() {
//    document.body.scrollTop = 0; // For Chrome, Safari and Opera 
//    document.documentElement.scrollTop = 0; // For IE and Firefox
//}

function saveArticleListData(from, realPath, ele) {
    var savedData = sessionStorage.getItem("geek_list_article_from_" + from);
    if (savedData === null) {
        var url = realPath + '/GetArticleListJavaScript?from=' + (from - 1) + '&maxResult=10';
        loadData(url);
        xhttp.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                var xmlRes = this.responseXML;
                if (xmlRes !== null) {
                    var xslPath = realPath + "/content/xslt/OtherArticles.xsl";
                    loadData(xslPath);
                    xhttp.onreadystatechange = function () {
                        if (this.readyState === 4 && this.status === 200) {
                            xsl = this.responseXML;
                            xsltProcessor = new XSLTProcessor();
                            xsltProcessor.importStylesheet(xsl);
                            var resultDocument = xsltProcessor.transformToFragment(xmlRes, document);

                            sessionStorage.setItem("geek_list_article_from_" + from, resultDocument.getElementById("articleList").innerHTML);

                            if (from === 7) {
                                loadMoreArticle(ele, from);
                            }
                        }
                    };
                }
            }
        };
    } else {
//        loadMoreGame(ele, from);
    }
}

function loadMoreArticle(ele, from) {
//    while (ele.firstChild) {
//        ele.removeChild(ele.firstChild);
//    }

    var d = document.createElement("div");
    d.innerHTML = sessionStorage.getItem("geek_list_article_from_" + from);

    ele.appendChild(d);
//    bindingModalClick(true);
//    scroll(0, 0);

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

function saveGameListData(from, realPath, ele) {
    var savedData = localStorage.getItem("geek_list_game_from_" + from);
    if (savedData === null || isLocalStorageExpired(from)) {
        var url = realPath + '/GetGameListJavaScript?from=' + (from - 1) + '&maxResult=20';
        loadData(url);
        xhttp.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                var xmlRes = this.responseXML;
                if (xmlRes !== null) {
                    var xslPath = realPath + "/content/xslt/LoadMoreGameList.xsl";
                    loadData(xslPath);
                    xhttp.onreadystatechange = function () {
                        if (this.readyState === 4 && this.status === 200) {
                            xsl = this.responseXML;
                            xsltProcessor = new XSLTProcessor();
                            xsltProcessor.importStylesheet(xsl);
                            var resultDocument = xsltProcessor.transformToFragment(xmlRes, document);
                            var htmlResult = resultDocument.getElementById("remove-tag");
                            var b = document.createElement("tbody");
                            b.innerHTML = htmlResult.innerHTML;
                            localStorage.setItem("geek_list_game_from_" + from, b.innerHTML);
                            if (from === 1) {
                                loadMoreGame(ele, from);
                            }
                        }
                    };
                }
            }
        };
    } else {
//        loadMoreGame(ele, from);
    }
}

function loadMoreGame(ele, from) {
//    while (ele.firstChild) {
//        ele.removeChild(ele.firstChild);
//    }

    var d = document.createElement("tbody");
    d.innerHTML = ele.innerHTML + localStorage.getItem("geek_list_game_from_" + from);
    ele.replaceWith(d);
    bindingModalClick(true);
//    scroll(0, 0);

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
    localStorage.removeItem("geek_list_game_from_" + from);
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