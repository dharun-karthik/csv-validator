package validation.implementation.lengthValidator

enum class LengthType(val errorMessage: String) {
    FIXED_LENGTH("Value length should be equal to"),
    MIN_LENGTH("Value length should be greater than or equal to"),
    MAX_LENGTH("Value length should be lesser than or equal to")
}