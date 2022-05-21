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