package validation.implementation.valueValidator

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class NumberValidatorTest {
    private val numberValidator = NumberValidator()

    @Test
    fun shouldReturnTrueIfInputTextIsNumeric() {
        val inputText = "1"

        val actual = numberValidator.validate(inputText)

        assertTrue(actual)
    }

    @Test
    fun shouldReturnFalseIfInputTextIsNotNumeric() {
        val inputText = "123aA:?"

        val actual = numberValidator.validate(inputText)

        assertFalse(actual)
    }
}