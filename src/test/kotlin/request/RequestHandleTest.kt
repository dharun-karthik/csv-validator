package request

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class RequestHandleTest {

    internal class GetBodyTest {

        private val requestHandler = RequestHandler()

        @Test
        fun shouldReturnPathForGetRequest() {
            val request = "GET /favicon.ico HTTP/1.1"
            val expected = "/favicon.ico"

            val actual = requestHandler.getPath(request)

            assertEquals(expected, actual)
        }

        @Test
        fun shouldReturnPathForPostRequest() {
            val request = "POST /csv-meta-data HTTP/1.1"
            val expected = "/csv-meta-data"

            val actual = requestHandler.getPath(request)

            assertEquals(expected, actual)
        }

        @Test
        fun shouldGetContentRangeFromHeader() {
            val header = """POST /test/file-in-chunks HTTP/1.1
                |Host: localhost:3000
                |Connection: keep-alive
                |Content-Length: 253
                |content-range: bytes 0-253/2583 
            """.trimIndent()

            val actual = requestHandler.getContentRange(header)
            val expected = ContentRange("bytes", 0, 253, 2583)

            assertEquals(expected, actual)
        }
    }

}