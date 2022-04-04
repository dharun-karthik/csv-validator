package validation

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TypeValidationTest {

    @Test
    fun test() {
        assertEquals(1, 1)
    }

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
    fun shouldBeAbleToCheckIfValueIsNotAlphabetic() {
        val typeValidation = TypeValidation()
        val value = "abcrere12"

        val actual = typeValidation.isAlphabetic(value)

        assertFalse(actual)
    }

    @Test
    fun shouldBeAbleToCheckIfValueIsAlphaNumeric(){
        val typeValidation = TypeValidation()
        val value = "asfd123"

        val actual = typeValidation.isAlphaNumeric(value)

        assertTrue(actual)
    }
}