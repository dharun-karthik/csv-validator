package validation.jsonConfig

import metaData.template.JsonConfigTemplate

class DateTimePatternValidator {
    fun validate(jsonField: JsonConfigTemplate): String? {
        val dateTimeFieldNames = listOf("time", "date", "date-time")
        if (jsonField.type in dateTimeFieldNames && jsonField.pattern == null) {
            return "Type '${jsonField.type}' expects pattern field to be not empty"
        }
        return null
    }
}