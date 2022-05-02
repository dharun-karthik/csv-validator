package validation.operation

import metaData.template.JsonConfigTemplate
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
        metaDataField: JsonConfigTemplate,
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
        metaDataField: JsonConfigTemplate, key: String, currentFieldValue: String
    ): String? {
        val isValid = valueTypeMap[metaDataField.type]!!.validate(currentFieldValue, metaDataField.pattern)

        if (!isValid) {
            if (metaDataField.type == "date" || metaDataField.type == "time" || metaDataField.type == "date-time") {
                val expectedPattern = metaDataField.pattern!!.replace('u', 'y')
                return "Incorrect format of '${metaDataField.type}' in $key : $currentFieldValue, expected format : ${expectedPattern}"
            }
            return "Incorrect format of '${metaDataField.type}' in $key : $currentFieldValue"
        }
        return null
    }
}