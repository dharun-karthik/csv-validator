package routeHandler

import metaData.MetaDataReaderWriter
import request.RequestHandle
import response.ContentType
import response.Response

class DeleteRouteHandler(
    private val metaDataReaderWriter: MetaDataReaderWriter,
    private val response: Response = Response()
) {
    private val requestHandler = RequestHandle()
    fun handleDeleteRequest(request: String): String {
        return when(requestHandler.getPath(request)){
            "/reset-config" -> deleteConfig()
            else -> return response.getHttpHead(200)+"\r\n\r\n"
        }
    }

    private fun deleteConfig(): String {
        metaDataReaderWriter.clearFields()
        return response.generateResponse("Content Deleted",200, ContentType.HTML.value)
    }
}
