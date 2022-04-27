package validation.operation

import metaData.template.DependencyTemplate
import metaData.template.JsonMetaDataTemplate
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class DependencyValidatorOperationTest {

    @Test
    fun shouldGetErrorWhenDependencyIsNotMet() {
        val dependencyValidation = DependencyValidationOperation()
        val dependency = DependencyTemplate("parent", "null", "null")
        val metaDataField = JsonMetaDataTemplate("test", "alphabets", dependencies = listOf(dependency))
        val currentRow = JSONObject("""{"test":"hello","parent":"null"}""")

        val actual = dependencyValidation.validate(metaDataField, "hello", "test", currentRow)
        val expected = "parent is null but test is hello"

        assertEquals(expected, actual)
    }

    @Test
    fun shouldGetNullWhenDependencyConditionIsMet() {
        val dependencyValidation = DependencyValidationOperation()
        val dependency = DependencyTemplate("parent", "null", "null")
        val metaDataField = JsonMetaDataTemplate("test", "alphabets", dependencies = listOf(dependency))
        val currentRow = JSONObject("""{"test":"null","parent":"null"}""")

        val actual = dependencyValidation.validate(metaDataField, "null", "test", currentRow)

        assertNull(actual)
    }
}