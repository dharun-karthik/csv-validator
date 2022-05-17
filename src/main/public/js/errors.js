let errors = []
let count = -1
let errorTypes = []
let errorLines = []
let errorLinesInOneColumn = []

window.addEventListener('load', async () => getErrorsFromServer());

async function getErrorsFromServer() {
    const response = await fetch('validate', {
        method: 'GET',
    });
    if (response.status === 200) {
        const jsonData = await response.json();
        hideLoadingText();
        displayErrorsOrValid(jsonData)
    }
}

function hideLoadingText() {
    document.getElementById('uploading-pop-up-errors').style.display = 'none';
    document.getElementById('error-page').className = 'section'
}

async function displayErrorsOrValid(jsonData) {
    errors = jsonData
    console.log(`errors.length ${jsonData}`)
    if (JSON.stringify(jsonData)==='{}') {
        printCsvValid()
        return
    }
    displayErrors()
}

function printCsvValid() {
    let container = document.getElementById('display-no-errors')
    let errorContainer = document.getElementById('display-errors')
    container.innerHTML = ""
    errorContainer.style.display = 'none'

    let outerDiv = document.createElement('div')
    outerDiv.className = 'no-error';

    let innerDiv = document.createElement('div')
    innerDiv.className = 'valid-csv';
    innerDiv.id = "valid-csv-id"
    innerDiv.innerText = 'CSV has no errors'

    outerDiv.appendChild(innerDiv)
    container.appendChild(outerDiv)
}

function countObjectKeys(obj) { 
    return Object.keys(obj).length; 
}

function getKeys(obj) {
    return Object.keys(obj)
}

function displayErrors() {
    let keyList = getKeys(errors)
    for(let key in keyList) {
        let keyName = keyList[key]
        displayErrorInColumn(keyName)
    }
}

function displayErrorInColumn(keyName) {
    let errorCount = displayTotalErrorCount(errors[keyName])
    displayOneColumnInDOM(keyName, errorCount)
    displayErrorInDetail(errors[keyName]["details"])
}

function displayOneColumnInDOM(columnName, errorCount) {
    count++
    let element = document.createElement('div')
    element.className = 'one-column'
    element.id = `column${count}`
    element.setAttribute('onclick', 'displayColumnInDetail(this)')
    element.innerHTML = `
        <div class="column-title">
            ${columnName}
        </div>
        <div class="column-error-count">
            ${errorCount} errors
        </div>`
    document.getElementById('column-list-container').appendChild(element)
}

function displayTotalErrorCount(obj) {
    return obj["total-error-count"]
}

function displayErrorInDetail(obj) {
    let distinctErrorTypeInAColumnList = getKeys(obj)
    let errorTypesInOneColumn = []
    errorLinesInOneColumn = []
    for(let index in distinctErrorTypeInAColumnList) {
        let errorType = distinctErrorTypeInAColumnList[index]
        errorTypesInOneColumn.push(displayErrorTypeAndCount(obj[errorType], errorType))
    }
    errorTypes.push(errorTypesInOneColumn)
    errorLines.push(errorLinesInOneColumn)
}

function displayErrorTypeAndCount(obj, errorType) {
    displayLineNumberAndDescription(obj["lines"])
    return [errorType, obj["error-count"]]
}

function displayLineNumberAndDescription(obj) {
    let errorLinesOfOneType = []
    for (let [lineNumber, description] of Object.entries(obj)) {
        errorLinesOfOneType.push([lineNumber, description])
    }
    errorLinesInOneColumn.push(errorLinesOfOneType)
}

function clearPreviousErrors() {
    document.getElementById('display-errors').innerText = "";
}

function extractIndexFromId(fieldId) {
    return fieldId.match(/(\d+)/)[0]
}

function displayColumnInDetail(target) {
    document.getElementById('details-container').innerText = ""
    removeActiveFromAllColumns()
    document.getElementById(`${target.id}`).className = 'one-column active'
    let index = extractIndexFromId(target.id)
    displayColumnDetailsOf(index)
}

function removeActiveFromAllColumns() {
    let totalErrorColumns = countObjectKeys(errors)
    for (let index = 0; index < totalErrorColumns; index++) {
        document.getElementById(`column${index}`).classList.remove('active')
    }
}

function displayColumnDetailsOf(index) {
    let errorTypeHTML = getErrorTypeHTMLAt(index)
    document.getElementById('details-container').innerHTML = errorTypeHTML
}

function extractErrorFromWholeErrorLine(line, shortErrorDescription) {
    if(!line.match(/(in)/)){
        return shortErrorDescription   
    }    
    let indexOfIn = line.match(/(in)/).index - 1
    return line.slice(0, indexOfIn)
}

function getErrorTypeHTMLAt(index) {
    let errorsInOneColumnHTML = ""
    for (let indexToFill = 0; indexToFill < errorTypes[index].length; indexToFill++) {
        errorsInOneColumnHTML += `
        <div class="one-error-type-section">
        <div class="error-details-without-lines">
            <div class="error-type-title">
                <span id="error-type-message"> ${extractErrorFromWholeErrorLine(errorLines[index][indexToFill][0][1], errorTypes[index][indexToFill][0])} </span> in <span id="error-count"
                    class="column-name"> ${errorTypes[index][indexToFill][1]} </span> lines
            </div>
            <button id="error${indexToFill}" class="button no-margin" onclick="displayLinesForErrorType(this)">See <span>More</span></button>
        </div>`
        errorsInOneColumnHTML += getOneTypeOfError(index, indexToFill)
        errorsInOneColumnHTML += '</div>'
    }
    console.log(errorsInOneColumnHTML)
    return errorsInOneColumnHTML
}

function getOneTypeOfError(index, indexToFill) {
    let linesHTML = `<div class="lines-container" style="display: none;" id="line-container${indexToFill}">`
    for (let indexOfLine = 0; indexOfLine < errorTypes[index][indexToFill][1]; indexOfLine++) {
        linesHTML += `
        <div class="one-error-line">
                Line ${parseInt(errorLines[index][indexToFill][indexOfLine][0]) + 1}: ${errorLines[index][indexToFill][indexOfLine][1]}
        </div>
        `
    }
    linesHTML += `</div>`
    return linesHTML
}

function displayLinesForErrorType(target) {
    let index = extractIndexFromId(target.id)
    let element = document.getElementById(`line-container${index}`)
    if (element.style.display === 'block') {
        element.style.display = 'none'
        document.querySelector(`#error${index} span`).innerText = "More"
        return
    }
    element.style.display = 'block'
    document.querySelector(`#error${index} span`).innerText = "Less"
}