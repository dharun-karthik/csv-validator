package validation.implementation.valueValidator

class TextValidator : ValueTypeValidator {

    override fun validate(value: String, pattern: String?): Boolean {
        return value.matches("""^[!-~\s\u00A0]*$""".toRegex())
    }
}
