package validation.implementation.valueValidator

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class TimeValidator : ValueTypeValidator {
    override fun validate(value: String, pattern: String?): Boolean {
        return try {
            val lowerCaseTimeValue = value.lowercase()
            val format = DateTimeFormatter.ofPattern(pattern, Locale.UK)
            LocalTime.parse(lowerCaseTimeValue, format)
            true
        } catch (e: Exception) {
            println(e)
            false
        }
    }

}