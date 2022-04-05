package routeHandler

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class UnknownTest {

    @Test
    fun shouldReturn400BadRequest() {
        val unknown = Unknown()
        val expected = "HTTP/1.1 400 Bad Request\n"  + "\r\n\r\n"

        val actual = unknown.handleUnknownRequest()

        assertEquals(expected,actual)
    }
}