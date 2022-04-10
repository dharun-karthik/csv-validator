package routeHandler

import metaData.MetaDataReaderWriter
import response.Response

class DeleteRouteHandler(
    private val metaDataReaderWriter: MetaDataReaderWriter,
    private val response: Response = Response()
) {
    fun handleDeleteRequest(request: String): String {
        return when(getPath(request)){
            "/reset-config" -> deleteConfig()
            else -> return response.getHttpHead(204)+"\r\n\r\n"
        }
    }

    private fun deleteConfig(): String {
        metaDataReaderWriter.clearFields()
        val endOfHeader = "\r\n\r\n"
        return response.getHttpHead(204) + endOfHeader
    }

    private fun getPath(request: String): String {
        return request.split("\r\n")[0].split(" ")[1].substringBefore("?")
    }

}
