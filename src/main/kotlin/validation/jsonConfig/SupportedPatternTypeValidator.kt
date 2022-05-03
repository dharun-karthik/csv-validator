package validation.jsonConfig

import metaData.template.JsonConfigTemplate

class SupportedPatternTypeValidator : ConfigValidator {

    private val supportedPatternTypeList = listOf(
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
        "uuuu-MM-dd",
        "HH:mm:ss",
        "HH:ss:mm",
        "ss:HH:mm",
        "HH:ss:mm:SSS",
        "hh:ss:mm a",
        "hh:ss:mma",
        "ahh:ss:mm",
        "hh:ass:mm",
        "hh:ass:mm:SSS",
        "HH:mm:ss dd/MM/uuuu",
        "HH:mm:ss?dd/uuuu/MM",
        "HH:ss:mm:dd:MM:uuuu",
        "ss:HH:mm/uuuu/MM/dd",
        "HH:ss:mm-dd-uuuu-MM",
        "HH:ss:mm:SSS dd MM uuuu",
        "hh:ss:mm a,uuuu-MM-dd",
        "hh:ss:mma+dd/MM/uuuu",
        "ahh:ss:mm () dd/MM/uuuu",
        "dd/MM/uuuu hh:ass:mm",
        "dd/MM/uuuu == hh:ass:mm:SSS",
        "dd MM uuuu hh:ass:mm",
        "uuuu-MM-dd't'HH:mm:ss",
        "uuuu-MM-dd't'HH:mm:ss.SSS'z'",
    )

    override fun validate(jsonField: JsonConfigTemplate): List<String> {
        val genericValidator = GenericValidator()
        if (jsonField.pattern == null) {
            return listOf()
        }
        return genericValidator.validate(jsonField.pattern, supportedPatternTypeList)
    }
}