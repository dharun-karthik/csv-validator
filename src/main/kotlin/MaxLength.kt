import validation.LengthValidation

class MaxLength : Action {
    override fun performAction(value: String, length: Int?, lengthValidation: LengthValidation): Boolean {
        if (length != null && !lengthValidation.maxLength(value, length)) {
                return false
        }
        return true
    }
}