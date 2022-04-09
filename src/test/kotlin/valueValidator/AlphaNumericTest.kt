package valueValidator

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import validation.TypeValidation

internal class AlphaNumericTest{
    private val alphaNumeric = AlphaNumeric()
    private val typeValidation = TypeValidation()

    @Test
    fun shouldReturnTrueIfInputTextIsAlphaNumeric() {
        val inputText = "abcABC12"

        val actual = alphaNumeric.validateValueType(inputText, typeValidation)

        assertTrue(actual)
    }

    @Test
    fun shouldReturnFalseIfInputTextIsNotAlphaNumeric() {
        val inputText = "abcABC1:"

        val actual = alphaNumeric.validateValueType(inputText, typeValidation)

        assertFalse(actual)
    }

    @Test
    fun shouldReturnTrueIfInputTextHavingAlphabetsOnlyIsAlphaNumeric() {
        val inputText = "abcABC"

        val actual = alphaNumeric.validateValueType(inputText, typeValidation)

        assertTrue(actual)
    }

    @Test
    fun shouldReturnTrueIfInputTextHavingDigitsOnlyIsAlphaNumeric() {
        val inputText = "1234"

        val actual = alphaNumeric.validateValueType(inputText, typeValidation)

        assertTrue(actual)
    }
}