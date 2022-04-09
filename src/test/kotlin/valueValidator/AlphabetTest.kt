package valueValidator

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import validation.TypeValidation

internal class AlphabetTest{
    private val alphabet = Alphabet()
    private val typeValidation = TypeValidation()

    @Test
    fun shouldReturnTrueIfInputTextIsAlphabetic(){
        val inputText = "abcABC"

        val actual = alphabet.validateValueType(inputText, "Alphabet", typeValidation)

        assertTrue(actual)
    }

    @Test
    fun shouldReturnFalseIfInputTextIsNotAlphabetic(){
        val inputText = "abcABC12:@"

        val actual = alphabet.validateValueType(inputText, "Alphabet", typeValidation)

        assertFalse(actual)
    }


}