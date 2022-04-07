package routeHandler

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PageNotFoundResponseTest {

    @Test
    fun shouldReturn400BadRequest() {
        val pageNotFoundResponse = PageNotFoundResponse()
        val expected = "HTTP/1.1 400 Bad Request\n" + "\r\n\r\n"

        val actual = pageNotFoundResponse.handleUnknownRequest()

        assertEquals(expected, actual)
    }
}