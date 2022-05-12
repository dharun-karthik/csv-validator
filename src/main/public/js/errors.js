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