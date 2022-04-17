package request

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class RequestHandleTest {

    internal class GetBodyTest {

        @Test
        fun shouldReturnPathForGetRequest() {
            val requestHandler = RequestHandler()
            val request = "GET /favicon.ico HTTP/1.1"
            val expected = "/favicon.ico"

            val actual = requestHandler.getPath(request)

            assertEquals(expected, actual)
        }

        @Test
        fun shouldReturnPathForPostRequest() {
            val requestHandler = RequestHandler()
            val request = "POST /csv-meta-data HTTP/1.1"
            val expected = "/csv-meta-data"

            val actual = requestHandler.getPath(request)

            assertEquals(expected, actual)
        }
    }

}