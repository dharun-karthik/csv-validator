package validation

class TypeValidation {

    fun isNumeric(value: String): Boolean {
        return value.matches("-?\\d+(\\.\\d+)?".toRegex())
    }

    fun isAlphabetic(value: String): Boolean {
        return value.matches("""^[A-Za-z\s]*$""".toRegex())
    }

    fun isAlphaNumeric(value: String): Boolean {
        return value.matches("""^[A-Za-z0-9\s]*$""".toRegex())
    }

    fun isDecimal(value: String): Boolean {
        return value.matches("""^[+-]?(\d+\.?\d*|\.\d+)${'$'}""".toRegex())
    }
}