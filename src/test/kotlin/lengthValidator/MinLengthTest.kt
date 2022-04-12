package lengthValidator

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import validation.LengthValidation

internal class MinLengthTest {
    private val minLength = MinLength()
    private val inputText = "Abc123"
    private val lengthValidation = LengthValidation()

    @Test
    fun shouldReturnTrueIfInputTextLengthIsMoreThanMinLengthSpecified() {
        val length = 3

        val actual = minLength.validateLengthType(inputText, length, lengthValidation)

        assertTrue(actual);
    }

    @Test
    fun shouldReturnTrueIfInputTextLengthIsMoreThanOrEqualToMinLengthSpecified() {
        val length = 6

        val actual = minLength.validateLengthType(inputText, length, lengthValidation)

        assertTrue(actual);
    }

    @Test
    fun shouldReturnFalseIfInputTextLengthIsLessThanMinLengthSpecified() {
        val length = 7

        val actual = minLength.validateLengthType(inputText, length, lengthValidation)

        assertFalse(actual);
    }

    @Test
    fun shouldReturnTrueIfMinLengthIsNull() {
        val length = null

        val actual = minLength.validateLengthType(inputText, length, lengthValidation)

        assertTrue(actual);
    }
}