package validation.valueValidator

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class TimeValidator {
    fun validate(pattern: String, timeValue: String): Boolean {
        return try {
            val lowerCaseTimeValue = timeValue.lowercase()
            val format = DateTimeFormatter.ofPattern(pattern,Locale.UK)
            LocalTime.parse(lowerCaseTimeValue, format)
            true
        } catch (e: Exception) {
            println(e)
            false
        }
    }

}