package lengthValidator

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import validation.LengthValidation

internal class FixedLengthTest{
    @Test
    fun shouldReturnTrueIfInputTextLengthIsSameAsSpecified(){
        val fixedLength = FixedLength();
        val inputText = "IND"
        val length = 3

        val actual = fixedLength.validateLengthType(inputText, length, lengthValidation = LengthValidation())

        assertTrue(actual)
    }

    @Test
    fun shouldReturnFalseIfInputTextLengthIsNotSameAsSpecified(){
        val fixedLength = FixedLength();
        val inputText = "IND"
        val length = 2

        val actual = fixedLength.validateLengthType(inputText, length, lengthValidation = LengthValidation())

        assertFalse(actual)
    }

    @Test
    fun shouldReturnTrueIfLengthIsNull(){
        val fixedLength = FixedLength();
        val inputText = "IND"
        val length = null

        val actual = fixedLength.validateLengthType(inputText, length, lengthValidation = LengthValidation())

        assertTrue(actual)
    }


}