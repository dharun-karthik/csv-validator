package validation.implementation.valueValidator

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TextValidatorTest {

    @ParameterizedTest
    @MethodSource("validTextArguments")
    fun shouldReturnTrueWhenTextIsValid(value:String){
        val textValidator = TextValidator()

        val actual = textValidator.validate(value)

        assertTrue(actual)
    }


    private fun validTextArguments(): List<Arguments> {
        return listOf(
            Arguments.of("hello this is me"),
            Arguments.of("who is this?"),
            Arguments.of("fishing!!"),
            Arguments.of("""he said "this is cool""""),
        )
    }
}