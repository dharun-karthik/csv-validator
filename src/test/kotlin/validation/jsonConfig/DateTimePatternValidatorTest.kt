package validation.jsonConfig

import metaData.template.JsonConfigTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DateTimePatternValidatorTest {

    @ParameterizedTest
    @MethodSource("dateAndTimeFieldWithPatternArgs")
    fun shouldBeValidWhenFieldWithPatternIsPassed(field: String, pattern: String) {
        val dateTimePatternValidator = DateTimePatternValidator()
        val content = JsonConfigTemplate("test", field, pattern = pattern)
        val expected = listOf<String>()

        val actual = dateTimePatternValidator.validate(content)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("dateAndTimeFieldArgs")
    fun shouldGetErrorWhenDateTimeFieldIsGivenButPatternIsNotGiven(field: String) {
        val dateTimePatternValidator = DateTimePatternValidator()
        val content = JsonConfigTemplate("test", field)
        val expected = listOf("Type '$field' expects pattern field to be not empty")

        val actual = dateTimePatternValidator.validate(content)

        assertEquals(expected, actual)
    }

    private fun dateAndTimeFieldArgs(): List<Arguments> {
        return listOf(
            Arguments.of(
                "date",
            ),
            Arguments.of(
                "date-time",
            ),
            Arguments.of(
                "time",
            )
        )
    }

    private fun dateAndTimeFieldWithPatternArgs(): List<Arguments> {
        return listOf(
            Arguments.of(
                "date",
                "32/43/4333"
            ),
            Arguments.of(
                "time",
                "32/43/4333"
            ),
            Arguments.of(
                "date-time",
                "32/43/4333"
            ),
        )
    }
}