package valueValidator

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import validation.implementation.TypeValidation

internal class NumbersTest {
    private val numbers = Numbers()
    private val typeValidation = TypeValidation()

    @Test
    fun shouldReturnTrueIfInputTextIsNumeric() {
        val inputText = "1"

        val actual = numbers.validateValueType(inputText, typeValidation)

        assertTrue(actual)
    }

    @Test
    fun shouldReturnFalseIfInputTextIsNotNumeric() {
        val inputText = "123aA:?"

        val actual = numbers.validateValueType(inputText, typeValidation)

        assertFalse(actual)
    }
}