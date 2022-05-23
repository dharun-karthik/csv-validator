package request

import fakeStreams.FakeBufferedReader
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class RequestHandlerTest {

    private val requestHandler = RequestHandler()

    @Test
    fun shouldGetPath() {
        val expected = "/man"

        val actual = requestHandler.getPath("GET /man HTTP/1.1")

        assertEquals(expected, actual)
    }

    @Test
    fun shouldGetContentLength() {
        val request = """GET / HTTP/1.1
Content-Type: application/x-www-form-urlencoded
Content-Length: 81"""
        val expected = 81

        val actual = requestHandler.getContentLength(request)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldGetDefaultContentLengthWhenItIsNotGiven() {
        val request = """GET / HTTP/1.1
Content-Type: application/x-www-form-urlencoded"""
        val expected = 0

        val actual = requestHandler.getContentLength(request)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldGetNullWhenContentRangeIsNotGiven() {
        val request = """GET / HTTP/1.1
Content-Type: application/x-www-form-urlencoded"""

        val actual = requestHandler.getContentRange(request)

        assertNull(actual)
    }

    @Test
    fun shouldGetContentRange() {
        val request = """GET / HTTP/1.1
Content-Type: application/x-www-form-urlencoded
Content-Range: bytes 200-1000/67589"""
        val expected = ContentRange("bytes", 200, 1000, 67589)

        val actual = requestHandler.getContentRange(request)

        assertEquals(expected, actual)
    }

    @Test
    fun shouldGetBodyInString() {
        val content = "hello this is me"
        val fakeBufferedReader = FakeBufferedReader(content)

        val actual = requestHandler.getBodyInString(content.length, fakeBufferedReader)

        assertEquals(content, actual)
    }

    @Test
    fun shouldGetEmptyBodyInStringWhenThereIsNoContent() {
        val content = ""
        val fakeBufferedReader = FakeBufferedReader(content)

        val actual = requestHandler.getBodyInString(content.length, fakeBufferedReader)

        assertEquals(content, actual)
    }
}