package validation.jsonConfig

import metaData.template.JsonConfigTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MaxMinValidatorTest {

    @Test
    fun shouldGiveErrorWhenMinIsGreaterThanMaxLength() {
        val maxMinValidator = MaxMinValidator()
        val jsonConfig = JsonConfigTemplate("sample", "number", minLength = 5, maxLength = 3)

        val expected = listOf("Max length : 3 should be greater than min length : 5")
        val actual = maxMinValidator.validate(jsonConfig)

        assertEquals(expected, actual)
    }
    @Test
    fun shouldNotGetErrorWhenMinAndMaxValueAreValid() {
        val maxMinValidator = MaxMinValidator()
        val jsonConfig = JsonConfigTemplate("sample", "number", minLength = 7, maxLength = 20)

        val expected = listOf<String>()
        val actual = maxMinValidator.validate(jsonConfig)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldNotGetErrorWhenMaxLengthIsNotProvided() {
        val maxMinValidator = MaxMinValidator()
        val jsonConfig = JsonConfigTemplate("sample", "number", minLength = 5)

        val expected = listOf<String>()
        val actual = maxMinValidator.validate(jsonConfig)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldNotGetErrorWhenMinLengthIsNotProvided() {
        val maxMinValidator = MaxMinValidator()
        val jsonConfig = JsonConfigTemplate("sample", "number", maxLength = 5)

        val expected = listOf<String>()
        val actual = maxMinValidator.validate(jsonConfig)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldNotGetErrorWhenBothMinOrMaxLengthIsNotProvided() {
        val maxMinValidator = MaxMinValidator()
        val jsonConfig = JsonConfigTemplate("sample", "number")

        val expected = listOf<String>()
        val actual = maxMinValidator.validate(jsonConfig)

        assertEquals(expected, actual)
    }
}