package validation

class TypeValidation {

    fun isNumeric(value: String): Boolean {
        return value.matches("-?\\d+(\\.\\d+)?".toRegex())
    }

    fun isAlphabetic(value: String): Boolean {
        return value.all { it.isLetter() }
    }

    fun isAlphaNumeric(value: String): Boolean {
        return value.all { it.isLetterOrDigit() }
    }


}