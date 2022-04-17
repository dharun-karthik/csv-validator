package validation.implementation.valueValidator

interface ValueTypeValidator {
    fun validate(value: String, pattern: String? = null): Boolean
}