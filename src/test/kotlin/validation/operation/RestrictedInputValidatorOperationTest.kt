package validation.operation

import metaData.template.JsonConfigTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class RestrictedInputValidatorOperationTest {
    @Test
    fun shouldGetNullWhenFieldValueIsNull() {
        val restrictedInputValidationOperation = RestrictedInputValidationOperation()
        val metaDataField = JsonConfigTemplate("test", "number")

        val actual = restrictedInputValidationOperation.validate(metaDataField, "null", "test")

        assertNull(actual)
    }

    @Test
    fun shouldGetAppropriateErrorMessage() {
        val restrictedInputValidationOperation = RestrictedInputValidationOperation()
        val metaDataField = JsonConfigTemplate("test", "number", values = listOf("123"))
        val expected = "Value not found test : 12"

        val actual = restrictedInputValidationOperation.validate(metaDataField, "12", "test")

        assertEquals(expected, actual)
    }
}