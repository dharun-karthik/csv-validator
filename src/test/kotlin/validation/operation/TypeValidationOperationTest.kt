package validation.operation

import metaData.template.JsonMetaDataTemplate
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class TypeValidationOperationTest {

    @Test
    fun shouldGetNullWhenFieldValueIsNull() {
        val typeValidation = TypeValidationOperation()
        val metaDataField = JsonMetaDataTemplate("test", "number")

        val actual = typeValidation.validate(metaDataField, "null", "test")

        assertNull(actual)
    }

    @Test
    fun shouldGetAppropriateErrorMessage() {
        val typeValidation = TypeValidationOperation()
        val metaDataField = JsonMetaDataTemplate("test", "number")

        val actual = typeValidation.validate(metaDataField, "1fa2", "test")
        val expected = "Incorrect format of 'number' in test : 1fa2"

        assertEquals(expected, actual)
    }
}