package validation.operation

import metaData.template.JsonMetaDataTemplate
import org.json.JSONObject
import validation.implementation.NullValidation

class NullValidationOperation : ValidationOperation {
    override fun validate(
        metaDataField: JsonMetaDataTemplate,
        currentFieldValue: String,
        currentRow: JSONObject?
    ): String? {
        val nullValidation = NullValidation()

        if (isNullValueAllowed(metaDataField) && nullValidation.validate(currentFieldValue)) {
            return "Empty Value not allowed in"
        }
        return null
    }

    private fun isNullValueAllowed(metaDataField: JsonMetaDataTemplate) =
        metaDataField.isNullAllowed == "No"
}