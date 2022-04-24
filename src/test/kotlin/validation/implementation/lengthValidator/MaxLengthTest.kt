package validation.implementation.lengthValidator

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class MaxLengthTest {
    private val maxLength = MaxLength()
    private val inputText = "Abc123"

    @Test
    fun shouldReturnTrueIfInputTextLengthIsLessThanMaxLengthSpecified() {
        val length = 7

        val actual = maxLength.validateLengthType(inputText, length)

        assertTrue(actual)
    }

    @Test
    fun shouldReturnTrueIfInputTextLengthIsLessThanOrEqualToMaxLengthSpecified() {
        val length = 6

        val actual = maxLength.validateLengthType(inputText, length)

        assertTrue(actual)
    }

    @Test
    fun shouldReturnFalseIfInputTextLengthIsMoreThanMaxLengthSpecified() {
        val length = 5

        val actual = maxLength.validateLengthType(inputText, length)

        assertFalse(actual)
    }

    @Test
    fun shouldReturnTrueIfMaxLengthIsNull() {
        val length = null

        val actual = maxLength.validateLengthType(inputText, length)

        assertTrue(actual)
    }
}