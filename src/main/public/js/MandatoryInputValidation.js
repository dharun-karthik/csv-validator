export default class MandatoryInputValidation {
    validateInputsForDependency(dependsOnColumn, expectedDependentFieldValue, expectedCurrentFieldValue) {
        if (dependsOnColumn == "" || expectedDependentFieldValue == "" || expectedCurrentFieldValue == "") {
            return false
        }
        return true
    }

    validateInputsForFields(field, type) {
        console.log(field + type)
        if (field == "" || type == "") {
            return false
        }
        return true
    }
}