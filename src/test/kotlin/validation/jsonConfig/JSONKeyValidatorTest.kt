package validation.jsonConfig

import org.json.JSONObject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class JSONKeyValidatorTest  {
    @Test
    fun shouldReturnErrorMessageIfKeyIsNotValid() {
        val jsonKeyValidator = JSONKeyValidator()
        val jsonString = "{\"fieldName\":\"xyz\",\"abc\":\"d\", \"xyz\":\"e\",\"minLength\":\"-1\",\"type\":\"text\"}"
        val jsonObject = JSONObject(jsonString)
        val actual = jsonKeyValidator.validateKey(jsonObject)

        val expected = listOf("abc is not a valid key", "xyz is not a valid key")

        assertEquals(expected, actual)
    }
}