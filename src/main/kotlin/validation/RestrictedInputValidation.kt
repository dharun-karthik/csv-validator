package validation

class RestrictedInputValidation {
    fun validate(value : String, restrictedInputList : List<String>) : Boolean{
        restrictedInputList.find { value == it } ?: return false
        return true
    }
}