package validation

import org.json.JSONArray
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class DuplicationValidationTest {
    private val duplicationValidation = DuplicationValidation()

    @Test
    fun shouldReturnEmptyListForNoDuplication() {
        val jsonString = """[{ "a": "d", "b": "e", "c": "f" }, { "a": "g", "b": "h", "c": "i" }]"""
        val jsonArray = JSONArray(jsonString)

        val actual = duplicationValidation.getDuplicateRowNumberInJSON(jsonArray)

        assertTrue(actual.isEmpty)
    }

    @Test
    fun shouldReturnMapWithDuplication() {
        val jsonString =
            """[{ "a": "d", "b": "e", "c": "f" }, { "a": "g", "b": "h", "c": "i" }, { "a": "d", "b": "e", "c": "f" }]"""
        val jsonArray = JSONArray(jsonString)
        val expected = JSONArray("""[{"Line Number 3":"Row Duplicated From 1"}]""")

        val actual = duplicationValidation.getDuplicateRowNumberInJSON(jsonArray)

        assertEquals(expected.toList(), actual.toList())
    }

    @Test
    fun shouldReturnRowNumbersForMultipleRepetitionOfOneRow() {
        val jsonString =
            """[{ "a": "d", "b": "e", "c": "f" }, { "a": "g", "b": "h", "c": "i" }, { "a": "d", "b": "e", "c": "f" }, { "a": "d", "b": "e", "c": "f" }]"""
        val jsonArray = JSONArray(jsonString)
        val expected = JSONArray("""[{"Line Number 3":"Row Duplicated From 1"},{"Line Number 4":"Row Duplicated From 1"}]""")

        val actual = duplicationValidation.getDuplicateRowNumberInJSON(jsonArray)

        assertEquals(expected.toList(), actual.toList())
    }

    @Test
    fun shouldReturnTwoListOfRowNumbersForTwoRepeatedRows() {
        val jsonString =
            """[{ "a": "d", "b": "e", "c": "f" }, { "a": "g", "b": "h", "c": "i" }, { "a": "d", "b": "e", "c": "f" }, { "a": "d", "b": "e", "c": "f" }, { "a": "g", "b": "h", "c": "i" }, { "a": "g", "b": "h", "c": "z" }]"""
        val jsonArray = JSONArray(jsonString)
        val expected =
            JSONArray("""[{"Line Number 3":"Row Duplicated From 1"},{"Line Number 4":"Row Duplicated From 1"},{"Line Number 5":"Row Duplicated From 2"}]""")

        val actual = duplicationValidation.getDuplicateRowNumberInJSON(jsonArray)

        assertEquals(expected.toList(), actual.toList())
    }

}