package routeHandler

import metaData.MetaDataReaderWriter
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class DeleteRouteHandlerTest{

    @Test
    fun shouldReturnStatusCode204IfDeletedSuccessfully(){
        val request = "DELETE /reset-config HTTP/1.1\r\nHost: 0.0.0.0:3000\r\n\r\n"
        val metaDataReaderWriter = MetaDataReaderWriter("src/test/kotlin/metaDataTestFiles/meta-data-delete-test.json")
        val deleteRouteHandler = DeleteRouteHandler(metaDataReaderWriter)
        val expected = "HTTP/1.1 204 No Content\n\r\n\r\n"

        metaDataReaderWriter.appendField("{productId: 210}")
        val actual = deleteRouteHandler.handleDeleteRequest(request)

        assertEquals(expected,actual)
    }
}