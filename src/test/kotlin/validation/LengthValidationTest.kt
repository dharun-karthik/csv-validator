package validation

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import validation.implementation.LengthValidation

internal class LengthValidationTest {

    @Test
    fun shouldReturnFalseIfDataLengthIsGreaterThanMaxLength() {
        val lengthValidation = LengthValidation()

        val actual = lengthValidation.maxLength("abcdef", 5)

        assertFalse(actual)
    }

    @Test
    fun shouldReturnTrueIfDataLengthIsLessThanMaxLength() {
        val lengthValidation = LengthValidation()

        val actual = lengthValidation.maxLength("abcdef", 7)

        assertTrue(actual)
    }

    @Test
    fun shouldReturnTrueIfDataLengthIsEqualToMaxLength() {
        val lengthValidation = LengthValidation()

        val actual = lengthValidation.maxLength("abcdef", 6)

        assertTrue(actual)
    }

    @Test
    fun shouldReturnFalseIfDataLengthIsLessThanMinLength() {
        val lengthValidation = LengthValidation()

        val actual = lengthValidation.minLength("abcdef", 7)

        assertFalse(actual)
    }

    @Test
    fun shouldReturnFalseIfDataLengthIsGreaterThanMinLength() {
        val lengthValidation = LengthValidation()

        val actual = lengthValidation.minLength("abcdef", 5)

        assertTrue(actual)
    }

    @Test
    fun shouldReturnTrueIfDataLengthIsEqualToMinLength() {
        val lengthValidation = LengthValidation()

        val actual = lengthValidation.minLength("abcdef", 6)

        assertTrue(actual)
    }

    @Test
    fun shouldReturnFalseIfDataLengthIsLessThanFixedLength() {
        val lengthValidation = LengthValidation()

        val actual = lengthValidation.fixedLength("abcde", 6)

        assertFalse(actual)
    }

    @Test
    fun shouldReturnFalseIfDataLengthIsGreaterThanFixedLength() {
        val lengthValidation = LengthValidation()

        val actual = lengthValidation.fixedLength("abcdeas", 6)

        assertFalse(actual)
    }

    @Test
    fun shouldReturnTrueIfDataLengthIsEqualFixedLength() {
        val lengthValidation = LengthValidation()

        val actual = lengthValidation.fixedLength("abcdef", 6)

        assertTrue(actual)
    }
}