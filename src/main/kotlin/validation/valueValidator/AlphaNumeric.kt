package validation.valueValidator

import validation.implementation.TypeValidation

class AlphaNumeric : ValueTypeValidator {
    override fun validateValueType(value: String, typeValidation: TypeValidation): Boolean {
        if (!typeValidation.isAlphaNumeric(value)) {
            return false
        }
        return true
    }
}