package validation.operation

enum class ValidationType(val validationErrorName: String) {
    TYPE_VALIDATION("Type Error"),
    LENGTH_VALIDATION("Length Error"),
    RESTRICTED_INPUT_VALIDATION("Value Not Found Error"),
    DEPENDENCY_VALIDATION("Dependency Error")
}