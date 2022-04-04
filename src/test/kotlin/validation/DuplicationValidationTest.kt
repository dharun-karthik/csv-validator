package validation

import org.json.JSONArray
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DuplicationValidationTest {
    private val duplicationValidation = DuplicationValidation()

    @Test
    fun shouldReturnEmptyListForNoDuplication() {
        val jsonString = "[{ \"a\": \"d\", \"b\": \"e\", \"c\": \"f\" }, { \"a\": \"g\", \"b\": \"h\", \"c\": \"i\" }]"
        val jsonArray = JSONArray(jsonString)

        val actual = duplicationValidation.getDuplicateRowNumberInJSON(jsonArray)

        assertTrue(actual.isEmpty())
    }

    @Test
    fun shouldReturnMapWithDuplication() {
        val jsonString =
            "[{ \"a\": \"d\", \"b\": \"e\", \"c\": \"f\" }, { \"a\": \"g\", \"b\": \"h\", \"c\": \"i\" }, { \"a\": \"d\", \"b\": \"e\", \"c\": \"f\" }]"
        val jsonArray = JSONArray(jsonString)
        val expected = listOf(listOf(1, 3))

        val actual = duplicationValidation.getDuplicateRowNumberInJSON(jsonArray)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldReturnRowNumbersForMultipleRepetitionOfOneRow() {
        val jsonString =
            "[{ \"a\": \"d\", \"b\": \"e\", \"c\": \"f\" }, { \"a\": \"g\", \"b\": \"h\", \"c\": \"i\" }, { \"a\": \"d\", \"b\": \"e\", \"c\": \"f\" }, { \"a\": \"d\", \"b\": \"e\", \"c\": \"f\" }]"
        val jsonArray = JSONArray(jsonString)
        val expected = listOf(listOf(1, 3, 4))

        val actual = duplicationValidation.getDuplicateRowNumberInJSON(jsonArray)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldReturnTwoListOfRowNumbersForTwoRepeatedRows() {
        val jsonString =
            "[{ \"a\": \"d\", \"b\": \"e\", \"c\": \"f\" }, { \"a\": \"g\", \"b\": \"h\", \"c\": \"i\" }, { \"a\": \"d\", \"b\": \"e\", \"c\": \"f\" }, { \"a\": \"d\", \"b\": \"e\", \"c\": \"f\" }, { \"a\": \"g\", \"b\": \"h\", \"c\": \"i\" }, { \"a\": \"g\", \"b\": \"h\", \"c\": \"z\" }]"
        val jsonArray = JSONArray(jsonString)
        val expected = listOf(listOf(1, 3, 4), listOf(2, 5))

        val actual = duplicationValidation.getDuplicateRowNumberInJSON(jsonArray)

        assertEquals(expected, actual)
    }

}