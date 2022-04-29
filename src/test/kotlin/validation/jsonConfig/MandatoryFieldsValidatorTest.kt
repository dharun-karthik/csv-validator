package validation.jsonConfig

import metaData.template.JsonConfigTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MandatoryFieldsValidatorTest {

    @Test
    fun shouldGetErrorWhenMandatoryFieldsAreNotProvided() {
        val mandatoryFieldsValidator = MandatoryFieldsValidator()
        val jsonConfig = JsonConfigTemplate()

        val expected = listOf(
            "Field 'fieldName' should be provided",
            "Field 'type' should be provided"
        )
        val actual = mandatoryFieldsValidator.validate(jsonConfig)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldNotGetErrorWhenMandatoryFieldsAreProvided() {
        val mandatoryFieldsValidator = MandatoryFieldsValidator()
        val jsonConfig = JsonConfigTemplate("test", "type")

        val expected = listOf<String>()
        val actual = mandatoryFieldsValidator.validate(jsonConfig)

        assertEquals(expected, actual)
    }
}