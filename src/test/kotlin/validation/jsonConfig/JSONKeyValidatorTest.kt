package validation.jsonConfig

import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class JSONKeyValidatorTest {
    @Test
    fun shouldReturnErrorMessageIfKeyIsNotValid() {
        val jsonKeyValidator = JSONKeyValidator()
        val jsonString = "{\"fieldName\":\"xyz\",\"abc\":\"d\", \"xyz\":\"e\",\"minLength\":\"-1\",\"type\":\"text\"}"
        val jsonObject = JSONObject(jsonString)
        val expected = listOf("abc is not a valid key", "xyz is not a valid key")

        val actual = jsonKeyValidator.validateKey(jsonObject)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldNotReturnErrorMessageIfKeyIsValid() {
        val jsonKeyValidator = JSONKeyValidator()
        val jsonString = "{\"fieldName\":\"xyz\",\"minLength\":\"-1\",\"type\":\"text\"}"
        val jsonObject = JSONObject(jsonString)
        val expected = listOf<String>()

        val actual = jsonKeyValidator.validateKey(jsonObject)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldReturnErrorMessageForInvalidKeyInsideDependencies() {
        val jsonKeyValidator = JSONKeyValidator()
        val jsonString =
            "{\"fieldName\":\"xyz\",\"type\":\"text\",\"dependencies\":[{\"expectedDependentFieldValue\":\"a\",\"expectedCurrentFieldValue\":\"s\",\"dependentOn\":\"abc\",\"xyz\":\"1\"}]}"
        val jsonObject = JSONObject(jsonString)
        val expected = listOf("xyz is not a valid key inside dependency")

        val actual = jsonKeyValidator.validateKey(jsonObject)

        assertEquals(expected, actual)
    }
}