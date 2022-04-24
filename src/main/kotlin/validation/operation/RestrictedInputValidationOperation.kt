package validation.operation

import metaData.template.JsonMetaDataTemplate
import org.json.JSONObject
import validation.implementation.RestrictedInputValidation

class RestrictedInputValidationOperation : ValidationOperation {
    override fun validate(
        metaDataField: JsonMetaDataTemplate,
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
        metaDataField: JsonMetaDataTemplate,
        currentFieldValue: String,
        key: String
    ): String? {
        val restrictedInputValidation = RestrictedInputValidation()
        val restrictedInputList = metaDataField.values ?: return null
        if (!restrictedInputValidation.validate(currentFieldValue, restrictedInputList)) {
            return "Value Not Found $key : $currentFieldValue"
        }
        return null
    }
}