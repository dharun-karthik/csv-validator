package validation.jsonConfig

import metaData.template.JsonConfigTemplate
import validation.jsonConfig.length.FixedLengthValidator
import validation.jsonConfig.length.LengthType.*
import validation.jsonConfig.length.MaxLengthValidator
import validation.jsonConfig.length.MinLengthValidator

class PositiveLengthValidator : ConfigValidator {
    private val lengthValidatorList = listOf(
        MinLengthValidator(),
        MaxLengthValidator(),
        FixedLengthValidator()
    )

    override fun validate(jsonField: JsonConfigTemplate): List<String> {
        val errorList = mutableListOf<String>()
        for (lengthValidator in lengthValidatorList) {
            val errorMessage = lengthValidator.validate(jsonField)
            if (errorMessage != null) {
                errorList.add(errorMessage)
            }
        }
        return errorList
    }

}