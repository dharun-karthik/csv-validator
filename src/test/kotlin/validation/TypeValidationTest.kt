package validation

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

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

}