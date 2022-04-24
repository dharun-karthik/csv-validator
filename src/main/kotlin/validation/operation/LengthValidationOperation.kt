package validation.operation

import lengthValidator.*
import lengthValidator.LengthType.*
import metaData.template.JsonMetaDataTemplate
import org.json.JSONObject

class LengthValidationOperation : ValidationOperation {
    private val lengthTypeMap: Map<LengthType, LengthTypeValidator> = mapOf(
        FIXED_LENGTH to FixedLength(),
        MIN_LENGTH to MinLength(),
        MAX_LENGTH to MaxLength()
    )

    override fun validate(
        metaDataField: JsonMetaDataTemplate, currentFieldValue: String, currentRow: JSONObject?
    ): String? {
        if (isFieldIsNull(currentFieldValue)) {
            return null
        }
        return lengthCheck(currentFieldValue, metaDataField)
    }

    private fun lengthCheck(
        currentFieldValue: String, metaDataField: JsonMetaDataTemplate
    ): String? {
        lengthTypeMap.forEach { entry ->
            val lengthTypeValidation = entry.value
            val field = lengthTypeValidation.getAppropriateLengthRestriction(metaDataField)
            if (lengthTypeValidation.validateLengthType(currentFieldValue, field)) {
                return "Length Error in"
            }
        }
        return null
    }
}