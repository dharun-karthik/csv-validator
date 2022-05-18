package validation.jsonConfig.length

import metaData.template.JsonConfigTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class MaxLengthValidatorTest {
    @Test
    fun shouldGetErrorMessageWhenMaxLengthIsLesserThanOne() {
        val maxLengthValidator = MaxLengthValidator()
        val jsonConfig = JsonConfigTemplate("test", "number", maxLength = -1)
        val expected = "Max length should be greater than 0"

        val actual = maxLengthValidator.validate(jsonConfig)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldGetNullWhenMaxLengthIsGreaterThanOne() {
        val maxLengthValidator = MaxLengthValidator()
        val jsonConfig = JsonConfigTemplate("test", "number", maxLength = 11)

        val actual = maxLengthValidator.validate(jsonConfig)

        assertNull(actual)
    }

    @Test
    fun shouldGetNullWhenMaxLengthIsNotGiven() {
        val maxLengthValidator = MaxLengthValidator()
        val jsonConfig = JsonConfigTemplate("test", "number")

        val actual = maxLengthValidator.validate(jsonConfig)

        assertNull(actual)
    }
}