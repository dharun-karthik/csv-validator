package lengthValidator

import validation.LengthValidation

class MaxLength : LengthTypeValidator {
    override fun validateLengthType(value: String, length: Int?, lengthValidation: LengthValidation): Boolean {
        if (length != null && !lengthValidation.maxLength(value, length)) {
            return false
        }
        return true
    }
}