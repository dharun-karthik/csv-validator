let errors = []

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
}

async function displayErrorsOrValid(jsonData) {
    errors = jsonData
    console.log(errors)
    if (errors.length == 0) {
        printCsvValid()
        return
    }
    displayErrors()
}

function printCsvValid() {
    let container = document.getElementById('display-errors')
    console.log(container)
    container.innerHTML = ""

    let outerDiv = document.createElement('div')
    outerDiv.className = 'no-error';

    let innerDiv = document.createElement('div')
    innerDiv.className = 'valid-csv';
    innerDiv.id = "valid-csv-id"
    innerDiv.innerText = 'CSV has no errors'

    outerDiv.appendChild(innerDiv)
    container.appendChild(outerDiv)
}

let errorInJson = {
    "country name": {
      "total-error-count": 0,
      "details": {}
    },
    "product description": {    
      "total-error-count": 1,
      "details": {
        "Length error": {
          "error-count": 1,
          "lines": {
            "1": "Value length should be lesser than 7 in product description : Table"
          }
        }
      }
    },
    "source city": {
      "total-error-count": 0,
      "details": {}
    },
    "source pincode": {
      "total-error-count": 1,
      "details": {
        "Value not found": {
          "error-count": 1,
          "lines": {
            "1": "Value Not Found source pincode : 560002"
          }
        }
      }
    }
  }

function countObjectKeys(obj) { 
    return Object.keys(obj).length; 
}

function getKeys(obj) {
    return Object.keys(obj)
}

function displayErrors() {
    let keyList = getKeys(errorInJson)
    console.log(keyList)
    for(let key in keyList) {
        let keyName = keyList[key]
        displayErrorInColumn(keyName)
    }
}

function displayErrorInColumn(keyName) {
    console.log(JSON.stringify(errorInJson[keyName]))
    displayTotalErrorCount(errorInJson[keyName])
    displayErrorInDetail(errorInJson[keyName]["details"])
}

function displayTotalErrorCount(obj) {
    console.log(obj["total-error-count"])
    return obj["total-error-count"]
}

function displayErrorInDetail(obj) {
    console.log(`error in details ------> ${JSON.stringify(obj)}`)
    let distinctTypesOfErrorCount = countObjectKeys(obj)
    console.log(`distinct type of error count ----> ${distinctTypesOfErrorCount}`)
    let distinctErrorTypeInAColumnList = getKeys(obj)
    console.log(distinctErrorTypeInAColumnList)
    for(let index in distinctErrorTypeInAColumnList) {
        let errorType = distinctErrorTypeInAColumnList[index]
        console.log(JSON.stringify(obj[errorType]))
        displayErrorTypeAndCount(obj[errorType], errorType)
    }
}

function displayErrorTypeAndCount(obj, errorType) {
    console.log(`ErrorType and count -> ${errorType}-----${obj["error-count"]}`)
    displayLineNumberAndDescription(obj["lines"])
}

function displayLineNumberAndDescription(obj) {
    console.log(`lines--------> ${JSON.stringify(obj)}`)
    for (let [lineNumber, description] of Object.entries(obj)) {
        console.log(lineNumber, description);
    }
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
    //todo
}

function displayColumnDetailsOf(index) {
    document.getElementById('details-container').innerHTML = `
    <div class="one-error-type-section">
        <div class="error-details-without-lines">
            <div class="error-type-title">
                <span id="error-type-message"> Value Error </span> in <span id="error-count"
                    class="column-name"> 72 </span> lines
            </div>
            <button id="error0" class="button no-margin" onclick="displayLinesForErrorType(this)">See <span>More</span></button>
        </div>
        <div class="lines-container" style="display: none;" id="line-container0">
            <div class="one-error-line">
                Line 1: This error
            </div>
            <div class="one-error-line">
                Line 4: This error
            </div>
            <div class="one-error-line">
                Line 11: This error
            </div>
            <div class="one-error-line">
                Line 41: This error
            </div>
            <div class="one-error-line">
                Line 21: This error
            </div>
            <div class="one-error-line">
                Line 14: This error
            </div>
        </div>
    </div>
    <div class="one-error-type-section">
        <div class="error-details-without-lines">
            <div class="error-type-title">
                <span id="error-type-message"> Incorrect format for 'alphabets' </span> in <span
                    id="error-count" class="column-name"> 112 </span> lines
            </div>
            <button id="error1" class="button no-margin" onclick="displayLinesForErrorType(this)">See <span>More</span></button>
        </div>
        <div class="lines-container" style="display: none;" id="line-container1">
            <div class="one-error-line">
                Line 2: Incorrect format of 'alphabets' in Product Id : 12345
            </div>
            <div class="one-error-line">
                Line 22: Incorrect format of 'alphabets' in Product Id : 11111
            </div>
            <div class="one-error-line">
                Line 24: Incorrect format of 'alphabets' in Product Id : 12341
            </div>
        </div>
    </div>`
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