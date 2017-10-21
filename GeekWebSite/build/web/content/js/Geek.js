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
	else check.value = "";
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