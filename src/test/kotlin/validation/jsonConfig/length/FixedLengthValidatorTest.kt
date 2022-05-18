package validation.jsonConfig.length

import metaData.template.JsonConfigTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class FixedLengthValidatorTest {
    @Test
    fun shouldGetErrorMessageWhenLengthIsLesserThanOne() {
        val fixedLengthValidator = FixedLengthValidator()
        val jsonConfig = JsonConfigTemplate("test", "number", length = -1)

        val expected = "Fixed length should be greater than 0"
        val actual = fixedLengthValidator.validate(jsonConfig)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldGetNullWhenLengthIsGreaterThanOne() {
        val fixedLengthValidator = FixedLengthValidator()
        val jsonConfig = JsonConfigTemplate("test", "number", length = 11)

        val actual = fixedLengthValidator.validate(jsonConfig)

        assertNull(actual)
    }

    @Test
    fun shouldGetNullWhenMinLengthIsNotGiven() {
        val fixedLengthValidator = FixedLengthValidator()
        val jsonConfig = JsonConfigTemplate("test", "number")

        val actual = fixedLengthValidator.validate(jsonConfig)

        assertNull(actual)
    }
}