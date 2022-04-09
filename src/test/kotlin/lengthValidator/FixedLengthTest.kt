package lengthValidator

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import validation.LengthValidation

internal class FixedLengthTest {

    private val fixedLength = FixedLength();
    private val inputText = "IND"
    private val lengthValidation = LengthValidation()

    @Test
    fun shouldReturnTrueIfInputTextLengthIsSameAsSpecified() {
        val length = 3

        val actual = fixedLength.validateLengthType(inputText, length, lengthValidation)

        assertTrue(actual)
    }

    @Test
    fun shouldReturnFalseIfInputTextLengthIsNotSameAsSpecified() {
        val length = 2

        val actual = fixedLength.validateLengthType(inputText, length, lengthValidation)

        assertFalse(actual)
    }

    @Test
    fun shouldReturnTrueIfLengthIsNull() {
        val length = null

        val actual = fixedLength.validateLengthType(inputText, length, lengthValidation)

        assertTrue(actual)
    }


}