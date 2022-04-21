package routeHandler

import metaData.ConfigReaderWriter
import request.RequestHandler
import response.Response
import routeHandler.get.FileGetter

class DeleteRouteHandler(
    private val configReaderWriter: ConfigReaderWriter,
    private val response: Response = Response()
) {
    private val fileGetter = FileGetter()
    private val requestHandler = RequestHandler()
    fun handleDeleteRequest(request: String): String {
        return when (requestHandler.getPath(request)) {
            "/reset-config" -> deleteMetaData()
            else -> return fileGetter.getFileNotFound()
        }
    }

    private fun deleteMetaData(): String {
        configReaderWriter.clearFields()
        return response.onlyHeaderResponse(204)
    }
}
