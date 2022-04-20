package validation.implementation.valueValidator

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AlphabetValidatorTest {
    private val alphabetValidator = AlphabetValidator()

    @Test
    fun shouldReturnTrueIfInputTextIsAlphabetic() {
        val inputText = "abcABC"

        val actual = alphabetValidator.validate(inputText)

        assertTrue(actual)
    }

    @ParameterizedTest
    @MethodSource("inValidAlphabetArguments")
    fun shouldReturnFalseIfInputTextIsNotAlphabetic(value: String) {
        val actual = alphabetValidator.validate(value)

        assertFalse(actual)
    }

    private fun inValidAlphabetArguments(): List<Arguments> {
        return listOf(
            Arguments.of("flafj:fjaf"),
            Arguments.of("hell no"),
            Arguments.of("4jfa"),
            Arguments.of("^"),
            Arguments.of("4"),
        )
    }
}