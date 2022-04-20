package validation.implementation.valueValidator

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmailValidatorTest {

    @ParameterizedTest
    @MethodSource("validEmailArguments")
    fun shouldReturnTrueWhenEmailIsValid(emailValue: String) {
        val emailValidator = EmailValidator()

        val actual = emailValidator.validate(emailValue)

        assertTrue(actual)
    }

    @ParameterizedTest
    @MethodSource("inValidEmailArguments")
    fun shouldReturnFalseWhenEmailIsInValid(emailValue: String) {
        val emailValidator = EmailValidator()

        val actual = emailValidator.validate(emailValue)

        assertFalse(actual)
    }

    private fun inValidEmailArguments(): List<Arguments> {
        return listOf(
            Arguments.of("Abc.example.com"),
            Arguments.of("A@b@c@example.com"),
            Arguments.of("a\"b(c)d,e:f;g<h>i[j\\k]l@example.com"),
            Arguments.of("just\"not\"right@example.com"),
            Arguments.of("this is\"not\\allowed@example.com"),
            Arguments.of("this\\ still\\\"not\\\\allowed@example.com"),
            Arguments.of("i_like_underscore@but_its_not_allowed_in_this_part.example.com"),
            Arguments.of("QA[icon]CHOCOLATE[icon]@test.com"),
        )
    }

    private fun validEmailArguments(): List<Arguments> {
        return listOf(
            Arguments.of("simple@example.com"),
            Arguments.of("very.common@example.com"),
            Arguments.of("disposable.style.email.with+symbol@example.com"),
            Arguments.of("other.email-with-hyphen@example.com"),
            Arguments.of("fully-qualified-domain@example.com"),
            Arguments.of("user.name+tag+sorting@example.com"),
            Arguments.of("x@example.com"),
            Arguments.of("example-indeed@strange-example.com"),
            Arguments.of("test/test@test.com"),
            Arguments.of("admin@mailserver1"),
            Arguments.of("example@s.example"),
            Arguments.of("mailhost!username@example.org"),
            Arguments.of("user%example.com@example.org"),
            Arguments.of("user-@example.org"),
        )
    }
}