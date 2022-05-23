package routeHandler.post

import fakeStreams.FakeInputStreamProvider
import metaData.FileReaderWriter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CsvFileDownloaderTest {

    @Test
    fun shouldHandleCsvFileDownload() {
        val downloadFilePath = FileReaderWriter("src/test/kotlin/metaDataTestFiles/download/downloaded-test.csv")
        val csvFileDownloader = CsvFileDownloader(downloadFilePath)
        val request = """GET / HTTP/1.0
            |Content-Range: bytes 0-10/10
        """.trimMargin()
        val expected = "hello this"
        val fakeInputStreamProvider = FakeInputStreamProvider(expected)

        csvFileDownloader.handle(request, fakeInputStreamProvider)
        val actual = downloadFilePath.readRawContent()

        assertEquals(expected, actual)
    }

    @Test
    fun shouldGetSuccessCsvFileDownload() {
        val downloadFilePath = FileReaderWriter("src/test/kotlin/metaDataTestFiles/download/downloaded-test-2.csv")
        val csvFileDownloader = CsvFileDownloader(downloadFilePath)
        val request = """GET / HTTP/1.0
            |Content-Range: bytes 0-10/10
        """.trimMargin()
        val content = "hello this"
        val fakeInputStreamProvider = FakeInputStreamProvider(content)
        val expected = """HTTP/1.1 200 OK
            |Content-Type: text/html; charset=utf-8
            |Content-Length: 7

            |success""".trimMargin()

        val actual = csvFileDownloader.handle(request, fakeInputStreamProvider)

        assertEquals(expected, actual)
    }

}