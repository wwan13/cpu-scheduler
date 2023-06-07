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

const dummyData = {
    "algorithmType" : "FCFS",
    "processes" : [
        {
            "PID" : "P0",
            "WT" : 3,
            "TT" : 7,
            "RT" : 4
        },
        {
            "PID" : "P1",
            "WT" : 3,
            "TT" : 7,
            "RT" : 4
        },
        {
            "PID" : "P2",
            "WT" : 3,
            "TT" : 7,
            "RT" : 4
        },
        {
            "PID" : "P3",
            "WT" : 3,
            "TT" : 7,
            "RT" : 4
        },
    ],
    "scheduledDataList" : [
        {
            "process" : {
                "PID" : "P0",
                "WT" : 3,
                "TT" : 7,
                "RT" : 4
            },
                "startAt" : 0,
                "endAt" : 3
        },
        {
            "process" : {
                "PID" : "P0",
                "WT" : 3,
                "TT" : 7,
                "RT" : 4
            },
            "startAt" : 4,
            "endAt" : 7
        },
        {
            "process" : {
                "PID" : "P0",
                "WT" : 3,
                "TT" : 7,
                "RT" : 4
            },
            "startAt" : 8,
            "endAt" : 10
        },
        {
            "process" : {
                "PID" : "P0",
                "WT" : 3,
                "TT" : 7,
                "RT" : 4
            },
            "startAt" : 11,
            "endAt" : 16
        },
    ],
    "AWT" : "3",
    "ATT" : "5"
}

function setResultTable() {
    var tableBody = document.getElementsByClassName("table-body")[0];
    var firstChild = tableBody.children[0]
    var elseChild = tableBody.children[1].cloneNode(true)

    firstChild.children[0].innerHTML = dummyData.processes[0].PID
    firstChild.children[1].innerHTML = dummyData.processes[0].WT
    firstChild.children[2].innerHTML = dummyData.processes[0].TT
    firstChild.children[3].innerHTML = dummyData.processes[0].RT
    firstChild.children[4].rowSpan = dummyData.processes.length
    firstChild.children[5].rowSpan = dummyData.processes.length
    firstChild.children[4].innerHTML = dummyData.AWT
    firstChild.children[5].innerHTML = dummyData.ATT
    tableBody.children[1].remove()

    for (var i=1; i<dummyData.processes.length; i++) {
        var newNode = elseChild.cloneNode(true);
        newNode.children[0].innerHTML = dummyData.processes[i].PID
        newNode.children[1].innerHTML = dummyData.processes[i].WT
        newNode.children[2].innerHTML = dummyData.processes[i].TT
        newNode.children[3].innerHTML = dummyData.processes[i].RT
        tableBody.append(newNode);
    }


}

setResultTable()
init();