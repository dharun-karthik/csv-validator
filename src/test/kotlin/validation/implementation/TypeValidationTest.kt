package validation.implementation

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import validation.implementation.valueValidator.AlphaNumericValidator
import validation.implementation.valueValidator.AlphabetValidator
import validation.implementation.valueValidator.NumberValidator

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TypeValidationTest {

    @Test
    fun shouldBeAbleToCheckIfValueIsNumeric() {
        val value = "410401"

        val actual = NumberValidator().validate(value)

        assertTrue(actual)
    }

    @Test
    fun shouldBeAbleToCheckIfValueIsNotNumeric() {
        val value = "410401a"

        val actual = NumberValidator().validate(value)

        assertFalse(actual)
    }

    @Test
    fun shouldBeAbleToCheckIfValueIsAlphabetic() {
        val value = "abcrere"

        val actual = AlphabetValidator().validate(value)

        assertTrue(actual)
    }

    @Test
    fun shouldBeAbleToCheckIfValueWithSpacesIsAlphabetic() {
        val value = "This is Fun"

        val actual = AlphabetValidator().validate(value)

        assertTrue(actual)
    }

    @Test
    fun shouldBeAbleToCheckIfValueIsNotAlphabetic() {
        val value = "abcrere12"

        val actual = AlphabetValidator().validate(value)

        assertFalse(actual)
    }

    @Test
    fun shouldBeAbleToCheckIfValueIsAlphaNumeric() {
        val value = "asfd123"

        val actual = AlphaNumericValidator().validate(value)

        assertTrue(actual)
    }

    @Test
    fun shouldBeAbleToCheckIfValueWithSpaceIsAlphaNumeric() {
        val value = "asfd 123 23fv"

        val actual = AlphaNumericValidator().validate(value)

        assertTrue(actual)
    }

    @Test
    fun shouldBeAbleToCheckIfValueIsNotAlphaNumeric() {
        val value = "asfs:ae1"

        val actual = AlphaNumericValidator().validate(value)

        assertFalse(actual)
    }
}