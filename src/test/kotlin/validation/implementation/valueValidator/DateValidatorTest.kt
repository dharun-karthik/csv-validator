package validation.implementation.valueValidator

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

        val actual = dateValidator.validate(dateValue, pattern)

        assertTrue(actual)
    }

    @ParameterizedTest
    @MethodSource("inValidDateArguments")
    fun shouldReturnFalseWhenValidDateAndPatternIsGiven(pattern: String, dateValue: String) {
        val dateValidator = DateValidator()

        val actual = dateValidator.validate(dateValue, pattern)

        assertFalse(actual)
    }

    private fun validDateArguments(): List<Arguments> {
        return listOf(
            Arguments.of("dd/MM/uuuu", "20/07/2000"),
            Arguments.of("dd/uuuu/MM", "20/2000/07"),
            Arguments.of("MM/dd/uuuu", "07/20/2000"),
            Arguments.of("MM/uuuu/dd", "07/2000/20"),
            Arguments.of("uuuu/dd/MM", "2000/20/07"),
            Arguments.of("uuuu/MM/dd", "2000/07/20"),
            Arguments.of("dd-MM-uuuu", "20-07-2000"),
            Arguments.of("dd-uuuu-MM", "20-2000-07"),
            Arguments.of("MM-dd-uuuu", "07-20-2000"),
            Arguments.of("MM-uuuu-dd", "07-2000-20"),
            Arguments.of("uuuu-dd-MM", "2000-20-07"),
            Arguments.of("uuuu-MM-dd", "2000-07-20"),
            Arguments.of("dd:MM:uuuu", "20:07:2000"),
            Arguments.of("dd:uuuu:MM", "20:2000:07"),
            Arguments.of("MM:dd:uuuu", "07:20:2000"),
            Arguments.of("MM:uuuu:dd", "07:2000:20"),
            Arguments.of("uuuu:dd:MM", "2000:20:07"),
            Arguments.of("uuuu:MM:dd", "2000:07:20"),
            Arguments.of("dd MM uuuu", "20 07 2000"),
            Arguments.of("dd uuuu MM", "20 2000 07"),
            Arguments.of("MM dd uuuu", "07 20 2000"),
            Arguments.of("MM uuuu dd", "07 2000 20"),
            Arguments.of("uuuu dd MM", "2000 20 07"),
            Arguments.of("uuuu MM dd", "2000 07 20"),
            Arguments.of("dd/MM/uuuu", "29/02/2000"),
        )
    }

    private fun inValidDateArguments(): List<Arguments> {
        return listOf(
            Arguments.of("dd/MM/uuuu", "2000/07/20"),
            Arguments.of("dd/MM/uuuu", "20:07:2000"),
            Arguments.of("dd/MM/uuuu", "31/02/2000"),
            Arguments.of("dd/MM/uuuu", "29/02/2001"),
            Arguments.of("dd/MM/uuuu", "38/02/2000"),
            Arguments.of("dd/MM/uuuu", "38/13/2000"),
        )
    }
}