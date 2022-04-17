package validation.valueValidator

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateTimeValidator {
    fun validate(pattern: String, dateTimeValue: String): Boolean {
        try {
            val lowerCasedDateTime = dateTimeValue.lowercase()
            val format = DateTimeFormatter.ofPattern(pattern)
            LocalDateTime.parse(lowerCasedDateTime, format)
            return true
        } catch (e : Exception){
            println(e)
            return false
        }
    }
}