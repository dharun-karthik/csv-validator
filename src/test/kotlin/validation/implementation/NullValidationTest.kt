package validation.implementation

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class NullValidationTest {
    @Test
    fun shouldGetTrueIfNullIsNotThere(){
        val nullValidation = NullValidation()

        val actual = nullValidation.validate("null")

        assertTrue(actual)
    }
}