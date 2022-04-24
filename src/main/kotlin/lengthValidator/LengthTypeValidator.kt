package lengthValidator

import metaData.template.JsonMetaDataTemplate

interface LengthTypeValidator {
    fun validateLengthType(value: String, length: Int?): Boolean

    fun getAppropriateLengthRestriction(jsonMetaData: JsonMetaDataTemplate): Int?
}