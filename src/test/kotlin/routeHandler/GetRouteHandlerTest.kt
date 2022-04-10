package routeHandler

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GetRouteHandlerTest {

    @Test
    fun shouldBeAbleToGetResponse() {
        val getRouteHandler = GetRouteHandler()
        val expectedContentLength = "Content-Length: 3057"
        val request = "GET / HTTP/1.1\n\n"

        val actualContentLength =
            getRouteHandler.handleGetRequest(request).split("\n")[2].replace("\n", "").replace("\r", "")
        assertEquals(expectedContentLength, actualContentLength)
    }
}