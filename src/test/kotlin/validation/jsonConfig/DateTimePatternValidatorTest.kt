package validation.jsonConfig

import metaData.template.JsonConfigTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class DateTimePatternValidatorTest {

    //todo write more tests with time, date-time
    @Test
    fun shouldGetErrorWhenDateOrTimeFieldIsGivenButPatternIsNotGiven() {
        val dateTimePatternValidator = DateTimePatternValidator()
        val content = JsonConfigTemplate("test", "date")

        val expected = "Type 'date' expects pattern field to be not empty"
        val actual = dateTimePatternValidator.validate(content)

        assertEquals(expected, actual)
    }
}