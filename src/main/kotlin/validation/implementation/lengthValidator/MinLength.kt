package validation.implementation.lengthValidator

import metaData.template.JsonMetaDataTemplate

class MinLength : LengthTypeValidator {
    override fun validateLengthType(value: String, length: Int?): Boolean {
        if (length != null) {
            return value.length >= length
        }
        return true
    }

    override fun getAppropriateLengthRestriction(jsonMetaData: JsonMetaDataTemplate): Int? {
        return jsonMetaData.minLength?.toInt()
    }
}