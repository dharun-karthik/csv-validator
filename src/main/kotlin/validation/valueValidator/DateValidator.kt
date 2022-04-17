package validation.valueValidator

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateValidator {
    fun validate(pattern: String, value: String): Boolean {
        return try {
            val format = DateTimeFormatter.ofPattern(pattern)
            println(LocalDate.parse(value, format).atStartOfDay())
            true
        } catch (e: Exception) {
            false
        }
    }
}