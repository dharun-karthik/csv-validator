package routeHandler

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import routeHandler.get.GetRouteHandler
import java.io.File
import java.util.stream.Stream

class GetRouteHandlerTest {
    companion object {
        @JvmStatic
        fun contentTypeArguments() = Stream.of(
            Arguments.of("/css/style2.css", "text/css"),
            Arguments.of("/js/app.js", "text/javascript"),
        )
    }

    @Test
    fun shouldBeAbleToGetResponse() {
        val getRouteHandler = GetRouteHandler()
        val length = File("src/main/public/index.html").readText().length
        val expectedContentLength = "Content-Length: $length"
        val request = "GET / HTTP/1.1\n\n"

        val actualContentLength =
            getRouteHandler.handleGetRequest(request).split("\n")[2].replace("\n", "").replace("\r", "")
        assertEquals(expectedContentLength, actualContentLength)
    }

    @Test
    fun shouldReturnMetaDataJsonIfTheGetRequestIsMadeForMetaDataJson() {
        val getRouteHandler = GetRouteHandler()
        val lineSeparator = System.lineSeparator()
        val request = "GET /get-meta-data HTTP/1.1$lineSeparator$lineSeparator"
        val expected = getMetaDataContent()

        val actual = getRouteHandler.handleGetRequest(request).split(lineSeparator + lineSeparator)[1]

        assertEquals(expected, actual)
    }

    private fun getMetaDataContent(): String {
        val path = System.getProperty("user.dir")
        val file = File("$path/src/main/public/files/csv-config.json")
        return file.readText(Charsets.UTF_8)
    }

    @ParameterizedTest
    @MethodSource("contentTypeArguments")
    fun shouldGetRightContentType(requestPath: String, expectedContentType: String) {
        val getRouteHandler = GetRouteHandler()
        val request = "GET $requestPath HTTP/1.1\n\n"
        val expected = "Content-Type: $expectedContentType; charset=utf-8"

        val actual = getRouteHandler.handleGetRequest(request).split("\n")[1]

        assertEquals(expected, actual)
    }

    @Test
    fun shouldGet404ResponseStatusCodeForInvalidLink() {
        val getRouteHandler = GetRouteHandler()
        val request = "GET /random HTTP/1.1\n\n"
        val expected = "404"

        val actual = getRouteHandler.handleGetRequest(request).split("\n")[0].split(" ")[1]

        assertEquals(expected, actual)
    }
}