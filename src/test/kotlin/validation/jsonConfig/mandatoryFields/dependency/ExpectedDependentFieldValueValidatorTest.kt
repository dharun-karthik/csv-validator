package validation.jsonConfig.mandatoryFields.dependency

import metaData.template.DependencyTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class ExpectedDependentFieldValueValidatorTest {

    @Test
    fun shouldGetErrorWhenExpectedDependentFieldValueIsNotProvided() {
        val expectedDependentFieldValueValidator = ExpectedDependentFieldValueValidator()
        val dependency = DependencyTemplate("hello", expectedCurrentFieldValue = "new")
        val expected = "Dependency field 'expectedDependentFieldValue' should be present"

        val actual = expectedDependentFieldValueValidator.validate(dependency)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldNotGetErrorWhenExpectedDependentFieldValueIsProvided() {
        val expectedDependentFieldValueValidator = ExpectedDependentFieldValueValidator()
        val dependency = DependencyTemplate("hello", "new", "current")

        val actual = expectedDependentFieldValueValidator.validate(dependency)

        assertNull(actual)
    }
}