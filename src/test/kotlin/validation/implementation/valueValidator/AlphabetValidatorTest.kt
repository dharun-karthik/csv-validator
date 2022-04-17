package validation.implementation.valueValidator

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class AlphabetValidatorTest {
    private val alphabetValidator = AlphabetValidator()

    @Test
    fun shouldReturnTrueIfInputTextIsAlphabetic() {
        val inputText = "abcABC"

        val actual = alphabetValidator.validate(inputText)

        assertTrue(actual)
    }

    @Test
    fun shouldReturnFalseIfInputTextIsNotAlphabetic() {
        val inputText = "abcABC12:@"

        val actual = alphabetValidator.validate(inputText)

        assertFalse(actual)
    }
}