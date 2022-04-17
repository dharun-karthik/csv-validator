package validation.implementation.valueValidator

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateValidator : ValueTypeValidator {
    override fun validate(value: String, pattern: String?): Boolean {
        return try {
            val format = DateTimeFormatter.ofPattern(pattern)
            println(LocalDate.parse(value, format))
            true
        } catch (e: Exception) {
            false
        }
    }
}