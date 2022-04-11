package routeHandler

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

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

    @Test
    fun shouldReturnMetaDataJsonIfTheGetRequestIsMadeForMetaDataJson(){
        val getRouteHandler = GetRouteHandler()
        val request = "GET /get-meta-data HTTP/1.1\n\n"
        val expected = getMetaDataContent()

        val actual = getRouteHandler.handleGetRequest(request).split("\r\n").last()

        assertEquals(expected,actual)
    }

    private fun getMetaDataContent(): String {
        val path = System.getProperty("user.dir")
        var file = File("$path/src/main/public/csv-meta-data.json")
        return file.readText(Charsets.UTF_8)
    }
}