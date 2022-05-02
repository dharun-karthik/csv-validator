package validation.operation

import metaData.template.JsonConfigTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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

    @ParameterizedTest()
    @MethodSource("typeAndPatternArguments")
    fun shouldGetExpectedFormatInErrorMessageForDate(
        type: String,
        pattern: String,
        currentFieldValue: String,
        expectedPattern: String
    ) {
        val typeValidation = TypeValidationOperation()
        val metaDataField = JsonConfigTemplate("test", type, pattern = pattern)

        val actual = typeValidation.validate(metaDataField, currentFieldValue, "test")
        val expected = "Incorrect format of '$type' in test : $currentFieldValue, expected format : $expectedPattern"

        assertEquals(expected, actual)
    }

    private fun typeAndPatternArguments(): List<Arguments> {
        return listOf(
            Arguments.of("date", "uuuu/MM/dd", "02/2022/05", "yyyy/MM/dd"),
            Arguments.of("time","HH:MM:ss", "abc", "HH:MM:ss"),
            Arguments.of("date-time", "HH:mm:ss?dd/uuuu/MM", "2022-05-02T12:10:09")
        )
    }
}