const headers = [];
let numberOfFields = 0;
let tempValues;

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
    loadRuleNamesFromDB()
    let headers = getHeaders()
    const resp = await fetch('get-meta-data', {
        method: 'GET',
    });
    if (resp.status === 200) {
        const jsonData = await resp.json();
        if (jsonData.length === 0) {
            displayHeadersInContainers(headers);
            loadDataFromJsonFile()
            return
        }
        displayConfigDataFromServer(jsonData);
    }
}

async function loadRuleNamesFromDB() {
    const resp = await fetch('get-config-names', {
        method: 'GET',
    });

    if(resp.status === 200) {
        const jsonData = await resp.json()
        ruleNameListInDB = jsonData
    }
}

function loadDataFromJsonFile() {
    let jsonString = sessionStorage.getItem('json-upload')
    if(jsonString == null) {
        return
    }
    let jsonData = JSON.parse(jsonString)
    try {
        fillDataInContainer(jsonData)
        sessionStorage.removeItem('json-upload')
    }
    catch (err) {
        customAlertForInvalidJson()
        sessionStorage.removeItem('json-upload')
        location.reload()
    }
    
}

function getHeaders() {
    return JSON.parse(sessionStorage.getItem('headers'));

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

function fillDependencyColumn(headers) {
    for (let index = 0; index <= numberOfFields; index++) {
        fillAllDependenciesInOneRow(index, headers)
    }
}

function fillAllDependenciesInOneRow(index, headers) {
    for (let oneFieldIndex in headers) {
        let oneField = headers[oneFieldIndex]
        let newFieldForDependency = document.createElement('option')
        newFieldForDependency.value = oneField
        newFieldForDependency.innerText = oneField
        document.getElementById(`depends-on${index}`).appendChild(newFieldForDependency)
    }
}

function displayEmptyContainers(numberOfRows) {
    for (let index = 1; index <= numberOfRows; index++) {
        addNewField();
    }
    fillDependencyColumn(getHeaders())
}

function fillDataInContainer(jsonData) {
    let typesWithPatterns = ["date", "time", "date-time"]
    jsonData.forEach((element, index) => {
        for (key in element) {
            console.log(`${index} - key: ${key}: ${element[key]}`)
            if (key === "dependencies") {
                displayDependencies(element, index);
                continue
            }
            if (key === "isNullAllowed") {
                toggleYesNoButton(`${nameIdMap[key]}${index}`)
                continue
            }
            if (key === "type" && typesWithPatterns.includes(element[key])) {
                disableLengthInput(index)
            }
            if (key === "pattern") {
                insertPatternInRespectiveField(index, element);
                continue
            }
            console.log(`idToEdit: ${nameIdMap[key]}${index}`)
            document.getElementById(`${nameIdMap[key]}${index}`).value = element[key]
            changeButtonToEditIfValuesAdded(index)
        }
    });
}

function displayDependencies(element, index) {
    for (dependencyField in element[key]) {
        oneDepenedency = element[key][dependencyField];
        document.getElementById(`dependent-value${index}`).style.display = 'block';
        document.getElementById(`current-value${index}`).style.display = 'block';
        displayOneDependency(index);
    }
}

function displayOneDependency(index) {
    for (dependencyKey in oneDepenedency) {
        document.getElementById(`${nameIdMap[dependencyKey]}${index}`).value = oneDepenedency[dependencyKey];
    }
}

function insertPatternInRespectiveField(index, element) {
    console.log(`${nameIdMap['type']}${index}   `)
    let typeOfPattern = document.getElementById(`${nameIdMap['type']}${index}`).value;
    console.log(`insertPattern -> ${typeOfPattern} -->`)
    document.getElementById(`${typeOfPattern}-format-div${index}`).style.display = 'block'
    document.getElementById(`${typeOfPattern}-format${index}`).value = element[key];
}

function toggleDependencyInputs(element) {
    let index = extractIndexFromId(element.id)
    let dependsOnColumn = document.getElementById(`depends-on${index}`).value
    if (dependsOnColumn !== "") {
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
    let dateType = document.getElementById(`type${index}`).value
    if (dateType === "date") {
        document.getElementById(`date-format-div${index}`).style.display = 'block'
        disableLengthInput(index)
    } else {
        document.getElementById(`date-format-div${index}`).style.display = 'none'
    }
}

function toggleTimeInput(element) {
    let index = extractIndexFromId(element.id)
    let timeType = document.getElementById(`type${index}`).value
    if (timeType === "time") {
        document.getElementById(`time-format-div${index}`).style.display = 'block'
        disableLengthInput(index)
    } else {
        document.getElementById(`time-format-div${index}`).style.display = 'none'
    }
}

function toggleDateTimeInput(element) {
    let index = extractIndexFromId(element.id)
    let dateTimeType = document.getElementById(`type${index}`).value
    if (dateTimeType === "date-time") {
        document.getElementById(`date-time-format-div${index}`).style.display = 'block'
        disableLengthInput(index)
    } else {
        document.getElementById(`date-time-format-div${index}`).style.display = 'none'
    }
}

function toggleYesNoButton(element) {
    let oldValue = document.getElementById(element).value
    if (oldValue === "Yes") {
        document.getElementById(element).value = "No"
        document.getElementById(element).style.backgroundColor = '#ffeded'
        return
    }
    document.getElementById(element).value = "Yes"
    document.getElementById(element).style.backgroundColor = '#c5e3ff'
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
            <label for="field${numberOfFields}">Field name<span class="required-field">*</span>: </label>
            <input type="text" id="field${numberOfFields}" class="inputs" name="fieldName" onchange="onChangeHandler(event)">
        </div>
        <div id="field-type">
            <label for="type${numberOfFields}">Type<span class="required-field">*</span>: </label>
            <select id="type${numberOfFields}" name="type" class="dropdowns" onchange="onChangeHandler(event)">
                <option value="">Choose type</option>
                <option value="number">Number</option>
                <option value="alphanumeric">AlphaNumeric</option>
                <option value="alphabets">Alphabets</option>
                <option value="date">Date</option>
                <option value="time">Time</option>
                <option value="date-time">Datetime</option>
                <option value="email">Email</option>
                <option value="text">Text</option>
            </select>
        </div>
        <div id="date-format-div${numberOfFields}" style="display: none;">
            <label for="date-format${numberOfFields}">Date format<span class="required-field">*</span>: </label>
            <select id="date-format${numberOfFields}" name="pattern" class="dropdowns" onchange="onChangeHandler(event)">
                <option value="">Choose type</option>
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
            <label for="time-format${numberOfFields}">Time format<span class="required-field">*</span>: </label>
            <select id="time-format${numberOfFields}" name="pattern" class="dropdowns" onchange="onChangeHandler(event)">
                <option value="">Choose type</option>
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
            <label for="date-time-format${numberOfFields}">Datetime format<span class="required-field">*</span>: </label>
            <select id="date-time-format${numberOfFields}" name="pattern" class="dropdowns" onchange="onChangeHandler(event)">
                <option value="">Choose type</option>
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
                <option value="uuuu-MM-dd't'HH:mm:ss">yyyy-MM-dd't'HH:mm:ss</option>
                <option value="uuuu-MM-dd't'HH:mm:ss.SSS'z'">yyyy-MM-dd'T'HH:mm:ss.SSSZ</option>
            </select>
        </div>
        <div id="field-value${numberOfFields}">

            <label for="edit-button${numberOfFields}">Allowed values: </label>
            <button id="edit-button${numberOfFields}" onclick="displayValues(this.id)" class="button-on-config-form">Add</button>
            <div class="modal" id="value-modal${numberOfFields}">
            <div class="modal-content">
                <span class="close" id="close-modal${numberOfFields}" onclick="hideValues(this.id)">&times;</span>
                <div class="modal-container">
                    <div class="text-box">
                        <h3>Enter values</h3>
                            <textarea class="value-text" id="value-textbox${numberOfFields}" rows="30" cols="50"></textarea>
                        </div>
                        <div class="upload-values">
                            <div class="upload-div">
                                <input type="file" class="choose-file config-file" id="value-file${numberOfFields}" name="values" accept=".txt">
                                <input type="submit" class="button-on-config-form" value="Fetch Values From File" id="value-fileupload-btn${numberOfFields}" onclick="uploadFileAndChangeContents(this.id)">
                            </div>
                            <div class="hints">
                                <h2>Hints: </h2>
                                <h3>- Choose txt file for values and click "Fetch values from file" to display in text box</h3>
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
            <input type="number" name="minLength" id="min-len${numberOfFields}" class="inputs" onchange="onChangeHandler(event)" min="1" oninput="validity.valid||(value='');">
        </div>
        <div id="max-length">
            <label for="max-len${numberOfFields}">Max-length: </label>
            <input type="number" name="maxLength" id="max-len${numberOfFields}" class="inputs" onchange="onChangeHandler(event)" min="1" oninput="validity.valid||(value='');">
        </div>
        <div id="fixed-length">
            <label for="fixed-len${numberOfFields}">Fixed-length: </label>
            <input type="number" name="length" id="fixed-len${numberOfFields}" class="inputs" onchange="onChangeHandler(event)" min="1" oninput="validity.valid||(value='');">
        </div>
    </div>
    <div class="row1">
        <div id="allow-null">
            <label for="allow-null${numberOfFields}" style="margin-right: 40px;">Allow empty value
                <input class="button-on-config-form bg-lightblue" id="allow-null${numberOfFields}" name="allowNull" type="button" value="Yes" onclick="toggleYesNoButton(this.id)">
            </label>
        </div>
        <div id="depends-on-column">
            <label for="depends-on${numberOfFields}">Dependency on column:
                <select class="dropdowns" id="depends-on${numberOfFields}" name="dependentOn" onchange="onChangeHandler(event)">
                    <option value="">Choose field</option>
                </select> 
            </label>
        </div>
    </div>
    <div class="row1">
        <div id="dependent-value${numberOfFields}" class="hidden">
            <label for="dependent-field-value${numberOfFields}">Dependent field value<span class="required-field">*</span>: </label>
            <input class="inputs extra-width" list="dependent-field-valuelist${numberOfFields}" id="dependent-field-value${numberOfFields}" name="expectedDependentFieldValue">
                <datalist id="dependent-field-valuelist${numberOfFields}">
                    <option value="null">Empty</option>
                    <option value="!null">Not Empty</option>
                </datalist>
        </div>
        <div id="current-value${numberOfFields}" class="hidden">
            <label for="expectedCurrentFieldValue${numberOfFields}">Expected current field value<span class="required-field">*</span>: </label>
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
    if (event.target.name === "dependentOn") {
        toggleDependencyInputs(event.target)
    }
    if (event.target.name === "type") {
        toggleDateInput(event.target)
        toggleTimeInput(event.target)
        toggleDateTimeInput(event.target)
        enableLengthInput(event.target)
    }
}

async function sendConfigData() {
    let newConfigData = generatePayload()
    let newPayload = convertPayloadToJsonArray(newConfigData)
    await sendResetConfigRequest();
    const response = await fetch('add-meta-data', {
        method: 'POST', body: JSON.stringify(newPayload)
    });
    console.log("After adding metaDate")
    if (response.status === 201) {
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
    const blurBg = document.getElementById("blur")
    blurBg.classList.toggle('active')
    const alertPopup = document.getElementById("alert-popup")
    alertPopup.classList.toggle('active')
}

function customAlertForInvalidJson() {
    const blurBg = document.getElementById("blur")
    blurBg.classList.toggle('active')
    const alertPopup = document.getElementById("alert-popup-invalid-json")
    alertPopup.classList.toggle('active')
}

function customConfirm() {
    let isInputValid = validateInputFields()
    if (!isInputValid) {
        customAlert();
        return
    }
    const blurBg = document.getElementById("blur")
    blurBg.classList.toggle('active')
    const confirmPopup = document.getElementById("confirm-popup")
    confirmPopup.classList.toggle('active')
}

function isDateEmpty(index) {
    let isDateDivBlock = document.getElementById(`date-format-div${index}`).style.display === "block";
    let isDateFormatEmpty = document.getElementById(`date-format${index}`).value === "";
    return (isDateDivBlock && isDateFormatEmpty)
}

function isTimeEmpty(index) {
    let isTimeDivBlock = document.getElementById(`time-format-div${index}`).style.display === "block";
    let isTimeFormatEmpty = document.getElementById(`time-format${index}`).value === "";
    return (isTimeDivBlock && isTimeFormatEmpty)
}

function isDateTimeEmpty(index) {
    let isDateTimeDivBlock = document.getElementById(`date-time-format-div${index}`).style.display === "block";
    let isDateTimeFormatEmpty = document.getElementById(`date-time-format${index}`).value === "";
    return (isDateTimeDivBlock && isDateTimeFormatEmpty)
}

function isDependentFieldValueEmpty(index) {
    let isDependentValueVisible = document.getElementById(`dependent-value${index}`).style.display === 'block';
    let isDependentFieldEmpty = document.getElementById(`dependent-field-value${index}`).value === "";
    return (isDependentValueVisible && isDependentFieldEmpty)
}

function isExpectedCurrentFieldValueEmpty(index) {
    let isCurrentValueVisible = document.getElementById(`current-value${index}`).style.display === 'block';
    let isCurrentValueEmpty = document.getElementById(`expectedCurrentFieldValue${index}`).value === "";
    return (isCurrentValueVisible && isCurrentValueEmpty)
}

function validateInputFields() {
    for (let index = 0; index <= numberOfFields; index++) {
        let isFieldEmpty = document.getElementById(`field${index}`).value === ""
        let isTypeEmpty = document.getElementById(`type${index}`).value === ""
        console.log("inside validate input")
        console.log(document.getElementById(`date-format-div${index}`).style.display === "block")
        console.log(document.getElementById(`date-format${index}`).value === "")
        if (isFieldEmpty || isTypeEmpty || isDateEmpty(index) || isTimeEmpty(index) ||
            isDateTimeEmpty(index) || isDependentFieldValueEmpty(index) || isExpectedCurrentFieldValueEmpty(index)) {
            return false
        }
    }
    return true
}

function generatePayload() {
    let typesWithPatterns = ["date", "time", "date-time"]
    finalJson = {}
    for (let index = 0; index <= numberOfFields; index++) {
        let singleJson = {}
        let fieldName = document.getElementById(`field${index}`).value
        let type = document.getElementById(`type${index}`).value

        singleJson = addOptionalDataToPayload(index, singleJson)

        if (typesWithPatterns.includes(type)) {
            singleJson["pattern"] = document.getElementById(`${type}-format${index}`).value
        }

        finalJson[fieldName] = singleJson
    }
    return finalJson
}


function addOptionalDataToPayload(index, singleJson) {
    for (fieldName in nameIdMap) {
        fieldId = nameIdMap[fieldName]
        if (fieldName === "values") {
            addValueDataToPayload(index, singleJson);
            continue
        }
        if (fieldName === "isNullAllowed") {
            addNullFieldValueToPayload(index, singleJson);
            continue
        }
        let data = document.getElementById(`${fieldId}${index}`).value
        let isDataEmpty = data !== ""
        if (isDataEmpty) {
            singleJson[`${fieldName}`] = data
        }
    }
    return singleJson
}

function addValueDataToPayload(index, singleJson) {
    let data = document.getElementById(`${fieldId}${index}`).value;
    let doesDataContainComma = String(data).search(',') !== -1
    if (doesDataContainComma) {
        singleJson["values"] = String(data).split(',');
        return
    }
    if (data !== "") {
        singleJson["values"] = String(data).split('\n');
    }
}

function addNullFieldValueToPayload(index, singleJson) {
    let data = document.getElementById(`${fieldId}${index}`).value
    if (data === "No") {
        singleJson["isNullAllowed"] = data
    }
}

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
    const index = extractIndexFromId(elementId)
    const fileInputTag = document.getElementById(`value-file${index}`)
    const uploadedFile = fileInputTag.files[0]
    const fileReader = new FileReader()
    const textBox = document.getElementById(`value-textbox${index}`)
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
    const textBoxValue = document.getElementById(`value-textbox${index}`).value
    const button = document.getElementById(`edit-button${index}`)
    console.log(textBoxValue)
    if (textBoxValue === "") {
        button.innerText = "Add"
        button.style.backgroundColor = "#c5e3ff"
    } else {
        button.innerText = "Edit"
        button.style.backgroundColor = "#dffddf"
    }
}

function downloadConfig() {
    let newConfigData = generatePayload()
    let jsonData = JSON.stringify(convertPayloadToJsonArray(newConfigData))
    download(jsonData)
}

function download(jsonData) {
    const element = document.createElement('a')
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
    const jsonFile = document.getElementById('rules-json-id').files[0]
    const fileReader = new FileReader()
    fileReader.addEventListener("load", () => {
        displayConfigDataOrDisplayError(fileReader.result);
        hideRuleUploadModal()
    });
    fileReader.readAsText(jsonFile, "UTF-8");
}

function displayConfigDataOrDisplayError(jsonString) {

    let jsonData = JSON.parse(jsonString)
    if (isJsonValid(jsonData)) {
        sendResetConfigRequest()
        sessionStorage.setItem("json-upload", jsonString)
        location.reload()
        return
    }
    customAlertForInvalidJson()
}

function isJsonValid(jsonData) {
    return jsonData.length - 1 == numberOfFields
}

function enterRuleName() {
    let isInputValid = validateInputFields()
    if (!isInputValid) {
        customAlert();
        return
    }
    const blurBg = document.getElementById("blur")
    blurBg.classList.toggle('active')
    const ruleNamePopup = document.getElementById("enter-rule-name-popup")
    ruleNamePopup.classList.toggle('active')
}

var ruleNameListInDB = []
function validateRuleName() {
    document.getElementById("tooltiptext").style.visibility = "hidden"

    let ruleName = document.getElementById("rule-name-input").value
    if(ruleNameListInDB.includes(ruleName)) {
        document.getElementById("rule-name-message").style.visibility = "visible"
    }
    else
    document.getElementById("rule-name-message").style.visibility = "hidden"
}

function sendRuleName() {
    let ruleName = document.getElementById("rule-name-input").value
    if(ruleName == "") {
        document.getElementById("tooltiptext").style.visibility = "visible"
        return
    }
    if(!ruleNameListInDB.includes(ruleName)) {
        console.log("sendig config to backend db")
        saveRuleInDB(ruleName)
    }
}

async function saveRuleInDB(ruleName) {
    let newConfigData = generatePayload()
    let newPayload = convertPayloadToJsonArray(newConfigData)   
    let dbSendingFormat = {}
    dbSendingFormat[ruleName] = newPayload
    const response = await fetch('add-config', {
        method: 'POST', body: JSON.stringify(dbSendingFormat)
    });
}

function displayRuleName() {
    let select = document.getElementById("display-rule")
    for(let index = 0; index < ruleNameListInDB.length; index++) {
        let option = ruleNameListInDB[index]
        let element = document.createElement("option")
        element.textContent = option
        element.value = option
        select.appendChild(element)
    }
}

async function fetchAndFillRule() {
    let ruleName = document.getElementById("display-rule").value
    console.log(ruleName)
    const response = await fetch('get-config', {
        method: 'GET',
        headers : {
            'config-name' : 'smallSampleRule'
        }
    })
    if(response.status === 200) {
        const jsonData = await response.json()
        console.log(JSON.stringify(jsonData))
        displayConfigDataOrDisplayError(JSON.stringify(jsonData))
    }
}