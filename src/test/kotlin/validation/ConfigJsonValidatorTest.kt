package validation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ConfigJsonValidatorTest {
    private val configJsonValidator = ConfigJsonValidator()

    @Test
    fun shouldGetConfigErrors() {
        val content = """[{"minLength":"-88"}]"""

        val expected =
            """[{"1":["Field 'fieldName' should be provided","Field 'type' should be provided","Min length should be greater than 0"]}]"""

        val actual = configJsonValidator.validate(content)

        assertEquals(expected, actual.toString())
    }
}