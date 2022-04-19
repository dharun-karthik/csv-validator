const payload = {};
const headers = [];
var numberOfFields = 0;
const nameIdMap = {
    "fieldName": "field",
    "type": "type",
    "values": "alternate-value",
    "minLength": "min-len",
    "maxLength": "max-len",
    "length": "fixed-len",
    "dependentOn": "depends-on",
    "expectedDependentFieldValue": "dependent-field-value",
    "expectedCurrentFieldValue": "expectedCurrentFieldValue"
}

window.addEventListener('load', async () => loadMetaData());

async function loadMetaData() {
    const resp = await fetch('get-meta-data', {
        method: 'GET',
    });
    if (resp.status === 200) {
        const jsonData = await resp.json();
        displayConfigDataFromServer(jsonData);
    }
}

function displayConfigDataFromServer(jsonData) {
    let numberOfRows = jsonData.length - 1
    displayEmptyContainers(numberOfRows)
    fillDataInContainer(jsonData)
}

function displayEmptyContainers(numberOfRows) {
    for (let index = 1; index <= numberOfRows; index++) {
        addNewField();
    }
}

function fillDataInContainer(jsonData) {
    jsonData.forEach((element, index) => {
        for (key in element) {
            console.log(`${index} - key: ${key}: ${element[key]}`)
            if (key == "pattern") {
                insertPatternInRespectiveField(index, element);
                continue
            }
            if (key == "dependencies") {
                displayDependencies(element, index);
                continue
            }
            console.log(`idToEdit: ${nameIdMap[key]}${index}`)
            document.getElementById(`${nameIdMap[key]}${index}`).value = element[key]
        }
    });
}

function displayDependencies(element, index) {
    for (dependencyField in element[key]) {
        oneDepenedency = element[key][dependencyField];
        document.getElementById(`dependent-value${index}`).style.visibility = 'visible';
        document.getElementById(`current-value${index}`).style.visibility = 'visible';
        displayOneDependency(index);
    }
}

function displayOneDependency(index) {
    for (dependencyKey in oneDepenedency) {
        document.getElementById(`${nameIdMap[dependencyKey]}${index}`).value = oneDepenedency[dependencyKey];
    }
}

function insertPatternInRespectiveField(index, element) {
    let typeOfPattern = document.getElementById(`${nameIdMap['type']}${index}`).value;
    console.log(`IdToEdit: ${typeOfPattern}-format${index}`);
    document.getElementById(`${typeOfPattern}-format${index}`).value = element[key];
}

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

function disableLengthInput(index) {
    document.getElementById(`min-len${index}`).disabled = true
    document.getElementById(`min-len${index}`).style.cssText = "background-color: #ffeded; cursor: not-allowed";
    document.getElementById(`max-len${index}`).disabled = true
    document.getElementById(`max-len${index}`).style.cssText = "background-color: #ffeded; cursor: not-allowed";
    document.getElementById(`fixed-len${index}`).disabled = true
    document.getElementById(`fixed-len${index}`).style.cssText = "background-color: #ffeded; cursor: not-allowed";
}   

function toggleDateInput(element) {
    let index = extractIndexFromId(element.id)
    dateType = document.getElementById(`type${index}`).value
    if (dateType == "date") {
        document.getElementById(`date-format-div${index}`).style.display = 'block'
        disableLengthInput(index)
    }
    else {
        document.getElementById(`date-format-div${index}`).style.display = 'none'
    }
}

function toggleTimeInput(element) {
    let index = extractIndexFromId(element.id)
    timeType = document.getElementById(`type${index}`).value
    if (timeType == "time") {
        document.getElementById(`time-format-div${index}`).style.display = 'block'
        disableLengthInput(index)
    }
    else {
        document.getElementById(`time-format-div${index}`).style.display = 'none'
    }
}

function toggleDateTimeInput(element) {
    let index = extractIndexFromId(element.id)
    dateTimeType = document.getElementById(`type${index}`).value
    if (dateTimeType == "date-time") {
        document.getElementById(`date-time-format-div${index}`).style.display = 'block'
        disableLengthInput(index)
    }
    else {
        document.getElementById(`date-time-format-div${index}`).style.display = 'none'
    }
}

function extractIndexFromId(fieldId) {
    return fieldId[fieldId.length - 1]
}

function addNewField() {
    numberOfFields += 1;
    let configContainer = document.createElement('div')
    configContainer.className = 'config-container'
    configContainer.innerHTML = `
    <div class="row1">
        <div id="field-name">
            <label for="field${numberOfFields}">Field Name<span class="required-field">*</span>: </label>
            <input type="text" id="field${numberOfFields}" class="inputs" name="fieldName" onchange="onChangeHandler(event)">
        </div>
        <div id="field-type">
            <label for="type${numberOfFields}">Type<span class="required-field">*</span>: </label>
            <select id="type${numberOfFields}" name="type" class="dropdowns" onchange="onChangeHandler(event)">
                <option value="">Choose Type</option>
                <option value="number">Number</option>
                <option value="alphanumeric">AlphaNumeric</option>
                <option value="alphabets">Alphabets</option>
                <option value="date">Date</option>
                <option value="time">Time</option>
                <option value="date-time">Date-Time</option>
            </select>
        </div>
        <div id="date-format-div${numberOfFields}" style="display: none;">
            <label for="date-format${numberOfFields}">Date Format<span class="required-field">*</span>: </label>
            <select id="date-format${numberOfFields}" name="pattern" class="dropdowns" onchange="onChangeHandler(event)">
                <option value="">Choose Type</option>
                <option value="dd/MM/uuuu">dd/MM/yyyy</option>
                <option value="dd/uuuu/MM">dd/yyyy/MM</option>
                <option value="MM/dd/uuuu">MM/dd/yyyy</option>
                <option value="MM/uuuu/dd">MM/yyyy/dd</option>
                <option value="uuuu/dd/MM">yyyy/dd/MM</option>
                <option value="uuuu/MM/dd">yyyy/MM/dd</option>
                <option value="dd-MM-uuuu">dd-MM-yyyy</option>
                <option value="dd-uuuu-MM">dd-yyyy-MM</option>
                <option value="MM-dd-uuuu">MM-dd-yyyy</option>
                <option value="MM-uuuu-dd">MM-yyyy-dd</option>
                <option value="uuuu-dd-MM">yyyy-dd-MM</option>
                <option value="uuuu-MM-dd">yyyy-MM-dd</option>
            </select>
        </div>
        <div id="time-format-div${numberOfFields}" style="display: none;">
            <label for="time-format${numberOfFields}">Time Format<span class="required-field">*</span>: </label>
            <select id="time-format${numberOfFields}" name="pattern" class="dropdowns" onchange="onChangeHandler(event)">
                <option value="">Choose Type</option>
                <option value="HH:mm:ss">HH:mm:ss</option>
                <option value="HH:ss:mm">HH:ss:mm</option>
                <option value="ss:HH:mm">ss:HH:mm</option>
                <option value="HH:ss:mm:SSS">HH:ss:mm:SSS</option>
                <option value="hh:ss:mm a">hh:ss:mm a</option>
                <option value="hh:ss:mma">hh:ss:mma</option>
                <option value="ahh:ss:mm">ahh:ss:mm</option>
                <option value="hh:ass:mm">hh:ass:mm</option>
                <option value="hh:ass:mm:SSS">hh:ass:mm:SSS</option>
            </select>
        </div>
        <div id="date-time-format-div${numberOfFields}" style="display: none;">
            <label for="date-time-format${numberOfFields}">Date-Time Format<span class="required-field">*</span>: </label>
            <select id="date-time-format${numberOfFields}" name="pattern" class="dropdowns" onchange="onChangeHandler(event)">
                <option value="">Choose Type</option>
                <option value="HH:mm:ss dd/MM/uuuu">HH:mm:ss dd/MM/yyyy</option>
                <option value="HH:mm:ss?dd/uuuu/MM">HH:mm:ss?dd/yyyy/MM</option>
                <option value="HH:ss:mm:dd:MM:uuuu">HH:ss:mm:dd:MM:yyyy</option>
                <option value="ss:HH:mm/uuuu/MM/dd">ss:HH:mm/yyyy/MM/dd</option>
                <option value="HH:ss:mm-dd-uuuu-MM">HH:ss:mm-dd-yyyy-MM</option>
                <option value="HH:ss:mm:SSS dd MM uuuu">HH:ss:mm:SSS dd MM yyyy</option>
                <option value="hh:ss:mm a,uuuu-MM-dd">hh:ss:mm a,yyyy-MM-dd</option>
                <option value="hh:ss:mma+dd/MM/uuuu">hh:ss:mma+dd/MM/yyyy</option>
                <option value="ahh:ss:mm () dd/MM/uuuu">ahh:ss:mm () dd/MM/yyyy</option>
                <option value="dd/MM/uuuu hh:ass:mm">dd/MM/yyyy hh:ass:mm</option>
                <option value="dd/MM/uuuu == hh:ass:mm:SSS">dd/MM/yyyy == hh:ass:mm:SSS</option>
                <option value="dd MM uuuu hh:ass:mm">dd MM yyyy hh:ass:mm</option>
                <option value="uuuu-MM-dd't'HH:mm:ss.SSS'z'">yyyy-MM-dd'T'HH:mm:ss.SSSZ</option>
            </select>
        </div>
        <div id="field-value">

            <label for="text-file-id${numberOfFields}">Values: </label>
            <button id="edit-button${numberOfFields}" onclick="displayValues(this.id)">Edit</button>
            <div class="modal" id="value-modal${numberOfFields}">
                <div class="modal-content">
                    <span class="close" id="close-modal${numberOfFields}" onclick="hideValues(this.id)">&times;</span>
                    <div class="modal-container">
                        <div class="text-box">
                            <p>Enter Values</p>
                            <textarea class="value-text" id="value-textbox${numberOfFields}" rows="30" cols="50"></textarea>
                        </div>
                        <div class="uplaod-values">
                            <input type="file" class="choose-file" id="value-file${numberOfFields}" name="values" accept=".txt">
                            <input type="submit" value="Upload" id="value-fileupload-btn${numberOfFields}" onclick="uploadFileAndChangeContents(this.id)">
                        </div>
                    </div>
                </div>
            </div>
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
    if (event.target.name == "dependentOn") {
        toggleDependencyInputs(event.target)
    }
    if (event.target.name == "type") {
        toggleDateInput(event.target)
        toggleTimeInput(event.target)
        toggleDateTimeInput(event.target)
    }
}

function lowerCase(data, field = "") {
    if (field == 'pattern') {
        return data
    }
    return String(data).toLowerCase()
}

function arrangeDependencies(payload) {
    for (let field in payload) {
        for (let key in payload[field]) {
            // console.log(key + ":" + payload[field][key]);
            if (key == "dependentOn") {
                assignDependencies(payload, field);
            }
        }
    }
    return payload;
}

function assignDependencies(payload, field) {
    payload[field]["dependencies"] = [{
        "dependentOn": payload[field]["dependentOn"],
        "expectedDependentFieldValue": payload[field]["expectedDependentFieldValue"],
        "expectedCurrentFieldValue": payload[field]["expectedCurrentFieldValue"]
    }];
}

function convertPayloadToJsonArray(payload) {
    let jsonArray = []
    let index = 0;
    payload = arrangeDependencies(payload);
    for (let field in payload) {
        jsonArray[index++] = payload[field];
    }
    console.log(jsonArray);
    return jsonArray;
}

async function sendConfigData() {
    let isInputValid = validateInputFields()
    if (!isInputValid) {
        customAlert();
        return
    }
    let newConfigData = generatePayload()
    let newPayload = convertPayloadToJsonArray(newConfigData)
    await fetch('reset-config', {
        method: 'DELETE',
    })
    for (var i = 0; i < newPayload.length; i++) {
        sendOneConfig(newPayload[i])
    }
    customConfirm()
}

function customAlert() {
    var blurBg = document.getElementById("blur")
    blurBg.classList.toggle('active')
    var alertPopup = document.getElementById("alert-popup")
    alertPopup.classList.toggle('active')
}

function customConfirm() {
    var blurBg = document.getElementById("blur")
    blurBg.classList.toggle('active')
    const okButton = document.getElementById('ok-btn')
    okButton.addEventListener("click", () => {
        window.location.href = 'uploadCSV.html'
    });
    var confirmPopup = document.getElementById("confirm-popup")
    confirmPopup.classList.toggle('active')
}

function isDateEmpty(index) {
    return (document.getElementById(`date-format-div${index}`).style.display == "block"
        && document.getElementById(`date-format${index}`).value == "")
}

function isTimeEmpty(index) {
    return (document.getElementById(`time-format-div${index}`).style.display == "block"
        && document.getElementById(`time-format${index}`).value == "")
}

function isDateTimeEmpty(index) {
    return (document.getElementById(`date-time-format-div${index}`).style.display == "block"
        && document.getElementById(`date-time-format${index}`).value == "")
}
function validateInputFields() {
    for (let index = 0; index <= numberOfFields; index++) {
        let isFieldEmpty = document.getElementById(`field${index}`).value == ""
        let isTypeEmpty = document.getElementById(`type${index}`).value == ""
        console.log("inside validate input")
        console.log(document.getElementById(`date-format-div${index}`).style.display == "block")
        console.log(document.getElementById(`date-format${index}`).value == "")
        if (isFieldEmpty || isTypeEmpty || isDateEmpty(index) || isTimeEmpty(index) || isDateTimeEmpty(index)) {
            return false
        }
    }
    return true
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

function generatePayload() {
    finalJson = {}
    for (let index = 0; index <= numberOfFields; index++) {
        let singleJson = {}
        let fieldName = lowerCase(document.getElementById(`field${index}`).value)
        let type = document.getElementById(`type${index}`).value
        singleJson["fieldName"] = fieldName
        singleJson["type"] = type
        singleJson["minLength"] = document.getElementById(`min-len${index}`).value
        singleJson["maxLength"] = document.getElementById(`max-len${index}`).value
        singleJson["length"] = document.getElementById(`fixed-len${index}`).value
        singleJson["dependentOn"] = lowerCase(document.getElementById(`depends-on${index}`).value)
        singleJson["expectedDependentFieldValue"] = lowerCase(document.getElementById(`dependent-field-value${index}`).value)
        singleJson["expectedCurrentFieldValue"] = lowerCase(document.getElementById(`expectedCurrentFieldValue${index}`).value)

        addValuesToPayload(index, singleJson);
        if (type == "date" || type == 'date-time' || type == 'time') {
            singleJson["pattern"] = document.getElementById(`${type}-format${index}`).value
        }

        finalJson[fieldName] = singleJson
    }
    return finalJson
}

function addValuesToPayload(index, singleJson) {
    let values = document.getElementById(`text-file-id${index}`).files[0];
    let reader = new FileReader();
    reader.addEventListener('load', function (e) {
        let text = e.target.result;
        singleJson["values"] = text.split('\n');
    });
    if (values != undefined) {
        reader.readAsText(values);
    }
    else {
        let alternateValue = document.getElementById(`alternate-value${index}`).value;
        console.log(typeof (alternateValue))
        if (String(alternateValue).search(',') != -1) {
            singleJson["values"] = String(alternateValue).split(',');
        }
        else {
            singleJson["values"] = String(alternateValue).split('\n');
        }
    }
}

function displayValues(elementId) {
    let configNumber = elementId[elementId.length - 1]
    let modalDiv = document.getElementById(`value-modal${configNumber}`)
    modalDiv.style.display = "block"
}

function hideValues(elementId) {
    let configNumber = elementId[elementId.length - 1]
    let modalDiv = document.getElementById(`value-modal${configNumber}`)
    modalDiv.style.display = "none"
}

function uploadFileAndChangeContents(elementId) {
    var configNumber = elementId[elementId.length - 1]
    var fileInputTag = document.getElementById(`value-file${configNumber}`)
    var uploadedFile = fileInputTag.files[0]
    var fileReader = new FileReader();
    var textBox = document.getElementById(`value-textbox${configNumber}`)
    fileReader.addEventListener("load", () => {
        textBox.value = fileReader.result;
    });
    fileReader.readAsText(uploadedFile, "UTF-8");
    fileInputTag.value =''
}
