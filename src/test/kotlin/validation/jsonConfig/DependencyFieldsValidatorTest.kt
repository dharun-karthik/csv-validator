package validation.jsonConfig

import metaData.template.DependencyTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class DependencyFieldsValidatorTest {

    @Test
    fun shouldGetErrorsWhenDependencyFieldsAreNotProvided() {
        val dependencyFieldsValidator = DependencyFieldsValidator()
        val dependencies = listOf(
            DependencyTemplate(dependentOn = "hello", expectedCurrentFieldValue = "current"),
            DependencyTemplate(expectedCurrentFieldValue = "current", expectedDependentFieldValue = "given"),
            DependencyTemplate(dependentOn = "hello", expectedDependentFieldValue = "dependent")
        )

        val expected =
            """[{"1":["Dependency field 'expectedDependentFieldValue' should be present"]},{"2":["Dependency field 'dependentOn' should be present"]},{"3":["Dependency field 'expectedCurrentFieldValue' should be present"]}]"""
        val actual = dependencyFieldsValidator.validate(dependencies)

        assertEquals(expected, actual.toString())
    }

    @Test
    fun shouldNotGetErrorWhenDependencyFieldIsGiven() {
        val dependencyFieldsValidator = DependencyFieldsValidator()
        val dependencies = listOf(
            DependencyTemplate(
                dependentOn = "hello",
                expectedCurrentFieldValue = "current",
                expectedDependentFieldValue = "dependent"
            )
        )

        val expected = "[]"
        val actual = dependencyFieldsValidator.validate(dependencies)

        assertEquals(expected, actual.toString())

    }
}