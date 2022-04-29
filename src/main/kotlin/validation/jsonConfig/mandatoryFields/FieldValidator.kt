package validation.jsonConfig.mandatoryFields

import metaData.template.JsonConfigTemplate

abstract class FieldValidator {

    abstract fun validate(jsonField: JsonConfigTemplate): String?

    protected fun checkField(fieldName: String, fieldValue: String?): String? {
        if (fieldValue == null) {
            return "Field '$fieldName' should be provided"
        }
        return null
    }
}