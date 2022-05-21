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
            if (element[key] === 0) continue
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

function disableLengthInput(index) {
    document.getElementById(`row${index}`).style.display = "none"
    document.getElementById(`field-value${index}`).style.display = "none"
}

function insertPatternInRespectiveField(index, element) {
    let typeOfPattern = element['type']
    console.log(`insertPattern -> ${typeOfPattern} -->`)
    document.getElementById(`${typeOfPattern}-format-div${index}`).style.display = 'block'
    document.getElementById(`${typeOfPattern}-format${index}`).value = element[key];
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