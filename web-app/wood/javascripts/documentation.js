function showDocs() {
    var docs = document.getElementsByClassName("doc-box");
    var dtrigger = document.getElementById("dtrigger");

    for (var i = 0; i < docs.length; i += 1) {
        if (docs[i].style.display == "block") {
            docs[i].style.display = 'none';
            dtrigger.innerHTML = "Show docs";
        }
        else {
            docs[i].style.display = 'block';
            dtrigger.innerHTML = "Hide docs";
        }
    }
}
;
