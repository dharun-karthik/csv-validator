package validation.implementation.lengthValidator

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class FixedLengthTest {

    private val fixedLength = FixedLength()
    private val inputText = "IND"

    @Test
    fun shouldReturnTrueIfInputTextLengthIsSameAsSpecified() {
        val length = 3

        val actual = fixedLength.validateLengthType(inputText, length)

        assertTrue(actual)
    }

    @Test
    fun shouldReturnFalseIfInputTextLengthIsNotSameAsSpecified() {
        val length = 2

        val actual = fixedLength.validateLengthType(inputText, length)

        assertFalse(actual)
    }

    @Test
    fun shouldReturnTrueIfLengthIsNull() {
        val length = null

        val actual = fixedLength.validateLengthType(inputText, length)

        assertTrue(actual)
    }
}