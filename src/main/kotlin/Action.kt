import validation.LengthValidation

interface Action {
    fun performAction(value: String, length: Int?, lengthValidation: LengthValidation): Boolean
}