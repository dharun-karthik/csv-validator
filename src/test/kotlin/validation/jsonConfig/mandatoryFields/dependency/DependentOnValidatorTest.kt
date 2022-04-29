package validation.jsonConfig.mandatoryFields.dependency

import metaData.template.DependencyTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class DependentOnValidatorTest {

    @Test
    fun shouldGetErrorWhenDependentOnValueIsNotProvided() {
        val expectedCurrentFieldValueValidator = DependentOnValidator()
        val dependency = DependencyTemplate(expectedCurrentFieldValue = "hello", expectedDependentFieldValue = "new")

        val expected = "Dependency field 'dependentOn' should be present"
        val actual = expectedCurrentFieldValueValidator.validate(dependency)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldNotGetErrorWhenDependentOnValueIsProvided() {
        val expectedCurrentFieldValueValidator = DependentOnValidator()
        val dependency = DependencyTemplate("hello", "new", "current")

        val actual = expectedCurrentFieldValueValidator.validate(dependency)

        assertNull(actual)
    }
}