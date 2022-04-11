class MandatoryInputValidation{
    validateInputsForDependency(dependsOnColumn, expectedDependentFieldValue, expectedCurrentFieldValue) {
        if (dependsOnColumn != "" && expectedDependentFieldValue == "" || expectedCurrentFieldValue == "") {
            alert("Please enter values for Expected Dependent Field and Expected Current Field")
            return false
        }
        return true
    }

    validateInputsForFields(field, type) {
        console.log(field + type)
        if (field != "" && type == "") {
            alert("Please enter type for the field !")
            return false
        }
        return true
    }
}