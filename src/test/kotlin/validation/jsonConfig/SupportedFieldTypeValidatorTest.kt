package validation.jsonConfig

import metaData.template.JsonConfigTemplate
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SupportedFieldTypeValidatorTest {
    @Test
    fun shouldReturnErrorForFieldTypeNotSupported() {
        val supportedFieldTypeValidator = SupportedFieldTypeValidator()
        val jsonConfig = JsonConfigTemplate("test", "abc")
        val expected = listOf("Field type abc is not supported")

        val actual = supportedFieldTypeValidator.validate(jsonConfig)
        assertEquals(expected, actual)
    }
}