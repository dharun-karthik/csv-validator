package validation.jsonConfig.mandatoryFields.dependency

import metaData.template.DependencyTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class ExpectedCurrentFieldValueValidatorTest {

    @Test
    fun shouldGetErrorWhenExpectedCurrentFieldValueIsNotProvided() {
        val expectedCurrentFieldValueValidator = ExpectedCurrentFieldValueValidator()
        val dependency = DependencyTemplate("hello", "new")

        val expected = "Dependency field 'expectedCurrentFieldValue' should be present"
        val actual = expectedCurrentFieldValueValidator.validate(dependency)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldNotGetErrorWhenExpectedCurrentFieldValueIsProvided() {
        val expectedCurrentFieldValueValidator = ExpectedCurrentFieldValueValidator()
        val dependency = DependencyTemplate("hello", "new", "current")

        val actual = expectedCurrentFieldValueValidator.validate(dependency)

        assertNull(actual)
    }
}