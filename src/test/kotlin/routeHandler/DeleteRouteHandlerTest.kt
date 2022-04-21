package routeHandler

import metaData.ConfigReaderWriter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DeleteRouteHandlerTest {

    @Test
    fun shouldReturnStatusCode204IfDeletedSuccessfully() {
        val request = "DELETE /reset-config HTTP/1.1\r\nHost: 0.0.0.0:3000\r\n\r\n"
        val configReaderWriter = ConfigReaderWriter("src/test/kotlin/metaDataTestFiles/meta-data-delete-test.json")
        val deleteRouteHandler = DeleteRouteHandler(configReaderWriter)
        val lineSeparator = System.lineSeparator()
        val expected = "HTTP/1.1 204 No Content$lineSeparator$lineSeparator"

        configReaderWriter.writeRawContent("[{productId: 210}]")
        val actual = deleteRouteHandler.handleDeleteRequest(request)

        assertEquals(expected, actual)
    }
}