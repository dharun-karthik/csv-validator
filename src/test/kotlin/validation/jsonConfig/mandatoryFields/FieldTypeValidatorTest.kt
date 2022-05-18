package validation.jsonConfig.mandatoryFields

import metaData.template.JsonConfigTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class FieldTypeValidatorTest {

    @Test
    fun shouldGetErrorIfFieldTypeIsNotProvided() {
        val fieldTypeValidator = FieldTypeValidator()
        val jsonField = JsonConfigTemplate("hello")
        val expected = "Field 'type' should be provided"

        val actual = fieldTypeValidator.validate(jsonField)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldNotGetErrorIfFieldTypeIsProvided() {
        val fieldTypeValidator = FieldTypeValidator()
        val jsonField = JsonConfigTemplate("hello", "number")

        val actual = fieldTypeValidator.validate(jsonField)

        assertNull(actual)
    }
}