function validateInputsForDependency(dependsOnColumn, expectedDependentFieldValue, expectedCurrentFieldValue) {
    if (dependsOnColumn == "" || expectedDependentFieldValue == "" || expectedCurrentFieldValue == "") {
        return false
    }
    return true
}

function validateInputsForFields(field, type) {
    console.log(field + type)
    if (field == "" || type == "") {
        return false
    }
    return true
}