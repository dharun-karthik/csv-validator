package validation.operation

import metaData.template.JsonConfigTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PositiveLengthValidatorOperationTest {

    @Test
    fun shouldGetNullWhenFieldValueIsNull() {
        val lengthValidation = LengthValidationOperation()
        val metaDataField = JsonConfigTemplate("test", "number", maxLength = "2")

        val actual = lengthValidation.validate(metaDataField, "null", "test")

        assertNull(actual)
    }

    @Test
    fun shouldGetNullWhenLengthConstrainIsNotGiven() {
        val lengthValidation = LengthValidationOperation()
        val metaDataField = JsonConfigTemplate("test", "number")

        val actual = lengthValidation.validate(metaDataField, "3424", "test")

        assertNull(actual)
    }

    @ParameterizedTest
    @MethodSource("inValidValidationArguments")
    fun shouldGetErrorWhenLengthConstrainIsGiven(
        metaDataField: JsonConfigTemplate,
        currentFieldValue: String,
        expected: String,
    ) {
        val lengthValidation = LengthValidationOperation()

        val actual = lengthValidation.validate(metaDataField, currentFieldValue, "test")

        assertEquals(expected, actual)
    }

    private fun inValidValidationArguments(): List<Arguments> {
        return listOf(
            Arguments.of(
                JsonConfigTemplate("test", "number", minLength = "2"),
                "3",
                "Value length should be lesser than 2 in test : 3",
            ),
            Arguments.of(
                JsonConfigTemplate("test", "number", maxLength = "2"),
                "hell",
                "Value length should be greater than 2 in test : hell",
            ),
            Arguments.of(
                JsonConfigTemplate("test", "number", length = "2"),
                "hell",
                "Value length should be equal to 2 in test : hell",
            ),
            Arguments.of(
                JsonConfigTemplate("test", "number", length = "2"),
                "h",
                "Value length should be equal to 2 in test : h",
            ),
        )
    }
}