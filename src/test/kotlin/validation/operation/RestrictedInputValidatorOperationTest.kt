package validation.operation

import metaData.template.JsonMetaDataTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class RestrictedInputValidatorOperationTest {
    @Test
    fun shouldGetNullWhenFieldValueIsNull() {
        val restrictedInputValidationOperation = RestrictedInputValidationOperation()
        val metaDataField = JsonMetaDataTemplate("test", "number")

        val actual = restrictedInputValidationOperation.validate(metaDataField, "null", "test")

        assertNull(actual)
    }

    @Test
    fun shouldGetAppropriateErrorMessage() {
        val restrictedInputValidationOperation = RestrictedInputValidationOperation()
        val metaDataField = JsonMetaDataTemplate("test", "number", values = listOf("123"))

        val actual = restrictedInputValidationOperation.validate(metaDataField, "12", "test")
        val expected = "Value Not Found test : 12"

        assertEquals(expected, actual)
    }
}