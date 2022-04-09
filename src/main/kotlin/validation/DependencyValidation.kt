package validation

class DependencyValidation {
    fun validate(
        currentField: FieldValue,
        dependentField: FieldValue,
        expectedDependentFieldValue: String?,
        expectedCurrentFieldValue: String?
    ): Boolean {
        if (dependentField.value.contentEquals(expectedDependentFieldValue, ignoreCase = true)) {
            if (currentField.value.contentEquals(expectedCurrentFieldValue, ignoreCase = true)) {
                return true
            }
            return false
        }
        return true
    }
}