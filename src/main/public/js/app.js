const payload = {};
const headers = [];

function toggleValueFieldTextBox(element) {
    console.log(element)
    useTextForValue = document.getElementById('use-text-for-value').value
    valueTextArea = document.getElementById('alternate-value')
    if (valueTextArea.style.display == 'block') {
        valueTextArea.style.display = 'none'
    }
    else {
        valueTextArea.style.display = 'block'
    }
}

function toggleDependencyInputs(element) {
    console.log(element)
    dependsOnColumn = document.getElementById('depends-on').value
    if (dependsOnColumn != "") {
        document.getElementById('dependent-value').style.visibility = 'visible'
        document.getElementById('current-value').style.visibility = 'visible'
    }
    else {
        document.getElementById('dependent-value').style.visibility = 'hidden'
        document.getElementById('current-value').style.visibility = 'hidden'
    }
}

async function uploadCSV() {
    let csvElement = document.getElementById('csv-id').files[0];
    const reader = new FileReader();
    reader.onload = await handleCsvFile
    reader.readAsText(csvElement)
    alert("CSV file submitted")
}

async function handleCsvFile(event) {
    const csv = event.target.result;
    const lines = csv.toString().split("\n");
    captureHeaders(lines[0])
    let convert = new Convert();
    const result = convert.csvToJson(lines);
    // const response = await sendRequest(result);
    // await handleResponse(response);
}

function captureHeaders(headersString) {
    headersList = headersString.split(',')
    headersList.forEach(element => {
        headers.push(element)
    });
    displayConfigurationsForCSV()
    window.location.href = "#configs-upload"
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
        }
        for (let key in jsonData) {
            const obj = jsonData[key]
            console.log("obj ", obj)
            printLines(obj)
        }
    } else {
        console.log("Error : ", response)
    }
}

function displayConfigurationsForCSV() {
    console.log(headers)
    console.log(headers.pop())
    headers.forEach((header, index) => {
        console.log(`Header: ${header} and index ${index}`)
        displayConfiguration(headers, header, index);
    })
    displaySubmitButton()
}

function displayConfiguration(headers, header, index) {
    let configContainer = document.createElement('div')
    configContainer.className = 'config-container'
    configContainer.innerHTML = `
    <div class="row1">
        <div id="field-name">Field Name:
            <span> ${header} </span>
        </div>
        <div id="field-type">
            <label for="type">Type: </label>
            <select id="type" name="type" class="dropdowns" onchange="onChangeHandler(event, '${header}')">
                <option value="">Choose Type</option>
                <option value="Number">Number</option>
                <option value="AlphaNumeric">AlphaNumeric</option>
                <option value="Alphabets">Alphabets</option>
            </select>
        </div>
        <div id="field-value">
            <label for="text-file-id">Values: </label>
            <input type="file" name="values" id="text-file-id" accept=".txt" onchange="onChangeHandler(event, '${header}')">
            <input type="checkbox" name="use-text-for-value" id="use-text-for-value"
                onclick="toggleValueFieldTextBox(this)">
            <span>Enter Values</span>
            <textarea name="alternate-value" id="alternate-value" cols="10" rows="10"></textarea>
        </div>
    </div>
    <div class="row1">
        <div id="min-length">
            <label for="min-len">Min-length: </label>
            <input type="number" id="min-len" name="min-length" class="inputs" onchange="onChangeHandler(event, '${header}')">
        </div>
        <div id="max-length">
            <label for="max-len">Max-length: </label>
            <input type="number" id="max-len" name="max-length" class="inputs" onchange="onChangeHandler(event, '${header}')">
        </div>
        <div id="fixed-length">
            <label for="fixed-len">Fixed-length: </label>
            <input type="number" id="fixed-len" name="fixed-length" class="inputs" onchange="onChangeHandler(event, '${header}')">
        </div>
    </div>
    <div class="row1">
        <div id="depends-on-column">
            <label for="dependentOnColumn">Dependency on column: </label>
            <select id="depends-on" class="dropdowns" name="dependentField" onchange="onChangeHandler(event, '${header}')">
                <option value="">Choose Field</option>
                <option value="pid">Product ID</option>
                <option value="pd">Product Description</option>
                <option value="cc">Country Code</option>
            </select>
        </div>
        <div id="dependent-value">
            <label for="dependent-field-value">Dependent Field Value: </label>
            <input type="text" class="inputs" id="dependent-field-value"
                placeholder="null for no value, !null for any value">
        </div>
        <div id="current-value">
            <label for="expectedCurrentFieldValue">Expected Current Field Value: </label>
            <select id="expectedCurrentFieldValue" class="dropdowns">
                <option value="">Choose here</option>
                <option value="!null">Present</option>
                <option value="null">Not Present</option>
            </select>
        </div>
    </div>
    `
    document.getElementById('configs-upload').appendChild(configContainer)
}

function displaySubmitButton() {
    let configSubmitButton = document.createElement('button')
    configSubmitButton.className = 'button'
    configSubmitButton.id = 'upload-configs'
    configSubmitButton.innerText = 'Submit'

    document.getElementById('configs-upload').appendChild(configSubmitButton)
}

function onChangeHandler(event, fieldName) {
    console.log(`event.target.name: ${event.target.name}`)
    if (event.target.name == "dependentField") {
        triggerNullValueField(fieldName)
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
}

