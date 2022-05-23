package validation.jsonConfig

import metaData.template.JsonConfigTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SupportedFieldTypeValidatorTest {
    @Test
    fun shouldReturnErrorForFieldTypeNotSupported() {
        val supportedFieldTypeValidator = SupportedFieldTypeValidator()
        val jsonConfig = JsonConfigTemplate("test", "abc")
        val expected = listOf("abc is not supported")

        val actual = supportedFieldTypeValidator.validate(jsonConfig)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("supportedFieldTypeArguments")
    fun shouldNotReturnErrorForSupportedFieldType(type: String) {
        val supportedFieldTypeValidator = SupportedFieldTypeValidator()
        val jsonConfig = JsonConfigTemplate("test", type)
        val expected = listOf<String>()

        val actual = supportedFieldTypeValidator.validate(jsonConfig)

        assertEquals(expected, actual)
    }

    private fun supportedFieldTypeArguments(): List<Arguments> {
        return listOf<Arguments>(
            Arguments.of("number"),
            Arguments.of("alphanumeric"),
            Arguments.of("alphabets"),
            Arguments.of("date"),
            Arguments.of("time"),
            Arguments.of("date-time"),
            Arguments.of("email"),
            Arguments.of("text")
        )
    }
}