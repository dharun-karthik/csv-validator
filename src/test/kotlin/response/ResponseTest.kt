package response

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ResponseTest {

    @Test
    fun shouldReturnHttpResponseHeadFor200StatusCode() {
        val response = Response()
        val expected = "HTTP/1.1 200 OK\n"

        val actual = response.getHttpHead(200)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldReturnHttpResponseHeadFor400StatusCode() {
        val response = Response()
        val expected = "HTTP/1.1 400 Bad Request\n"

        val actual = response.getHttpHead(400)

        assertEquals(expected, actual)
    }
}