package validation.operation

import metaData.JsonMetaDataTemplate
import org.json.JSONObject
import validation.implementation.TypeValidation
import valueValidator.AlphaNumeric
import valueValidator.Alphabet
import valueValidator.Numbers
import valueValidator.ValueTypeValidator

class TypeValidationOperation : ValidationOperation {
    private val valueTypeMap: Map<String, ValueTypeValidator> = mapOf(
        "alphanumeric" to AlphaNumeric(), "alphabets" to Alphabet(), "number" to Numbers()
    )

    override fun validate(
        metaDataField: JsonMetaDataTemplate, currentFieldValue: String, currentRow: JSONObject?
    ): Boolean {
        val typeValidation = TypeValidation()
        if (isFieldIsNull(currentFieldValue)) {
            return true
        }

        return checkType(metaDataField, currentFieldValue, typeValidation)
    }

    private fun checkType(
        metaDataField: JsonMetaDataTemplate, currentFieldValue: String, typeValidation: TypeValidation
    ): Boolean {
        val isValid = valueTypeMap[metaDataField.type]!!.validateValueType(currentFieldValue, typeValidation)

        if (!isValid) {
            return false
        }
        return true
    }
}