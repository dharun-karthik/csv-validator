package validation

class LengthValidation {
    fun maxLength(data:String, len:Int): Boolean {
        if(data.length <= len) {
            return true
        }
        return false
    }
}