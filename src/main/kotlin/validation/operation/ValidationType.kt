package validation.operation

enum class ValidationType(val validationErrorName: String) {
    TYPE_VALIDATION("Type"),
    LENGTH_VALIDATION("Length"),
    RESTRICTED_INPUT_VALIDATION("Value Not Found"),
    DEPENDENCY_VALIDATION("Dependency")
}