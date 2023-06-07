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
    var submitButton = document.getElementsByClassName("submit")[0];
    toHomeButtton.addEventListener("click", changeContents_resultToInput);
    submitButton.addEventListener("click", submitButtonHandler);
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
                "PID" : "P1",
                "WT" : 3,
                "TT" : 7,
                "RT" : 4
            },
            "startAt" : 3,
            "endAt" : 7
        },
        {
            "process" : {
                "PID" : "P2",
                "WT" : 3,
                "TT" : 7,
                "RT" : 4
            },
            "startAt" : 7,
            "endAt" : 10
        },
        {
            "process" : {
                "PID" : "P3",
                "WT" : 3,
                "TT" : 7,
                "RT" : 4
            },
            "startAt" : 10,
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

function setGantChart() {
    var gantChart = document.getElementsByClassName("gant-chart")[0];
    var timeDisplay = document.getElementsByClassName("time-display")[0];

    schedulingDataLength = dummyData.scheduledDataList.length
    entireServiceTime = dummyData.scheduledDataList[schedulingDataLength-1].endAt
    blockCopy = gantChart.children[0].cloneNode(true)
    timeBlockCopy = timeDisplay.children[0].cloneNode(true)

    var emptyBlock = blockCopy.cloneNode(true)
    emptyBlock.style.backgroundColor = "#f5f5f5"
    emptyBlock.innerHTML = ""
    emptyBlock.style.width = "10px"

    gantChart.children[0].remove()
    timeDisplay.children[0].remove()

    for(var i=0; i<schedulingDataLength; i++) {
        startTime = dummyData.scheduledDataList[i].startAt
        endTime = dummyData.scheduledDataList[i].endAt
        var serviceTime = endTime - startTime
        var blockWidth = parseInt(((450 / entireServiceTime) * serviceTime)-2)

        var newBlock = blockCopy.cloneNode(true)
        newBlock.style.width = blockWidth.toString() + "px"
        newBlock.innerHTML = dummyData.scheduledDataList[i].process.PID
        gantChart.append(newBlock)

        var newTimeBlock = timeBlockCopy.cloneNode(true)
        newTimeBlock.style.width = blockWidth.toString() + "px"
        newTimeBlock.children[0].innerHTML = startTime
        newTimeBlock.children[1].innerHTML = endTime
        timeDisplay.append(newTimeBlock)
    }
}

function submitButtonHandler() {

    var datas = $("#main-form").serialize();
    console.log(datas)

    // $.ajax({
    //     url : "",
    //     type : "POST",
    //     // data :
    // })
    // .done((result) => {
    //     console.log(result)

    // })
}

setGantChart()
setResultTable()
init();
console.log("asdasd")