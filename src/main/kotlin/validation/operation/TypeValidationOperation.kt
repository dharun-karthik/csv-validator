package validation.operation

import metaData.JsonMetaDataTemplate
import org.json.JSONObject
import validation.implementation.valueValidator.*

class TypeValidationOperation : ValidationOperation {
    private val valueTypeMap: Map<String, ValueTypeValidator> = mapOf(
        "alphanumeric" to AlphaNumeric(),
        "alphabets" to Alphabet(),
        "number" to Numbers(),
        "date" to DateValidator(),
        "time" to TimeValidator(),
        "datetime" to DateTimeValidator()
    )

    override fun validate(
        metaDataField: JsonMetaDataTemplate, currentFieldValue: String, currentRow: JSONObject?
    ): Boolean {
        if (isFieldIsNull(currentFieldValue)) {
            return true
        }

        return checkType(metaDataField, currentFieldValue)
    }

    private fun checkType(
        metaDataField: JsonMetaDataTemplate, currentFieldValue: String
    ): Boolean {
        val isValid = valueTypeMap[metaDataField.type]!!.validate(currentFieldValue, metaDataField.pattern)

        if (!isValid) {
            return false
        }
        return true
    }
}