package validation.implementation

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class NullValidatorTest {
    @Test
    fun shouldGetFalseIfNullIsNotThere() {
        val nullValidation = NullValidation()

        val actual = nullValidation.validate("hello")

        assertFalse(actual)
    }

    @Test
    fun shouldGetTrueIfNullIsThere() {
        val nullValidation = NullValidation()

        val actual = nullValidation.validate("null")

        assertTrue(actual)
    }

}