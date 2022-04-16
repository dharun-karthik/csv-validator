package valueValidator

import validation.implementation.TypeValidation

interface ValueTypeValidator {
    fun validateValueType(value: String, typeValidation: TypeValidation): Boolean
}