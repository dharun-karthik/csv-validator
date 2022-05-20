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
}