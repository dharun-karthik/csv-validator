let errors = []
let newErrors = [{
    "column-name": "product-id",
    "total-error-count": "230344",
    "details": [{
        "error-type": "Length Error",
        "error-count": "00056",
        "lines": {
            "1": "Value length should be equal to 6 in Product Id: 12345",
            "2": "Value length should be equal to 6 in Product Id: 11111"
        }
    },
        {
            "error-type": "Value Error",
            "error-count": "11134",
            "lines": {
                "1": "Value length should be equal to 6 in Product Id: 12345",
                "2": "error summary"
            }
        }
    ]
},
    {
        "column-name": "product-description",
        "total-error-count": "230321",
        "details": [{
            "error-type": "Length Error",
            "error-count": "12346",
            "lines": {
                "1": "Value length should be equal to 6 in Product Id: 12345",
                "2": "error summary"
            }
        },
            {
                "error-type": "Value Error",
                "error-count": "12344",
                "lines": {
                    "1": "Value length should be equal to 6 in Product Id: 12345",
                    "2": "error summary"
                }
            }
        ]
    }
]

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


// async function displayErrors() {
//     clearPreviousErrors()
//     for (key in errors) {
//         let errorListContainer = document.createElement('div')
//         errorListContainer.className = 'error-list'

//         displayErrorsForAllLines(errorListContainer);
//         document.getElementById('display-errors').appendChild(errorListContainer)
//     }
// }

// function displayErrorsForAllLines(errorListContainer) {
//     for (lineNumber in errors[key]) {
//         errorsInLineNumber = errors[key][lineNumber];
//         if (lineNumber == '0') {
//             displayColumnErrors(errorsInLineNumber)
//             return
//         }
//         let lineNo = document.createElement('div');
//         lineNo.className = 'line-number';
//         lineNo.innerHTML = `Line No:
//             <span>${lineNumber}</span>`;

//         errorListContainer.appendChild(lineNo);

//         let allErrorsInOneLine = document.createElement('div');
//         allErrorsInOneLine.className = 'errors';

//         displayOneTypeOfError(allErrorsInOneLine);
//         errorListContainer.appendChild(allErrorsInOneLine);
//     }
// }

// function displayOneTypeOfError(allErrorsInOneLine) {
//     for (errorIndex in errorsInLineNumber) {
//         let errorField = errorsInLineNumber[errorIndex];
//         let errorDiv = document.createElement('div');
//         errorDiv.className = 'error';

//         errorDiv.innerText = `${errorField}`;

//         allErrorsInOneLine.appendChild(errorDiv);
//     }
// }

// function displayColumnErrors(incorrectColumns) {
//     let columnNameContainer = document.createElement('div')
//     columnNameContainer.className = 'all-column-errors'
//     incorrectColumns.forEach(incorrectColumn => {
//         let oneColumnErrorDiv = document.createElement('div')
//         oneColumnErrorDiv.className = 'column-error'
//         oneColumnErrorDiv.innerText = `Column ${incorrectColumn} not found in CSV`;

//         columnNameContainer.appendChild(oneColumnErrorDiv)
//     })
//     document.getElementById('display-errors').appendChild(columnNameContainer)
// }

// function clearPreviousErrors() {
//     document.getElementById('display-errors').innerText = "";
// }

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

function toggleFirstLine(target) {
    let index = extractIndexFromId(target.id)
    let elementToToggle = document.getElementById(`error-details${index}`);
    if (elementToToggle.style.display === 'block') {
        elementToToggle.style.display = 'none'
        document.querySelector(`#${target.id} span`).innerText = 'More'
        return
    }
    elementToToggle.style.display = 'block'
    document.querySelector(`#${target.id} span`).innerText = 'Less'
}

function toggleSecondLine(target) {
    let elementToToggle = target.parentNode.parentNode.children[1];
    let targetNode = target.parentNode.parentNode.children[0].children[1]
    if (elementToToggle.style.display === 'none') {
        elementToToggle.style.display = 'block'
        targetNode.innerText = '^'
        return
    }
    elementToToggle.style.display = 'none'
    targetNode.innerText = 'v'
}

function showErrorSummary() {
    for (let key in newErrors) {
        displayOneErrorColumn(key)
    }
}

function displayOneErrorColumn(key) {
    let columnErrors = newErrors[key]
    let columnName = columnErrors['column-name']
    let errorCount = columnErrors['total-error-count']

    let columnElement = document.createElement('div')
    columnElement.className = 'error-summary'
    let innerColumnElement = document.createElement('div')
    innerColumnElement.className = 'column-heading'

    innerColumnElement.innerHTML = `
    <div class="first-line-of-error">
        <div class="headingline">
            Column <span class="column-name">${columnName}</span> contains <span
                class="column-name">${errorCount}</span>
            errors
        </div>
        <button id="error${key}" class="button no-margin" onclick="toggleFirstLine(this)"><span>More</span> details</button>
    </div>`

    let countOfErrors = 0
    for (let typeErrorKey in columnErrors['details']) {
        element = displayErrorsInColumn(columnErrors['details'], typeErrorKey, countOfErrors)
        countOfErrors++
    }
    innerColumnElement.appendChild(element)
    columnElement.appendChild(innerColumnElement)
    document.getElementById('display-errors').appendChild(columnElement)
}

function displayErrorsInColumn(columnErrors, typeErrorKey, key) {
    let typeOfError = columnErrors[typeErrorKey]
    let type = typeOfError['error-type']
    let count = typeOfError['error-count']
    let linesOfErrors = typeOfError['lines']

    let errorTypeElement = document.createElement('div')
    errorTypeElement.className = 'first-line-of-error'
    errorTypeElement.innerHTML = `
    <div class="error-message">
        <span id="error-type-message"> ${type} </span> in <span id="error-count"
            class="column-name"> ${count} </span> lines
    </div>
    <button id="show-more-lines${key}" class="button no-margin no-padding" onclick="toggleSecondLine(this)">v</button>`

    let allLinesElement = document.createElement('div')
    for (let line in linesOfErrors) {
        let element = displayAllLines(linesOfErrors, line)
        allLinesElement.appendChild(element)
    }
    allLinesElement.style.display = 'none'
    errorTypeElement.appendChild(allLinesElement)
    return errorTypeElement
}

function displayAllLines(linesOfErrors, line) {
    let errorLine = linesOfErrors[line]
    let linesElement = document.createElement('div')
    linesElement.className = 'error-lines'

    linesElement.innerHTML = `Line No <span>${line}</span> : <span> ${errorLine} </span>`
    return linesElement
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