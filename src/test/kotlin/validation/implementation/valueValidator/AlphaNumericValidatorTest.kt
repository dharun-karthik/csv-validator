package validation.implementation.valueValidator

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class AlphaNumericValidatorTest {
    private val alphaNumericValidator = AlphaNumericValidator()

    @Test
    fun shouldReturnTrueIfInputTextIsAlphaNumeric() {
        val inputText = "abcABC12"

        val actual = alphaNumericValidator.validate(inputText)

        assertTrue(actual)
    }

    @Test
    fun shouldReturnFalseIfInputTextIsNotAlphaNumeric() {
        val inputText = "abcABC1:"

        val actual = alphaNumericValidator.validate(inputText)

        assertFalse(actual)
    }

    @Test
    fun shouldReturnTrueIfInputTextHavingAlphabetsOnlyIsAlphaNumeric() {
        val inputText = "abcABC"

        val actual = alphaNumericValidator.validate(inputText)

        assertTrue(actual)
    }

    @Test
    fun shouldReturnTrueIfInputTextHavingDigitsOnlyIsAlphaNumeric() {
        val inputText = "1234"

        val actual = alphaNumericValidator.validate(inputText)

        assertTrue(actual)
    }
}