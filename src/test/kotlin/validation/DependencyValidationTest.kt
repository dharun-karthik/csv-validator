package validation

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class DependencyValidationTest {
    @Test
    fun shouldGetTrueWhenDependencyIsValid(){
        val dependencyValidation = DependencyValidation()
        val currentField = FieldValue("Country Name","null")
        val dependentField = FieldValue("Export","N")
        val expectedDependentFieldValue = "N"
        val expectedCurrentFieldValue = null

        val actual = dependencyValidation.validate(currentField,dependentField,expectedDependentFieldValue,expectedCurrentFieldValue)

        assertTrue(actual)
    }
}