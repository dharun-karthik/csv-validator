package validation.operation

enum class ValidationType(val errorName: String) {
    NULL_VALIDATION("Empty value found"),
    TYPE_VALIDATION("Type mismatch error"),
    LENGTH_VALIDATION("Length error"),
    RESTRICTED_INPUT_VALIDATION("Value not found"),
    DEPENDENCY_VALIDATION("Dependency error")
}