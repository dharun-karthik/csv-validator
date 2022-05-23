package validation.jsonConfig

class GenericValidator {
    fun validate(fieldValue: String, supportedFieldValue: List<String>): List<String> {
        val error = mutableListOf<String>()
        if (!supportedFieldValue.contains(fieldValue)) {
            error.add("${fieldValue} is not supported")
        }
        return error
    }
}