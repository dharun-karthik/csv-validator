package valueValidator

import validation.TypeValidation

class Alphabet : ValueTypeValidator {
    override fun validateValueType(value: String, type: String, typeValidation: TypeValidation): Boolean {
        if (!typeValidation.isAlphabetic(value)) {
            return false
        }
        return true
    }
}