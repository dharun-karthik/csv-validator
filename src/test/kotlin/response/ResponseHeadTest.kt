package response

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import response.ResponseHead

internal class ResponseHeadTest {

    @Test
    fun shouldReturnHttpResponseHeadFor200StatusCode() {
        val responseHead = ResponseHead()
        val expected = "HTTP/1.1 200 Found\n"

        val actual = responseHead.getHttpHead(200)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldReturnHttpResponseHeadFor400StatusCode() {
        val responseHead = ResponseHead()
        val expected = "HTTP/1.1 400 Bad Request\n"

        val actual = responseHead.getHttpHead(400)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldReturnHttpResponseHeadFor401StatusCode() {
        val responseHead = ResponseHead()
        val expected = "HTTP/1.1 401 Unauthorized\n"

        val actual = responseHead.getHttpHead(401)

        assertEquals(expected, actual)
    }
}