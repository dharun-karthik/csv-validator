package validation.jsonConfig

import metaData.template.JsonConfigTemplate
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SupportedPatternTypeValidatorTest {
    @Test
    fun shouldReturnErrorForDateTypeNotSupported() {
        val supportedPatternTypeValidator = SupportedPatternTypeValidator()
        val jsonConfig = JsonConfigTemplate("abc", "date", pattern = "yyyy:mm:dd")
        val expected = listOf("Field type yyyy:mm:dd is not supported")

        val actual = supportedPatternTypeValidator.validate(jsonConfig)

        assertEquals(expected, actual)
    }
}