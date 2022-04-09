package valueValidator

import validation.TypeValidation

class AlphaNumeric : ValueTypeValidator {
    override fun validateValueType(value: String, typeValidation: TypeValidation): Boolean {
        if (!typeValidation.isAlphaNumeric(value)) {
            return false
        }
        return true
    }
}