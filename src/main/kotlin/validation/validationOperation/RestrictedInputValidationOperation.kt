package validation.validationOperation

import metaData.JsonMetaDataTemplate
import org.json.JSONObject
import validation.RestrictedInputValidation

class RestrictedInputValidationOperation : ValidationOperation {
    override fun validate(
        metaDataField: JsonMetaDataTemplate,
        currentFieldValue: String,
        currentRow: JSONObject?
    ): Boolean {
        val restrictedInputValidation = RestrictedInputValidation()
        val restrictedInputList = metaDataField.values ?: return true
        return restrictedInputValidation.validate(currentFieldValue, restrictedInputList)
    }
}