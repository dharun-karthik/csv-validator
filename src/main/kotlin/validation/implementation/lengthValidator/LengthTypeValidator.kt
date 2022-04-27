package validation.implementation.lengthValidator

import metaData.template.JsonConfigTemplate

interface LengthTypeValidator {
    fun validateLengthType(value: String, length: Int?): Boolean

    fun getAppropriateLengthRestriction(jsonMetaData: JsonConfigTemplate): Int?
}