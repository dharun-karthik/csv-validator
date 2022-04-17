package validation.implementation.valueValidator

class AlphaNumeric : ValueTypeValidator {
    override fun validate(value: String, pattern: String?): Boolean {
        return value.matches("""^[A-Za-z0-9\s]*$""".toRegex())
    }
}