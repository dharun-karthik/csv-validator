package validation.operation

import metaData.JsonMetaDataTemplate
import org.json.JSONObject
import validation.implementation.RestrictedInputValidation

class RestrictedInputValidationOperation : ValidationOperation {
    override fun validate(
        metaDataField: JsonMetaDataTemplate, currentFieldValue: String, currentRow: JSONObject?
    ): String? {
        val restrictedInputValidation = RestrictedInputValidation()
        val restrictedInputList = metaDataField.values ?: return null
        if (!restrictedInputValidation.validate(currentFieldValue, restrictedInputList)) {
            return "Value Not Found"
        }
        return null
    }
}