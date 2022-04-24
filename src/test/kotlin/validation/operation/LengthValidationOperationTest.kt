package validation.operation

import metaData.template.JsonMetaDataTemplate
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class LengthValidationOperationTest {

    @Test
    fun shouldGetNullWhenFieldValueIsNull() {
        val lengthValidation = LengthValidationOperation()
        val metaDataField = JsonMetaDataTemplate("test", "number", maxLength = "2")

        val actual = lengthValidation.validate(metaDataField, "null", "test")

        assertNull(actual)
    }

    @Test
    fun shouldGetNullWhenLengthConstrainIsNotGiven() {
        val lengthValidation = LengthValidationOperation()
        val metaDataField = JsonMetaDataTemplate("test", "number")

        val actual = lengthValidation.validate(metaDataField, "3424", "test")

        assertNull(actual)
    }

    @Test
    fun shouldGetErrorWhenLengthConstrainIsNotGiven() {
        val lengthValidation = LengthValidationOperation()
        val metaDataField = JsonMetaDataTemplate("test", "number", minLength = "2")

        val actual = lengthValidation.validate(metaDataField, "3", "test")
        val expected = "Value length should be lesser than 2 in test : 3"

        assertEquals(expected,actual)
    }
}