const payload = [];
const dependencyList = [];

const csvReader = () => {
    let csvElement = document.getElementById("csv_id").files[0];
    const reader = new FileReader();
    reader.onload = handleCsvFile
    reader.readAsText(csvElement)
    alert("CSV file submitted")
}

function csvToJson(lines) {
    const result = [];
    const headers = lines[0].split(",");
    for (let i = 1; i < lines.length; i++) {
        const obj = {};
        const currentLine = lines[i].split(",");
        for (let j = 0; j < headers.length; j++) {
            obj[headers[j]] = currentLine[j];
        }
        result.push(obj);
    }
    return result;
}

async function handleResponse(response) {
    if (response.status === 200) {
        const jsonData = await response.json();
        console.log(jsonData)
        if (jsonData.length == 0) {
            printCsvValid()
        }
        for (let key in jsonData) {
            const obj = jsonData[key]
            console.log("obj ", obj)
            printLines(obj)
        }
    } else {
        //todo handle
        console.log("Error : ", response)
    }
}

function printCsvValid(){
    const node = document.createElement("h3");
    const textNode = document.createTextNode(`CSV IS VALID`);
    node.appendChild(textNode);
    document.getElementById("error-msg").appendChild(node)
}

function printLines(arrayOfObject) {
    for (let obj in arrayOfObject) {
        const node = document.createElement("li");
        const currentObject = arrayOfObject[obj]
        const textNode = document.createTextNode(`Line Number ${obj}: ${currentObject}`);
        console.log("key ", obj, "obj ", currentObject)
        node.appendChild(textNode);
        document.getElementById("error_msg_list").appendChild(node)
    }
}

async function handleCsvFile(event) {
    const csv = event.target.result;
    const lines = csv.toString().split("\n");
    const result = csvToJson(lines);
    const response = await sendRequest(result);
    await handleResponse(response);
}

async function sendRequest(result) {
    return await fetch('csv', {
        method: 'POST',
        body: JSON.stringify(result)
    });
}

function addMoreDependencyRow() {
    let jsonObj = {}
    const dependsOnColumn = document.getElementById("dependentOnColumn");
    const expectedDependentFieldValue = document.getElementById("expectedDependentFieldValue");
    const expectedCurrentFieldValue = document.getElementById("expectedCurrentFieldValue")
    jsonObj["dependentOn"] = dependsOnColumn.value
    jsonObj["expectedDependentFieldValue"] = expectedDependentFieldValue.value
    jsonObj["expectedCurrentFieldValue"] = expectedCurrentFieldValue.value
    dependencyList.push(jsonObj)
    clearDependencyInputs()
}

function clearDependencyInputs() {
    dependentOnColumn.value = ""
    expectedDependentFieldValue.value = ""
    expectedCurrentFieldValue.value = ""
}

function addDataToJson() {
    let jsonObj = {}
    const field = document.getElementById("field");
    const type = document.getElementById("type");
    const value = document.getElementById("text_file_id").files[0];
    const max_len = document.getElementById("max-len");
    const min_len = document.getElementById("min-len");
    const fixed_len = document.getElementById("fixed-len");
    jsonObj["fieldName"] = field.value
    jsonObj["type"] = type.value
    let reader = new FileReader();
    reader.addEventListener('load', function (e) {
        let text = e.target.result
        jsonObj["values"] = text.split('\n')
    });
    if (value != undefined) {
        reader.readAsText(value)
    }
    jsonObj["maxLength"] = max_len.value
    jsonObj["minLength"] = min_len.value
    jsonObj["length"] = fixed_len.value
    jsonObj["dependencies"] = dependencyList
    payload.push(jsonObj)
    console.log(payload)
    alert("Field: " + field.value + " is added to configuration of CSV")
}

async function sendConfigData() {
    for (var i = 0; i < payload.length; i++) {
        sendOneConfig(payload[i])
    }
    alert("Submitted configuration of CSV\nNow you can add your csv file.")
}
async function sendOneConfig(oneConfig) {
    const resp = await fetch('add-meta-data', {
        method: 'POST',
        body: JSON.stringify(oneConfig)
    });
    if (resp.status === 200) {
        const jsonData = await resp.json();
        console.log(jsonData)
    }
}

async function resetConfigData() {
    let shouldReset = confirm("Do you want to reset configuration? \nThis will clear all existing configuration \nThis process is non-reversible")
    if (!shouldReset) {
        console.log("Cancelled reset configuration")
        return
    }
    clearPayload()
    await fetch('reset-config', {
        method: 'DELETE',
    })
    console.log("Configurations reset")
}

function clearPayload() {
    while (payload.length > 0) {
        payload.pop()
    }
}