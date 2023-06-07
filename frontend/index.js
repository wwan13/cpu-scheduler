var btnList = document.getElementsByClassName("algorithm-list");
var algorithmType = "FCFS"

var addButton = document.getElementsByClassName("add-button")[0];
var deleteButton = document.getElementsByClassName("delete-button")[0];
const targetItem = document.getElementsByClassName("input-each")[1]
var numberOfItems = 1

function handleClick(event) {

    if (event.target.classList.length === 0) {
        target = event.target.parentNode
    } else {
        target = event.target
    }

    if (target.classList[1] === "clicked") {
        // target.classList.remove("clicked");
    } else {
        for (var i = 0; i < btnList.length; i++) {
            btnList[i].classList.remove("clicked");
        }

        target.classList.add("clicked");

        var algorithmName = target.innerHTML
        if (algorithmName === "<div>PRIORITY</div><div>비선점</div>") {
            algorithmName = "Non-Preemptive Priority"
        } else if (algorithmName === "<div>PRIORITY</div><div>선점</div>"){
            algorithmName = "Preemptive Priority"
        } else if (algorithmName === "FCFS" ) {
            algorithmName = "First Come First Served"
        } else if (algorithmName === "SJF" ) {
            algorithmName = "Shortest Job First"
        } else if (algorithmName === "SRT" ) {
            algorithmName = "Shortest Remaining Time"
        } else if (algorithmName === "HRN" ) {
            algorithmName = "Highest Response ratio Next"
        } else if (algorithmName === "RR" ) {
            algorithmName = "Round Robin"
        }

        var algorithmNameDom = document.getElementsByClassName("algorithm-name")[0]
        algorithmNameDom.innerHTML = algorithmName
        algorithmType = algorithmName

        changeContents_resultToInput()

    }
}

function addButtonHandleClick(event) {
    var proccesses = document.getElementsByClassName("proccesses")[0];

    var newElement = targetItem.cloneNode(true);
    newElement.childNodes[1].value = "";
    newElement.childNodes[3].value = "";
    newElement.childNodes[5].value = "";
    newElement.childNodes[7].value = "";
    proccesses.append(newElement);

    deleteBtns = document.getElementsByClassName("delete-button");

    for (var i = 0; i < deleteBtns.length; i++) {
        deleteBtns[i].addEventListener("click", deleteButtonHandleClick);
    }

    window.scrollTo(0,-1)
}

function deleteButtonHandleClick(event) {
    event.target.parentNode.remove();
}

function changeContents_resultToInput() {
    var input_contents = document.getElementsByClassName("input-contents")[0];
    var result_contents = document.getElementsByClassName("result-contents")[0];

    input_contents.classList.remove("display-none");
    result_contents.classList.add("display-none");
}

function changeContents_inputToResult() {
    var input_contents = document.getElementsByClassName("input-contents")[0];
    var result_contents = document.getElementsByClassName("result-contents")[0];

    input_contents.classList.add("display-none");
    result_contents.classList.remove("display-none");
}

function init() {
    for (var i = 0; i < btnList.length; i++) {
        btnList[i].addEventListener("click", handleClick);
    }
    addButton.addEventListener("click", addButtonHandleClick);
    deleteButton.addEventListener("click", deleteButtonHandleClick);

    var toHomeButtton = document.getElementsByClassName("to-home")[0];
    var submitButton = document.getElementsByClassName("submit-button")[0];
    toHomeButtton.addEventListener("click", changeContents_resultToInput);
}

init();