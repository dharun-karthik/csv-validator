package validation.operation

import metaData.template.JsonMetaDataTemplate
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class NullValidatorOperationTest {

    @Test
    fun shouldGetErrorWhenFieldValueIsNullAndNullIsNotAllowed() {
        val nullValidationOperation = NullValidationOperation()
        val metaDataField = JsonMetaDataTemplate("test", "number")

        val actual = nullValidationOperation.validate(metaDataField, "null", "test")

        assertNull(actual)
    }

    @Test
    fun shouldGetNullWhenFieldValueIsNullAndNullIsAllowed() {
        val nullValidationOperation = NullValidationOperation()
        val metaDataField = JsonMetaDataTemplate("test", "number", isNullAllowed = "No")

        val actual = nullValidationOperation.validate(metaDataField, "null", "test")
        val expected = "Empty Value not allowed in test"

        assertEquals(expected, actual)
    }
}