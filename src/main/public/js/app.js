const payload = {};
const headers = [];
var numberOfFields = 0;
const nameIdMap = {
    "fieldName": "field",
    "type": "type",
    "values": "value-textbox",
    "minLength": "min-len",
    "maxLength": "max-len",
    "length": "fixed-len",
    "isNullAllowed": "allow-null",
    "dependentOn": "depends-on",
    "expectedDependentFieldValue": "dependent-field-value",
    "expectedCurrentFieldValue": "expectedCurrentFieldValue"
}

window.addEventListener('load', async () => loadMetaData());

async function loadMetaData() {
    let headers = JSON.parse(sessionStorage.getItem('headers'))
    const resp = await fetch('get-meta-data', {
        method: 'GET',
    });
    if (resp.status === 200) {
        const jsonData = await resp.json();
        if (jsonData.length == 0) {
            displayHeadersInContainers(headers);
            return
        }
        displayConfigDataFromServer(jsonData);
    }
}

function displayHeadersInContainers(headers) {
    let numberOfHeaders = headers.length - 1
    displayEmptyContainers(numberOfHeaders)
    fillHeadersInContainers(headers)
}

function fillHeadersInContainers(headers) {
    headers.forEach((fieldName, index) => {
        document.getElementById(`field${index}`).value = fieldName
    })
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
            if (key == "isNullAllowed") {
                toggleYesNoButton(`${nameIdMap[key]}${index}`)
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
    document.getElementById(`${typeOfPattern}-format-div${index}`).style.display = 'block'
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
        document.getElementById(`dependent-value${index}`).style.display = 'block'
        document.getElementById(`current-value${index}`).style.display = 'block'
    } else {
        document.getElementById(`dependent-value${index}`).style.display = 'none'
        document.getElementById(`current-value${index}`).style.display = 'none'
    }
}

function disableLengthInput(index) {
    document.getElementById(`row${index}`).style.display = "none"
    document.getElementById(`field-value${index}`).style.display = "none"
}

function enableLengthInput(element) {
    let index = extractIndexFromId(element.id)
    let unknownType = document.getElementById(`type${index}`).value
    let lengthRequiredDataTypeList = ["number", "alphanumeric", "alphabets", "email", "text"]
    if (lengthRequiredDataTypeList.includes(unknownType)) {
        document.getElementById(`row${index}`).style.display = "flex"
        document.getElementById(`field-value${index}`).style.display = "block"
    }
}

function toggleDateInput(element) {
    let index = extractIndexFromId(element.id)
    dateType = document.getElementById(`type${index}`).value
    if (dateType == "date") {
        document.getElementById(`date-format-div${index}`).style.display = 'block'
        disableLengthInput(index)
    } else {
        document.getElementById(`date-format-div${index}`).style.display = 'none'
    }
}

function toggleTimeInput(element) {
    let index = extractIndexFromId(element.id)
    timeType = document.getElementById(`type${index}`).value
    if (timeType == "time") {
        document.getElementById(`time-format-div${index}`).style.display = 'block'
        disableLengthInput(index)
    } else {
        document.getElementById(`time-format-div${index}`).style.display = 'none'
    }
}

function toggleDateTimeInput(element) {
    let index = extractIndexFromId(element.id)
    dateTimeType = document.getElementById(`type${index}`).value
    if (dateTimeType == "date-time") {
        document.getElementById(`date-time-format-div${index}`).style.display = 'block'
        disableLengthInput(index)
    } else {
        document.getElementById(`date-time-format-div${index}`).style.display = 'none'
    }
}

function toggleYesNoButton(element) {
    let index = extractIndexFromId(element)
    let oldValue = document.getElementById(element).value
    if (oldValue == "Yes") {
        document.getElementById(element).value = "No"
        document.getElementById(element).style.backgroundColor = '#ffeded'
        document.getElementById(`empty-field-hint${index}`).style.display = 'none'
        return
    }
    document.getElementById(element).value = "Yes"
    document.getElementById(element).style.backgroundColor = 'rgb(223, 253, 223)'
    document.getElementById(`empty-field-hint${index}`).style.display = 'inline'
}

function extractIndexFromId(fieldId) {
    let id = fieldId.match(/(\d+)/)
    return id[0]
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
                <option value="email">Email</option>
                <option value="text">Text</option>
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
        <div id="field-value${numberOfFields}">

            <label for="text-file-id${numberOfFields}">Values: </label>
            <button id="edit-button${numberOfFields}" onclick="displayValues(this.id)" class="button-on-config-form">ADD</button>
            <div class="modal" id="value-modal${numberOfFields}">
            <div class="modal-content">
                <span class="close" id="close-modal${numberOfFields}" onclick="hideValues(this.id)">&times;</span>
                <div class="modal-container">
                    <div class="text-box">
                        <h3>Enter Values</h3>
                            <textarea class="value-text" id="value-textbox${numberOfFields}" rows="30" cols="50"></textarea>
                        </div>
                        <div class="upload-values">
                            <div class="upload-div">
                                <input type="file" class="choose-file config-file" id="value-file${numberOfFields}" name="values" accept=".txt">
                                <input type="submit" class="button-on-config-form" value="Fetch Values From File" id="value-fileupload-btn${numberOfFields}" onclick="uploadFileAndChangeContents(this.id)">
                            </div>
                            <div class="hints">
                                <h2>Hints: </h2>
                                <h3>- Choose txt file for values and click "Fetch Values From File" to display in text box</h3>
                                <h3>- In text box, <span class="important">Enter values on new lines</span> </h3>
                                <h3>- If values are seperated by comma(,), don't give any space</h3>
                            </div>
                        </div>
                    </div>
                    <div class="save-button">
                        <button class="button big-font" id="save-btn${numberOfFields}" name="save-btn${numberOfFields}" onclick="saveValue(this.id)">Save</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row1" id="row${numberOfFields}">
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
        <div id="allow-null">
            <label for="allow-null${numberOfFields}" style="margin-right: 40px;">Allow empty value
                <input class="button-on-config-form bg-green" id="allow-null${numberOfFields}" name="allowNull" type="button" value="Yes" onclick="toggleYesNoButton(this.id)">
            </label>
        </div>
        <div id="depends-on-column">
            <label for="depends-on${numberOfFields}">Dependency on column: </label>
            <input type="text" id="depends-on${numberOfFields}" class="inputs" name="dependentOn" onchange="onChangeHandler(event)">
        </div>
    </div>
    <div class="row1">
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
    if (event.target.name == "dependentOn") {
        toggleDependencyInputs(event.target)
    }
    if (event.target.name == "type") {
        toggleDateInput(event.target)
        toggleTimeInput(event.target)
        toggleDateTimeInput(event.target)
        enableLengthInput(event.target)
    }
}

async function sendConfigData() {
    let newConfigData = generatePayload()
    let newPayload = convertPayloadToJsonArray(newConfigData)
    console.log("Before Reset")
    await sendResetConfigRequest();
    const response = await fetch('add-meta-data', {
        method: 'POST', body: JSON.stringify(newPayload)
    });
    if (response.status === 201) {
        storeErrorsInSessionStorage()
    }
}

async function storeErrorsInSessionStorage() {
    const response = await fetch('validate', {
        method: 'GET',
    });
    if (response.status === 200) {
        const jsonData = await response.json();
        sessionStorage.removeItem('errors')
        sessionStorage.setItem('errors', JSON.stringify(jsonData))
        window.location.href = 'errors.html'
        return
    }
    window.location.href = 'pages/404.html'
}

async function resetConfigs() {
    await sendResetConfigRequest();
    location.reload()
}

async function sendResetConfigRequest() {
    await fetch('reset-config', {
        method: 'DELETE',
    });
}

function customAlert() {
    var blurBg = document.getElementById("blur")
    blurBg.classList.toggle('active')
    var alertPopup = document.getElementById("alert-popup")
    alertPopup.classList.toggle('active')
}

function customConfirm() {
    let isInputValid = validateInputFields()
    if (!isInputValid) {
        customAlert();
        return
    }
    var blurBg = document.getElementById("blur")
    blurBg.classList.toggle('active')
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

function isDependentFieldValueEmpty(index) {
    return (document.getElementById(`dependent-value${index}`).style.visibility == 'visible'
        && document.getElementById(`dependent-field-value${index}`).value == "")
}

function isExpectedCurrentFieldValueEmpty(index) {
    return (document.getElementById(`current-value${index}`).style.visibility == 'visible'
        && document.getElementById(`expectedCurrentFieldValue${index}`).value == "")
}

function validateInputFields() {
    for (let index = 0; index <= numberOfFields; index++) {
        let isFieldEmpty = document.getElementById(`field${index}`).value == ""
        let isTypeEmpty = document.getElementById(`type${index}`).value == ""
        console.log("inside validate input")
        console.log(document.getElementById(`date-format-div${index}`).style.display == "block")
        console.log(document.getElementById(`date-format${index}`).value == "")
        if (isFieldEmpty || isTypeEmpty || isDateEmpty(index) || isTimeEmpty(index) ||
            isDateTimeEmpty(index) || isDependentFieldValueEmpty(index) || isExpectedCurrentFieldValueEmpty(index)) {
            return false
        }
    }
    return true
}

function generatePayload() {
    finalJson = {}
    for (let index = 0; index <= numberOfFields; index++) {
        let singleJson = {}
        let fieldName = lowerCase(document.getElementById(`field${index}`).value)
        let type = document.getElementById(`type${index}`).value

        singleJson = addOptionalDataToPayload(index, singleJson)

        if (type == "date" || type == 'date-time' || type == 'time') {
            singleJson["pattern"] = document.getElementById(`${type}-format${index}`).value
        }

        finalJson[fieldName] = singleJson
    }
    return finalJson
}


function addOptionalDataToPayload(index, singleJson) {
    for (fieldName in nameIdMap) {
        fieldId = nameIdMap[fieldName]
        if (fieldName == "values") {
            addValueDataToPayload(index, singleJson);
            continue
        }
        if (fieldName == "isNullAllowed") {
            addNullFieldValueToPayload(index, singleJson);
            continue
        }
        let data = document.getElementById(`${fieldId}${index}`).value
        let isDataEmpty = data != ""
        if (isDataEmpty) {
            singleJson[`${fieldName}`] = lowerCase(data, fieldName)
        }
    }
    return singleJson
}

function addValueDataToPayload(index, singleJson) {
    let data = document.getElementById(`${fieldId}${index}`).value;
    let doesDataContainComma = String(data).search(',') != -1
    if (doesDataContainComma) {
        singleJson["values"] = String(data).split(',');
        return
    }
    if (data != "") {
        singleJson["values"] = String(data).split('\n');
    }
}

function addNullFieldValueToPayload(index, singleJson) {
    let data = document.getElementById(`${fieldId}${index}`).value
    if (data == "No") {
        singleJson["isNullAllowed"] = data
    }
}

var tempValues

function displayValues(elementId) {
    let index = extractIndexFromId(elementId)
    let modalDiv = document.getElementById(`value-modal${index}`)
    modalDiv.style.display = "block"
    tempValues = document.getElementById(`value-textbox${index}`).value
}

function hideValues(elementId) {
    let index = extractIndexFromId(elementId)
    let modalDiv = document.getElementById(`value-modal${index}`)
    modalDiv.style.display = "none"
    document.getElementById(`value-textbox${index}`).value = tempValues
}

function uploadFileAndChangeContents(elementId) {
    var index = extractIndexFromId(elementId)
    var fileInputTag = document.getElementById(`value-file${index}`)
    var uploadedFile = fileInputTag.files[0]
    var fileReader = new FileReader();
    var textBox = document.getElementById(`value-textbox${index}`)
    fileReader.addEventListener("load", () => {
        textBox.value = fileReader.result;
    });
    fileReader.readAsText(uploadedFile, "UTF-8");
    fileInputTag.value = ''
}

function saveValue(elementId) {
    let index = extractIndexFromId(elementId)
    let modalDiv = document.getElementById(`value-modal${index}`)
    modalDiv.style.display = "none"
    changeButtonToEditIfValuesAdded(index)
}

function changeButtonToEditIfValuesAdded(index) {
    var textBoxValue = document.getElementById(`value-textbox${index}`).value
    var button = document.getElementById(`edit-button${index}`)
    console.log(textBoxValue)
    if (textBoxValue == "") {
        button.innerText = "ADD"
        button.style.backgroundColor = "#f4f9fe"
    } else {
        button.innerText = "EDIT"
        button.style.backgroundColor = "#dffddf"
    }
}

function downloadConfig() {
    let newConfigData = generatePayload()
    let jsonData = JSON.stringify(convertPayloadToJsonArray(newConfigData))
    download(jsonData)
}

function download(jsonData) {
    var element = document.createElement('a');
    element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(jsonData));
    element.setAttribute('download', 'csv-validator-config.json');
    element.style.display = 'none';
    document.body.appendChild(element);
    element.click();
    document.body.removeChild(element);
}

function openUploadRulesModal() {
    document.getElementById('upload-rules-modal').style.display = 'block';
}

function hideRuleUploadModal() {
    document.getElementById('upload-rules-modal').style.display = 'none';
}

function uploadConfig() {
    var jsonFile = document.getElementById('rules-json-id').files[0]
    var fileReader = new FileReader()
    fileReader.addEventListener("load", () => {
        let jsonData = JSON.parse(fileReader.result)
        displayConfigDataFromServers(jsonData);
        hideRuleUploadModal()
    });
    fileReader.readAsText(jsonFile, "UTF-8");
}

function displayConfigDataFromServers(jsonData) {
    let numberOfRows = jsonData.length - 1
    fillDataInContainer(jsonData)
}
