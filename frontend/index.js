var btnList = document.getElementsByClassName("algorithm-list");
var algorithmType = "FCFS"

var addButton = document.getElementsByClassName("add-button")[0];
var deleteButton = document.getElementsByClassName("delete-button")[0];
var targetItem = document.getElementsByClassName("input-each")[1]
var numberOfItems = 1

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
    }
}

function addButtonHandleClick(event) {
    numberOfItems += 1;
    displayInnerContents(numberOfItems)
}

function deleteButtonHandleClick(event) {
    numberOfItems -= 1;
    displayInnerContents(numberOfItems)
}

function displayInnerContents(numberOfItems) {
    var inputWrap = document.getElementsByClassName("input-wrap")[0];
    
    console.log(targetItem)
    console.log(inputWrap.firstElementChild)

    for (var i = 1; i < numberOfItems; i++) {
        inputWrap.insertBefore(targetItem, inputWrap.firstElementChild);
        console.log(inputWrap)
    }
}

function init() {
    for (var i = 0; i < btnList.length; i++) {
        btnList[i].addEventListener("click", handleClick);
    }
    addButton.addEventListener("click", addButtonHandleClick);
    deleteButton.addEventListener("click", deleteButtonHandleClick);
}

init();