package validation.implementation.valueValidator

class NumberValidator : ValueTypeValidator {
    override fun validate(value: String, pattern: String?): Boolean {
        return value.matches("-?\\d+(\\.\\d+)?".toRegex())
    }
}