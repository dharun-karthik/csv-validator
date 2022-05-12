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
