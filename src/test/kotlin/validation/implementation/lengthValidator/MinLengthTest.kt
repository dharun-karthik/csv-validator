package validation.implementation.lengthValidator

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import validation.implementation.lengthValidator.MinLength

internal class MinLengthTest {
    private val minLength = MinLength()
    private val inputText = "Abc123"

    @Test
    fun shouldReturnTrueIfInputTextLengthIsMoreThanMinLengthSpecified() {
        val length = 3

        val actual = minLength.validateLengthType(inputText, length)

        assertTrue(actual);
    }

    @Test
    fun shouldReturnTrueIfInputTextLengthIsMoreThanOrEqualToMinLengthSpecified() {
        val length = 6

        val actual = minLength.validateLengthType(inputText, length)

        assertTrue(actual);
    }

    @Test
    fun shouldReturnFalseIfInputTextLengthIsLessThanMinLengthSpecified() {
        val length = 7

        val actual = minLength.validateLengthType(inputText, length)

        assertFalse(actual);
    }

    @Test
    fun shouldReturnTrueIfMinLengthIsNull() {
        val length = null

        val actual = minLength.validateLengthType(inputText, length)

        assertTrue(actual);
    }
}