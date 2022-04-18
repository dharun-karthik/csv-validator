package validation.implementation.valueValidator

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle
import java.util.*

class DateTimeValidator : ValueTypeValidator {
    override fun validate(value: String, pattern: String?): Boolean {
        return try {
            val lowerCasedDateTime = value.lowercase()
            val format = DateTimeFormatter.ofPattern(pattern, Locale.UK).withResolverStyle(ResolverStyle.STRICT)
            LocalDateTime.parse(lowerCasedDateTime, format)
            true
        } catch (e: Exception) {
            println(e)
            false
        }
    }
}