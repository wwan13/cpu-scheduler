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

//const dummyData = {
//    "algorithmType" : "FCFS",
//    "processes" : [
//        {
//            "PID" : "P0",
//            "WT" : 3,
//            "TT" : 7,
//            "RT" : 4
//        },
//        {
//            "PID" : "P1",
//            "WT" : 3,
//            "TT" : 7,
//            "RT" : 4
//        },
//        {
//            "PID" : "P2",
//            "WT" : 3,
//            "TT" : 7,
//            "RT" : 4
//        },
//        {
//            "PID" : "P3",
//            "WT" : 3,
//            "TT" : 7,
//            "RT" : 4
//        },
//    ],
//    "scheduledDataList" : [
//        {
//            "process" : {
//                "PID" : "P0",
//                "WT" : 3,
//                "TT" : 7,
//                "RT" : 4
//            },
//                "startAt" : 0,
//                "endAt" : 3
//        },
//        {
//            "process" : {
//                "PID" : "P1",
//                "WT" : 3,
//                "TT" : 7,
//                "RT" : 4
//            },
//            "startAt" : 3,
//            "endAt" : 7
//        },
//        {
//            "process" : {
//                "PID" : "P2",
//                "WT" : 3,
//                "TT" : 7,
//                "RT" : 4
//            },
//            "startAt" : 7,
//            "endAt" : 10
//        },
//        {
//            "process" : {
//                "PID" : "P3",
//                "WT" : 3,
//                "TT" : 7,
//                "RT" : 4
//            },
//            "startAt" : 10,
//            "endAt" : 16
//        },
//    ],
//    "AWT" : "3",
//    "ATT" : "5"
//}

const tableBody = document.getElementsByClassName("table-body")[0];
const firstChild = tableBody.children[0].cloneNode(true)
const elseChild = tableBody.children[1].cloneNode(true)

function setResultTable(data) {

    var data = data.response

    tableBody.replaceChildren()

    newFistChild = firstChild.cloneNode(true)
    newFistChild.children[0].innerHTML = data.processes[0].processId
    newFistChild.children[1].innerHTML = data.processes[0].waitTime
    newFistChild.children[2].innerHTML = data.processes[0].turnAroundTime
    newFistChild.children[3].innerHTML = data.processes[0].responseTime
    newFistChild.children[4].rowSpan = data.processes.length
    newFistChild.children[5].rowSpan = data.processes.length
    newFistChild.children[4].innerHTML = data.awt.toFixed(2);
    newFistChild.children[5].innerHTML = data.att.toFixed(2);

    tableBody.append(newFistChild)
    for (var i=1; i<data.processes.length; i++) {
        var newNode = elseChild.cloneNode(true);
        newNode.children[0].innerHTML = data.processes[i].processId
        newNode.children[1].innerHTML = data.processes[i].waitTime
        newNode.children[2].innerHTML = data.processes[i].turnAroundTime
        newNode.children[3].innerHTML = data.processes[i].responseTime
        tableBody.append(newNode);
    }
}

const gantChart = document.getElementsByClassName("gant-chart")[0];
const timeDisplay = document.getElementsByClassName("time-display")[0];
const blockCopy = gantChart.children[0].cloneNode(true)
const timeBlockCopy = timeDisplay.children[0].cloneNode(true)

function setGantChart(data) {

    var dummyData = data.response

    schedulingDataLength = dummyData.scheduledDataList.length
    entireServiceTime = dummyData.scheduledDataList[schedulingDataLength-1].endAt

    gantChart.replaceChildren()
    timeDisplay.replaceChildren()

    var lastProcessEndAt = 0

    for(var i=0; i<schedulingDataLength; i++) {
        if(parseInt(dummyData.scheduledDataList[0].startAt) > parseInt(lastProcessEndAt)) {
            var startAt = dummyData.scheduledDataList[0].startAt
            var emptyBlock = blockCopy.cloneNode(true)
            var blockWidth = parseInt(((450 / entireServiceTime) * startAt)-2)

            emptyBlock.style.backgroundColor = "#f5f5f5"
            emptyBlock.innerHTML = ""
            emptyBlock.style.width = blockWidth.toString() + "px"
            emptyBlock.style.border = "1px gray dotted"
            gantChart.append(emptyBlock)

            var newTimeBlock = timeBlockCopy.cloneNode(true)
            newTimeBlock.children[0].innerHTML = ""
            newTimeBlock.children[1].innerHTML = ""
            newTimeBlock.style.width = blockWidth.toString() + "px"
            timeDisplay.append(newTimeBlock)
        }

        startTime = dummyData.scheduledDataList[i].startAt
        endTime = dummyData.scheduledDataList[i].endAt
        var serviceTime = endTime - startTime
        var blockWidth = parseInt(((450 / entireServiceTime) * serviceTime)-2)

        var newBlock = blockCopy.cloneNode(true)
        newBlock.style.width = blockWidth.toString() + "px"
        newBlock.innerHTML = dummyData.scheduledDataList[i].process.processId
        gantChart.append(newBlock)

        var newTimeBlock = timeBlockCopy.cloneNode(true)
        newTimeBlock.style.width = blockWidth.toString() + "px"
        newTimeBlock.children[0].innerHTML = startTime
        newTimeBlock.children[1].innerHTML = endTime
        timeDisplay.append(newTimeBlock)

        lastProcessEndAt = endTime
    }
}

function submitButtonHandler() {

    var requestBody = {}

    var datas = []
    var pids = document.getElementsByName("pid");
    var arrivalTimes = document.getElementsByName("arrivalTime");
    var serviceTimes = document.getElementsByName("serviceTime");
    var prioritys = document.getElementsByName("priority");

    for(var i = 0; i<pids.length; i++) {
        var data = {}
        data["PID"] = pids[i].value
        data["arrivalTime"] = arrivalTimes[i].value
        data["serviceTime"] = serviceTimes[i].value
        data["priority"] = prioritys[i].value
        datas.push(data)
    }

    requestBody["datas"] = datas
    requestBody["timeSlice"] = document.getElementsByName("timeSlice")[0].value

    var algorithm = document.getElementsByClassName("algorithm-name")[0].innerHTML;
        if (algorithm === "First Come First Served") {
            algorithmName = "FCFS"
        } else if (algorithm === "Shortest Job First") {
            algorithmName = "SJF"
        } else if (algorithm === "Non-Preemptive Priority") {
            algorithmName = "npPriority"
        } else if (algorithm === "Preemptive Priority") {
            algorithmName = "pPriority"
        } else if (algorithm === "Round Robin") {
            algorithmName = "RR"
        } else if (algorithm === "Shortest Remaining Time") {
            algorithmName = "SRT"
        } else if (algorithm === "Highest Response ratio Next") {
            algorithmName = "HRN"
        }

        requestBody["algorithmType"] = algorithmName

    $.ajax({
        url : "/",
        type : "POST",
        contentsType : "application/json",
        data : requestBody,
        success : (result) => {
            changeContents_inputToResult()
            setResultTable(result)
            setGantChart(result)
        }
    })
}

init();