package validation.operation

import metaData.template.JsonConfigTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class NullValidatorOperationTest {

    @Test
    fun shouldGetErrorWhenFieldValueIsNullAndNullIsNotAllowed() {
        val nullValidationOperation = NullValidationOperation()
        val metaDataField = JsonConfigTemplate("test", "number")

        val actual = nullValidationOperation.validate(metaDataField, "null", "test")

        assertNull(actual)
    }

    @Test
    fun shouldGetNullWhenFieldValueIsNullAndNullIsAllowed() {
        val nullValidationOperation = NullValidationOperation()
        val metaDataField = JsonConfigTemplate("test", "number", isNullAllowed = "No")
        val expected = "Empty Value not allowed in test"

        val actual = nullValidationOperation.validate(metaDataField, "null", "test")

        assertEquals(expected, actual)
    }
}