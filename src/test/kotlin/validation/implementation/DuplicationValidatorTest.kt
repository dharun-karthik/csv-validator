package validation.implementation

import org.json.JSONArray
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class DuplicationValidatorTest {

    @Test
    fun shouldReturnEmptyListForNoDuplication() {
        val duplicationValidation = DuplicationValidation()
        val jsonString = """[{ "a": "d", "b": "e", "c": "f" }]"""
        val testJson = JSONObject("""{ "a": "g", "b": "h", "c": "i" }""")
        val jsonArray = JSONArray(jsonString)
        var index = 1
        for (json in jsonArray) {
            duplicationValidation.isDuplicateIndexAvailable(json, index++)
        }

        val actual = duplicationValidation.isDuplicateIndexAvailable(testJson, 2)

        assertNull(actual)
    }

    @Test
    fun shouldReturnMapWithDuplication() {
        val duplicationValidation = DuplicationValidation()
        val jsonString = """[{ "a": "d", "b": "e", "c": "f" }, { "a": "g", "b": "h", "c": "i" }]"""
        val testJson = JSONObject(""" { "a": "d", "b": "e", "c": "f" }""")
        val jsonArray = JSONArray(jsonString)
        for ((index, json) in jsonArray.withIndex()) {
            duplicationValidation.isDuplicateIndexAvailable(json, index)
        }
        val expected = 1

        val actual = duplicationValidation.isDuplicateIndexAvailable(testJson, 3)

        assertEquals(expected, actual)
    }
}