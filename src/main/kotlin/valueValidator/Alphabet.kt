package valueValidator

import validation.implementation.TypeValidation

class Alphabet : ValueTypeValidator {
    override fun validateValueType(value: String, typeValidation: TypeValidation): Boolean {
        if (!typeValidation.isAlphabetic(value)) {
            return false
        }
        return true
    }
}