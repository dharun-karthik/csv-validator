const payload = {};
const headers = [];
var numberOfFields = 0;

function toggleValueFieldTextBox(element) {
    let index = extractIndexFromId(element.id)
    useTextForValue = document.getElementById(`use-text-for-value${index}`).value
    valueTextArea = document.getElementById(`alternate-value${index}`)
    if (valueTextArea.style.display == 'block') {
        valueTextArea.style.display = 'none'
    } else {
        valueTextArea.style.display = 'block'
    }
}

function toggleDependencyInputs(element) {
    let index = extractIndexFromId(element.id)
    dependsOnColumn = document.getElementById(`depends-on${index}`).value
    if (dependsOnColumn != "") {
        document.getElementById(`dependent-value${index}`).style.visibility = 'visible'
        document.getElementById(`current-value${index}`).style.visibility = 'visible'
    } else {
        document.getElementById('dependent-value0').style.visibility = 'hidden'
        document.getElementById('current-value0').style.visibility = 'hidden'
    }
}

function extractIndexFromId(fieldId) {
    return fieldId[fieldId.length - 1]
}

async function uploadCSV() {
    let csvElement = document.getElementById('csv-id').files[0];
    const reader = new FileReader();
    reader.onload = await handleCsvFile
    reader.readAsText(csvElement)
    alert("CSV file submitted")
    window.location.href = "errors.html"
}

async function handleCsvFile(event) {
    const csv = event.target.result;
    const lines = csv.toString().split("\n");
    captureHeaders(lines[0])
    let convert = new Convert();
    const result = convert.csvToJson(lines);
    const response = await sendRequest(result);
    await handleResponse(response);
}

function captureHeaders(headersString) {
    headersList = headersString.split(',')
    headersList.forEach(element => {
        headers.push(element)
    });
}

async function sendRequest(result) {
    return await fetch('csv', {
        method: 'POST', body: JSON.stringify(result)
    });
}

async function handleResponse(response) {
    if (response.status === 200) {
        const jsonData = await response.json();
        console.log(jsonData)
        if (jsonData.length == 0) {
            printCsvValid()
            return
        }
        for (let key in jsonData) {
            const obj = jsonData[key]
            console.log("obj ", obj)
            // printLines(obj)
        }
    } else {
        console.log("Error : ", response)
    }
}

function printCsvValid() {
    let container = document.getElementById('display-errors')
    container.innerHTML = ""

    let outerDiv = document.createElement('div')
    outerDiv.className = 'no-error';

    let innerDiv = document.createElement('div')
    innerDiv.className = 'valid-csv';
    innerDiv.innerText = 'CSV Has No Errors'
    
    outerDiv.appendChild(innerDiv)
    container.appendChild(outerDiv)
}

function addNewField() {
    numberOfFields += 1;
    let configContainer = document.createElement('div')
    configContainer.className = 'config-container'
    configContainer.innerHTML = `
    <div class="row1">
        <div id="field-name">
            <label for="field${numberOfFields}">Field Name: </label>
            <input type="text" id="field${numberOfFields}" class="inputs" name="fieldName" onchange="onChangeHandler(event)">
        </div>
        <div id="field-type">
            <label for="type${numberOfFields}">Type: </label>
            <select id="type${numberOfFields}" name="type" class="dropdowns" onchange="onChangeHandler(event)">
                <option value="">Choose Type</option>
                <option value="Number">Number</option>
                <option value="AlphaNumeric">AlphaNumeric</option>
                <option value="Alphabets">Alphabets</option>
            </select>
        </div>
        <div id="field-value">
            <label for="text-file-id${numberOfFields}">Values: </label>
            <input type="file" class="choose-file" name="values" id="text-file-id${numberOfFields}" accept=".txt" onchange="onChangeHandler(event)">
            <input type="checkbox" name="use-text-for-value" id="use-text-for-value${numberOfFields}"
                onclick="toggleValueFieldTextBox(this)">
            <span>Enter Values</span>
            <textarea name="alternate-value" id="alternate-value${numberOfFields}" cols="10" rows="10" class="displayNone alternate-value"></textarea>
        </div>
    </div>
    <div class="row1">
        <div id="min-length">
            <label for="min-len${numberOfFields}">Min-length: </label>
            <input type="number" name="minLength" id="min-len${numberOfFields}" class="inputs" onchange="onChangeHandler(event)">
        </div>
        <div id="max-length">
            <label for="max-len${numberOfFields}">Max-length: </label>
            <input type="number" name="maxLength" id="max-len${numberOfFields}" class="inputs" onchange="onChangeHandler(event)">
        </div>
        <div id="fixed-length">
            <label for="fixed-len${numberOfFields}">Fixed-length: </label>
            <input type="number" name="length" id="fixed-len${numberOfFields}" class="inputs" onchange="onChangeHandler(event)">
        </div>
    </div>
    <div class="row1">
        <div id="depends-on-column">
            <label for="depends-on${numberOfFields}">Dependency on column: </label>
            <input type="text" id="depends-on${numberOfFields}" class="inputs" name="dependentOn" onchange="onChangeHandler(event)">
            </input>
        </div>
        <div id="dependent-value${numberOfFields}" class="hidden">
            <label for="dependent-field-value${numberOfFields}">Dependent Field Value: </label>
            <input type="text" class="inputs extra-width" id="dependent-field-value${numberOfFields}" name="expectedDependentFieldValue"
                placeholder="null for no value, !null for any value" onchange="onChangeHandler(event)">
        </div>
        <div id="current-value${numberOfFields}" class="hidden">
            <label for="expectedCurrentFieldValue${numberOfFields}">Expected Current Field Value: </label>
            <select id="expectedCurrentFieldValue${numberOfFields}" class="dropdowns" name="expectedCurrentFieldValue" onchange="onChangeHandler(event)">
                <option value="">Choose here</option>
                <option value="!null">Present</option>
                <option value="null">Not Present</option>
            </select>
        </div>
    </div>
    `
    let buttons = document.getElementById('new-field-and-submit-button')
    document.getElementById('configs-upload').insertBefore(configContainer, buttons)

}

function onChangeHandler(event) {
    index = extractIndexFromId(event.target.id)
    let fieldName = document.getElementById(`field${index}`).value
    console.log(`fieldname: ${fieldName}`)
    if (event.target.name == "dependentOn") {
        toggleDependencyInputs(event.target)
    }
    if (event.target.name == "fieldName") {
        document.getElementById(`field${index}`).setAttribute('readonly', 'true');
    }
    if (payload[fieldName] == undefined) payload[fieldName] = {}
    if (event.target.name == "values") {
        let reader = new FileReader();
        reader.addEventListener('load', function (e) {
            let text = e.target.result
            payload[fieldName][event.target.name] = text.split('\n')
        });
        reader.readAsText(event.target.files[0])
        return
    }
    payload[fieldName][event.target.name] = event.target.value
    console.log(payload)
    convertPayloadToJsonArray(payload);    
}

function convertPayloadToJsonArray(payload) {
    let jsonArray = []
    let index = 0;
    for(let field in payload){
        jsonArray[index++] = payload[field];
    }
    console.log(jsonArray);
    return jsonArray;
}

async function sendConfigData() {
    let newPayload = convertPayloadToJsonArray(payload)
    await fetch('reset-config', {
        method: 'DELETE',
    })
    for (var i = 0; i < newPayload.length; i++) {
        sendOneConfig(newPayload[i])
    }
    alert("Submitted configuration of CSV\nNow you can add your csv file.")
    window.location.href = 'uploadCSV.html'
}

async function sendOneConfig(oneConfig) {
    const resp = await fetch('add-meta-data', {
        method: 'POST', body: JSON.stringify(oneConfig)
    });
    if (resp.status === 200) {
        const jsonData = await resp.json();
        console.log(jsonData)
    }
}

