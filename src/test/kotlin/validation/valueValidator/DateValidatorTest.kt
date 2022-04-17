package validation.valueValidator

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DateValidatorTest {

    @ParameterizedTest
    @MethodSource("validDateArguments")
    fun shouldReturnTrueWhenValidDateAndPatternIsGiven(pattern: String, dateValue: String) {
        val dateValidator = DateValidator()

        val actual = dateValidator.validate(pattern, dateValue)

        assertTrue(actual)
    }

    @ParameterizedTest
    @MethodSource("inValidDateArguments")
    fun shouldReturnFalseWhenValidDateAndPatternIsGiven(pattern: String, dateValue: String) {
        val dateValidator = DateValidator()

        val actual = dateValidator.validate(pattern, dateValue)

        assertFalse(actual)
    }

    private fun validDateArguments(): List<Arguments> {
        return listOf(
            Arguments.of("dd/MM/yyyy", "20/07/2000"),
            Arguments.of("dd/yyyy/MM", "20/2000/07"),
            Arguments.of("MM/dd/yyyy", "07/20/2000"),
            Arguments.of("MM/yyyy/dd", "07/2000/20"),
            Arguments.of("yyyy/dd/MM", "2000/20/07"),
            Arguments.of("yyyy/MM/dd", "2000/07/20"),
            Arguments.of("dd-MM-yyyy", "20-07-2000"),
            Arguments.of("dd-yyyy-MM", "20-2000-07"),
            Arguments.of("MM-dd-yyyy", "07-20-2000"),
            Arguments.of("MM-yyyy-dd", "07-2000-20"),
            Arguments.of("yyyy-dd-MM", "2000-20-07"),
            Arguments.of("yyyy-MM-dd", "2000-07-20"),
            Arguments.of("dd:MM:yyyy", "20:07:2000"),
            Arguments.of("dd:yyyy:MM", "20:2000:07"),
            Arguments.of("MM:dd:yyyy", "07:20:2000"),
            Arguments.of("MM:yyyy:dd", "07:2000:20"),
            Arguments.of("yyyy:dd:MM", "2000:20:07"),
            Arguments.of("yyyy:MM:dd", "2000:07:20"),
            Arguments.of("dd MM yyyy", "20 07 2000"),
            Arguments.of("dd yyyy MM", "20 2000 07"),
            Arguments.of("MM dd yyyy", "07 20 2000"),
            Arguments.of("MM yyyy dd", "07 2000 20"),
            Arguments.of("yyyy dd MM", "2000 20 07"),
            Arguments.of("yyyy MM dd", "2000 07 20"),
        )
    }

    private fun inValidDateArguments(): List<Arguments> {
        return listOf(
            Arguments.of("dd/MM/yyyy", "2000/07/20"),
            Arguments.of("dd/MM/yyyy", "20:07:2000"),
            Arguments.of("dd/MM/yyyy", "38/02/2000"),
            Arguments.of("dd/MM/yyyy", "38/13/2000"),
        )
    }
}