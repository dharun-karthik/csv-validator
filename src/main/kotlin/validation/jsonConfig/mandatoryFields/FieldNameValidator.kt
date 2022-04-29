package validation.jsonConfig.mandatoryFields

import metaData.template.JsonConfigTemplate

class FieldNameValidator : FieldValidator() {

    override fun validate(jsonField: JsonConfigTemplate): String? {
        return checkField("fieldName", jsonField.fieldName)
    }
}