package validation.operation

import metaData.template.JsonMetaDataTemplate
import org.json.JSONObject
import validation.implementation.valueValidator.*

class TypeValidationOperation : ValidationOperation {
    private val valueTypeMap: Map<String, ValueTypeValidator> = mapOf(
        "alphanumeric" to AlphaNumericValidator(),
        "alphabets" to AlphabetValidator(),
        "number" to NumberValidator(),
        "date" to DateValidator(),
        "time" to TimeValidator(),
        "date-time" to DateTimeValidator(),
        "email" to EmailValidator(),
        "text" to TextValidator()
    )

    override fun validate(
        metaDataField: JsonMetaDataTemplate,
        currentFieldValue: String,
        key: String,
        currentRow: JSONObject?
    ): String? {
        if (isFieldIsNull(currentFieldValue)) {
            return null
        }

        return checkType(metaDataField, key, currentFieldValue)
    }

    private fun checkType(
        metaDataField: JsonMetaDataTemplate, key: String, currentFieldValue: String
    ): String? {
        val isValid = valueTypeMap[metaDataField.type]!!.validate(currentFieldValue, metaDataField.pattern)

        if (!isValid) {
            return "Incorrect format of '${metaDataField.type}' in $key : $currentFieldValue"

        }
        return null
    }
}