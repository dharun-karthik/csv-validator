package validation.jsonConfig

import metaData.template.JsonConfigTemplate

class SupportedDateTypeValidator {

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

    fun validate(jsonConfig: JsonConfigTemplate): Any? {
        val genericValidator = GenericValidator()
        return genericValidator.validate(jsonConfig.type, supportedDateTypeList)
    }
}