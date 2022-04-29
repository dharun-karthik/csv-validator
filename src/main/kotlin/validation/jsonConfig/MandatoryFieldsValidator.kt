package validation.jsonConfig

import metaData.template.JsonConfigTemplate
import validation.jsonConfig.mandatoryFields.FieldNameValidator
import validation.jsonConfig.mandatoryFields.FieldTypeValidator

class MandatoryFieldsValidator : ConfigValidator {
    private val fieldsValidatorList = listOf(
        FieldNameValidator(),
        FieldTypeValidator(),
    )

    override fun validate(jsonField: JsonConfigTemplate): List<String> {
        val errorList = mutableListOf<String>()
        for (lengthValidator in fieldsValidatorList) {
            val errorMessage = lengthValidator.validate(jsonField)
            if (errorMessage != null) {
                errorList.add(errorMessage)
            }
        }
        return errorList
    }

}