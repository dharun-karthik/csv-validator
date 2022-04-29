package validation.jsonConfig.mandatoryFields

import metaData.template.JsonConfigTemplate

class FieldTypeValidator : FieldValidator() {
    override fun validate(jsonField: JsonConfigTemplate): String? {
        return checkField("type", jsonField.type)
    }
}