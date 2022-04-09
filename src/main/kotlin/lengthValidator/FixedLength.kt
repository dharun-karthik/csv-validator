package lengthValidator

import validation.LengthValidation

class FixedLength : LengthTypeValidator {
    override fun validateLengthType(value: String, length: Int?, lengthValidation: LengthValidation): Boolean {
        if (length != null && !lengthValidation.fixedLength(value, length)) {
            return false
        }
        return true
    }
}