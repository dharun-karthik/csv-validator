package validation

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class RestrictedInputValidationTest {
    private val restrictedInputList = listOf("Y", "N", "y", "n")

    @Test
    fun shouldReturnTrueIfInputTextIsPresentInRestrictedInputList() {
        val restrictedInputValidation = RestrictedInputValidation()
        val inputText = "Y"

        val actual = restrictedInputValidation.validate(inputText, restrictedInputList)

        assertTrue(actual)
    }

    @Test
    fun shouldReturnFalseIfInputTextIsNotPresentInRestrictedInputList() {
        val restrictedInputValidation = RestrictedInputValidation()
        val inputText = "Yes"

        val actual = restrictedInputValidation.validate(inputText, restrictedInputList)

        assertFalse(actual)
    }
}