package validation

class LengthValidation {

    fun maxLength(data: String, len: Int): Boolean {
        return data.length <= len
    }

    fun minLength(data: String, len: Int): Boolean {
        return data.length >= len
    }

    fun fixedLength(data: String, len: Int): Boolean {
        return data.length == len
    }
}