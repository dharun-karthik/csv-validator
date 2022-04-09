package validation

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class DependencyValidationTest {
    @Test
    fun shouldGetTrueWhenDependencyIsValid(){
        val dependencyValidation = DependencyValidation()
        val currentField = FieldValue("Country Name",null)
        val dependentField = FieldValue("Export","N")
        val expectedDependentFieldValue = "N"
        val expectedCurrentFieldValue = null

        val actual = dependencyValidation.validate(currentField,dependentField,expectedDependentFieldValue,expectedCurrentFieldValue)

        assertTrue(actual)
    }

    @Test
    fun shouldGetFalseWhenDependencyIsInValid(){
        val dependencyValidation = DependencyValidation()
        val currentField = FieldValue("Country Name","AUS")
        val dependentField = FieldValue("Export","N")
        val expectedDependentFieldValue = "N"
        val expectedCurrentFieldValue = null

        val actual = dependencyValidation.validate(currentField,dependentField,expectedDependentFieldValue,expectedCurrentFieldValue)

        assertFalse(actual)
    }

    @Test
    fun shouldGetTrueWhenDependencyFieldValueIsNotMet(){
        val dependencyValidation = DependencyValidation()
        val currentField = FieldValue("Country Name","AUS")
        val dependentField = FieldValue("Export","Y")
        val expectedDependentFieldValue = "N"
        val expectedCurrentFieldValue = null

        val actual = dependencyValidation.validate(currentField,dependentField,expectedDependentFieldValue,expectedCurrentFieldValue)

        assertTrue(actual)
    }

    @Test
    fun shouldPassDespiteOfCaseSensitivity(){
        val dependencyValidation = DependencyValidation()
        val currentField = FieldValue("Country Name",null)
        val dependentField = FieldValue("Export","n")
        val expectedDependentFieldValue = "N"
        val expectedCurrentFieldValue = null

        val actual = dependencyValidation.validate(currentField,dependentField,expectedDependentFieldValue,expectedCurrentFieldValue)

        assertTrue(actual)
    }
}