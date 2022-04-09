import validation.LengthValidation

interface LengthTypeValidator {
    fun validateLengthType(value: String, length: Int?, lengthValidation: LengthValidation): Boolean
}