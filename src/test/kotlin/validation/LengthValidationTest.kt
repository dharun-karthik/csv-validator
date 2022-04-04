package validation

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class LengthValidationTest {

    @Test
    fun ShouldreturnFalseIfDataLengthIsgreaterThan_maxLength() {
        val lengthValidation = LengthValidation()

        val actual = lengthValidation.maxLength("abcdef", 5)

        assertFalse(actual)
    }

    @Test
    fun ShouldreturnTrueIfDataLengthIsLessThan_maxLength() {
        val lengthValidation = LengthValidation()

        val actual = lengthValidation.maxLength("abcdef", 7)

        assertTrue(actual)
    }

    @Test
    fun ShouldreturnTrueIfDataLengthIsEqualTo_maxLength() {
        val lengthValidation = LengthValidation()

        val actual = lengthValidation.maxLength("abcdef", 6)

        assertTrue(actual)
    }
}