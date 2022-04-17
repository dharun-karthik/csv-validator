package validation.implementation

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import validation.implementation.DependencyValidation
import java.util.stream.Stream

class DependencyValidationTest {
    companion object {
        @JvmStatic
        fun firstArguments() = Stream.of(
            Arguments.of("null", "N", "N", "null"),
            Arguments.of("223", "Hello", "null", "null"),
            Arguments.of("AUS", "Y", "Y", "!null"),
            Arguments.of("AUS", "Y", "!null", "!null"),
        )

        @JvmStatic
        fun secondArguments() = Stream.of(
            Arguments.of("AUS", "N", "N", "null"),
            Arguments.of("223", "null", "null", "null"),
            Arguments.of("null", "Y", "Y", "!null"),
            Arguments.of("null", "India", "!null", "!null")
        )
    }

    @ParameterizedTest
    @MethodSource("firstArguments")
    fun shouldGetTrueWhenDependencyIsValid(
        currentValue: String,
        dependentValue: String,
        expectedDependentFieldValue: String,
        expectedCurrentFieldValue: String
    ) {
        val dependencyValidation = DependencyValidation()

        val actual = dependencyValidation.validate(
            currentValue,
            dependentValue,
            expectedDependentFieldValue,
            expectedCurrentFieldValue
        )

        assertTrue(actual)
    }

    @ParameterizedTest
    @MethodSource("secondArguments")
    fun shouldGetFalseWhenDependencyIsInValid(
        currentValue: String,
        dependentValue: String,
        expectedDependentFieldValue: String,
        expectedCurrentFieldValue: String
    ) {
        val dependencyValidation = DependencyValidation()

        val actual = dependencyValidation.validate(
            currentValue,
            dependentValue,
            expectedDependentFieldValue,
            expectedCurrentFieldValue
        )

        assertFalse(actual)
    }

    @Test
    fun shouldGetTrueWhenDependencyFieldValueIsNotMet() {
        val dependencyValidation = DependencyValidation()
        val currentValue = "AUS"
        val dependentValue = "Y"
        val expectedDependentFieldValue = "N"
        val expectedCurrentFieldValue = "null"
        val actual = dependencyValidation.validate(
            currentValue,
            dependentValue,
            expectedDependentFieldValue,
            expectedCurrentFieldValue
        )

        assertTrue(actual)
    }

    @Test
    fun shouldPassDespiteOfCaseSensitivity() {
        val dependencyValidation = DependencyValidation()
        val currentValue = "null"
        val dependentValue = "n"
        val expectedDependentFieldValue = "N"
        val expectedCurrentFieldValue = "null"
        val actual = dependencyValidation.validate(
            currentValue,
            dependentValue,
            expectedDependentFieldValue,
            expectedCurrentFieldValue
        )

        assertTrue(actual)
    }

    @Test
    fun shouldPassWhenNullIsPassedToIsNull(){
        val dependencyValidation = DependencyValidation()
        val nullString = "null"

        assertTrue( dependencyValidation.isNull(nullString))
    }

    @Test
    fun shouldFailWhenNotNullValueIsPassedToIsNull(){
        val dependencyValidation = DependencyValidation()
        val notNullString = "!null"

        assertFalse( dependencyValidation.isNull(notNullString))
    }

    @Test
    fun shouldFailWhenRandomValueIsPassedToIsNull(){
        val dependencyValidation = DependencyValidation()
        val randomString = "hell22"

        assertFalse( dependencyValidation.isNull(randomString))
    }

    @Test
    fun shouldPassWhenNotNullIsPassedToIsNotNull(){
        val dependencyValidation = DependencyValidation()
        val nullString = "!null"

        assertTrue( dependencyValidation.isNotNull(nullString))
    }

    @Test
    fun shouldFailWhenNullValueIsPassedToIsNotNull(){
        val dependencyValidation = DependencyValidation()
        val notNullString = "null"

        assertFalse( dependencyValidation.isNotNull(notNullString))
    }

    @Test
    fun shouldFailWhenRandomValueIsPassedToIsNotNull(){
        val dependencyValidation = DependencyValidation()
        val randomString = "hell22"

        assertFalse( dependencyValidation.isNotNull(randomString))
    }
}