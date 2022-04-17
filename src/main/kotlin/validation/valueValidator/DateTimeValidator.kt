package validation.valueValidator

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DateTimeValidator {
    fun validate(pattern: String, dateTimeValue: String): Boolean {
        return try {
            val lowerCasedDateTime = dateTimeValue.lowercase()
            val format = DateTimeFormatter.ofPattern(pattern, Locale.UK)
            LocalDateTime.parse(lowerCasedDateTime, format)
            true
        } catch (e: Exception) {
            println(e)
            false
        }
    }
}