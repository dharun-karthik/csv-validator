package validation.implementation.valueValidator

class Alphabet : ValueTypeValidator {
    override fun validate(value: String, pattern: String?): Boolean {
        return value.matches("""^[A-Za-z\s]*$""".toRegex())
    }
}