package validation.operation

import metaData.template.JsonConfigTemplate
import org.json.JSONObject
import validation.implementation.NullValidation

class NullValidationOperation : ValidationOperation {
    override fun validate(
        metaDataField: JsonConfigTemplate,
        currentFieldValue: String,
        key: String,
        currentRow: JSONObject?
    ): String? {
        val nullValidation = NullValidation()

        if (isNullValueAllowed(metaDataField) && nullValidation.validate(currentFieldValue)) {
            return "Empty Value not allowed in $key"
        }
        return null
    }

    private fun isNullValueAllowed(metaDataField: JsonConfigTemplate) =
        metaDataField.isNullAllowed == "No"
}