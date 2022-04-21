package validation.operation

import lengthValidator.*
import lengthValidator.LengthType.*
import metaData.template.JsonMetaDataTemplate
import org.json.JSONObject
import validation.implementation.LengthValidation

class LengthValidationOperation : ValidationOperation {
    private val lengthTypeMap: Map<LengthType, LengthTypeValidator> = mapOf(
        FIXED_LENGTH to FixedLength(),
        MIN_LENGTH to MinLength(),
        MAX_LENGTH to MaxLength()
    )

    override fun validate(
        metaDataField: JsonMetaDataTemplate, currentFieldValue: String, currentRow: JSONObject?
    ): String? {
        val lengthValidation = LengthValidation()
        if (isFieldIsNull(currentFieldValue)) {
            return null
        }
        return lengthCheck(currentFieldValue, metaDataField, lengthValidation)
    }

    private fun lengthCheck(
        currentFieldValue: String, metaDataField: JsonMetaDataTemplate, lengthValidation: LengthValidation
    ): String? {
        val isValid = (checkFixedLength(currentFieldValue, metaDataField, lengthValidation) && checkMinLength(
            currentFieldValue, metaDataField, lengthValidation
        ) && checkMaxLength(currentFieldValue, metaDataField, lengthValidation))

        if (!isValid) {
            return "Length Error in"
        }
        return null
    }

    private fun checkMaxLength(
        currentFieldValue: String, metaDataField: JsonMetaDataTemplate, lengthValidation: LengthValidation
    ) = lengthTypeMap[MAX_LENGTH]!!.validateLengthType(
        currentFieldValue, metaDataField.maxLength?.toInt(), lengthValidation
    )

    private fun checkMinLength(
        currentFieldValue: String, metaDataField: JsonMetaDataTemplate, lengthValidation: LengthValidation
    ) = lengthTypeMap[MIN_LENGTH]!!.validateLengthType(
        currentFieldValue, metaDataField.minLength?.toInt(), lengthValidation
    )

    private fun checkFixedLength(
        currentFieldValue: String, metaDataField: JsonMetaDataTemplate, lengthValidation: LengthValidation
    ) = lengthTypeMap[FIXED_LENGTH]!!.validateLengthType(
        currentFieldValue, metaDataField.length?.toInt(), lengthValidation
    )
}