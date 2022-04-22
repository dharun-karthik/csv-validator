package validation.implementation

class NullValidation {

    fun validate(value: String): Boolean {
        return value.contentEquals("null", ignoreCase = true)
    }
}
