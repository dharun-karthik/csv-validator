package routeHandler

import metaData.ConfigFileReaderWriter
import request.RequestHandler
import response.Response
import routeHandler.get.FileGetter

class DeleteRouteHandler(
    private val configFileReaderWriter: ConfigFileReaderWriter,
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
        configFileReaderWriter.clearFields()
        return response.onlyHeaderResponse(204)
    }
}
