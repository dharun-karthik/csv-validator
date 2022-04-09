package lengthValidator

import validation.LengthValidation

class MinLength : LengthTypeValidator {
    override fun validateLengthType(value: String, length: Int?, lengthValidation: LengthValidation): Boolean {
        if (length != null && !lengthValidation.minLength(value, length)) {
            return false
        }
        return true
    }
}