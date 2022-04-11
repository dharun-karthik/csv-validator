const payload = [];
const dependencyList = [];

window.addEventListener('load', async() => loadMetaData());

async function loadMetaData() {
    const resp = await fetch('get-meta-data', {
        method: 'GET',
    });
    if (resp.status === 200) {
        const jsonData = await resp.json();
        displayMetaData(jsonData)
    }
}

function displayMetaData(jsonData) {
    jsonData.forEach(obj => {
        displayConfigs(obj["fieldName"], obj["type"], obj["maxLength"], obj["minLength"], obj["length"])
    })
}

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
            if (currentLine[j] == "") {
                obj[headers[j]] = "null"
                continue;
            }
            obj[headers[j]] = currentLine[j];
        }
        result.push(obj);
    }
    return result;
}

async function handleResponse(response) {
    clearOldErrors()
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

function clearOldErrors(){
    let errorDiv = document.getElementById("error-msg")
    errorDiv.innerHTML = ""
    let ul = document.createElement("ul")
    ul.id = "error_msg_list"
    errorDiv.appendChild(ul)

}

function printCsvValid() {
    const node = document.createElement("h3");
    const textNode = document.createTextNode(`CSV IS VALID`);
    node.appendChild(textNode);
    document.getElementById("error-msg").appendChild(node)
}

function printLines(arrayOfObject) {
    for (let obj in arrayOfObject) {
        const node = document.createElement("li");
        const currentObject = arrayOfObject[obj]
        const textNode = document.createTextNode(`${obj}: ${currentObject}`);
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
    const dependsOnColumn = document.getElementById("dependentOnColumn").value;
    const expectedDependentFieldValue = document.getElementById("expectedDependentFieldValue").value;
    const expectedCurrentFieldValue = document.getElementById("expectedCurrentFieldValue").value;
    let isInputValid = validateInputsForDependency(dependsOnColumn, expectedDependentFieldValue, expectedCurrentFieldValue)
    if (!isInputValid) {
        return
    }
    let jsonObj = {}
    jsonObj["dependentOn"] = dependsOnColumn
    jsonObj["expectedDependentFieldValue"] = expectedDependentFieldValue
    jsonObj["expectedCurrentFieldValue"] = expectedCurrentFieldValue
    dependencyList.push(jsonObj)
    displayDependencyList(dependsOnColumn, expectedDependentFieldValue, expectedCurrentFieldValue)
    clearDependencyInputs()
}

function validateInputsForDependency(dependsOnColumn, expectedDependentFieldValue, expectedCurrentFieldValue) {
    if (dependsOnColumn != "" && expectedDependentFieldValue == "" || expectedCurrentFieldValue == "") {
        alert("Please enter values for Expected Dependent Field and Expected Current Field")
        return false
    }
    return true
}

function displayDependencyList(dependsOnColumn, dependentFieldValue, currenFieldValue) {
    let dependency = document.createElement('div')
    dependency.id = 'dependency'

    let dependsOnColumnElement = addDependencyElement("Depends On: ", dependsOnColumn);
    dependency.appendChild(dependsOnColumnElement)

    let dependentFieldValueElement = addDependencyElement(" Expected Dependent Field: ", dependentFieldValue);
    dependency.appendChild(dependentFieldValueElement)

    let currenFieldValueElement = addDependencyElement(" Expected Current Field: ", currenFieldValue)
    dependency.appendChild(currenFieldValueElement)

    let dependencies = document.getElementById('dependencies')
    dependencies.appendChild(dependency)
}

function addDependencyElement(dependencyType, dependsOnColumn) {
    let newElement = document.createElement('div');
    newElement.innerText = dependencyType;
    let spanColumn = document.createElement('span');
    spanColumn.innerText = dependsOnColumn;
    newElement.appendChild(spanColumn);
    return newElement;
}

function clearDependencyInputs() {
    dependentOnColumn.value = ""
    expectedDependentFieldValue.value = ""
    expectedCurrentFieldValue.value = ""
}

function addDataToJson() {
    let isInputValid = validateInputsForFields()
    if (!isInputValid) {
        return
    }
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
    displayConfigs(field.value, type.value, max_len.value, min_len.value, fixed_len.value)
    console.log(payload)
    alert("Field: " + field.value + " is added to configuration of CSV")
    clearConfigInputs()
}

function validateInputsForFields() {
    const field = document.getElementById("field").value;
    const type = document.getElementById("type").value;

    console.log(field + type)
    if (field != "" && type == "") {
        alert("Please enter type for the field !")
        return false
    }
    return true
}

function displayConfigs(fieldName, typeValue, maxLengthValue, minLengthValue, fixedLengthValue) {
    let config = document.createElement('div')
    config.id = 'config'

    let field = addDependencyElement("Field: ", fieldName);
    config.appendChild(field)

    let type = addDependencyElement(" Type: ", typeValue);
    config.appendChild(type)

    let maxLength = addDependencyElement(" Maximum Length: ", maxLengthValue);
    config.appendChild(maxLength)

    let minLength = addDependencyElement(" Minimum Length: ", minLengthValue);
    config.appendChild(minLength)

    let fixedLength = addDependencyElement(" Fixed Length: ", fixedLengthValue);
    config.appendChild(fixedLength)

    let configs = document.getElementById('configs')
    configs.appendChild(config)

    let dependencies = document.getElementById("dependencies")
    configs.appendChild(dependencies)
}

function clearConfigInputs() {
    myform.reset()
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