package validation.jsonConfig.length

import metaData.template.JsonConfigTemplate

class FixedLengthValidator : LengthValidator() {
    override fun validate(jsonField: JsonConfigTemplate): String? {
        return validateLength(jsonField.length, jsonField.fieldName, LengthType.FIXED)
    }
}