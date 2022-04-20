package validation.implementation.valueValidator

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AlphaNumericValidatorTest {
    private val alphaNumericValidator = AlphaNumericValidator()

    @ParameterizedTest
    @MethodSource("validAlphaNumericArguments")
    fun shouldReturnTrueIfInputTextIsAlphaNumeric(inputText: String) {
        val actual = alphaNumericValidator.validate(inputText)

        assertTrue(actual)
    }

    @ParameterizedTest
    @MethodSource("inValidAlphaNumericArguments")
    fun shouldReturnFalseIfInputTextIsNotAlphaNumeric(inputText: String) {
        val actual = alphaNumericValidator.validate(inputText)

        assertFalse(actual)
    }

    private fun validAlphaNumericArguments(): List<Arguments> {
        return listOf(
            Arguments.of("abc"),
            Arguments.of("Abc"),
            Arguments.of("AA"),
            Arguments.of("Ad12222"),
            Arguments.of("a22"),
        )
    }

    private fun inValidAlphaNumericArguments(): List<Arguments> {
        return listOf(
            Arguments.of("abc fj"),
            Arguments.of("abc 12"),
            Arguments.of("abc:12"),
            Arguments.of("abc12,"),
        )
    }
}