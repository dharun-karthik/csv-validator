package validation.jsonConfig

import metaData.template.JsonConfigTemplate

class SupportedDateTypeValidator : ConfigValidator{

    private val supportedDateTypeList = listOf(
        "dd/MM/uuuu",
        "dd/uuuu/MM",
        "MM/dd/uuuu",
        "MM/uuuu/dd",
        "uuuu/dd/MM",
        "uuuu/MM/dd",
        "dd-MM-uuuu",
        "dd-uuuu-MM",
        "MM-dd-uuuu",
        "MM-uuuu-dd",
        "uuuu-dd-MM",
        "uuuu-MM-dd"
    )

    override fun validate(jsonField: JsonConfigTemplate): List<String> {
        val genericValidator = GenericValidator()
        if(jsonField.pattern == null) {
            return listOf()
        }
        return genericValidator.validate(jsonField.pattern, supportedDateTypeList)
    }
}