package valueValidator

import validation.TypeValidation

interface ValueTypeValidator {
    fun validateValueType(value: String, typeValidation: TypeValidation) : Boolean
}