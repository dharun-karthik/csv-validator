package routeHandler

import metaData.MetaDataReaderWriter
import request.RequestHandle
import response.Response

class DeleteRouteHandler(
    private val metaDataReaderWriter: MetaDataReaderWriter,
    private val response: Response = Response()
) {
    private val requestHandler = RequestHandle()
    fun handleDeleteRequest(request: String): String {
        return when(requestHandler.getPath(request)){
            "/reset-config" -> deleteConfig()
            else -> return response.onlyHeaderResponse(200)
        }
    }

    private fun deleteConfig(): String {
        metaDataReaderWriter.clearFields()
        return response.onlyHeaderResponse(204)
    }
}
