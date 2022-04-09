package valueValidator

import validation.TypeValidation

interface ValueTypeValidator {
    fun validateValueType(value: String, type: String, typeValidation: TypeValidation) : Boolean
}