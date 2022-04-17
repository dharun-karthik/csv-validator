package validation.implementation.valueValidator

class Numbers : ValueTypeValidator {
    override fun validate(value: String, pattern: String?): Boolean {
        return value.matches("-?\\d+(\\.\\d+)?".toRegex())
    }
}