import validation.LengthValidation

class MinLength : Action {
    override fun performAction(value: String, length: Int?, lengthValidation: LengthValidation): Boolean {
        if (length != null && !lengthValidation.minLength(value, length)) {
            return false
        }
        return true
    }
}