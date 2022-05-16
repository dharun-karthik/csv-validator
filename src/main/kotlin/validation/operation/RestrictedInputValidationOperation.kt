package validation.operation

import metaData.template.JsonConfigTemplate
import org.json.JSONObject
import validation.implementation.RestrictedInputValidation

class RestrictedInputValidationOperation : ValidationOperation {
    override fun validate(
        metaDataField: JsonConfigTemplate,
        currentFieldValue: String,
        key: String,
        currentRow: JSONObject?
    ): String? {
        if (isFieldIsNull(currentFieldValue)) {
            return null
        }
        return checkValueInGivenValues(metaDataField, currentFieldValue, key)
    }

    private fun checkValueInGivenValues(
        metaDataField: JsonConfigTemplate,
        currentFieldValue: String,
        key: String
    ): String? {
        val restrictedInputValidation = RestrictedInputValidation()
        val restrictedInputList = metaDataField.values ?: return null
        if (!restrictedInputValidation.validate(currentFieldValue, restrictedInputList)) {
            return "Value not found $key : $currentFieldValue"
        }
        return null
    }
}