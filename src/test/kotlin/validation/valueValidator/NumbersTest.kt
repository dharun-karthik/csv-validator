package validation.valueValidator

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import validation.implementation.valueValidator.Numbers

internal class NumbersTest {
    private val numbers = Numbers()

    @Test
    fun shouldReturnTrueIfInputTextIsNumeric() {
        val inputText = "1"

        val actual = numbers.validate(inputText)

        assertTrue(actual)
    }

    @Test
    fun shouldReturnFalseIfInputTextIsNotNumeric() {
        val inputText = "123aA:?"

        val actual = numbers.validate(inputText)

        assertFalse(actual)
    }
}