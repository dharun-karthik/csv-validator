package validation.jsonConfig.length

import metaData.template.JsonConfigTemplate

class MinLengthValidator : LengthValidator() {
    override fun validate(jsonField: JsonConfigTemplate): String? {
        return validateLength(jsonField.minLength, jsonField.fieldName, LengthType.MIN)
    }
}