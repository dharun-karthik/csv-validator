package validation.operation

import metaData.template.JsonConfigTemplate
import org.json.JSONObject

interface ValidationOperation {
    fun validate(
        metaDataField: JsonConfigTemplate,
        currentFieldValue: String,
        key: String,
        currentRow: JSONObject? = null,
    ): String?

    fun isFieldIsNull(value: String?): Boolean {
        return value.contentEquals("null", ignoreCase = true)
    }
}