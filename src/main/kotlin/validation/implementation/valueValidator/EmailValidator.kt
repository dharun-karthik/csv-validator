package validation.implementation.valueValidator

class EmailValidator : ValueTypeValidator {
    override fun validate(value: String, pattern: String?): Boolean {
        return value.matches("""^(.+)@(.+)${'$'}""".toRegex())
    }

}
