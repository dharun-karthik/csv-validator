package validation.implementation.valueValidator

class AlphaNumericValidator : ValueTypeValidator {
    override fun validate(value: String, pattern: String?): Boolean {
        return value.matches("""^[A-Za-z0-9]*$""".toRegex())
    }
}