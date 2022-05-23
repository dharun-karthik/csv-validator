package validation.jsonConfig

import metaData.template.JsonConfigTemplate

class SupportedFieldTypeValidator : ConfigValidator {

    private val supportedFieldTypeList =
        listOf("number", "alphanumeric", "alphabets", "date", "time", "date-time", "email", "text")

    override fun validate(jsonField: JsonConfigTemplate): List<String> {
        val genericValidator = GenericValidator()
        return genericValidator.validate(jsonField.type, supportedFieldTypeList)
    }

}