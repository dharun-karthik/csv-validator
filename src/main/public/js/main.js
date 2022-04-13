
// Frontend Animation
const t1 = gsap.timeline({ defaults: { ease: "power1.out" } })
.to('#landing_page_title', {y: '0%', duration: 1, stagger: 0.25})
.to("#shuttle", {y: "-100%", duration: 1.5, delay: 0.5})
.to('.landing_page', {y: "-100%", duration: 1}, "-=1")

const payload = [];
const dependencyList = [];
window.addEventListener('load', async () => loadMetaData());

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
        displayConfigs(obj["fieldName"] || "", obj["type"] || "", obj["maxLength"] || "", obj["minLength"] || "", obj["length"] || "")
        if(obj["dependencies"] != undefined) {
            displayDependencies(obj["dependencies"])
        }
    })
}

function displayDependencies(dependencies){
    dependencies.forEach(depedencyObj => 
        displayDependencyList(depedencyObj["dependentOn"],depedencyObj["expectedDependentFieldValue"],depedencyObj["expectedCurrentFieldValue"])
    )
}

const csvReader = () => {
    let csvElement = document.getElementById("csv_id").files[0];
    const reader = new FileReader();
    reader.onload = handleCsvFile
    reader.readAsText(csvElement)
    alert("CSV file submitted")
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

function clearOldErrors() {
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
    let convert = new Convert();
    const result = convert.csvToJson(lines);
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
    let mandatoryInputValidation = new MandatoryInputValidation();
    let isInputValid = mandatoryInputValidation.validateInputsForDependency(dependsOnColumn, expectedDependentFieldValue, expectedCurrentFieldValue)
    if (!isInputValid) {
        alert("Please enter values for Dependency On Column, Dependent Field and Expected Current Field")
        return
    }
    let jsonObj = {}
    jsonObj["dependentOn"] = dependsOnColumn
    jsonObj["expectedDependentFieldValue"] = expectedDependentFieldValue
    jsonObj["expectedCurrentFieldValue"] = expectedCurrentFieldValue
    console.log(payload[payload.length - 1]["dependencies"])
    if(payload[payload.length - 1]["dependencies"] == undefined){
        payload[payload.length - 1]["dependencies"] = [jsonObj]
    }else{
        payload[payload.length - 1]["dependencies"].push(jsonObj)
    }
    displayDependencyList(dependsOnColumn, expectedDependentFieldValue, expectedCurrentFieldValue)
    clearDependencyInputs()
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

    let dependencies = document.getElementById('configs')
    dependencies.appendChild(dependency)
}

function addDependencyElement(dependencyType, dependsOnColumn) {
    let newElement = document.createElement('div');
    newElement.innerText = dependencyType;
    let spanColumn = document.createElement('span');
    spanColumn.id = "input_text";
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
    const field = document.getElementById("field").value;
    const type = document.getElementById("type").value;
    let mandatoryInputValidation = new MandatoryInputValidation();
    let isInputValid = mandatoryInputValidation.validateInputsForFields(field, type)
    if (!isInputValid) {
        alert("Please enter field and type for the field !")
        return
    }
    let jsonObj = {}
    const value = document.getElementById("text_file_id").files[0];
    const max_len = document.getElementById("max-len").value;
    const min_len = document.getElementById("min-len").value;
    const fixed_len = document.getElementById("fixed-len").value;
    jsonObj["fieldName"] = field
    jsonObj["type"] = type
    let reader = new FileReader();
    reader.addEventListener('load', function (e) {
        let text = e.target.result
        jsonObj["values"] = text.split('\n')
    });
    if (value != undefined) {
        reader.readAsText(value)
    }
    jsonObj["maxLength"] = max_len
    jsonObj["minLength"] = min_len
    jsonObj["length"] = fixed_len
    payload.push(jsonObj)
    displayConfigs(field, type, max_len, min_len, fixed_len)
    console.log(payload)
    alert("Field: " + field + " is added to configuration of CSV")
    clearConfigInputs()
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
    clearConfigDisplay()
    console.log("Configurations reset")
}

function clearConfigDisplay() {
    document.getElementById("configs").innerHTML = '<h3>Fields to Validates</h3>'
}

function clearPayload() {
    while (payload.length > 0) {
        payload.pop()
    }
}