package validation.valueValidator

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TimeValidatorTest{
    @ParameterizedTest
    @MethodSource("validTimeArguments")
    fun shouldReturnTrueWhenValidTimeAndPatternIsGiven(pattern: String, timeValue: String) {
        val timeValidator = TimeValidator()

        val actual = timeValidator.validate(pattern, timeValue)

        assertTrue(actual)
    }

    @ParameterizedTest
    @MethodSource("inValidTimeArguments")
    fun shouldReturnFalseWhenValidTimeAndPatternIsGiven(pattern: String, timeValue: String) {
        val timeValidator = TimeValidator()

        val actual = timeValidator.validate(pattern, timeValue)

        assertFalse(actual)
    }

    private fun validTimeArguments(): List<Arguments> {
        return listOf(
            Arguments.of("HH:mm:ss", "00:12:18"),
            Arguments.of("HH:mm:ss", "23:59:58"),
            Arguments.of("HH:ss:mm", "23:58:59"),
            Arguments.of("ss:HH:mm", "56:23:35"),
            Arguments.of("HH:ss:mm", "23:58:59"),
            Arguments.of("HH:ss:mm:SSS", "23:58:59:978"),
            Arguments.of("hh:ss:mm a", "12:00:58 am"),
            Arguments.of("hh:ss:mma", "12:00:58am"),
            Arguments.of("ahh:ss:mm", "am12:00:58"),
            Arguments.of("hh:ass:mm", "12:am00:58"),
            Arguments.of("hh:ass:mm:SSS", "12:am00:58:917"),
        )
    }

    private fun inValidTimeArguments(): List<Arguments> {
        return listOf(
            Arguments.of("HH:mm:ss", "25:34:58"),
            Arguments.of("hh:ss:mm a", "12:00:58am"),
            Arguments.of("hh:ss:mm", "0:58:36"),
            Arguments.of("hh:mm:ss", "13:00:00"),
        )
    }

}