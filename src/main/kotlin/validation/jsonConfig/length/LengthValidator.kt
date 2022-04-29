package validation.jsonConfig.length

import metaData.template.JsonConfigTemplate

abstract class LengthValidator {

    abstract fun validate(jsonField: JsonConfigTemplate): String?

    protected fun validateLength(length: String?, lengthType: LengthType): String? {
        if (lengthCheck(length)) {
            return generateLengthErrorMessage(lengthType.value)
        }
        return null
    }

    private fun lengthCheck(length: String?): Boolean {
        return length != null && length.toInt() <= 0
    }

    private fun generateLengthErrorMessage(type: String): String {
        return "$type length should be greater than 0"
    }
}