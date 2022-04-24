package validation.implementation.lengthValidator

import metaData.template.JsonMetaDataTemplate

class MaxLength : LengthTypeValidator {
    override fun validateLengthType(value: String, length: Int?): Boolean {
        if (length != null) {
            return value.length <= length
        }
        return true
    }

    override fun getAppropriateLengthRestriction(jsonMetaData: JsonMetaDataTemplate): Int? {
        return jsonMetaData.maxLength?.toInt()
    }
}