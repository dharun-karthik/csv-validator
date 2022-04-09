package valueValidator

import validation.TypeValidation

class Numbers : ValueTypeValidator {
    override fun validateValueType(value: String, typeValidation: TypeValidation): Boolean {
        if (!typeValidation.isNumeric(value)) {
            return false
        }
        return true
    }
}