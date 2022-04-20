package validation.operation

import metaData.JsonMetaDataTemplate
import org.json.JSONObject

interface ValidationOperation {
    fun validate(
        metaDataField: JsonMetaDataTemplate,
        currentFieldValue: String,
        currentRow: JSONObject? = null,
    ): String?

    fun isFieldIsNull(value: String?): Boolean {
        return value.contentEquals("null", ignoreCase = true)
    }
}