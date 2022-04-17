package validation.valueValidator

import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TimeValidator {
    fun validate(pattern: String, timeValue: String): Boolean {
        return try {
            val format = DateTimeFormatter.ofPattern(pattern)
            LocalTime.parse(timeValue, format)
            true
        } catch (e: Exception) {
            false
        }
    }

}