package validation.implementation.valueValidator

class AlphabetValidator : ValueTypeValidator {
    override fun validate(value: String, pattern: String?): Boolean {
        return value.matches("""^[A-Za-z]*$""".toRegex())
    }
}