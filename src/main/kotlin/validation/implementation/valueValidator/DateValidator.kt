package validation.implementation.valueValidator

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle

class DateValidator : ValueTypeValidator {
    override fun validate(value: String, pattern: String?): Boolean {
        return try {
            val format = DateTimeFormatter.ofPattern(pattern).withResolverStyle(ResolverStyle.STRICT)
            LocalDate.parse(value, format)
            true
        } catch (e: Exception) {
            false
        }
    }
}