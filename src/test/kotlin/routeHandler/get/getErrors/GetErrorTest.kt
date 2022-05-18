package routeHandler.get.getErrors

import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GetErrorTest {

    @Test
    fun shouldGetErrors() {
        val errorContent =
            """{"Emoji":{"total-error-count":1,"details":{"Type mismatch error":{"error-count":1,"lines":{"1":"Incorrect format of 'text' in Emoji : 😁"}}}},"Meaning":{"total-error-count":1,"details":{"Type mismatch error":{"error-count":1,"lines":{"1":"Incorrect format of 'alphabets' in Meaning : Beaming Face With Smiling Eyes"}}}}}"""
        val getError = GetError(JSONObject(errorContent))
        val request = """GET /get-error HTTP/1.0
            |column-name : Emoji
            |error-type : Type mismatch error
        """.trimIndent()
        val expected = """{"1":"Incorrect format of 'text' in Emoji : 😁"}"""

        val actual = getError.get(request)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldGetMultipleErrors() {
        val errorContent =
            """{"date":{"total-error-count":2,"details":{"Type mismatch error":{"error-count":2,"lines":{"1":"Incorrect format of 'alphabets' in date : 11/3/1888","2":"Incorrect format of 'alphabets' in date : 06/06/2000"}}}}}"""
        val getError = GetError(JSONObject(errorContent))
        val request = """GET /get-error HTTP/1.0
            |column-name : date
            |error-type : Type mismatch error
        """.trimIndent()
        val expected = """{"1":"Incorrect format of 'alphabets' in date : 11/3/1888","2":"Incorrect format of 'alphabets' in date : 06/06/2000"}"""

        val actual = getError.get(request)

        assertEquals(expected, actual)
    }
}