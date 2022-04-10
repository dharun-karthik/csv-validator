package routeHandler

import metaData.MetaDataReaderWriter
import response.ResponseHead

class DeleteRouteHandler(
    private val metaDataReaderWriter: MetaDataReaderWriter,
    private val responseHead: ResponseHead = ResponseHead()
) {
    fun handleDeleteRequest(request: String): String {
        return when(getPath(request)){
            "/reset-config" -> deleteConfig()
            else -> return responseHead.getHttpHead(204)+"\r\n\r\n"
        }
    }

    private fun deleteConfig(): String {
        metaDataReaderWriter.clearFields()
        val endOfHeader = "\r\n\r\n"
        return responseHead.getHttpHead(204) + endOfHeader
    }

    private fun getPath(request: String): String {
        return request.split("\r\n")[0].split(" ")[1].substringBefore("?")
    }

}
