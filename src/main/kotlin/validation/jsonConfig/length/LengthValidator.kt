package validation.jsonConfig.length

import metaData.template.JsonConfigTemplate

abstract class LengthValidator {

    abstract fun validate(jsonField: JsonConfigTemplate): String?

    fun validateLength(length: String?, fieldName: String, lengthType: LengthType): String? {
        if (lengthCheck(length)) {
            return generateLengthErrorMessage(fieldName, lengthType.value)
        }
        return null
    }

    private fun lengthCheck(length: String?): Boolean {
        return length != null && length.toInt() <= 0
    }

    private fun generateLengthErrorMessage(fieldName: String, type: String): String {
        return "$type length in $fieldName should be greater than 0"
    }
}