package validation.operation

import metaData.template.JsonMetaDataTemplate
import org.json.JSONObject
import validation.implementation.lengthValidator.*
import validation.implementation.lengthValidator.LengthType.*

class LengthValidationOperation : ValidationOperation {
    private val lengthTypeMap: Map<LengthType, LengthTypeValidator> = mapOf(
        FIXED_LENGTH to FixedLength(),
        MIN_LENGTH to MinLength(),
        MAX_LENGTH to MaxLength()
    )

    override fun validate(
        metaDataField: JsonMetaDataTemplate,
        currentFieldValue: String,
        key: String,
        currentRow: JSONObject?
    ): String? {
        if (isFieldIsNull(currentFieldValue)) {
            return null
        }
        return lengthCheck(currentFieldValue, key, metaDataField)
    }

    private fun lengthCheck(
        currentFieldValue: String, key: String, metaDataField: JsonMetaDataTemplate
    ): String? {
        lengthTypeMap.forEach { entry ->
            val lengthTypeValidation = entry.value
            val field = lengthTypeValidation.getAppropriateLengthRestriction(metaDataField)
            if (!lengthTypeValidation.validateLengthType(currentFieldValue, field)) {
                val lengthType = entry.key
                return generateErrorMessage(lengthType, field, key, currentFieldValue)
            }
        }
        return null
    }

    private fun generateErrorMessage(
        lengthType: LengthType,
        field: Int?,
        key: String,
        currentFieldValue: String
    ) = "${lengthType.errorMessage} $field in $key : $currentFieldValue"
}