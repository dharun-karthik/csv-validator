package validation.implementation.valueValidator

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
            Arguments.of("\" \"@example.org"),
            Arguments.of("\"john..doe\"@example.org"),
            Arguments.of("mailhost!username@example.org"),
            Arguments.of("\"very.(),:;<>[]\\\".VERY.\\\"very@\\\\ \\\"very\\\".unusual\"@strange.example.com"),
            Arguments.of("user%example.com@example.org"),
            Arguments.of("user-@example.org"),
            Arguments.of("postmaster@[123.123.123.123]"),
            Arguments.of("postmaster@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370:7334]"),
        )
    }
}