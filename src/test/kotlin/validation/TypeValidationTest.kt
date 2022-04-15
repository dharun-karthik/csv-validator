package validation

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TypeValidationTest {

    @Test
    fun shouldBeAbleToCheckIfValueIsNumeric() {
        val typeValidation = TypeValidation()
        val value = "410401"

        val actual = typeValidation.isNumeric(value)

        assertTrue(actual)
    }

    @Test
    fun shouldBeAbleToCheckIfValueIsNotNumeric() {
        val typeValidation = TypeValidation()
        val value = "410401a"

        val actual = typeValidation.isNumeric(value)

        assertFalse(actual)
    }

    @Test
    fun shouldBeAbleToCheckIfValueIsAlphabetic() {
        val typeValidation = TypeValidation()
        val value = "abcrere"

        val actual = typeValidation.isAlphabetic(value)

        assertTrue(actual)
    }

    @Test
    fun shouldBeAbleToCheckIfValueWithSpacesIsAlphabetic() {
        val typeValidation = TypeValidation()
        val value = "This is Fun"

        val actual = typeValidation.isAlphabetic(value)

        assertTrue(actual)
    }

    @Test
    fun shouldBeAbleToCheckIfValueIsNotAlphabetic() {
        val typeValidation = TypeValidation()
        val value = "abcrere12"

        val actual = typeValidation.isAlphabetic(value)

        assertFalse(actual)
    }

    @Test
    fun shouldBeAbleToCheckIfValueIsAlphaNumeric() {
        val typeValidation = TypeValidation()
        val value = "asfd123"

        val actual = typeValidation.isAlphaNumeric(value)

        assertTrue(actual)
    }

    @Test
    fun shouldBeAbleToCheckIfValueWithSpaceIsAlphaNumeric() {
        val typeValidation = TypeValidation()
        val value = "asfd 123 23fv"

        val actual = typeValidation.isAlphaNumeric(value)

        assertTrue(actual)
    }

    @Test
    fun shouldBeAbleToCheckIfValueIsNotAlphaNumeric() {
        val typeValidation = TypeValidation()
        val value = "asfs:ae1"

        val actual = typeValidation.isAlphaNumeric(value)

        assertFalse(actual)
    }

    @ParameterizedTest
    @MethodSource("getDecimalValue")
    fun shouldBeAbleToCheckIfValueIsDecimal(value: String) {
        val typeValidation = TypeValidation()

        val actual = typeValidation.isDecimal(value)

        assertTrue(actual)
    }

    @Test
    fun shouldBeAbleToCheckIfValueIsNotDecimal() {
        val typeValidation = TypeValidation()
        val value = "12.0.0"

        val actual = typeValidation.isDecimal(value)

        assertFalse(actual)
    }

    private fun getDecimalValue(): List<Arguments> {
        return listOf(
            Arguments.of("14.32"),
            Arguments.of("-212.02"),
            Arguments.of(".2"),
            Arguments.of("11."),
            Arguments.of("+231.220")
        )
    }

    @ParameterizedTest
    @MethodSource("getDateValueInYYYYMMDDFormat")
    fun `shouldBeAbleToCheckIfValueIsDateInFormatOfYYYY-MM-DD`() {
        val typeValidation = TypeValidation()
        val value = "2022-04-15"

        val actual = typeValidation.isDateInYYYYMMDDFormat(value)

        assertTrue(actual)
    }

    private fun getDateValueInYYYYMMDDFormat(): List<Arguments>{
        return listOf(
            Arguments.of("2022-04-15"),
            Arguments.of("2025-01-01"),
            Arguments.of("2000-12-31")
        )
    }
}