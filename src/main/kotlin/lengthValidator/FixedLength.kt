package lengthValidator

import metaData.template.JsonMetaDataTemplate

class FixedLength : LengthTypeValidator {
    override fun validateLengthType(value: String, length: Int?): Boolean {
        if (length != null) {
            return value.length == length
        }
        return true
    }

    override fun getAppropriateLengthRestriction(jsonMetaData: JsonMetaDataTemplate): Int? {
        return jsonMetaData.length?.toInt()
    }
}