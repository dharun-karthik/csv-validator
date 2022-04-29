package validation.jsonConfig.length

import metaData.template.JsonConfigTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class MinLengthValidatorTest {

    @Test
    fun shouldGetErrorMessageWhenMinLengthIsLesserThanOne() {
        val minLengthValidator = MinLengthValidator()
        val jsonConfig = JsonConfigTemplate("test", "number", minLength = -1)

        val expected = "Min length should be greater than 0"
        val actual = minLengthValidator.validate(jsonConfig)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldGetNullWhenMinLengthIsGreaterThanOne() {
        val minLengthValidator = MinLengthValidator()
        val jsonConfig = JsonConfigTemplate("test", "number", minLength = 11)

        val actual = minLengthValidator.validate(jsonConfig)

        assertNull(actual)
    }

    @Test
    fun shouldGetNullWhenMinLengthIsNotGiven() {
        val minLengthValidator = MinLengthValidator()
        val jsonConfig = JsonConfigTemplate("test", "number")

        val actual = minLengthValidator.validate(jsonConfig)

        assertNull(actual)
    }
}