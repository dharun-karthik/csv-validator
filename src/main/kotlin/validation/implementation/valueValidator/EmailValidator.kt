package validation.implementation.valueValidator

class EmailValidator : ValueTypeValidator {
    override fun validate(value: String, pattern: String?): Boolean {
        return value.matches("""^[a-zA-Z0-9_!#${'$'}%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+${'$'}""".toRegex())
    }

}
