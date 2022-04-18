package validation.implementation.valueValidator

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DateTimeValidatorTest {


    @ParameterizedTest
    @MethodSource("validDateTimeArguments")
    fun shouldReturnTrueWhenValidDateTimeAndPatternIsGiven(pattern: String, dateTimeValue: String) {
        val dateTimeValidator = DateTimeValidator()

        val actual = dateTimeValidator.validate(dateTimeValue, pattern)

        assertTrue(actual)
    }

    @ParameterizedTest
    @MethodSource("inValidDateTimeArguments")
    fun shouldReturnFalseWhenInValidDateTimeAndPatternIsGiven(pattern: String, dateTimeValue: String) {
        val dateTimeValidator = DateTimeValidator()

        val actual = dateTimeValidator.validate(dateTimeValue, pattern)

        assertFalse(actual)
    }

    private fun validDateTimeArguments(): List<Arguments> {
        return listOf(
            Arguments.of("HH:mm:ss dd/MM/uuuu", "00:12:18 20/07/2000"),
            Arguments.of("HH:mm:ss?dd/uuuu/MM", "23:59:58?20/2000/07"),
            Arguments.of("HH:ss:mm:dd:MM:uuuu", "23:58:59:20:07:2000"),
            Arguments.of("ss:HH:mm/uuuu/MM/dd", "56:23:35/2000/07/20"),
            Arguments.of("HH:ss:mm-dd-uuuu-MM", "23:58:59-20-2000-07"),
            Arguments.of("HH:ss:mm:SSS dd MM uuuu", "23:58:59:978 20 07 2000"),
            Arguments.of("hh:ss:mm a,uuuu-MM-dd", "12:00:58 AM,2000-07-20"),
            Arguments.of("hh:ss:mma+dd/MM/uuuu", "12:00:58AM+20/07/2000"),
            Arguments.of("ahh:ss:mm () dd/MM/uuuu", "aM12:00:58 () 20/07/2000"),
            Arguments.of("dd/MM/uuuu hh:ass:mm", "20/07/2000 12:AM00:58"),
            Arguments.of("dd/MM/uuuu == hh:ass:mm:SSS", "20/07/2000 == 12:am00:58:917"),
            Arguments.of("dd MM uuuu hh:ass:mm", "20 07 2001 12:aM00:58"),
        )
    }

    private fun inValidDateTimeArguments(): List<Arguments> {
        return listOf(
            Arguments.of("HH:mm:ss dd/MM/uuuu", "25:34:58 12/02/2000"),
            Arguments.of("HH:mm:ss dd/MM/uuuu", "23:34:58 30/02/2000"),
            Arguments.of("HH:mm:ss dd/MM/uuuu", "00:12:18/28/07/2000"),
            Arguments.of("hh:ss:mm a-dd-MM-uuuu", "12:00:58 am-33/07/2000"),
            Arguments.of("dd-MM/uuuu,HH:ss:mm", "12/16/2000,0:58:36"),
            Arguments.of("HH:mm:ss", "13:00:00"),
            Arguments.of("dd-MM-uuuu", "12/08/2000")
        )
    }
}