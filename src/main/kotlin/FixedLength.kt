import validation.LengthValidation

class FixedLength : Action{
    override fun performAction(value: String, length: Int?, lengthValidation: LengthValidation): Boolean {
        if (length != null && !lengthValidation.fixedLength(value, length)) {
            return false
        }
        return true
    }
}