package validation.implementation.lengthValidator

import metaData.template.JsonConfigTemplate

class MaxLength : LengthTypeValidator {
    override fun validateLengthType(value: String, length: Int?): Boolean {
        if (length != null) {
            return value.length <= length
        }
        return true
    }

    override fun getAppropriateLengthRestriction(jsonMetaData: JsonConfigTemplate): Int? {
        return jsonMetaData.maxLength?.toInt()
    }
}