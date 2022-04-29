package validation.jsonConfig.length

import metaData.template.JsonConfigTemplate

class MaxLengthValidator : LengthValidator() {
    override fun validate(jsonField: JsonConfigTemplate): String? {
        return validateLength(jsonField.maxLength, LengthType.MAX)
    }
}