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

function toggleDateInput(element) {
    let index = extractIndexFromId(element.id)
    dateType = document.getElementById(`type${index}`).value
    if (dateType == "Date") {
        document.getElementById(`date-format-div${index}`).style.display = 'block'
    }
    else {
        document.getElementById(`date-format-div${index}`).style.display = 'none'
    }
}

function toggleTimeInput(element) {
    let index = extractIndexFromId(element.id)
    timeType = document.getElementById(`type${index}`).value
    if (timeType == "Time") {
        document.getElementById(`time-format-div${index}`).style.display = 'block'
    }
    else {
        document.getElementById(`time-format-div${index}`).style.display = 'none'
    }
}

function toggleDateTimeInput(element) {
    let index = extractIndexFromId(element.id)
    dateTimeType = document.getElementById(`type${index}`).value
    if (dateTimeType == "Date-Time") {
        document.getElementById(`date-time-format-div${index}`).style.display = 'block'
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
                <option value="Number">Number</option>
                <option value="AlphaNumeric">AlphaNumeric</option>
                <option value="Alphabets">Alphabets</option>
                <option value="Date">Date</option>
                <option value="Time">Time</option>
                <option value="Date-Time">Date-Time</option>
            </select>
        </div>
        <div id="date-format-div${numberOfFields}" style="display: none;">
            <label for="date-format${numberOfFields}">Date Format</label>
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
            <label for="time-format${numberOfFields}">Time Format</label>
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
            <label for="date-time-format${numberOfFields}">Date-Time Format</label>
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
            <input type="file" class="choose-file" name="values" id="text-file-id${numberOfFields}" accept=".txt" onchange="onChangeHandler(event)">
            <div class="extra-values">
                <input type="checkbox" name="use-text-for-value" id="use-text-for-value${numberOfFields}"
                    onclick="toggleValueFieldTextBox(this)">
                <span>Enter Values</span>
                <textarea name="alt-values" id="alternate-value${numberOfFields}" cols="10" rows="10"
                    class="displayNone alternate-value" onchange="onChangeHandler(event)"></textarea>
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
    console.log(`fieldname: ${fieldName}`)
    if (event.target.name == "dependentOn") {
        toggleDependencyInputs(event.target)
    }
    if (event.target.name == "type") {
        toggleDateInput(event.target)
        toggleTimeInput(event.target)
        toggleDateTimeInput(event.target)
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
    if (event.target.name == "alt-values") {
        payload[fieldName]['values'] = String(event.target.value).split('\n')
        return
    }
    payload[fieldName][event.target.name] = lowerCase(event.target.value, event.target.name)
    console.log(payload)
}

function lowerCase(data, field) {
    if (field == 'pattern'){
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
    let newPayload = convertPayloadToJsonArray(payload)
    await fetch('reset-config', {
        method: 'DELETE',
    })
    for (var i = 0; i < newPayload.length; i++) {   
        sendOneConfig(newPayload[i])
    }   
    customConfirm()
}

function customAlert(){
    var blurBg = document.getElementById("blur")
    blurBg.classList.toggle('active')
    var alertPopup = document.getElementById("alert-popup")
    alertPopup.classList.toggle('active')
}

function customConfirm(){
    var blurBg = document.getElementById("blur")
    blurBg.classList.toggle('active')
    const okButton = document.getElementById('ok-btn')
    okButton.addEventListener("click", () => {
	    window.location.href = 'uploadCSV.html'
    });
    var confirmPopup = document.getElementById("confirm-popup")
    confirmPopup.classList.toggle('active')
}

function validateInputFields() {
    for (let index = 0; index <= numberOfFields; index++) {
        let isFieldEmpty = document.getElementById(`field${index}`).value == ""
        let isTypeEmpty = document.getElementById(`type${index}`).value == ""
        if (isFieldEmpty || isTypeEmpty) {
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