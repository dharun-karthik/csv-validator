const headers = [];
let numberOfFields = 0;
let valuesFieldText;
let ruleNameListInDB = [];

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
    const response = await fetch('get-config-names', {
        method: 'GET',
    });

    if (response.status === 200) {
        ruleNameListInDB = await response.json()
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

function extractIndexFromId(fieldId) {
    let id = fieldId.match(/(\d+)/)
    return id[0]
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
    if (response.status !== 201) {
        window.location.href = 'pages/errors.html'
    }
    sessionStorage.setItem('config-json', JSON.stringify(newPayload));
    console.log("After adding metaDate")
    window.location.href = 'errors.html'
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
    valuesFieldText = document.getElementById(`value-textbox${index}`).value
}

function hideValues(elementId) {
    let index = extractIndexFromId(elementId)
    let modalDiv = document.getElementById(`value-modal${index}`)
    modalDiv.style.display = "none"
    document.getElementById(`value-textbox${index}`).value = valuesFieldText
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
    if (jsonData == null) {
        return false
    }
    return jsonData.length - 1 === numberOfFields
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

function validateRuleName() {
    document.getElementById("tooltiptext").style.visibility = "hidden"

    let ruleName = document.getElementById("rule-name-input").value
    if (ruleNameListInDB.includes(ruleName)) {
        document.getElementById("rule-name-message").style.visibility = "visible"
    }
    else
        document.getElementById("rule-name-message").style.visibility = "hidden"
}

function sendRuleName() {
    let ruleName = document.getElementById("rule-name-input").value
    if (ruleName === "") {
        document.getElementById("tooltiptext").style.visibility = "visible"
        return
    }
    if (!ruleNameListInDB.includes(ruleName)) {
        saveRuleInDB(ruleName)
        loadRuleNamesFromDB()
        displaySavedSuccessfully()
        enterRuleName()
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
    return response.status === 200;

}

function displayRuleName() {
    let select = document.getElementById("display-rule")
    for (let index = 0; index < ruleNameListInDB.length; index++) {
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
        headers: {
            'config-name': ruleName
        }
    })
    if (response.status === 200) {
        const jsonData = await response.json()
        console.log(JSON.stringify(jsonData))
        if(!displayConfigDataOrDisplayError(JSON.stringify(jsonData))){
            document.getElementById("display-rule").value=""
        }
    }
}

function displaySavedSuccessfully() {
    document.getElementById("success-popup").style.position = "absolute"
    document.getElementById("success-popup").style.left = "300px"
    setTimeout(function () {
        document.getElementById('success-popup').className = 'hide';
    }, 3000);
}
