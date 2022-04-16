package validation.validationOperation

import lengthValidator.*
import metaData.JsonMetaDataTemplate
import org.json.JSONObject
import validation.LengthValidation

class LengthValidationOperation : ValidationOperation {
    private val lengthTypeMap: Map<LengthType, LengthTypeValidator> = mapOf(
        LengthType.FIXED_LENGTH to FixedLength(),
        LengthType.MIN_LENGTH to MinLength(),
        LengthType.MAX_LENGTH to MaxLength()
    )

    override fun validate(
        metaDataField: JsonMetaDataTemplate, currentFieldValue: String, currentRow: JSONObject?
    ): Boolean {
        val lengthValidation = LengthValidation()
        if (isFieldIsNull(currentFieldValue)) {
            return true
        }
        return lengthCheck(currentFieldValue, metaDataField, lengthValidation)
    }

    private fun lengthCheck(
        currentFieldValue: String, metaDataField: JsonMetaDataTemplate, lengthValidation: LengthValidation
    ): Boolean {
        val isValid = (checkFixedLength(currentFieldValue, metaDataField, lengthValidation) && checkMinLength(
            currentFieldValue, metaDataField, lengthValidation
        ) && checkMaxLength(currentFieldValue, metaDataField, lengthValidation))

        if (!isValid) {
            return false
        }
        return true
    }

    private fun checkMaxLength(
        currentFieldValue: String, metaDataField: JsonMetaDataTemplate, lengthValidation: LengthValidation
    ) = lengthTypeMap[LengthType.MAX_LENGTH]!!.validateLengthType(
        currentFieldValue, metaDataField.maxLength?.toInt(), lengthValidation
    )

    private fun checkMinLength(
        currentFieldValue: String, metaDataField: JsonMetaDataTemplate, lengthValidation: LengthValidation
    ) = lengthTypeMap[LengthType.MIN_LENGTH]!!.validateLengthType(
        currentFieldValue, metaDataField.minLength?.toInt(), lengthValidation
    )

    private fun checkFixedLength(
        currentFieldValue: String, metaDataField: JsonMetaDataTemplate, lengthValidation: LengthValidation
    ) = lengthTypeMap[LengthType.FIXED_LENGTH]!!.validateLengthType(
        currentFieldValue, metaDataField.length?.toInt(), lengthValidation
    )
}