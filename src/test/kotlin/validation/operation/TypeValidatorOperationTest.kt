package validation.operation

import metaData.template.JsonConfigTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class TypeValidatorOperationTest {

    @Test
    fun shouldGetNullWhenFieldValueIsNull() {
        val typeValidation = TypeValidationOperation()
        val metaDataField = JsonConfigTemplate("test", "number")

        val actual = typeValidation.validate(metaDataField, "null", "test")

        assertNull(actual)
    }

    @Test
    fun shouldGetAppropriateErrorMessage() {
        val typeValidation = TypeValidationOperation()
        val metaDataField = JsonConfigTemplate("test", "number")

        val actual = typeValidation.validate(metaDataField, "1fa2", "test")
        val expected = "Incorrect format of 'number' in test : 1fa2"

        assertEquals(expected, actual)
    }

    @Test
    fun shouldGetExpectedFormatInErrorMessageForDate() {
        val typeValidation = TypeValidationOperation()
        val metaDataField = JsonConfigTemplate("test", "date", pattern = "uuuu/MM/dd")

        val actual = typeValidation.validate(metaDataField, "02/2022/05", "test")
        val expected = "Incorrect format of 'date' in test : 02/2022/05, expected format : yyyy/MM/dd"

        assertEquals(expected, actual)
    }
}