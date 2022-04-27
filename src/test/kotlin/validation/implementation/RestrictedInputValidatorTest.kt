package validation.implementation

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class RestrictedInputValidatorTest {
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