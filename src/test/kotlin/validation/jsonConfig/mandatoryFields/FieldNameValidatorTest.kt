package validation.jsonConfig.mandatoryFields

import metaData.template.JsonConfigTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class FieldNameValidatorTest {

    @Test
    fun shouldGetErrorIfFieldNameIsNotProvided() {
        val fieldNameValidator = FieldNameValidator()
        val jsonField = JsonConfigTemplate(type = "number")

        val expected = "Field 'fieldName' should be provided"
        val actual = fieldNameValidator.validate(jsonField)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldNotGetErrorIfFieldNameIsProvided() {
        val fieldNameValidator = FieldNameValidator()
        val jsonField = JsonConfigTemplate("hello", "number")

        val actual = fieldNameValidator.validate(jsonField)

        assertNull(actual)
    }
}