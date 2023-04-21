var btnList = document.getElementsByClassName("algorithm-list");
var algorithmType = "FCFS"

function handleClick(event) {

    if (event.target.classList.length === 0) {
        target = event.target.parentNode
    } else {
        target = event.target
    }

    if (target.classList[1] === "clicked") {
        target.classList.remove("clicked");
    } else {
        for (var i = 0; i < btnList.length; i++) {
            btnList[i].classList.remove("clicked");
        }

        target.classList.add("clicked");

        var algorithmName = target.innerHTML
        if (algorithmName === "<div>PRIORITY</div><div>비선점</div>") {
            algorithmName = "비선점 PRIORITY"
        } else if (algorithmName === "<div>PRIORITY</div><div>선점</div>"){
            algorithmName = "선점 PRIORITY"
        }

        var algorithmNameDom = document.getElementsByClassName("algorithm-name")[0]
        algorithmNameDom.innerHTML = algorithmName
        algorithmType = algorithmName
    }
}

function init() {
    for (var i = 0; i < btnList.length; i++) {
        btnList[i].addEventListener("click", handleClick);
    }
}

init()