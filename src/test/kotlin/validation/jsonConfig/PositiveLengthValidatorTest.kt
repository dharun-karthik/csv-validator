package validation.jsonConfig

import metaData.template.JsonConfigTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PositiveLengthValidatorTest {

    @Test
    fun shouldGetAllTypeOfLengthErrors() {
        val positiveLengthValidator = PositiveLengthValidator()
        val jsonConfig = JsonConfigTemplate("test12", "number", length = "-12", maxLength = "-1", minLength = "-9")

        val expected = listOf(
            "Min length in test12 should be greater than 0",
            "Max length in test12 should be greater than 0",
            "Fixed length in test12 should be greater than 0"
        )
        val actual = positiveLengthValidator.validate(jsonConfig)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldGetNoErrorsWhenNoLengthIsPassed() {
        val positiveLengthValidator = PositiveLengthValidator()
        val jsonConfig = JsonConfigTemplate("test12", "number")

        val expected = listOf<String>()
        val actual = positiveLengthValidator.validate(jsonConfig)

        assertEquals(expected, actual)
    }
}