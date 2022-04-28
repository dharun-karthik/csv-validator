package validation.jsonConfig

import metaData.template.JsonConfigTemplate

class DateTimePatternValidator : ConfigValidator {
    override fun validate(jsonField: JsonConfigTemplate): List<String> {
        val dateTimeFieldNames = listOf("time", "date", "date-time")
        if (jsonField.type in dateTimeFieldNames && jsonField.pattern == null) {
            return listOf("Type '${jsonField.type}' expects pattern field to be not empty")
        }
        return listOf()
    }
}